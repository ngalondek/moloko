package dev.drsoran.moloko.content;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmTasksProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTasksProviderPart.class.getSimpleName();
   
   

   public RtmTasksProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "tasks" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Tasks._ID
         + " INTEGER NOT NULL, " + Tasks.DUE_DATE + " INTEGER, "
         + Tasks.ADDED_DATE + " INTEGER, " + Tasks.COMPLETED_DATE
         + " INTEGER, " + Tasks.DELETED_DATE + " INTEGER, " + Tasks.PRIORITY
         + " INTEGER CHECK ( " + Tasks.PRIORITY + " BETWEEN 0 AND 3 " + "), "
         + Tasks.POSTPONED + " INTEGER DEFAULT 0, " + Tasks.ESTIMATE
         + " TEXT, " + "CONSTRAINT PK_TASKS PRIMARY KEY ( \"" + Tasks._ID
         + "\" )" + " );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      // Make sure that the fields are all set
      if ( initialValues.containsKey( Tasks.ADDED_DATE ) == false )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         
         initialValues.put( Tasks.ADDED_DATE, now );
      }
      
      return initialValues;
   }
   


   @Override
   protected void fillProjectionMap()
   {
      projectionMap.put( Tasks._ID, Tasks._ID );
      projectionMap.put( Tasks.DUE_DATE, Tasks.DUE_DATE );
      projectionMap.put( Tasks.ADDED_DATE, Tasks.ADDED_DATE );
      projectionMap.put( Tasks.COMPLETED_DATE, Tasks.COMPLETED_DATE );
      projectionMap.put( Tasks.DELETED_DATE, Tasks.DELETED_DATE );
      projectionMap.put( Tasks.PRIORITY, Tasks.PRIORITY );
      projectionMap.put( Tasks.POSTPONED, Tasks.POSTPONED );
      projectionMap.put( Tasks.ESTIMATE, Tasks.ESTIMATE );
   }
   


   @Override
   protected String getContentItemType()
   {
      return Tasks.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Tasks.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Tasks.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Tasks.DEFAULT_SORT_ORDER;
   }
}
