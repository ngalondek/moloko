package dev.drsoran.moloko.content;

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
   protected void fillProjectionMap()
   {
      projectionMap.put( Notes._ID, Notes._ID );
      projectionMap.put( Notes.CREATED_DATE, Notes.CREATED_DATE );
      projectionMap.put( Notes.MODIFIED_DATE, Notes.MODIFIED_DATE );
      projectionMap.put( Notes.TITLE, Notes.TITLE );
      projectionMap.put( Notes.TEXT, Notes.TEXT );
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
}
