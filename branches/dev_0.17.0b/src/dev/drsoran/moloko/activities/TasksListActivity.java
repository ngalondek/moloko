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
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.FullDetailedTasksListFragment;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.rtm.RtmSmartFilter;


public class TasksListActivity extends AbstractFullDetailedTasksListActivity
{
   @InstanceState( key = "SHOW_ADD_SMART_LIST" )
   private boolean showAddSmartListMenu;
   
   
   
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
         menu.findItem( R.id.menu_add_list ).setVisible( showAddSmartListMenu );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_add_list:
            showAddListDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public boolean onNavigationItemSelected( int itemPosition, long itemId )
   {
      final IItem selectedItem = getNavigationItem( itemPosition );
      if ( selectedItem != null )
      {
         final boolean show = evaluateAddSmartListMenuVisibility( selectedItem );
         if ( show != showAddSmartListMenu )
         {
            showAddSmartListMenu = show;
            invalidateOptionsMenu();
         }
      }
      
      return super.onNavigationItemSelected( itemPosition, itemId );
   }
   
   
   
   private boolean evaluateAddSmartListMenuVisibility( IItem selectedItem )
   {
      final IFilter filter = selectedItem.getTasksListConfig()
                                         .getParcelable( Intents.Extras.KEY_FILTER );
      boolean show = filter instanceof RtmSmartFilter;
      
      // if we are configured with a list name then we already are in a list
      // and do not need to add a new one.
      show = show && selectedItem.getId() == CUSTOM_NAVIGATION_ITEM_ID;
      
      if ( show )
      {
         final RtmSmartFilter rtmSmartFilter = (RtmSmartFilter) filter;
         final List< RtmSmartFilterToken > unAmbigiousTokens = RtmSmartFilterParsing.removeAmbiguousTokens( rtmSmartFilter.getTokens() );
         show = unAmbigiousTokens.size() > 0;
      }
      
      return show;
   }
   
   
   
   @Override
   protected Fragment createTasksListFragment( Bundle config )
   {
      return FullDetailedTasksListFragment.newInstance( config );
   }
}
