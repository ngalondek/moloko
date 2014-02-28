/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.sync;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.SyncResult;
import android.database.SQLException;
import android.os.Bundle;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.ISyncService;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.service.IRtmSyncService;
import dev.drsoran.rtm.service.RtmErrorCodes;
import dev.drsoran.rtm.service.RtmServicePermission;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.SyncTime;


public final class SyncAdapter extends AbstractThreadedSyncAdapter
{
   private final static Class< SyncAdapter > TAG = SyncAdapter.class;
   
   private final AppContext context;
   
   private final ILog log;
   
   private final ISyncService syncService;
   
   private final IAccountService accountService;
   
   
   
   public SyncAdapter( AppContext context, boolean autoInitialize )
   {
      super( context, autoInitialize );
      
      this.context = context;
      this.log = context.Log();
      this.syncService = context.getSyncService();
      this.accountService = context.getAccountService();
   }
   
   
   
   @Override
   public void onPerformSync( Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult )
   {
      if ( shouldProcessRequest( extras ) )
      {
         log.i( TAG,
                MessageFormat.format( "Precessing sync with extras {0}", extras ) );
         
         context.sendBroadcast( Intents.createSyncStartedIntent() );
         
         final IRtmSyncPartner syncPartner = syncService.getSyncPartner();
         final IRtmSyncService rtmSyncService = context.getRtmSyncService( syncPartner );
         final RtmServicePermission permission = accountService.getAccessLevel( account );
         final SyncTime lastSyncTime = context.getContentRepository()
                                              .getSyncTimes();
         
         try
         {
            SyncTime newSyncTime = null;
            
            if ( isSettingsOnlySync( extras )
               || permission == RtmServicePermission.read )
            {
               rtmSyncService.performIncomingSync( lastSyncTime );
               newSyncTime = new SyncTime( System.currentTimeMillis(),
                                           lastSyncTime.getLastSyncOutMillis() );
            }
            else if ( permission == RtmServicePermission.write )
            {
               rtmSyncService.performFullSync( lastSyncTime );
               
               final long now = System.currentTimeMillis();
               newSyncTime = new SyncTime( now, now );
            }
            
            if ( newSyncTime != null )
            {
               context.asDomainContext()
                      .getContentEditService()
                      .setSyncTimes( newSyncTime );
            }
         }
         catch ( RtmServiceException e )
         {
            final Throwable innerException = e.getCause();
            
            if ( innerException == null
               || innerException instanceof RtmServiceException )
            {
               if ( RtmErrorCodes.isAuthError( e.getResponseCode() ) )
               {
                  handleAuthError( account, syncResult, e );
               }
               else if ( RtmErrorCodes.isServiceError( e.getResponseCode() ) )
               {
                  handleServiceError( syncResult, e );
               }
            }
            else if ( innerException instanceof IOException )
            {
               handleIOException( syncResult, (IOException) innerException );
            }
            else if ( innerException instanceof SQLException )
            {
               handleSQLException( syncResult, (SQLException) innerException );
            }
            else if ( innerException instanceof ParseException )
            {
               handleParseError( syncResult, (ParseException) innerException );
            }
         }
         finally
         {
            context.sendBroadcast( Intents.createSyncFinishedIntent() );
         }
      }
      else
      {
         log.i( TAG,
                MessageFormat.format( "Didn''t processed sync with extras {0}",
                                      extras ) );
      }
   }
   
   
   
   private void handleAuthError( Account account,
                                 SyncResult syncResult,
                                 RtmServiceException e )
   {
      ++syncResult.stats.numAuthExceptions;
      
      log.e( TAG,
             MessageFormat.format( "Sync failed due to authentication error: {0}: {1}",
                                   e.getResponseCode(),
                                   e.getResponseMessage() ) );
      
      if ( e.getResponseCode() == RtmErrorCodes.INVALID_AUTH_TOKEN )
      {
         log.i( TAG, "Invalidating auth token." );
         accountService.invalidateAccount( account );
      }
   }
   
   
   
   private void handleServiceError( SyncResult syncResult, RtmServiceException e )
   {
      log.e( TAG,
             MessageFormat.format( "Sync failed due to RTM service error: {0}: {1}",
                                   e.getResponseCode(),
                                   e.getResponseMessage() ) );
   }
   
   
   
   private void handleIOException( SyncResult syncResult, IOException ex )
   {
      ++syncResult.stats.numIoExceptions;
      log.e( TAG, MessageFormat.format( "Sync failed due to IO error", ex ) );
   }
   
   
   
   private void handleSQLException( SyncResult syncResult, SQLException ex )
   {
      syncResult.databaseError = true;
      log.e( TAG,
             MessageFormat.format( "Sync failed due to database error", ex ) );
   }
   
   
   
   private void handleParseError( SyncResult syncResult, ParseException ex )
   {
      ++syncResult.stats.numParseExceptions;
      log.e( TAG, MessageFormat.format( "Sync failed due to parsing error", ex ) );
   }
   
   
   
   private static boolean isSettingsOnlySync( Bundle extras )
   {
      final boolean isSyncSettingsOnly = extras.getBoolean( SyncConstants.SYNC_EXTRAS_ONLY_SETTINGS,
                                                            false );
      return isSyncSettingsOnly;
   }
   
   
   
   private static boolean shouldProcessRequest( Bundle bundle )
   {
      return ( bundle != null && ( bundle.containsKey( ContentResolver.SYNC_EXTRAS_INITIALIZE )
         || bundle.containsKey( ContentResolver.SYNC_EXTRAS_MANUAL ) || bundle.containsKey( SyncConstants.SYNC_EXTRAS_SCHEDULED ) ) );
   }
}
