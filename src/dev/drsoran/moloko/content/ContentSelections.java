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

package dev.drsoran.moloko.content;

import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;


public class ContentSelections
{
   public final static String SEL_NO_DELETED_TASKS;
   
   public final static String SEL_NO_COMPLETED_AND_DELETED_TASKS;
   
   public final static String SEL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS;
   
   public final static String SEL_PHYSICAL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS;
   
   public final static String SEL_NO_DELETED_NOTES;
   
   static
   {
      SEL_NO_DELETED_TASKS = TaskColumns.DELETED_DATE + " IS NULL";
      
      SEL_NO_COMPLETED_AND_DELETED_TASKS = new StringBuilder( TaskColumns.COMPLETED_DATE ).append( " IS NULL AND " )
                                                                                          .append( SEL_NO_DELETED_TASKS )
                                                                                          .toString();
      SEL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS = TasksListColumns.LIST_DELETED_DATE
         + " IS NULL AND " + TasksListColumns.ARCHIVED + "=0";
      
      SEL_PHYSICAL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS = new StringBuilder( SEL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS ).append( " AND " )
                                                                                                                   .append( TasksListColumns.IS_SMART_LIST )
                                                                                                                   .append( "=0" )
                                                                                                                   .toString();
      SEL_NO_DELETED_NOTES = NoteColumns.NOTE_DELETED_DATE + " IS NULL";
   }
}
