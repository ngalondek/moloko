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

import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.ParticipantsColumns;


public class ParticipantsQuery
{
   private final RtmDatabase database;
   
   private final ParticipantsTable participantsTable;
   
   
   
   public ParticipantsQuery( RtmDatabase database,
      ParticipantsTable participantsTable )
   {
      this.database = database;
      this.participantsTable = participantsTable;
   }
   
   
   
   public Cursor getParticipants( String taskSeriesId )
   {
      final Cursor c = database.getReadable()
                               .query( participantsTable.getTableName(),
                                       participantsTable.getProjection(),
                                       ParticipantsColumns.TASKSERIES_ID + "="
                                          + taskSeriesId,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public int getNumTasksContactIsParticipating( String contactId )
   {
      int numTasksParticipating = -1;
      Cursor c = null;
      
      try
      {
         c = database.getReadable().query( participantsTable.getTableName(),
                                           new String[]
                                           { ParticipantsColumns._ID,
                                            ParticipantsColumns.CONTACT_ID },
                                           ParticipantsColumns.CONTACT_ID + "="
                                              + contactId,
                                           null,
                                           null,
                                           null,
                                           null );
         
         numTasksParticipating = c.getCount();
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return numTasksParticipating;
   }
}
