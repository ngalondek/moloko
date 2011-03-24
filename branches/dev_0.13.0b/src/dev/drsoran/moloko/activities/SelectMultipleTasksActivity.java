/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.activities;

import java.util.Collections;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


public class SelectMultipleTasksActivity extends TasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + SelectMultipleTasksActivity.class.getSimpleName();
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      public final static int MENU_ORDER = 0;
      
      public final static int SELECT_ALL = START_IDX + 0;
      
      public final static int DESELECT_ALL = START_IDX + 1;
      
      public final static int INVERT_SELECTION = START_IDX + 2;
      
      public final static int SORT = START_IDX + 3;
      
      public final static int DO_EDIT = START_IDX + 4;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      getListView().setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SELECT_ALL,
                OptionsMenu.MENU_ORDER,
                R.string.select_multiple_tasks_menu_opt_select_all );
      
      menu.add( Menu.NONE,
                OptionsMenu.SELECT_ALL,
                OptionsMenu.MENU_ORDER,
                R.string.select_multiple_tasks_menu_opt_unselect_all );
      
      menu.add( Menu.NONE,
                OptionsMenu.INVERT_SELECTION,
                OptionsMenu.MENU_ORDER,
                R.string.select_multiple_tasks_menu_opt_inv_selection );
      
      menu.add( Menu.NONE,
                OptionsMenu.DO_EDIT,
                OptionsMenu.MENU_ORDER + 2,
                R.string.select_multiple_tasks_menu_opt_do_edit );
      
      return true;
   }
   


   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      if ( getListAdapter() != null )
         addOptionalMenuItem( menu,
                              OptionsMenu.SORT,
                              getString( R.string.abstaskslist_menu_opt_sort ),
                              OptionsMenu.MENU_ORDER + 1,
                              R.drawable.ic_menu_sort,
                              getListAdapter().getCount() > 1 );
      
      // TODO: Disable DO_EDIT if nothing selected
      final MenuItem doEditItem = menu.findItem( OptionsMenu.DO_EDIT );
      
      if ( doEditItem != null )
      {
         
      }
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      // Handle item selection
      switch ( item.getItemId() )
      {
         case OptionsMenu.SELECT_ALL:
            return true;
         case OptionsMenu.DESELECT_ALL:
            return true;
         case OptionsMenu.INVERT_SELECTION:
            return true;
         case OptionsMenu.SORT:
            return true;
         case OptionsMenu.DO_EDIT:
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   @Override
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
   }
   


   @Override
   protected ListAdapter createListAdapter( AsyncFillListResult result )
   {
      return new SelectMultipleTasksListAdapter( this,
                                                 R.layout.selectmultipletasks_activity_listitem,
                                                 result != null
                                                               ? result.tasks
                                                               : Collections.< ListTask > emptyList(),
                                                 result != null
                                                               ? result.filter
                                                               : new RtmSmartFilter( Strings.EMPTY_STRING ) );
   }
   


   private void toggleSelection( int pos )
   {
      final SelectMultipleTasksListAdapter adapter = (SelectMultipleTasksListAdapter) getListAdapter();
      
      adapter.toggleSelection( pos );
   }
}
