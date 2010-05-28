package dev.drsoran.moloko.content;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmListsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmListsProviderPart.class.getSimpleName();
   
   

   public RtmListsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "lists" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Lists._ID
         + " INTEGER NOT NULL, " + Lists.NAME + " TEXT, "
         + "CONSTRAINT PK_LISTS PRIMARY KEY ( \"" + Lists._ID + "\" )"
         + "CONSTRAINT taskseries FOREIGN KEY ( \"" + TaskSeries._ID
         + "\" ) REFERENCES taskseries ( \"" + TaskSeries._ID + "\" )" + " );" );
   }
   


   @Override
   protected void fillProjectionMap()
   {
      projectionMap.put( Lists._ID, Lists._ID );
      projectionMap.put( Lists.NAME, Lists.NAME );
   }
   


   @Override
   protected String getContentItemType()
   {
      return Lists.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Lists.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Lists.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Lists.DEFAULT_SORT_ORDER;
   }
}
