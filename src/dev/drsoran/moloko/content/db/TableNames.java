/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

public final class TableNames
{
   private TableNames()
   {
      throw new AssertionError();
   }
   
   public final static String RTM_TASKS_LIST_TABLE = RtmTasksListsTable.TABLE_NAME;
   
   public final static String RTM_RAW_TASKS_TABLE = RtmRawTasksTable.TABLE_NAME;
   
   public final static String RTM_TASK_SERIES_TABLE = RtmTaskSeriesTable.TABLE_NAME;
   
   public final static String RTM_NOTES_TABLE = RtmNotesTable.TABLE_NAME;
   
   public final static String RTM_LOCATIONS_TABLE = RtmLocationsTable.TABLE_NAME;
   
   public final static String RTM_CONTACTS_TABLE = RtmContactsTable.TABLE_NAME;
   
   public final static String RTM_SETTINGS_TABLE = RtmSettingsTable.TABLE_NAME;
   
   public final static String RTM_PARTICIPANTS_TABLE = RtmParticipantsTable.TABLE_NAME;
}
