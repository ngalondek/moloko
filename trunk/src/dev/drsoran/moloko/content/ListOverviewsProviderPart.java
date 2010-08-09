package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;


public class ListOverviewsProviderPart extends AbstractProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = ListOverviewsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { ListOverviews._ID, ListOverviews.LIST_NAME, ListOverviews.LIST_DELETED,
    ListOverviews.LOCKED, ListOverviews.ARCHIVED, ListOverviews.POSITION,
    ListOverviews.IS_SMART_LIST, ListOverviews.FILTER, ListOverviews.TASKS_COUNT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String query;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      query = "SELECT " + Lists.PATH + ".*, count( " + TaskSeries.PATH + "."
         + TaskSeries._ID + " ) AS " + ListOverviews.TASKS_COUNT + " FROM "
         + Lists.PATH + " LEFT OUTER JOIN " + TaskSeries.PATH + " ON "
         + Lists.PATH + "." + Lists._ID + " = " + TaskSeries.PATH + "."
         + TaskSeries.LIST_ID + " GROUP BY " + Lists.LIST_NAME;
   }
   
   

   public ListOverviewsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, ListOverviews.PATH );
   }
   


   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( Queries.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( query )
                                                                        .append( ")" );
      
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
      
      final String finalQuery = stringBuilder.toString();
      
      // Get the database and run the query
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      final Cursor cursor = db.rawQuery( finalQuery, null );
      
      return cursor;
   }
   


   @Override
   protected String getContentItemType()
   {
      return null;
   }
   


   @Override
   protected String getContentType()
   {
      return ListOverviews.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return ListOverviews.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return ListOverviews.DEFAULT_SORT_ORDER;
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
