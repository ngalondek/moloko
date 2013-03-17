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

import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;


public class RtmListsQuery
{
   private final RtmDatabase database;
   
   private final RtmListsTable rtmListsTable;
   
   
   
   public RtmListsQuery( RtmDatabase database, RtmListsTable rtmListsTable )
   {
      this.database = database;
      this.rtmListsTable = rtmListsTable;
   }
   
   
   
   public Cursor getList( long listId )
   {
      final Cursor c = database.getReadable()
                               .query( rtmListsTable.getTableName(),
                                       rtmListsTable.getProjection(),
                                       RtmListsColumns._ID + "=" + listId,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getListByName( String listName )
   {
      final Cursor c = database.getReadable()
                               .query( rtmListsTable.getTableName(),
                                       rtmListsTable.getProjection(),
                                       RtmListsColumns.LIST_NAME + " like '"
                                          + listName + "'",
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor resolveListIdsToListNames( Iterable< String > listIds )
   {
      final String selectionString = DbUtils.toColumnList( listIds,
                                                            RtmListsColumns._ID,
                                                            " OR " );
      
      final Cursor c = database.getReadable()
                               .query( rtmListsTable.getTableName(),
                                       new String[]
                                       { RtmListsColumns._ID,
                                        RtmListsColumns.LIST_NAME },
                                       selectionString,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getAllLists( String selection )
   {
      final Cursor c = database.getReadable()
                               .query( rtmListsTable.getTableName(),
                                       rtmListsTable.getProjection(),
                                       selection,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getLocalCreatedLists()
   {
      final Cursor c = database.getQuery( CreationsQuery.class )
                               .getCreationsForTable( rtmListsTable.getTableName() );
      return c;
   }
   
   
   
   public Cursor getDeletedLists()
   {
      final Cursor c = database.getReadable()
                               .query( rtmListsTable.getTableName(),
                                       new String[]
                                       { RtmListsColumns._ID },
                                       RtmListsColumns.LIST_DELETED_DATE
                                          + " IS NOT NULL",
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
}
