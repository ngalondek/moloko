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

package dev.drsoran.moloko.content.db;

import java.util.HashMap;
import java.util.Map;

import android.database.SQLException;
import dev.drsoran.moloko.content.db.Columns.NotesColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


class RtmNotesTable extends Table
{
   public final static String TABLE_NAME = "notes";
   
   private final static Map< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   private final static String[] PROJECTION =
   { NotesColumns._ID, NotesColumns.TASKSERIES_ID,
    NotesColumns.NOTE_CREATED_DATE, NotesColumns.NOTE_MODIFIED_DATE,
    NotesColumns.NOTE_DELETED, NotesColumns.NOTE_TITLE, NotesColumns.NOTE_TEXT };
   
   private final static Map< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   
   @Deprecated
   public final static class NewNoteId
   {
      public String noteId;
   }
   
   static
   {
      initProjectionDependent( PROJECTION, PROJECTION_MAP, COL_INDICES );
   }
   
   
   
   public RtmNotesTable( RtmDatabase database )
   {
      super( database, TABLE_NAME );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( " ( " );
      builder.append( NotesColumns._ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( NotesColumns.TASKSERIES_ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( NotesColumns.NOTE_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( NotesColumns.NOTE_MODIFIED_DATE );
      builder.append( " INTEGER, " );
      builder.append( NotesColumns.NOTE_DELETED );
      builder.append( " INTEGER, " );
      builder.append( NotesColumns.NOTE_TITLE );
      builder.append( " TEXT, " );
      builder.append( NotesColumns.NOTE_TEXT );
      builder.append( " TEXT, " );
      builder.append( "CONSTRAINT PK_NOTES PRIMARY KEY ( \"" );
      builder.append( NotesColumns._ID );
      builder.append( "\" ), " );
      builder.append( "CONSTRAINT notes_taskseries_ref FOREIGN KEY ( " );
      builder.append( NotesColumns.TASKSERIES_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " ( " );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " ) );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return NotesColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public Map< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public Map< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
