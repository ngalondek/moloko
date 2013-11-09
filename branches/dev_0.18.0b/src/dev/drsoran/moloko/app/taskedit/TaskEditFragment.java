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

import android.os.Bundle;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.domain.model.Task;


class TaskEditFragment extends AbstractTaskEditFragment implements
         IOnSettingsChangedListener
{
   
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
   public Task getInitialTaskAssertNotNull()
   {
      if ( initialTask == null )
         throw new AssertionError( "expected task to be not null" );
      
      return initialTask;
   }
   
   
   
   @Override
   public Task getEditedTaskAssertNotNull()
   {
      if ( editedTask == null )
         throw new AssertionError( "expected task to be not null" );
      
      return editedTask;
   }
}
