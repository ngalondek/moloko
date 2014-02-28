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
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;


class TaskNotesContentUriHandler extends AbstractContentUriHandler
{
   private final ITable rtmNotesTable;
   
   private final ITaskSeriesIdProvider taskSeriesIdProvider;
   
   
   
   public TaskNotesContentUriHandler( ITable rtmNotesTable,
      ITaskSeriesIdProvider taskSeriesIdProvider )
   {
      this.rtmNotesTable = rtmNotesTable;
      this.taskSeriesIdProvider = taskSeriesIdProvider;
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs )
   {
      selection = getElementSelection( contentUri, id, selection );
      return rtmNotesTable.query( projection, selection, selectionArgs, null );
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      selection = getAllSelection( contentUri, selection );
      return rtmNotesTable.query( projection,
                                  selection,
                                  selectionArgs,
                                  sortOrder );
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      final long taskSeriesId = taskSeriesIdProvider.getTaskSeriesIdOfTask( ContentUris.getTaskIdFromUri( contentUri ) );
      initialValues.put( RtmNoteColumns.TASKSERIES_ID, taskSeriesId );
      
      final long rowId = rtmNotesTable.insert( initialValues );
      return rowId;
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      final int numUpdated = rtmNotesTable.update( id, values, null, null );
      return numUpdated;
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      return rtmNotesTable.delete( id, where, whereArgs );
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      where = getAllSelection( contentUri, where );
      return rtmNotesTable.delete( Constants.NO_ID, where, whereArgs );
   }
   
   
   
   private String getAllSelection( Uri contentUri, String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  RtmNotesTable.TABLE_NAME ).selectAllForTaskSeries( taskSeriesIdProvider,
                                                                                                     RtmNoteColumns.TASKSERIES_ID )
                                                                            .andSelect( appendedSelection )
                                                                            .build();
   }
   
   
   
   private String getElementSelection( Uri contentUri,
                                       long id,
                                       String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  RtmNotesTable.TABLE_NAME ).selectElement( id )
                                                                            .andSelect( appendedSelection )
                                                                            .build();
   }
}
