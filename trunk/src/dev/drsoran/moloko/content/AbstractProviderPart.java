package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import dev.drsoran.provider.Rtm;


public abstract class AbstractProviderPart implements IProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = AbstractProviderPart.class.getSimpleName();
   
   protected static final String ITEM_ID_EQUALS = BaseColumns._ID + "=";
   
   protected final String path;
   
   protected static final int DIR = 1;
   
   protected static final int ITEM_ID = 2;
   
   protected final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
   
   protected SQLiteOpenHelper dbAccess = null;
   
   

   public AbstractProviderPart( SQLiteOpenHelper dbAccess, String path )
   {
      this.dbAccess = dbAccess;
      this.path = path;
      
      addUris();
   }
   


   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      Cursor cursor = null;
      
      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
      qb.setTables( path );
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
   


   protected void addUris()
   {
      uriMatcher.addURI( Rtm.AUTHORITY, path, DIR );
      uriMatcher.addURI( Rtm.AUTHORITY, path + "/#", ITEM_ID );
   }
   


   protected final static void initProjectionDependent( String[] projection,
                                                        HashMap< String, String > projectionMap,
                                                        HashMap< String, Integer > columnIndices )
   {
      for ( int i = 0; i < projection.length; i++ )
      {
         final String column = projection[ i ];
         projectionMap.put( column, column );
         columnIndices.put( column, i );
      }
   }
   


   protected abstract String getDefaultSortOrder();
   


   protected abstract Uri getContentUri();
   


   protected abstract String getContentType();
   


   protected abstract String getContentItemType();
   
}
