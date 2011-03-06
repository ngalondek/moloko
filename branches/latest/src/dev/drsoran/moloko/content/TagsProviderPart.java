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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.provider.Rtm.TaskSeries;


public class TagsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = "Moloko."
      + TagsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Tags._ID, Tags.TASKSERIES_ID, Tags.TAG };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static List< Tag > getAllTags( ContentProviderClient client,
                                               String taskSeriesId )
   {
      List< Tag > tags = null;
      
      if ( !TextUtils.isEmpty( taskSeriesId ) )
      {
         Cursor c = null;
         
         try
         {
            c = client.query( Tags.CONTENT_URI, PROJECTION, Tags.TASKSERIES_ID
               + " = " + taskSeriesId, null, Tags.TAG );
            
            boolean ok = c != null;
            
            if ( ok )
            {
               tags = new ArrayList< Tag >( c.getCount() );
               
               if ( c.getCount() > 0 )
               {
                  
                  for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
                  {
                     tags.add( new Tag( c.getString( COL_INDICES.get( Tags._ID ) ),
                                        c.getString( COL_INDICES.get( Tags.TASKSERIES_ID ) ),
                                        c.getString( COL_INDICES.get( Tags.TAG ) ) ) );
                  }
               }
               
               if ( !ok )
                  tags = null;
            }
         }
         catch ( final RemoteException e )
         {
            Log.e( TAG, "Query tags failed. ", e );
            tags = null;
         }
         finally
         {
            if ( c != null )
               c.close();
         }
      }
      
      return tags;
   }
   


   public final static List< String > getAllTagTexts( ContentProviderClient client,
                                                      String taskSeriesId )
   {
      List< String > tags = null;
      Cursor c = null;
      
      if ( taskSeriesId != null )
      {
         try
         {
            final String[] projection =
            { Tags._ID, Tags.TAG };
            
            c = client.query( Tags.CONTENT_URI, projection, Tags.TASKSERIES_ID
               + " = " + taskSeriesId, null, null );
            
            boolean ok = c != null;
            
            if ( ok )
            {
               tags = new ArrayList< String >( c.getCount() );
               
               if ( c.getCount() > 0 )
               {
                  
                  for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
                  {
                     tags.add( c.getString( 1 ) );
                  }
               }
               
               if ( !ok )
                  tags = null;
            }
         }
         catch ( final RemoteException e )
         {
            Log.e( TAG, "Query tag texts failed. ", e );
            tags = null;
         }
         finally
         {
            if ( c != null )
               c.close();
         }
      }
      
      return tags;
   }
   


   public TagsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Tags.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Tags._ID
         + " INTEGER NOT NULL CONSTRAINT PK_TAGS PRIMARY KEY AUTOINCREMENT, "
         + Tags.TASKSERIES_ID + " TEXT NOT NULL, " + Tags.TAG
         + " TEXT NOT NULL, CONSTRAINT tags_taskseries_ref FOREIGN KEY ( "
         + Tags.TASKSERIES_ID + ") REFERENCES " + TaskSeries.PATH + "( "
         + TaskSeries._ID + " ) );" );
      
      // Trigger: Silently ignores inserts with a taskseries ID
      // and a tag that already exits
      db.execSQL( "CREATE TRIGGER " + path
         + "_ignore_duplicate_insert BEFORE INSERT ON " + path
         + " FOR EACH ROW BEGIN SELECT RAISE ( IGNORE ) WHERE EXISTS ( SELECT "
         + Tags.TASKSERIES_ID + ", " + Tags.TAG + " FROM " + Tags.PATH
         + " WHERE " + Tags.TASKSERIES_ID + " = new." + Tags.TASKSERIES_ID
         + " AND " + Tags.TAG + " = new." + Tags.TAG + "); END;" );
   }
   


   @Override
   protected String getContentItemType()
   {
      return Tags.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Tags.CONTENT_TYPE;
   }
   


   @Override
   public Uri getContentUri()
   {
      return Tags.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Tags.DEFAULT_SORT_ORDER;
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
