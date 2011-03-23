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

import java.util.Collections;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class TasksListActivity extends AbstractTasksListActivity implements
         DialogInterface.OnClickListener
{
   private final static String TAG = "Moloko."
      + TasksListActivity.class.getSimpleName();
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = AbstractTasksListActivity.OptionsMenu.START_IDX + 1000;
      
      public final static int MENU_ORDER = AbstractTasksListActivity.OptionsMenu.MENU_ORDER - 1000;
      
      public final static int SHOW_LISTS = START_IDX + 0;
   }
   
   

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      boolean ok = super.onCreateOptionsMenu( menu );
      
      if ( ok )
         menu.add( Menu.NONE,
                   OptionsMenu.SHOW_LISTS,
                   OptionsMenu.MENU_ORDER,
                   R.string.taskslist_menu_opt_lists )
             .setIcon( R.drawable.ic_menu_list );
      
      return ok && addOptionsMenuIntents( menu );
   }
   


   @Override
   protected AsyncFillListResult queryTasksAsync( ContentResolver contentResolver,
                                                  Bundle configuration )
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmSmartFilter smartFilter = configuration.getParcelable( FILTER );
         String evaluatedFilter = null;
         
         if ( smartFilter != null )
         {
            // try to evaluate the filter
            evaluatedFilter = smartFilter.getEvaluatedFilterString( true );
            
            if ( evaluatedFilter == null )
            {
               // TODO: Show error if eval of filter failed
               // RETURN: evaluation failed
               return null;
            }
         }
         else
         {
            // TODO: Show error if no filter
            // RETURN: no filter
            return null;
         }
         
         // Don't call getTaskSort() cause this runs not in the UI thread.
         final int taskSort = configuration.getInt( TASK_SORT_ORDER,
                                                    Settings.TASK_SORT_DEFAULT );
         
         final List< Task > tasks = TasksProviderPart.getTasks( client,
                                                                evaluatedFilter,
                                                                Settings.resolveTaskSortToSqlite( taskSort ) );
         client.release();
         
         // TODO: Handle null. Show error?
         return new AsyncFillListResult( ListTask.fromTaskList( tasks ),
                                         smartFilter,
                                         configuration );
      }
      else
      {
         LogUtils.logDBError( this, TAG, "Tasks" );
         return null;
      }
   }
   


   @Override
   protected void setTasksResult( AsyncFillListResult result )
   {
      switchEmptyView( emptyListView );
      
      final String title = result.configuration.getString( TITLE );
      
      final int titleIconId = result.configuration.getInt( TITLE_ICON, -1 );
      
      if ( title != null )
      {
         UIUtils.setTitle( this, title, titleIconId );
      }
      else
      {
         UIUtils.setTitle( this,
                           getString( R.string.taskslist_titlebar,
                                      getString( R.string.app_name ) ) );
      }
      
      setListAdapter( createListAdapter( result ) );
   }
   


   protected ListAdapter createListAdapter( AsyncFillListResult result )
   {
      return new TasksListAdapter( this,
                                   R.layout.taskslist_activity_listitem,
                                   result != null
                                                 ? result.tasks
                                                 : Collections.< ListTask > emptyList(),
                                   result != null
                                                 ? result.filter
                                                 : new RtmSmartFilter( Strings.EMPTY_STRING ) );
   }
   


   @Override
   protected void handleIntent( Intent intent )
   {
      fillListAsync();
   }
   


   private boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      final MenuItem item = menu.findItem( OptionsMenu.SHOW_LISTS );
      
      ok = item != null;
      
      if ( ok )
      {
         item.setIntent( new Intent( Intent.ACTION_VIEW,
                                     ListOverviews.CONTENT_URI ) );
      }
      
      return ok;
   }
   
}
