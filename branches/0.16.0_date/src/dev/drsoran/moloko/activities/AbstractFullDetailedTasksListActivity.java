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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.UIUtils;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements
         IFullDetailedTasksListFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractFullDetailedTasksListActivity.class.getSimpleName();
   
   
   private static class OptionsMenu
   {
      public final static int QUICK_ADD_TASK = R.id.menu_quick_add_task;
      
      public final static int SHOW_LISTS = R.id.menu_show_lists;
   }
   
   

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.QUICK_ADD_TASK,
                                   getString( R.string.app_task_add ),
                                   MenuCategory.CONTAINER,
                                   Menu.NONE,
                                   R.drawable.ic_menu_add_task,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   !AccountUtils.isReadOnlyAccess( this ) );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.SHOW_LISTS,
                                   getString( R.string.taskslist_menu_opt_lists ),
                                   MenuCategory.ALTERNATIVE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_list,
                                   MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                   Intents.createOpenListOverviewsIntent(),
                                   !isInDropDownNavigationMode() );
      
      UIUtils.addSearchMenuItem( this,
                                 menu,
                                 MenuCategory.ALTERNATIVE,
                                 MenuItem.SHOW_AS_ACTION_IF_ROOM );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.QUICK_ADD_TASK:
            showQuickAddTaskFragment( !isQuickAddTaskFragmentOpen() );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   @Override
   public void onOpenTask( int pos )
   {
      startActivity( Intents.createOpenTaskIntent( this, getTask( pos ).getId() ) );
   }
   


   @Override
   public void onSelectTasks()
   {
      startActivity( Intents.createSelectMultipleTasksIntent( this,
                                                              getConfiguredFilter(),
                                                              getTaskSort() ) );
   }
   


   @Override
   public void onEditTask( int pos )
   {
      startActivity( Intents.createEditTaskIntent( this, getTask( pos ) ) );
   }
   


   @Override
   public void onOpenList( int pos, String listId )
   {
      reloadTasksListWithConfiguration( Intents.Extras.createOpenListExtrasById( this,
                                                                                 listId,
                                                                                 null ) );
   }
   


   @Override
   public void onOpenLocation( int pos, String locationId )
   {
      reloadTasksListWithConfiguration( Intents.Extras.createOpenLocationExtras( this,
                                                                                 getTask( pos ).getLocationName() ) );
   }
   


   @Override
   public void onShowTasksWithTag( String tag )
   {
      reloadTasksListWithConfiguration( Intents.Extras.createOpenTagExtras( this,
                                                                            tag ) );
   }
   


   @Override
   public void onShowTasksWithTags( List< String > tags, String logicalOperator )
   {
      reloadTasksListWithConfiguration( Intents.Extras.createOpenTagsExtras( this,
                                                                             tags,
                                                                             logicalOperator ) );
   }
}
