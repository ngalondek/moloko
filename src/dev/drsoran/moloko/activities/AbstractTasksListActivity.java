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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.AbstractTaskListFragment;
import dev.drsoran.moloko.fragments.factories.TasksListFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.ITasksListListener;
import dev.drsoran.moloko.layouts.TitleBarLayout;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListActivity extends FragmentActivity implements
         ITasksListListener, IConfigurable
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTasksListActivity.class.getSimpleName();
   
   
   public static class Config extends AbstractTaskListFragment.Config
   {
      public final static String TITLE = "title";
      
      public final static String TITLE_ICON = "title_icon";
   }
   
   private Bundle configuration;
   
   

   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case android.R.id.home:
            startActivity( Intents.createOpenHomeIntent( this ) );
            return true;
      }
      
      return super.onOptionsItemSelected( item );
   }
   


   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
      
      final Bundle intentConfig = new Bundle( getIntent().getExtras() );
      if ( savedInstanceState != null )
         intentConfig.putAll( savedInstanceState );
      
      configure( intentConfig );
      
      // First-time init; create fragment to embed in activity.
      if ( savedInstanceState == null )
         initTasksListWithConfiguration( getIntent(), intentConfig );
   }
   


   @Override
   protected void onStop()
   {
      super.onStop();
      
      UIUtils.showTitleBarAddTask( this, false );
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
      outState.putAll( getConfiguration() );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      configure( state );
   }
   


   @Override
   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   


   @Override
   public void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
      {
         if ( config.containsKey( Config.TITLE ) )
            configuration.putString( Config.TITLE,
                                     config.getString( Config.TITLE ) );
         if ( config.containsKey( Config.TITLE_ICON ) )
            configuration.putInt( Config.TITLE_ICON,
                                  config.getInt( Config.TITLE_ICON ) );
      }
   }
   


   @Override
   public Bundle createDefaultConfiguration()
   {
      final Bundle bundle = new Bundle();
      
      bundle.putString( Config.TITLE, getString( R.string.app_name ) );
      
      return bundle;
   }
   


   private Bundle getCurrentTasksListFragmentConfiguration()
   {
      return new Bundle( getTasksListFragment().getConfiguration() );
   }
   


   @Override
   public void onTaskSortChanged( int newTaskSort )
   {
      final Bundle config = getCurrentTasksListFragmentConfiguration();
      config.putInt( Config.TASK_SORT_ORDER, newTaskSort );
      
      newTasksListFragmentbyIntent( getNewConfiguredIntent( config ) );
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
   


   protected final Task getTask( int pos )
   {
      return getTasksListFragment().getTask( pos );
   }
   


   protected int getTaskSortValue( int idx )
   {
      return getTasksListFragment().getTaskSortValue( idx );
   }
   


   protected int getTaskSortIndex( int value )
   {
      return getTasksListFragment().getTaskSortIndex( value );
   }
   


   protected int getTaskSort()
   {
      return getTasksListFragment().getTaskSortConfiguration();
   }
   


   protected boolean isSameTaskSortLikeCurrent( int sortOrder )
   {
      return getTasksListFragment().getTaskSortConfiguration() == sortOrder;
   }
   


   protected IFilter getFilter()
   {
      return getTasksListFragment().getFilter();
   }
   


   @SuppressWarnings( "unchecked" )
   protected AbstractTaskListFragment< ? extends Task > getTasksListFragment()
   {
      return (AbstractTaskListFragment< ? extends Task >) getSupportFragmentManager().findFragmentById( R.id.frag_taskslist );
   }
   


   private void initTasksListWithConfiguration( Intent intent, Bundle config )
   {
      final Fragment newTasksListFragment = getNewTasksListFragmentInstance( intent );
      
      if ( newTasksListFragment != null )
      {
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.add( R.id.frag_taskslist, newTasksListFragment );
         
         transaction.commit();
      }
   }
   


   protected void reloadTasksListWithConfiguration( Bundle config )
   {
      newTasksListFragmentbyIntent( getNewConfiguredIntent( config ) );
   }
   


   protected void newTasksListFragmentbyIntent( Intent intent )
   {
      final Fragment newTasksListFragment = getNewTasksListFragmentInstance( intent );
      
      if ( newTasksListFragment != null )
      {
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.replace( R.id.frag_taskslist, newTasksListFragment );
         transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
         
         transaction.commit();
      }
   }
   


   protected Fragment getNewTasksListFragmentInstance( Intent intent )
   {
      return TasksListFragmentFactory.newFragment( this, intent );
   }
   


   private Intent getNewConfiguredIntent( Bundle config )
   {
      final Intent newIntent = getTasksListFragment().newDefaultIntent();
      newIntent.putExtras( config );
      
      return newIntent;
   }
}
