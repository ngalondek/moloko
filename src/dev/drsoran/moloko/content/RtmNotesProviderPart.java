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
import java.util.Date;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmNotesProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmNotesProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Notes._ID, Notes.NOTE_CREATED_DATE, Notes.NOTE_MODIFIED_DATE,
    Notes.NOTE_TITLE, Notes.NOTE_TEXT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( RtmTaskNote note,
                                                       String taskSeriesId,
                                                       boolean withId )
   {
      ContentValues values = null;
      
      if ( note != null && !TextUtils.isEmpty( taskSeriesId ) )
      {
         values = new ContentValues();
         
         if ( withId )
            values.put( Notes._ID, note.getId() );
         
         values.put( Notes.TASKSERIES_ID, taskSeriesId );
         values.put( Notes.NOTE_CREATED_DATE, note.getCreated().getTime() );
         
         if ( note.getModified() != null )
            values.put( Notes.NOTE_MODIFIED_DATE, note.getModified().getTime() );
         else
            values.putNull( Notes.NOTE_MODIFIED_DATE );
         
         if ( !TextUtils.isEmpty( note.getTitle() ) )
            values.put( Notes.NOTE_TITLE, note.getTitle() );
         else
            values.putNull( Notes.NOTE_TITLE );
         
         values.put( Notes.NOTE_TEXT, note.getText() );
      }
      
      return values;
   }
   


   public final static RtmTaskNotes getAllNotes( ContentProviderClient client,
                                                 String taskSeriesId ) throws RemoteException
   {
      RtmTaskNotes notes = null;
      
      boolean ok = taskSeriesId != null;
      
      if ( ok )
      {
         final Cursor c = client.query( Notes.CONTENT_URI,
                                        PROJECTION,
                                        Notes.TASKSERIES_ID + " = "
                                           + taskSeriesId,
                                        null,
                                        null );
         
         ok = c != null;
         
         if ( ok )
         {
            ArrayList< RtmTaskNote > taskNotes = new ArrayList< RtmTaskNote >( c.getCount() );
            
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               final RtmTaskNote taskNote = createNote( c );
               taskNotes.add( taskNote );
            }
            
            notes = new RtmTaskNotes( taskNotes );
         }
         
         if ( c != null )
            c.close();
      }
      
      return notes;
   }
   


   public final static RtmTaskNote getNote( ContentProviderClient client,
                                            String noteId ) throws RemoteException
   {
      RtmTaskNote note = null;
      
      boolean ok = noteId != null;
      
      if ( ok )
      {
         final Cursor c = client.query( Notes.CONTENT_URI,
                                        PROJECTION,
                                        Notes._ID + " = " + noteId,
                                        null,
                                        null );
         
         ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            note = createNote( c );
         }
         
         if ( c != null )
            c.close();
      }
      
      return note;
   }
   


   public final static ContentProviderOperation insertNote( ContentProviderClient client,
                                                            RtmTaskNote note,
                                                            String taskSeriesId ) throws RemoteException
   {
      ContentProviderOperation operation = null;
      
      // Only insert if not exists
      boolean ok = !Queries.exists( client, Notes.CONTENT_URI, note.getId() );
      
      // Check mandatory attributes
      ok = ok && note.getId() != null;
      ok = ok && taskSeriesId != null;
      ok = ok && note.getText() != null;
      
      if ( ok )
      {
         operation = ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                             .withValues( getContentValues( note,
                                                                            taskSeriesId,
                                                                            true ) )
                                             .build();
      }
      
      return operation;
   }
   


   public RtmNotesProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Notes.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Notes._ID
         + " INTEGER NOT NULL, " + Notes.TASKSERIES_ID + " INTEGER NOT NULL, "
         + Notes.NOTE_CREATED_DATE + " INTEGER NOT NULL, "
         + Notes.NOTE_MODIFIED_DATE + " INTEGER, " + Notes.NOTE_TITLE
         + " TEXT, " + Notes.NOTE_TEXT + " TEXT NOT NULL, "
         + "CONSTRAINT PK_NOTES PRIMARY KEY ( \"" + Notes._ID + "\" ), "
         + "CONSTRAINT notes_taskseries_ref FOREIGN KEY ( "
         + Notes.TASKSERIES_ID + " ) REFERENCES " + TaskSeries.PATH + " ( "
         + TaskSeries._ID + " ) );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      if ( !initialValues.containsKey( Notes.NOTE_CREATED_DATE ) )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         initialValues.put( Notes.NOTE_CREATED_DATE, now );
      }
      
      return initialValues;
   }
   


   @Override
   protected String getContentItemType()
   {
      return Notes.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Notes.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Notes.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Notes.DEFAULT_SORT_ORDER;
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
   


   private final static RtmTaskNote createNote( Cursor c )
   {
      return new RtmTaskNote( c.getString( COL_INDICES.get( Notes._ID ) ),
                              new Date( c.getLong( COL_INDICES.get( Notes.NOTE_CREATED_DATE ) ) ),
                              Queries.getOptDate( c,
                                                  COL_INDICES.get( Notes.NOTE_MODIFIED_DATE ) ),
                              Queries.getOptString( c,
                                                    COL_INDICES.get( Notes.NOTE_TITLE ) ),
                              c.getString( COL_INDICES.get( Notes.NOTE_TEXT ) ) );
   }
}
