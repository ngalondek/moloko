package dev.drsoran.moloko.util;

import java.util.HashMap;


public final class ContentUiMapper
{
   public String[] PROJECTION;
   
   public String[] UI_COLUMNS;
   
   public HashMap< String, Integer > UI_COL_INDICES;
   
   public int[] RESSOURCE_IDS;
   
   

   public ContentUiMapper( String[] projection, String[] uiColumns, int[] resIds )
   {
      PROJECTION = projection;
      RESSOURCE_IDS = resIds;
      UI_COL_INDICES = new HashMap< String, Integer >();
      UI_COLUMNS = uiColumns;
      
      // this id the difference from the projection to the
      // UI elements. It may happen that we have to query
      // more information as we display. These additionally
      // information has to come before the UI indices.
      final int diff = projection.length - uiColumns.length;
      
      for ( int i = 0; i < uiColumns.length; i++ )
      {
         UI_COL_INDICES.put( uiColumns[ i ], i + diff );
      }
   }
}
