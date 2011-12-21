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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.dialogs.AddRenameListDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChooseTagsDialogFragment;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IShowTasksWithTagsListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.UIUtils;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements
         IFullDetailedTasksListFragmentListener, IShowTasksWithTagsListener
{
   private static class OptionsMenu
   {
      public final static int QUICK_ADD_TASK = R.id.menu_quick_add_task;
      
      public final static int SHOW_LISTS = R.id.menu_show_lists;
   }
   
   /**
    * This flag indicates that a new Intent has been received. Activities which are singleTop are paused before the new
    * Intent is delivered. So replacing the fragment directly in onNewIntent can cause a state loss exception due to
    * pause. So we store the state and reload in onResume().
    */
   private boolean newTasksListFragmentbyIntent = false;
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      newTasksListFragmentbyIntent = true;
   }
   
   
   
   @Override
   protected void onResume()
   {
      super.onResume();
      
      if ( newTasksListFragmentbyIntent )
      {
         newTasksListFragmentbyIntent( getIntent() );
         newTasksListFragmentbyIntent = false;
      }
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
            showQuickAddTaskInput();
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
   public final void onShowTasksWithTags( List< String > tags )
   {
      if ( tags.size() == 1 )
      {
         onOpenChoosenTags( tags, null );
      }
      else if ( tags.size() > 1 )
      {
         showChooseTagsDialog( tags );
      }
   }
   
   
   
   /*
    * Callback from ChooseTagsDialogFragment after choosing tags and a logical operation on them.
    */
   @Override
   public final void onShowTasksWithTags( List< String > tags,
                                          LogicalOperation operation )
   {
      final String logOpString = determineLogicalOperantionString( operation );
      onOpenChoosenTags( tags, logOpString );
   }
   
   
   
   protected void onOpenChoosenTags( List< String > tags,
                                     String logicalOperation )
   {
      if ( tags.size() == 1 )
         reloadTasksListWithConfiguration( Intents.Extras.createOpenTagExtras( this,
                                                                               tags.get( 0 ) ) );
      else
         reloadTasksListWithConfiguration( Intents.Extras.createOpenTagsExtras( this,
                                                                                tags,
                                                                                logicalOperation ) );
   }
   
   
   
   private void showQuickAddTaskInput()
   {
      showQuickAddTaskActionBarFragment( !isQuickAddTaskFragmentOpen() );
   }
   
   
   
   private static String determineLogicalOperantionString( LogicalOperation operation )
   {
      final String logOpString;
      
      switch ( operation )
      {
         case AND:
            logOpString = RtmSmartFilterLexer.AND_LIT;
            break;
         
         case OR:
            logOpString = RtmSmartFilterLexer.OR_LIT;
            break;
         
         default :
            logOpString = null;
            break;
      }
      return logOpString;
   }
   
   
   
   protected void showAddListDialog()
   {
      final Bundle config = new Bundle();
      config.putParcelable( AddRenameListDialogFragment.Config.FILTER,
                            getConfiguredFilter() );
      
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      UIUtils.showDialogFragment( this,
                                  dialogFragment,
                                  String.valueOf( R.id.frag_add_rename_list ) );
   }
   
   
   
   private void showChooseTagsDialog( List< String > tags )
   {
      ChooseTagsDialogFragment.show( this, tags );
   }
}
