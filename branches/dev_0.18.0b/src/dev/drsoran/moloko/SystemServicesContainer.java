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

package dev.drsoran.moloko;

import android.content.Context;
import android.os.Handler;
import dev.drsoran.moloko.event.ISystemEventService;
import dev.drsoran.moloko.event.MolokoSystemEventService;


class SystemServicesContainer implements ISystemServices
{
   private final static TokenBasedHandler Handler = new TokenBasedHandler();
   
   private final IExecutorService executorService = new MolokoExecutorService();
   
   private final ILog log;
   
   private final MolokoSystemEventService eventService;
   
   
   
   public SystemServicesContainer( Context context )
   {
      this.log = new AndroidLogger( context );
      this.eventService = new MolokoSystemEventService( context, log, Handler );
   }
   
   
   
   public void shutdown()
   {
      eventService.shutdown();
      Handler.removeCallbacksAndMessages( null );
   }
   
   
   
   @Override
   public ILog Log()
   {
      return log;
   }
   
   
   
   @Override
   public Handler getHandler()
   {
      return Handler;
   }
   
   
   
   @Override
   public IHandlerTokenFactory getHandlerTokenFactory()
   {
      return Handler;
   }
   
   
   
   @Override
   public IExecutorService getExecutorService()
   {
      return executorService;
   }
   
   
   
   @Override
   public ISystemEventService getSystemEvents()
   {
      return eventService;
   }
   
   
   
   @Override
   public IHandlerToken acquireHandlerToken()
   {
      return Handler.acquireToken();
   }
}
