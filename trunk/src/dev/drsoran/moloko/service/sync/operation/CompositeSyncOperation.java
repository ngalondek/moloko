package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.SyncResult;


public class CompositeSyncOperation implements ISyncOperation
{
   private final ArrayList< ISyncOperation > operations = new ArrayList< ISyncOperation >();
   
   

   public CompositeSyncOperation()
   {
      
   }
   


   public CompositeSyncOperation( ArrayList< ISyncOperation > operations )
      throws NullPointerException
   {
      if ( operations == null )
         throw new NullPointerException();
      
      this.operations.addAll( operations );
   }
   


   public void add( ISyncOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( operation );
   }
   


   public boolean execute( SyncResult result )
   {
      boolean ok = true;
      
      for ( Iterator< ISyncOperation > i = operations.iterator(); ok
         && i.hasNext(); )
      {
         ok = i.next().execute( result );
      }
      
      return ok;
   }
   
}
