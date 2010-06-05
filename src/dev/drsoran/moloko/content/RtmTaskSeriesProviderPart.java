package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderClient;
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
import dev.drsoran.provider.Rtm.NoteRefs;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmTaskSeriesProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTaskSeriesProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( TaskSeries._ID, 0 );
      COL_INDICES.put( TaskSeries.CREATED_DATE, 1 );
      COL_INDICES.put( TaskSeries.MODIFIED_DATE, 2 );
      COL_INDICES.put( TaskSeries.NAME, 3 );
      COL_INDICES.put( TaskSeries.SOURCE, 4 );
      COL_INDICES.put( TaskSeries.URL, 5 );
      COL_INDICES.put( TaskSeries.TASK_ID, 6 );
      COL_INDICES.put( TaskSeries.LOCATION_ID, 7 );
      COL_INDICES.put( TaskSeries.LIST_ID, 8 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
   }
   
   

   public final static RtmTasks getAllTaskSeries( ContentProviderClient client ) throws RemoteException
   {
      RtmTasks tasksLists = null;
      
      // We query all TaskSeries rows and sort them by their list ID.
      // So we have all lists with their tasks together.
      final Cursor c = client.query( Rtm.TaskSeries.CONTENT_URI,
                                     null,
                                     null,
                                     null,
                                     TaskSeries.LIST_ID );
      
      boolean ok = c != null;
      
      if ( ok )
      {
         tasksLists = new RtmTasks();
         RtmTaskList taskList = null;
         
         for ( ok = ok && c.moveToFirst(); ok && !c.isAfterLast(); ok = ok
            && c.moveToNext() )
         {
            {
               final String currentTaskListId = c.getString( COL_INDICES.get( TaskSeries.LIST_ID ) );
               
               // Check if we have reached a new task list
               if ( taskList == null
                  || !taskList.getId().equals( currentTaskListId ) )
               {
                  // store current task list and create a new one
                  if ( taskList != null )
                  {
                     ok = tasksLists.add( taskList );
                  }
                  
                  taskList = new RtmTaskList( currentTaskListId );
               }
            }
            
            RtmTask task = null;
            
            if ( ok )
            {
               task = RtmTasksProviderPart.getTask( client,
                                                    c.getString( COL_INDICES.get( TaskSeries.TASK_ID ) ) );
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
                                                               Tags.CONTENT_URI,
                                                               tagRef.getTagId() );
                     
                     ok = tagCursor != null;
                     ok = ok
                        && tags.add( c.getString( RtmTagsProviderPart.COL_INDICES.get( Tags.TAG ) ) );
                     
                     if ( tagCursor != null )
                        tagCursor.close();
                  }
               }
            }
            
            RtmTaskNotes notes = null;
            
            if ( ok )
            {
               // Get all notes this taskseries references
               final List< NoteRef > noteRefs = NoteRefsProviderPart.getNoteRefs( client,
                                                                                  taskSeriesId );
               // If the taskseries has no tags, we get an empty list, but not null
               ok = noteRefs != null;
               
               if ( ok && noteRefs.size() > 0 )
               {
                  final ArrayList< RtmTaskNote > notesList = new ArrayList< RtmTaskNote >();
                  
                  for ( Iterator< NoteRef > i = noteRefs.iterator(); ok
                     && i.hasNext(); )
                  {
                     final NoteRef noteRef = i.next();
                     final Cursor noteCursor = Queries.getItem( client,
                                                                NoteRefs.CONTENT_URI,
                                                                noteRef.getNoteId() );
                     
                     ok = noteCursor != null;
                     
                     if ( ok )
                     {
                        Date modified = null;
                        
                        if ( !c.isNull( RtmNotesProviderPart.COL_INDICES.get( Notes.MODIFIED_DATE ) ) )
                           modified = new Date( c.getLong( RtmNotesProviderPart.COL_INDICES.get( Notes.MODIFIED_DATE ) ) );
                        
                        ok = notesList.add( new RtmTaskNote( c.getString( RtmNotesProviderPart.COL_INDICES.get( Notes._ID ) ),
                                                             new Date( c.getLong( RtmNotesProviderPart.COL_INDICES.get( Notes.CREATED_DATE ) ) ),
                                                             modified,
                                                             c.getString( RtmNotesProviderPart.COL_INDICES.get( Notes.TITLE ) ),
                                                             c.getString( RtmNotesProviderPart.COL_INDICES.get( Notes.TEXT ) ) ) );
                     }
                     
                     if ( noteCursor != null )
                        noteCursor.close();
                  }
                  
                  if ( ok )
                  {
                     notes = new RtmTaskNotes( notesList );
                  }
               }
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
                                                                   new Date( c.getLong( COL_INDICES.get( TaskSeries.CREATED_DATE ) ) ),
                                                                   modifiedDate,
                                                                   c.getString( COL_INDICES.get( TaskSeries.NAME ) ),
                                                                   Queries.getOptString( c,
                                                                                         COL_INDICES.get( TaskSeries.SOURCE ) ),
                                                                   task,
                                                                   notes,
                                                                   locationId,
                                                                   Queries.getOptString( c,
                                                                                         COL_INDICES.get( TaskSeries.URL ) ),
                                                                   tags );
               ok = taskList.add( taskSeries );
            }
         }
      }
      
      if ( c != null )
         c.close();
      
      return tasksLists;
   }
   


   public RtmTaskSeriesProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "taskseries" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + TaskSeries._ID
         + " INTEGER NOT NULL, " + TaskSeries.CREATED_DATE
         + " INTEGER NOT NULL, " + TaskSeries.MODIFIED_DATE + " INTEGER, "
         + TaskSeries.NAME + " TEXT NOT NULL, " + TaskSeries.SOURCE + " TEXT, "
         + TaskSeries.URL + " TEXT, " + TaskSeries.TASK_ID
         + " INTEGER NOT NULL, " + TaskSeries.LOCATION_ID + " INTEGER, "
         + TaskSeries.LIST_ID + " INTEGER NOT NULL, "
         + "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \"" + TaskSeries._ID
         + "\" ), " + "CONSTRAINT list FOREIGN KEY ( " + TaskSeries.LIST_ID
         + " ) REFERENCES lists ( \"" + Lists._ID + "\" ), "
         + "CONSTRAINT location FOREIGN KEY ( " + TaskSeries.LOCATION_ID
         + " ) REFERENCES locations ( \"" + Locations._ID + "\" ),"
         + "CONSTRAINT task FOREIGN KEY ( " + TaskSeries.TASK_ID
         + " ) REFERENCES tasks ( \"" + Tasks._ID + "\" ) );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      if ( !initialValues.containsKey( TaskSeries.CREATED_DATE ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         initialValues.put( TaskSeries.CREATED_DATE, now );
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
}
