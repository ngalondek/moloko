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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Participants;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.SyncTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Participant;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.SyncTask;


public class SyncTasksProviderPart extends AbstractProviderPart
{
   private static final String TAG = "Moloko."
      + SyncTasksProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { SyncTasks._ID, SyncTasks.LIST_ID, SyncTasks.TASKSERIES_CREATED_DATE,
    SyncTasks.MODIFIED_DATE, SyncTasks.TASKSERIES_NAME, SyncTasks.SOURCE,
    SyncTasks.URL, SyncTasks.RECURRENCE, SyncTasks.RECURRENCE_EVERY,
    SyncTasks.TASKSERIES_ID, SyncTasks.DUE_DATE, SyncTasks.HAS_DUE_TIME,
    SyncTasks.ADDED_DATE, SyncTasks.COMPLETED_DATE, SyncTasks.DELETED_DATE,
    SyncTasks.PRIORITY, SyncTasks.POSTPONED, SyncTasks.ESTIMATE,
    SyncTasks.ESTIMATE_MILLIS, SyncTasks.LOCATION_ID, SyncTasks.TAGS,
    SyncTasks.PARTICIPANT_IDS, SyncTasks.PARTICIPANT_FULLNAMES,
    SyncTasks.PARTICIPANT_USERNAMES };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String SUB_QUERY;
   
   public final static String TAGS_SUB_QUERY;
   
   public final static String PARTICIPANTS_SUB_QUERY;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      SUB_QUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                       
                                                       // tables
                                                       TaskSeries.PATH + ","
                                                          + RawTasks.PATH,
                                                       
                                                       // columns
                                                       new String[]
                                                       {
                                                        RawTasks.PATH + "."
                                                           + RawTasks._ID
                                                           + " AS "
                                                           + SyncTasks._ID,
                                                        SyncTasks.LIST_ID,
                                                        SyncTasks.TASKSERIES_CREATED_DATE,
                                                        SyncTasks.MODIFIED_DATE,
                                                        SyncTasks.TASKSERIES_NAME,
                                                        SyncTasks.SOURCE,
                                                        SyncTasks.URL,
                                                        SyncTasks.RECURRENCE,
                                                        SyncTasks.RECURRENCE_EVERY,
                                                        SyncTasks.TASKSERIES_ID,
                                                        SyncTasks.DUE_DATE,
                                                        SyncTasks.HAS_DUE_TIME,
                                                        SyncTasks.ADDED_DATE,
                                                        SyncTasks.COMPLETED_DATE,
                                                        SyncTasks.DELETED_DATE,
                                                        SyncTasks.PRIORITY,
                                                        SyncTasks.POSTPONED,
                                                        SyncTasks.ESTIMATE,
                                                        SyncTasks.ESTIMATE_MILLIS,
                                                        SyncTasks.LOCATION_ID },
                                                       
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
      
      TAGS_SUB_QUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                            
                                                            // tables
                                                            TaskSeries.PATH
                                                               + ","
                                                               + Tags.PATH,
                                                            
                                                            // columns
                                                            new String[]
                                                            {
                                                             TaskSeries.PATH
                                                                + "."
                                                                + TaskSeries._ID,
                                                             Tags.PATH
                                                                + "."
                                                                + Tags.TASKSERIES_ID
                                                                + " AS series_id",
                                                             "group_concat("
                                                                + Tags.TAG
                                                                + ",\""
                                                                + SyncTasks.TAGS_DELIMITER
                                                                + "\") AS "
                                                                + SyncTasks.TAGS },
                                                            
                                                            // where
                                                            TaskSeries.PATH
                                                               + "."
                                                               + TaskSeries._ID
                                                               + "="
                                                               + Tags.PATH
                                                               + "."
                                                               + Tags.TASKSERIES_ID,
                                                            
                                                            // group by
                                                            TaskSeries.PATH
                                                               + "."
                                                               + TaskSeries._ID,
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
                                                                        + TaskSeries._ID,
                                                                     Participants.PATH
                                                                        + "."
                                                                        + Participants.TASKSERIES_ID
                                                                        + " AS series_id",
                                                                     "group_concat("
                                                                        + Participants.CONTACT_ID
                                                                        + ",\""
                                                                        + SyncTasks.PARTICIPANTS_DELIMITER
                                                                        + "\") AS "
                                                                        + SyncTasks.PARTICIPANT_IDS,
                                                                     "group_concat("
                                                                        + Participants.FULLNAME
                                                                        + ",\""
                                                                        + SyncTasks.PARTICIPANTS_DELIMITER
                                                                        + "\") AS "
                                                                        + SyncTasks.PARTICIPANT_FULLNAMES,
                                                                     "group_concat("
                                                                        + Participants.USERNAME
                                                                        + ",\""
                                                                        + SyncTasks.PARTICIPANTS_DELIMITER
                                                                        + "\") AS "
                                                                        + SyncTasks.PARTICIPANT_USERNAMES },
                                                                    
                                                                    // where
                                                                    TaskSeries.PATH
                                                                       + "."
                                                                       + TaskSeries._ID
                                                                       + "="
                                                                       + Participants.PATH
                                                                       + "."
                                                                       + Participants.TASKSERIES_ID,
                                                                    
                                                                    // group by
                                                                    TaskSeries.PATH
                                                                       + "."
                                                                       + TaskSeries._ID,
                                                                    null,
                                                                    // order by
                                                                    Participants.DEFAULT_SORT_ORDER,
                                                                    null );
   }
   
   

   public final static List< ContentProviderOperation > insertSyncTask( SyncTask task )
   {
      final List< ContentProviderOperation > operations = new LinkedList< ContentProviderOperation >();
      
      operations.add( ContentProviderOperation.newInsert( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                    task.getTaskSeriesId() ) )
                                              .withValue( TaskSeries._ID,
                                                          task.getTaskSeriesId() )
                                              // .withValue( TaskSeries., arg1 )
                                              .build() );
      
      return operations;
   }
   


   public final static SyncTask getSyncTask( ContentProviderClient client,
                                             String id )
   {
      SyncTask task = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Queries.contentUriWithId( SyncTasks.CONTENT_URI, id ),
                           PROJECTION,
                           null,
                           null,
                           null );
         
         boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            task = createSyncTask( c );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query sync task failed. ", e );
         task = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return task;
   }
   


   public final static List< SyncTask > getSyncTasks( ContentProviderClient client,
                                                      String selection,
                                                      String order )
   {
      List< SyncTask > tasks = null;
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
            tasks = new ArrayList< SyncTask >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final SyncTask task = createSyncTask( c );
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
         Log.e( TAG, "Query sync tasks failed. ", e );
         tasks = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return tasks;
   }
   


   public SyncTasksProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, SyncTasks.PATH );
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
      
      final StringBuilder projectionSB = new StringBuilder();
      
      for ( int i = 0; i < projection.length && !projectionContainsId; i++ )
      {
         final String column = projection[ i ];
         
         // In case of a join with the a table the _id gets ambiguous.
         // So we have to qualify it.
         if ( !projectionContainsId && column.endsWith( SyncTasks._ID ) )
         {
            projectionSB.append( "subQuery." )
                        .append( RawTasks._ID )
                        .append( " AS " )
                        .append( SyncTasks._ID );
            
            projectionList.set( i, projectionSB.toString() );
            projectionContainsId = true;
            
            projectionSB.setLength( 0 );
         }
      }
      
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( SUB_QUERY )
                                                                        .append( ") AS subQuery" );
      // Add tags columns
      stringBuilder.append( " LEFT OUTER JOIN " )
                   .append( "(" )
                   .append( TAGS_SUB_QUERY )
                   .append( ") AS tagsSubQuery ON tagsSubQuery.series_id" )
                   .append( " = subQuery." )
                   .append( SyncTasks.TASKSERIES_ID );
      
      // Add participants columns
      stringBuilder.append( " LEFT OUTER JOIN " )
                   .append( "(" )
                   .append( PARTICIPANTS_SUB_QUERY )
                   .append( ") AS participantsSubQuery ON participantsSubQuery.series_id" )
                   .append( " = subQuery." )
                   .append( SyncTasks.TASKSERIES_ID );
      
      // Only if the ID is given in the projection we can use it
      if ( id != null && projectionContainsId )
      {
         stringBuilder.append( " WHERE subQuery." )
                      .append( RawTasks._ID )
                      .append( " = " )
                      .append( id );
      }
      else
      {
         // TODO: Throw exception in this case otherwise we get a list of all tasks and no error
      }
      
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
      
      final String query = stringBuilder.toString();
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      cursor = db.rawQuery( query, null );
      
      return cursor;
   }
   


   @Override
   protected String getContentItemType()
   {
      return SyncTasks.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return SyncTasks.CONTENT_TYPE;
   }
   


   @Override
   public Uri getContentUri()
   {
      return SyncTasks.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return SyncTasks.DEFAULT_SORT_ORDER;
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
   


   private final static SyncTask createSyncTask( Cursor c )
   {
      final String taskSeriesId = c.getString( COL_INDICES.get( SyncTasks.TASKSERIES_ID ) );
      
      return new SyncTask( c.getString( COL_INDICES.get( SyncTasks._ID ) ),
                           taskSeriesId,
                           Queries.getOptDate( c,
                                               COL_INDICES.get( SyncTasks.TASKSERIES_CREATED_DATE ) ),
                           Queries.getOptDate( c,
                                               COL_INDICES.get( SyncTasks.MODIFIED_DATE ) ),
                           c.getString( COL_INDICES.get( SyncTasks.TASKSERIES_NAME ) ),
                           c.getString( COL_INDICES.get( SyncTasks.SOURCE ) ),
                           Queries.getOptString( c,
                                                 COL_INDICES.get( SyncTasks.URL ) ),
                           Queries.getOptString( c,
                                                 COL_INDICES.get( SyncTasks.RECURRENCE ) ),
                           Queries.getOptBool( c,
                                               COL_INDICES.get( SyncTasks.RECURRENCE_EVERY ),
                                               false ),
                           Queries.getOptString( c,
                                                 COL_INDICES.get( SyncTasks.LOCATION_ID ) ),
                           c.getString( COL_INDICES.get( SyncTasks.LIST_ID ) ),
                           Queries.getOptDate( c,
                                               COL_INDICES.get( SyncTasks.DUE_DATE ) ),
                           c.getInt( COL_INDICES.get( SyncTasks.HAS_DUE_TIME ) ),
                           Queries.getOptDate( c,
                                               COL_INDICES.get( SyncTasks.ADDED_DATE ) ),
                           Queries.getOptDate( c,
                                               COL_INDICES.get( SyncTasks.COMPLETED_DATE ) ),
                           Queries.getOptDate( c,
                                               COL_INDICES.get( SyncTasks.DELETED_DATE ) ),
                           RtmTask.convertPriority( c.getString( COL_INDICES.get( SyncTasks.PRIORITY ) ) ),
                           c.getInt( COL_INDICES.get( SyncTasks.POSTPONED ) ),
                           Queries.getOptString( c,
                                                 COL_INDICES.get( SyncTasks.ESTIMATE ) ),
                           Queries.getOptLong( c,
                                               COL_INDICES.get( SyncTasks.ESTIMATE_MILLIS ),
                                               0 ),
                           Queries.getOptString( c,
                                                 COL_INDICES.get( SyncTasks.TAGS ) ),
                           createPartitiansList( taskSeriesId, c ) );
   }
   


   public final static ParticipantList createPartitiansList( String taskSeriesId,
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
                  participantList.addParticipant( new Participant( null,
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
}
