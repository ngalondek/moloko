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

package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TagOverviews;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.TagWithTaskCount;


public class TagOverviewsProviderPart extends AbstractProviderPart
{
   private static final String TAG = TagOverviewsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { TagOverviews._ID, TagOverviews.TAG, TagOverviews.TASKS_COUNT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String query;
   
   private final static String subQuery;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      query = SQLiteQueryBuilder.buildQueryString( // not distinct
                                                   false,
                                                   // tables
                                                   Tags.PATH,
                                                   // columns
                                                   new String[]
                                                   {
                                                    TagOverviews._ID,
                                                    TagOverviews.TAG,
                                                    "count("
                                                       + TagOverviews.TAG
                                                       + ") AS "
                                                       + TagOverviews.TASKS_COUNT },
                                                   null,
                                                   null,
                                                   null,
                                                   null,
                                                   null );
      
      subQuery = SQLiteQueryBuilder.buildQueryString( // not distinct
                                                      false,
                                                      // tables
                                                      TaskSeries.PATH + ","
                                                         + RawTasks.PATH,
                                                      // columns
                                                      new String[]
                                                      {
                                                       TaskSeries.PATH + "."
                                                          + TaskSeries._ID
                                                          + " AS series_id",
                                                       RawTasks.COMPLETED_DATE },
                                                      // where
                                                      TaskSeries.PATH
                                                         + "."
                                                         + TaskSeries._ID
                                                         + "="
                                                         + RawTasks.PATH
                                                         + "."
                                                         + RawTasks.TASKSERIES_ID,
                                                      null,
                                                      null,
                                                      null,
                                                      null );
   }
   
   

   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver().registerContentObserver( Tags.CONTENT_URI,
                                                            true,
                                                            observer );
   }
   


   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   


   public final static TagWithTaskCount getTagOverview( ContentProviderClient client,
                                                        String id )
   {
      TagWithTaskCount tag = null;
      
      try
      {
         final Cursor c = client.query( TagOverviews.CONTENT_URI,
                                        PROJECTION,
                                        TagOverviews._ID + " = " + id,
                                        null,
                                        null );
         
         if ( c != null && c.getCount() > 0 && c.moveToFirst() )
         {
            tag = createTagOverview( c );
         }
         
         if ( c != null )
            c.close();
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query tag overview failed. ", e );
         tag = null;
      }
      
      return tag;
   }
   


   public final static ArrayList< TagWithTaskCount > getTagsOverview( ContentProviderClient client,
                                                                      boolean excludeCompleted )
   {
      ArrayList< TagWithTaskCount > tags = null;
      
      try
      {
         final Cursor c = client.query( TagOverviews.CONTENT_URI,
                                        PROJECTION,
                                        excludeCompleted ? "subQuery."
                                           + RawTasks.COMPLETED_DATE
                                           + " IS NULL" : null,
                                        null,
                                        TagOverviews.DEFAULT_SORT_ORDER );
         
         tags = new ArrayList< TagWithTaskCount >();
         
         boolean ok = true;
         
         if ( c.getCount() > 0 )
         {
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               final TagWithTaskCount tag = createTagOverview( c );
               tags.add( tag );
            }
         }
         
         if ( !ok )
            tags = null;
         
         c.close();
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query tags overview failed. ", e );
         tags = null;
      }
      
      return tags;
   }
   


   public TagOverviewsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, TagOverviews.PATH );
   }
   


   @Override
   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( query )
                                                                        .append( " LEFT OUTER JOIN (" )
                                                                        .append( subQuery )
                                                                        .append( ") AS subQuery ON subQuery.series_id = " )
                                                                        .append( Tags.PATH )
                                                                        .append( "." )
                                                                        .append( Tags.TASKSERIES_ID );
      
      if ( !TextUtils.isEmpty( selection ) )
      {
         stringBuilder.append( " WHERE ( " )
                      .append( selectionArgs != null
                                                    ? Queries.bindAll( selection,
                                                                       selectionArgs )
                                                    : selection )
                      .append( " )" );
      }
      
      stringBuilder.append( " GROUP BY " ).append( Tags.TAG );
      
      if ( !TextUtils.isEmpty( sortOrder ) )
      {
         stringBuilder.append( " ORDER BY " ).append( sortOrder );
      }
      
      final String finalQuery = stringBuilder.append( ")" ).toString();
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      final Cursor cursor = db.rawQuery( finalQuery, null );
      
      return cursor;
   }
   


   @Override
   protected String getContentItemType()
   {
      return null;
   }
   


   @Override
   protected String getContentType()
   {
      return TagOverviews.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return TagOverviews.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return TagOverviews.DEFAULT_SORT_ORDER;
   }
   


   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   


   public String[] getProjection()
   {
      return PROJECTION;
   }
   


   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   


   private final static TagWithTaskCount createTagOverview( Cursor c )
   {
      return new TagWithTaskCount( c.getString( COL_INDICES.get( TagOverviews._ID ) ),
                                   c.getString( COL_INDICES.get( TagOverviews.TAG ) ),
                                   c.getInt( COL_INDICES.get( TagOverviews.TASKS_COUNT ) ) );
   }
   
}
