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

package dev.drsoran.moloko.util;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.provider.Rtm.Tasks;


public final class Queries
{
   public final static String[] PROJECTION_ID =
   { BaseColumns._ID };
   
   
   
   private Queries()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static String resolveTaskSortToSqlite( int sortValue )
   {
      switch ( sortValue )
      {
         case Settings.TASK_SORT_PRIORITY:
            return Tasks.SORT_PRIORITY;
         case Settings.TASK_SORT_DUE_DATE:
            return Tasks.SORT_DUE_DATE;
         case Settings.TASK_SORT_NAME:
            return Tasks.SORT_TASK_NAME;
         default :
            return null;
      }
   }
   
   
   
   public final static String bindAll( String selection, String[] selectionArgs )
   {
      String result = selection;
      
      for ( int i = 0; i < selectionArgs.length; i++ )
      {
         result = result.replaceFirst( "\\?", selectionArgs[ i ] );
      }
      
      return result.toString();
   }
   
   
   
   public final static String toCommaList( String[] values )
   {
      if ( values != null )
         return TextUtils.join( ",", values );
      else
         return "";
   }
   
   
   
   public final static < T > String toColumnList( Iterable< T > set,
                                                  String colName,
                                                  String seperator )
   {
      final StringBuilder sb = new StringBuilder();
      
      for ( Iterator< T > i = set.iterator(); i.hasNext(); )
      {
         final T obj = i.next();
         
         sb.append( colName ).append( "=" ).append( obj.toString() );
         
         if ( i.hasNext() )
            sb.append( seperator );
      }
      
      return sb.toString();
   }
   
   
   
   public final static Uri contentUriWithId( Uri contentUri, String id )
   {
      
      return ( !TextUtils.isEmpty( id ) ) ? contentUri.buildUpon()
                                                      .appendEncodedPath( id )
                                                      .build() : null;
   }
   
   
   
   public final static boolean exists( ContentProviderClient client,
                                       Uri contentUri,
                                       String id ) throws RemoteException
   {
      final Cursor c = getItem( client, PROJECTION_ID, contentUri, id );
      
      final boolean exists = c != null && c.getCount() > 0;
      
      if ( c != null )
         c.close();
      
      return exists;
   }
   
   
   
   public final static Cursor getItem( ContentProviderClient client,
                                       String[] projection,
                                       Uri contentUri,
                                       String id ) throws RemoteException
   {
      return ( contentUri != null && !TextUtils.isEmpty( id ) )
                                                               ? client.query( contentUriWithId( contentUri,
                                                                                                 id ),
                                                                               projection,
                                                                               null,
                                                                               null,
                                                                               null )
                                                               : null;
   }
   
   
   
   public final static String getNextId( ContentProviderClient client,
                                         Uri contentUri )
   {
      Cursor c = null;
      String newId = null;
      
      try
      {
         c = client.query( contentUri, new String[]
         { BaseColumns._ID }, null, null, null );
         
         if ( c != null )
         {
            if ( c.getCount() > 0 )
            {
               long longId = -1;
               
               for ( boolean ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final long id = c.getLong( 0 );
                  if ( id > longId )
                     longId = id;
               }
               
               newId = String.valueOf( longId + 1 );
            }
            else
               newId = String.valueOf( 1L );
         }
      }
      catch ( Throwable e )
      {
         Log.e( LogUtils.toTag( Queries.class ),
                "Generating new ID failed. ",
                e );
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return newId;
   }
   
   
   
   public final static String getOptString( Cursor c, int index )
   {
      return c.isNull( index ) ? null : c.getString( index );
   }
   
   
   
   public final static String getOptString( Cursor c, int index, String defValue )
   {
      return c.isNull( index ) ? defValue : c.getString( index );
   }
   
   
   
   public final static Date getOptDate( Cursor c, int index )
   {
      return c.isNull( index ) ? null : new Date( c.getLong( index ) );
   }
   
   
   
   public final static float getOptFloat( Cursor c, int index, float defValue )
   {
      return c.isNull( index ) ? defValue : c.getFloat( index );
   }
   
   
   
   public final static int getOptInt( Cursor c, int index, int defValue )
   {
      return c.isNull( index ) ? defValue : c.getInt( index );
   }
   
   
   
   public final static long getOptLong( Cursor c, int index, int defValue )
   {
      return c.isNull( index ) ? defValue : c.getLong( index );
   }
   
   
   
   public final static Long getOptLong( Cursor c, int index )
   {
      return c.isNull( index ) ? null : c.getLong( index );
   }
   
   
   
   public final static boolean getOptBool( Cursor c, int index, boolean defValue )
   {
      return c.isNull( index ) ? defValue : c.getInt( index ) != 0;
   }
   
   
   
   public final static String[] cursorAsStringArray( Cursor c, int columnIndex )
   {
      return fillStringArray( c, columnIndex, null, 0 );
   }
   
   
   
   public final static String[] fillStringArray( Cursor c,
                                                 int columnIndex,
                                                 String[] array,
                                                 int startIdx )
   {
      String[] res = null;
      
      if ( array == null )
         res = new String[ c.getCount() ];
      else
         res = array;
      
      final boolean cursorHasElements = c.getCount() > 0;
      
      if ( res.length > 0 && cursorHasElements && c.getCount() <= res.length )
      {
         boolean ok = c.moveToFirst();
         
         for ( int i = startIdx; ok && !c.isAfterLast(); c.moveToNext(), ++i )
         {
            res[ i ] = c.getString( columnIndex );
         }
         
         if ( !ok )
            res = null;
      }
      
      return res;
   }
   
   
   
   public final static boolean applyActionItemList( FragmentActivity activity,
                                                    ContentProviderActionItemList actionItemList,
                                                    String progressText )
   {
      boolean ok = true;
      
      if ( actionItemList.size() > 0 )
      {
         try
         {
            ok = new ApplyContentProviderActionItemsTask( activity,
                                                          progressText ).execute( actionItemList )
                                                                        .get();
         }
         catch ( InterruptedException e )
         {
            Log.e( LogUtils.toTag( Queries.class ),
                   "Applying ContentProviderActionItemList failed",
                   e );
            ok = false;
         }
         catch ( ExecutionException e )
         {
            Log.e( LogUtils.toTag( Queries.class ),
                   "Applying ContentProviderActionItemList failed",
                   e );
            ok = false;
         }
      }
      
      return ok;
   }
}
