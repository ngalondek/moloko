package dev.drsoran.moloko.service.sync.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.content.SyncResult;

import dev.drsoran.moloko.service.sync.lists.SyncableList;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public class SyncDiffer
{
   public static < T > ArrayList< ISyncOperation > diff( Collection< T > reference,
                                                         SyncableList< T > target,
                                                         SyncResult syncResult ) throws NullPointerException
   {
      if ( reference == null || target == null )
         throw new NullPointerException();
      
      ArrayList< ISyncOperation > operations = new ArrayList< ISyncOperation >();
      
      // for each element of the reference list
      for ( Iterator< T > iterator = reference.iterator(); iterator.hasNext(); )
      {
         final T refElement = iterator.next();
         
         final int pos = target.find( refElement );
         
         // INSERT: The reference element is not contained in the target list.
         if ( pos == -1 )
         {
            operations.add( target.computeInsertOperation( refElement ) );
            
            if ( syncResult != null )
               ++syncResult.stats.numInserts;
         }
         
         // UPDATE: The reference element is contained in the target list.
         else
         {
            operations.add( target.computeUpdateOperation( pos, refElement ) );
            
            if ( syncResult != null )
               ++syncResult.stats.numUpdates;
         }
         
         // DELETE: Get all elements which have not been touched during the diff.
         // These elements are no in the reference list.
         final ArrayList< T > untouchedElements = target.getUntouchedElements();
         
         for ( T tgtElement : untouchedElements )
         {
            operations.add( target.computeDeleteOperation( tgtElement ) );
            
            if ( syncResult != null )
               ++syncResult.stats.numDeletes;
         }
      }
      
      return operations;
   }
}
