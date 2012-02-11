/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.sync.periodic;

import java.util.HashMap;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.os.Handler;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;


abstract class AbstractPeriodicSyncHandler implements IPeriodicSyncHandler,
         IOnSettingsChangedListener, OnAccountsUpdateListener
{
   private final Handler handler = new Handler();
   
   private boolean hasAccount = false;
   
   protected final Context context;
   
   
   
   protected AbstractPeriodicSyncHandler( Context context )
   {
      this.context = context;
      
      registerSettingsChangedListener();
      registerAccountsUpdatedListener();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      if ( which == IOnSettingsChangedListener.SYNC_INTERVAL )
      {
         final Account account = AccountUtils.getRtmAccount( context );
         
         if ( account != null )
         {
            final long syncInterval = MolokoApp.getSettings( context )
                                               .getSyncInterval();
            
            if ( syncInterval != Constants.SYNC_INTERVAL_MANUAL )
               SyncUtils.schedulePeriodicSync( context.getApplicationContext(),
                                               syncInterval );
            else
               MolokoApp.get( context.getApplicationContext() )
                        .stopPeriodicSync();
         }
      }
   }
   
   
   
   @Override
   public void onAccountsUpdated( Account[] accounts )
   {
      boolean foundAccount = false;
      
      if ( accounts != null )
      {
         for ( int i = 0; i < accounts.length && !foundAccount; i++ )
         {
            final Account account = accounts[ i ];
            foundAccount = account != null
               && account.type.equals( dev.drsoran.moloko.auth.Constants.ACCOUNT_TYPE );
         }
      }
      
      if ( foundAccount && !hasAccount )
      {
         SyncUtils.schedulePeriodicSync( context );
         hasAccount = true;
      }
      else if ( !foundAccount && hasAccount )
      {
         MolokoApp.get( context.getApplicationContext() ).stopPeriodicSync();
         hasAccount = false;
      }
   }
   
   
   
   @Override
   public void shutdown()
   {
      unregisterSettingsChangedListener();
      unregisterAccountsUpdatedListener();
   }
   
   
   
   private void registerSettingsChangedListener()
   {
      MolokoApp.getNotifierContext( context )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.SYNC_INTERVAL,
                                                   this );
   }
   
   
   
   private void unregisterSettingsChangedListener()
   {
      MolokoApp.getNotifierContext( context )
               .unregisterOnSettingsChangedListener( this );
   }
   
   
   
   private void registerAccountsUpdatedListener()
   {
      final AccountManager accountManager = AccountManager.get( context );
      if ( accountManager != null )
         accountManager.addOnAccountsUpdatedListener( this, handler, true );
   }
   
   
   
   private void unregisterAccountsUpdatedListener()
   {
      final AccountManager accountManager = AccountManager.get( context );
      if ( accountManager != null )
         accountManager.removeOnAccountsUpdatedListener( this );
   }
}
