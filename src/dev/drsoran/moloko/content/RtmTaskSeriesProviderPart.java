package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmTaskSeriesProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTaskSeriesProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { TaskSeries._ID, TaskSeries.TASKSERIES_CREATED_DATE,
    TaskSeries.MODIFIED_DATE, TaskSeries.TASKSERIES_NAME, TaskSeries.SOURCE,
    TaskSeries.URL, TaskSeries.RAW_TASK_ID, TaskSeries.LOCATION_ID,
    TaskSeries.LIST_ID };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( RtmTaskSeries taskSeries,
                                                       String listId,
                                                       boolean withId )
   {
      final ContentValues values = new ContentValues();
      
      if ( withId )
         values.put( TaskSeries._ID, taskSeries.getId() );
      
      if ( taskSeries.getCreated() != null )
         values.put( TaskSeries.TASKSERIES_CREATED_DATE,
                     taskSeries.getCreated().getTime() );
      else
         values.putNull( TaskSeries.TASKSERIES_CREATED_DATE );
      
      if ( taskSeries.getModified() != null )
         values.put( TaskSeries.MODIFIED_DATE, taskSeries.getModified()
                                                         .getTime() );
      else
         values.putNull( TaskSeries.MODIFIED_DATE );
      
      values.put( TaskSeries.TASKSERIES_NAME, taskSeries.getName() );
      
      if ( taskSeries.getSource() != null )
         values.put( TaskSeries.SOURCE, taskSeries.getSource() );
      else
         values.putNull( TaskSeries.SOURCE );
      
      if ( taskSeries.getURL() != null )
         values.put( TaskSeries.URL, taskSeries.getURL() );
      else
         values.putNull( TaskSeries.URL );
      
      values.put( TaskSeries.RAW_TASK_ID, taskSeries.getTask().getId() );
      
      if ( taskSeries.getLocationId() != null )
         values.put( TaskSeries.LOCATION_ID, taskSeries.getLocationId() );
      else
         values.putNull( TaskSeries.LOCATION_ID );
      
      values.put( TaskSeries.LIST_ID, listId );
      
      return values;
   }
   


   public final static RtmTasks getAllTaskSeries( ContentProviderClient client )
   {
      RtmTasks tasksLists = null;
      
      // We query all TaskSeries rows and sort them by their list ID.
      // So we have all lists with their tasks together.
      try
      {
         final Cursor c = client.query( Rtm.TaskSeries.CONTENT_URI,
                                        PROJECTION,
                                        null,
                                        null,
                                        TaskSeries.LIST_ID );
         
         tasksLists = new RtmTasks();
         
         boolean ok = true;
         
         if ( c.getCount() > 0 )
         {
            RtmTaskList taskList = null;
            
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               final String currentTaskListId = c.getString( COL_INDICES.get( TaskSeries.LIST_ID ) );
               
               // Check if we have reached a new task list
               if ( taskList == null
                  || !taskList.getId().equals( currentTaskListId ) )
               {
                  taskList = new RtmTaskList( currentTaskListId );
                  tasksLists.add( taskList );
               }
               
               RtmTask task = null;
               
               if ( ok )
               {
                  task = RtmTasksProviderPart.getTask( client,
                                                       c.getString( COL_INDICES.get( TaskSeries.RAW_TASK_ID ) ) );
                  ok = task != null;
               }
               
               String taskSeriesId = null;
               
               if ( ok )
               {
                  taskSeriesId = c.getString( COL_INDICES.get( TaskSeries._ID ) );
               }
               
               List< String > tags = null;
               
               if ( ok )
               {
                  // Get all tags this taskseries references
                  final List< TagRef > tagRefs = TagRefsProviderPart.getTagRefs( client,
                                                                                 taskSeriesId );
                  // If the taskseries has no tags, we get an empty list, but not null
                  ok = tagRefs != null;
                  
                  if ( ok && tagRefs.size() > 0 )
                  {
                     tags = new ArrayList< String >();
                     
                     for ( Iterator< TagRef > i = tagRefs.iterator(); ok
                        && i.hasNext(); )
                     {
                        final TagRef tagRef = i.next();
                        final Cursor tagCursor = Queries.getItem( client,
                                                                  RtmTagsProviderPart.PROJECTION,
                                                                  Tags.CONTENT_URI,
                                                                  tagRef.getTagId() );
                        
                        ok = tagCursor != null;
                        if ( ok )
                           tags.add( c.getString( RtmTagsProviderPart.COL_INDICES.get( Tags.TAG ) ) );
                        
                        if ( tagCursor != null )
                           tagCursor.close();
                     }
                  }
               }
               
               RtmTaskNotes notes = null;
               
               if ( ok )
               {
                  // Get all notes this taskseries references
                  notes = RtmNotesProviderPart.getAllNotes( client,
                                                            taskSeriesId );
                  
                  // If the taskseries has no notes, we get an empty list, but not null
                  ok = notes != null;
               }
               
               if ( ok )
               {
                  Date modifiedDate = null;
                  String locationId = null;
                  
                  // Check if this taskseries has an modified date
                  if ( !c.isNull( COL_INDICES.get( TaskSeries.MODIFIED_DATE ) ) )
                  {
                     modifiedDate = new Date( c.getLong( COL_INDICES.get( TaskSeries.MODIFIED_DATE ) ) );
                  }
                  
                  // Check if this taskseries has a location
                  if ( !c.isNull( COL_INDICES.get( TaskSeries.LOCATION_ID ) ) )
                  {
                     locationId = Long.toString( c.getLong( COL_INDICES.get( TaskSeries.LOCATION_ID ) ) );
                  }
                  
                  // add the current task series to the task list.
                  final RtmTaskSeries taskSeries = new RtmTaskSeries( taskSeriesId,
                                                                      new Date( c.getLong( COL_INDICES.get( TaskSeries.TASKSERIES_CREATED_DATE ) ) ),
                                                                      modifiedDate,
                                                                      c.getString( COL_INDICES.get( TaskSeries.TASKSERIES_NAME ) ),
                                                                      Queries.getOptString( c,
                                                                                            COL_INDICES.get( TaskSeries.SOURCE ) ),
                                                                      task,
                                                                      notes,
                                                                      locationId,
                                                                      Queries.getOptString( c,
                                                                                            COL_INDICES.get( TaskSeries.URL ) ),
                                                                      tags );
                  taskList.add( taskSeries );
               }
            }
         }
         
         if ( !ok )
            tasksLists = null;
         
         c.close();
      }
      catch ( RemoteException e )
      {
         tasksLists = null;
      }
      
      return tasksLists;
   }
   


   public final static ArrayList< ContentProviderOperation > insertTaskSeries( ContentProviderClient client,
                                                                               String listId,
                                                                               RtmTaskSeries taskSeries ) throws RemoteException
   {
      ArrayList< ContentProviderOperation > operations = null;
      
      final String taskSeriesId = taskSeries.getId();
      
      boolean ok = taskSeriesId != null;
      
      // Only insert if does not exists
      ok = ok && !Queries.exists( client, TaskSeries.CONTENT_URI, taskSeriesId );
      
      // Check mandatory values
      ok = ok && taskSeries.getName() != null;
      ok = ok && taskSeries.getTask() != null;
      ok = ok && listId != null;
      
      if ( ok )
      {
         operations = new ArrayList< ContentProviderOperation >();
         
         // Insert a new RtmTask
         {
            final ContentProviderOperation inserTaskOp = RtmTasksProviderPart.insertTask( client,
                                                                                          taskSeries.getTask() );
            ok = inserTaskOp != null;
            if ( ok )
               operations.add( inserTaskOp );
         }
         
         // TODO: Handle tags
         /*
          * Check for tags if ( ok ) { final List< String > tags = taskSeries.getTags();
          * 
          * if ( tags != null && tags.size() > 0 ) { for ( final String tag : tags ) { // Check if we already have the
          * tag final String tagId = RtmTagsProviderPart.getMatchingTagId( client, tag );
          * 
          * ArrayList< ContentProviderOperation > tagOperations = new ArrayList< ContentProviderOperation >();
          * 
          * // if not found, create a new tag if ( tagId == null ) { final ContentProviderOperation tagOperation =
          * RtmTagsProviderPart.insertTag( client, tag ); ok = tagOperation != null; ok = ok && tagOperations.add(
          * tagOperation );
          * 
          * }
          * 
          * // link to taskseries if ( ok ) { final ContentProviderOperation tagOperation =
          * TagRefsProviderPart.addTagToTaskSeries( client, taskSeriesId, tagId ); ok = tagOperation != null; ok = ok &&
          * tagOperations.add( tagOperation ); }
          * 
          * ok = ok && operations.addAll( tagOperations ); } } }
          */

         // Check for notes
         if ( ok )
         {
            final RtmTaskNotes notes = taskSeries.getNotes();
            
            if ( notes != null )
            {
               final List< RtmTaskNote > notesList = notes.getNotes();
               
               if ( notesList != null && notesList.size() > 0 )
               {
                  for ( RtmTaskNote rtmTaskNote : notesList )
                  {
                     final ContentProviderOperation noteOperation = RtmNotesProviderPart.insertNote( client,
                                                                                                     rtmTaskNote,
                                                                                                     taskSeriesId );
                     
                     ok = noteOperation != null;
                     if ( ok )
                        operations.add( noteOperation );
                  }
               }
            }
         }
         
         // Insert new taskseries
         if ( ok )
         {
            operations.add( ContentProviderOperation.newInsert( TaskSeries.CONTENT_URI )
                                                    .withValues( getContentValues( taskSeries,
                                                                                   listId,
                                                                                   true ) )
                                                    .build() );
         }
      }
      
      return operations;
   }
   


   public RtmTaskSeriesProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, TaskSeries.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + TaskSeries._ID
         + " INTEGER NOT NULL, " + TaskSeries.TASKSERIES_CREATED_DATE
         + " INTEGER NOT NULL, " + TaskSeries.MODIFIED_DATE + " INTEGER, "
         + TaskSeries.TASKSERIES_NAME + " NOTE_TEXT NOT NULL, "
         + TaskSeries.SOURCE + " NOTE_TEXT, " + TaskSeries.URL + " NOTE_TEXT, "
         + TaskSeries.RAW_TASK_ID + " INTEGER NOT NULL, "
         + TaskSeries.LOCATION_ID + " INTEGER, " + TaskSeries.LIST_ID
         + " INTEGER NOT NULL, " + "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \""
         + TaskSeries._ID + "\" ), " + "CONSTRAINT list FOREIGN KEY ( "
         + TaskSeries.LIST_ID + " ) REFERENCES lists ( \"" + Lists._ID
         + "\" ), " + "CONSTRAINT location FOREIGN KEY ( "
         + TaskSeries.LOCATION_ID + " ) REFERENCES locations ( \""
         + Locations._ID + "\" )," + "CONSTRAINT task FOREIGN KEY ( "
         + TaskSeries.RAW_TASK_ID + " ) REFERENCES tasks ( \"" + RawTasks._ID
         + "\" ) );" );
      
      // Trigger: If a taskseries gets deleted, we also delete all raw tasks
      // and notes referenced by this taskseries
      db.execSQL( "CREATE TRIGGER " + path
         + "_delete_taskseries AFTER DELETE ON " + path
         + " FOR EACH ROW BEGIN DELETE " + RawTasks.PATH + " WHERE "
         + RawTasks.PATH + "._id = old." + TaskSeries._ID + "; DELETE "
         + Notes.PATH + " WHERE " + Notes.TASKSERIES_ID + " = old."
         + TaskSeries._ID + " END;" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      if ( !initialValues.containsKey( TaskSeries.TASKSERIES_CREATED_DATE ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         initialValues.put( TaskSeries.TASKSERIES_CREATED_DATE, now );
      }
      
      return initialValues;
   }
   


   @Override
   protected String getContentItemType()
   {
      return TaskSeries.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return TaskSeries.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return TaskSeries.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return TaskSeries.DEFAULT_SORT_ORDER;
   }
   


   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   


   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   


   public String[] getProjection()
   {
      return PROJECTION;
   }
}
