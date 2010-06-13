package dev.drsoran.moloko.content;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;


public abstract class AbstractRtmProviderPart extends AbstractProviderPart
         implements IRtmProviderPart
{
   
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTasksProviderPart.class.getSimpleName();
   
   

   public AbstractRtmProviderPart( SQLiteOpenHelper dbAccess, String tableName )
   {
      super( dbAccess, tableName );
   }
   


   public void upgrade( SQLiteDatabase db, int oldVersion, int newVersion ) throws SQLException
   {
      Log.w( path, "Upgrading database from version " + oldVersion + " to "
         + newVersion + ", which will destroy all old data" );
      
      drop( db );
      create( db );
   }
   


   public void drop( SQLiteDatabase db )
   {
      db.execSQL( "DROP TABLE IF EXISTS " + path );
   }
   


   public Uri insert( ContentValues initialValues )
   {
      Uri uri = null;
      
      if ( initialValues != null && initialValues.containsKey( BaseColumns._ID ) )
      {
         initialValues = getInitialValues( initialValues );
         
         final SQLiteDatabase db = dbAccess.getWritableDatabase();
         final long rowId = db.insert( path, BaseColumns._ID, initialValues );
         
         if ( rowId > 0 )
         {
            uri = ContentUris.withAppendedId( getContentUri(), rowId );
         }
      }
      
      return uri;
   }
   


   public int update( String id,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.update( path, values, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.update( path, values, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   public int delete( String id, String where, String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.delete( path, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.delete( path, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   public String getTableName()
   {
      return path;
   }
   


   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      return initialValues;
   }
}
