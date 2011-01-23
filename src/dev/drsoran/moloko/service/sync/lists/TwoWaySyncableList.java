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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentProviderClient;
import android.util.Pair;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;


public class TwoWaySyncableList< T extends ITwoWaySyncable< T > >
{
   private final ArrayList< ? extends ITwoWaySyncable< T > > serverElements;
   
   private final ArrayList< ? extends ITwoWaySyncable< T > > localElements;
   
   private final Comparator< ITwoWaySyncable< T > > comparator;
   
   private final boolean[] serverTouchedElements;
   
   private final boolean[] localTouchedElements;
   
   private final Service service;
   
   private final ContentProviderClient provider;
   
   

   public TwoWaySyncableList(
      List< ? extends ITwoWaySyncable< T > > serverElements,
      List< ? extends ITwoWaySyncable< T > > localElements,
      Comparator< ITwoWaySyncable< T >> comparator, Service service,
      ContentProviderClient provider )
   {
      if ( serverElements == null )
         throw new NullPointerException( "serverElements is null" );
      if ( localElements == null )
         throw new NullPointerException( "localElements is null" );
      if ( comparator == null )
         throw new NullPointerException( "comparator is null" );
      if ( service == null )
         throw new NullPointerException( "service is null" );
      if ( provider == null )
         throw new NullPointerException( "provider is null" );
      
      this.serverElements = new ArrayList< ITwoWaySyncable< T > >( serverElements );
      this.localElements = new ArrayList< ITwoWaySyncable< T > >( localElements );
      this.comparator = comparator;
      this.serverTouchedElements = new boolean[ serverElements.size() ];
      this.localTouchedElements = new boolean[ localElements.size() ];
      this.service = service;
      this.provider = provider;
      
      Collections.sort( this.serverElements, this.comparator );
      Collections.sort( this.localElements, this.comparator );
   }
   


   public List< ? extends ITwoWaySyncable< T > > getUntouchedServerElements()
   {
      final LinkedList< ITwoWaySyncable< T > > result = new LinkedList< ITwoWaySyncable< T > >();
      
      for ( int i = 0; i < serverTouchedElements.length; i++ )
      {
         if ( !serverTouchedElements[ i ] )
         {
            result.add( serverElements.get( i ) );
         }
      }
      
      return result;
   }
   


   public List< ? extends ITwoWaySyncable< T > > getUntouchedLocalElements()
   {
      final LinkedList< ITwoWaySyncable< T > > result = new LinkedList< ITwoWaySyncable< T > >();
      
      for ( int i = 0; i < localTouchedElements.length; i++ )
      {
         if ( !localTouchedElements[ i ] )
         {
            result.add( localElements.get( i ) );
         }
      }
      
      return result;
   }
   


   public int findServer( ITwoWaySyncable< T > element )
   {
      if ( element == null )
         throw new NullPointerException( "element is null" );
      
      int pos = Collections.binarySearch( serverElements, element, comparator );
      
      // We only want found or not and not the insert position.
      if ( pos < 0 )
         pos = -1;
      
      return pos;
   }
   


   public int findLocal( ITwoWaySyncable< T > element )
   {
      if ( element == null )
         throw new NullPointerException( "element is null" );
      
      int pos = Collections.binarySearch( localElements, element, comparator );
      
      // We only want found or not and not the insert position.
      if ( pos < 0 )
         pos = -1;
      
      return pos;
   }
   


   public ISyncOperation computeServerInsertOperation( ITwoWaySyncable< T > newElement,
                                                       Object... params )
   {
      return newElement.computeServerInsertOperation( service, params );
   }
   


   public ISyncOperation computeServerDeleteOperation( ITwoWaySyncable< T > elementToDelete,
                                                       Object... params )
   {
      return elementToDelete.computeServerDeleteOperation( service, params );
   }
   


   public ISyncOperation computeLocalInsertOperation( ITwoWaySyncable< T > newElement,
                                                      Object... params )
   {
      return newElement.computeContentProviderInsertOperation( provider, params );
   }
   


   public ISyncOperation computeLocalDeleteOperation( ITwoWaySyncable< T > elementToDelete,
                                                      Object... params )
   {
      return elementToDelete.computeContentProviderDeleteOperation( provider,
                                                                    params );
   }
   


   public Pair< ISyncOperation, ISyncOperation > computeMergeOperations( int pos,
                                                                         T updateElement,
                                                                         Object... params )
   {
      serverTouchedElements[ pos ] = true;
      
      return internalComputeUpdateOperation( impl.get( pos ),
                                             updateElement,
                                             params );
   }
   
}
