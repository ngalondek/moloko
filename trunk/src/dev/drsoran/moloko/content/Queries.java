package dev.drsoran.moloko.content;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.provider.Rtm;


public final class Queries
{
   public final static boolean exists( ContentResolver resolver,
                                       Uri contentUri,
                                       String id )
   {
      final Cursor c
      = resolver.query( ContentUris.withAppendedId( contentUri, Long.parseLong( id ) ),
                        new String[]{ BaseColumns._ID }, null, null, null ) ) );
   }
   
   
   public final static class Lists
   {
      public final static boolean exists( ContentResolver resolver, RtmList list )
      {
         ContentValues values = new ContentValues();
         
         values.put( Rtm.Lists._ID, list.getId() );
         values.put( Rtm.Lists.NAME, list.getName() );
         
         return resolver.insert( Rtm.Lists.CONTENT_URI, values );
      }
      


      public final static Uri insertOrReplace( ContentResolver resolver,
                                               RtmList list )
      {
         ContentValues values = new ContentValues();
         
         values.put( Rtm.Lists._ID, list.getId() );
         values.put( Rtm.Lists.NAME, list.getName() );
         
         return resolver.insert( Rtm.Lists.CONTENT_URI, values );
      }
      


      public final static int addTaskSeries( ContentResolver resolver,
                                             String listId,
                                             RtmTaskSeries taskSeries )
      {
         ContentValues values = new ContentValues();
         
         values.put( Rtm.Lists.TASKSERIES_ID, taskSeries.getId() );
         
         return resolver.update( ContentUris.withAppendedId( Rtm.Lists.CONTENT_URI,
                                                             Long.parseLong( listId ) ),
                                 values,
                                 null,
                                 null );
      }
   }
}
