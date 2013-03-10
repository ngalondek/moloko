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

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;


public class TokenBasedHandler extends Handler implements IHandlerTokenFactory
{
   public TokenBasedHandler()
   {
      super();
   }
   
   
   
   public TokenBasedHandler( Callback callback )
   {
      super( callback );
   }
   
   
   
   @Override
   public IHandlerToken acquireToken()
   {
      return new HandlerToken();
   }
   
   
   
   public void releaseToken( IHandlerToken token )
   {
      if ( token instanceof HandlerToken )
      {
         removeCallbacksAndMessages( ( (HandlerToken) token ).getTokenObject() );
      }
      else
      {
         throw new IllegalArgumentException( "token is not created from this Handler." );
      }
   }
   
   
   private final class HandlerToken implements IHandlerToken
   {
      private final Object token = new Object();
      
      private boolean released;
      
      
      
      public Object getTokenObject()
      {
         return token;
      }
      
      
      
      @Override
      public boolean post( Runnable r )
      {
         return postAtTime( r, SystemClock.uptimeMillis() );
      }
      
      
      
      @Override
      public boolean postAtTime( Runnable r, long uptimeMillis )
      {
         return TokenBasedHandler.this.postAtTime( r, token, uptimeMillis );
      }
      
      
      
      @Override
      public boolean postDelayed( Runnable r, long delayMillis )
      {
         if ( delayMillis < 0 )
         {
            delayMillis = 0;
         }
         
         return postAtTime( r, SystemClock.uptimeMillis() + delayMillis );
      }
      
      
      
      @Override
      public void removeRunnable( Runnable r )
      {
         TokenBasedHandler.this.removeCallbacks( r, token );
      }
      
      
      
      @Override
      public void sendEmptyMessage( int what )
      {
         final Message message = Message.obtain( TokenBasedHandler.this,
                                                 what,
                                                 token );
         TokenBasedHandler.this.sendMessage( message );
      }
      
      
      
      @Override
      public void removeMessages( int what )
      {
         TokenBasedHandler.this.removeMessages( what, token );
      }
      
      
      
      @Override
      public void removeRunnablesAndMessages()
      {
         TokenBasedHandler.this.removeCallbacksAndMessages( token );
      }
      
      
      
      @Override
      public boolean isReleased()
      {
         return released;
      }
      
      
      
      @Override
      public void release()
      {
         if ( released )
         {
            throw new IllegalStateException( "Token is already released." );
         }
         
         removeRunnablesAndMessages();
         released = true;
      }
   }
}
