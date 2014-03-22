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

package dev.drsoran.moloko.ui;

import android.content.Context;
import android.content.ContextWrapper;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.event.ISystemEventService;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.ui.services.ISmartAddService;
import dev.drsoran.moloko.ui.services.IUiServices;
import dev.drsoran.rtm.ILog;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;


public final class UiContext extends ContextWrapper
{
   private final SystemContext systemContext;
   
   private final DomainContext domainContext;
   
   private final IUiServices uiServices;
   
   
   
   public UiContext( DomainContext base, IUiServices uiServices )
   {
      super( base );
      
      this.domainContext = base;
      this.systemContext = base.asSystemContext();
      this.uiServices = uiServices;
   }
   
   
   
   public static UiContext get( Context context )
   {
      return MolokoApp.getUiContext( context );
   }
   
   
   
   public SystemContext asSystemContext()
   {
      return systemContext;
   }
   
   
   
   public DomainContext asDomainContext()
   {
      return domainContext;
   }
   
   
   
   public ILog Log()
   {
      return systemContext.Log();
   }
   
   
   
   public ISystemEventService getSystemEvents()
   {
      return systemContext.getSystemEvents();
   }
   
   
   
   public IHandlerToken acquireHandlerToken()
   {
      return systemContext.acquireHandlerToken();
   }
   
   
   
   public IParsingService getParsingService()
   {
      return domainContext.getParsingService();
   }
   
   
   
   public IDateFormatterService getDateFormatter()
   {
      return uiServices.getDateFormatter();
   }
   
   
   
   public ISmartAddService getSmartAddService()
   {
      return uiServices.getSmartAddService();
   }
   
   
   
   public IRtmCalendarProvider getCalendarProvider()
   {
      return domainContext.getCalendarProvider();
   }
}
