package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Locations;


public class RtmLocationsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmLocationsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( Locations._ID, 0 );
      COL_INDICES.put( Locations.NAME, 1 );
      COL_INDICES.put( Locations.LONGITUDE, 2 );
      COL_INDICES.put( Locations.LATITUDE, 3 );
      COL_INDICES.put( Locations.ADDRESS, 4 );
      COL_INDICES.put( Locations.VIEWABLE, 5 );
      COL_INDICES.put( Locations.ZOOM, 6 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
   }
   
   

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
}
