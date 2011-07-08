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

import java.util.List;

import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;


public class TasksListActivity extends AbstractTasksListActivity implements
         IFullDetailedTasksListListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TasksListActivity.class.getSimpleName();
   
   
   protected static class OptionsMenu extends
            AbstractTasksListActivity.OptionsMenu
   {
      public final static int SHOW_LISTS = R.id.menu_show_lists;
      
      public final static int DELETE_LIST = R.id.menu_delete_list;
   }
   
   

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.SHOW_LISTS,
                                   getString( R.string.taskslist_menu_opt_lists ),
                                   Menu.CATEGORY_ALTERNATIVE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_list,
                                   MenuItem.SHOW_AS_ACTION_NEVER,
                                   Intents.createOpenListOverviewsIntent(),
                                   !isInDropDownNavigationMode() );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.DELETE_LIST,
                                   getString( R.string.taskslist_menu_opt_delete_list ),
                                   Menu.CATEGORY_ALTERNATIVE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_trash,
                                   MenuItem.SHOW_AS_ACTION_NEVER,
                                   isConfiguredWithListName()
                                      && !AccountUtils.isReadOnlyAccess( this ) );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.DELETE_LIST:
            final String listName = getIntent().getStringExtra( Lists.LIST_NAME );
            UIUtils.newDeleteElementDialog( this, listName, new Runnable()
            {
               @Override
               public void run()
               {
                  RtmListEditUtils.deleteListByName( TasksListActivity.this,
                                                     listName );
                  finish();
               }
            },
                                            null ).show();
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
                                                              getFilter(),
                                                              getTaskSort() ) );
   }
   


   @Override
   public void onEditTask( int pos )
   {
      startActivity( Intents.createEditTaskIntent( this, getTask( pos ).getId() ) );
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
   public void onOpenNotes( int pos, List< RtmTaskNote > notes )
   {
      if ( notes.size() > 0 )
      {
         if ( notes.size() == 1 )
            startActivity( Intents.createOpenNotesIntent( this,
                                                          null,
                                                          notes.get( 0 )
                                                               .getId() ) );
         else
            startActivity( Intents.createOpenNotesIntent( this,
                                                          notes.get( 0 )
                                                               .getTaskSeriesId(),
                                                          null ) );
      }
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
