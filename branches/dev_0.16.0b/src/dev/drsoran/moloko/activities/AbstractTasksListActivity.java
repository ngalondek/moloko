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
import android.support.v4.app.FragmentActivity;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.fragments.AbstractTaskListFragment;
import dev.drsoran.moloko.fragments.listeners.ITasksListListener;
import dev.drsoran.moloko.layouts.TitleBarLayout;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


abstract class AbstractTasksListActivity< T extends AbstractTaskListFragment >
         extends FragmentActivity implements ITasksListListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTasksListActivity.class.getSimpleName();
   
   public static final String TITLE = "title";
   
   public static final String TITLE_ICON = "title_icon";
   
   protected T tasksListFragment;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
      
      {
         @SuppressWarnings( "unchecked" )
         final T findFragmentById = (T) getSupportFragmentManager().findFragmentById( R.id.frag_taskslist );
         tasksListFragment = findFragmentById;
      }
      
      onNewIntent( getIntent() );
   }
   


   @Override
   protected void onStop()
   {
      super.onStop();
      
      UIUtils.showTitleBarAddTask( this, false );
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
   }
   


   @Override
   protected void onNewIntent( Intent intent )
   {
      UIUtils.showTitleBarAddTask( this, false );
      
      setIntent( intent );
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( getIntent().getExtras() );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      
      if ( state != null )
      {
         getIntent().putExtras( state );
      }
   }
   


   public void onTaskSortChanged( int newTaskSort )
   {
   }
   


   protected void updateTitleBar( TitleBarLayout titleBar )
   {
      
   }
   


   private static void updateTitleBarSmartFilter( IFilter filter,
                                                  TitleBarLayout titleBar )
   {
      if ( filter instanceof RtmSmartFilter )
         titleBar.setAddTaskFilter( (RtmSmartFilter) filter );
   }
   


   protected final ListTask getTask( int pos )
   {
      return tasksListFragment.getTask( pos );
   }
   


   protected int getTaskSortValue( int idx )
   {
      return tasksListFragment.getTaskSortValue( idx );
   }
   


   protected int getTaskSortIndex( int value )
   {
      return tasksListFragment.getTaskSortIndex( value );
   }
   


   protected int getTaskSort()
   {
      return getIntent().getIntExtra( TASK_SORT_ORDER,
                                      Settings.TASK_SORT_DEFAULT );
   }
   


   protected void setTaskSort( int taskSort, boolean refillList )
   {
      getIntent().putExtra( TASK_SORT_ORDER, taskSort );
      
      if ( refillList )
         fillListAsync();
   }
   


   protected boolean isSameTaskSortLikeCurrent( int sortOrder )
   {
      return getTaskSort() == sortOrder;
   }
}
