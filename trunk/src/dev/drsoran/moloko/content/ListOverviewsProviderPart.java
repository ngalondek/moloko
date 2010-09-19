package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public class ListOverviewsProviderPart extends AbstractProviderPart
{
   private static final String TAG = ListOverviewsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { ListOverviews._ID, ListOverviews.LIST_NAME, ListOverviews.LIST_DELETED,
    ListOverviews.LOCKED, ListOverviews.ARCHIVED, ListOverviews.POSITION,
    ListOverviews.IS_SMART_LIST, ListOverviews.FILTER,
    ListOverviews.TASKS_COUNT };
   
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
   
   

   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver().registerContentObserver( Lists.CONTENT_URI,
                                                            true,
                                                            observer );
      context.getContentResolver()
             .registerContentObserver( TaskSeries.CONTENT_URI, true, observer );
   }
   


   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   


   public final static ArrayList< RtmListWithTaskCount > getListsOverview( ContentProviderClient client )
   {
      ArrayList< RtmListWithTaskCount > lists = null;
      
      try
      {
         final Cursor c = client.query( Rtm.ListOverviews.CONTENT_URI,
                                        PROJECTION,
                                        null,
                                        null,
                                        Rtm.ListOverviews.DEFAULT_SORT_ORDER );
         
         lists = new ArrayList< RtmListWithTaskCount >();
         
         boolean ok = true;
         
         if ( c.getCount() > 0 )
         {
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               RtmSmartFilter filter = null;
               
               if ( !c.isNull( COL_INDICES.get( Lists.FILTER ) ) )
                  filter = new RtmSmartFilter( c.getString( COL_INDICES.get( Lists.FILTER ) ) );
               
               final RtmListWithTaskCount list = new RtmListWithTaskCount( c.getString( COL_INDICES.get( ListOverviews._ID ) ),
                                                                           c.getString( COL_INDICES.get( ListOverviews.LIST_NAME ) ),
                                                                           c.getInt( COL_INDICES.get( ListOverviews.LIST_DELETED ) ),
                                                                           c.getInt( COL_INDICES.get( ListOverviews.LOCKED ) ),
                                                                           c.getInt( COL_INDICES.get( ListOverviews.ARCHIVED ) ),
                                                                           c.getInt( COL_INDICES.get( ListOverviews.POSITION ) ),
                                                                           filter,
                                                                           ( filter != null )
                                                                                             ? getSmartFilterTaskCount( client,
                                                                                                                        filter )
                                                                                             : c.getInt( COL_INDICES.get( ListOverviews.TASKS_COUNT ) ) );
               
               lists.add( list );
            }
         }
         
         if ( !ok )
            lists = null;
         
         c.close();
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query lists overview failed. ", e );
         lists = null;
      }
      
      return lists;
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
   


   private final static int getSmartFilterTaskCount( ContentProviderClient client,
                                                     RtmSmartFilter filter )
   {
      int taskCount = -1;
      
      final String evalFilter = filter.getEvaluatedFilterString();
      
      boolean badFilter = evalFilter == null;
      
      if ( !badFilter )
      {
         try
         {
            final Cursor smartListTasks = client.query( Tasks.CONTENT_URI,
                                                        new String[]
                                                        { Tasks._ID },
                                                        evalFilter,
                                                        null,
                                                        null );
            
            taskCount = smartListTasks.getCount();
            smartListTasks.close();
         }
         catch ( SQLiteException e )
         {
            Log.e( TAG, "Tried to query with Bad RTM filter: " + evalFilter, e );
            badFilter = true;
         }
         catch ( RemoteException e )
         {
            Log.e( TAG, "Unable to query tasks with filter.", e );
            badFilter = true;
         }
      }
      
      if ( badFilter )
      {
         taskCount = -1;
      }
      
      return taskCount;
   }
}
