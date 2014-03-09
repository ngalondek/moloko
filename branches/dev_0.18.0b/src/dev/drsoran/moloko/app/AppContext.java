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
import android.content.ContextWrapper;
import dev.drsoran.moloko.IConnectionService;
import dev.drsoran.moloko.IExecutorService;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.IAppContentEditService;
import dev.drsoran.moloko.app.services.IAppEventService;
import dev.drsoran.moloko.app.services.IAppServices;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.app.services.ISyncService;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.event.ISystemEventService;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.service.IRtmAuthenticationService;
import dev.drsoran.rtm.service.IRtmSyncService;
import dev.drsoran.rtm.sync.IRtmSyncPartner;


public final class AppContext extends ContextWrapper
{
   private final SystemContext systemContext;
   
   private final DomainContext domainContext;
   
   private final UiContext uiContext;
   
   private final IAppServices appServices;
   
   
   
   public AppContext( UiContext base, IAppServices appServices )
   {
      super( base );
      
      this.systemContext = base.asSystemContext();
      this.domainContext = base.asDomainContext();
      this.uiContext = base;
      this.appServices = appServices;
   }
   
   
   
   public static AppContext get( Context context )
   {
      return MolokoApp.getAppContext( context );
   }
   
   
   
   public SystemContext asSystemContext()
   {
      return systemContext;
   }
   
   
   
   public DomainContext asDomainContext()
   {
      return domainContext;
   }
   
   
   
   public UiContext asUiContext()
   {
      return uiContext;
   }
   
   
   
   public IExecutorService getExecutorService()
   {
      return systemContext.getExecutorService();
   }
   
   
   
   public ILog Log()
   {
      return systemContext.Log();
   }
   
   
   
   public ISettingsService getSettings()
   {
      return appServices.getSettings();
   }
   
   
   
   public ISystemEventService getSystemEvents()
   {
      return systemContext.getSystemEvents();
   }
   
   
   
   public IAppEventService getAppEvents()
   {
      return appServices.getAppEvents();
   }
   
   
   
   public IHandlerToken acquireHandlerToken()
   {
      return systemContext.acquireHandlerToken();
   }
   
   
   
   public IConnectionService getConnectionService()
   {
      return systemContext.getConnectionService();
   }
   
   
   
   public ISyncService getSyncService()
   {
      return appServices.getSyncService();
   }
   
   
   
   public IRtmSyncService getRtmSyncService( IRtmSyncPartner syncPartner,
                                             String authToken )
   {
      return appServices.getRtmService()
                        .getSyncService( syncPartner, authToken );
   }
   
   
   
   public IAccountService getAccountService()
   {
      return appServices.getAccountService();
   }
   
   
   
   public IRtmAuthenticationService getRtmAuthService()
   {
      return appServices.getRtmService().getAuthenticationService();
   }
   
   
   
   public IDateFormatterService getDateFormatter()
   {
      return uiContext.getDateFormatter();
   }
   
   
   
   public IParsingService getParsingService()
   {
      return domainContext.getParsingService();
   }
   
   
   
   public IContentRepository getContentRepository()
   {
      return domainContext.getContentRepository();
   }
   
   
   
   public IAppContentEditService getContentEditService()
   {
      return appServices.getContentEditService();
   }
   
   
   
   public IRtmCalendarProvider getCalendarProvider()
   {
      return domainContext.getCalendarProvider();
   }
}
