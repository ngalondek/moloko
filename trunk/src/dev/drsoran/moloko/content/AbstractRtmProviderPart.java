package dev.drsoran.moloko.content;

import java.util.Collection;
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
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.provider.Rtm;


public abstract class AbstractRtmProviderPart implements IRtmProviderPart
{
   
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTasksProviderPart.class.getSimpleName();
   
   protected static final String ITEM_ID_EQUALS = BaseColumns._ID + "=";
   
   protected final String tableName;
   
   protected static final int DIR = 1;
   
   protected static final int ITEM_ID = 2;
   
   protected final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
   
   protected SQLiteOpenHelper dbAccess = null;
   
   

   public AbstractRtmProviderPart( SQLiteOpenHelper dbAccess, String tableName )
   {
      this.dbAccess = dbAccess;
      this.tableName = tableName;
      
      uriMatcher.addURI( Rtm.AUTHORITY, tableName, DIR );
      uriMatcher.addURI( Rtm.AUTHORITY, tableName + "/#", ITEM_ID );
   }
   


   public void upgrade( SQLiteDatabase db, int oldVersion, int newVersion ) throws SQLException
   {
      Log.w( tableName, "Upgrading database from version " + oldVersion
         + " to " + newVersion + ", which will destroy all old data" );
      
      drop( db );
      create( db );
   }
   


   public void drop( SQLiteDatabase db )
   {
      db.execSQL( "DROP TABLE IF EXISTS " + tableName );
   }
   


   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      Cursor cursor = null;
      
      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
      qb.setTables( tableName );
      qb.setProjectionMap( getProjectionMap() );
      
      if ( id != null )
      {
         qb.appendWhere( ITEM_ID_EQUALS + id );
      }
      
      // If no sort order is specified use the default
      String orderBy = getDefaultSortOrder();
      
      if ( !TextUtils.isEmpty( sortOrder ) )
      {
         orderBy = sortOrder;
      }
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      cursor = qb.query( db,
                         projection,
                         selection,
                         selectionArgs,
                         null,
                         null,
                         orderBy );
      
      return cursor;
   }
   


   public Uri insert( ContentValues initialValues )
   {
      Uri uri = null;
      
      if ( initialValues != null && initialValues.containsKey( BaseColumns._ID ) )
      {
         initialValues = getInitialValues( initialValues );
         
         final SQLiteDatabase db = dbAccess.getWritableDatabase();
         final long rowId = db.insert( tableName,
                                       BaseColumns._ID,
                                       initialValues );
         
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
         count = db.update( tableName, values, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.update( tableName, values, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   public int delete( String id, String where, String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.delete( tableName, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.delete( tableName, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   public String getTableName()
   {
      return tableName;
   }
   


   public UriMatcher getUriMatcher()
   {
      return uriMatcher;
   }
   


   public int matchUri( Uri uri )
   {
      switch ( uriMatcher.match( uri ) )
      {
         case DIR:
            return MATCH_TYPE;
         case ITEM_ID:
            return MATCH_ITEM_TYPE;
         default :
            return UriMatcher.NO_MATCH;
      }
   }
   


   public String getType( Uri uri )
   {
      switch ( uriMatcher.match( uri ) )
      {
         case DIR:
            return getContentType();
         case ITEM_ID:
            return getContentItemType();
         default :
            return null;
      }
   }
   


   public String getIdSegement( Uri uri )
   {
      return uri.getPathSegments().get( 1 );
   }
   


   public String[] getProjection()
   {
      return (String[]) getProjectionMap().keySet().toArray();
   }
   


   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      return initialValues;
   }
   


   protected final static void fillProjectionMap( HashMap< String, String > projectionMap,
                                                  HashMap< String, Integer > indices )
   {
      final Collection< String > keys = indices.keySet();
      
      for ( String key : keys )
      {
         projectionMap.put( key, key );
      }
   }
   

   protected abstract String getDefaultSortOrder();
   


   protected abstract Uri getContentUri();
   


   protected abstract String getContentType();
   


   protected abstract String getContentItemType();
}
