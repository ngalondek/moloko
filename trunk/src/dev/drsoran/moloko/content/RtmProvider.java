package dev.drsoran.moloko.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


public class RtmProvider extends ContentProvider
{
   @SuppressWarnings( "unused" )
   private final static String TAG = RtmProvider.class.getSimpleName();
   
   private final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   private static IRtmProviderPart parts[] = null;
   
   private static class RtmProviderOpenHelper extends SQLiteOpenHelper
   {
      public RtmProviderOpenHelper( Context context )
      {
         super( context, DATABASE_NAME, null, DATABASE_VERSION );
      }
      


      public void onCreate( SQLiteDatabase db )
      {
         try
         {
            RtmProvider.parts = new IRtmProviderPart[] { RtmTasksProviderPart.create( this ) };
         } catch ( SQLException e )
         {
            
         }
      }
      


      public void onOpen( SQLiteDatabase sb )
      {
         RtmProvider.parts = new IRtmProviderPart[] { new RtmTasksProviderPart( this ) };
      }
      


      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         try
         {
            RtmProvider.parts = new IRtmProviderPart[] { RtmTasksProviderPart.upgrade( this, oldVersion, newVersion ) };
         } catch ( SQLException e )
         {
            
         }
      }
   }
   
   private RtmProviderOpenHelper taskDb = null;
   
   

   @Override
   public boolean onCreate()
   {
      taskDb = new RtmProviderOpenHelper( getContext() );
      return true;
   }
   


   @Override
   public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder )
   {
      Cursor cursor = null;
      
      // Find the DB part that can handle the URI.
      for ( int i = 0; i < parts.length; i++ )
      {
         final IRtmProviderPart part = parts[ i ];
         final int matchType = part.matchUri( uri );
         
         switch ( matchType )
         {
            case IRtmProviderPart.MATCH_TYPE_DIR:
               cursor = part.query( null, projection, selection, selectionArgs, sortOrder );
               break;
            
            case IRtmProviderPart.MATCH_TYPE_ITEM:
               cursor = part.query( part.getIdSegement( uri ), projection, selection, selectionArgs, sortOrder );
               break;
            
            default:
               break;
         }
      }
      
      return cursor;
   }
   


   @Override
   public int delete( Uri uri, String where, String[] whereArgs )
   {
      // TODO: Notify changed
      // getContext().getContentResolver().notifyChange( uri, null );
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
      // TODO: Notify changed
      // context.getContentResolver().notifyChange( noteUri, null );
      return null;
   }
   


   @Override
   public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs )
   {
      // TODO: Notify changed
      // getContext().getContentResolver().notifyChange( uri, null );
      return 0;
   }
   
}
