/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Settings;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.RtmSmartFilter;


public class RtmListsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = "Moloko."
      + RtmListsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Lists._ID, Lists.RTM_ID, Lists.LIST_NAME, Lists.CREATED_DATE,
    Lists.MODIFIED_DATE, Lists.LIST_DELETED, Lists.LOCKED, Lists.ARCHIVED,
    Lists.POSITION, Lists.IS_SMART_LIST, Lists.FILTER };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( RtmList list )
   {
      final ContentValues values = new ContentValues();
      
      values.put( Lists.RTM_ID, list.getRtmId() );
      values.put( Lists.LIST_NAME, list.getName() );
      
      if ( list.getCreatedDate() != null )
         values.put( Lists.CREATED_DATE, list.getCreatedDate().getTime() );
      else
         values.putNull( Lists.CREATED_DATE );
      
      if ( list.getModifiedDate() != null )
         values.put( Lists.MODIFIED_DATE, list.getModifiedDate().getTime() );
      else
         values.putNull( Lists.MODIFIED_DATE );
      
      if ( list.getDeletedDate() != null )
         values.put( Lists.LIST_DELETED, list.getDeletedDate().getTime() );
      else
         values.putNull( Lists.LIST_DELETED );
      
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
   


   public final static RtmList getList( ContentProviderClient client,
                                        String rtmId )
   {
      RtmList list = null;
      Cursor c = null;
      
      try
      {
         // Only non-deleted lists
         c = client.query( Lists.CONTENT_URI, PROJECTION, Lists.RTM_ID + "="
            + rtmId, null, null );
         
         boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
            list = createList( c );
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query list failed. ", e );
         list = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return list;
   }
   


   public final static RtmLists getAllLists( ContentProviderClient client,
                                             String selection )
   {
      RtmLists lists = null;
      Cursor c = null;
      
      try
      {
         // Only non-deleted lists
         c = client.query( Rtm.Lists.CONTENT_URI,
                           PROJECTION,
                           new StringBuilder( "(" ).append( selection != null
                                                                             ? selection
                                                                             : "1" )
                                                   .append( " ) AND " )
                                                   .append( Lists.LIST_DELETED )
                                                   .append( " IS NULL" )
                                                   .toString(),
                           null,
                           null );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            lists = new RtmLists();
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final RtmList list = createList( c );
                  lists.add( list );
               }
            }
         }
         
         if ( !ok )
            lists = null;
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query lists failed. ", e );
         lists = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return lists;
   }
   


   public RtmListsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Lists.PATH );
   }
   


   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      super.create( db );
      
      db.execSQL( "CREATE TABLE " + path + " ( " + Lists._ID
         + " INTEGER NOT NULL AUTOINCREMENT, " + Lists.RTM_ID
         + " TEXT NOT NULL, " + Lists.LIST_NAME + " TEXT NOT NULL, "
         + Lists.CREATED_DATE + " INTEGER, " + Lists.MODIFIED_DATE
         + " INTEGER, " + Lists.LIST_DELETED + " INTEGER, " + Lists.LOCKED
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.ARCHIVED
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.POSITION
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.IS_SMART_LIST
         + " INTEGER NOT NULL DEFAULT 0, " + Lists.FILTER + " TEXT, "
         + "CONSTRAINT PK_LISTS PRIMARY KEY ( \"" + Lists._ID + "\" ) );" );
      
      // Trigger: If a list gets deleted, move all contained tasks to the
      // Inbox list.
      db.execSQL( "CREATE TRIGGER " + path + "_delete_list AFTER DELETE ON "
         + path + " BEGIN UPDATE " + TaskSeries.PATH + " SET "
         + TaskSeries.LIST_ID + " = ( SELECT " + Lists._ID + " FROM " + path
         + " WHERE " + Lists.LIST_NAME + " like 'Inbox' );" + " SET "
         + TaskSeries.RTM_LIST_ID + " = ( SELECT " + Lists.RTM_ID + " FROM "
         + path + " WHERE " + Lists.LIST_NAME + " like 'Inbox' );" + " END;" );
      
      // Trigger: If a list gets deleted, check the default list setting
      db.execSQL( "CREATE TRIGGER " + path + "_default_list AFTER DELETE ON "
         + path + " BEGIN UPDATE " + Settings.PATH + " SET "
         + Settings.DEFAULTLIST_ID + " = NULL WHERE old." + Lists.RTM_ID
         + " = " + Settings.DEFAULTLIST_ID + "; END;" );
      
      // Trigger: A locked list should always exist and cannot be
      // deleted.
      /*
       * db.execSQL( "CREATE TRIGGER " + path + "_locked_must_survive BEFORE DELETE ON " + path +
       * " BEGIN SELECT RAISE ( ABORT, 'A locked list must always exist' ) WHERE EXISTS ( SELECT 1 FROM " + path +
       * " WHERE old." + Lists.LOCKED + " != 0 ); END;" );
       */
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
   public Uri getContentUri()
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
   


   private static RtmList createList( Cursor c )
   {
      RtmSmartFilter filter = null;
      
      if ( !c.isNull( COL_INDICES.get( Lists.FILTER ) ) )
         filter = new RtmSmartFilter( c.getString( COL_INDICES.get( Lists.FILTER ) ) );
      
      final RtmList list = new RtmList( c.getLong( COL_INDICES.get( Lists._ID ) ),
                                        c.getString( COL_INDICES.get( Lists.RTM_ID ) ),
                                        c.getString( COL_INDICES.get( Lists.LIST_NAME ) ),
                                        Queries.getOptDate( c,
                                                            COL_INDICES.get( Lists.CREATED_DATE ) ),
                                        Queries.getOptDate( c,
                                                            COL_INDICES.get( Lists.MODIFIED_DATE ) ),
                                        Queries.getOptDate( c,
                                                            COL_INDICES.get( Lists.LIST_DELETED ) ),
                                        c.getInt( COL_INDICES.get( Lists.LOCKED ) ),
                                        c.getInt( COL_INDICES.get( Lists.ARCHIVED ) ),
                                        c.getInt( COL_INDICES.get( Lists.POSITION ) ),
                                        filter );
      return list;
   }
}
