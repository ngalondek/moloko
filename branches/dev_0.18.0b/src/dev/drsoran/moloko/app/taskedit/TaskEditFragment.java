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

import android.os.Bundle;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;


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
   public Task getTask()
   {
      return task;
   }
   
   
   
   @Override
   public void onFinishEditing()
   {
      final ITaskEditFragmentListener editFragmentListener = getListener();
      if ( editFragmentListener != null )
      {
         editFragmentListener.onUpdateTasks( Collections.singletonList( task ) );
      }
   }
}
