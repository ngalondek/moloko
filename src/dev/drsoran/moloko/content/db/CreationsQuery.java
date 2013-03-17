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
import android.provider.BaseColumns;
import dev.drsoran.moloko.content.db.Columns.CreationsColumns;


public class CreationsQuery
{
   private final RtmDatabase database;
   
   private final CreationsTable creationsTable;
   
   
   
   public CreationsQuery( RtmDatabase database, CreationsTable creationsTable )
   {
      this.database = database;
      this.creationsTable = creationsTable;
   }
   
   
   
   public Cursor getCreation( String creationId )
   {
      final Cursor c = database.getReadable()
                               .query( creationsTable.getTableName(),
                                       creationsTable.getProjection(),
                                       CreationsColumns._ID + "=" + creationId,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getAllCreations()
   {
      final Cursor c = database.getReadable()
                               .query( creationsTable.getTableName(),
                                       creationsTable.getProjection(),
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getCreationsForTable( String tableName )
   {
      final String query = buildQueryCreationsOfTable( tableName );
      
      final Cursor c = database.getReadable().rawQuery( query, null );
      return c;
   }
   
   
   
   private String buildQueryCreationsOfTable( String tableName )
   {
      final String creations = creationsTable.getTableName();
      final String query = new StringBuilder( "SELECT " ).append( "%s.* FROM %s," )
                                                         .append( creations )
                                                         .append( " WHERE instr(" )
                                                         .append( CreationsColumns.ENTITY_URI )
                                                         .append( ",('%s/' || %s." )
                                                         .append( BaseColumns._ID )
                                                         .append( "))" )
                                                         .toString();
      return query;
   }
}
