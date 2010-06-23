package dev.drsoran.moloko.util;

import java.util.HashMap;


public final class ContentUiMapper
{
   public final String[] PROJECTION;
   
   public final String[] UI_COLUMNS;
   
   public final HashMap< String, Integer > UI_COL_INDICES;
   
   public final int[] RESSOURCE_IDS;
   
   

   public ContentUiMapper( String[] projection, int[] resIds )
   {
      PROJECTION = projection;
      RESSOURCE_IDS = resIds;
      UI_COL_INDICES = new HashMap< String, Integer >();
      UI_COLUMNS = new String[ projection.length - 1 ];
      
      // 1 cause if query from a ContentProvider the first
      // projection item has to be _ID. We do not include this
      // into UI but have to start from 1.
      for ( int i = 1; i < projection.length; i++ )
      {
         UI_COL_INDICES.put( projection[ i ], i );
         UI_COLUMNS[ i - 1 ] = projection[ i ];
      }
   }
}
