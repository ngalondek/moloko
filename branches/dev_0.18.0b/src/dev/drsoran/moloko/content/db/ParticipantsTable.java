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

import java.util.HashMap;
import java.util.Map;

import android.database.SQLException;
import dev.drsoran.moloko.content.db.Columns.RtmContactsColumns;
import dev.drsoran.moloko.content.db.Columns.ParticipantsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


class ParticipantsTable extends Table
{
   public final static String TABLE_NAME = "participants";
   
   private final static Map< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   private final static String[] PROJECTION =
   { ParticipantsColumns._ID, ParticipantsColumns.CONTACT_ID,
    ParticipantsColumns.TASKSERIES_ID, ParticipantsColumns.FULLNAME,
    ParticipantsColumns.USERNAME };
   
   private final static Map< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      initProjectionDependent( PROJECTION, PROJECTION_MAP, COL_INDICES );
   }
   
   
   
   public ParticipantsTable( RtmDatabase database )
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
      builder.append( ParticipantsColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_PARTICIPANTS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( ParticipantsColumns.CONTACT_ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( ParticipantsColumns.TASKSERIES_ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( ParticipantsColumns.FULLNAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( ParticipantsColumns.USERNAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( "CONSTRAINT participant FOREIGN KEY ( " );
      builder.append( ParticipantsColumns.TASKSERIES_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " (\"" );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( "\"), " );
      builder.append( "CONSTRAINT participates FOREIGN KEY ( " );
      builder.append( ParticipantsColumns.CONTACT_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmContactsTable.TABLE_NAME );
      builder.append( " (\"" );
      builder.append( RtmContactsColumns._ID );
      builder.append( "\") " );
      builder.append( " );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return ParticipantsColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public Map< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public Map< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
