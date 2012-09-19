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

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.LocationOverviews;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.LocationWithTaskCount;


public class LocationOverviewsProviderPart extends AbstractProviderPart
{
   private static final Class< LocationOverviewsProviderPart > TAG = LocationOverviewsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { LocationOverviews._ID, LocationOverviews.LOCATION_NAME,
    LocationOverviews.LONGITUDE, LocationOverviews.LATITUDE,
    LocationOverviews.ADDRESS, LocationOverviews.VIEWABLE,
    LocationOverviews.ZOOM, LocationOverviews.TASKS_COUNT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String QUERY;
   
   private final static String SUBQUERY_NON_COMPLETED;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      SUBQUERY_NON_COMPLETED = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                                    
                                                                    // tables
                                                                    TaskSeries.PATH
                                                                       + ","
                                                                       + RawTasks.PATH,
                                                                    
                                                                    // columns
                                                                    new String[]
                                                                    {
                                                                     TaskSeries.PATH
                                                                        + "."
                                                                        + TaskSeries._ID
                                                                        + " AS series_id",
                                                                     TaskSeries.LOCATION_ID,
                                                                     RawTasks.COMPLETED_DATE },
                                                                    
                                                                    // where
                                                                    "series_id ="
                                                                       + RawTasks.PATH
                                                                       + "."
                                                                       + RawTasks.TASKSERIES_ID
                                                                       + " AND "
                                                                       
                                                                       // Only non-completed tasks
                                                                       + RawTasks.COMPLETED_DATE
                                                                       + " IS NULL",
                                                                    null,
                                                                    null,
                                                                    null,
                                                                    null );
      
      QUERY = new StringBuilder( "SELECT " ).append( Locations.PATH )
                                            .append( ".*, count( subQuery.series_id ) AS " )
                                            .append( LocationOverviews.TASKS_COUNT )
                                            .append( " FROM " )
                                            .append( Locations.PATH )
                                            .append( " LEFT OUTER JOIN (" )
                                            .append( SUBQUERY_NON_COMPLETED )
                                            .append( ") AS subQuery ON " )
                                            .append( Locations.PATH )
                                            .append( "." )
                                            .append( Locations._ID )
                                            .append( " = " )
                                            .append( TaskSeries.LOCATION_ID )
                                            .append( " GROUP BY " )
                                            .append( Locations.PATH )
                                            .append( "." )
                                            .append( Locations._ID )
                                            .toString();
   }
   
   
   
   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver()
             .registerContentObserver( Locations.CONTENT_URI, true, observer );
      context.getContentResolver()
             .registerContentObserver( TaskSeries.CONTENT_URI, true, observer );
   }
   
   
   
   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public final static LocationWithTaskCount getLocationOverview( ContentProviderClient client,
                                                                  String id )
   {
      LocationWithTaskCount location = null;
      Cursor c = null;
      
      try
      {
         c = client.query( LocationOverviews.CONTENT_URI,
                           PROJECTION,
                           LocationOverviews._ID + " = " + id,
                           null,
                           null );
         
         if ( c != null && c.getCount() > 0 && c.moveToFirst() )
         {
            location = createLocationOverview( c );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query location overview failed. ", e );
         location = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return location;
   }
   
   
   
   public final static ArrayList< LocationWithTaskCount > getLocationsOverview( ContentProviderClient client,
                                                                                String selection )
   {
      ArrayList< LocationWithTaskCount > locations = null;
      
      Cursor c = null;
      
      try
      {
         c = client.query( LocationOverviews.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           LocationOverviews.DEFAULT_SORT_ORDER );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            locations = new ArrayList< LocationWithTaskCount >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final LocationWithTaskCount location = createLocationOverview( c );
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
         MolokoApp.Log.e( TAG, "Query location overviews failed. ", e );
         locations = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return locations;
   }
   
   
   
   public LocationOverviewsProviderPart( Context context,
      SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, LocationOverviews.PATH );
   }
   
   
   
   @Override
   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( QUERY )
                                                                        .append( " )" );
      
      if ( !TextUtils.isEmpty( selection ) )
      {
         stringBuilder.append( " WHERE ( " )
                      .append( selectionArgs != null
                                                    ? Queries.bindAll( selection,
                                                                       selectionArgs )
                                                    : selection )
                      .append( " )" );
      }
      
      if ( !TextUtils.isEmpty( sortOrder ) )
      {
         stringBuilder.append( " ORDER BY " ).append( sortOrder );
      }
      
      final String finalQuery = stringBuilder.toString();
      
      // Get the database and run the QUERY
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      final Cursor cursor = db.rawQuery( finalQuery, null );
      
      return cursor;
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return null;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return LocationOverviews.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return LocationOverviews.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return LocationOverviews.DEFAULT_SORT_ORDER;
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
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   private final static LocationWithTaskCount createLocationOverview( Cursor c )
   {
      return new LocationWithTaskCount( new RtmLocation( c.getString( COL_INDICES.get( LocationOverviews._ID ) ),
                                                         c.getString( COL_INDICES.get( LocationOverviews.LOCATION_NAME ) ),
                                                         c.getFloat( COL_INDICES.get( LocationOverviews.LONGITUDE ) ),
                                                         c.getFloat( COL_INDICES.get( LocationOverviews.LATITUDE ) ),
                                                         Queries.getOptString( c,
                                                                               COL_INDICES.get( LocationOverviews.ADDRESS ) ),
                                                         c.getInt( COL_INDICES.get( LocationOverviews.VIEWABLE ) ) != 0,
                                                         Queries.getOptInt( c,
                                                                            COL_INDICES.get( LocationOverviews.ZOOM ),
                                                                            0 ) ),
                                        c.getInt( COL_INDICES.get( LocationOverviews.TASKS_COUNT ) ) );
   }
   
}
