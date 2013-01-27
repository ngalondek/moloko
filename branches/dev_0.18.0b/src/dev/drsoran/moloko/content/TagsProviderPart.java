/*
 * Copyright (c) 2012 Ronny Röhricht
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

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
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.Tag;
import dev.drsoran.rtm.TagWithTaskCount;


public class TagsProviderPart extends AbstractProviderPart
{
   private static final Class< TagsProviderPart > TAG = TagsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Tags._ID, Tags.TAGS };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String QUERY;
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
      
      QUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                   
                                                   // tables
                                                   TaskSeries.PATH + ","
                                                      + RawTasks.PATH,
                                                   
                                                   // columns
                                                   new String[]
                                                   {
                                                    TaskSeries.PATH + "."
                                                       + TaskSeries._ID,
                                                    TaskSeries.TAGS,
                                                    RawTasks.COMPLETED_DATE },
                                                   // where
                                                   TaskSeries.PATH
                                                      + "."
                                                      + TaskSeries._ID
                                                      + "="
                                                      + RawTasks.TASKSERIES_ID
                                                      
                                                      // Only taskseries with tags
                                                      + " AND "
                                                      + TaskSeries.TAGS
                                                      + " IS NOT NULL"
                                                      
                                                      // Only non-completed tasks
                                                      + " AND "
                                                      + RawTasks.COMPLETED_DATE
                                                      + " IS NULL"
                                                      
                                                      // Only non-deleted tasks
                                                      + " AND "
                                                      + RawTasks.DELETED_DATE
                                                      + " IS NULL",
                                                   
                                                   // group by
                                                   null,
                                                   null,
                                                   
                                                   // order by
                                                   TaskSeries.PATH + "."
                                                      + TaskSeries._ID,
                                                   null );
   }
   
   
   
   public final static List< Tag > getAllTags( ContentProviderClient client,
                                               String taskSeriesId,
                                               Comparator< Tag > cmp,
                                               boolean distinct )
   {
      Collection< Tag > tags = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Tags.CONTENT_URI,
                           PROJECTION,
                           taskSeriesId != null ? TaskSeries.PATH + "."
                              + TaskSeries._ID + "=" + taskSeriesId : null,
                           null,
                           Tags.DEFAULT_SORT_ORDER );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            if ( distinct )
            {
               if ( cmp != null )
                  tags = new TreeSet< Tag >( cmp );
               else
                  tags = new HashSet< Tag >();
            }
            else
            {
               tags = new LinkedList< Tag >();
            }
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final String[] tagStrings = TextUtils.split( c.getString( COL_INDICES.get( Tags.TAGS ) ),
                                                               Tags.TAGS_SEPARATOR );
                  ok = tagStrings != null;
                  
                  if ( ok )
                     for ( String tagStr : tagStrings )
                     {
                        tags.add( new Tag( taskSeriesId, tagStr ) );
                     }
               }
            }
            
            if ( !ok )
               tags = null;
            else if ( cmp != null && tags instanceof List< ? > )
               Collections.sort( (List< Tag >) tags, cmp );
         }
      }
      catch ( final RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query tags failed. ", e );
         tags = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return new ArrayList< Tag >( tags );
   }
   
   
   
   public final static List< TagWithTaskCount > getAllTagsWithTaskCount( ContentProviderClient client )
   {
      List< TagWithTaskCount > result = null;
      final List< Tag > allTags = getAllTags( client, null, null, false );
      
      boolean ok = allTags != null;
      
      if ( ok )
      {
         final HashMap< String, Integer > tagsWithCount = new HashMap< String, Integer >();
         
         for ( Tag tag : allTags )
         {
            final String tagStr = tag.getTag();
            final Integer count = tagsWithCount.get( tagStr );
            
            if ( count != null )
               tagsWithCount.put( tagStr, Integer.valueOf( count + 1 ) );
            else
               tagsWithCount.put( tagStr, Integer.valueOf( 1 ) );
         }
         
         result = new ArrayList< TagWithTaskCount >( tagsWithCount.size() );
         
         for ( Entry< String, Integer > tagWithCnt : tagsWithCount.entrySet() )
         {
            result.add( new TagWithTaskCount( tagWithCnt.getKey(),
                                              tagWithCnt.getValue() ) );
         }
      }
      
      return ok ? result : null;
   }
   
   
   
   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver()
             .registerContentObserver( TaskSeries.CONTENT_URI, true, observer );
      context.getContentResolver()
             .registerContentObserver( RawTasks.CONTENT_URI, true, observer );
   }
   
   
   
   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public TagsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Tags.PATH );
   }
   
   
   
   @Override
   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      // Replace _id to ROWID AS _id
      {
         final List< String > projectionList = Arrays.asList( projection );
         
         for ( int i = 0, cnt = projectionList.size(); i < cnt; ++i )
         {
            if ( projectionList.get( i ).equalsIgnoreCase( Tags._ID ) )
               projectionList.set( i, "ROWID AS " + Tags._ID );
         }
      }
      
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( QUERY )
                                                                        .append( ")" );
      
      if ( !TextUtils.isEmpty( selection ) )
      {
         stringBuilder.append( " WHERE ( " )
                      .append( selectionArgs != null
                                                    ? Queries.bindAll( selection,
                                                                       selectionArgs )
                                                    : selection )
                      .append( " )" );
      }
      
      if ( !TextUtils.isEmpty( sortOrder ) )
      {
         stringBuilder.append( " ORDER BY " ).append( sortOrder );
      }
      
      final String finalQuery = stringBuilder.toString();
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      final Cursor cursor = db.rawQuery( finalQuery, null );
      
      return cursor;
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Tags.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Tags.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Tags.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return Tags.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
