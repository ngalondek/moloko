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

package dev.drsoran.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public abstract class AbstractTrigger
{
   private final SQLiteDatabase database;
   
   private final String triggerName;
   
   
   
   protected AbstractTrigger( SQLiteDatabase database, String triggerName )
   {
      this.database = database;
      this.triggerName = triggerName;
   }
   
   
   
   public SQLiteDatabase getDatabase()
   {
      return database;
   }
   
   
   
   public String getTriggerName()
   {
      return triggerName;
   }
   
   
   
   public void upgrade( int oldVersion, int newVersion ) throws SQLException
   {
   }
   
   
   
   public void drop()
   {
      database.execSQL( "DROP TRIGGER " + triggerName );
   }
   
   
   
   public abstract void create() throws SQLException;
}
