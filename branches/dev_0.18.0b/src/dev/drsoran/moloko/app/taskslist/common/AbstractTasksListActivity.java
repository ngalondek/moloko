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

package dev.drsoran.moloko.app.taskslist.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.SparseArray;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoEditFragmentActivity;
import dev.drsoran.moloko.app.loaders.TasksListsLoader;
import dev.drsoran.moloko.app.taskslist.common.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


abstract class AbstractTasksListActivity extends MolokoEditFragmentActivity
         implements ITasksListFragmentListener, OnNavigationListener,
         OnBackStackChangedListener, LoaderCallbacks< List< TasksList > >
{
   protected final static long CUSTOM_NAVIGATION_ITEM_ID = 0L;
   
   private final static int INITIAL_FRAGMENT_BACK_STACK_ID = -1;
   
   @InstanceState( key = Intents.Extras.KEY_ACTIVITY_TITLE,
                   defaultValue = InstanceState.NULL )
   private String title;
   
   @InstanceState( key = Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                   defaultValue = InstanceState.NULL )
   private String subTitle;
   
   @InstanceState( key = "sel_nav_item",
                   defaultValue = InstanceState.NO_DEFAULT )
   private SelectedNavigationItem selectedNavigationItem;
   
   @InstanceState( key = "backstack_nav_items",
                   defaultValue = InstanceState.NO_DEFAULT )
   private SparseArray< SelectedNavigationItem > backStackNavigationItems;
   
   private List< TasksList > loadedRtmLists;
   
   private TasksListNavigationAdapter actionBarNavigationAdapter;
   
   private boolean updateNavigationAdapter;
   
   
   
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
      initialize();
   }
   
   
   
   protected void initialize()
   {
      initializeHomeNavigation();
      initializeTitle();
      initializeTasksListFragment();
      
      // Initialize the ActionBar, and possible list navigation,
      // after the initial fragment has been added.
      initializeActionBar();
   }
   
   
   
   protected void initializeHomeNavigation()
   {
      if ( Intents.HomeAction.BACK.equals( getHomeAction() )
         || Intents.HomeAction.ACTIVITY.equals( getHomeAction() ) )
      {
         getSupportActionBar().setDisplayHomeAsUpEnabled( true );
      }
      else
      {
         getSupportActionBar().setDisplayHomeAsUpEnabled( false );
      }
   }
   
   
   
   protected void initializeTitle()
   {
      if ( !TextUtils.isEmpty( title ) )
      {
         setTitle( title );
         return;
      }
      
      title = getListNameFromIntentExtras();
      if ( !TextUtils.isEmpty( title ) )
      {
         setTitle( title );
         return;
      }
      
      title = getTitleFromActiveList();
      if ( !TextUtils.isEmpty( title ) )
      {
         setTitle( title );
         return;
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
      final BackStackEntry topOfStack = getTopOfBackStack();
      final int backStackId = topOfStack != null
                                                ? topOfStack.getId()
                                                : INITIAL_FRAGMENT_BACK_STACK_ID;
      
      final SelectedNavigationItem backStackSelectedItem = backStackNavigationItems.get( backStackId );
      
      // Push to stack
      if ( backStackSelectedItem == null )
      {
         backStackNavigationItems.append( backStackId, selectedNavigationItem );
      }
      
      // Pop of stack
      else if ( !backStackSelectedItem.equals( selectedNavigationItem ) )
      {
         updateNavigationAdapter = backStackSelectedItem.id != selectedNavigationItem.id;
         
         backStackNavigationItems.removeAt( backStackNavigationItems.size() - 1 );
         selectedNavigationItem = backStackSelectedItem;
         
         // TODO: What happens if the list is already deleted by a background sync?
      }
      
      if ( updateNavigationAdapter )
      {
         setNavigationAdapter();
         updateNavigationAdapter = false;
      }
      else
      {
         getSupportActionBar().setSelectedNavigationItem( selectedNavigationItem.position );
      }
   }
   
   
   
   private BackStackEntry getTopOfBackStack()
   {
      final FragmentManager fragmentManager = getSupportFragmentManager();
      final int backStackSize = fragmentManager.getBackStackEntryCount();
      
      return backStackSize > 0
                              ? fragmentManager.getBackStackEntryAt( backStackSize - 1 )
                              : null;
   }
   
   
   
   public Bundle getCurrentTasksListFragmentConfiguration()
   {
      return getFragmentConfigurations( R.id.frag_taskslist );
   }
   
   
   
   public final Task getTask( int pos )
   {
      return getTasksListFragment().getTask( pos );
   }
   
   
   
   public final Task getTask( long taskId )
   {
      return getTasksListFragment().getTask( taskId );
   }
   
   
   
   public int getTaskSort()
   {
      return getTasksListFragment().getTaskSort();
   }
   
   
   
   public boolean isSameTaskSortLikeCurrent( int sortOrder )
   {
      return getTasksListFragment().getTaskSort() == sortOrder;
   }
   
   
   
   public RtmSmartFilter getActiveFilter()
   {
      return getTasksListFragment().getFilter();
   }
   
   
   
   public boolean hasListNameInIntentExtras()
   {
      return getIntent().getExtras().containsKey( Intents.Extras.KEY_LIST_NAME );
   }
   
   
   
   public String getListNameFromIntentExtras()
   {
      final String listName = getIntent().getExtras()
                                         .getString( Intents.Extras.KEY_LIST_NAME );
      return listName;
   }
   
   
   
   public long getListIdFromIntent()
   {
      long listId = getListIdFromIntentExtras();
      if ( listId == Constants.NO_ID )
      {
         listId = getListIdFromIntentUri();
      }
      
      return listId;
   }
   
   
   
   public long getListIdFromIntentExtras()
   {
      final long listId = getIntent().getExtras()
                                     .getLong( Intents.Extras.KEY_LIST_ID,
                                               Constants.NO_ID );
      return listId;
   }
   
   
   
   public long getListIdFromIntentUri()
   {
      final Uri intentUri = getIntent().getData();
      if ( ContentUris.MATCHER.match( intentUri ) == ContentUris.MATCH_TASKS_LISTS_ID )
      {
         return ContentUris.getLastPathIdFromUri( intentUri );
      }
      
      return Constants.NO_ID;
   }
   
   
   
   public long getActiveListId()
   {
      if ( isListNavigationMode() )
      {
         return selectedNavigationItem.id;
      }
      
      return getListIdFromIntent();
   }
   
   
   
   protected IItem getNavigationItem( int position )
   {
      if ( !isListNavigationMode() )
      {
         throw new UnsupportedOperationException( "No list navigation mode." );
      }
      
      return actionBarNavigationAdapter.getItem( position );
   }
   
   
   
   protected int getSelectedNavigationItemPosition()
   {
      if ( !isListNavigationMode() )
      {
         throw new UnsupportedOperationException( "No list navigation mode." );
      }
      
      return selectedNavigationItem.position;
   }
   
   
   
   protected void setStandardNavigationMode()
   {
      final ActionBar actionBar = getSupportActionBar();
      
      actionBar.setSubtitle( subTitle );
      actionBar.setDisplayShowTitleEnabled( true );
      
      if ( isListNavigationMode() )
      {
         getSupportFragmentManager().removeOnBackStackChangedListener( this );
         selectedNavigationItem = null;
         backStackNavigationItems = null;
         actionBarNavigationAdapter = null;
      }
      
      actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_STANDARD );
      actionBar.setListNavigationCallbacks( null, null );
   }
   
   
   
   public boolean isStandardNavigationMode()
   {
      return getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_STANDARD;
   }
   
   
   
   protected void setListNavigationMode()
   {
      initializeListNavigation();
      
      final ActionBar actionBar = getSupportActionBar();
      
      actionBar.setDisplayShowTitleEnabled( false );
      actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
      actionBar.setSelectedNavigationItem( selectedNavigationItem.position );
   }
   
   
   
   private void initializeListNavigation()
   {
      getSupportFragmentManager().addOnBackStackChangedListener( this );
      
      final boolean isFirstInitialization = selectedNavigationItem == null;
      if ( isFirstInitialization )
      {
         selectedNavigationItem = new SelectedNavigationItem();
         setSelectedNavigationItemIdFromIntent();
         setSelectedNavigationItemPositionFromSubTitle();
         
         backStackNavigationItems = new SparseArray< SelectedNavigationItem >();
         
         // The initial fragment gets the back stack ID -1 because it was added
         // without back stack.
         backStackNavigationItems.append( INITIAL_FRAGMENT_BACK_STACK_ID,
                                          selectedNavigationItem );
      }
      
      setNavigationAdapter();
   }
   
   
   
   private void setSelectedNavigationItemIdFromIntent()
   {
      final long listIdFromIntent = getListIdFromIntent();
      selectedNavigationItem.id = listIdFromIntent != Constants.NO_ID
                                                                     ? listIdFromIntent
                                                                     : CUSTOM_NAVIGATION_ITEM_ID;
   }
   
   
   
   private void setSelectedNavigationItemPositionFromSubTitle()
   {
      if ( !TextUtils.isEmpty( subTitle ) )
      {
         if ( getCompletedSubtitle().equals( subTitle ) )
         {
            selectedNavigationItem.position = TasksListNavigationAdapter.ITEM_POSITION_COMPLETED_TASKS;
         }
         else if ( getOverdueSubtitle().equals( subTitle ) )
         {
            selectedNavigationItem.position = TasksListNavigationAdapter.ITEM_POSITION_OVERDUE_TASKS;
         }
         else if ( getDueTodaySubtitle().equals( subTitle ) )
         {
            selectedNavigationItem.position = TasksListNavigationAdapter.ITEM_POSITION_TODAY_TASKS;
         }
         else if ( getDueTomorrowSubtitle().equals( subTitle ) )
         {
            selectedNavigationItem.position = TasksListNavigationAdapter.ITEM_POSITION_TOMORROW_TASKS;
         }
      }
   }
   
   
   
   public boolean isListNavigationMode()
   {
      return getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST;
   }
   
   
   
   private List< IItem > createActionBarNavigationItems()
   {
      final Context context = getSupportActionBar().getThemedContext();
      
      final List< IItem > actionBarNavigationItems = new ArrayList< IItem >( loadedRtmLists.size() );
      for ( Iterator< TasksList > i = loadedRtmLists.iterator(); i.hasNext(); )
      {
         final TasksList list = i.next();
         
         if ( list.getId() != selectedNavigationItem.id )
         {
            final IItem newListItem = new TasksListNavigationAdapter.RtmListItem( context,
                                                                                  list );
            actionBarNavigationItems.add( newListItem );
         }
         else
         {
            final ExtendedTaskCount extendedListInfo = list.getTasksCount();
            
            Bundle config = Intents.Extras.createOpenListExtras( list.getId() );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_DEFAULT_TASKS,
                                          new TasksListNavigationAdapter.RtmListItem( context,
                                                                                      list ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          new RtmSmartFilterBuilder().statusCompleted()
                                                                                     .toString() );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_COMPLETED_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getCompletedSubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.getCompletedTaskCount() ).setTasksListConfig( config ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          new RtmSmartFilterBuilder().dueBefore()
                                                                                     .today()
                                                                                     .toString() );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_OVERDUE_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getOverdueSubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.getOverDueTaskCount() ).setTasksListConfig( config ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          new RtmSmartFilterBuilder().due()
                                                                                     .today()
                                                                                     .toString() );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_TODAY_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getDueTodaySubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.getDueTodayTaskCount() ).setTasksListConfig( config ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          new RtmSmartFilterBuilder().due()
                                                                                     .tomorrow()
                                                                                     .toString() );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_TOMORROW_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getDueTomorrowSubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.getDueTomorrowTaskCount() ).setTasksListConfig( config ) );
         }
      }
      
      if ( selectedNavigationItem.id == CUSTOM_NAVIGATION_ITEM_ID )
      {
         actionBarNavigationItems.add( 0,
                                       new TasksListNavigationAdapter.CustomItem( CUSTOM_NAVIGATION_ITEM_ID,
                                                                                  getTitle().toString(),
                                                                                  null,
                                                                                  getIntent().getExtras() ) );
      }
      
      return actionBarNavigationItems;
   }
   
   
   
   private String getCompletedSubtitle()
   {
      return getString( R.string.taskslist_actionbar_subtitle_completed );
   }
   
   
   
   private String getOverdueSubtitle()
   {
      return getString( R.string.taskslist_actionbar_subtitle_overdue );
   }
   
   
   
   private String getDueTodaySubtitle()
   {
      return getString( R.string.taskslist_actionbar_subtitle_due_today );
   }
   
   
   
   private String getDueTomorrowSubtitle()
   {
      return getString( R.string.taskslist_actionbar_subtitle_due_tomorrow );
   }
   
   
   
   private void startLoadingRtmLists()
   {
      getSupportLoaderManager().initLoader( TasksListsLoader.ID,
                                            Bundle.EMPTY,
                                            this );
   }
   
   
   
   @Override
   public Loader< List< TasksList >> onCreateLoader( int id, Bundle args )
   {
      final TasksListsLoader loader = new TasksListsLoader( getAppContext().asDomainContext(),
                                                            true );
      loader.setRespectContentChanges( false );
      
      return loader;
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< TasksList >> loader,
                               List< TasksList > data )
   {
      loadedRtmLists = data;
      
      if ( loadedRtmLists != null && loadedRtmLists.size() > 1 )
      {
         if ( TextUtils.isEmpty( getTitle() ) )
         {
            initializeTitle();
         }
         
         if ( actionBarNavigationAdapter == null )
         {
            setListNavigationMode();
         }
         else
         {
            final List< IItem > navigationItems = createActionBarNavigationItems();
            actionBarNavigationAdapter.swap( navigationItems );
         }
      }
   }
   
   
   
   private void setNavigationAdapter()
   {
      createActionBarNavigationAdapter();
      getSupportActionBar().setListNavigationCallbacks( actionBarNavigationAdapter,
                                                        this );
   }
   
   
   
   private void createActionBarNavigationAdapter()
   {
      final List< IItem > navigationItems = createActionBarNavigationItems();
      actionBarNavigationAdapter = new TasksListNavigationAdapter( getSupportActionBar().getThemedContext(),
                                                                   navigationItems );
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< List< TasksList >> loader )
   {
      setStandardNavigationMode();
   }
   
   
   
   @Override
   public boolean onNavigationItemSelected( int itemPosition, long itemId )
   {
      final boolean hasChangedSelectedItemId = itemId != selectedNavigationItem.id;
      final boolean handled = hasChangedSelectedItemId
         || itemPosition != selectedNavigationItem.position;
      
      if ( handled )
      {
         final Bundle newConfig = getCurrentTasksListFragmentConfiguration();
         newConfig.putAll( actionBarNavigationAdapter.getTasksListConfigForItem( itemPosition ) );
         
         // We set the selected position to 0 in the case of an ID change because the back stack change (see
         // onBackStackChanged() will cause a recreation of the spinner adapter what causes the selected
         // item position to be 0 and we do not want a reload again due to changed position.
         if ( hasChangedSelectedItemId )
         {
            itemPosition = 0;
         }
         
         selectedNavigationItem = new SelectedNavigationItem( itemId,
                                                              itemPosition );
         updateNavigationAdapter = hasChangedSelectedItemId;
         
         reloadTasksListWithConfiguration( newConfig );
      }
      
      return handled;
   }
   
   
   
   protected ITasksListFragment getTasksListFragment()
   {
      final Fragment fragment = findAddedFragmentById( R.id.frag_taskslist );
      
      if ( fragment instanceof ITasksListFragment )
      {
         final ITasksListFragment tasksListFragment = (ITasksListFragment) fragment;
         return tasksListFragment;
      }
      
      return null;
   }
   
   
   
   private void initializeTasksListFragment()
   {
      if ( findAddedFragmentById( R.id.frag_taskslist ) == null )
      {
         final Fragment fragment = createTasksListFragment( getInitalTasksListFragmentConfig() );
         
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.add( R.id.frag_taskslist, fragment );
         transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
         transaction.commit();
      }
   }
   
   
   
   protected void reloadTasksListWithConfiguration( Bundle config )
   {
      final Fragment fragment = createTasksListFragment( config );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      transaction.replace( R.id.frag_taskslist, fragment );
      transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
      transaction.addToBackStack( null );
      transaction.commit();
   }
   
   
   
   private Bundle getInitalTasksListFragmentConfig()
   {
      final Intent intent = getIntent();
      final Bundle config = new Bundle( intent.getExtras() );
      
      final long listIdFromUri = getListIdFromIntentUri();
      if ( listIdFromUri != Constants.NO_ID )
      {
         config.putLong( Intents.Extras.KEY_LIST_ID, listIdFromUri );
      }
      
      return config;
   }
   
   
   
   private String getTitleFromActiveList()
   {
      if ( loadedRtmLists != null )
      {
         final long activeListId = getActiveListId();
         
         for ( TasksList tasksList : loadedRtmLists )
         {
            if ( tasksList.getId() == activeListId )
            {
               return tasksList.getName();
            }
         }
      }
      
      return null;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_taskslist };
   }
   
   
   private final static class SelectedNavigationItem implements Parcelable
   {
      @SuppressWarnings( "unused" )
      public static final Parcelable.Creator< SelectedNavigationItem > CREATOR = new Parcelable.Creator< SelectedNavigationItem >()
      {
         
         @Override
         public SelectedNavigationItem createFromParcel( Parcel source )
         {
            return new SelectedNavigationItem( source );
         }
         
         
         
         @Override
         public SelectedNavigationItem[] newArray( int size )
         {
            return new SelectedNavigationItem[ size ];
         }
         
      };
      
      public long id = Constants.NO_ID;
      
      public int position;
      
      
      
      public SelectedNavigationItem()
      {
      }
      
      
      
      public SelectedNavigationItem( long id, int position )
      {
         this.id = id;
         this.position = position;
      }
      
      
      
      public SelectedNavigationItem( Parcel source )
      {
         this( source.readLong(), source.readInt() );
      }
      
      
      
      @Override
      public void writeToParcel( Parcel dest, int flags )
      {
         dest.writeLong( id );
         dest.writeInt( position );
      }
      
      
      
      @Override
      public int describeContents()
      {
         return 0;
      }
      
      
      
      @Override
      public boolean equals( Object o )
      {
         if ( o == null )
         {
            return false;
         }
         
         if ( o == this )
         {
            return true;
         }
         
         if ( o.getClass() != getClass() )
         {
            return false;
         }
         
         final SelectedNavigationItem other = (SelectedNavigationItem) o;
         return id == other.id && position == other.position;
      }
      
      
      
      @Override
      public String toString()
      {
         return format( id, position );
      }
      
      
      
      public static String format( long id, int position )
      {
         return String.format( "%d;%d", id, position );
      }
   }
   
   
   
   public abstract Fragment createTasksListFragment( Bundle config );
}
