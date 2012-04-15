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

package dev.drsoran.moloko.actionmodes;

import android.os.Bundle;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ActionBarTabsAdapter;
import dev.drsoran.moloko.fragments.TaskEditFragment;
import dev.drsoran.moloko.fragments.TaskFragment;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.Task;


public class TaskEditActionMode implements ActionMode.Callback
{
   private final Task task;
   
   private final ActionBarTabsAdapter tabsAdapter;
   
   
   
   public TaskEditActionMode( Task task, ActionBarTabsAdapter tabsAdapter )
   {
      this.task = task;
      this.tabsAdapter = tabsAdapter;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      mode.getMenuInflater().inflate( R.menu.edit_mode, menu );
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      mode.setTitle( R.string.app_task_edit );
      tabsAdapter.setTabFragment( 0,
                                  TaskEditFragment.class,
                                  getTaskEditFragmentConfig() );
      return true;
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      return false;
   }
   
   
   
   @Override
   public void onDestroyActionMode( ActionMode mode )
   {
      tabsAdapter.setTabFragment( 0,
                                  TaskFragment.class,
                                  getTaskFragmentConfiguration() );
   }
   
   
   
   private Bundle getTaskFragmentConfiguration()
   {
      final Bundle config = new Bundle();
      
      config.putString( TaskFragment.Config.TASK_ID, task.getId() );
      
      return config;
   }
   
   
   
   private Bundle getTaskEditFragmentConfig()
   {
      final Bundle config = new Bundle();
      
      config.putParcelable( Intents.Extras.KEY_TASK, task );
      
      return config;
   }
}
