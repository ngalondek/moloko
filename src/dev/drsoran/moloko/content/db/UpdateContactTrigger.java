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
 * @brief If a contact gets updated, we also update the user name and full name of all referenced participants.
 */
class UpdateContactTrigger extends AbstractTrigger
{
   public UpdateContactTrigger()
   {
      super( RtmContactsTable.TABLE_NAME + "_update_contact" );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER UPDATE OF " );
      builder.append( RtmContactColumns.FULLNAME );
      builder.append( ", " );
      builder.append( RtmContactColumns.USERNAME );
      builder.append( " ON " );
      builder.append( RtmContactsTable.TABLE_NAME );
      builder.append( " FOR EACH ROW BEGIN UPDATE " );
      builder.append( RtmParticipantsTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( RtmParticipantColumns.FULLNAME );
      builder.append( " = " );
      builder.append( " new. " );
      builder.append( RtmContactColumns.FULLNAME );
      builder.append( ", " );
      builder.append( RtmParticipantColumns.USERNAME );
      builder.append( " = " );
      builder.append( " new. " );
      builder.append( RtmContactColumns.USERNAME );
      builder.append( " WHERE " );
      builder.append( RtmParticipantColumns.RTM_CONTACT_ID );
      builder.append( " = new." );
      builder.append( RtmContactColumns.RTM_CONTACT_ID );
      builder.append( "; END;" );
      
      database.execSQL( builder.toString() );
   }
}
