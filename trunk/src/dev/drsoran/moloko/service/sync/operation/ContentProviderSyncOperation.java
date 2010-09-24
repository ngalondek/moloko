/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.RemoteException;


public class ContentProviderSyncOperation extends AbstractContentProviderSyncOperation
{
   private final ArrayList< ContentProviderOperation > operations;
   
   

   public ContentProviderSyncOperation( ContentProviderClient provider,
      int operationType ) throws NullPointerException
   {
      super( provider, operationType );
      
      if ( provider == null )
         throw new NullPointerException();
      
      this.operations = new ArrayList< ContentProviderOperation >();
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      ContentProviderOperation operation, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< ContentProviderOperation >( 1 );
      this.operations.add( operation );
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      Collection< ContentProviderOperation > operations, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< ContentProviderOperation >( operations );
   }
   


   public void add( ContentProviderOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      this.operations.add( operation );
   }
   


   public void addAll( Collection< ContentProviderOperation > operations ) throws NullPointerException
   {
      if ( operations == null )
         throw new NullPointerException();
      
      this.operations.addAll( operations );
   }
   


   public boolean execute( SyncResult result )
   {
      boolean ok = true;
      
      try
      {
         provider.applyBatch( operations );
         updateSyncResult( result, operationType, operations.isEmpty() ? 0 : 1 );
      }
      catch ( OperationApplicationException e )
      {
         ++result.stats.numIoExceptions;
         ok = false;
      }
      catch ( RemoteException e )
      {
         ++result.stats.numIoExceptions;
         ok = false;
      }
      
      return ok;
   }
   


   public int getBatch( ArrayList< ContentProviderOperation > batch )
   {
      batch.addAll( operations );
      return operations.size();
   }
   


   public int getOperationType()
   {
      return operationType;
   }
}
