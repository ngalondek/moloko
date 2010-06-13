package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.provider.Rtm.Notes;


public class RtmNotesProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmNotesProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Notes._ID, Notes.NOTE_CREATED_DATE, Notes.NOTE_MODIFIED_DATE,
    Notes.NOTE_TITLE, Notes.NOTE_TEXT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( RtmTaskNote note,
                                                       boolean withId )
   {
      ContentValues values = new ContentValues();
      
      if ( withId )
         values.put( Notes._ID, note.getId() );
      
      if ( note.getCreated() != null )
         values.put( Notes.NOTE_CREATED_DATE, note.getCreated().getTime() );
      else
         values.putNull( Notes.NOTE_CREATED_DATE );
      
      if ( note.getModified() != null )
         values.put( Notes.NOTE_MODIFIED_DATE, note.getModified().getTime() );
      else
         values.putNull( Notes.NOTE_MODIFIED_DATE );
      
      values.put( Notes.NOTE_TITLE, note.getTitle() );
      values.put( Notes.NOTE_TEXT, note.getText() );
      
      return values;
   }
   


   public final static ContentProviderOperation insertNote( ContentProviderClient client,
                                                            RtmTaskNote note ) throws RemoteException
   {
      ContentProviderOperation operation = null;
      
      // Only insert if not exists
      boolean ok = !Queries.exists( client, Notes.CONTENT_URI, note.getId() );
      
      // Check mandatory attributes
      ok = ok && note.getId() != null;
      ok = ok && note.getTitle() != null;
      ok = ok && note.getText() != null;
      
      if ( ok )
      {
         operation = ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                             .withValues( getContentValues( note,
                                                                            true ) )
                                             .build();
      }
      
      return operation;
   }
   


   public RtmNotesProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Notes.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Notes._ID
         + " INTEGER NOT NULL, " + Notes.NOTE_CREATED_DATE
         + " INTEGER NOT NULL, " + Notes.NOTE_MODIFIED_DATE + " INTEGER, "
         + Notes.NOTE_TITLE + " NOTE_TEXT NOT NULL, " + Notes.NOTE_TEXT
         + " NOTE_TEXT NOT NULL, " + "CONSTRAINT PK_NOTES PRIMARY KEY ( \""
         + Notes._ID + "\" )" + " );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      if ( !initialValues.containsKey( Notes.NOTE_CREATED_DATE ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         initialValues.put( Notes.NOTE_CREATED_DATE, now );
      }
      
      return initialValues;
   }
   


   @Override
   protected String getContentItemType()
   {
      return Notes.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Notes.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Notes.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Notes.DEFAULT_SORT_ORDER;
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
