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
import dev.drsoran.moloko.content.db.Columns.RtmSettingsColumns;


public class RtmSettingsQuery
{
   private final RtmDatabase database;
   
   private final RtmSettingsTable rtmSettingsTable;
   
   
   
   public RtmSettingsQuery( RtmDatabase database,
      RtmSettingsTable rtmSettingsTable )
   {
      this.database = database;
      this.rtmSettingsTable = rtmSettingsTable;
   }
   
   
   
   public long getSettingsId()
   {
      long settingsId = -1;
      Cursor c = null;
      
      try
      {
         c = database.getReadable().query( rtmSettingsTable.getTableName(),
                                           new String[]
                                           { RtmSettingsColumns._ID },
                                           null,
                                           null,
                                           null,
                                           null,
                                           null );
         
         // We only consider the first entry cause we do not expect
         // more than 1 entry in this table
         if ( c.moveToFirst() )
         {
            settingsId = c.getLong( Columns.ID_IDX );
         }
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return settingsId;
   }
   
   
   
   public Cursor getSettings()
   {
      final Cursor c = database.getReadable()
                               .query( rtmSettingsTable.getTableName(),
                                       rtmSettingsTable.getProjection(),
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
}
