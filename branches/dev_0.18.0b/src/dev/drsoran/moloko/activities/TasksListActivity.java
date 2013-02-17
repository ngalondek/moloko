/*
 * Copyright (c) 2012 Ronny Röhricht
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

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.fragments.FullDetailedTasksListFragment;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.rtm.RtmSmartFilter;


public class TasksListActivity extends AbstractFullDetailedTasksListActivity
{
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( isWritableAccess() )
      {
         getSupportMenuInflater().inflate( R.menu.taskslist_activity_rwd, menu );
      }
      else
      {
         getSupportMenuInflater().inflate( R.menu.taskslist_activity, menu );
      }
      
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      super.onPrepareOptionsMenu( menu );
      
      if ( isWritableAccess() )
      {
         final MenuItem addSmartListItem = menu.findItem( R.id.menu_add_list );
         prepareAddSmartListMenuVisibility( addSmartListItem );
      }
      
      final MenuItem toggleDefaultListItem = menu.findItem( R.id.menu_toggle_default_list );
      prepareToggleDefaultListMenu( toggleDefaultListItem );
      
      return true;
   }
   
   
   
   private void prepareAddSmartListMenuVisibility( MenuItem addSmartListItem )
   {
      final RtmSmartFilter filter = geActiveRtmSmartFilter();
      boolean show = filter != null;
      
      // the active, selected item is an already existing list, then we
      // don't need to add a new list.
      show = show && !isRealList( getActiveListId() );
      
      if ( show )
      {
         final List< RtmSmartFilterToken > unAmbigiousTokens = RtmSmartFilterParsing.removeAmbiguousTokens( filter.getTokens() );
         show = unAmbigiousTokens.size() > 0;
      }
      
      addSmartListItem.setVisible( show );
   }
   
   
   
   private void prepareToggleDefaultListMenu( MenuItem toggleDefaultListItem )
   {
      final String listIdOfTasksList = getActiveListId();
      
      toggleDefaultListItem.setVisible( listIdOfTasksList != null
         && isRealList( listIdOfTasksList ) );
      
      if ( toggleDefaultListItem.isVisible() )
      {
         if ( isDefaultList() )
         {
            toggleDefaultListItem.setTitle( R.string.tasklists_menu_ctx_remove_def_list );
            toggleDefaultListItem.setIcon( R.drawable.ic_menu_flag_unset );
         }
         else
         {
            toggleDefaultListItem.setTitle( R.string.tasklists_menu_ctx_make_def_list );
            toggleDefaultListItem.setIcon( R.drawable.ic_menu_flag );
         }
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_add_list:
            showAddListDialog();
            return true;
            
         case R.id.menu_toggle_default_list:
            if ( isDefaultList() )
            {
               resetDefaultList();
            }
            else
            {
               setAsDefaultList();
            }
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public boolean onNavigationItemSelected( int itemPosition, long itemId )
   {
      final boolean superNavigationItemSelected = super.onNavigationItemSelected( itemPosition,
                                                                                  itemId );
      
      if ( superNavigationItemSelected )
      {
         supportInvalidateOptionsMenu();
      }
      
      return superNavigationItemSelected;
   }
   
   
   
   @Override
   protected Fragment createTasksListFragment( Bundle config )
   {
      return FullDetailedTasksListFragment.newInstance( config );
   }
   
   
   
   private RtmSmartFilter geActiveRtmSmartFilter()
   {
      final IFilter activeFilter = getActiveFilter();
      if ( activeFilter instanceof RtmSmartFilter )
      {
         return (RtmSmartFilter) activeFilter;
      }
      else
      {
         return null;
      }
   }
   
   
   
   /**
    * Checks if the list ID belongs to a list from the database or is a custom navigation item ID. E.g. a search query.
    */
   private static boolean isRealList( String listId )
   {
      return !String.valueOf( CUSTOM_NAVIGATION_ITEM_ID ).equals( listId );
   }
   
   
   
   private boolean isDefaultList()
   {
      return getActiveListId().equals( MolokoApp.getSettings( this )
                                                .getDefaultListId() );
   }
   
   
   
   private void setAsDefaultList()
   {
      MolokoApp.getSettings( this ).setDefaultListId( getActiveListId() );
   }
   
   
   
   private void resetDefaultList()
   {
      MolokoApp.getSettings( this )
               .setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
   }
}
