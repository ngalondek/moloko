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
import dev.drsoran.moloko.content.AbstractContentUriHandler;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;


class SyncTimesContentUriHandler extends AbstractContentUriHandler
{
   private final ITable syncTimesTable;
   
   
   
   public SyncTimesContentUriHandler( ITable syncTimesTable )
   {
      this.syncTimesTable = syncTimesTable;
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs )
   {
      selection = getElementSelection( contentUri, id, selection );
      return syncTimesTable.query( projection, selection, selectionArgs, null );
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      return queryElement( contentUri,
                           SyncTimesColumns.SINGLETON_ID,
                           projection,
                           selection,
                           selectionArgs );
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      throw new UnsupportedOperationException( "It is not supported to have more than one SyncTimes row." );
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      return syncTimesTable.update( id, values, null, null );
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   private String getElementSelection( Uri contentUri,
                                       long id,
                                       String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  SyncTimesTable.TABLE_NAME ).selectElement( id )
                                                                             .andSelect( appendedSelection )
                                                                             .build();
   }
}
