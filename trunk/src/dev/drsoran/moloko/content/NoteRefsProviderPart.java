package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.NoteRefs;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;


public class NoteRefsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = NoteRefsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( NoteRefs._ID, 0 );
      COL_INDICES.put( NoteRefs.TASKSERIES_ID, 1 );
      COL_INDICES.put( NoteRefs.NOTE_ID, 2 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
   }
   
   

   public final static List< NoteRef > getNoteRefs( ContentProviderClient client,
                                                    String taskSeriesId ) throws RemoteException
   {
      ArrayList< NoteRef > noteRefs = null;
      
      final Cursor c = client.query( Rtm.NoteRefs.CONTENT_URI,
                                     null,
                                     NoteRefs.TASKSERIES_ID + "="
                                        + taskSeriesId,
                                     null,
                                     null );
      
      boolean ok = c != null;
      
      if ( ok )
      {
         noteRefs = new ArrayList< NoteRef >();
         
         for ( ok = ok && c.moveToFirst(); ok && !c.isAfterLast(); ok = ok
            && c.moveToNext() )
         {
            ok = noteRefs.add( new NoteRef( c.getString( COL_INDICES.get( NoteRefs._ID ) ),
                                            c.getString( COL_INDICES.get( NoteRefs.TASKSERIES_ID ) ),
                                            c.getString( COL_INDICES.get( NoteRefs.NOTE_ID ) ) ) );
         }
      }
      
      if ( c != null )
         c.close();
      
      return noteRefs;
   }
   


   public NoteRefsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "noterefs" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + NoteRefs._ID
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
      return Tags.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Tags.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Tags.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Tags.DEFAULT_SORT_ORDER;
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
