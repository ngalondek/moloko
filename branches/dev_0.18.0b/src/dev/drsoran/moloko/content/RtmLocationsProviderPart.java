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
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.content.db.AbstractRtmProviderPart;
import dev.drsoran.moloko.content.db.DbHelper;
import dev.drsoran.provider.Rtm.Locations;


public class RtmLocationsProviderPart extends AbstractRtmProviderPart
{
   private static final Class< RtmLocationsProviderPart > TAG = RtmLocationsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Locations._ID, Locations.LOCATION_NAME, Locations.LONGITUDE,
    Locations.LATITUDE, Locations.ADDRESS, Locations.VIEWABLE, Locations.ZOOM };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( RtmLocation location,
                                                       boolean withId )
   {
      ContentValues values = null;
      
      if ( location != null )
      {
         values = new ContentValues();
         
         if ( withId )
            values.put( Locations._ID, location.id );
         
         values.put( Locations.LOCATION_NAME, location.name );
         values.put( Locations.LONGITUDE, location.longitude );
         values.put( Locations.LATITUDE, location.latitude );
         
         if ( !TextUtils.isEmpty( location.address ) )
            values.put( Locations.ADDRESS, location.address );
         else
            values.putNull( Locations.ADDRESS );
         
         values.put( Locations.VIEWABLE, location.viewable ? 1 : 0 );
         values.put( Locations.ZOOM, location.zoom );
      }
      
      return values;
   }
   
   
   
   public final static RtmLocation getLocation( ContentProviderClient client,
                                                String selection )
   {
      RtmLocation location = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Locations.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           null );
         
         final boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            location = createLocation( c );
         }
      }
      catch ( final RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query location failed. ", e );
         location = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return location;
   }
   
   
   
   public final static List< RtmLocation > getAllLocations( ContentProviderClient client )
   {
      List< RtmLocation > locations = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Locations.CONTENT_URI, PROJECTION, null, null, null );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            locations = new ArrayList< RtmLocation >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final RtmLocation location = createLocation( c );
                  ok = location != null;
                  
                  if ( ok )
                     locations.add( location );
               }
            }
            
            if ( !ok )
               locations = null;
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query locations failed. ", e );
         locations = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return locations;
   }
   
   
   
   private static RtmLocation createLocation( Cursor c )
   {
      return new RtmLocation( c.getString( COL_INDICES.get( Locations._ID ) ),
                              c.getString( COL_INDICES.get( Locations.LOCATION_NAME ) ),
                              c.getFloat( COL_INDICES.get( Locations.LONGITUDE ) ),
                              c.getFloat( COL_INDICES.get( Locations.LATITUDE ) ),
                              DbHelper.getOptString( c,
                                                    COL_INDICES.get( Locations.ADDRESS ) ),
                              c.getInt( COL_INDICES.get( Locations.VIEWABLE ) ) == 1
                                                                                    ? true
                                                                                    : false,
                              DbHelper.getOptInt( c,
                                                 COL_INDICES.get( Locations.ZOOM ),
                                                 0 ) );
   }
   
   
   
   public RtmLocationsProviderPart( SystemContext context,
      SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Locations.PATH );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Locations._ID
         + " TEXT NOT NULL, " + Locations.LOCATION_NAME + " NOTE_TEXT, "
         + Locations.LONGITUDE + " REAL NOT NULL, " + Locations.LATITUDE
         + " REAL NOT NULL, " + Locations.ADDRESS + " NOTE_TEXT, "
         + Locations.VIEWABLE + " INTEGER NOT NULL DEFAULT 1, "
         + Locations.ZOOM + " INTEGER, "
         + "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \"" + Locations._ID + "\" )"
         + " );" );
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Locations.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Locations.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Locations.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return Locations.DEFAULT_SORT_ORDER;
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
