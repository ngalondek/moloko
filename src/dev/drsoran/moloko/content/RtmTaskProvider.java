package dev.drsoran.moloko.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class RtmTaskProvider extends ContentProvider
{
   private static final String TAG = RtmTaskProvider.class.getSimpleName();
   
   private static final String TABLE_NAME = "tasks";
   
   private static class RtmTaskSQLiteDatabase extends SQLiteRtmDatabase
   {
      public RtmTaskSQLiteDatabase( Context context )
      {
         super( context );
      }
      


      @Override
      public void onCreate( SQLiteDatabase db )
      {
         // TODO Implement
      }
      


      @Override
      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         Log.w( TAG, "Upgrading database from version "
                     + oldVersion + " to " + newVersion + ", which will destroy all old data" );
         onCreate( db );
      }
   }
   
   private RtmTaskSQLiteDatabase taskDb;
   
   

   @Override
   public boolean onCreate()
   {
      taskDb = new RtmTaskSQLiteDatabase( getContext() );
      return true;
   }
   


   @Override
   public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder )
   {
      return null;
   }
   


   @Override
   public int delete( Uri arg0, String arg1, String[] arg2 )
   {
      return 0;
   }
   


   @Override
   public String getType( Uri uri )
   {
      return null;
   }
   


   @Override
   public Uri insert( Uri uri, ContentValues values )
   {
      return null;
   }
   


   @Override
   public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs )
   {
      return 0;
   }
   
}
