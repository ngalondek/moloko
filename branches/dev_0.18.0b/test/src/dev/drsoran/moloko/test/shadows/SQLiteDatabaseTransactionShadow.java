/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowSQLiteDatabase;

import android.database.sqlite.SQLiteDatabase;


@Implements( value = SQLiteDatabase.class,
             inheritImplementationMethods = true,
             callThroughByDefault = true )
public class SQLiteDatabaseTransactionShadow extends ShadowSQLiteDatabase
{
   public int beginCalledCount;
   
   public int endCalledCount;
   
   public int successfullCalledCount;
   
   
   
   @Override
   @Implementation
   public void beginTransaction()
   {
      ++beginCalledCount;
      super.beginTransaction();
   }
   
   
   
   @Override
   @Implementation
   public void setTransactionSuccessful()
   {
      ++successfullCalledCount;
      super.setTransactionSuccessful();
   }
   
   
   
   @Override
   @Implementation
   public void endTransaction()
   {
      ++endCalledCount;
      super.endTransaction();
   }
}
