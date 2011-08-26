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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.AbstractTasksListActivity.Config;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.moloko.fragments.TaskListsFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskListsFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskListsActivity extends MolokoFragmentActivity implements
         ITaskListsFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskListsActivity.class.getSimpleName();
   
   
   protected static class OptionsMenu
   {
      public final static int SETTINGS = R.id.menu_settings;
      
      public final static int SYNC = R.id.menu_sync;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tasklists_activity );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                Menu.NONE,
                R.string.phr_settings )
          .setIcon( R.drawable.ic_menu_settings )
          .setIntent( new Intent( this, MolokoPreferencesActivity.class ) )
          .setShowAsAction( MenuItem.SHOW_AS_ACTION_NEVER );
      
      UIUtils.addSyncMenuItem( this,
                               menu,
                               OptionsMenu.SYNC,
                               Menu.NONE,
                               MenuItem.SHOW_AS_ACTION_IF_ROOM );
      return true;
   }
   


   @Override
   public void openList( int pos )
   {
      final RtmListWithTaskCount rtmList = getRtmList( pos );
      
      // Check if the smart filter could be parsed. Otherwise
      // we do not fire the intent.
      if ( rtmList.isSmartFilterValid() )
      {
         final String listName = rtmList.getName();
         
         final Intent intent = new Intent( Intent.ACTION_VIEW,
                                           Tasks.CONTENT_URI );
         
         intent.putExtra( Config.TITLE, getString( R.string.taskslist_titlebar,
                                                   listName ) );
         intent.putExtra( Config.TITLE_ICON, R.drawable.ic_title_list );
         
         RtmSmartFilter filter = rtmList.getSmartFilter();
         
         // If we have no smart filter we use the list name as "list:" filter
         if ( filter == null )
         {
            filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_LIST_LIT
               + RtmSmartFilterLexer.quotify( listName ) );
         }
         
         intent.putExtra( Lists.LIST_NAME, rtmList.getName() );
         intent.putExtra( AbstractTasksListFragment.Config.FILTER, filter );
         
         startActivity( intent );
      }
   }
   


   @Override
   public void openChild( Intent intent )
   {
      // TODO Auto-generated method stub
      
   }
   


   private RtmListWithTaskCount getRtmList( int pos )
   {
      final TaskListsFragment taskListsFragment = (TaskListsFragment) getSupportFragmentManager().findFragmentById( R.id.frag_tasklists );
      return taskListsFragment.getRtmList( pos );
   }
   


   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_tasklists };
   }
}
