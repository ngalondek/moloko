/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.Tag;


public class TagsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = TagsProviderPart.class.getSimpleName();
   
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
   
   

   public final static ContentValues getContentValues( Tag tag, boolean withId )
   {
      ContentValues values = null;
      
      if ( tag != null && tag.getTag() != null && tag.getTaskSeriesId() != null )
      {
         values = new ContentValues();
         
         if ( withId )
            if ( tag.getId() != null )
               values.put( Tags._ID, tag.getId() );
            else
               values.putNull( Tags._ID );
         
         values.put( Tags.TASKSERIES_ID, tag.getTaskSeriesId() );
         values.put( Tags.TAG, tag.getTag() );
      }
      
      return values;
   }
   


   public final static ArrayList< Tag > getAllTags( ContentProviderClient client,
                                                    String taskSeriesId )
   {
      ArrayList< Tag > tags = null;
      
      if ( taskSeriesId != null )
      {
         try
         {
            final Cursor c = client.query( Tags.CONTENT_URI,
                                           PROJECTION,
                                           Tags.TASKSERIES_ID + " = "
                                              + taskSeriesId,
                                           null,
                                           Tags.TAG + " ASC " );
            
            tags = new ArrayList< Tag >( c.getCount() );
            
            boolean ok = true;
            
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
            
            c.close();
         }
         catch ( RemoteException e )
         {
            tags = null;
         }
      }
      
      return tags;
   }
   


   public final static ArrayList< String > getAllTagTexts( ContentProviderClient client,
                                                           String taskSeriesId )
   {
      ArrayList< String > tags = null;
      
      if ( taskSeriesId != null )
      {
         try
         {
            final String[] projection =
            { Tags._ID, Tags.TAG };
            
            final Cursor c = client.query( Tags.CONTENT_URI,
                                           projection,
                                           Tags.TASKSERIES_ID + " = "
                                              + taskSeriesId,
                                           null,
                                           null );
            
            tags = new ArrayList< String >( c.getCount() );
            
            boolean ok = true;
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  tags.add( c.getString( 1 ) );
               }
            }
            
            if ( !ok )
               tags = null;
            
            c.close();
         }
         catch ( RemoteException e )
         {
            tags = null;
         }
      }
      
      return tags;
   }
   


   public TagsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Tags.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Tags._ID
         + " INTEGER NOT NULL CONSTRAINT PK_TAGS PRIMARY KEY AUTOINCREMENT, "
         + Tags.TASKSERIES_ID + " INTEGER NOT NULL, " + Tags.TAG
         + " TEXT NOT NULL, CONSTRAINT taskseries_1 FOREIGN KEY ( "
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
   protected Uri getContentUri()
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
