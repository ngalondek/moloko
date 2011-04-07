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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Participants;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Participant;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class TasksProviderPart extends AbstractProviderPart
{
   private static final String TAG = "Moloko."
      + TasksProviderPart.class.getSimpleName();
   
   
   public final static class NewTaskIds
   {
      public final String taskSeriesId;
      
      public final String rawTaskId;
      
      

      public NewTaskIds( String taskSeriesId, String rawTaskId )
      {
         this.taskSeriesId = taskSeriesId;
         this.rawTaskId = rawTaskId;
      }
   }
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Tasks._ID, Tasks.LIST_ID, Tasks.LIST_NAME, Tasks.IS_SMART_LIST,
    Tasks.TASKSERIES_CREATED_DATE, Tasks.MODIFIED_DATE, Tasks.TASKSERIES_NAME,
    Tasks.SOURCE, Tasks.URL, Tasks.RECURRENCE, Tasks.RECURRENCE_EVERY,
    Tasks.TASKSERIES_ID, Tasks.DUE_DATE, Tasks.HAS_DUE_TIME, Tasks.ADDED_DATE,
    Tasks.COMPLETED_DATE, Tasks.DELETED_DATE, Tasks.PRIORITY, Tasks.POSTPONED,
    Tasks.ESTIMATE, Tasks.ESTIMATE_MILLIS, Tasks.TAGS, Tasks.LOCATION_ID,
    Tasks.LOCATION_NAME, Tasks.LONGITUDE, Tasks.LATITUDE, Tasks.ADDRESS,
    Tasks.VIEWABLE, Tasks.ZOOM, Tasks.TAGS, Tasks.PARTICIPANT_IDS,
    Tasks.PARTICIPANT_FULLNAMES, Tasks.PARTICIPANT_USERNAMES, Tasks.NUM_NOTES };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String SUB_QUERY;
   
   private final static String PARTICIPANTS_SUB_QUERY;
   
   private final static String NUM_NOTES_SUBQUERY;
   
   private final static String NEW_TASK_SERIES_ID_STUB = TaskSeries.PATH + "_";
   
   private final static String NEW_RAW_TASK_ID_STUB = RawTasks.PATH + "_";
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      SUB_QUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                       
                                                       // tables
                                                       TaskSeries.PATH + ","
                                                          + Lists.PATH + ","
                                                          + RawTasks.PATH,
                                                       
                                                       // columns
                                                       new String[]
                                                       {
                                                        RawTasks.PATH + "."
                                                           + RawTasks._ID
                                                           + " AS rawTask_id",
                                                        TaskSeries.PATH + "."
                                                           + TaskSeries.LIST_ID
                                                           + " AS "
                                                           + Tasks.LIST_ID,
                                                        Tasks.LIST_NAME,
                                                        Tasks.IS_SMART_LIST,
                                                        Tasks.TASKSERIES_CREATED_DATE,
                                                        Tasks.MODIFIED_DATE,
                                                        Tasks.TASKSERIES_NAME,
                                                        Tasks.SOURCE,
                                                        Tasks.URL,
                                                        Tasks.RECURRENCE,
                                                        Tasks.RECURRENCE_EVERY,
                                                        Tasks.TASKSERIES_ID,
                                                        Tasks.DUE_DATE,
                                                        Tasks.HAS_DUE_TIME,
                                                        Tasks.ADDED_DATE,
                                                        Tasks.COMPLETED_DATE,
                                                        Tasks.DELETED_DATE,
                                                        Tasks.PRIORITY,
                                                        Tasks.POSTPONED,
                                                        Tasks.ESTIMATE,
                                                        Tasks.ESTIMATE_MILLIS,
                                                        Tasks.TAGS,
                                                        Tasks.LOCATION_ID },
                                                       
                                                       // where
                                                       TaskSeries.PATH
                                                          + "."
                                                          + TaskSeries.LIST_ID
                                                          + "="
                                                          + Lists.PATH
                                                          + "."
                                                          + Lists._ID
                                                          + " AND "
                                                          + TaskSeries.PATH
                                                          + "."
                                                          + TaskSeries._ID
                                                          + "="
                                                          + RawTasks.PATH
                                                          + "."
                                                          + RawTasks.TASKSERIES_ID
                                                          // Only non-deleted tasks
                                                          + " AND "
                                                          + RawTasks.PATH
                                                          + "."
                                                          + RawTasks.DELETED_DATE
                                                          + " IS NULL",
                                                       null,
                                                       null,
                                                       null,
                                                       null );
      
      PARTICIPANTS_SUB_QUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                                    
                                                                    // tables
                                                                    TaskSeries.PATH
                                                                       + ","
                                                                       + Participants.PATH,
                                                                    
                                                                    // columns
                                                                    new String[]
                                                                    {
                                                                     TaskSeries.PATH
                                                                        + "."
                                                                        + TaskSeries.RTM_ID,
                                                                     Participants.PATH
                                                                        + "."
                                                                        + Participants.RTM_TASKSERIES_ID
                                                                        + " AS series_id",
                                                                     "group_concat("
                                                                        + Participants.RTM_CONTACT_ID
                                                                        + ",\""
                                                                        + Tasks.PARTICIPANTS_DELIMITER
                                                                        + "\") AS "
                                                                        + Tasks.PARTICIPANT_IDS,
                                                                     "group_concat("
                                                                        + Participants.FULLNAME
                                                                        + ",\""
                                                                        + Tasks.PARTICIPANTS_DELIMITER
                                                                        + "\") AS "
                                                                        + Tasks.PARTICIPANT_FULLNAMES,
                                                                     "group_concat("
                                                                        + Participants.USERNAME
                                                                        + ",\""
                                                                        + Tasks.PARTICIPANTS_DELIMITER
                                                                        + "\") AS "
                                                                        + Tasks.PARTICIPANT_USERNAMES },
                                                                    
                                                                    // where
                                                                    TaskSeries.PATH
                                                                       + "."
                                                                       + TaskSeries.RTM_ID
                                                                       + "="
                                                                       + Participants.PATH
                                                                       + "."
                                                                       + Participants.RTM_TASKSERIES_ID,
                                                                    
                                                                    // group by
                                                                    TaskSeries.PATH
                                                                       + "."
                                                                       + TaskSeries.RTM_ID,
                                                                    null,
                                                                    // order by
                                                                    Participants.DEFAULT_SORT_ORDER,
                                                                    null );
      
      NUM_NOTES_SUBQUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                                
                                                                // tables
                                                                Notes.PATH,
                                                                
                                                                // columns
                                                                new String[]
                                                                { "count("
                                                                   + Notes.PATH
                                                                   + "."
                                                                   + Notes.TASKSERIES_ID
                                                                   + ")" },
                                                                
                                                                // where
                                                                "subQuery."
                                                                   + RawTasks.TASKSERIES_ID
                                                                   + "="
                                                                   + Notes.PATH
                                                                   + "."
                                                                   + Notes.TASKSERIES_ID
                                                                   // Only non-deleted notes
                                                                   + " AND "
                                                                   + Notes.PATH
                                                                   + "."
                                                                   + Notes.NOTE_DELETED
                                                                   + " IS NULL",
                                                                null,
                                                                null,
                                                                null,
                                                                null );
   }
   
   

   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver().registerContentObserver( Lists.CONTENT_URI,
                                                            true,
                                                            observer );
      context.getContentResolver()
             .registerContentObserver( TaskSeries.CONTENT_URI, true, observer );
      context.getContentResolver()
             .registerContentObserver( RawTasks.CONTENT_URI, true, observer );
      context.getContentResolver()
             .registerContentObserver( Locations.CONTENT_URI, true, observer );
      context.getContentResolver().registerContentObserver( Notes.CONTENT_URI,
                                                            true,
                                                            observer );
      context.getContentResolver()
             .registerContentObserver( Participants.CONTENT_URI, true, observer );
   }
   


   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   


   public final static Task getTask( ContentProviderClient client, long id )
   {
      Task task = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Queries.contentUriWithId( Tasks.CONTENT_URI, id ),
                           PROJECTION,
                           null,
                           null,
                           null );
         
         boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            task = createTask( c );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query task failed. ", e );
         task = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return task;
   }
   


   public final static List< Task > getTasks( ContentProviderClient client,
                                              String selection,
                                              String order )
   {
      List< Task > tasks = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Rtm.Tasks.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           order );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            tasks = new ArrayList< Task >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final Task task = createTask( c );
                  ok = task != null;
                  
                  if ( ok )
                     tasks.add( task );
               }
            }
            
            if ( !ok )
               tasks = null;
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query tasks failed. ", e );
         tasks = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return tasks;
   }
   


   public final static List< ContentProviderOperation > insertTask( ContentResolver contentResolver,
                                                                    Task task )
   {
      List< ContentProviderOperation > operations = null;
      // final ContentProviderClient client = contentResolver.acquireContentProviderClient( Rtm.AUTHORITY );
      //
      // boolean ok = client != null;
      //
      // NewTaskIds newIds = null;
      //
      // if ( ok )
      // {
      // newIds = TasksProviderPart.generateIdsForNewTask( client );
      // ok = newIds != null;
      // }
      //
      // if ( client != null )
      // client.release();
      //
      // if ( ok )
      // {
      // final RtmTask rtmTask = new RtmTask( newIds.rawTaskId,
      // newIds.taskSeriesId,
      // task.getDue(),
      // task.hasDueTime() ? 1 : 0,
      // task.getAdded(),
      // task.getCompleted(),
      // task.getDeleted(),
      // task.getPriority(),
      // task.getPosponed(),
      // task.getEstimate(),
      // task.getEstimateMillis() );
      //
      // final RtmTaskSeries rtmTaskSeries = new RtmTaskSeries( newIds.taskSeriesId,
      // task.getListId(),
      // task.getCreated(),
      // task.getModified(),
      // task.getName(),
      // task.getSource(),
      // Collections.singletonList( rtmTask ),
      // null,
      // task.getLocationId(),
      // task.getUrl(),
      // task.getRecurrence(),
      // task.isEveryRecurrence(),
      // task.getTags(),
      // task.getParticipants() );
      //
      // operations = RtmTaskSeriesProviderPart.insertTaskSeries( rtmTaskSeries );
      // }
      
      return operations;
   }
   


   public TasksProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Tasks.PATH );
   }
   


   @Override
   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      Cursor cursor = null;
      
      final List< String > projectionList = Arrays.asList( projection );
      
      boolean projectionContainsId = false;
      boolean replacedNumNotes = false;
      
      final StringBuilder projectionSB = new StringBuilder();
      
      for ( int i = 0; i < projection.length
         && ( !projectionContainsId || !replacedNumNotes ); i++ )
      {
         final String column = projection[ i ];
         
         // In case of a join with the locations table the _id gets ambiguous.
         // So we have to qualify it.
         if ( !projectionContainsId && column.endsWith( Tasks._ID ) )
         {
            projectionSB.append( "rawTask_id AS " ).append( Tasks._ID );
            
            projectionList.set( i, projectionSB.toString() );
            projectionContainsId = true;
            
            projectionSB.setLength( 0 );
         }
         
         // We have to replace the num_notes column by the numNotesSubQuery
         // expression.
         if ( !replacedNumNotes && column.equals( Tasks.NUM_NOTES ) )
         {
            projectionSB.append( "(" )
                        .append( NUM_NOTES_SUBQUERY )
                        .append( ")" )
                        .append( " AS " )
                        .append( Tasks.NUM_NOTES );
            
            projectionList.set( i, projectionSB.toString() );
            replacedNumNotes = true;
            
            projectionSB.setLength( 0 );
         }
      }
      
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( SUB_QUERY )
                                                                        .append( ") AS subQuery" );
      
      // Add locations columns
      stringBuilder.append( " LEFT OUTER JOIN " )
                   .append( Locations.PATH )
                   .append( " ON " )
                   .append( Locations.PATH )
                   .append( "." )
                   .append( Locations._ID )
                   .append( " = subQuery." )
                   .append( TaskSeries.LOCATION_ID );
      
      // Add participants columns
      stringBuilder.append( " LEFT OUTER JOIN " )
                   .append( "(" )
                   .append( PARTICIPANTS_SUB_QUERY )
                   .append( ") AS participantsSubQuery ON participantsSubQuery.series_id" )
                   .append( " = subQuery." )
                   .append( Tasks.TASKSERIES_ID );
      
      // Only if the ID is given in the projection we can use it
      if ( id != null && projectionContainsId )
      {
         stringBuilder.append( " WHERE rawTask_id" )
                      .append( " = " )
                      .append( id );
      }
      
      if ( !TextUtils.isEmpty( selection ) )
      {
         selection = selection.replaceAll( "\\b" + Tasks._ID + "\\b",
                                           "rawTask_id" );
         
         // Replace selection _ids
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
      
      final String query = stringBuilder.toString();
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      cursor = db.rawQuery( query, null );
      
      return cursor;
   }
   


   @Override
   protected String getContentItemType()
   {
      return Tasks.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Tasks.CONTENT_TYPE;
   }
   


   @Override
   public Uri getContentUri()
   {
      return Tasks.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Tasks.DEFAULT_SORT_ORDER;
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
   


   private final static Task createTask( Cursor c )
   {
      final String taskSeriesId = c.getString( COL_INDICES.get( Tasks.TASKSERIES_ID ) );
      
      return new Task( c.getString( COL_INDICES.get( Tasks._ID ) ),
                       taskSeriesId,
                       c.getString( COL_INDICES.get( Tasks.LIST_NAME ) ),
                       c.getInt( COL_INDICES.get( Tasks.IS_SMART_LIST ) ) != 0,
                       new Date( c.getLong( COL_INDICES.get( Tasks.TASKSERIES_CREATED_DATE ) ) ),
                       Queries.getOptDate( c,
                                           COL_INDICES.get( Tasks.MODIFIED_DATE ) ),
                       c.getString( COL_INDICES.get( Tasks.TASKSERIES_NAME ) ),
                       c.getString( COL_INDICES.get( Tasks.SOURCE ) ),
                       c.getString( COL_INDICES.get( Tasks.URL ) ),
                       Queries.getOptString( c,
                                             COL_INDICES.get( Tasks.RECURRENCE ) ),
                       Queries.getOptBool( c,
                                           COL_INDICES.get( Tasks.RECURRENCE_EVERY ),
                                           false ),
                       Queries.getOptString( c,
                                             COL_INDICES.get( Tasks.LOCATION_ID ) ),
                       c.getString( COL_INDICES.get( Tasks.LIST_ID ) ),
                       Queries.getOptDate( c, COL_INDICES.get( Tasks.DUE_DATE ) ),
                       c.getInt( COL_INDICES.get( Tasks.HAS_DUE_TIME ) ) != 0,
                       new Date( c.getLong( COL_INDICES.get( Tasks.ADDED_DATE ) ) ),
                       Queries.getOptDate( c,
                                           COL_INDICES.get( Tasks.COMPLETED_DATE ) ),
                       Queries.getOptDate( c,
                                           COL_INDICES.get( Tasks.DELETED_DATE ) ),
                       RtmTask.convertPriority( c.getString( COL_INDICES.get( Tasks.PRIORITY ) ) ),
                       c.getInt( COL_INDICES.get( Tasks.POSTPONED ) ),
                       Queries.getOptString( c,
                                             COL_INDICES.get( Tasks.ESTIMATE ) ),
                       c.getLong( COL_INDICES.get( Tasks.ESTIMATE_MILLIS ) ),
                       Queries.getOptString( c,
                                             COL_INDICES.get( Tasks.LOCATION_NAME ) ),
                       Queries.getOptFloat( c,
                                            COL_INDICES.get( Tasks.LONGITUDE ),
                                            0.0f ),
                       Queries.getOptFloat( c,
                                            COL_INDICES.get( Tasks.LATITUDE ),
                                            0.0f ),
                       Queries.getOptString( c, COL_INDICES.get( Tasks.ADDRESS ) ),
                       Queries.getOptBool( c,
                                           COL_INDICES.get( Tasks.VIEWABLE ),
                                           false ),
                       Queries.getOptInt( c, COL_INDICES.get( Tasks.ZOOM ), -1 ),
                       Queries.getOptString( c, COL_INDICES.get( Tasks.TAGS ) ),
                       getPartitipantsList( taskSeriesId, c ),
                       c.getInt( COL_INDICES.get( Tasks.NUM_NOTES ) ) );
   }
   


   private final static ParticipantList getPartitipantsList( String taskSeriesId,
                                                             Cursor c )
   {
      ParticipantList participantList = null;
      
      final String partContactIds = Queries.getOptString( c,
                                                          COL_INDICES.get( Tasks.PARTICIPANT_IDS ) );
      if ( !TextUtils.isEmpty( partContactIds ) )
      {
         final String partFullnames = Queries.getOptString( c,
                                                            COL_INDICES.get( Tasks.PARTICIPANT_FULLNAMES ) );
         final String partUsernames = Queries.getOptString( c,
                                                            COL_INDICES.get( Tasks.PARTICIPANT_FULLNAMES ) );
         
         if ( !TextUtils.isEmpty( partFullnames )
            && !TextUtils.isEmpty( partUsernames ) )
         {
            final String[] splitIds = TextUtils.split( partContactIds,
                                                       Tasks.PARTICIPANTS_DELIMITER );
            final String[] splitFullnames = TextUtils.split( partFullnames,
                                                             Tasks.PARTICIPANTS_DELIMITER );
            final String[] splitUsernames = TextUtils.split( partUsernames,
                                                             Tasks.PARTICIPANTS_DELIMITER );
            
            if ( splitIds.length == splitFullnames.length
               && splitIds.length == splitUsernames.length )
            {
               participantList = new ParticipantList( taskSeriesId );
               
               for ( int i = 0; i < splitIds.length; i++ )
               {
                  participantList.addParticipant( new Participant( -1,
                                                                   taskSeriesId,
                                                                   splitIds[ i ],
                                                                   splitFullnames[ i ],
                                                                   splitUsernames[ i ] ) );
               }
            }
            else
            {
               Log.e( TAG,
                      "Expected equal lengths for participant fields. Has IDs:"
                         + splitIds.length + ", Names:" + splitFullnames.length
                         + ", User:" + splitUsernames.length );
            }
         }
      }
      
      return participantList;
   }
   


   private final static NewTaskIds generateIdsForNewTask( ContentProviderClient client )
   {
      String nextTaskSeriesId = getNextId( client,
                                           TaskSeries.CONTENT_URI,
                                           NEW_TASK_SERIES_ID_STUB );
      String nextRawTaskId = null;
      
      if ( nextTaskSeriesId != null )
         nextRawTaskId = getNextId( client,
                                    RawTasks.CONTENT_URI,
                                    NEW_RAW_TASK_ID_STUB );
      
      if ( nextTaskSeriesId != null && nextRawTaskId != null )
         return new NewTaskIds( nextTaskSeriesId, nextRawTaskId );
      else
         return null;
   }
   


   private final static String getNextId( ContentProviderClient client,
                                          Uri contentUri,
                                          String stub )
   {
      Cursor c = null;
      String newId = null;
      
      try
      {
         c = client.query( contentUri,
                           new String[]
                           { BaseColumns._ID },
                           BaseColumns._ID + " like '%" + stub + "%'",
                           null,
                           BaseColumns._ID + " ASC" );
         
         if ( c != null )
         {
            if ( c.getCount() > 0 )
            {
               if ( c.moveToLast() )
               {
                  final String[] parts = TextUtils.split( c.getString( 0 ),
                                                          stub );
                  if ( parts.length == 2 )
                  {
                     try
                     {
                        newId = stub + ( Long.parseLong( parts[ 1 ] ) + 1 );
                     }
                     catch ( NumberFormatException e )
                     {
                        Log.e( TAG, "Invalid new ID suffix", e );
                     }
                  }
               }
            }
            else
               newId = stub + 0;
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query new IDs failed. ", e );
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return newId;
   }
}
