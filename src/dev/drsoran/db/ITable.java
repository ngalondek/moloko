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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public interface ITable
{
   String getTableName();
   
   
   
   String getDefaultSortOrder();
   
   
   
   String[] getProjection();
   
   
   
   Cursor query( String[] projection,
                 String selection,
                 String[] selectionArgs,
                 String sortOrder );
   
   
   
   long insert( ContentValues initialValues ) throws SQLException;
   
   
   
   int update( long id, ContentValues values, String where, String[] whereArgs );
   
   
   
   int delete( long id, String where, String[] whereArgs );
   
   
   
   void clear(SQLiteDatabase database);
}
