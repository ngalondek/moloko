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

import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;


public final class TypedDirectedSyncOperations< T extends IServerSyncable< T >>
{
   private final ServerSyncOperation.Builder< T > serverOperationBuilder;
   
   private final ContentProviderSyncOperation.Builder localOperationBuilder;
   
   

   public TypedDirectedSyncOperations( ISyncOperation.Op type )
   {
      serverOperationBuilder = new ServerSyncOperation.Builder< T >( type );
      localOperationBuilder = new ContentProviderSyncOperation.Builder( type );
   }
   


   public TypedDirectedSyncOperations(
      ServerSyncOperation.Builder< T > serverBuilder,
      ContentProviderSyncOperation.Builder localBuilder )
   {
      serverOperationBuilder = new ServerSyncOperation.Builder< T >( serverBuilder );
      localOperationBuilder = new ContentProviderSyncOperation.Builder( localBuilder );
   }
   


   public TypedDirectedSyncOperations( TypedDirectedSyncOperations< T > other )
   {
      serverOperationBuilder = new ServerSyncOperation.Builder< T >( other.serverOperationBuilder );
      localOperationBuilder = new ContentProviderSyncOperation.Builder( other.localOperationBuilder );
   }
   


   public ServerSyncOperation.Builder< T > getServerBuilder()
   {
      return serverOperationBuilder;
   }
   


   public ContentProviderSyncOperation.Builder getLocalBuilder()
   {
      return localOperationBuilder;
   }
   


   public IServerSyncOperation< T > getServerOperation()
   {
      return serverOperationBuilder.build();
   }
   


   public IContentProviderSyncOperation getLocalOperation()
   {
      return localOperationBuilder.build();
   }
}
