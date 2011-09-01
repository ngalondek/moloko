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

import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;


public class TasksListActivity extends AbstractFullDetailedTasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TasksListActivity.class.getSimpleName();
   
   
   private static class OptionsMenu
   {
      public final static int DELETE_LIST = R.id.menu_delete_list;
   }
   
   

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.DELETE_LIST,
                                   getString( R.string.taskslist_menu_opt_delete_list ),
                                   Menu.CATEGORY_ALTERNATIVE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_trash,
                                   MenuItem.SHOW_AS_ACTION_IF_ROOM,
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
}
