/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.services;

import java.text.MessageFormat;

import android.content.ContentProvider;
import android.content.Context;
import android.os.Handler;
import dev.drsoran.moloko.IConnectionService;
import dev.drsoran.moloko.IHandlerTokenFactory;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.account.AccountService;
import dev.drsoran.moloko.app.event.AppEventService;
import dev.drsoran.moloko.app.settings.SettingsService;
import dev.drsoran.moloko.content.ContentAuthority;
import dev.drsoran.moloko.content.db.DbContentProvider;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.sync.DbRtmSyncPartnerFactory;
import dev.drsoran.moloko.content.db.sync.RtmContentValuesFactory;
import dev.drsoran.moloko.content.db.sync.RtmModelElementFactory;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.content.MolokoContentValuesFactory;
import dev.drsoran.rtm.RtmConnectionProtocol;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.service.IRtmService;
import dev.drsoran.rtm.service.RtmService;
import dev.drsoran.rtm.service.RtmServicePermission;


public class AppServicesContainer implements IAppServices
{
   private final Context context;
   
   private final IConnectionService connectionService;
   
   private final AppEventService appEventService;
   
   private final SettingsService settingsService;
   
   private final IAccountService accountService;
   
   private final SyncService syncService;
   
   private final IAppContentEditService appContentEditService;
   
   private final ILog log;
   
   private final IRtmCalendarProvider calendarProvider;
   
   private IRtmService rtmService;
   
   
   
   public AppServicesContainer( DomainContext context, Handler handler,
      IHandlerTokenFactory handlerTokenFactory,
      IConnectionService connectionService, SettingsService settingsService,
      ILog log )
   {
      this.context = context;
      this.connectionService = connectionService;
      this.log = log;
      
      this.appEventService = new AppEventService( context,
                                                  handler,
                                                  log,
                                                  handlerTokenFactory );
      
      this.settingsService = settingsService;
      settingsService.setAppEvents( appEventService );
      
      this.accountService = new AccountService( context );
      
      this.calendarProvider = context.getCalendarProvider();
      
      this.syncService = new SyncService( new DbRtmSyncPartnerFactory( getDatabase(),
                                                                       new RtmModelElementFactory(),
                                                                       new RtmContentValuesFactory(),
                                                                       new MolokoContentValuesFactory(),
                                                                       context.getParsingService()
                                                                              .getDateTimeParsing(),
                                                                       calendarProvider,
                                                                       log ),
                                          settingsService,
                                          connectionService,
                                          appEventService,
                                          accountService,
                                          log );
      
      this.appContentEditService = new AppContentEditService( context,
                                                              context.getContentEditService(),
                                                              accountService,
                                                              calendarProvider );
      checkForcedReadableAccess();
      
      this.rtmService = createRtmService( settingsService.getRtmConnectionProtocol() );
   }
   
   
   
   public void shutdown()
   {
      appEventService.shutdown();
      syncService.shutdown();
      settingsService.shutdown();
   }
   
   
   
   @Override
   public IAppEventService getAppEvents()
   {
      return appEventService;
   }
   
   
   
   @Override
   public ISettingsService getSettings()
   {
      return settingsService;
   }
   
   
   
   @Override
   public ISyncService getSyncService()
   {
      return syncService;
   }
   
   
   
   @Override
   public IAccountService getAccountService()
   {
      return accountService;
   }
   
   
   
   @Override
   public IAppContentEditService getContentEditService()
   {
      return appContentEditService;
   }
   
   
   
   @Override
   public IRtmService getRtmService()
   {
      return rtmService;
   }
   
   
   
   public void setRtmConnectionProtocol( RtmConnectionProtocol connectionProtocol )
   {
      this.rtmService = createRtmService( connectionProtocol );
   }
   
   
   
   private void checkForcedReadableAccess()
   {
      if ( context.getResources()
                  .getBoolean( R.bool.env_force_readable_rtm_access ) )
      {
         accountService.setForcedAccessLevel( RtmServicePermission.read );
      }
   }
   
   
   
   private RtmService createRtmService( RtmConnectionProtocol connectionProtocol )
   {
      final RtmService rtmService = new RtmService( log,
                                                    connectionService.getConnectionFactory(),
                                                    calendarProvider,
                                                    connectionProtocol,
                                                    context.getString( R.string.app_rtm_api_key ),
                                                    context.getString( R.string.app_rtm_shared_secret ) );
      return rtmService;
   }
   
   
   
   private RtmDatabase getDatabase()
   {
      final ContentProvider localContentProvider = context.getContentResolver()
                                                          .acquireContentProviderClient( ContentAuthority.RTM )
                                                          .getLocalContentProvider();
      
      if ( localContentProvider instanceof DbContentProvider )
      {
         return ( (DbContentProvider) localContentProvider ).getDatabase();
      }
      
      throw new RuntimeException( MessageFormat.format( "Expected local content provider to be of type {0} but was {1}",
                                                        DbContentProvider.class.getSimpleName(),
                                                        localContentProvider.getClass() ) );
   }
}
