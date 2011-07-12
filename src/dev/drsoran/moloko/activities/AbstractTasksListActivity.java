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

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.OnNavigationListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.text.TextUtils;
import android.widget.SpinnerAdapter;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ActionBarNavigationAdapter;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.moloko.fragments.factories.TasksListFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.ITasksListListener;
import dev.drsoran.moloko.layouts.TitleBarLayout;
import dev.drsoran.moloko.loaders.RtmListWithTaskCountLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListActivity extends FragmentActivity implements
         ITasksListListener, IConfigurable, OnNavigationListener,
         LoaderCallbacks< List< RtmListWithTaskCount > >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTasksListActivity.class.getSimpleName();
   
   
   public static class Config extends AbstractTasksListFragment.Config
   {
      public final static String TITLE = "title";
      
      public final static String TITLE_ICON = "title_icon";
      
      public final static String LIST_NAME = Lists.LIST_NAME;
   }
   

   protected static class OptionsMenu
   {
      public final static int QUICK_ADD_TASK = R.id.menu_quick_add_task;
      
      public final static int SEARCH = R.id.menu_search_tasks;
      
      public final static int DELETE_LIST = R.id.menu_delete_list;
   }
   
   private final static int LISTS_LOADER_ID = 1;
   
   private SpinnerAdapter actionBarNavigationAdapter;
   
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
      final Bundle intentConfig = new Bundle( getIntent().getExtras() );
      if ( savedInstanceState != null )
         intentConfig.putAll( savedInstanceState );
      
      configure( intentConfig );
      
      // TODO: ActionBarSherlock workaround
      // https://github.com/JakeWharton/ActionBarSherlock/issues/35
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.taskslist_activity );
      
      getSupportActionBar().setTitle( getConfiguredTitle() );
      
      determineActionBarNavigationMode();
      
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
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.QUICK_ADD_TASK,
                                   getString( R.string.app_task_add ),
                                   Menu.CATEGORY_CONTAINER,
                                   Menu.NONE,
                                   R.drawable.ic_button_title_add_task,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   true )
             .setActionView( R.layout.quick_add_task_action_view );
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.SEARCH,
                                   getString( R.string.search_hint ),
                                   Menu.CATEGORY_ALTERNATIVE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_search,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   true );
      
      return true;
   }
   


   protected void determineActionBarNavigationMode()
   {
      // If we are opened for a list, then we show the other lists as navigation
      // alternative
      if ( isConfiguredWithListName() )
      {
         getSupportLoaderManager().initLoader( LISTS_LOADER_ID,
                                               Bundle.EMPTY,
                                               this );
      }
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
         if ( config.containsKey( Config.LIST_NAME ) )
            configuration.putString( Config.LIST_NAME,
                                     config.getString( Config.LIST_NAME ) );
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
   


   private Bundle getCurrentActivityAndFragmentsConfiguration()
   {
      final Bundle completeConfig = getConfiguration();
      completeConfig.putAll( getCurrentTasksListFragmentConfiguration() );
      
      return completeConfig;
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
   


   protected String getConfiguredListName()
   {
      return configuration.getString( Config.LIST_NAME );
   }
   


   protected String getConfiguredTitle()
   {
      final String title = configuration.getString( Config.TITLE );
      return !TextUtils.isEmpty( title ) ? title
                                        : getString( R.string.app_name );
   }
   


   protected boolean isConfiguredWithListName()
   {
      return !TextUtils.isEmpty( getConfiguredListName() );
   }
   


   protected boolean isInDropDownNavigationMode()
   {
      return getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST;
   }
   


   protected SpinnerAdapter createActionBarNavigationAdapterForResult( List< RtmListWithTaskCount > lists )
   {
      final List< ActionBarNavigationAdapter.Item > items = new ArrayList< ActionBarNavigationAdapter.Item >( lists.size() );
      for ( RtmListWithTaskCount rtmListWithTaskCount : lists )
         items.add( new ActionBarNavigationAdapter.Item( R.drawable.ic_button_task_list,
                                                         rtmListWithTaskCount.getName() ) );
      
      actionBarNavigationAdapter = new ActionBarNavigationAdapter( this, items );
      return actionBarNavigationAdapter;
   }
   


   @Override
   public Loader< List< RtmListWithTaskCount >> onCreateLoader( int id,
                                                                Bundle args )
   {
      return new RtmListWithTaskCountLoader( this, null );
   }
   


   @Override
   public void onLoadFinished( Loader< List< RtmListWithTaskCount >> loader,
                               List< RtmListWithTaskCount > data )
   {
      if ( data.size() > 1 )
      {
         getSupportActionBar().setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
         getSupportActionBar().setListNavigationCallbacks( createActionBarNavigationAdapterForResult( data ),
                                                           this );
         
         int pos = -1;
         for ( int i = 0, cnt = data.size(); i < cnt && pos == -1; i++ )
         {
            final RtmListWithTaskCount list = data.get( i );
            if ( list.getName().equalsIgnoreCase( getConfiguredListName() ) )
               pos = i;
         }
         
         if ( pos != -1 )
            getSupportActionBar().setSelectedNavigationItem( pos );
         
         invalidateOptionsMenu();
      }
   }
   


   @Override
   public void onLoaderReset( Loader< List< RtmListWithTaskCount >> loader )
   {
      getSupportActionBar().setNavigationMode( ActionBar.NAVIGATION_MODE_STANDARD );
   }
   


   @Override
   public boolean onNavigationItemSelected( int itemPosition, long itemId )
   {
      boolean handled = false;
      
      final ActionBarNavigationAdapter.Item item = (ActionBarNavigationAdapter.Item) actionBarNavigationAdapter.getItem( itemPosition );
      
      if ( !item.itemText.equalsIgnoreCase( getConfiguredListName() ) )
      {
         final Bundle newConfig = getCurrentActivityAndFragmentsConfiguration();
         newConfig.putAll( Intents.Extras.createOpenListExtrasByName( this,
                                                                      item.itemText,
                                                                      null ) );
         
         configure( newConfig );
         reloadTasksListWithConfiguration( newConfig );
         
         handled = true;
      }
      
      return handled;
   }
   


   @SuppressWarnings( "unchecked" )
   protected AbstractTasksListFragment< ? extends Task > getTasksListFragment()
   {
      return (AbstractTasksListFragment< ? extends Task >) getSupportFragmentManager().findFragmentById( R.id.frag_taskslist );
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
