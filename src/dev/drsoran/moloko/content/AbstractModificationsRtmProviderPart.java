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

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.provider.Rtm.Modifications;


public abstract class AbstractModificationsRtmProviderPart extends
         AbstractRtmProviderPart
{
   public AbstractModificationsRtmProviderPart( SystemContext context,
      SQLiteOpenHelper dbAccess, String tableName )
   {
      super( context, dbAccess, tableName );
   }
   
   
   
   protected void createModificationsTrigger( SQLiteDatabase db ) throws SQLException
   {
      // TRIGGER: If this RTM element gets deleted we also delete all possible
      // open modifications for this element.
      db.execSQL( "CREATE TRIGGER " + path
         + "_delete_modifications AFTER DELETE ON " + path
         + " FOR EACH ROW BEGIN DELETE FROM " + Modifications.PATH + " WHERE "
         + Modifications.ENTITY_URI + " = '" + getContentUri()
         + "' || '/' || old." + BaseColumns._ID + ";" + "END;" );
   }
}
