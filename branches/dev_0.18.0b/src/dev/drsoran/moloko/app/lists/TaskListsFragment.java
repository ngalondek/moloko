/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.lists;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.content.loaders.TasksListsLoader;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.lists.TaskListsAdapter.IOnGroupIndicatorClickedListener;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.ui.fragments.MolokoExpandableListFragment;


class TaskListsFragment extends MolokoExpandableListFragment< TasksList >
         implements IOnGroupIndicatorClickedListener,
         IOnSettingsChangedListener
{
   private ITaskListsFragmentListener listener;
   
   private AppContext appContext;
   
   
   
   public final static TaskListsFragment newInstance( Bundle config )
   {
      final TaskListsFragment fragment = new TaskListsFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public TaskListsFragment()
   {
      setNoElementsResourceId( R.string.tasklists_no_lists );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      appContext = AppContext.get( activity );
      
      if ( activity instanceof ITaskListsFragmentListener )
         listener = (ITaskListsFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      registerForContextMenu( getExpandableListView() );
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      appContext.getAppEvents()
                .registerOnSettingsChangedListener( IOnSettingsChangedListener.RTM_DEFAULTLIST,
                                                    this );
   }
   
   
   
   @Override
   public void onStop()
   {
      appContext.getAppEvents().unregisterOnSettingsChangedListener( this );
      super.onStop();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      appContext = null;
      
      super.onDetach();
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.tasklists_fragment,
                                                  container,
                                                  false );
      return fragmentView;
   }
   
   
   
   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
      final TasksList list = getList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
      final String listName = list.getName();
      
      if ( hasWritableAccess() && !list.isLocked() )
      {
         getSherlockActivity().getMenuInflater()
                              .inflate( R.menu.tasklists_group_context_rwd,
                                        menu );
         
         menu.findItem( R.id.ctx_menu_delete_list )
             .setTitle( getString( R.string.phr_delete_with_name, listName ) );
         
         menu.findItem( R.id.ctx_menu_rename_list )
             .setTitle( getString( R.string.tasklists_menu_ctx_rename_list,
                                   listName ) );
      }
      else
      {
         getSherlockActivity().getMenuInflater()
                              .inflate( R.menu.tasklists_group_context, menu );
      }
      
      if ( getExpandableListView().isGroupExpanded( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ) )
      {
         menu.findItem( R.id.ctx_menu_collapse )
             .setVisible( true )
             .setTitle( getString( R.string.tasklists_menu_ctx_collapse,
                                   listName ) );
         
         menu.findItem( R.id.ctx_menu_expand ).setVisible( false );
      }
      else
      {
         menu.findItem( R.id.ctx_menu_expand )
             .setVisible( true )
             .setTitle( getString( R.string.tasklists_menu_ctx_expand, listName ) );
         
         menu.findItem( R.id.ctx_menu_collapse ).setVisible( false );
      }
      
      final boolean isDefaultList = list.getId() == appContext.getSettings()
                                                              .getDefaultListId();
      menu.findItem( R.id.ctx_menu_set_default_list )
          .setVisible( !isDefaultList );
      menu.findItem( R.id.ctx_menu_unset_default_list )
          .setVisible( isDefaultList );
   }
   
   
   
   @Override
   public boolean onContextItemSelected( android.view.MenuItem item )
   {
      final ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case R.id.ctx_menu_expand:
            getExpandableListView().expandGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case R.id.ctx_menu_collapse:
            getExpandableListView().collapseGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case R.id.ctx_menu_delete_list:
            if ( listener != null )
               listener.deleteList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case R.id.ctx_menu_rename_list:
            if ( listener != null )
               listener.renameList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case R.id.ctx_menu_set_default_list:
            setAsDefaultList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case R.id.ctx_menu_unset_default_list:
            resetDefaultList();
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   
   
   
   @Override
   public boolean onGroupClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                long id )
   {
      if ( listener != null )
      {
         listener.openList( groupPosition );
      }
      
      return true;
   }
   
   
   
   @Override
   public void onGroupIndicatorClicked( View groupView )
   {
      final ExpandableListView listView = getExpandableListView();
      final int pos = ExpandableListView.getPackedPositionGroup( listView.getExpandableListPosition( listView.getPositionForView( groupView ) ) );
      
      if ( listView.isGroupExpanded( pos ) )
      {
         listView.collapseGroup( pos );
      }
      else
      {
         listView.expandGroup( pos );
      }
   }
   
   
   
   @Override
   public boolean onChildClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id )
   {
      final Intent intent = getExpandableListAdapter().getChildIntent( groupPosition,
                                                                       childPosition );
      
      if ( intent != null )
      {
         if ( listener != null )
         {
            listener.openChild( intent );
         }
         
         return true;
      }
      else
      {
         return super.onChildClick( parent, v, groupPosition, childPosition, id );
      }
   }
   
   
   
   @Override
   public ExpandableListAdapter createExpandableListAdapterForResult( List< TasksList > result )
   {
      final TaskListsAdapter taskListsAdapter = new TaskListsAdapter( appContext,
                                                                      R.layout.tasklists_fragment_group,
                                                                      R.layout.tasklists_fragment_child,
                                                                      result );
      taskListsAdapter.setOnGroupIndicatorClickedListener( this );
      
      return taskListsAdapter;
   }
   
   
   
   @Override
   public Loader< List< TasksList >> newLoaderInstance( int id, Bundle config )
   {
      return new TasksListsLoader( appContext.asDomainContext(), true );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_tasklists );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TasksListsLoader.ID;
   }
   
   
   
   @Override
   public TaskListsAdapter getExpandableListAdapter()
   {
      return (TaskListsAdapter) super.getExpandableListAdapter();
   }
   
   
   
   public TasksList getList( int flatPos )
   {
      return (TasksList) getExpandableListAdapter().getGroup( flatPos );
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( which == IOnSettingsChangedListener.RTM_DEFAULTLIST )
      {
         getExpandableListAdapter().notifyDataSetChanged();
      }
   }
   
   
   
   private void setAsDefaultList( int pos )
   {
      appContext.getSettings().setDefaultListId( getList( pos ).getId() );
   }
   
   
   
   private void resetDefaultList()
   {
      appContext.getSettings().setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
   }
}
