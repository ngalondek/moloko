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

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.text.TextUtils;


public final class Queries
{
   public final static String[] PROJECTION_ID =
   { BaseColumns._ID };
   
   

   private Queries()
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
   


   public final static String getOptString( Cursor c, int index )
   {
      return c.isNull( index ) ? null : c.getString( index );
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
   


   public final static Long getOptLong( Cursor c, int index )
   {
      return c.isNull( index ) ? null : c.getLong( index );
   }
   


   public final static boolean getOptBool( Cursor c, int index, boolean defValue )
   {
      return c.isNull( index ) ? defValue : c.getInt( index ) != 0;
   }
}
