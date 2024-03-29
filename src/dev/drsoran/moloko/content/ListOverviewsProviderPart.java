/*
 * Copyright (c) 2010 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmListWithTaskCount.ExtendedListInfo;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class ListOverviewsProviderPart extends AbstractProviderPart
{
   private static final Class< ListOverviewsProviderPart > TAG = ListOverviewsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { ListOverviews._ID, ListOverviews.LIST_NAME, ListOverviews.LIST_DELETED,
    ListOverviews.LOCKED, ListOverviews.ARCHIVED, ListOverviews.POSITION,
    ListOverviews.IS_SMART_LIST, ListOverviews.FILTER,
    ListOverviews.TASKS_COUNT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String QUERY;
   
   private final static String SUBQUERY_NON_COMPLETED_NON_DELETED;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      SUBQUERY_NON_COMPLETED_NON_DELETED = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                                                // tables
                                                                                TaskSeries.PATH
                                                                                   + ","
                                                                                   + RawTasks.PATH,
                                                                                // columns
                                                                                new String[]
                                                                                {
                                                                                 TaskSeries.PATH
                                                                                    + "."
                                                                                    + TaskSeries._ID
                                                                                    + " AS series_id",
                                                                                 TaskSeries.PATH
                                                                                    + "."
                                                                                    + TaskSeries.LIST_ID
                                                                                    + " AS "
                                                                                    + TaskSeries.LIST_ID,
                                                                                 RawTasks.COMPLETED_DATE },
                                                                                // where
                                                                                "series_id ="
                                                                                   + RawTasks.PATH
                                                                                   + "."
                                                                                   + RawTasks.TASKSERIES_ID
                                                                                   + " AND "
                                                                                   
                                                                                   // Only non-completed tasks
                                                                                   + RawTasks.COMPLETED_DATE
                                                                                   + " IS NULL AND "
                                                                                   // Only non-deleted tasks
                                                                                   + RawTasks.DELETED_DATE
                                                                                   + " IS NULL",
                                                                                null,
                                                                                null,
                                                                                null,
                                                                                null );
      
      QUERY = new StringBuilder( "SELECT " ).append( Lists.PATH )
                                            .append( ".*, count( subQuery.series_id ) AS " )
                                            .append( ListOverviews.TASKS_COUNT )
                                            .append( " FROM " )
                                            .append( Lists.PATH )
                                            .append( " LEFT OUTER JOIN (" )
                                            .append( SUBQUERY_NON_COMPLETED_NON_DELETED )
                                            .append( ") AS subQuery ON " )
                                            .append( Lists.PATH )
                                            .append( "." )
                                            .append( Lists._ID )
                                            .append( " = " )
                                            .append( TaskSeries.LIST_ID )
                                            .append( " AND " )
                                            .append( Lists.PATH )
                                            .append( "." )
                                            // Only non-deleted lists
                                            .append( Lists.LIST_DELETED )
                                            .append( " IS NULL GROUP BY " )
                                            .append( Lists.LIST_NAME )
                                            .toString();
   }
   
   
   
   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver().registerContentObserver( Lists.CONTENT_URI,
                                                            true,
                                                            observer );
      context.getContentResolver()
             .registerContentObserver( TaskSeries.CONTENT_URI, true, observer );
   }
   
   
   
   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public final static RtmListWithTaskCount getListOverview( ContentProviderClient client,
                                                             String selection )
   {
      RtmListWithTaskCount list = null;
      
      Cursor c = null;
      
      try
      {
         c = client.query( Rtm.ListOverviews.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           null );
         
         if ( c != null && c.getCount() > 0 && c.moveToFirst() )
         {
            list = createListOverview( client, c );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query lists overview failed. ", e );
         list = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return list;
   }
   
   
   
   public final static List< RtmListWithTaskCount > getListsOverview( ContentProviderClient client,
                                                                      String selection )
   {
      List< RtmListWithTaskCount > lists = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Rtm.ListOverviews.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           Rtm.ListOverviews.DEFAULT_SORT_ORDER );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            lists = new ArrayList< RtmListWithTaskCount >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final RtmListWithTaskCount list = createListOverview( client,
                                                                        c );
                  ok = list != null;
                  
                  if ( ok )
                     lists.add( list );
               }
            }
         }
         
         if ( !ok )
            lists = null;
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query lists overview failed. ", e );
         lists = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return lists;
   }
   
   
   
   public final static ExtendedListInfo getExtendedOverview( ContentResolver contentResolver,
                                                             String listId,
                                                             String filter )
   {
      ExtendedListInfo extendedOverview = null;
      List< Task > tasks = null;
      
      final ContentProviderClient client = contentResolver.acquireContentProviderClient( Tasks.CONTENT_URI );
      
      if ( client != null )
      {
         // Check if we have a smart list
         if ( filter != null )
         {
            // Here we need to collect also completed tasks since we have to count them.
            final String evalFilter = RtmSmartFilter.evaluate( filter, false /* collect completed tasks */);
            
            if ( !TextUtils.isEmpty( evalFilter ) )
            {
               tasks = TasksProviderPart.getTasks( client, evalFilter, null );
            }
            else
            {
               MolokoApp.Log.e( TAG,
                                "Unable to query extended list info with invalid filter "
                                   + filter );
            }
         }
         else
         {
            tasks = TasksProviderPart.getTasks( client, Tasks.LIST_ID + " = "
               + listId, null );
         }
         
         if ( tasks != null )
         {
            extendedOverview = new ExtendedListInfo();
            long sumEstimated = 0;
            
            for ( Task task : tasks )
            {
               final boolean isCompleted = task.getCompleted() != null;
               
               if ( !isCompleted )
               {
                  if ( task.getDue() != null )
                  {
                     final long dueMillis = task.getDue().getTime();
                     final int diffToNow = MolokoDateUtils.getTimespanInDays( System.currentTimeMillis(),
                                                                              dueMillis );
                     
                     // Today?
                     if ( diffToNow == 0 )
                        ++extendedOverview.dueTodayTaskCount;
                     
                     // Tomorrow?
                     else if ( diffToNow == 1 )
                        ++extendedOverview.dueTomorrowTaskCount;
                     
                     // Overdue?
                     else if ( diffToNow < 0 )
                        ++extendedOverview.overDueTaskCount;
                  }
                  
                  // Sum up estimated times
                  final long estimateMillis = task.getEstimateMillis();
                  
                  if ( estimateMillis != -1 )
                     sumEstimated += estimateMillis;
               }
               
               // Completed?
               if ( isCompleted )
                  ++extendedOverview.completedTaskCount;
            }
            
            if ( sumEstimated > 0 )
               extendedOverview.sumEstimated = sumEstimated;
         }
         
      }
      
      return extendedOverview;
   }
   
   
   
   public ListOverviewsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, ListOverviews.PATH );
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
      
      // Get the database and run the QUERY
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
      return ListOverviews.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ListOverviews.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return ListOverviews.DEFAULT_SORT_ORDER;
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
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   private final static RtmListWithTaskCount createListOverview( ContentProviderClient client,
                                                                 Cursor c )
   {
      RtmSmartFilter filter = null;
      
      if ( !c.isNull( COL_INDICES.get( Lists.FILTER ) ) )
         filter = new RtmSmartFilter( c.getString( COL_INDICES.get( Lists.FILTER ) ) );
      
      return new RtmListWithTaskCount( c.getString( COL_INDICES.get( ListOverviews._ID ) ),
                                       c.getString( COL_INDICES.get( ListOverviews.LIST_NAME ) ),
                                       c.getInt( COL_INDICES.get( ListOverviews.LOCKED ) ),
                                       c.getInt( COL_INDICES.get( ListOverviews.ARCHIVED ) ),
                                       c.getInt( COL_INDICES.get( ListOverviews.POSITION ) ),
                                       filter,
                                       ( filter != null )
                                                         ? getSmartFilterTaskCount( client,
                                                                                    filter )
                                                         : c.getInt( COL_INDICES.get( ListOverviews.TASKS_COUNT ) ) );
   }
   
   
   
   private final static int getSmartFilterTaskCount( ContentProviderClient client,
                                                     RtmSmartFilter filter )
   {
      int taskCount = -1;
      
      final String evalFilter = filter.getEvaluatedFilterString( false );
      
      boolean badFilter = evalFilter == null;
      
      if ( !badFilter )
      {
         try
         {
            final Cursor smartListTasks = client.query( Tasks.CONTENT_URI,
                                                        TasksProviderPart.PROJECTION /*
                                                                                      * Always use full projection to
                                                                                      * num_notes column
                                                                                      */,
                                                        evalFilter,
                                                        null,
                                                        null );
            
            taskCount = smartListTasks.getCount();
            smartListTasks.close();
         }
         catch ( SQLiteException e )
         {
            MolokoApp.Log.e( TAG, "Tried to QUERY with Bad RTM filter: "
               + evalFilter, e );
            badFilter = true;
         }
         catch ( RemoteException e )
         {
            MolokoApp.Log.e( TAG, "Unable to QUERY tasks with filter.", e );
            badFilter = true;
         }
      }
      
      if ( badFilter )
      {
         taskCount = -1;
      }
      
      return taskCount;
   }
}
