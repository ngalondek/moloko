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
   
   public final static String[] PROJECTION =
   { Locations._ID, Locations.NAME, Locations.LONGITUDE, Locations.LATITUDE,
    Locations.ADDRESS, Locations.VIEWABLE, Locations.ZOOM };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
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
   


   public String[] getProjection()
   {
      return PROJECTION;
   }
}
