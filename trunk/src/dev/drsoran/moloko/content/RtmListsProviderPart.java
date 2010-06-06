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

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmListsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmListsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( Lists._ID, 0 );
      COL_INDICES.put( Lists.NAME, 1 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
   }
   
   

   public final static RtmLists getAllLists( ContentProviderClient client ) throws RemoteException
   {
      RtmLists lists = null;
      
      final Cursor c = client.query( Rtm.Lists.CONTENT_URI,
                                     null,
                                     null,
                                     null,
                                     null );
      
      boolean ok = c != null;
      
      if ( ok )
         lists = new RtmLists();
      
      for ( ok = ok && c.moveToFirst(); ok && !c.isAfterLast(); ok = ok
         && c.moveToNext() )
      {
         final RtmList list = new RtmList( c.getString( COL_INDICES.get( Lists._ID ) ),
                                           c.getString( COL_INDICES.get( Lists.NAME ) ) );
         lists.add( list );
      }
      
      if ( c != null )
         c.close();
      
      return lists;
   }
   


   public final static ContentProviderOperation insertOrReplace( RtmList list )
   {
      ContentProviderOperation operation = null;
      
      if ( list.getId() != null && list.getName() != null )
      {
         ContentValues values = new ContentValues();
         
         values.put( Rtm.Lists._ID, list.getId() );
         values.put( Rtm.Lists.NAME, list.getName() );
         
         operation = ContentProviderOperation.newInsert( Rtm.Lists.CONTENT_URI )
                                             .withValues( values )
                                             .build();
      }
      
      return operation;
   }
   


   public final static ContentProviderOperation updateList( String listId,
                                                            ContentValues values )
   {
      return ContentProviderOperation.newUpdate( ContentUris.withAppendedId( Rtm.Lists.CONTENT_URI,
                                                                             Long.parseLong( listId ) ) )
                                     .withValues( values )
                                     .build();
   }
   


   public final static ContentProviderOperation deleteList( String listId )
   {
      return ContentProviderOperation.newDelete( ContentUris.withAppendedId( Rtm.Lists.CONTENT_URI,
                                                                             Long.parseLong( listId ) ) )
                                     .build();
   }
   


   public RtmListsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "lists" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Lists._ID
         + " INTEGER NOT NULL, " + Lists.NAME + " TEXT, "
         + "CONSTRAINT PK_LISTS PRIMARY KEY ( \"" + Lists._ID + "\" ) );" );
      
      // Trigger: If a list gets deleted, move all contained tasks to the
      // Inbox list.
      db.execSQL( "CREATE TRIGGER " + tableName
         + "_delete_list AFTER DELETE ON " + tableName
         + " BEGIN UPDATE taskseries SET " + TaskSeries.LIST_ID
         + " = ( SELECT " + Lists._ID + " FROM " + tableName + " WHERE "
         + Lists.NAME + " like 'Inbox' ); END;" );
      
      // Trigger: The Inbox list should always exist and cannot be
      // deleted.
      db.execSQL( "CREATE TRIGGER "
         + tableName
         + "_inbox_must_survive BEFORE DELETE ON "
         + tableName
         + " BEGIN SELECT RAISE ( ABORT, 'List Inbox must always exist' ) WHERE EXISTS ( SELECT 1 FROM "
         + tableName + " WHERE old." + Lists.NAME + " like 'Inbox' ); END;" );
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
}
