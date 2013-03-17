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

import android.database.SQLException;
import dev.drsoran.moloko.content.db.Columns.RtmNotesColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


class RtmNotesTable extends AbstractTable
{
   public final static String TABLE_NAME = "notes";
   
   
   @Deprecated
   public final static class NewNoteId
   {
      public String noteId;
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
      builder.append( RtmNotesColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_NOTES PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmNotesColumns.RTM_NOTE_ID );
      builder.append( " TEXT, " );
      builder.append( RtmNotesColumns.TASKSERIES_ID );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmNotesColumns.NOTE_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmNotesColumns.NOTE_MODIFIED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmNotesColumns.NOTE_DELETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmNotesColumns.NOTE_TITLE );
      builder.append( " TEXT, " );
      builder.append( RtmNotesColumns.NOTE_TEXT );
      builder.append( " TEXT NOT NULL, " );
      builder.append( "CONSTRAINT notes_taskseries_ref FOREIGN KEY ( " );
      builder.append( RtmNotesColumns.TASKSERIES_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " ( " );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " ) );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public void createIndices()
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE INDEX " );
      builder.append( TABLE_NAME );
      builder.append( "_" );
      builder.append( RtmNotesColumns.TASKSERIES_ID );
      builder.append( " ON " );
      builder.append( TABLE_NAME );
      builder.append( "(" );
      builder.append( RtmNotesColumns.TASKSERIES_ID );
      builder.append( ");" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public void dropIndices()
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "DROP INDEX " );
      builder.append( TABLE_NAME );
      builder.append( "_" );
      builder.append( RtmNotesColumns.TASKSERIES_ID );
      builder.append( ";" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmNotesColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmNotesColumns.PROJECTION;
   }
}
