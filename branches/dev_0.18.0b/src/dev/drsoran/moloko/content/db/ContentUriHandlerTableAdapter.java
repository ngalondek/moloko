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

package dev.drsoran.moloko.content.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.AbstractContentUriHandler;
import dev.drsoran.moloko.content.Constants;


class ContentUriHandlerTableAdapter extends AbstractContentUriHandler
{
   private final ITable table;
   
   
   
   public ContentUriHandlerTableAdapter( ITable tableToAdapt )
   {
      this.table = tableToAdapt;
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs,
                                  String sortOrder )
   {
      selection = getElementSelection( contentUri, id, selection );
      return table.query( projection, selection, selectionArgs, sortOrder );
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      return table.query( projection, selection, selectionArgs, sortOrder );
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      final long rowId = table.insert( initialValues );
      return rowId;
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      final int numUpdated = table.update( id, values, null, null );
      return numUpdated;
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      return table.delete( id, where, whereArgs );
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      return table.delete( Constants.NO_ID, where, whereArgs );
   }
   
   
   
   private String getElementSelection( Uri contentUri,
                                       long id,
                                       String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  table.getTableName() ).selectElement( id )
                                                                        .andSelect( appendedSelection )
                                                                        .build();
   }
}
