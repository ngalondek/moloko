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

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.TasksListNavigationAdapter;
import dev.drsoran.moloko.adapters.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.ITasksListFragment;
import dev.drsoran.moloko.fragments.listeners.ITasksListFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.loaders.RtmListWithTaskCountLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmListWithTaskCount.ExtendedListInfo;
import dev.drsoran.rtm.Task;


public abstract class AbstractTasksListActivity extends
         MolokoEditFragmentActivity implements ITasksListFragmentListener,
         OnNavigationListener, OnBackStackChangedListener,
         LoaderCallbacks< List< RtmListWithTaskCount > >
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
   
   private List< RtmListWithTaskCount > loadedRtmLists;
   
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
      setTitle( title );
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
      if ( TextUtils.isEmpty( getTitle() ) )
      {
         final String intentListName = getListNameFromIntent();
         if ( !TextUtils.isEmpty( intentListName ) )
         {
            setTitle( intentListName );
         }
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
      
      if ( isListNavigationMode() )
      {
         getSupportFragmentManager().removeOnBackStackChangedListener( this );
         selectedNavigationItem = null;
         backStackNavigationItems = null;
         actionBarNavigationAdapter = null;
      }
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
      
      initializeListNavigation();
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
      
      getSupportActionBar().setSelectedNavigationItem( selectedNavigationItem.position );
   }
   
   
   
   private void setSelectedNavigationItemIdFromIntent()
   {
      final String listIdFromIntent = getListIdFromIntent();
      selectedNavigationItem.id = !TextUtils.isEmpty( listIdFromIntent )
                                                                        ? Long.valueOf( listIdFromIntent )
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
      for ( Iterator< RtmListWithTaskCount > i = loadedRtmLists.iterator(); i.hasNext(); )
      {
         final RtmListWithTaskCount list = i.next();
         
         if ( Long.valueOf( list.getId() ) != selectedNavigationItem.id )
         {
            final IItem newListItem = new TasksListNavigationAdapter.RtmListItem( context,
                                                                                  list );
            actionBarNavigationItems.add( newListItem );
         }
         else
         {
            final ExtendedListInfo extendedListInfo = list.getExtendedListInfo( this );
            
            Bundle config = Intents.Extras.createOpenListExtras( context,
                                                                 list,
                                                                 null );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_DEFAULT_TASKS,
                                          new TasksListNavigationAdapter.RtmListItem( context,
                                                                                      list ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          RtmSmartFilterLexer.OP_STATUS_LIT
                                                             + RtmSmartFilterLexer.COMPLETED_LIT );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_COMPLETED_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getCompletedSubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.completedTaskCount ).setTasksListConfig( config ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                             + DateParser.tokenNames[ DateParser.TODAY ] );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_OVERDUE_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getOverdueSubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.overDueTaskCount ).setTasksListConfig( config ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          RtmSmartFilterLexer.OP_DUE_LIT
                                                             + DateParser.tokenNames[ DateParser.TODAY ] );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_TODAY_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getDueTodaySubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.dueTodayTaskCount ).setTasksListConfig( config ) );
            
            config = Intents.Extras.createOpenListExtras( context,
                                                          list,
                                                          RtmSmartFilterLexer.OP_DUE_LIT
                                                             + DateParser.tokenNames[ DateParser.TOMORROW ] );
            actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_TOMORROW_TASKS,
                                          new TasksListNavigationAdapter.ExtendedRtmListItem( context,
                                                                                              getDueTomorrowSubtitle(),
                                                                                              list,
                                                                                              extendedListInfo.dueTomorrowTaskCount ).setTasksListConfig( config ) );
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
   
   
   
   @Override
   public Loader< List< RtmListWithTaskCount >> onCreateLoader( int id,
                                                                Bundle args )
   {
      final RtmListWithTaskCountLoader loader = new RtmListWithTaskCountLoader( this );
      loader.setPreExpandExtendedListInfoAfterLoad( true );
      
      return loader;
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< RtmListWithTaskCount >> loader,
                               List< RtmListWithTaskCount > data )
   {
      loadedRtmLists = data;
      
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
   public void onLoaderReset( Loader< List< RtmListWithTaskCount >> loader )
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
         // onBackStackChanged() will cause a recreation of the spinner adapter that causes the selected
         // item position to be and we do not want a reload again due to changed position.
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
   
   
   
   private ITasksListFragment< ? extends Task > getTasksListFragment()
   {
      final Fragment fragment = findAddedFragmentById( R.id.frag_taskslist );
      
      if ( fragment instanceof ITasksListFragment )
      {
         @SuppressWarnings( "unchecked" )
         final ITasksListFragment< ? extends Task > tasksListFragment = (ITasksListFragment< ? extends Task >) fragment;
         return tasksListFragment;
      }
      else
      {
         return null;
      }
   }
   
   
   
   private void initializeTasksListFragment()
   {
      if ( findAddedFragmentById( R.id.frag_taskslist ) == null )
      {
         final Fragment fragment = createTasksListFragment( getIntent().getExtras() );
         
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
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_taskslist };
   }
   
   
   
   protected abstract Fragment createTasksListFragment( Bundle config );
   
   
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
      
      public long id = -1L;
      
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
}
