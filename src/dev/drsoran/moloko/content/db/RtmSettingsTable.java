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
import dev.drsoran.moloko.content.Columns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


class RtmSettingsTable extends AbstractTable
{
   public final static String TABLE_NAME = "settings";
   
   
   
   public RtmSettingsTable( SQLiteDatabase database )
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
      builder.append( RtmSettingsColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_SETTINGS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmSettingsColumns.SYNC_TIMESTAMP );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmSettingsColumns.TIMEZONE );
      builder.append( " TEXT, " );
      builder.append( RtmSettingsColumns.DATEFORMAT );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmSettingsColumns.TIMEFORMAT );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      builder.append( " INTEGER, " );
      builder.append( RtmSettingsColumns.LANGUAGE );
      builder.append( " TEXT, " );
      builder.append( "CONSTRAINT defaultlist FOREIGN KEY (" );
      builder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      builder.append( ") REFERENCES " );
      builder.append( RtmTasksListsTable.TABLE_NAME );
      builder.append( "( " );
      builder.append( RtmTasksListColumns._ID );
      builder.append( " ) );" );
      
      getDatabase().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return null;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmSettingsColumns.PROJECTION;
   }
}
