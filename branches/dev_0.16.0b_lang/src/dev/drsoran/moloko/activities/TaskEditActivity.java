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

import java.util.Collections;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
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
      if ( task != null )
         return createModificationSet( Collections.singletonList( task ) );
      else
         return new ModificationSet();
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
         source.setText( getString( R.string.task_source,
                                    UIUtils.convertSource( this,
                                                           task.getSource() ) ) );
      }
      else
         source.setText( "?" );
   }
   
}
