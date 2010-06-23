package dev.drsoran.moloko.util;

import java.util.HashMap;


public final class ContentUiMapper
{
   public final String[] COLUMNS;
   
   public final HashMap< String, Integer > COL_INDICES;
   
   public final int[] RESSOURCE_IDS;
   
   

   public ContentUiMapper( String[] cols, int[] resIds )
      throws IllegalArgumentException
   {
      if ( cols.length != resIds.length )
         throw new IllegalAccessError( "cols and resIds must have the same length" );
      
      COLUMNS = cols;
      RESSOURCE_IDS = resIds;
      COL_INDICES = new HashMap< String, Integer >();
      
      for ( int i = 0; i < cols.length; i++ )
      {
         // +1 cause if query from a ContentProvider the first
         // projection has to be _ID. We do not include this
         // into UI but have to add 1.
         COL_INDICES.put( cols[ i ], i + 1 );
      }
   }
}
