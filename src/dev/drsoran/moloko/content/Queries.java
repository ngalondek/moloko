package dev.drsoran.moloko.content;

import java.util.Date;

import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.text.TextUtils;


public final class Queries
{
   public final static String[] PROJECTION_ID =
   { BaseColumns._ID };
   
   

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
      return ContentUris.withAppendedId( contentUri, Long.parseLong( id ) );
   }
   


   public final static boolean exists( ContentProviderClient client,
                                       Uri contentUri,
                                       String id ) throws RemoteException
   {
      final Cursor c = getItem( client, PROJECTION_ID, contentUri, id );
      
      final boolean exists = c.getCount() > 0;
      
      c.close();
      
      return exists;
   }
   


   public final static Cursor getItem( ContentProviderClient client,
                                       String[] projection,
                                       Uri contentUri,
                                       String id ) throws RemoteException
   {
      return client.query( contentUriWithId( contentUri, id ),
                           projection,
                           null,
                           null,
                           null );
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
   


   public final static boolean getOptBool( Cursor c, int index, boolean defValue )
   {
      return c.isNull( index ) ? defValue : c.getInt( index ) != 0;
   }
}
