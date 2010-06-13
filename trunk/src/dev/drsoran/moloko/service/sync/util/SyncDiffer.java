package dev.drsoran.moloko.service.sync.util;

import java.util.ArrayList;
import java.util.List;


public class SyncDiffer< L1, L2 >
{
   private final ArrayList< L1 > list1;
   
   private final ArrayList< L2 > list2;
   
   

   public SyncDiffer( List< L1 > list1, List< L2 > list2 )
      throws IllegalArgumentException
   {
      if ( list1 == null )
         throw new IllegalArgumentException( "list1 must not be null" );
      
      if ( list2 == null )
         throw new IllegalArgumentException( "list2 must not be null" );
      
      this.list1 = new ArrayList< L1 >( list1.size() );
      this.list2 = new ArrayList< L2 >( list2.size() );
      
      list1.addAll( list1 );
      list2.addAll( list2 );
   }
   
   
   
}
