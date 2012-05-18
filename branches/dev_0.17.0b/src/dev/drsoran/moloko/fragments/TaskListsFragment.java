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

package dev.drsoran.moloko.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.adapters.TaskListsAdapter;
import dev.drsoran.moloko.adapters.TaskListsAdapter.IOnGroupIndicatorClickedListener;
import dev.drsoran.moloko.fragments.base.MolokoExpandableListFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskListsFragmentListener;
import dev.drsoran.moloko.loaders.RtmListWithTaskCountLoader;
import dev.drsoran.moloko.loaders.RtmListsLoader;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class TaskListsFragment extends
         MolokoExpandableListFragment< RtmListWithTaskCount > implements
         IOnGroupIndicatorClickedListener
{
   private static class CtxtMenu
   {
      public final static int OPEN_LIST = R.id.ctx_menu_open_list;
      
      public final static int EXPAND = R.id.ctx_menu_expand;
      
      public final static int COLLAPSE = R.id.ctx_menu_collapse;
      
      public final static int DELETE = R.id.ctx_menu_delete_list;
      
      public final static int RENAME = R.id.ctx_menu_rename_list;
      
      public final static int MAKE_DEFAULT_LIST = R.id.ctx_menu_set_default_list;
      
      public final static int REMOVE_DEFAULT_LIST = R.id.ctx_menu_unset_default_list;
   }
   
   private ITaskListsFragmentListener listener;
   
   
   
   public final static TaskListsFragment newInstance( Bundle config )
   {
      final TaskListsFragment fragment = new TaskListsFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
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
   public void onDetach()
   {
      listener = null;
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
      final RtmListWithTaskCount list = getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
      
      if ( getExpandableListView().isGroupExpanded( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ) )
      {
         // show collapse before open
         menu.add( Menu.NONE,
                   CtxtMenu.COLLAPSE,
                   Menu.NONE,
                   getString( R.string.tasklists_menu_ctx_collapse,
                              list.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.OPEN_LIST,
                   Menu.NONE,
                   getString( R.string.phr_open_with_name, list.getName() ) );
      }
      else
      {
         menu.add( Menu.NONE,
                   CtxtMenu.OPEN_LIST,
                   Menu.NONE,
                   getString( R.string.phr_open_with_name, list.getName() ) );
         
         menu.add( Menu.NONE,
                   CtxtMenu.EXPAND,
                   Menu.NONE,
                   getString( R.string.tasklists_menu_ctx_expand,
                              list.getName() ) );
      }
      
      if ( list.getLocked() == 0
         && AccountUtils.isWriteableAccess( getSherlockActivity() ) )
      {
         menu.add( Menu.NONE,
                   CtxtMenu.DELETE,
                   Menu.NONE,
                   getString( R.string.phr_delete_with_name, list.getName() ) );
         
         menu.add( Menu.NONE,
                   CtxtMenu.RENAME,
                   Menu.NONE,
                   getString( R.string.tasklists_menu_ctx_rename_list,
                              list.getName() ) );
      }
      
      if ( list.getId().equals( MolokoApp.getSettings( getSherlockActivity() )
                                         .getDefaultListId() ) )
         menu.add( Menu.NONE,
                   CtxtMenu.REMOVE_DEFAULT_LIST,
                   Menu.NONE,
                   getString( R.string.tasklists_menu_ctx_remove_def_list ) );
      else
         menu.add( Menu.NONE,
                   CtxtMenu.MAKE_DEFAULT_LIST,
                   Menu.NONE,
                   getString( R.string.tasklists_menu_ctx_make_def_list ) );
   }
   
   
   
   @Override
   public boolean onContextItemSelected( android.view.MenuItem item )
   {
      final ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.OPEN_LIST:
            if ( listener != null )
               listener.openList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.EXPAND:
            getExpandableListView().expandGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.COLLAPSE:
            getExpandableListView().collapseGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.DELETE:
            if ( listener != null )
               listener.deleteList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.RENAME:
            if ( listener != null )
               listener.renameList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.MAKE_DEFAULT_LIST:
            setAsDefaultList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.REMOVE_DEFAULT_LIST:
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
         listener.openList( groupPosition );
      
      return true;
   }
   
   
   
   @Override
   public void onGroupIndicatorClicked( View groupView )
   {
      final ExpandableListView listView = getExpandableListView();
      final int pos = ExpandableListView.getPackedPositionGroup( listView.getExpandableListPosition( listView.getPositionForView( groupView ) ) );
      
      if ( listView.isGroupExpanded( pos ) )
         listView.collapseGroup( pos );
      else
         listView.expandGroup( pos );
   }
   
   
   
   @Override
   public boolean onChildClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id )
   {
      final Intent intent = ( getExpandableListAdapter() ).getChildIntent( groupPosition,
                                                                           childPosition );
      
      if ( intent != null )
      {
         if ( listener != null )
            listener.openChild( intent );
         return true;
      }
      else
      {
         return super.onChildClick( parent, v, groupPosition, childPosition, id );
      }
   }
   
   
   
   @Override
   public int getSettingsMask()
   {
      return super.getSettingsMask()
         | IOnSettingsChangedListener.RTM_DEFAULTLIST;
   }
   
   
   
   @Override
   public ExpandableListAdapter createExpandableListAdapterForResult( List< RtmListWithTaskCount > result )
   {
      final TaskListsAdapter taskListsAdapter = new TaskListsAdapter( getSherlockActivity(),
                                                                      R.layout.tasklists_fragment_group,
                                                                      R.layout.tasklists_fragment_child,
                                                                      result );
      taskListsAdapter.setOnGroupIndicatorClickedListener( this );
      
      return taskListsAdapter;
   }
   
   
   
   @Override
   public Loader< List< RtmListWithTaskCount >> newLoaderInstance( int id,
                                                                   Bundle config )
   {
      return new RtmListWithTaskCountLoader( getSherlockActivity() );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_tasklists );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return RtmListsLoader.ID;
   }
   
   
   
   @Override
   public TaskListsAdapter getExpandableListAdapter()
   {
      return (TaskListsAdapter) super.getExpandableListAdapter();
   }
   
   
   
   public RtmListWithTaskCount getRtmList( int flatPos )
   {
      return (RtmListWithTaskCount) getExpandableListAdapter().getGroup( flatPos );
   }
   
   
   
   private void setAsDefaultList( int pos )
   {
      MolokoApp.getSettings( getSherlockActivity() )
               .setDefaultListId( getRtmList( pos ).getId() );
   }
   
   
   
   private void resetDefaultList()
   {
      MolokoApp.getSettings( getSherlockActivity() )
               .setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
   }
   
}
