/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentProviderOperation;

import com.mdt.rtm.TimeLineMethod;

import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;


public final class DirectedSyncOperations
{
   private final List< IServerSyncOperation< ? > > serverOps = new ArrayList< IServerSyncOperation< ? > >();
   
   private final List< IContentProviderSyncOperation > localOps = new ArrayList< IContentProviderSyncOperation >();
   
   

   public boolean addAll( DirectedSyncOperations other )
   {
      return serverOps.addAll( other.serverOps )
         || localOps.addAll( other.localOps );
   }
   


   public < T extends IServerSyncable< T > > boolean add( IServerSyncOperation< T > op )
   {
      if ( op == null )
         throw new NullPointerException( "op is null" );
      
      if ( !( op instanceof INoopSyncOperation ) )
         return serverOps.add( op );
      
      return false;
   }
   


   public < T extends IServerSyncable< T > > boolean add( TimeLineMethod< T > op,
                                                          ISyncOperation.Op type,
                                                          T sourceElement )
   {
      if ( op == null )
         throw new NullPointerException( "op is null" );
      
      return serverOps.add( ServerSyncOperation.fromType( type, sourceElement )
                                               .add( op )
                                               .build() );
   }
   


   public boolean addAllServerOps( Collection< IServerSyncOperation< ? > > operations )
   {
      if ( operations == null )
         throw new NullPointerException( "operations is null" );
      
      return serverOps.addAll( operations );
   }
   


   public boolean add( IContentProviderSyncOperation op )
   {
      if ( op == null )
         throw new NullPointerException( "op is null" );
      
      if ( !( op instanceof INoopSyncOperation ) )
         return localOps.add( op );
      
      return false;
   }
   


   public boolean add( ContentProviderOperation op, ISyncOperation.Op type )
   {
      if ( op == null )
         throw new NullPointerException( "op is null" );
      
      return localOps.add( ContentProviderSyncOperation.fromType( type )
                                                       .add( op )
                                                       .build() );
   }
   


   public boolean addAllLocalOps( Collection< IContentProviderSyncOperation > operations )
   {
      if ( operations == null )
         throw new NullPointerException( "operations is null" );
      
      return localOps.addAll( operations );
   }
   


   public List< IServerSyncOperation< ? > > getServerOperations()
   {
      return serverOps;
   }
   


   public List< IContentProviderSyncOperation > getLocalOperations()
   {
      return localOps;
   }
}
