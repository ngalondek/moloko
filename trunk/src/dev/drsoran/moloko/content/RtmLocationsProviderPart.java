package dev.drsoran.moloko.content;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Locations;


public class RtmLocationsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmLocationsProviderPart.class.getSimpleName();
   
   

   public RtmLocationsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "locations" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Locations._ID
         + " INTEGER NOT NULL, " + Locations.NAME + " TEXT, "
         + Locations.LONGITUDE + " REAL NOT NULL, " + Locations.LATITUDE
         + " REAL NOT NULL, " + Locations.ADDRESS + " TEXT, "
         + Locations.VIEWABLE + " INTEGER NOT NULL DEFAULT 1, "
         + Locations.ZOOM + " INTEGER, "
         + "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \"" + Locations._ID + "\" )"
         + " );" );
   }
   


   @Override
   protected void fillProjectionMap()
   {
      projectionMap.put( Locations._ID, Locations._ID );
      projectionMap.put( Locations.NAME, Locations.NAME );
      projectionMap.put( Locations.LONGITUDE, Locations.LONGITUDE );
      projectionMap.put( Locations.LATITUDE, Locations.LATITUDE );
      projectionMap.put( Locations.ADDRESS, Locations.ADDRESS );
      projectionMap.put( Locations.VIEWABLE, Locations.VIEWABLE );
      projectionMap.put( Locations.ZOOM, Locations.ZOOM );
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
}
