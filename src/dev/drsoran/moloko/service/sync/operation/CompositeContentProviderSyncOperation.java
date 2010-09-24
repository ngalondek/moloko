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
import java.util.Iterator;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.RemoteException;


public class CompositeContentProviderSyncOperation extends
         AbstractContentProviderSyncOperation
{
   private final ArrayList< IContentProviderSyncOperation > operations;
   
   

   public CompositeContentProviderSyncOperation(
      ContentProviderClient provider, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< IContentProviderSyncOperation >();
   }
   


   public CompositeContentProviderSyncOperation(
      ContentProviderClient provider,
      ArrayList< IContentProviderSyncOperation > operations, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< IContentProviderSyncOperation >( operations );
   }
   


   public void add( IContentProviderSyncOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( operation );
   }
   


   public void addAll( ArrayList< IContentProviderSyncOperation > operations ) throws NullPointerException
   {
      if ( operations == null )
         throw new NullPointerException();
      
      this.operations.addAll( operations );
   }
   


   public void add( ContentProviderOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( new ContentProviderSyncOperation( provider,
                                                        operation,
                                                        operationType ) );
   }
   


   public boolean execute( SyncResult result )
   {
      final ArrayList< ContentProviderOperation > batch = new ArrayList< ContentProviderOperation >();
      
      getBatch( batch );
      
      boolean ok = true;
      
      try
      {
         provider.applyBatch( batch );
         updateSyncResult( result, operationType, batch.size() );
      }
      catch ( RemoteException e )
      {
         ++result.stats.numIoExceptions;
         ok = false;
      }
      catch ( OperationApplicationException e )
      {
         result.databaseError = true;
         ok = false;
      }
      
      return ok;
   }
   


   public int getBatch( ArrayList< ContentProviderOperation > batch )
   {
      int num = 0;
      
      for ( Iterator< IContentProviderSyncOperation > i = operations.iterator(); i.hasNext(); )
      {
         final IContentProviderSyncOperation operation = i.next();
         num += operation.getBatch( batch );
      }
      
      return num;
   }
}
