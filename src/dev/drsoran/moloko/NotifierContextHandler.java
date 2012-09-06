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

package dev.drsoran.moloko;

import android.accounts.Account;


public abstract class NotifierContextHandler
{
   private IHandlerToken handlerToken = MolokoApp.acquireHandlerToken();
   
   
   
   public void release()
   {
      handlerToken.release();
      handlerToken = null;
   }
   
   
   
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
   
   
   
   public void onSettingChanged( final int what, final Object oldValues )
   {
      postIfNotReleased( new Runnable()
      {
         @Override
         public void run()
         {
            handleSettingChanged( what, oldValues );
         }
      } );
   }
   
   
   
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
   
   
   
   protected abstract void handleTimeChanged( int what );
   
   
   
   protected abstract void handleNetworkStatusChanged( int what,
                                                       boolean connected );
   
   
   
   protected abstract void handleSyncStatusChanged( int what );
   
   
   
   protected abstract void handleSettingChanged( int what, Object oldValues );
   
   
   
   protected abstract void handleAccountUpdated( int what, Account account );
   
   
   
   private void postIfNotReleased( Runnable r )
   {
      if ( handlerToken != null && !handlerToken.isReleased() )
      {
         handlerToken.post( r );
      }
   }
}
