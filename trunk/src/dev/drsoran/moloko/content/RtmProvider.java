package dev.drsoran.moloko.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;


public class RtmProvider extends ContentProvider
{
   private final static String TAG = RtmProvider.class.getSimpleName();
   
   private final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   private static IRtmProviderPart parts[] = new IRtmProviderPart[] {};
   
   
   private static class RtmProviderOpenHelper extends SQLiteOpenHelper
   {
      public RtmProviderOpenHelper( Context context )
      {
         super( context, DATABASE_NAME, null, DATABASE_VERSION );
      }
      


      public void onCreate( SQLiteDatabase db )
      {
         for ( int i = 0; i < parts.length; i++ )
         {
            try
            {
               parts[ i ].create( db );
            }
            catch ( SQLException e )
            {
               // TODO: handle exception
            }
         }
      }
      


      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         for ( int i = 0; i < parts.length; i++ )
         {
            try
            {
               parts[ i ].upgrade( db, oldVersion, newVersion );
            }
            catch ( SQLException e )
            {
               // TODO: handle exception
            }
         }
      }
   }
   

   private final static class MatchType
   {
      public IRtmProviderPart part = null;
      
      public int type = UriMatcher.NO_MATCH;
   }
   
   private RtmProviderOpenHelper dbHelper = null;
   
   

   @Override
   public boolean onCreate()
   {
      dbHelper = new RtmProviderOpenHelper( getContext() );
      
      parts = new IRtmProviderPart[]
      { new RtmTasksProviderPart( dbHelper ),
       new RtmTaskSeriesProviderPart( dbHelper ),
       new RtmListsProviderPart( dbHelper ),
       new RtmTagsProviderPart( dbHelper ),
       new TagRefsProviderPart( dbHelper ),
       new RtmNotesProviderPart( dbHelper ),
       new NoteRefsProviderPart( dbHelper ),
       new RtmLocationsProviderPart( dbHelper ) };
      
      return true;
   }
   


   @Override
   public Cursor query( Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      Cursor cursor = null;
      
      final MatchType matchType = matchUriToPart( uri );
      
      switch ( matchType.type )
      {
         case IRtmProviderPart.MATCH_TYPE:
            cursor = matchType.part.query( null,
                                           projection,
                                           selection,
                                           selectionArgs,
                                           sortOrder );
            break;
         
         case IRtmProviderPart.MATCH_ITEM_TYPE:
            cursor = matchType.part.query( matchType.part.getIdSegement( uri ),
                                           projection,
                                           selection,
                                           selectionArgs,
                                           sortOrder );
            break;
         
         default :
            // TODO: Throw and handle exception
            break;
      }
      
      return cursor;
   }
   


   @Override
   public String getType( Uri uri )
   {
      String type = null;
      
      // Find the DB part that can handle the URI.
      for ( int i = 0; i < parts.length && type == null; i++ )
      {
         type = parts[ i ].getType( uri );
      }
      
      return type;
   }
   


   @Override
   public Uri insert( Uri uri, ContentValues values )
   {
      Uri resUri = null;
      
      final MatchType matchType = matchUriToPart( uri );
      
      if ( matchType != null )
      {
         resUri = matchType.part.insert( values );
      }
      
      if ( resUri != null )
      {
         getContext().getContentResolver().notifyChange( resUri, null );
      }
      else
      {
         // TODO: Throw and handle exception.
         Log.e( TAG, "Failed to insert row into " + uri );
      }
      
      return resUri;
   }
   


   @Override
   public int delete( Uri uri, String where, String[] whereArgs )
   {
      int numDeleted = 0;
      
      final MatchType matchType = matchUriToPart( uri );
      
      switch ( matchType.type )
      {
         case IRtmProviderPart.MATCH_TYPE:
            numDeleted = matchType.part.delete( null, where, whereArgs );
            break;
         
         case IRtmProviderPart.MATCH_ITEM_TYPE:
            numDeleted = matchType.part.delete( matchType.part.getIdSegement( uri ),
                                                where,
                                                whereArgs );
            break;
         
         default :
            break;
      }
      
      if ( numDeleted > 0 )
      {
         getContext().getContentResolver().notifyChange( uri, null );
      }
      else
      {
         // TODO: Throw and handle exception.
         Log.e( TAG, "Unknown URI " + uri );
      }
      
      return numDeleted;
   }
   


   @Override
   public int update( Uri uri,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      int numUpdated = 0;
      
      final MatchType matchType = matchUriToPart( uri );
      
      switch ( matchType.type )
      {
         case IRtmProviderPart.MATCH_TYPE:
            numUpdated = matchType.part.update( null, values, where, whereArgs );
            break;
         
         case IRtmProviderPart.MATCH_ITEM_TYPE:
            numUpdated = matchType.part.update( matchType.part.getIdSegement( uri ),
                                                values,
                                                where,
                                                whereArgs );
            break;
         
         default :
            break;
      }
      
      if ( numUpdated > 0 )
      {
         getContext().getContentResolver().notifyChange( uri, null );
      }
      else
      {
         // TODO: Throw and handle exception.
         Log.e( TAG, "Unknown URI " + uri );
      }
      
      return numUpdated;
   }
   


   private MatchType matchUriToPart( Uri uri )
   {
      MatchType matchType = new MatchType();
      
      // Find the DB part that can handle the URI.
      for ( int i = 0; i < parts.length
         && matchType.type == UriMatcher.NO_MATCH; i++ )
      {
         final IRtmProviderPart part = parts[ i ];
         final int type = part.matchUri( uri );
         
         if ( type != UriMatcher.NO_MATCH )
         {
            matchType = new MatchType();
            matchType.part = part;
            matchType.type = type;
         }
      }
      
      return matchType;
   }
}
