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

package dev.drsoran.moloko.event;

import dev.drsoran.moloko.IHandlerToken;


abstract class AbstractEventDrain implements IEventDrain
{
   private final IHandlerToken handlerToken;
   
   
   
   protected AbstractEventDrain( IHandlerToken handlerToken )
   {
      this.handlerToken = handlerToken;
   }
   
   
   
   @Override
   public void onTimeChanged( final int what )
   {
      postIfNotReleased( new Runnable()
      {
         @Override
         public void run()
         {
            handleTimeChanged( what );
         }
      } );
   }
   
   
   
   @Override
   public void onNetworkStatusChanged( final int what, final boolean connected )
   {
      postIfNotReleased( new Runnable()
      {
         @Override
         public void run()
         {
            handleNetworkStatusChanged( what, connected );
         }
      } );
   }
   
   
   
   protected abstract void handleTimeChanged( int what );
   
   
   
   protected abstract void handleNetworkStatusChanged( int what,
                                                       boolean connected );
   
   
   
   private void postIfNotReleased( Runnable r )
   {
      if ( handlerToken != null && !handlerToken.isReleased() )
      {
         handlerToken.post( r );
      }
   }
}
