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

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TaskCountColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.ListCursor;
import dev.drsoran.moloko.util.MolokoDateUtils;


class TaskCountContentUriHandler extends AbstractContentUriHandler
{
   private final IContentUriHandler tasksContentUriHandler;
   
   
   
   public TaskCountContentUriHandler( ILog log,
      IContentUriHandler tasksContentUriHandler )
   {
      super( log );
      this.tasksContentUriHandler = tasksContentUriHandler;
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs,
                                  String sortOrder )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      Cursor tasksCursor = null;
      try
      {
         tasksCursor = tasksContentUriHandler.query( ContentUris.TASKS_CONTENT_URI,
                                                     TaskColumns.PROJECTION,
                                                     selection,
                                                     selectionArgs,
                                                     null );
         
         final Object[] taskCountColumns = createTaskCountColumns( tasksCursor );
         final List< Object[] > taskCountCursorList = new ArrayList< Object[] >( 1 );
         
         taskCountCursorList.add( taskCountColumns );
         
         return new ListCursor( taskCountCursorList,
                                TaskCountColumns.PROJECTION );
      }
      finally
      {
         if ( tasksCursor != null )
         {
            tasksCursor.close();
         }
      }
   }
   
   
   
   private Object[] createTaskCountColumns( Cursor tasksCursor )
   {
      int incompleteTaskCount = 0;
      int completedTaskCount = 0;
      int dueTodayTaskCount = 0;
      int dueTomorrowTaskCount = 0;
      int overDueTaskCount = 0;
      long sumEstimated = 0;
      
      while ( tasksCursor.moveToNext() )
      {
         final boolean isCompleted = !tasksCursor.isNull( TaskColumns.COMPLETED_DATE_IDX );
         
         if ( !isCompleted )
         {
            final long dueMillisUtc = CursorUtils.getOptLong( tasksCursor,
                                                              TaskColumns.DUE_IDX,
                                                              Constants.NO_TIME );
            
            if ( dueMillisUtc != Constants.NO_TIME )
            {
               final int diffToNow = MolokoDateUtils.getTimespanInDays( System.currentTimeMillis(),
                                                                        dueMillisUtc );
               
               // Today?
               if ( diffToNow == 0 )
               {
                  ++dueTodayTaskCount;
               }
               
               // Tomorrow?
               else if ( diffToNow == 1 )
               {
                  ++dueTomorrowTaskCount;
               }
               
               // Overdue?
               else if ( diffToNow < 0 )
               {
                  ++overDueTaskCount;
               }
            }
            
            // Sum up estimated times
            final long estimateMillis = CursorUtils.getOptLong( tasksCursor,
                                                                TaskColumns.ESTIMATE_MILLIS_IDX,
                                                                Constants.NO_TIME );
            if ( estimateMillis != Constants.NO_TIME )
            {
               sumEstimated += estimateMillis;
            }
         }
         
         // Completed?
         if ( isCompleted )
         {
            ++completedTaskCount;
         }
      }
      
      final Object[] columnValues = new Object[ TaskCountColumns.PROJECTION.length ];
      
      columnValues[ TaskCountColumns.INCOMPLETE_IDX ] = incompleteTaskCount;
      columnValues[ TaskCountColumns.COMPLETED_IDX ] = completedTaskCount;
      columnValues[ TaskCountColumns.DUE_TODAY_IDX ] = dueTodayTaskCount;
      columnValues[ TaskCountColumns.DUE_TOMORROW_IDX ] = dueTomorrowTaskCount;
      columnValues[ TaskCountColumns.OVERDUE_IDX ] = overDueTaskCount;
      columnValues[ TaskCountColumns.SUM_ESTIMATED_IDX ] = sumEstimated;
      
      return columnValues;
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      throw new UnsupportedOperationException();
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
}
