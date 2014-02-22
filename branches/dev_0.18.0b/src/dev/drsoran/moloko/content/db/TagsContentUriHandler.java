/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.moloko.content.Columns.TagColumns;
import dev.drsoran.moloko.content.AbstractContentUriHandler;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;


class TagsContentUriHandler extends AbstractContentUriHandler
{
   private final static String TAGS_QUERY_TABLE_NAMES;
   
   private final static String TAGS_QUERY_WHERE_CLAUSE;
   
   private final SQLiteDatabase database;
   
   static
   {
      TAGS_QUERY_TABLE_NAMES = RtmTaskSeriesTable.TABLE_NAME + ", "
         + RtmRawTasksTable.TABLE_NAME;
      
      TAGS_QUERY_WHERE_CLAUSE = RtmTaskSeriesTable.TABLE_NAME + "."
         + RtmTaskSeriesColumns._ID + "=" + RtmRawTasksTable.TABLE_NAME + "."
         + RtmRawTaskColumns.TASKSERIES_ID + " AND "
         + RtmTaskSeriesTable.TABLE_NAME + "." + RtmTaskSeriesColumns.TAGS
         + " IS NOT NULL";
   }
   
   
   
   public TagsContentUriHandler( SQLiteDatabase database )
   {
      this.database = database;
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
         throw new IllegalArgumentException( "Sort order is not supported for Tags" );
      }
      
      final SQLiteQueryBuilder queryBuilder = getTagsQueryBuilder();
      
      Cursor tasksCursor = null;
      try
      {
         final String rawQueryString = queryBuilder.buildQuery( new String[]
         { RtmTaskSeriesColumns.TAGS }, selection, null, null, null, null );
         
         tasksCursor = database.rawQuery( rawQueryString, selectionArgs );
         
         final Set< String > tags = new LinkedHashSet< String >( tasksCursor.getCount() );
         while ( tasksCursor.moveToNext() )
         {
            final String separatedTags = tasksCursor.getString( 0 );
            for ( String tag : TextUtils.split( separatedTags,
                                                RtmTaskSeriesColumns.TAGS_SEPARATOR ) )
            {
               tags.add( tag );
            }
         }
         
         final List< Object[] > tagColumns = new ArrayList< Object[] >( tags.size() );
         for ( String tag : tags )
         {
            tagColumns.add( new Object[]
            { tag } );
         }
         
         return new ListCursor( tagColumns, TagColumns.PROJECTION );
      }
      finally
      {
         if ( tasksCursor != null )
         {
            tasksCursor.close();
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
   
   
   
   private SQLiteQueryBuilder getTagsQueryBuilder()
   {
      final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
      queryBuilder.setTables( TAGS_QUERY_TABLE_NAMES );
      queryBuilder.appendWhere( TAGS_QUERY_WHERE_CLAUSE );
      queryBuilder.setDistinct( true );
      
      return queryBuilder;
   }
}
