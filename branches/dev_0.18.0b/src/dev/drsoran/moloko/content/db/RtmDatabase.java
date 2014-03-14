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

import android.content.Context;
import dev.drsoran.db.AbstractDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.db.AbstractTrigger;
import dev.drsoran.moloko.content.ContentUris;


public class RtmDatabase extends AbstractDatabase
{
   public final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   
   
   public RtmDatabase( Context context )
   {
      super( context, DATABASE_NAME, DATABASE_VERSION );
   }
   
   
   
   @Override
   protected AbstractTable[] createTables()
   {
      return new AbstractTable[]
      { new RtmTasksListsTable(), new RtmRawTasksTable(),
       new RtmTaskSeriesTable(), new RtmNotesTable(), new RtmContactsTable(),
       new RtmParticipantsTable(), new RtmLocationsTable(),
       new RtmSettingsTable(), new ModificationsTable(), new SyncTimesTable() };
   }
   
   
   
   @Override
   protected AbstractTrigger[] createTriggers()
   {
      return new AbstractTrigger[]
      {
       new DeleteDefaultListTrigger(),
       new DeleteRawTaskTrigger(),
       new DeleteTaskSeriesTrigger(),
       new DeleteContactTrigger(),
       new UpdateDefaultListTrigger(),
       new UpdateContactTrigger(),
       new UpdateTaskSeriesListIdTrigger(),
       new UpdateTaskSeriesLocationIdTrigger(),
       new PreventDeleteLockedListTrigger(),
       new PreventSetDeletedDateOfLockedListTrigger(),
       new UpdateTasksFromDeletedListTrigger(),
       new UpdateTasksFromSetDeletedDateListTrigger(),
       
       new DeleteModificationsTrigger( RtmRawTasksTable.TABLE_NAME,
                                       ContentUris.TASKS_URI_PATH ),
       new DeleteModificationsTrigger( RtmTasksListsTable.TABLE_NAME,
                                       ContentUris.TASKS_LISTS_URI_PATH ) };
   }
}
