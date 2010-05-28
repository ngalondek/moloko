package dev.drsoran.moloko.content;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmTaskSeriesProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTaskSeriesProviderPart.class.getSimpleName();
   
   

   public RtmTaskSeriesProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "taskseries" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + TaskSeries._ID
         + " INTEGER NOT NULL, " + TaskSeries.CREATED_DATE
         + " INTEGER NOT NULL, " + TaskSeries.MODIFIED_DATE + " INTEGER, "
         + TaskSeries.NAME + " TEXT, " + TaskSeries.SOURCE + " TEXT, "
         + TaskSeries.URL + " TEXT, "
         + "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \"" + TaskSeries._ID
         + "\" )" + "CONSTRAINT notes FOREIGN KEY ( \"" + Notes._ID
         + "\" ) REFERENCES notes ( \"" + Notes._ID + "\" )"
         + "CONSTRAINT tags FOREIGN KEY ( \"" + Tags._ID
         + "\" ) REFERENCES tags ( \"" + Tags._ID + "\" )"
         + "CONSTRAINT task FOREIGN KEY ( \"" + Tasks._ID
         + "\" ) REFERENCES task ( \"" + Tasks._ID + "\" )"
         + "CONSTRAINT location FOREIGN KEY ( \"" + Locations._ID
         + "\" ) REFERENCES location ( \"" + Locations._ID + "\" )" + " );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      if ( !initialValues.containsKey( TaskSeries.CREATED_DATE ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         initialValues.put( TaskSeries.CREATED_DATE, now );
      }
      
      return initialValues;
   }
   


   @Override
   protected void fillProjectionMap()
   {
      projectionMap.put( TaskSeries._ID, TaskSeries._ID );
      projectionMap.put( TaskSeries.CREATED_DATE, TaskSeries.CREATED_DATE );
      projectionMap.put( TaskSeries.MODIFIED_DATE, TaskSeries.MODIFIED_DATE );
      projectionMap.put( TaskSeries.NAME, TaskSeries.NAME );
      projectionMap.put( TaskSeries.SOURCE, TaskSeries.SOURCE );
      projectionMap.put( TaskSeries.URL, TaskSeries.URL );
      projectionMap.put( TaskSeries.NOTES_ID, TaskSeries.NOTES_ID );
      projectionMap.put( TaskSeries.TAGS_ID, TaskSeries.TAGS_ID );
      projectionMap.put( TaskSeries.TASKS_ID, TaskSeries.TASKS_ID );
      projectionMap.put( TaskSeries.LOCATIONS_ID, TaskSeries.LOCATIONS_ID );
   }
   


   @Override
   protected String getContentItemType()
   {
      return TaskSeries.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return TaskSeries.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return TaskSeries.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return TaskSeries.DEFAULT_SORT_ORDER;
   }
}
