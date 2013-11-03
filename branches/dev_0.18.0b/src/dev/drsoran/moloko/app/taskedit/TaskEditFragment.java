/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskedit;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.services.IDateFormatterService;


class TaskEditFragment extends AbstractTaskEditFragment implements
         IOnSettingsChangedListener
{
   @InstanceState( key = Intents.Extras.KEY_TASK )
   private Task task;
   
   
   
   public final static TaskEditFragment newInstance( Bundle config )
   {
      final TaskEditFragment fragment = new TaskEditFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public TaskEditFragment()
   {
      registerAnnotatedConfiguredInstance( this, TaskEditFragment.class );
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      getAppContext().getAppEvents()
                     .registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                         this );
   }
   
   
   
   @Override
   public void onStop()
   {
      getAppContext().getAppEvents().unregisterOnSettingsChangedListener( this );
      super.onStop();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( ( which & IOnSettingsChangedListener.DATE_TIME_RELATED ) != 0
         && isAdded() && !isRemoving() )
      {
         initializeHeadSection();
      }
   }
   
   
   
   @Override
   public Bundle determineInitialValues()
   {
      final Task task = getTaskAssertNotNull();
      
      final Bundle initialValues = new Bundle();
      
      initialValues.putString( TaskColumns.TASK_NAME, task.getName() );
      initialValues.putLong( TaskColumns.LIST_ID, task.getListId() );
      initialValues.putString( TaskColumns.PRIORITY, task.getPriority()
                                                         .toString() );
      initialValues.putString( TaskColumns.TAGS,
                               TextUtils.join( TaskColumns.TAGS_SEPARATOR,
                                               task.getTags() ) );
      initialValues.putSerializable( TaskColumns.DUE_DATE, task.getDue() );
      initialValues.putSerializable( TaskColumns.RECURRENCE,
                                     task.getRecurrence() );
      initialValues.putSerializable( TaskColumns.ESTIMATE, task.getEstimation() );
      initialValues.putLong( TaskColumns.LOCATION_ID, task.getLocationId() );
      initialValues.putString( TaskColumns.URL, task.getUrl() );
      
      return initialValues;
   }
   
   
   
   @Override
   protected void initializeHeadSection()
   {
      final Task task = getTaskAssertNotNull();
      initializeHeadSectionImpl( task );
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      if ( task == null )
         throw new AssertionError( "expected task to be not null" );
      
      return task;
   }
   
   
   
   @Override
   protected List< Task > getEditedTasks()
   {
      return Collections.singletonList( getTaskAssertNotNull() );
   }
   
   
   
   private void initializeHeadSectionImpl( Task task )
   {
      final UiContext context = getUiContext();
      final IDateFormatterService dateFormatter = context.getDateFormatter();
      
      addedDate.setText( dateFormatter.formatDateTime( task.getAddedMillisUtc(),
                                                       FULL_DATE_FLAGS ) );
      
      if ( task.isComplete() )
      {
         completedDate.setText( dateFormatter.formatDateTime( task.getCompletedMillisUtc(),
                                                              FULL_DATE_FLAGS ) );
         completedDate.setVisibility( View.VISIBLE );
      }
      else
      {
         completedDate.setVisibility( View.GONE );
      }
      
      if ( task.isPostponed() )
      {
         postponed.setText( getString( R.string.task_postponed,
                                       task.getPostponedCount() ) );
         postponed.setVisibility( View.VISIBLE );
      }
      else
      {
         postponed.setVisibility( View.GONE );
      }
      
      if ( !TextUtils.isEmpty( task.getSource() ) )
      {
         source.setText( getString( R.string.task_source, task.getSource() ) );
      }
      else
      {
         source.setText( "?" );
      }
   }
}
