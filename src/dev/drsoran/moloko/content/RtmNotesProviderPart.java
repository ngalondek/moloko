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
   { Notes._ID, Notes.CREATED_DATE, Notes.MODIFIED_DATE, Notes.TITLE,
    Notes.TEXT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
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
         ContentValues values = new ContentValues();
         
         values.put( Notes._ID, note.getId() );
         
         if ( note.getCreated() != null )
            values.put( Notes.CREATED_DATE, note.getCreated().getTime() );
         if ( note.getModified() != null )
            values.put( Notes.MODIFIED_DATE, note.getModified().getTime() );
         values.put( Notes.TITLE, note.getTitle() );
         values.put( Notes.TEXT, note.getText() );
         
         operation = ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                             .withValues( values )
                                             .build();
      }
      
      return operation;
   }
   


   public RtmNotesProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "notes" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Notes._ID
         + " INTEGER NOT NULL, " + Notes.CREATED_DATE + " INTEGER NOT NULL, "
         + Notes.MODIFIED_DATE + " INTEGER, " + Notes.TITLE
         + " TEXT NOT NULL, " + Notes.TEXT + " TEXT NOT NULL, "
         + "CONSTRAINT PK_NOTES PRIMARY KEY ( \"" + Notes._ID + "\" )" + " );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      if ( !initialValues.containsKey( Notes.CREATED_DATE ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         initialValues.put( Notes.CREATED_DATE, now );
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
