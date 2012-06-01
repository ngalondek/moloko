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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.TasksListNavigationAdapter;
import dev.drsoran.moloko.adapters.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.moloko.fragments.listeners.ITasksListFragmentListener;
import dev.drsoran.moloko.loaders.RtmListWithTaskCountLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.Task;


public abstract class AbstractTasksListActivity extends
         MolokoEditFragmentActivity implements ITasksListFragmentListener,
         OnNavigationListener, OnBackStackChangedListener,
         LoaderCallbacks< List< RtmListWithTaskCount > >
{
   private final static int[] FRAGMENT_IDS =
   { R.id.frag_taskslist };
   
   private final static String SELECTED_NAVIGATION_ID = "sel_nav_id";
   
   protected final static String CUSTOM_NAVIGATION_ITEM_ID = "0";
   
   @InstanceState( key = Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                   defaultValue = InstanceState.NULL )
   private String subTitle;
   
   @InstanceState( key = SELECTED_NAVIGATION_ID,
                   defaultValue = InstanceState.NULL )
   private String selectedNavigationItemId;
   
   private TasksListNavigationAdapter actionBarNavigationAdapter;
   
   
   
   protected AbstractTasksListActivity()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AbstractTasksListActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.taskslist_activity );
      setTitleFromConfiguration( savedInstanceState != null
                                                           ? savedInstanceState
                                                           : getIntent().getExtras() );
      
      initialize();
      
      getSupportFragmentManager().addOnBackStackChangedListener( this );
   }
   
   
   
   protected void initialize()
   {
      initializeTitle();
      initializeSelectedNavigationItemId();
      initializeActionBar();
      initializeTasksListFragment();
   }
   
   
   
   protected void initializeTitle()
   {
      if ( TextUtils.isEmpty( getTitle() ) )
      {
         final String intentListName = getListNameFromIntent();
         if ( !TextUtils.isEmpty( intentListName ) )
         {
            setTitle( intentListName );
         }
      }
   }
   
   
   
   protected void initializeSelectedNavigationItemId()
   {
      if ( selectedNavigationItemId == null )
      {
         final String listIdFromIntent = getListIdFromIntent();
         selectedNavigationItemId = !TextUtils.isEmpty( listIdFromIntent )
                                                                          ? listIdFromIntent
                                                                          : CUSTOM_NAVIGATION_ITEM_ID;
      }
   }
   
   
   
   protected void initializeActionBar()
   {
      setStandardNavigationMode();
      startLoadingRtmLists();
   }
   
   
   
   @Override
   public void onBackStackChanged()
   {
      if ( isListNavigationMode() )
      {
         selectedNavigationItemId = getTasksListFragment().getTag();
         updateNavigationAdapterSelectedIndex();
      }
   }
   
   
   
   private void startLoadingRtmLists()
   {
      getSupportLoaderManager().initLoader( RtmListWithTaskCountLoader.ID,
                                            Bundle.EMPTY,
                                            this );
   }
   
   
   
   protected Bundle getCurrentTasksListFragmentConfiguration()
   {
      return getFragmentConfigurations( R.id.frag_taskslist );
   }
   
   
   
   protected final Task getTask( int pos )
   {
      return getTasksListFragment().getTask( pos );
   }
   
   
   
   protected final Task getTask( String taskId )
   {
      return getTasksListFragment().getTask( taskId );
   }
   
   
   
   protected int getTaskSort()
   {
      return getTasksListFragment().getTaskSort();
   }
   
   
   
   protected boolean isSameTaskSortLikeCurrent( int sortOrder )
   {
      return getTasksListFragment().getTaskSort() == sortOrder;
   }
   
   
   
   public IFilter getConfiguredFilter()
   {
      return getTasksListFragment().getFilter();
   }
   
   
   
   private void setTitleFromConfiguration( Bundle bundle )
   {
      setTitle( bundle.getString( Intents.Extras.KEY_ACTIVITY_TITLE ) );
   }
   
   
   
   protected boolean hasListNameInIntent()
   {
      return getIntent().getExtras().containsKey( Intents.Extras.KEY_LIST_NAME );
   }
   
   
   
   protected String getListNameFromIntent()
   {
      final String listName = getIntent().getExtras()
                                         .getString( Intents.Extras.KEY_LIST_NAME );
      return listName;
   }
   
   
   
   protected String getListIdFromIntent()
   {
      final String listId = getIntent().getExtras()
                                       .getString( Intents.Extras.KEY_LIST_ID );
      return listId;
   }
   
   
   
   protected IItem getNavigationItem( int position )
   {
      if ( !isListNavigationMode() )
      {
         throw new UnsupportedOperationException( "No list navigation mode." );
      }
      
      return actionBarNavigationAdapter.getItem( position );
   }
   
   
   
   protected void setStandardNavigationMode()
   {
      final ActionBar actionBar = getSupportActionBar();
      
      actionBar.setSubtitle( subTitle );
      actionBar.setDisplayShowTitleEnabled( true );
      actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_STANDARD );
      actionBar.setListNavigationCallbacks( null, null );
   }
   
   
   
   public boolean isStandardNavigationMode()
   {
      return getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_STANDARD;
   }
   
   
   
   protected void setListNavigationMode()
   {
      final ActionBar actionBar = getSupportActionBar();
      
      actionBar.setDisplayShowTitleEnabled( false );
      actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
      actionBar.setListNavigationCallbacks( actionBarNavigationAdapter, this );
      
      updateNavigationAdapterSelectedIndex();
   }
   
   
   
   public boolean isListNavigationMode()
   {
      return getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST;
   }
   
   
   
   private void updateNavigationAdapterSelectedIndex()
   {
      final int selectedItemIndex = getNavigationItemIndexById();
      actionBarNavigationAdapter.setSelectedItemIndex( selectedItemIndex );
      
      if ( selectedItemIndex == -1 )
      {
         // TODO: We couldn't restore the selected item. Show error.
      }
      else
      {
         getSupportActionBar().setSelectedNavigationItem( selectedItemIndex );
      }
   }
   
   
   
   private void createNavigationAdapterForResult( List< RtmListWithTaskCount > lists )
   {
      final List< IItem > items = new ArrayList< IItem >( lists.size() );
      final Context context = getSupportActionBar().getThemedContext();
      
      for ( Iterator< RtmListWithTaskCount > i = lists.iterator(); i.hasNext(); )
      {
         final RtmListWithTaskCount list = i.next();
         items.add( new TasksListNavigationAdapter.RtmListItem( context, list ) );
      }
      
      if ( !hasListNameInIntent() )
      {
         items.add( 0,
                    new TasksListNavigationAdapter.CustomItem( CUSTOM_NAVIGATION_ITEM_ID,
                                                               getTitle().toString(),
                                                               getIntent().getExtras() ) );
      }
      
      actionBarNavigationAdapter = new TasksListNavigationAdapter( context,
                                                                   items );
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
         createNavigationAdapterForResult( data );
         setListNavigationMode();
      }
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< List< RtmListWithTaskCount >> loader )
   {
      setStandardNavigationMode();
      actionBarNavigationAdapter = null;
   }
   
   
   
   private int getNavigationItemIndexById()
   {
      final IItem selectedItem = actionBarNavigationAdapter.getItem( selectedNavigationItemId );
      
      return selectedItem != null
                                 ? actionBarNavigationAdapter.getPosition( selectedItem )
                                 : -1;
   }
   
   
   
   @Override
   public boolean onNavigationItemSelected( int itemPosition, long itemId )
   {
      boolean handled = false;
      
      actionBarNavigationAdapter.setSelectedItemIndex( itemPosition );
      final IItem selectedItem = actionBarNavigationAdapter.getItem( itemPosition );
      
      if ( !selectedItem.getId().equals( selectedNavigationItemId ) )
      {
         final Bundle newConfig = getCurrentTasksListFragmentConfiguration();
         newConfig.putAll( actionBarNavigationAdapter.getTasksListConfigForItem( itemPosition ) );
         
         reloadTasksListWithConfiguration( newConfig );
         handled = true;
      }
      
      return handled;
   }
   
   
   
   private AbstractTasksListFragment< ? > getTasksListFragment()
   {
      final AbstractTasksListFragment< ? > fragment = (AbstractTasksListFragment< ? >) findAddedFragmentById( R.id.frag_taskslist );
      return fragment;
   }
   
   
   
   private void initializeTasksListFragment()
   {
      if ( findAddedFragmentById( R.id.frag_taskslist ) == null )
      {
         final Fragment fragment = createTasksListFragment( getIntent().getExtras() );
         
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.add( R.id.frag_taskslist,
                          fragment,
                          selectedNavigationItemId );
         transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
         transaction.commit();
      }
   }
   
   
   
   protected void reloadTasksListWithConfiguration( Bundle config )
   {
      final Fragment fragment = createTasksListFragment( config );
      final String fragmentTag = isListNavigationMode()
                                                       ? getSelectedNavigationItemId()
                                                       : null;
      
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      transaction.replace( R.id.frag_taskslist, fragment, fragmentTag );
      transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
      transaction.addToBackStack( null );
      transaction.commit();
   }
   
   
   
   private String getSelectedNavigationItemId()
   {
      return actionBarNavigationAdapter.getItem( getSupportActionBar().getSelectedNavigationIndex() )
                                       .getId();
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return FRAGMENT_IDS;
   }
   
   
   
   protected abstract Fragment createTasksListFragment( Bundle config );
}
