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

import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Pair;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.content.db.AbstractRtmProviderPart;
import dev.drsoran.moloko.content.db.DbHelper;
import dev.drsoran.provider.Rtm.Sync;


public class SyncProviderPart extends AbstractRtmProviderPart
{
   private static final Class< SyncProviderPart > TAG = SyncProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Sync._ID, Sync.LAST_IN, Sync.LAST_OUT };
   
   public final static long DONT_TOUCH = -1;
   
   public final static String FIX_ID = "1";
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( Long lastIn, Long lastOut )
   {
      final ContentValues values = new ContentValues();
      
      values.put( Sync._ID, FIX_ID );
      
      if ( lastIn != null )
         if ( lastIn != DONT_TOUCH )
            values.put( Sync.LAST_IN, lastIn );
         else
            values.putNull( Sync.LAST_IN );
      
      if ( lastOut != null )
         if ( lastOut != DONT_TOUCH )
            values.put( Sync.LAST_OUT, lastOut );
         else
            values.putNull( Sync.LAST_OUT );
      
      return values;
   }
   
   
   
   public final static void updateSync( ContentProviderClient client,
                                        Long lastIn,
                                        Long lastOut )
   {
      try
      {
         if ( DbHelper.exists( client, Sync.CONTENT_URI, FIX_ID ) )
         {
            client.update( DbHelper.contentUriWithId( Sync.CONTENT_URI, FIX_ID ),
                           getContentValues( lastIn, lastOut ),
                           null,
                           null );
         }
         else
         {
            client.insert( Sync.CONTENT_URI, getContentValues( lastIn, lastOut ) );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query Sync failed. ", e );
      }
   }
   
   
   
   public final static String getSyncId( ContentProviderClient client )
   {
      String id = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Sync.CONTENT_URI, PROJECTION, null, null, null );
         
         // We only consider the first entry cause we do not expect
         // more than 1 entry in this table
         boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            id = c.getString( COL_INDICES.get( Sync._ID ) );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query Sync failed. ", e );
         id = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return id;
   }
   
   
   
   public final static Pair< Long, Long > getLastInAndLastOut( ContentProviderClient client )
   {
      Pair< Long, Long > inAndOut = new Pair< Long, Long >( null, null );
      Cursor c = null;
      
      try
      {
         c = client.query( Sync.CONTENT_URI, PROJECTION, null, null, null );
         
         // We only consider the first entry cause we do not expect
         // more than 1 entry in this table
         boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            inAndOut = new Pair< Long, Long >( DbHelper.getOptLong( c,
                                                                   COL_INDICES.get( Sync.LAST_IN ) ),
                                               DbHelper.getOptLong( c,
                                                                   COL_INDICES.get( Sync.LAST_OUT ) ) );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query Sync failed. ", e );
         inAndOut = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return inAndOut;
   }
   
   
   
   public SyncProviderPart( SystemContext context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Sync.PATH );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Sync._ID
         + " INTEGER NOT NULL CONSTRAINT PK_SYNC PRIMARY KEY AUTOINCREMENT, "
         + Sync.LAST_IN + " INTEGER, " + Sync.LAST_OUT + " INTEGER );" );
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Sync.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Sync.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Sync.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return null;
   }
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
