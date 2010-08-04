package dev.drsoran.moloko.util;

import java.util.ArrayList;
import java.util.Iterator;


public final class ContentUiMapper
{
   private final static class Mapping
   {
      public final String name;
      
      public final int index;
      
      

      public Mapping( String name, int index )
      {
         this.name = name;
         this.index = index;
      }
   }
   
   private final ArrayList< Mapping > projection;
   
   private final ArrayList< Mapping > uiColumns;
   
   public int[] RESSOURCE_IDS;
   
   

   public ContentUiMapper( String[] projection, String[] uiColumns, int[] resIds )
   {
      this.projection = new ArrayList< Mapping >( projection.length );
      this.uiColumns = new ArrayList< Mapping >( uiColumns.length );
      
      for ( int i = 0; i < projection.length; i++ )
      {
         this.projection.add( new Mapping( projection[ i ], i ) );
      }
      
      // this is the difference from the projection to the
      // UI elements. It may happen that we have to query
      // more information than we display. These additionally
      // information has to come before the UI indices.
      final int diff = projection.length - uiColumns.length;
      
      for ( int i = 0; i < uiColumns.length; i++ )
      {
         this.uiColumns.add( new Mapping( uiColumns[ i ], i + diff ) );
      }
      
      RESSOURCE_IDS = resIds;
   }
   


   public int getProjectionColumnIndex( String name )
   {
      return findIndexByName( name, projection );
   }
   


   public int getUiColumnIndex( String name )
   {
      return findIndexByName( name, uiColumns );
   }
   


   public String[] getProjectionArray()
   {
      return ToNameArray( projection );
   }
   


   public String[] getUiColumnsArray()
   {
      return ToNameArray( uiColumns );
   }
   


   private final static int findIndexByName( String name,
                                             ArrayList< Mapping > collection )
   {
      int idx = -1;
      
      for ( Iterator< Mapping > iterator = collection.iterator(); idx == -1
         && iterator.hasNext(); )
      {
         final Mapping mapping = (Mapping) iterator.next();
         if ( mapping.name.equals( name ) )
            idx = mapping.index;
      }
      
      return idx;
   }
   


   private final static String[] ToNameArray( ArrayList< Mapping > collection )
   {
      final String[] nameArray = new String[ collection.size() ];
      
      for ( int i = 0; i < nameArray.length; ++i )
      {
         nameArray[ i ] = collection.get( i ).name;
      }
      
      return nameArray;
   }
}
