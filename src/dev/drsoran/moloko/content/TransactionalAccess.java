/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.content;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public final class TransactionalAccess
{
   private final SQLiteDatabase database;
   
   

   private TransactionalAccess( SQLiteDatabase database )
   {
      if ( database == null )
         throw new NullPointerException( "Database is null" );
      if ( !database.isOpen() || database.isReadOnly() )
         throw new IllegalStateException( "Database is closed or read only" );
      
      this.database = database;
      this.database.acquireReference();
   }
   


   public void beginTransaction()
   {
      database.beginTransaction();
   }
   


   public boolean inTransaction()
   {
      return database.inTransaction();
   }
   


   public void setTransactionSuccessful() throws IllegalStateException
   {
      database.setTransactionSuccessful();
   }
   


   public void endTransaction()
   {
      database.endTransaction();
      database.releaseReference();
   }
   


   public final static TransactionalAccess newTransactionalAccess( SQLiteOpenHelper openHelper ) throws SQLException
   {
      return new TransactionalAccess( openHelper.getWritableDatabase() );
   }
}
