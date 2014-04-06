/*
 * Copyright (c) 2012 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.app.taskslist.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.home.NavigationHandlerBase;
import dev.drsoran.moloko.app.loaders.TasksListLoader;
import dev.drsoran.moloko.app.loaders.TasksListsLoader;
import dev.drsoran.moloko.app.taskslist.common.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.TasksListContentOptions;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


abstract class AbstractTasksListNavigationHandler extends NavigationHandlerBase
         implements OnNavigationListener, LoaderCallbacks< TasksList >
{
   @InstanceState( key = "sel_nav_item",
                   defaultValue = InstanceState.NO_DEFAULT )
   private SelectedNavigationItem selectedNavigationItem;
   
   @InstanceState( key = Intents.Extras.KEY_LIST,
                   defaultValue = InstanceState.NULL )
   private TasksList tasksList;
   
   private TasksListNavigationAdapter actionBarNavigationAdapter;
   
   
   
   protected AbstractTasksListNavigationHandler( MolokoActivity activity,
      int fragmentId )
   {
      super( activity, fragmentId );
      activity.registerAnnotatedConfiguredInstance( this,
                                                    AbstractTasksListNavigationHandler.class );
   }
   
   
   
   @Override
   public final void handleIntent( Intent intent )
   {
      final Fragment fragment = createTasksListFragment( intent.getExtras() );
      getActivity().showFragment( getFragmentId(), fragment );
      
      initializeActionBar();
   }
   
   
   
   @Override
   public void setActivityTitle()
   {
      setTitle();
      setSubTitle();
   }
   
   
   
   private void setTitle()
   {
      String title = getTitleFromIntentExtras();
      if ( !TextUtils.isEmpty( title ) )
      {
         getActivity().setTitle( title );
         return;
      }
      
      title = getListNameFromTasksList();
      if ( !TextUtils.isEmpty( title ) )
      {
         getActivity().setTitle( title );
         return;
      }
      
      title = getListNameFromIntentExtras();
      if ( !TextUtils.isEmpty( title ) )
      {
         getActivity().setTitle( title );
         return;
      }
   }
   
   
   
   private void setSubTitle()
   {
      final String subTitle = getSubTitleFromIntentExtras();
      getActivity().getActionBar().setSubtitle( subTitle );
   }
   
   
   
   private void initializeActionBar()
   {
      if ( tasksList == null )
      {
         setStandardNavigationMode();
         
         if ( canLoadTasksList() )
         {
            startLoadingTasksList();
         }
      }
      else
      {
         setListNavigationMode();
      }
   }
   
   
   
   private String getTitleFromIntentExtras()
   {
      return getStringFromIntentExtras( Intents.Extras.KEY_ACTIVITY_TITLE );
   }
   
   
   
   private String getSubTitleFromIntentExtras()
   {
      return getStringFromIntentExtras( Intents.Extras.KEY_ACTIVITY_SUB_TITLE );
   }
   
   
   
   private String getStringFromIntentExtras( String key )
   {
      final Bundle extras = getActivity().getIntent().getExtras();
      if ( extras != null )
      {
         return extras.getString( key );
      }
      
      return null;
   }
   
   
   
   private String getListNameFromTasksList()
   {
      if ( tasksList != null )
      {
         return tasksList.getName();
      }
      
      return null;
   }
   
   
   
   private String getListNameFromIntentExtras()
   {
      final Bundle extras = getActivity().getIntent().getExtras();
      if ( extras != null )
      {
         return extras.getString( Intents.Extras.KEY_LIST_NAME );
      }
      
      return null;
   }
   
   
   
   private long getListIdFromIntent()
   {
      long listId = getListIdFromIntentExtras();
      if ( listId == Constants.NO_ID )
      {
         listId = getListIdFromIntentUri();
      }
      
      return listId;
   }
   
   
   
   private long getListIdFromIntentExtras()
   {
      final Bundle extras = getActivity().getIntent().getExtras();
      if ( extras != null )
      {
         return extras.getLong( Intents.Extras.KEY_LIST_ID, Constants.NO_ID );
      }
      
      return Constants.NO_ID;
   }
   
   
   
   private long getListIdFromIntentUri()
   {
      final Uri intentUri = getActivity().getIntent().getData();
      if ( intentUri != null
         && ContentUris.MATCHER.match( intentUri ) == ContentUris.MATCH_TASKS_LISTS_ID )
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
   
   
   
   private void setStandardNavigationMode()
   {
      final ActionBar actionBar = getActivity().getActionBar();
      actionBar.setDisplayShowTitleEnabled( true );
      
      if ( isListNavigationMode() )
      {
         selectedNavigationItem = null;
         actionBarNavigationAdapter = null;
      }
      
      actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_STANDARD );
      actionBar.setListNavigationCallbacks( null, null );
   }
   
   
   
   private void setListNavigationMode()
   {
      initializeListNavigation();
      
      final ActionBar actionBar = getActivity().getActionBar();
      
      actionBar.setDisplayShowTitleEnabled( false );
      actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
      actionBar.setSelectedNavigationItem( selectedNavigationItem.position );
   }
   
   
   
   private void initializeListNavigation()
   {
      final boolean isFirstInitialization = selectedNavigationItem == null;
      if ( isFirstInitialization )
      {
         selectedNavigationItem = new SelectedNavigationItem();
         setSelectedNavigationItemIdFromIntent();
         setSelectedNavigationItemPositionFromSubTitle();
      }
      
      setNavigationAdapter();
   }
   
   
   
   private void setSelectedNavigationItemIdFromIntent()
   {
      final long listIdFromIntent = getListIdFromIntent();
      selectedNavigationItem.id = listIdFromIntent;
   }
   
   
   
   private void setSelectedNavigationItemPositionFromSubTitle()
   {
      final String subTitle = getSubTitleFromIntentExtras();
      
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
   
   
   
   private String getSubTitleFromSelectedNavigationItemPosition()
   {
      switch ( selectedNavigationItem.position )
      {
         case TasksListNavigationAdapter.ITEM_POSITION_COMPLETED_TASKS:
            return getCompletedSubtitle();
            
         case TasksListNavigationAdapter.ITEM_POSITION_OVERDUE_TASKS:
            return getOverdueSubtitle();
            
         case TasksListNavigationAdapter.ITEM_POSITION_TODAY_TASKS:
            return getDueTodaySubtitle();
            
         case TasksListNavigationAdapter.ITEM_POSITION_TOMORROW_TASKS:
            return getDueTomorrowSubtitle();
            
         default :
            return null;
      }
   }
   
   
   
   private boolean isListNavigationMode()
   {
      return getActivity().getActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST;
   }
   
   
   
   private boolean canLoadTasksList()
   {
      return getListIdFromIntent() != Constants.NO_ID;
   }
   
   
   
   private void startLoadingTasksList()
   {
      getActivity().getLoaderManager().initLoader( TasksListsLoader.ID,
                                                   Bundle.EMPTY,
                                                   this );
   }
   
   
   
   @Override
   public Loader< TasksList > onCreateLoader( int id, Bundle args )
   {
      final TasksListLoader loader = new TasksListLoader( getActivity().getAppContext()
                                                                       .asDomainContext(),
                                                          getListIdFromIntent(),
                                                          TasksListContentOptions.WithTaskCount );
      loader.setRespectContentChanges( false );
      
      return loader;
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< TasksList > loader, TasksList data )
   {
      tasksList = data;
      
      if ( tasksList != null )
      {
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
      getActivity().getActionBar()
                   .setListNavigationCallbacks( actionBarNavigationAdapter,
                                                this );
   }
   
   
   
   private void createActionBarNavigationAdapter()
   {
      final List< IItem > navigationItems = createActionBarNavigationItems();
      actionBarNavigationAdapter = new TasksListNavigationAdapter( getActivity().getActionBar()
                                                                                .getThemedContext(),
                                                                   navigationItems );
   }
   
   
   
   private List< IItem > createActionBarNavigationItems()
   {
      final List< IItem > actionBarNavigationItems = new ArrayList< IItem >();
      
      final ExtendedTaskCount extendedListInfo = tasksList.getTasksCount();
      
      Bundle config = Intents.Extras.createOpenListExtras( tasksList.getId() );
      actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_DEFAULT_TASKS,
                                    new TasksListNavigationAdapter.RtmListItem( tasksList ) );
      
      config = Intents.Extras.createOpenListExtras( tasksList,
                                                    new RtmSmartFilterBuilder().statusCompleted()
                                                                               .toString() );
      actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_COMPLETED_TASKS,
                                    new TasksListNavigationAdapter.ExtendedRtmListItem( getCompletedSubtitle(),
                                                                                        tasksList,
                                                                                        extendedListInfo.getCompletedTaskCount() ).setTasksListConfig( config ) );
      
      config = Intents.Extras.createOpenListExtras( tasksList,
                                                    new RtmSmartFilterBuilder().dueBefore()
                                                                               .today()
                                                                               .toString() );
      actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_OVERDUE_TASKS,
                                    new TasksListNavigationAdapter.ExtendedRtmListItem( getOverdueSubtitle(),
                                                                                        tasksList,
                                                                                        extendedListInfo.getOverDueTaskCount() ).setTasksListConfig( config ) );
      
      config = Intents.Extras.createOpenListExtras( tasksList,
                                                    new RtmSmartFilterBuilder().due()
                                                                               .today()
                                                                               .toString() );
      actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_TODAY_TASKS,
                                    new TasksListNavigationAdapter.ExtendedRtmListItem( getDueTodaySubtitle(),
                                                                                        tasksList,
                                                                                        extendedListInfo.getDueTodayTaskCount() ).setTasksListConfig( config ) );
      
      config = Intents.Extras.createOpenListExtras( tasksList,
                                                    new RtmSmartFilterBuilder().due()
                                                                               .tomorrow()
                                                                               .toString() );
      actionBarNavigationItems.add( TasksListNavigationAdapter.ITEM_POSITION_TOMORROW_TASKS,
                                    new TasksListNavigationAdapter.ExtendedRtmListItem( getDueTomorrowSubtitle(),
                                                                                        tasksList,
                                                                                        extendedListInfo.getDueTomorrowTaskCount() ).setTasksListConfig( config ) );
      
      return actionBarNavigationItems;
   }
   
   
   
   private String getCompletedSubtitle()
   {
      return getActivity().getString( R.string.taskslist_actionbar_subtitle_completed );
   }
   
   
   
   private String getOverdueSubtitle()
   {
      return getActivity().getString( R.string.taskslist_actionbar_subtitle_overdue );
   }
   
   
   
   private String getDueTodaySubtitle()
   {
      return getActivity().getString( R.string.taskslist_actionbar_subtitle_due_today );
   }
   
   
   
   private String getDueTomorrowSubtitle()
   {
      return getActivity().getString( R.string.taskslist_actionbar_subtitle_due_tomorrow );
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< TasksList > loader )
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
         final Intent intent = Intents.createOpenListIntent( tasksList, null );
         intent.getExtras()
               .putString( Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                           getSubTitleFromSelectedNavigationItemPosition() );
         
         getActivity().startActivity( intent );
      }
      
      return handled;
   }
   
   
   
   protected abstract Fragment createTasksListFragment( Bundle extras );
   
   
   
   @Deprecated
   protected ITasksListFragment getTasksListFragment()
   {
      final Fragment fragment = getActivity().findAddedFragmentById( R.id.frag_taskslist );
      
      if ( fragment instanceof ITasksListFragment )
      {
         final ITasksListFragment tasksListFragment = (ITasksListFragment) fragment;
         return tasksListFragment;
      }
      
      return null;
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
         return String.format( Locale.ENGLISH, "%d;%d", id, position );
      }
   }
}
