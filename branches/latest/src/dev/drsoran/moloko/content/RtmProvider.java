/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;


public class RtmProvider extends ContentProvider
{
   private final static String TAG = "Moloko."
      + RtmProvider.class.getSimpleName();
   
   private final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   private final static List< IProviderPart > parts = new ArrayList< IProviderPart >();
   
   private final static List< IRtmProviderPart > mutableParts = new ArrayList< IRtmProviderPart >();
   
   
   private static class RtmProviderOpenHelper extends SQLiteOpenHelper
   {
      public RtmProviderOpenHelper( Context context )
      {
         super( context, DATABASE_NAME, null, DATABASE_VERSION );
      }
      


      @Override
      public void onCreate( SQLiteDatabase db )
      {
         final int size = mutableParts.size();
         
         for ( int i = 0; i < size; i++ )
         {
            try
            {
               mutableParts.get( i ).create( db );
            }
            catch ( SQLException e )
            {
               // TODO: handle exception
            }
         }
      }
      


      @Override
      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         final int size = mutableParts.size();
         
         for ( int i = 0; i < size; i++ )
         {
            try
            {
               mutableParts.get( i ).upgrade( db, oldVersion, newVersion );
            }
            catch ( SQLException e )
            {
               // TODO: handle exception
            }
         }
      }
   }
   

   private final static class MatchType< T extends IProviderPart >
   {
      public T part = null;
      
      public int type = UriMatcher.NO_MATCH;
   }
   
   private RtmProviderOpenHelper dbHelper = null;
   
   

   @Override
   public boolean onCreate()
   {
      dbHelper = new RtmProviderOpenHelper( getContext() );
      
      // These parts allow modification in the local DB.
      mutableParts.addAll( Arrays.asList( new IRtmProviderPart[]
      { new RtmTasksProviderPart( dbHelper ),
       new RtmTaskSeriesProviderPart( dbHelper ),
       new RtmListsProviderPart( dbHelper ), new TagsProviderPart( dbHelper ),
       new RtmNotesProviderPart( dbHelper ),
       new RtmLocationsProviderPart( dbHelper ),
       new RtmContactsProviderPart( dbHelper ),
       new ParticipantsProviderPart( dbHelper ),
       new RtmSettingsProviderPart( dbHelper ),
       new SyncProviderPart( dbHelper ),
       new ModificationsProviderPart( dbHelper ),
       new RollbacksProviderPart( dbHelper ) } ) );
      
      parts.addAll( mutableParts );
      
      // These parts are immutable and allow no insert, update, deletion
      parts.addAll( Arrays.asList( new IProviderPart[]
      { new TasksProviderPart( dbHelper ),
       new ListOverviewsProviderPart( dbHelper ),
       new TagOverviewsProviderPart( dbHelper ),
       new LocationOverviewsProviderPart( dbHelper ),
       new ContactOverviewsProviderPart( dbHelper ) } ) );
      
      return true;
   }
   


   public TransactionalAccess newTransactionalAccess() throws SQLException
   {
      return TransactionalAccess.newTransactionalAccess( dbHelper );
   }
   


   @Override
   public Cursor query( Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      Cursor cursor = null;
      
      final MatchType< IProviderPart > matchType = matchUriToPart( parts, uri );
      
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
            cursor = matchType.part.query( uri.getLastPathSegment(),
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
      
      final int size = parts.size();
      
      // Find the DB part that can handle the URI.
      for ( int i = 0; i < size && type == null; i++ )
      {
         type = parts.get( i ).getType( uri );
      }
      
      return type;
   }
   


   @Override
   public Uri insert( Uri uri, ContentValues values )
   {
      Uri resUri = null;
      
      final MatchType< IRtmProviderPart > matchType = matchUriToPart( mutableParts,
                                                                      uri );
      
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
      
      final MatchType< IRtmProviderPart > matchType = matchUriToPart( mutableParts,
                                                                      uri );
      
      switch ( matchType.type )
      {
         case IRtmProviderPart.MATCH_TYPE:
            numDeleted = matchType.part.delete( null, where, whereArgs );
            break;
         
         case IRtmProviderPart.MATCH_ITEM_TYPE:
            numDeleted = matchType.part.delete( uri.getLastPathSegment(),
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
   


   public synchronized void clear() throws OperationApplicationException
   {
      final ArrayList< ContentProviderOperation > deleteOps = new ArrayList< ContentProviderOperation >();
      
      for ( IRtmProviderPart part : mutableParts )
      {
         deleteOps.add( ContentProviderOperation.newDelete( part.getContentUri() )
                                                .build() );
      }
      
      final TransactionalAccess transactionalAccess = newTransactionalAccess();
      
      try
      {
         transactionalAccess.beginTransaction();
         
         applyBatch( deleteOps );
         
         transactionalAccess.setTransactionSuccessful();
      }
      catch ( Throwable e )
      {
         Log.e( TAG, "Clearing database failed", e );
         throw new OperationApplicationException( e );
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
   }
   


   @Override
   public int update( Uri uri,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      int numUpdated = 0;
      
      final MatchType< IRtmProviderPart > matchType = matchUriToPart( mutableParts,
                                                                      uri );
      
      switch ( matchType.type )
      {
         case IRtmProviderPart.MATCH_TYPE:
            numUpdated = matchType.part.update( null, values, where, whereArgs );
            break;
         
         case IRtmProviderPart.MATCH_ITEM_TYPE:
            numUpdated = matchType.part.update( uri.getLastPathSegment(),
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
   


   private < T extends IProviderPart > MatchType< T > matchUriToPart( List< T > parts,
                                                                      Uri uri )
   {
      MatchType< T > matchType = new MatchType< T >();
      
      final int size = parts.size();
      
      // Find the DB part that can handle the URI.
      for ( int i = 0; i < size && matchType.type == UriMatcher.NO_MATCH; i++ )
      {
         final T part = parts.get( i );
         final int type = part.matchUri( uri );
         
         if ( type != UriMatcher.NO_MATCH )
         {
            matchType = new MatchType< T >();
            matchType.part = part;
            matchType.type = type;
         }
      }
      
      return matchType;
   }
}
