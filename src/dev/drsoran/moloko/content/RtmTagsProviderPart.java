package dev.drsoran.moloko.content;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Tags;


public class RtmTagsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTagsProviderPart.class.getSimpleName();
   
   

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
   protected void fillProjectionMap()
   {
      projectionMap.put( Tags._ID, Tags._ID );
      projectionMap.put( Tags.TAG, Tags.TAG );
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
}
