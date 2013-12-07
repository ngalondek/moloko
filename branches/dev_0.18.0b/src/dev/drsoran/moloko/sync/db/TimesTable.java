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

package dev.drsoran.moloko.sync.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.moloko.sync.db.TableColumns.TimesColumns;


class TimesTable extends AbstractTable
{
   public final static String TABLE_NAME = "times";
   
   
   
   public TimesTable( SQLiteDatabase database )
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
      builder.append( TimesColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_SYNC PRIMARY KEY AUTOINCREMENT, " );
      builder.append( TimesColumns.LAST_IN );
      builder.append( " INTEGER, " );
      builder.append( TimesColumns.LAST_OUT );
      builder.append( " INTEGER );" );
      
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
      return TimesColumns.PROJECTION;
   }
}
