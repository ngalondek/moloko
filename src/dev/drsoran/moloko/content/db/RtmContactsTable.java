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
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;


class RtmContactsTable extends AbstractTable
{
   public final static String TABLE_NAME = "contacts";
   
   
   
   public RtmContactsTable()
   {
      super( TABLE_NAME );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( "( " );
      builder.append( RtmContactColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_CONTACTS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmContactColumns.FULLNAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmContactColumns.USERNAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmContactColumns.RTM_CONTACT_ID );
      builder.append( " TEXT NOT NULL " );
      builder.append( " );" );
      
      database.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmContactColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmContactColumns.TABLE_PROJECTION;
   }
}
