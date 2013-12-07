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
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;


class RtmNotesTable extends AbstractTable
{
   public final static String TABLE_NAME = "notes";
   
   
   
   public RtmNotesTable( SQLiteDatabase database )
   {
      super( database, TABLE_NAME );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( "( " );
      builder.append( RtmNoteColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_NOTES PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmNoteColumns.TASKSERIES_ID );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmNoteColumns.NOTE_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmNoteColumns.NOTE_MODIFIED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmNoteColumns.NOTE_DELETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmNoteColumns.NOTE_TITLE );
      builder.append( " TEXT, " );
      builder.append( RtmNoteColumns.NOTE_TEXT );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmNoteColumns.RTM_NOTE_ID );
      builder.append( " TEXT, " );
      builder.append( "CONSTRAINT notes_taskseries_ref FOREIGN KEY ( " );
      builder.append( RtmNoteColumns.TASKSERIES_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " ( " );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " ) );" );
      
      getDatabase().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public void createIndices()
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE INDEX " );
      builder.append( TABLE_NAME );
      builder.append( "_" );
      builder.append( RtmNoteColumns.TASKSERIES_ID );
      builder.append( " ON " );
      builder.append( TABLE_NAME );
      builder.append( "(" );
      builder.append( RtmNoteColumns.TASKSERIES_ID );
      builder.append( ");" );
      
      getDatabase().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public void dropIndices()
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "DROP INDEX " );
      builder.append( TABLE_NAME );
      builder.append( "_" );
      builder.append( RtmNoteColumns.TASKSERIES_ID );
      builder.append( ";" );
      
      getDatabase().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmNoteColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmNoteColumns.TABLE_PROJECTION;
   }
}
