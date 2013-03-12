/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.moloko.content.db.Columns.NotesColumns;
import dev.drsoran.moloko.content.db.Columns.ParticipantsColumns;
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


/**
 * If a taskseries ID gets updated (e.g. after inserting on RTM side), we also update: all raw tasks, all referenced
 * notes, all referenced participants
 */
class UpdateTaskSeriesTrigger extends Trigger
{
   public UpdateTaskSeriesTrigger( RtmDatabase database )
   {
      super( database, RtmTaskSeriesTable.TABLE_NAME + "_update_taskseries" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final SQLiteDatabase db = getDatabase().getWritable();
      
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER UPDATE OF " );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " ON " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " FOR EACH ROW BEGIN UPDATE " );
      builder.append( RawTasksTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " = new." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " WHERE " );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " = old." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( "; UPDATE " );
      builder.append( RtmNotesTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( NotesColumns.TASKSERIES_ID );
      builder.append( " = new." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " WHERE " );
      builder.append( NotesColumns.TASKSERIES_ID );
      builder.append( " = old." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( "; UPDATE " );
      builder.append( ParticipantsTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( ParticipantsColumns.TASKSERIES_ID );
      builder.append( " = new." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " WHERE " );
      builder.append( ParticipantsColumns.TASKSERIES_ID );
      builder.append( " = old." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( "; END;" );
      
      db.execSQL( builder.toString() );
   }
}
