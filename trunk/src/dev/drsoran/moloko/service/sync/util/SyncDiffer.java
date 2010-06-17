package dev.drsoran.moloko.service.sync.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import dev.drsoran.moloko.service.sync.lists.SyncableList;


public class SyncDiffer
{
   public static < O, T > ArrayList< O > diff( Collection< T > reference,
                                               SyncableList< O, T > target,
                                               Object... params ) throws NullPointerException
   {
      if ( reference == null || target == null )
         throw new NullPointerException();
      
      boolean ok = true;
      
      ArrayList< O > operations = new ArrayList< O >();
      
      // for each element of the reference list
      for ( Iterator< T > iterator = reference.iterator(); ok && iterator.hasNext(); )
      {
         final T refElement = iterator.next();
         
         final int pos = target.find( refElement );
         
         O operation = null;
         
         // INSERT: The reference element is not contained in the target list.
         if ( pos == -1 )
         {
            operation = target.computeInsertOperation( refElement, params );
         }
         
         // UPDATE: The reference element is contained in the target list.
         else
         {
            operation = target.computeUpdateOperation( pos, refElement, params );
         }
         
         ok = operation != null;
         operations.add( operation );
      }
      
      // DELETE: Get all elements which have not been touched during the diff.
      // These elements are no in the reference list.
      final ArrayList< T > untouchedElements = target.getUntouchedElements();
      
      for ( T tgtElement : untouchedElements )
      {
         final O operation = target.computeDeleteOperation( tgtElement, params );
         
         ok = operation != null;
         operations.add( operation );
      }
      
      if ( !ok )
         operations = null;
      
      return operations;
   }
}
