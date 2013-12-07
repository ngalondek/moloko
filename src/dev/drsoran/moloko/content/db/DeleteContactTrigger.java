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

package dev.drsoran.moloko.content.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractTrigger;
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;


/**
 * If a contact gets deleted, we also delete all referenced participants
 */
class DeleteContactTrigger extends AbstractTrigger
{
   public DeleteContactTrigger( SQLiteDatabase database )
   {
      super( database, RtmContactsTable.TABLE_NAME + "_delete_contact" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final SQLiteDatabase db = getDatabase();
      
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER DELETE ON " );
      builder.append( RtmContactsTable.TABLE_NAME );
      builder.append( " FOR EACH ROW BEGIN DELETE FROM " );
      builder.append( RtmParticipantsTable.TABLE_NAME );
      builder.append( " WHERE " );
      builder.append( RtmParticipantColumns.CONTACT_ID );
      builder.append( " = old." );
      builder.append( RtmContactColumns._ID );
      builder.append( "; END;" );
      
      db.execSQL( builder.toString() );
   }
}
