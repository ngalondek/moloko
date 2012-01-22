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

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.OnNavigationListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.widget.SpinnerAdapter;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.QuickAddTaskActionBarSwitcher;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ActionBarNavigationAdapter;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.moloko.fragments.QuickAddTaskActionBarFragment;
import dev.drsoran.moloko.fragments.factories.TasksListFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.IQuickAddTaskActionBarFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IQuickAddTaskButtonBarFragmentListener;
import dev.drsoran.moloko.fragments.listeners.ITasksListFragmentListener;
import dev.drsoran.moloko.loaders.RtmListWithTaskCountLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListActivity extends MolokoEditFragmentActivity
         implements ITasksListFragmentListener, OnNavigationListener,
         IQuickAddTaskActionBarFragmentListener,
         IQuickAddTaskButtonBarFragmentListener,
         LoaderCallbacks< List< RtmListWithTaskCount > >
{
   private final static int[] FRAGMENT_IDS =
   { R.id.frag_quick_add_task_action_bar, R.id.frag_taskslist,
    R.id.frag_quick_add_task_button_bar };
   
   
   public static class Config
   {
      public final static String TITLE = "title";
      
      public final static String SUB_TITLE = "sub_title";
      
      public final static String LIST_NAME = Lists.LIST_NAME;
   }
   
   private final static int LISTS_LOADER_ID = 1;
   
   private QuickAddTaskActionBarSwitcher quickAddTaskActionBarSwitcher;
   
   private SpinnerAdapter actionBarNavigationAdapter;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.taskslist_activity );
      
      quickAddTaskActionBarSwitcher = new QuickAddTaskActionBarSwitcher( this,
                                                                         savedInstanceState );
      initializeActionBar();
      
      if ( savedInstanceState == null )
         initTasksListWithConfiguration( getIntent(), configuration );
   }
   
   
   
   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      quickAddTaskActionBarSwitcher.saveInstanceState( outState );
   }
   
   
   
   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      quickAddTaskActionBarSwitcher.restoreInstanceState( state );
   }
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      setTitleAndNavigationMode();
   }
   
   
   
   private void initializeActionBar()
   {
      quickAddTaskActionBarSwitcher.showInLastState();
      setTitleAndNavigationMode();
   }
   
   
   
   private void setTitleAndNavigationMode()
   {
      getSupportActionBar().setTitle( getConfiguredTitle() );
      getSupportActionBar().setSubtitle( getConfiguredSubTitle() );
      setActionBarNavigationMode();
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( isQuickAddTaskFragmentOpen() )
         showQuickAddTaskActionBarFragment( false );
      else
         super.onBackPressed();
   }
   
   
   
   protected void setActionBarNavigationMode()
   {
      // If we are opened for a list, then we show the other lists as navigation
      // alternative
      if ( isConfiguredWithListName() )
      {
         getSupportLoaderManager().initLoader( LISTS_LOADER_ID,
                                               Bundle.EMPTY,
                                               this );
      }
      else
      {
         setDropDownNavigationMode( null );
      }
   }
   
   
   
   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.TITLE ) )
         configuration.putString( Config.TITLE, config.getString( Config.TITLE ) );
      if ( config.containsKey( Config.SUB_TITLE ) )
         configuration.putString( Config.SUB_TITLE,
                                  config.getString( Config.SUB_TITLE ) );
      if ( config.containsKey( Config.LIST_NAME ) )
         configuration.putString( Config.LIST_NAME,
                                  config.getString( Config.LIST_NAME ) );
   }
   
   
   
   @Override
   public void putDefaultConfigurationTo( Bundle bundle )
   {
      super.putDefaultConfigurationTo( bundle );
      
      bundle.putString( Config.TITLE, getString( R.string.app_name ) );
   }
   
   
   
   private Bundle getCurrentTasksListFragmentConfiguration()
   {
      return getFragmentConfigurations( R.id.frag_taskslist );
   }
   
   
   
   private Bundle getCurrentActivityAndFragmentsConfiguration()
   {
      return getActivityAndFragmentsConfiguration( R.id.frag_taskslist );
   }
   
   
   
   @Override
   public void onTaskSortChanged( int newTaskSort )
   {
      final Bundle config = getCurrentTasksListFragmentConfiguration();
      config.putInt( AbstractTasksListFragment.Config.TASK_SORT_ORDER,
                     newTaskSort );
      
      newTasksListFragmentbyIntent( getNewConfiguredIntent( config ) );
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
   
   
   
   protected IFilter getConfiguredFilter()
   {
      return getTasksListFragment().getFilter();
   }
   
   
   
   protected String getConfiguredListName()
   {
      return configuration.getString( Config.LIST_NAME );
   }
   
   
   
   protected void configuredTitle( String title )
   {
      configuration.putString( Config.TITLE, title );
   }
   
   
   
   protected String getConfiguredTitle()
   {
      final String title = configuration.getString( Config.TITLE );
      return !TextUtils.isEmpty( title ) ? title
                                        : getString( R.string.app_name );
   }
   
   
   
   protected String getConfiguredSubTitle()
   {
      return Strings.nullIfEmpty( configuration.getString( Config.SUB_TITLE ) );
   }
   
   
   
   protected boolean isConfiguredWithListName()
   {
      return !TextUtils.isEmpty( getConfiguredListName() );
   }
   
   
   
   protected boolean isInDropDownNavigationMode()
   {
      return getSupportActionBar() != null
         && getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST;
   }
   
   
   
   protected void setDropDownNavigationMode( SpinnerAdapter spinnerAdapter )
   {
      if ( getSupportActionBar() != null )
      {
         if ( spinnerAdapter != null )
         {
            getSupportActionBar().setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
            getSupportActionBar().setListNavigationCallbacks( spinnerAdapter,
                                                              this );
         }
         else
         {
            getSupportActionBar().setNavigationMode( ActionBar.NAVIGATION_MODE_STANDARD );
         }
      }
   }
   
   
   
   protected SpinnerAdapter createActionBarNavigationAdapterForResult( List< RtmListWithTaskCount > lists )
   {
      final List< String > items = new ArrayList< String >( lists.size() );
      
      for ( RtmListWithTaskCount rtmListWithTaskCount : lists )
      {
         items.add( rtmListWithTaskCount.getName() );
      }
      
      actionBarNavigationAdapter = new ActionBarNavigationAdapter( this, items );
      return actionBarNavigationAdapter;
   }
   
   
   
   @Override
   public Loader< List< RtmListWithTaskCount >> onCreateLoader( int id,
                                                                Bundle args )
   {
      return new RtmListWithTaskCountLoader( this );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< RtmListWithTaskCount >> loader,
                               List< RtmListWithTaskCount > data )
   {
      if ( data.size() > 1 )
      {
         setDropDownNavigationMode( createActionBarNavigationAdapterForResult( data ) );
         
         final String configuredListName = Strings.emptyIfNull( getConfiguredListName() );
         
         int pos = -1;
         for ( int i = 0, cnt = data.size(); i < cnt && pos == -1; i++ )
         {
            final RtmListWithTaskCount list = data.get( i );
            if ( list.getName().equalsIgnoreCase( configuredListName ) )
               pos = i;
         }
         
         if ( pos != -1 )
         {
            getSupportActionBar().setTitle( null );
            getSupportActionBar().setSelectedNavigationItem( pos );
         }
         
         invalidateOptionsMenu();
      }
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< List< RtmListWithTaskCount >> loader )
   {
      setDropDownNavigationMode( null );
   }
   
   
   
   @Override
   public boolean onNavigationItemSelected( int itemPosition, long itemId )
   {
      boolean handled = false;
      
      final String item = (String) actionBarNavigationAdapter.getItem( itemPosition );
      
      if ( !item.equalsIgnoreCase( getConfiguredListName() ) )
      {
         final Bundle newConfig = getCurrentActivityAndFragmentsConfiguration();
         newConfig.putAll( Intents.Extras.createOpenListExtrasByName( this,
                                                                      item,
                                                                      null ) );
         reloadTasksListWithConfiguration( newConfig );
         
         handled = true;
      }
      
      return handled;
   }
   
   
   
   protected void showQuickAddTaskActionBarFragment( boolean show )
   {
      if ( show )
      {
         quickAddTaskActionBarSwitcher.showSwitched( createQuickAddTaskActionBarFragmentConfiguration() );
      }
      else
      {
         quickAddTaskActionBarSwitcher.showUnswitched();
      }
   }
   
   
   
   @Override
   public void onCloseQuickAddTaskFragment()
   {
      showQuickAddTaskActionBarFragment( false );
   }
   
   
   
   protected boolean isQuickAddTaskFragmentOpen()
   {
      return quickAddTaskActionBarSwitcher.isSwitched();
   }
   
   
   
   protected Bundle createQuickAddTaskActionBarFragmentConfiguration()
   {
      final Bundle config = new Bundle();
      
      config.putParcelable( QuickAddTaskActionBarFragment.Config.FILTER,
                            getConfiguredFilter() );
      return config;
   }
   
   
   
   @Override
   public void onQuickAddAddNewTask( Bundle parsedValues )
   {
      showQuickAddTaskActionBarFragment( false );
      startActivity( Intents.createAddTaskIntent( this, parsedValues ) );
   }
   
   
   
   @Override
   public void onQuickAddTaskOperatorSelected( char operator )
   {
      quickAddTaskActionBarSwitcher.insertOperator( operator );
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
      final Intent newIntent = getNewConfiguredIntent( config );
      
      onNewIntent( newIntent );
      newTasksListFragmentbyIntent( newIntent );
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
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return FRAGMENT_IDS;
   }
}
