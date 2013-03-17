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

import android.content.ContentProviderClient;
import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.RtmNotesColumns;


class RtmNotesQuery
{
   private final static String SEL_NON_DEL_NOTES_OF_TASKSERIES;
   
   private final static String SEL_NON_DEL_NOTE;
   
   private final RtmDatabase database;
   
   private final RtmNotesTable rtmNotesTable;
   
   static
   {
      SEL_NON_DEL_NOTES_OF_TASKSERIES = new StringBuilder( RtmNotesColumns.TASKSERIES_ID ).append( "=? AND " )
                                                                                          .append( RtmNotesColumns.NOTE_DELETED_DATE )
                                                                                          .append( " IS NULL" )
                                                                                          .toString();
      
      SEL_NON_DEL_NOTE = new StringBuilder( RtmNotesColumns._ID ).append( "=? AND " )
                                                                 .append( RtmNotesColumns.NOTE_DELETED_DATE )
                                                                 .append( " IS NULL" )
                                                                 .toString();
   }
   
   
   
   public RtmNotesQuery( RtmDatabase database, RtmNotesTable rtmNotesTable )
   {
      this.database = database;
      this.rtmNotesTable = rtmNotesTable;
   }
   
   
   
   public Cursor getNote( ContentProviderClient client, long noteId )
   {
      final Cursor c = database.getReadable()
                               .query( rtmNotesTable.getTableName(),
                                       rtmNotesTable.getProjection(),
                                       SEL_NON_DEL_NOTE,
                                       new String[]
                                       { String.valueOf( noteId ) },
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getAllNotes()
   {
      final Cursor c = database.getReadable()
                               .query( rtmNotesTable.getTableName(),
                                       rtmNotesTable.getProjection(),
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getNotesOfTaskSeries( long taskSeriesId )
   {
      final Cursor c = database.getReadable()
                               .query( rtmNotesTable.getTableName(),
                                       rtmNotesTable.getProjection(),
                                       SEL_NON_DEL_NOTES_OF_TASKSERIES,
                                       new String[]
                                       { String.valueOf( taskSeriesId ) },
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getLocalCreatedNotes()
   {
      final Cursor c = database.getQuery( CreationsQuery.class )
                               .getCreationsForTable( rtmNotesTable.getTableName() );
      return c;
   }
   
   
   
   public int getDeletedNotesCount()
   {
      int cnt = -1;
      
      Cursor c = null;
      
      try
      {
         c = getDeletedNotes();
         cnt = c.getCount();
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return cnt;
   }
   
   
   
   public Cursor getDeletedNotes()
   {
      final Cursor c = database.getReadable()
                               .query( rtmNotesTable.getTableName(),
                                       rtmNotesTable.getProjection(),
                                       RtmNotesColumns.NOTE_DELETED_DATE
                                          + " IS NOT NULL",
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
}
