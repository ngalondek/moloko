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

package dev.drsoran.rtm.sync.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.rtm.sync.db.TableColumns.CreationsColumns;


class CreationsTable extends AbstractTable
{
   public final static String TABLE_NAME = "creations";
   
   
   
   public CreationsTable( SQLiteDatabase database )
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
      builder.append( CreationsColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_CREATIONS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( CreationsColumns.SRC_ENTITY_URI );
      builder.append( " TEXT NOT NULL, " );
      builder.append( CreationsColumns.DST_ENTITY_URI );
      builder.append( " TEXT NOT NULL, " );
      builder.append( CreationsColumns.TIMESTAMP );
      builder.append( " INTEGER NOT NULL" );
      builder.append( ");" );
      
      getDatabase().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return CreationsColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return CreationsColumns.PROJECTION;
   }
}
