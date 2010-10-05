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

package dev.drsoran.moloko.content;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public interface IRtmProviderPart extends IProviderPart
{
   
   public void create( SQLiteDatabase db ) throws SQLException;
   


   public void upgrade( SQLiteDatabase db, int oldVersion, int newVersion ) throws SQLException;
   


   public void drop( SQLiteDatabase db );
   


   public Uri insert( ContentValues values );
   


   public int update( String id,
                      ContentValues initialValues,
                      String where,
                      String[] whereArgs );
   


   public int delete( String id, String where, String[] whereArgs );
   


   public String getTableName();
   
}
