package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Locations;


public class RtmLocationsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = RtmLocationsProviderPart.class.getSimpleName();
   
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
         
         if ( location.address != null )
            values.put( Locations.ADDRESS, location.address );
         else
            values.putNull( Locations.ADDRESS );
         
         values.put( Locations.VIEWABLE, location.viewable ? 1 : 0 );
         values.put( Locations.ZOOM, location.zoom );
      }
      
      return values;
   }
   


   public final static ArrayList< RtmLocation > getAllLocations( ContentProviderClient client )
   {
      ArrayList< RtmLocation > locations = null;
      
      try
      {
         final Cursor c = client.query( Locations.CONTENT_URI,
                                        PROJECTION,
                                        null,
                                        null,
                                        null );
         
         locations = new ArrayList< RtmLocation >( c.getCount() );
         
         if ( c.getCount() > 0 )
         {
            boolean ok = true;
            
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               int zoom = 0;
               
               if ( !c.isNull( COL_INDICES.get( Locations.ZOOM ) ) )
                  zoom = c.getInt( COL_INDICES.get( Locations.ZOOM ) );
               
               final RtmLocation location = new RtmLocation( c.getString( COL_INDICES.get( Locations._ID ) ),
                                                             c.getString( COL_INDICES.get( Locations.LOCATION_NAME ) ),
                                                             c.getFloat( COL_INDICES.get( Locations.LONGITUDE ) ),
                                                             c.getFloat( COL_INDICES.get( Locations.LATITUDE ) ),
                                                             Queries.getOptString( c,
                                                                                   COL_INDICES.get( Locations.LOCATION_NAME ) ),
                                                             c.getInt( COL_INDICES.get( Locations.VIEWABLE ) ) == 1
                                                                                                                   ? true
                                                                                                                   : false,
                                                             zoom );
               locations.add( location );
            }
         }
         
         c.close();
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query locations failed. ", e );
         locations = null;
      }
      
      return locations;
   }
   


   public RtmLocationsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Locations.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Locations._ID
         + " INTEGER NOT NULL, " + Locations.LOCATION_NAME + " NOTE_TEXT, "
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
   protected Uri getContentUri()
   {
      return Locations.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Locations.DEFAULT_SORT_ORDER;
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
