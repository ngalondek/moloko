/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.service.sync.lists;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import dev.drsoran.moloko.service.sync.util.PropertyAdapter;


public abstract class SyncableList< O, T >
{
   private static final long serialVersionUID = 5137594788305624736L;
   
   private final ArrayList< T > impl;
   
   private final Comparator< T > comparator;
   
   private final boolean[] touchedElements;
   
   

   protected SyncableList( Collection< T > elements )
      throws NullPointerException
   {
      if ( elements == null )
         throw new NullPointerException();
      
      this.impl = new ArrayList< T >( elements.size() );
      this.impl.addAll( elements );
      this.comparator = null;
      this.touchedElements = new boolean[ elements.size() ];
   }
   


   protected SyncableList( Collection< T > elements, Comparator< T > comparator )
      throws NullPointerException
   {
      if ( elements == null )
         throw new NullPointerException();
      
      this.impl = new ArrayList< T >( elements.size() );
      this.impl.addAll( elements );
      this.comparator = comparator;
      this.touchedElements = new boolean[ elements.size() ];
      
      if ( comparator != null )
         Collections.sort( this.impl, this.comparator );
   }
   


   public void clearSyncState()
   {
      for ( int i = 0; i < touchedElements.length; i++ )
      {
         touchedElements[ i ] = false;
      }
   }
   


   public ArrayList< T > getUntouchedElements()
   {
      ArrayList< T > result = new ArrayList< T >();
      
      for ( int i = 0; i < touchedElements.length; i++ )
      {
         if ( !touchedElements[ i ] )
         {
            result.add( impl.get( i ) );
         }
      }
      
      return result;
   }
   


   public int find( T element ) throws NullPointerException
   {
      if ( element == null )
         throw new NullPointerException();
      
      int pos = -1;
      
      if ( comparator != null )
      {
         pos = Collections.binarySearch( impl, element, comparator );
         
         // We only want found or not and not the insert position.
         if ( pos < 0 )
            pos = -1;
      }
      else
      {
         for ( int i = 0; i < impl.size() && pos == -1; i++ )
         {
            if ( impl.get( i ).equals( element ) )
               pos = i;
         }
      }
      
      return pos;
   }
   


   public < V > int find( V other, PropertyAdapter< T, V > adapter ) throws NullPointerException,
                                                                    InvocationTargetException
   {
      if ( adapter == null )
         throw new NullPointerException();
      
      int pos = -1;
      
      for ( int i = 0; i < impl.size() && pos == -1; i++ )
      {
         if ( adapter.equals( impl.get( i ), other ) )
            pos = i;
      }
      
      return pos;
   }
   


   public T get( int pos )
   {
      return impl.get( pos );
   }
   


   public boolean isEmpty()
   {
      return impl.isEmpty();
   }
   


   public Iterator< T > iterator()
   {
      return impl.iterator();
   }
   


   public int size()
   {
      return impl.size();
   }
   


   public Object[] toArray()
   {
      return impl.toArray();
   }
   


   public abstract O computeInsertOperation( T newElement, Object... params );
   


   public O computeUpdateOperation( int pos, T updateElement, Object... params )
   {
      touchedElements[ pos ] = true;
      
      return internalComputeUpdateOperation( impl.get( pos ),
                                             updateElement,
                                             params );
   }
   


   public abstract O computeDeleteOperation( T elementToDelete,
                                             Object... params );
   


   protected abstract O internalComputeUpdateOperation( T old,
                                                        T updateElement,
                                                        Object... params );
}
