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

import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.model.Priority;


public class TaskEditFragment extends AbstractTaskEditFragment
{
   @InstanceState( key = Intents.Extras.KEY_TASK,
                   defaultValue = InstanceState.NULL )
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
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_edit_fragment,
                                                  container,
                                                  false );
      setFragmentView( fragmentView );
      
      return fragmentView;
   }
   
   
   
   @Override
   protected void putInitialValues( ValuesContainer valuesContainer,
                                    TaskEditData taskEditData )
   {
      valuesContainer.putValue( TASK_NAME, task.getName(), String.class );
      valuesContainer.putValue( LIST_ID, task.getListId(), Long.class );
      valuesContainer.putValue( LIST_NAME, task.getListName(), String.class );
      valuesContainer.putValue( PRIORITY, task.getPriority(), Priority.class );
      valuesContainer.putValue( TAGS,
                                new ArrayList< String >( task.getTags() ),
                                ArrayList.class );
      
      valuesContainer.putValue( DUE_DATE, task.getDue(), Due.class );
      valuesContainer.putValue( RECURRENCE,
                                task.getRecurrence(),
                                Recurrence.class );
      valuesContainer.putValue( ESTIMATE,
                                task.getEstimation(),
                                Estimation.class );
      
      valuesContainer.putValue( LOCATION_ID, task.getLocationId(), Long.class );
      valuesContainer.putValue( LOCATION_NAME,
                                task.getLocationName(),
                                String.class );
      valuesContainer.putValue( URL, task.getUrl(), String.class );
   }
   
   
   
   @Override
   public void onFinishEditing()
   {
      applyChanges();
      
      final ITaskEditFragmentListener editFragmentListener = getListener();
      if ( editFragmentListener != null )
      {
         editFragmentListener.onUpdateTasks( Collections.singletonList( task ) );
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   private void applyChanges()
   {
      task.setName( getChange( TASK_NAME, String.class ) );
      task.setList( getChange( LIST_ID, Long.class ),
                    getChange( LIST_NAME, String.class ) );
      task.setPriority( getChange( PRIORITY, Priority.class ) );
      task.setTags( getChange( TAGS, ArrayList.class ) );
      task.setDue( getChange( DUE_DATE, Due.class ) );
      task.setRecurrence( getChange( RECURRENCE, Recurrence.class ) );
      task.setEstimation( getChange( ESTIMATE, Estimation.class ) );
      task.setLocationStub( getChange( LOCATION_ID, Long.class ),
                            getChange( LOCATION_NAME, String.class ) );
      task.setUrl( getChange( URL, String.class ) );
   }
}
