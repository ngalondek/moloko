/*
 * Copyright (c) 2012 Ronny Röhricht
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

import java.util.Iterator;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;


final class ContentProviderHelper
{
   private ContentProviderHelper()
   {
      throw new AssertionError( "This class should not be instantiated." );
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
                                                      .build() : contentUri;
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
         MolokoApp.Log.e( DbHelper.class, "Generating new ID failed. ", e );
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return newId;
      
}
