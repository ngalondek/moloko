package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public interface IRtmProviderPart
{
   public static int MATCH_TYPE = 0;
   
   public static int MATCH_ITEM_TYPE = 1;
   
   

   public void create( SQLiteDatabase db ) throws SQLException;
   


   public void upgrade( SQLiteDatabase db, int oldVersion, int newVersion ) throws SQLException;
   


   public void drop( SQLiteDatabase db );
   


   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder );
   


   public Uri insert( ContentValues values );
   


   public int update( String id,
                      ContentValues initialValues,
                      String where,
                      String[] whereArgs );
   


   public int delete( String id, String where, String[] whereArgs );
   


   public String getTableName();
   


   public UriMatcher getUriMatcher();
   


   public int matchUri( Uri uri );
   


   public String getIdSegement( Uri uri );
   


   public String getType( Uri uri );
   


   public HashMap< String, String > getProjectionMap();
   


   public String[] getProjection();
   


   public HashMap< String, Integer > getColumnIndices();
}
