/*
 * Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.db;

import android.database.sqlite.SQLiteDatabase;


public final class DbUtils
{
   private DbUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public static void doTransactional( SQLiteDatabase sqliteDatabase,
                                       Runnable action )
   {
      if ( sqliteDatabase == null )
      {
         throw new IllegalArgumentException( "sqliteDatabase" );
      }
      
      if ( action == null )
      {
         throw new IllegalArgumentException( "action" );
      }
      
      try
      {
         sqliteDatabase.beginTransaction();
         
         action.run();
         
         sqliteDatabase.setTransactionSuccessful();
      }
      finally
      {
         sqliteDatabase.endTransaction();
      }
   }
}
