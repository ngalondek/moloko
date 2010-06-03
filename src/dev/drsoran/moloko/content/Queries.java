package dev.drsoran.moloko.content;

import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;


public final class Queries
{
   public final static Uri contentUriWithId( Uri contentUri, String id )
   {
      return ContentUris.withAppendedId( contentUri, Long.parseLong( id ) );
   }
   


   public final static boolean exists( ContentProviderClient client,
                                       Uri contentUri,
                                       String id )
   {
      Cursor c;
      try
      {
         c = client.query( contentUriWithId( contentUri, id ), new String[]
         { BaseColumns._ID }, null, null, null );
      }
      catch ( RemoteException e )
      {
         c = null;
      }
      
      return c != null;
   }
}
