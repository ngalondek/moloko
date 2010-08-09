package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.RtmSmartFilter;


public class RtmListsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = RtmListsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Lists._ID, Lists.LIST_NAME, Lists.LIST_DELETED, Lists.LOCKED, Lists.ARCHIVED,
    Lists.POSITION, Lists.IS_SMART_LIST, Lists.FILTER };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static RtmLists getAllLists( ContentProviderClient client )
   {
      RtmLists lists = null;
      
      try
      {
         final Cursor c = client.query( Rtm.Lists.CONTENT_URI,
                                        PROJECTION,
                                        null,
                                        null,
                                        null );
         
         lists = new RtmLists();
         
         boolean ok = true;
         
         if ( c.getCount() > 0 )
         {
            RtmSmartFilter filter = null;

            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               if ( !c.isNull( COL_INDICES.get( Lists.FILTER ) ) )
                  filter = new RtmSmartFilter( c.getString( COL_INDICES.get( Lists.FILTER ) ) );
               
               final RtmList list = new RtmList( c.getString( COL_INDICES.get( Lists._ID ) ),
                                                 c.getString( COL_INDICES.get( Lists.LIST_NAME ) ),
                                                 c.getInt( COL_INDICES.get( Lists.LIST_DELETED ) ),
                                                 c.getInt( COL_INDICES.get( Lists.LOCKED ) ),
                                                 c.getInt( COL_INDICES.get( Lists.ARCHIVED ) ),
                                                 c.getInt( COL_INDICES.get( Lists.POSITION ) ),
                                                 filter );
               lists.add( list );
            }
         }
         if ( !ok )
            lists = null;
         
         c.close();
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query lists failed. ", e );
         lists = null;
      }
      
      return lists;
   }
   


   public final static ContentValues getContentValues( RtmList list,
                                                       boolean withId )
   {
      ContentValues values = new ContentValues();
      
      if ( withId )
         values.put( Rtm.Lists._ID, list.getId() );
      
      values.put( Lists.LIST_NAME, list.getName() );
      values.put( Lists.LIST_DELETED, list.getDeleted() );
      values.put( Lists.LOCKED, list.getLocked() );
      values.put( Lists.ARCHIVED, list.getArchived() );
      values.put( Lists.POSITION, list.getPosition() );
      
      final RtmSmartFilter filter = list.getSmartFilter();
      
      if ( filter != null )
      {
         values.put( Lists.IS_SMART_LIST, 1 );
         values.put( Lists.FILTER, filter.getFilterString() );
      }
      else
      {
         values.put( Lists.IS_SMART_LIST, 0 );
         values.putNull( Lists.FILTER );
      }
      
      return values;
   }
   


   public final static ContentProviderOperation insert( ContentProviderClient client,
                                                        RtmList list )
   {
      ContentProviderOperation operation = null;
      
      try
      {
         if ( list.getId() != null && list.getName() != null
            && !Queries.exists( client, Lists.CONTENT_URI, list.getId() ) )
         {
            operation = ContentProviderOperation.newInsert( Rtm.Lists.CONTENT_URI )
                                                .withValues( getContentValues( list,
                                                                               true ) )
                                                .build();
         }
      }
      catch ( RemoteException e )
      {
         operation = null;
      }
      
      return operation;
   }
   


   public final static ContentProviderOperation deleteList( String listId )
   {
      return ContentProviderOperation.newDelete( ContentUris.withAppendedId( Rtm.Lists.CONTENT_URI,
                                                                             Long.parseLong( listId ) ) )
                                     .build();
   }
   


   public RtmListsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Lists.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Lists._ID
         + " INTEGER NOT NULL, " + Lists.LIST_NAME + " TEXT NOT NULL, "
         + Lists.LIST_DELETED + " INTEGER NOT NULL DEFAULT 0, " + Lists.LOCKED
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.ARCHIVED
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.POSITION
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.IS_SMART_LIST
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.FILTER + " TEXT, "
         + "CONSTRAINT PK_LISTS PRIMARY KEY ( \"" + Lists._ID + "\" ) );" );
      
      // Trigger: If a list gets deleted, move all contained tasks to the
      // Inbox list.
      db.execSQL( "CREATE TRIGGER " + path + "_delete_list AFTER DELETE ON "
         + path + " BEGIN UPDATE taskseries SET " + TaskSeries.LIST_ID
         + " = ( SELECT " + Lists._ID + " FROM " + path + " WHERE "
         + Lists.LIST_NAME + " like 'Inbox' ); END;" );
      
      // Trigger: A locked list should always exist and cannot be
      // deleted.
      db.execSQL( "CREATE TRIGGER "
         + path
         + "_locked_must_survive BEFORE DELETE ON "
         + path
         + " BEGIN SELECT RAISE ( ABORT, 'A locked list must always exist' ) WHERE EXISTS ( SELECT 1 FROM "
         + path + " WHERE old." + Lists.LOCKED + " != 0 ); END;" );
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
