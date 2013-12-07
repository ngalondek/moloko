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

package dev.drsoran.moloko.sync.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.db.AbstractTrigger;


public class SyncDatabase extends AbstractDatabase
{
   public final static String DATABASE_NAME = "sync.db";
   
   private final static int DATABASE_VERSION = 1;
   
   
   
   public SyncDatabase( Context context )
   {
      super( context, DATABASE_NAME, DATABASE_VERSION );
   }
   
   
   
   @Override
   protected AbstractTable[] createTables( SQLiteDatabase database )
   {
      return new AbstractTable[]
      { new CreationsTable( database ), new ModificationsTable( database ),
       new DeletionsTable( database ), new TimesTable( database ) };
   }
   
   
   
   @Override
   protected AbstractTrigger[] createTriggers( SQLiteDatabase database )
   {
      return new AbstractTrigger[] {};
   }
   
}
