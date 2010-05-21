package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.content.provider.Rtm;
import dev.drsoran.moloko.content.provider.Rtm.Tasks;


public class RtmTasksProviderPart implements IRtmProviderPart
{
   private static final String TAG = RtmTasksProviderPart.class.getSimpleName();
   
   private static final String TABLE_NAME = "tasks";
   
   private static final String TASK_ID_EQUALS = Tasks._ID
                                                + "=";
   
   private static final int TASKS = 1;
   
   private static final int TASK_ID = 2;
   
   private static final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
   
   private static final HashMap< String, String > projectionMap = new HashMap< String, String >();
   
   static
   {
      uriMatcher.addURI( Rtm.AUTHORITY, "tasks", TASKS );
      uriMatcher.addURI( Rtm.AUTHORITY, "tasks/#", TASK_ID );
      
      projectionMap.put( Tasks._ID, Tasks._ID );
      projectionMap.put( Tasks.DUE_DATE, Tasks.DUE_DATE );
      projectionMap.put( Tasks.ADDED_DATE, Tasks.ADDED_DATE );
      projectionMap.put( Tasks.COMPLETED_DATE, Tasks.COMPLETED_DATE );
      projectionMap.put( Tasks.DELETED_DATE, Tasks.DELETED_DATE );
      projectionMap.put( Tasks.PRIORITY, Tasks.PRIORITY );
      projectionMap.put( Tasks.POSTPONED, Tasks.POSTPONED );
      projectionMap.put( Tasks.ESTIMATE, Tasks.ESTIMATE );
   }
   
   private SQLiteOpenHelper dbAccess = null;
   
   

   public RtmTasksProviderPart( SQLiteOpenHelper dbAccess )
   {
      this.dbAccess = dbAccess;
   }
   


   public static RtmTasksProviderPart create( SQLiteOpenHelper dbAccess ) throws SQLException
   {
      dbAccess.getWritableDatabase().execSQL( "CREATE TABLE "
                                              + TABLE_NAME + " (" + Tasks._ID + " INTEGER PRIMARY KEY,"
                                              + Tasks.DUE_DATE + " INTEGER" + Tasks.ADDED_DATE + " INTEGER"
                                              + Tasks.COMPLETED_DATE + " INTEGER" + Tasks.DELETED_DATE + " INTEGER"
                                              + Tasks.PRIORITY + " INTEGER CHECK ( " + Tasks.PRIORITY
                                              + " BETWEEN 0 AND 3 " + ")" + Tasks.POSTPONED + " INTEGER DEFAULT 0"
                                              + Tasks.ESTIMATE + " TEXT" + ");" );
      return new RtmTasksProviderPart( dbAccess );
   }
   


   public static RtmTasksProviderPart upgrade( SQLiteOpenHelper dbAccess, int oldVersion, int newVersion ) throws SQLException
   {
      Log.w( TAG, "Upgrading database from version "
                  + oldVersion + " to " + newVersion + ", which will destroy all old data" );
      
      dbAccess.getWritableDatabase().execSQL( "DROP TABLE IF EXISTS "
                                              + TABLE_NAME );
      
      return create( dbAccess );
   }
   


   @Override
   public Cursor query( String id, String[] projection, String selection, String[] selectionArgs, String sortOrder )
   {
      Cursor cursor = null;
      
      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
      qb.setTables( TABLE_NAME );
      qb.setProjectionMap( projectionMap );
      
      if ( id != null )
      {
         qb.appendWhere( TASK_ID_EQUALS
                         + id );
      }
      
      // If no sort order is specified use the default
      String orderBy = null;
      if ( TextUtils.isEmpty( sortOrder ) )
      {
         orderBy = Tasks.DEFAULT_SORT_ORDER;
      }
      else
      {
         orderBy = sortOrder;
      }
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      cursor = qb.query( db, projection, selection, selectionArgs, null, null, orderBy );
      
      return cursor;
   }
   


   @Override
   public Uri insert( ContentValues initialValues )
   {
      if ( initialValues != null
           && initialValues.containsKey( Rtm.Tasks._ID ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         
         // Make sure that the fields are all set
         if ( initialValues.containsKey( Rtm.Tasks.ADDED_DATE ) == false )
         {
            initialValues.put( Tasks.ADDED_DATE, now );
         }
         
         SQLiteDatabase db = dbAccess.getWritableDatabase();
         long rowId = db.insert( TABLE_NAME, Tasks._ID, initialValues );
         
         if ( rowId > 0 )
         {
            return ContentUris.withAppendedId( Rtm.Tasks.CONTENT_URI, rowId );
         }
      }
      
      throw new SQLException( "Failed to insert row" );
   }
   


   @Override
   public int update( String id, ContentValues values, String where, String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.update( TABLE_NAME, values, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( TASK_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.update( TABLE_NAME, values, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   @Override
   public int delete( String id, String where, String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.delete( TABLE_NAME, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( TASK_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.delete( TABLE_NAME, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   @Override
   public String getTableName()
   {
      return TABLE_NAME;
   }
   


   @Override
   public UriMatcher getUriMatcher()
   {
      return uriMatcher;
   }
   


   @Override
   public int matchUri( Uri uri )
   {
      switch ( uriMatcher.match( uri ) )
      {
         case TASKS:
            return MATCH_TYPE_DIR;
         case TASK_ID:
            return MATCH_TYPE_ITEM;
         default:
            return UriMatcher.NO_MATCH;
      }
   }
   


   @Override
   public String getIdSegement( Uri uri )
   {
      return uri.getPathSegments().get( 1 );
   }
   


   @Override
   public HashMap< String, String > getProjection()
   {
      return projectionMap;
   }
   
}
