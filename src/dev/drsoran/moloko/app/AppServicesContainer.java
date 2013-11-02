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

package dev.drsoran.moloko.app;

import android.content.Context;
import android.os.Handler;

import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.IHandlerTokenFactory;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.account.AccountService;
import dev.drsoran.moloko.app.event.AppEventService;
import dev.drsoran.moloko.app.services.AppContentEditService;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.IAppContentEditService;
import dev.drsoran.moloko.app.services.IAppEventService;
import dev.drsoran.moloko.app.services.IAppServices;
import dev.drsoran.moloko.app.services.IConnectionService;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.app.services.ISyncService;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.app.sync.SyncService;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.sync.connection.DefaultRtmConnectionFactory;
import dev.drsoran.moloko.sync.connection.IRtmConnectionFactory;


public class AppServicesContainer implements IAppServices
{
   private final AppEventService appEventService;
   
   private final Settings settingsService;
   
   private final IConnectionService connectionService;
   
   private final IAccountService accountService;
   
   private final SyncService syncService;
   
   private final IAppContentEditService appContentEditService;
   
   
   
   public AppServicesContainer( DomainContext context, Handler handler,
      IHandlerTokenFactory handlerTokenFactory, ILog log )
   {
      this.appEventService = new AppEventService( context,
                                                  handler,
                                                  log,
                                                  handlerTokenFactory );
      
      this.settingsService = new Settings( context, appEventService );
      
      final IRtmConnectionFactory rtmConnectionFactory = new DefaultRtmConnectionFactory( log,
                                                                                          getHttpUserAgent() );
      this.connectionService = new ConnectionService( context,
                                                      rtmConnectionFactory );
      
      this.accountService = new AccountService( context );
      
      this.syncService = new SyncService( context,
                                          settingsService,
                                          connectionService,
                                          appEventService,
                                          accountService,
                                          log );
      
      this.appContentEditService = new AppContentEditService( context.getContentEditService(),
                                                              accountService );
      
      checkForcedReadableAccess( context );
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
   public IConnectionService getConnectionService()
   {
      return connectionService;
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
   
   
   
   private void checkForcedReadableAccess( Context context )
   {
      if ( context.getResources()
                  .getBoolean( R.bool.env_force_readable_rtm_access ) )
      {
         accountService.setForcedAccessLevel( Perms.read );
      }
   }
   
   
   
   private String getHttpUserAgent()
   {
      // TODO: Make HTTP user agent a resource
      return "Mozilla/5.0 (Linux; U; Android 1.6; de-ch; HTC Magic Build/DRC92) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1";
   }
}
