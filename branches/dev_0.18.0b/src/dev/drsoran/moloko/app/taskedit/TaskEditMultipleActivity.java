/*
 * Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskedit;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.Task;


public class TaskEditMultipleActivity extends AbstractTaskEditActivity
{
   @InstanceState( key = Intents.Extras.KEY_TASKS,
                   defaultValue = InstanceState.NULL )
   private ArrayList< Task > tasks;
   
   
   
   public TaskEditMultipleActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskEditMultipleActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_edit_multiple_activity );
      addFragment();
   }
   
   
   
   private void addFragment()
   {
      if ( findAddedFragmentById( R.id.frag_task_edit ) == null )
      {
         final Fragment fragment = createTaskEditMultipleFragment();
         
         getSupportFragmentManager().beginTransaction()
                                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                                    .add( R.id.frag_task_edit, fragment )
                                    .commit();
      }
   }
   
   
   
   private Fragment createTaskEditMultipleFragment()
   {
      final Fragment fragment = TaskEditMultipleFragment.newInstance( createTaskEditMultipleFragmentConfig() );
      return fragment;
   }
   
   
   
   private Bundle createTaskEditMultipleFragmentConfig()
   {
      final Bundle config = new Bundle();
      
      config.putParcelableArrayList( Intents.Extras.KEY_TASKS, tasks );
      
      return config;
   }
}
