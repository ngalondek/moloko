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

package dev.drsoran.moloko.app.taskslist;

import java.util.Collection;

import android.view.Menu;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.app.taskslist.common.AbstractFullDetailedTasksListActivity;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterToken;


public class TasksListActivity extends AbstractFullDetailedTasksListActivity
{
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( isWritableAccess() )
      {
         getMenuInflater().inflate( R.menu.taskslist_activity_rwd, menu );
      }
      else
      {
         getMenuInflater().inflate( R.menu.taskslist_activity, menu );
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
      final RtmSmartFilter filter = getActiveFilter();
      boolean show = filter != null;
      
      // the active, selected item is an already existing list, then we
      // don't need to add a new list.
      show = show && !isRealList( getActiveListId() );
      
      if ( show )
      {
         try
         {
            final Collection< RtmSmartFilterToken > unAmbigiousTokens = getAppContext().getParsingService()
                                                                                       .getSmartFilterParsing()
                                                                                       .getSmartFilterTokens( filter.getFilterString() )
                                                                                       .getUniqueTokens();
            show = unAmbigiousTokens.size() > 0;
         }
         catch ( GrammarException e )
         {
            show = false;
         }
      }
      
      addSmartListItem.setVisible( show );
   }
   
   
   
   private void prepareToggleDefaultListMenu( MenuItem toggleDefaultListItem )
   {
      final long listIdOfTasksList = getActiveListId();
      
      toggleDefaultListItem.setVisible( listIdOfTasksList != Constants.NO_ID
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
         invalidateOptionsMenu();
      }
      
      return superNavigationItemSelected;
   }
   
   
   
   /**
    * Checks if the list ID belongs to a list from the database or is a custom navigation item ID. E.g. a search query.
    */
   private static boolean isRealList( long listId )
   {
      return CUSTOM_NAVIGATION_ITEM_ID != listId;
   }
   
   
   
   private boolean isDefaultList()
   {
      return getActiveListId() == getAppContext().getSettings()
                                                 .getDefaultListId();
   }
   
   
   
   private void setAsDefaultList()
   {
      getAppContext().getSettings().setDefaultListId( getActiveListId() );
   }
   
   
   
   private void resetDefaultList()
   {
      getAppContext().getSettings()
                     .setDefaultListId( SettingConstants.NO_DEFAULT_LIST_ID );
   }
}
