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
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmNotesProviderPart extends AbstractModificationsRtmProviderPart
{
   private static final String TAG = "Moloko."
      + RtmNotesProviderPart.class.getSimpleName();
   
   
   public final static class NewNoteId
   {
      public String noteId;
   }
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Notes._ID, Notes.TASKSERIES_ID, Notes.NOTE_CREATED_DATE,
    Notes.NOTE_MODIFIED_DATE, Notes.NOTE_DELETED, Notes.NOTE_TITLE,
    Notes.NOTE_TEXT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractModificationsRtmProviderPart.initProjectionDependent( PROJECTION,
                                                                    PROJECTION_MAP,
                                                                    COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( RtmTaskNote note,
                                                       boolean withId )
   {
      final ContentValues values = new ContentValues();
      
      if ( withId )
         values.put( Notes._ID, note.getId() );
      
      values.put( Notes.TASKSERIES_ID, note.getTaskSeriesId() );
      values.put( Notes.NOTE_CREATED_DATE, note.getCreatedDate().getTime() );
      
      if ( note.getModifiedDate() != null )
         values.put( Notes.NOTE_MODIFIED_DATE, note.getModifiedDate().getTime() );
      else
         values.putNull( Notes.NOTE_MODIFIED_DATE );
      if ( note.getDeletedDate() != null )
         values.put( Notes.NOTE_DELETED, note.getDeletedDate().getTime() );
      else
         values.putNull( Notes.NOTE_DELETED );
      
      if ( !TextUtils.isEmpty( note.getTitle() ) )
         values.put( Notes.NOTE_TITLE, note.getTitle() );
      else
         values.putNull( Notes.NOTE_TITLE );
      
      if ( !TextUtils.isEmpty( note.getText() ) )
         values.put( Notes.NOTE_TEXT, note.getText() );
      else
         values.putNull( Notes.NOTE_TEXT );
      
      return values;
   }
   
   
   
   public final static List< RtmTaskNote > getAllNotes( ContentProviderClient client )
   {
      List< RtmTaskNote > notes = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Notes.CONTENT_URI, PROJECTION, null, null, null );
         
         if ( c != null )
         {
            notes = fromCursor( c );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query notes failed. ", e );
         notes = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return notes;
   }
   
   
   
   public final static RtmTaskNotes getNotes( ContentProviderClient client,
                                              String taskSeriesId )
   {
      RtmTaskNotes notes = null;
      Cursor c = null;
      
      try
      {
         // Only non-deleted notes
         c = client.query( Notes.CONTENT_URI,
                           PROJECTION,
                           new StringBuilder( Notes.TASKSERIES_ID ).append( "=" )
                                                                   .append( taskSeriesId )
                                                                   .append( " AND " )
                                                                   .append( Notes.NOTE_DELETED )
                                                                   .append( " IS NULL" )
                                                                   .toString(),
                           null,
                           null );
         
         if ( c != null )
         {
            final List< RtmTaskNote > taskNotes = fromCursor( c );
            notes = new RtmTaskNotes( taskNotes );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query notes failed. ", e );
         notes = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return notes;
   }
   
   
   
   public final static RtmTaskNote getNote( ContentProviderClient client,
                                            String noteId )
   {
      RtmTaskNote note = null;
      Cursor c = null;
      
      try
      {
         // Only non-deleted notes
         c = client.query( Notes.CONTENT_URI,
                           PROJECTION,
                           new StringBuilder( Notes._ID ).append( "=" )
                                                         .append( noteId )
                                                         .append( " AND " )
                                                         .append( Notes.NOTE_DELETED )
                                                         .append( " IS NULL" )
                                                         .toString(),
                           null,
                           null );
         
         if ( c != null && c.moveToFirst() )
         {
            note = createNote( c );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "Query note failed. ", e );
         note = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return note;
   }
   
   
   
   public final static List< RtmTaskNote > getLocalCreatedNotes( ContentProviderClient client )
   {
      List< RtmTaskNote > notes = null;
      
      final List< Creation > creations = CreationsProviderPart.getCreations( client,
                                                                             Notes.CONTENT_URI );
      
      if ( creations != null )
      {
         if ( creations.size() == 0 )
            notes = new ArrayList< RtmTaskNote >( 0 );
         else
         {
            final String selection = Queries.toColumnList( creations,
                                                           Notes._ID,
                                                           " OR " );
            Cursor c = null;
            
            try
            {
               c = client.query( Notes.CONTENT_URI,
                                 PROJECTION,
                                 selection,
                                 null,
                                 null );
               
               if ( c != null )
               {
                  notes = fromCursor( c );
               }
            }
            catch ( final RemoteException e )
            {
               Log.e( TAG, "Query notes failed. ", e );
               notes = null;
            }
            finally
            {
               if ( c != null )
                  c.close();
            }
         }
      }
      
      return notes;
   }
   
   
   
   public final static int getDeletedNotesCount( ContentProviderClient client )
   {
      int cnt = -1;
      
      Cursor c = null;
      
      try
      {
         c = client.query( Notes.CONTENT_URI, new String[]
         { Notes._ID }, Notes.NOTE_DELETED + " IS NOT NULL", null, null );
         
         if ( c != null )
            cnt = c.getCount();
      }
      catch ( final RemoteException e )
      {
         Log.e( TAG, "Query notes failed. ", e );
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return cnt;
   }
   
   
   
   public final static List< RtmTaskNote > getDeletedNotes( ContentProviderClient client )
   {
      List< RtmTaskNote > notes = null;
      
      Cursor c = null;
      
      try
      {
         c = client.query( Notes.CONTENT_URI, PROJECTION, Notes.NOTE_DELETED
            + " IS NOT NULL", null, null );
         
         if ( c != null )
         {
            notes = fromCursor( c );
         }
      }
      catch ( final RemoteException e )
      {
         Log.e( TAG, "Query notes failed. ", e );
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return notes;
   }
   
   
   
   public final static List< RtmTaskNote > fromCursor( Cursor cursor )
   {
      final List< RtmTaskNote > notes = new ArrayList< RtmTaskNote >( cursor.getCount() );
      
      if ( cursor.getCount() > 0 )
      {
         for ( boolean ok = cursor.moveToFirst(); ok && !cursor.isAfterLast(); cursor.moveToNext() )
         {
            final RtmTaskNote note = createNote( cursor );
            ok = note != null;
            
            if ( ok )
            {
               notes.add( note );
            }
         }
      }
      
      return notes;
   }
   
   
   
   public final static NewNoteId createNewNoteId( ContentProviderClient client )
   {
      final NewNoteId newId = new NewNoteId();
      newId.noteId = Queries.getNextId( client, Notes.CONTENT_URI );
      
      if ( newId.noteId != null )
         return newId;
      else
         return null;
   }
   
   
   
   public final static ContentProviderOperation insertLocalCreatedNote( RtmTaskNote note )
   {
      return ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                     .withValues( getContentValues( note, true ) )
                                     .build();
   }
   
   
   
   public RtmNotesProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Notes.PATH );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Notes._ID
         + " TEXT NOT NULL, " + Notes.TASKSERIES_ID + " TEXT NOT NULL, "
         + Notes.NOTE_CREATED_DATE + " INTEGER NOT NULL, "
         + Notes.NOTE_MODIFIED_DATE + " INTEGER, " + Notes.NOTE_DELETED
         + " INTEGER, " + Notes.NOTE_TITLE + " TEXT, " + Notes.NOTE_TEXT
         + " TEXT, " + "CONSTRAINT PK_NOTES PRIMARY KEY ( \"" + Notes._ID
         + "\" ), " + "CONSTRAINT notes_taskseries_ref FOREIGN KEY ( "
         + Notes.TASKSERIES_ID + " ) REFERENCES " + TaskSeries.PATH + " ( "
         + TaskSeries._ID + " ) );" );
      
      createModificationsTrigger( db );
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
   public Uri getContentUri()
   {
      return Notes.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return Notes.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
   
   
   
   private final static RtmTaskNote createNote( Cursor c )
   {
      return new RtmTaskNote( c.getString( COL_INDICES.get( Notes._ID ) ),
                              c.getString( COL_INDICES.get( Notes.TASKSERIES_ID ) ),
                              new Date( c.getLong( COL_INDICES.get( Notes.NOTE_CREATED_DATE ) ) ),
                              Queries.getOptDate( c,
                                                  COL_INDICES.get( Notes.NOTE_MODIFIED_DATE ) ),
                              Queries.getOptDate( c,
                                                  COL_INDICES.get( Notes.NOTE_DELETED ) ),
                              Queries.getOptString( c,
                                                    COL_INDICES.get( Notes.NOTE_TITLE ) ),
                              Queries.getOptString( c,
                                                    COL_INDICES.get( Notes.NOTE_TEXT ) ) );
   }
}
