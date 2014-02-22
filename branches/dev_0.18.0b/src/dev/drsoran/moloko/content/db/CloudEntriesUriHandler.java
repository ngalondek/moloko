/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.moloko.content.Columns.CloudEntryColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.AbstractContentUriHandler;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.IContentUriHandler;
import dev.drsoran.moloko.domain.model.CloudEntryType;


public class CloudEntriesUriHandler extends AbstractContentUriHandler
{
   private final IContentUriHandler tasksContentUriHandler;
   
   
   
   public CloudEntriesUriHandler( IContentUriHandler tasksContentUriHandler )
   {
      this.tasksContentUriHandler = tasksContentUriHandler;
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs,
                                  String sortOrder )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      // We don't support sort order because this would include parsing SQL
      // syntax.
      if ( sortOrder != null )
      {
         throw new IllegalArgumentException( "Sort order is not supported for Cloud Entries" );
      }
      
      Cursor c = null;
      try
      {
         c = tasksContentUriHandler.query( ContentUris.TASKS_CONTENT_URI,
                                           TaskColumns.PROJECTION,
                                           selection,
                                           null,
                                           null );
         
         final Map< Entry, Integer > cloudEntriesSorted = new TreeMap< Entry, Integer >();
         
         while ( c.moveToNext() )
         {
            addOrIncrementTags( cloudEntriesSorted,
                                c.getString( TaskColumns.TAGS_IDX ) );
            
            addOrIncrementLists( cloudEntriesSorted,
                                 c.getString( TaskColumns.LIST_NAME_IDX ),
                                 c.getLong( TaskColumns.LIST_ID_IDX ) );
            
            final long locationId = c.getLong( TaskColumns.LOCATION_ID_IDX );
            if ( locationId != Constants.NO_ID )
            {
               addOrIncrementLocations( cloudEntriesSorted,
                                        c.getString( TaskColumns.LOCATION_NAME_IDX ),
                                        locationId );
            }
            
         }
         
         final List< Object[] > cloudEntryColumns = new ArrayList< Object[] >( cloudEntriesSorted.size() );
         
         for ( Entry entry : cloudEntriesSorted.keySet() )
         {
            final Object[] columns = new Object[ CloudEntryColumns.PROJECTION.length ];
            columns[ CloudEntryColumns.ENTRY_TYPE_IDX ] = entry.type;
            columns[ CloudEntryColumns.DISPLAY_IDX ] = entry.name;
            columns[ CloudEntryColumns.COUNT_IDX ] = cloudEntriesSorted.get( entry );
            columns[ CloudEntryColumns.ELEMENT_ID_IDX ] = entry.elementId;
            
            cloudEntryColumns.add( columns );
         }
         
         return new ListCursor( cloudEntryColumns, CloudEntryColumns.PROJECTION );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   private void addOrIncrementTags( Map< Entry, Integer > tags,
                                    String separatedTags )
   {
      for ( String tag : TextUtils.split( separatedTags,
                                          TaskColumns.TAGS_SEPARATOR ) )
      {
         addOrIncrement( tags, new Entry( tag,
                                          CloudEntryType.Tag.getValue(),
                                          Constants.NO_ID ) );
      }
   }
   
   
   
   private void addOrIncrementLists( Map< Entry, Integer > lists,
                                     String name,
                                     long listId )
   {
      addOrIncrement( lists,
                      new Entry( name,
                                 CloudEntryType.TasksList.getValue(),
                                 listId ) );
   }
   
   
   
   private void addOrIncrementLocations( Map< Entry, Integer > locations,
                                         String name,
                                         long locationId )
   {
      addOrIncrement( locations,
                      new Entry( name,
                                 CloudEntryType.Location.getValue(),
                                 locationId ) );
   }
   
   
   
   private void addOrIncrement( Map< Entry, Integer > map, Entry entry )
   {
      Integer count = map.get( entry );
      if ( count == null )
      {
         count = 1;
      }
      else
      {
         count = Integer.valueOf( count.intValue() + 1 );
      }
      
      map.put( entry, count );
   }
   
   
   private static class Entry implements Comparable< Entry >
   {
      public final String name;
      
      public final int type;
      
      public final long elementId;
      
      
      
      public Entry( String name, int type, long elementId )
      {
         this.name = name;
         this.type = type;
         this.elementId = elementId;
      }
      
      
      
      @Override
      public int compareTo( Entry another )
      {
         int res = name.compareTo( another.name );
         if ( res == 0 )
         {
            res = type - another.type;
         }
         
         if ( res == 0 )
         {
            res = (int) ( elementId - another.elementId );
         }
         
         return res;
      }
   }
}
