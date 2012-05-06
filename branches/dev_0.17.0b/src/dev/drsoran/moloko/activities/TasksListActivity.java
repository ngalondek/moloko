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

import android.app.Dialog;
import android.content.Intent;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.MolokoMenuItemBuilder;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class TasksListActivity extends AbstractFullDetailedTasksListActivity
{
   private static class OptionsMenu
   {
      public final static int ADD_LIST = R.id.menu_add_list;
      
      public final static int DELETE_LIST = R.id.menu_delete_list;
   }
   
   private Boolean showAddSmartListMenu;
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      // if we receive a new intent then we have to re-evaluate the
      // condition.
      showAddSmartListMenu = null;
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      if ( showAddSmartListMenu == null )
         evaluateAddSmartListMenuVisibility();
      
      final boolean isWritableAccess = AccountUtils.isWriteableAccess( this );
      final MolokoMenuItemBuilder builder = new MolokoMenuItemBuilder();
      
      builder.setItemId( OptionsMenu.ADD_LIST )
             .setTitle( getString( R.string.tasksearchresult_menu_add_smart_list ) )
             .setIconId( R.drawable.ic_menu_add_list )
             .setOrder( MenuCategory.CONTAINER )
             .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_IF_ROOM )
             .setShow( showAddSmartListMenu.booleanValue() && isWritableAccess )
             .build( menu );
      
      // TODO: list deletion no longer supported
      // builder.setItemId( OptionsMenu.DELETE_LIST )
      // .setTitle( getString( R.string.taskslist_menu_opt_delete_list ) )
      // .setIconId( R.drawable.ic_menu_trash )
      // .setOrder( MenuCategory.ALTERNATIVE )
      // .setShow( !isListLocked() && hasListName() && isWritableAccess )
      // .build( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.DELETE_LIST:
            final String listName = getIntent().getStringExtra( Lists.LIST_NAME );
            UIUtils.showDeleteElementDialog( this, listName );
            return true;
            
         case OptionsMenu.ADD_LIST:
            showAddListDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   protected void handleDeleteElementDialogClick( String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         final String listName = getIntent().getStringExtra( Lists.LIST_NAME );
         final ApplyChangesInfo deleteListActions = RtmListEditUtils.deleteListByName( TasksListActivity.this,
                                                                                       listName );
         applyModifications( deleteListActions );
         
         finish();
      }
   }
   
   
   
   private void evaluateAddSmartListMenuVisibility()
   {
      final IFilter filter = getConfiguredFilter();
      boolean show = filter instanceof RtmSmartFilter;
      
      // if we are configured with a list name then we already are in a list
      // and do not need to add a new one.
      show = show && !hasListNameInIntent();
      
      if ( show )
      {
         final RtmSmartFilter rtmSmartFilter = (RtmSmartFilter) filter;
         final List< RtmSmartFilterToken > unAmbigiousTokens = RtmSmartFilterParsing.removeAmbiguousTokens( rtmSmartFilter.getTokens() );
         show = unAmbigiousTokens.size() > 0;
      }
      
      showAddSmartListMenu = Boolean.valueOf( show );
   }
}
