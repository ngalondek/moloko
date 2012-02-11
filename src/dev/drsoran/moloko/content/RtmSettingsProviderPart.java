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
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Settings;
import dev.drsoran.rtm.RtmSettings;


public class RtmSettingsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = "Moloko."
      + RtmSettingsProviderPart.class.getSimpleName();
   
   public final static String SETTINGS_ID = "1";
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Settings._ID, Settings.SYNC_TIMESTAMP, Settings.TIMEZONE,
    Settings.DATEFORMAT, Settings.TIMEFORMAT, Settings.DEFAULTLIST_ID,
    Settings.LANGUAGE };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( RtmSettings settings )
   {
      ContentValues values = null;
      
      if ( settings != null )
      {
         values = new ContentValues();
         
         values.put( Settings._ID, SETTINGS_ID );
         values.put( Settings.SYNC_TIMESTAMP, settings.getSyncTimeStamp()
                                                      .getTime() );
         
         if ( !TextUtils.isEmpty( settings.getTimezone() ) )
            values.put( Settings.TIMEZONE, settings.getTimezone() );
         else
            values.putNull( Settings.TIMEZONE );
         
         values.put( Settings.DATEFORMAT, settings.getDateFormat() );
         values.put( Settings.TIMEFORMAT, settings.getTimeFormat() );
         
         if ( !TextUtils.isEmpty( settings.getDefaultListId() ) )
            values.put( Settings.DEFAULTLIST_ID, settings.getDefaultListId() );
         else
            values.putNull( Settings.DEFAULTLIST_ID );
         
         if ( !TextUtils.isEmpty( settings.getLanguage() ) )
            values.put( Settings.LANGUAGE, settings.getLanguage() );
         else
            values.putNull( Settings.LANGUAGE );
      }
      
      return values;
   }
   
   
   
   public final static String getSettingsId( ContentProviderClient client )
   {
      String id = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Settings.CONTENT_URI, PROJECTION, null, null, null );
         
         // We only consider the first entry cause we do not expect
         // more than 1 entry in this table
         final boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            id = c.getString( COL_INDICES.get( Settings._ID ) );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query settings ID failed. ", e );
         id = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return id;
   }
   
   
   
   public final static RtmSettings getSettings( ContentProviderClient client )
   {
      RtmSettings settings = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Settings.CONTENT_URI, PROJECTION, null, null, null );
         
         // We only consider the first entry cause we do not expect
         // more than 1 entry in this table
         final boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            settings = new RtmSettings( Queries.getOptDate( c,
                                                            COL_INDICES.get( Settings.SYNC_TIMESTAMP ) ),
                                        Queries.getOptString( c,
                                                              COL_INDICES.get( Settings.TIMEZONE ) ),
                                        c.getInt( COL_INDICES.get( Settings.DATEFORMAT ) ),
                                        c.getInt( COL_INDICES.get( Settings.TIMEFORMAT ) ),
                                        Queries.getOptString( c,
                                                              COL_INDICES.get( Settings.DEFAULTLIST_ID ) ),
                                        Queries.getOptString( c,
                                                              COL_INDICES.get( Settings.LANGUAGE ) ) );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query settings ailed. ", e );
         settings = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return settings;
   }
   
   
   
   public static void registerContentObserver( Context context,
                                               ContentObserver observer )
   {
      context.getContentResolver()
             .registerContentObserver( Settings.CONTENT_URI, true, observer );
   }
   
   
   
   public static void unregisterContentObserver( Context context,
                                                 ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public RtmSettingsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Settings.PATH );
   }
   
   
   
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE "
         + path
         + " ( "
         + Settings._ID
         + " INTEGER NOT NULL CONSTRAINT PK_SETTINGS PRIMARY KEY AUTOINCREMENT, "
         + Settings.SYNC_TIMESTAMP + " INTEGER NOT NULL, " + Settings.TIMEZONE
         + " TEXT, " + Settings.DATEFORMAT + " INTEGER NOT NULL DEFAULT 0, "
         + Settings.TIMEFORMAT + " INTEGER NOT NULL DEFAULT 0, "
         + Settings.DEFAULTLIST_ID + " TEXT, " + Settings.LANGUAGE
         + " TEXT, CONSTRAINT defaultlist FOREIGN KEY ( "
         + Settings.DEFAULTLIST_ID + ") REFERENCES " + Lists.PATH + "( "
         + Lists._ID + " ) );" );
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Settings.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Settings.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Settings.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return null;
   }
   
   
   
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
