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
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;


class TaskParticipantsContentUriHandler extends AbstractContentUriHandler
{
   private final ITable rtmParticipantsTable;
   
   private final ITaskSeriesIdProvider taskSeriesIdProvider;
   
   
   
   public TaskParticipantsContentUriHandler( ITable rtmParticipantsTable,
      ITaskSeriesIdProvider taskSeriesIdProvider )
   {
      this.rtmParticipantsTable = rtmParticipantsTable;
      this.taskSeriesIdProvider = taskSeriesIdProvider;
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
      return rtmParticipantsTable.query( projection,
                                         selection,
                                         selectionArgs,
                                         sortOrder );
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      selection = getAllSelection( contentUri, selection );
      return rtmParticipantsTable.query( projection,
                                         selection,
                                         selectionArgs,
                                         sortOrder );
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      final long taskSeriesId = taskSeriesIdProvider.getTaskSeriesIdOfTask( ContentUris.getTaskIdFromUri( contentUri ) );
      initialValues.put( RtmParticipantColumns.TASKSERIES_ID, taskSeriesId );
      
      final long rowId = rtmParticipantsTable.insert( initialValues );
      return rowId;
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      final int numUpdated = rtmParticipantsTable.update( id,
                                                          values,
                                                          null,
                                                          null );
      return numUpdated;
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      return rtmParticipantsTable.delete( id, where, whereArgs );
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      where = getAllSelection( contentUri, where );
      return rtmParticipantsTable.delete( Constants.NO_ID, where, whereArgs );
   }
   
   
   
   private String getAllSelection( Uri contentUri, String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  RtmParticipantsTable.TABLE_NAME ).selectAllForTaskSeries( taskSeriesIdProvider,
                                                                                                            RtmParticipantColumns.TASKSERIES_ID )
                                                                                   .andSelect( appendedSelection )
                                                                                   .build();
   }
   
   
   
   private String getElementSelection( Uri contentUri,
                                       long id,
                                       String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  RtmParticipantsTable.TABLE_NAME ).selectElement( id )
                                                                                   .andSelect( appendedSelection )
                                                                                   .build();
   }
}
