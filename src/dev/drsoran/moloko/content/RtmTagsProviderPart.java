package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Tags;


public class RtmTagsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTagsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( Tags._ID, 0 );
      COL_INDICES.put( Tags.TAG, 1 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
   }
   
   

   public RtmTagsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "tags" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Tags._ID
         + " INTEGER NOT NULL CONSTRAINT PK_TAGS PRIMARY KEY AUTOINCREMENT, "
         + Tags.TAG + " TEXT NOT NULL );" );
   }
   


   @Override
   protected String getContentItemType()
   {
      return Tags.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Tags.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Tags.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Tags.DEFAULT_SORT_ORDER;
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
