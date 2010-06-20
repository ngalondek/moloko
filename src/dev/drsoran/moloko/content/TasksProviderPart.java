package dev.drsoran.moloko.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;


public class TasksProviderPart extends AbstractProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = TasksProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Tasks._ID, Tasks.LIST_ID, Tasks.LIST_NAME, Tasks.TASKSERIES_CREATED_DATE,
    Tasks.MODIFIED_DATE, Tasks.TASKSERIES_NAME, Tasks.SOURCE, Tasks.URL,
    Tasks.RAW_TASK_ID, Tasks.DUE_DATE, Tasks.ADDED_DATE, Tasks.COMPLETED_DATE,
    Tasks.DELETED_DATE, Tasks.PRIORITY, Tasks.POSTPONED, Tasks.ESTIMATE,
    Tasks.LOCATION_ID, Tasks.LOCATION_NAME, Tasks.LONGITUDE, Tasks.LATITUDE,
    Tasks.ADDRESS, Tasks.VIEWABLE, Tasks.ZOOM };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String subQuery;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      subQuery = SQLiteQueryBuilder.buildQueryString( false, // not distinct
                                                      TaskSeries.PATH + ","
                                                         + Lists.PATH + ","
                                                         + RawTasks.PATH,
                                                      new String[]
                                                      {
                                                       TaskSeries.PATH + "."
                                                          + TaskSeries._ID
                                                          + " AS _id",
                                                       Tasks.LIST_ID,
                                                       Tasks.LIST_NAME,
                                                       Tasks.TASKSERIES_CREATED_DATE,
                                                       Tasks.MODIFIED_DATE,
                                                       Tasks.TASKSERIES_NAME,
                                                       Tasks.SOURCE, Tasks.URL,
                                                       Tasks.DUE_DATE,
                                                       Tasks.ADDED_DATE,
                                                       Tasks.COMPLETED_DATE,
                                                       Tasks.DELETED_DATE,
                                                       Tasks.PRIORITY,
                                                       Tasks.POSTPONED,
                                                       Tasks.ESTIMATE,
                                                       Tasks.LOCATION_ID },
                                                      TaskSeries.PATH
                                                         + "."
                                                         + TaskSeries.LIST_ID
                                                         + "="
                                                         + Lists.PATH
                                                         + "."
                                                         + Lists._ID
                                                         + " AND "
                                                         + TaskSeries.PATH
                                                         + "."
                                                         + TaskSeries.RAW_TASK_ID
                                                         + "=" + RawTasks.PATH
                                                         + "." + RawTasks._ID,
                                                      null,
                                                      null,
                                                      null,
                                                      null );
   }
   
   

   public TasksProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Tasks.PATH );
   }
   


   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      Cursor cursor = null;
      
      final List< String > projectionList = Arrays.asList( projection );
      
      boolean withLocation = false;
      boolean withTags = false;
      int idColumnIdx = -1;
      
      for ( int i = 0; i < projection.length
         && ( idColumnIdx == -1 || !withLocation || !withTags ); i++ )
      {
         final String column = projection[ i ];
         
         if ( idColumnIdx == -1 && column.equals( Tasks._ID ) )
         {
            idColumnIdx = i;
         }
         else if ( !withLocation
            && ( column.equals( Tasks.LOCATION_ID )
               || column.equals( Tasks.LOCATION_NAME )
               || column.equals( Tasks.LONGITUDE )
               || column.equals( Tasks.LATITUDE )
               || column.equals( Tasks.ADDRESS )
               || column.equals( Tasks.VIEWABLE ) || column.equals( Tasks.ZOOM ) ) )
            withLocation = true;
      }
      
      // In case of a join with the locations table the _id gets ambiguous. So
      // we have to qualify it.
      if ( idColumnIdx != -1 && withLocation )
      {
         projectionList.set( idColumnIdx,
                             new StringBuilder( "subQuery." ).append( TaskSeries._ID )
                                                             .append( " AS " )
                                                             .append( Tasks._ID )
                                                             .toString() );
      }
      
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( subQuery )
                                                                        .append( ") AS subQuery" );
      
      if ( withLocation )
         stringBuilder.append( " LEFT OUTER JOIN " )
                      .append( Locations.PATH )
                      .append( " ON " )
                      .append( Locations.PATH )
                      .append( "." )
                      .append( Locations._ID )
                      .append( " = subQuery." )
                      .append( TaskSeries.LOCATION_ID );
      
      // Only if the ID is given in the projection we can use it
      if ( id != null && idColumnIdx != -1 )
      {
         stringBuilder.append( " WHERE subQuery." )
                      .append( TaskSeries._ID )
                      .append( " = " )
                      .append( id );
      }
      else
      {
         // TODO: Throw exception in this case
      }
      
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
      
      final String query = stringBuilder.toString();
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      cursor = db.rawQuery( query, null );
      
      return cursor;
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
   


   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   


   public String[] getProjection()
   {
      return PROJECTION;
   }
   


   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
}
