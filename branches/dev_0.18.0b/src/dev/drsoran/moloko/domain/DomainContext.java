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

package dev.drsoran.moloko.domain;

import android.content.Context;
import android.content.ContextWrapper;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.domain.services.IDomainServices;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.event.ISystemEventService;


public final class DomainContext extends ContextWrapper
{
   private final SystemContext systemContext;
   
   private final IDomainServices domainServices;
   
   
   
   public DomainContext( SystemContext base, IDomainServices domainServices )
   {
      super( base );
      
      this.systemContext = base;
      this.domainServices = domainServices;
   }
   
   
   
   public static DomainContext get( Context context )
   {
      return MolokoApp.getDomainContext( context );
   }
   
   
   
   public SystemContext asSystemContext()
   {
      return systemContext;
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
      return domainServices.getParsingService();
   }
}
