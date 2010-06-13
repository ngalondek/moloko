package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.NoteRefs;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.TaskSeries;


public class NoteRefsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = NoteRefsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { NoteRefs._ID, NoteRefs.TASKSERIES_ID, NoteRefs.NOTE_ID };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentProviderOperation addNoteToTaskSeries( ContentProviderClient client,
                                                                     String taskSeriesId,
                                                                     String noteId )
   {
      ContentProviderOperation operation = null;
      
      // Check that booth exist
      try
      {
         if ( Queries.exists( client, TaskSeries.CONTENT_URI, taskSeriesId )
            && Queries.exists( client, Notes.CONTENT_URI, noteId )
            && taskSeriesId != null && noteId != null )
         {
            operation = ContentProviderOperation.newInsert( NoteRefs.CONTENT_URI )
                                                .withValue( NoteRefs.TASKSERIES_ID,
                                                            taskSeriesId )
                                                .withValue( NoteRefs.NOTE_ID,
                                                            noteId )
                                                .build();
         }
      }
      catch ( RemoteException e )
      {
         operation = null;
      }
      
      return operation;
   }
   


   public final static List< NoteRef > getNoteRefs( ContentProviderClient client,
                                                    String taskSeriesId )
   {
      ArrayList< NoteRef > noteRefs = null;
      
      try
      {
         final Cursor c = client.query( Rtm.NoteRefs.CONTENT_URI,
                                        PROJECTION,
                                        NoteRefs.TASKSERIES_ID + "="
                                           + taskSeriesId,
                                        null,
                                        null );
         
         noteRefs = new ArrayList< NoteRef >();
         
         boolean ok = true;
         
         if ( c.getCount() > 0 )
         {
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               noteRefs.add( new NoteRef( c.getString( COL_INDICES.get( NoteRefs._ID ) ),
                                          c.getString( COL_INDICES.get( NoteRefs.TASKSERIES_ID ) ),
                                          c.getString( COL_INDICES.get( NoteRefs.NOTE_ID ) ) ) );
            }
         }
         if ( !ok )
            noteRefs = null;
         
         c.close();
      }
      catch ( RemoteException e )
      {
         noteRefs = null;
      }
      
      return noteRefs;
   }
   


   public NoteRefsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, NoteRefs.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + NoteRefs._ID
         + " INTEGER NOT NULL, " + NoteRefs.TASKSERIES_ID
         + " INTEGER NOT NULL, " + NoteRefs.NOTE_ID + " INTEGER NOT NULL, "
         + "CONSTRAINT PK_NOTEREFS PRIMARY KEY ( " + NoteRefs.TASKSERIES_ID
         + ", " + NoteRefs.NOTE_ID + ", " + NoteRefs._ID + "), "
         + "CONSTRAINT note FOREIGN KEY ( " + NoteRefs.NOTE_ID
         + " ) REFERENCES notes ( " + Notes._ID + "), "
         + "CONSTRAINT taskseries_2 FOREIGN KEY ( " + NoteRefs.TASKSERIES_ID
         + " ) REFERENCES taskseries ( " + TaskSeries._ID + " ) );" );
   }
   


   @Override
   protected String getContentItemType()
   {
      return NoteRefs.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return NoteRefs.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return NoteRefs.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return NoteRefs.DEFAULT_SORT_ORDER;
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
