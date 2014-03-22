/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.content.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;


/**
 * @brief If a RTM element gets deleted we also delete all possible open modifications for this element.
 */
class DeleteModificationsTrigger extends AbstractTrigger
{
   private final String tableName;
   
   private final String uriPath;
   
   
   
   public DeleteModificationsTrigger( String tableName, String uriPath )
   {
      super( tableName + "_delete_modifications" );
      this.tableName = tableName;
      this.uriPath = uriPath;
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER DELETE ON " );
      builder.append( tableName );
      builder.append( " FOR EACH ROW BEGIN DELETE FROM " );
      builder.append( ModificationsTable.TABLE_NAME );
      builder.append( " WHERE " );
      builder.append( ModificationColumns.ENTITY_URI );
      builder.append( " LIKE '" );
      builder.append( ContentUris.buildUri( uriPath ) );
      builder.append( "' || '/' || old." );
      builder.append( BaseColumns._ID );
      builder.append( ";" );
      builder.append( "END;" );
      
      database.execSQL( builder.toString() );
   }
}
