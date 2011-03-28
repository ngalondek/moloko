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

package dev.drsoran.moloko.activities;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskEditActivity extends AbstractTaskEditActivity
{
   
   private final static String TAG = "Moloko."
      + TaskEditActivity.class.getSimpleName();
   
   private Task task;
   
   

   @Override
   public InitialValues onCreateImpl( Intent intent )
   {
      final Uri taskUri = intent.getData();
      
      if ( taskUri != null )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
         
         if ( client != null )
         {
            final String taskId = taskUri.getLastPathSegment();
            task = TasksProviderPart.getTask( client, taskId );
            
            client.release();
         }
         else
         {
            LogUtils.logDBError( this, TAG, "Task" );
            task = null;
         }
      }
      
      if ( task != null )
      {
         return new InitialValues( task.getName(),
                                   task.getListId(),
                                   RtmTask.convertPriority( task.getPriority() ),
                                   TextUtils.join( Tasks.TAGS_SEPARATOR,
                                                   task.getTags() ),
                                   task.getDue() != null ? task.getDue()
                                                               .getTime() : -1,
                                   task.hasDueTime(),
                                   task.getRecurrence(),
                                   task.isEveryRecurrence(),
                                   task.getEstimate(),
                                   task.getEstimateMillis(),
                                   task.getLocationId(),
                                   task.getUrl() );
      }
      else
         return null;
   }
   


   @Override
   protected ModificationSet getModifications()
   {
      final ModificationSet modifications = new ModificationSet();
      
      // Task name
      {
         final String taskName = getCurrentValue( Tasks.TASKSERIES_NAME,
                                                  String.class );
         
         if ( SyncUtils.hasChanged( task.getName(), taskName ) )
            modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                       task.getTaskSeriesId() ),
                                                             TaskSeries.TASKSERIES_NAME,
                                                             taskName ) );
      }
      
      // List
      {
         final String selectedListId = getCurrentValue( Tasks.LIST_ID,
                                                        String.class );
         
         if ( SyncUtils.hasChanged( task.getListId(), selectedListId ) )
            modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                       task.getTaskSeriesId() ),
                                                             TaskSeries.LIST_ID,
                                                             selectedListId ) );
      }
      
      // Priority
      {
         final String selectedPriority = getCurrentValue( Tasks.PRIORITY,
                                                          String.class );
         
         if ( SyncUtils.hasChanged( RtmTask.convertPriority( task.getPriority() ),
                                    selectedPriority ) )
            modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                       task.getId() ),
                                                             RawTasks.PRIORITY,
                                                             selectedPriority ) );
      }
      
      // Tags
      {
         final String tags = getCurrentValue( Tasks.TAGS, String.class );
         
         if ( SyncUtils.hasChanged( tags, TextUtils.join( Tags.TAGS_SEPARATOR,
                                                          task.getTags() ) ) )
            modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                       task.getTaskSeriesId() ),
                                                             TaskSeries.TAGS,
                                                             tags ) );
      }
      
      // Due
      {
         Long newDue = getCurrentValue( Tasks.DUE_DATE, Long.class );
         
         if ( newDue == -1 )
            newDue = null;
         
         if ( SyncUtils.hasChanged( MolokoDateUtils.getTime( task.getDue() ),
                                    newDue ) )
         {
            modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                       task.getId() ),
                                                             RawTasks.DUE_DATE,
                                                             newDue ) );
            
         }
         
         final boolean newHasDueTime = getCurrentValue( Tasks.HAS_DUE_TIME,
                                                        Boolean.class );
         
         if ( SyncUtils.hasChanged( task.hasDueTime(), newHasDueTime ) )
         {
            modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                       task.getId() ),
                                                             RawTasks.HAS_DUE_TIME,
                                                             newHasDueTime ? 1
                                                                          : 0 ) );
         }
      }
      
      // Recurrence
      {
         final String recurrence = getCurrentValue( Tasks.RECURRENCE,
                                                    String.class );
         
         if ( SyncUtils.hasChanged( task.getRecurrence(), recurrence ) )
         {
            modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                       task.getTaskSeriesId() ),
                                                             TaskSeries.RECURRENCE,
                                                             recurrence ) );
            
            final boolean isEveryRecurrence = getCurrentValue( Tasks.RECURRENCE_EVERY,
                                                               Boolean.class );
            
            if ( SyncUtils.hasChanged( task.isEveryRecurrence(),
                                       isEveryRecurrence ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.RECURRENCE_EVERY,
                                                                isEveryRecurrence ) );
            }
         }
      }
      
      // Estimate
      {
         final long estimateMillis = getCurrentValue( Tasks.ESTIMATE_MILLIS,
                                                      Long.class );
         
         if ( SyncUtils.hasChanged( task.getEstimateMillis(), estimateMillis ) )
         {
            modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                       task.getId() ),
                                                             RawTasks.ESTIMATE,
                                                             getCurrentValue( RawTasks.ESTIMATE,
                                                                              String.class ) ) );
            
            modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                       task.getId() ),
                                                             RawTasks.ESTIMATE_MILLIS,
                                                             estimateMillis ) );
         }
      }
      
      // Location
      {
         final String selectedLocation = getCurrentValue( Tasks.LOCATION_ID,
                                                          String.class );
         
         if ( SyncUtils.hasChanged( task.getLocationId(), selectedLocation ) )
            modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                       task.getTaskSeriesId() ),
                                                             TaskSeries.LOCATION_ID,
                                                             selectedLocation ) );
      }
      
      // URL
      {
         final String newUrl = getCurrentValue( Tasks.URL, String.class );
         
         if ( SyncUtils.hasChanged( task.getUrl(), newUrl ) )
            modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                       task.getTaskSeriesId() ),
                                                             TaskSeries.URL,
                                                             newUrl ) );
      }
      
      // set the taskseries modification time to now
      modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
      
      return modifications;
   }
   


   @Override
   protected void refreshHeadSection( TextView addedDate,
                                      TextView completedDate,
                                      TextView postponed,
                                      TextView source )
   {
      addedDate.setText( MolokoDateUtils.formatDateTime( task.getAdded()
                                                             .getTime(),
                                                         FULL_DATE_FLAGS ) );
      
      if ( task.getCompleted() != null )
      {
         completedDate.setText( MolokoDateUtils.formatDateTime( task.getCompleted()
                                                                    .getTime(),
                                                                FULL_DATE_FLAGS ) );
         completedDate.setVisibility( View.VISIBLE );
      }
      else
         completedDate.setVisibility( View.GONE );
      
      if ( task.getPosponed() > 0 )
      {
         postponed.setText( getString( R.string.task_postponed,
                                       task.getPosponed() ) );
         postponed.setVisibility( View.VISIBLE );
      }
      else
         postponed.setVisibility( View.GONE );
      
      if ( !TextUtils.isEmpty( task.getSource() ) )
      {
         String sourceStr = task.getSource();
         if ( sourceStr.equalsIgnoreCase( "js" ) )
            sourceStr = "web";
         
         source.setText( getString( R.string.task_source, sourceStr ) );
      }
      else
         source.setText( "?" );
   }
   
}
