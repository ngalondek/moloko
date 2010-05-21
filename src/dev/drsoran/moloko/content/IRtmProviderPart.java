package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public interface IRtmProviderPart
{
   public static int MATCH_TYPE_DIR = 0;
   
   public static int MATCH_TYPE_ITEM = 1;
   
   

   public Cursor query( String id, String[] projection, String selection, String[] selectionArgs, String sortOrder );
   


   public Uri insert( ContentValues values );
   


   public int update( String id, ContentValues initialValues, String where, String[] whereArgs );
   


   public int delete( String id, String where, String[] whereArgs );
   


   public String getTableName();
   


   public UriMatcher getUriMatcher();
   


   public int matchUri( Uri uri );
   


   public String getIdSegement( Uri uri );
   


   public HashMap< String, String > getProjection();
}
