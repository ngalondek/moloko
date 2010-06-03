package dev.drsoran.moloko.content;

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
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmListsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmListsProviderPart.class.getSimpleName();
   
   

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
         final RtmList list = new RtmList( c.getString( 0 ), c.getString( 1 ) );
         lists.add( list );
      }
      
      if ( c != null )
         c.close();
      
      return lists;
   }
   


   public final static ContentProviderOperation insertOrReplace( RtmList list )
   {
      ContentValues values = new ContentValues();
      
      values.put( Rtm.Lists._ID, list.getId() );
      values.put( Rtm.Lists.NAME, list.getName() );
      
      return ContentProviderOperation.newInsert( Rtm.Lists.CONTENT_URI )
                                     .withValues( values )
                                     .build();
   }
   


   public final static ContentProviderOperation addTaskSeries( String listId,
                                                               RtmTaskSeries taskSeries )
   {
      return ContentProviderOperation.newUpdate( ContentUris.withAppendedId( Rtm.Lists.CONTENT_URI,
                                                                             Long.parseLong( listId ) ) )
                                     .withValue( Rtm.Lists.TASKSERIES_ID,
                                                 taskSeries.getId() )
                                     .build();
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
