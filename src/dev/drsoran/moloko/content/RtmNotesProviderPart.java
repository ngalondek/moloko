package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Notes;


public class RtmNotesProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmNotesProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( Notes._ID, 0 );
      COL_INDICES.put( Notes.CREATED_DATE, 1 );
      COL_INDICES.put( Notes.MODIFIED_DATE, 2 );
      COL_INDICES.put( Notes.TITLE, 3 );
      COL_INDICES.put( Notes.TEXT, 4 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
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
}
