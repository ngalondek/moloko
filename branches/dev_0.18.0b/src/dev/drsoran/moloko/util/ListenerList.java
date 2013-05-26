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

package dev.drsoran.moloko.util;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ListenerList< T >
{
   private final Method method;
   
   
   private final class ListenerEntry
   {
      public final int mask;
      
      public final WeakReference< T > listener;
      
      
      
      public ListenerEntry( int mask, T listener )
      {
         this.mask = mask;
         this.listener = new WeakReference< T >( listener );
      }
      
      
      
      public boolean isDead()
      {
         return listener.get() == null;
      }
      
      
      
      public boolean matches( int setting )
      {
         return ( ( mask & setting ) != 0 );
      }
      
      
      
      public void notify( int mask )
      {
         T listenerStrong = listener.get();
         
         if ( listenerStrong != null )
         {
            try
            {
               method.invoke( listenerStrong, mask );
            }
            catch ( IllegalArgumentException e )
            {
               throw e;
            }
            catch ( IllegalAccessException e )
            {
               throw new RuntimeException( e );
            }
            catch ( InvocationTargetException e )
            {
               throw new RuntimeException( e );
            }
         }
      }
      
      
      
      public void notify( int mask, Object value )
      {
         T listenerStrong = listener.get();
         
         if ( listenerStrong != null )
         {
            try
            {
               method.invoke( listenerStrong, mask, value );
            }
            catch ( IllegalArgumentException e )
            {
               throw e;
            }
            catch ( IllegalAccessException e )
            {
               throw new RuntimeException( e );
            }
            catch ( InvocationTargetException e )
            {
               throw new RuntimeException( e );
            }
         }
      }
   }
   
   // TODO: No check for double registration, no check for registration of same
   // listener
   // with different mask. In these cases the listener gets notified multiple
   // times.
   private final List< ListenerEntry > listeners = new CopyOnWriteArrayList< ListenerEntry >();
   
   
   
   public ListenerList( Method method )
   {
      if ( method == null )
      {
         throw new IllegalArgumentException( "method" );
      }
      
      this.method = method;
   }
   
   
   
   public void registerListener( int which, T listener )
   {
      if ( listener == null )
      {
         throw new IllegalArgumentException( "listener" );
      }
      
      listeners.add( new ListenerEntry( which, listener ) );
   }
   
   
   
   public void unregisterListener( T listener )
   {
      if ( listener == null )
      {
         throw new IllegalArgumentException( "listener" );
      }
      
      removeListener( listener );
   }
   
   
   
   public int size()
   {
      return listeners.size();
   }
   
   
   
   public void clear()
   {
      listeners.clear();
   }
   
   
   
   public void notifyListeners( int mask )
   {
      for ( int i = listeners.size() - 1; i >= 0; --i )
      {
         final ListenerEntry entry = listeners.get( i );
         
         if ( entry.isDead() )
         {
            listeners.remove( i );
         }
         else if ( entry.matches( mask ) )
         {
            entry.notify( mask );
         }
      }
   }
   
   
   
   public void notifyListeners( int mask, Object value )
   {
      for ( int i = listeners.size() - 1; i >= 0; --i )
      {
         final ListenerEntry entry = listeners.get( i );
         
         if ( entry.isDead() )
         {
            listeners.remove( i );
         }
         else if ( entry.matches( mask ) )
         {
            entry.notify( mask, value );
         }
      }
   }
   
   
   
   private void removeListener( T listener )
   {
      ListenerEntry entryToRemove = null;
      
      for ( Iterator< ListenerEntry > i = listeners.iterator(); i.hasNext()
         && entryToRemove == null; )
      {
         final ListenerEntry entry = i.next();
         
         if ( listener == entry.listener.get() )
         {
            entryToRemove = entry;
         }
      }
      
      if ( entryToRemove != null )
      {
         listeners.remove( entryToRemove );
      }
   }
}
