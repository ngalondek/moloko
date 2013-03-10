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

package dev.drsoran.moloko.app.event;

import android.accounts.Account;
import dev.drsoran.moloko.IHandlerToken;


abstract class AbstractEventDrain implements IEventDrain
{
   private final IHandlerToken handlerToken;
   
   
   
   protected AbstractEventDrain( IHandlerToken handlerToken )
   {
      this.handlerToken = handlerToken;
   }
   
   
   
   @Override
   public void onSyncStatusChanged( final int what )
   {
      postIfNotReleased( new Runnable()
      {
         @Override
         public void run()
         {
            handleSyncStatusChanged( what );
         }
      } );
   }
   
   
   
   @Override
   public void onSettingChanged( final int what )
   {
      postIfNotReleased( new Runnable()
      {
         @Override
         public void run()
         {
            handleSettingChanged( what );
         }
      } );
   }
   
   
   
   @Override
   public void onAccountUpdated( final int what, final Account account )
   {
      postIfNotReleased( new Runnable()
      {
         @Override
         public void run()
         {
            handleAccountUpdated( what, account );
         }
      } );
   }
   
   
   
   protected abstract void handleSyncStatusChanged( int what );
   
   
   
   protected abstract void handleSettingChanged( int what );
   
   
   
   protected abstract void handleAccountUpdated( int what, Account account );
   
   
   
   private void postIfNotReleased( Runnable r )
   {
      if ( handlerToken != null && !handlerToken.isReleased() )
      {
         handlerToken.post( r );
      }
   }
}
