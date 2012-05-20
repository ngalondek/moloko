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

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.BaseSelectableActionModeCallback;
import dev.drsoran.moloko.actionmodes.TasksListActionModeCallback;
import dev.drsoran.moloko.adapters.FullDetailedTasksListFragmentAdapter;
import dev.drsoran.moloko.adapters.ISelectableAdapter;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.loaders.TasksLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class FullDetailedTasksListFragment extends
         AbstractTasksListFragment< Task > implements View.OnClickListener
{
   
   private final static class CtxtMenu
   {
      public final static int OPEN_LIST = R.id.ctx_menu_open_list;
      
      public final static int TAG = R.id.ctx_menu_open_tag;
      
      public final static int TAGS = R.id.ctx_menu_open_tags;
      
      public final static int TASKS_AT_LOCATION = R.id.ctx_menu_open_tasks_at_loc;
   }
   
   private IFullDetailedTasksListFragmentListener listener;
   
   
   
   public static FullDetailedTasksListFragment newInstance( Bundle configuration )
   {
      final FullDetailedTasksListFragment fragment = new FullDetailedTasksListFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IFullDetailedTasksListFragmentListener )
         listener = (IFullDetailedTasksListFragmentListener) activity;
      else
         listener = new NullTasksListFragmentListener();
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
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   
   
   
   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      final Task task = getTask( info.position );
      final RtmSmartFilter filter = getRtmSmartFilter();
      
      // If the list name was in the filter then we are in one list only. So no need to
      // open it again.
      if ( filter == null
         || !RtmSmartFilterParsing.hasOperatorAndValue( filter.getTokens(),
                                                        RtmSmartFilterLexer.OP_LIST,
                                                        task.getListName(),
                                                        false ) )
      {
         menu.add( Menu.NONE,
                   CtxtMenu.OPEN_LIST,
                   Menu.NONE,
                   getString( R.string.abstaskslist_listitem_ctx_open_list,
                              task.getListName() ) );
      }
      
      final int tagsCount = task.getTags().size();
      if ( tagsCount > 0 )
      {
         final View tagsContainer = info.targetView.findViewById( R.id.taskslist_listitem_additionals_container );
         
         if ( tagsContainer.getVisibility() == View.VISIBLE )
            menu.add( Menu.NONE,
                      tagsCount == 1 ? CtxtMenu.TAG : CtxtMenu.TAGS,
                      Menu.NONE,
                      getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                        tagsCount,
                                                        task.getTags().get( 0 ) ) );
      }
      
      final String locationName = task.getLocationName();
      if ( !TextUtils.isEmpty( locationName ) )
      {
         final View locationView = info.targetView.findViewById( R.id.taskslist_listitem_location );
         
         if ( locationView.getVisibility() == View.VISIBLE )
         {
            menu.add( Menu.NONE,
                      CtxtMenu.TASKS_AT_LOCATION,
                      Menu.NONE,
                      getString( R.string.abstaskslist_listitem_ctx_tasks_at_location,
                                 locationName ) );
         }
      }
   }
   
   
   
   @Override
   public boolean onContextItemSelected( android.view.MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.OPEN_LIST:
            listener.onOpenList( info.position,
                                 getTask( info.position ).getListId() );
            return true;
            
         case CtxtMenu.TAG:
         case CtxtMenu.TAGS:
            listener.onShowTasksWithTags( getTask( info.position ).getTags() );
            return true;
            
         case CtxtMenu.TASKS_AT_LOCATION:
            listener.onOpenLocation( info.position,
                                     getTask( info.position ).getLocationId() );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onClick( View view )
   {
      switch ( view.getId() )
      {
         case R.id.tags_layout_btn_tag:
            final String tag = ( (TextView) view ).getText().toString();
            listener.onShowTasksWithTags( Collections.singletonList( tag ) );
            break;
         
         case R.id.taskslist_listitem_btn_list_name:
            listener.onOpenList( getTaskPos( view ),
                                 getTask( view ).getListId() );
            break;
         
         case R.id.taskslist_listitem_location:
            listener.onOpenLocation( getTaskPos( view ),
                                     getTask( view ).getLocationId() );
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   protected int getDefaultTaskSort()
   {
      return MolokoApp.getSettings( getSherlockActivity() ).getTaskSort();
   }
   
   
   
   @Override
   public Loader< List< Task >> newLoaderInstance( int id, Bundle config )
   {
      final IFilter filter = config.getParcelable( Intents.Extras.KEY_FILTER );
      final String selection = filter != null ? filter.getSqlSelection() : null;
      final String order = resolveTaskSortToSqlite( config.getInt( Intents.Extras.KEY_TASK_SORT_ORDER ) );
      
      final TasksLoader loader = new TasksLoader( getSherlockActivity(),
                                                  selection,
                                                  order );
      loader.setUpdateThrottle( DEFAULT_LOADER_THROTTLE_MS );
      
      return loader;
   }
   
   
   
   @Override
   public ListAdapter createListAdapterForResult( List< Task > result,
                                                  IFilter filter )
   {
      final int flags = 0;
      return new FullDetailedTasksListFragmentAdapter( getSherlockActivity(),
                                                       result,
                                                       filter,
                                                       flags,
                                                       this );
   }
   
   
   
   @Override
   public FullDetailedTasksListFragmentAdapter getListAdapter()
   {
      return (FullDetailedTasksListFragmentAdapter) super.getListAdapter();
   }
   
   
   
   @Override
   protected BaseSelectableActionModeCallback< Task > createActionModeCallback()
   {
      final TasksListActionModeCallback callback = new TasksListActionModeCallback( getSherlockActivity(),
                                                                                    getListAdapter() );
      callback.setTasksListActionModeListener( listener );
      return callback;
   }
   
   
   
   @Override
   protected void configureListAdapterForSelectionMode()
   {
      super.configureListAdapterForSelectionMode();
      getListAdapter().setSelectable( true );
   }
   
   
   
   @Override
   protected void onSelectionModeStopped( ActionMode mode,
                                          BaseSelectableActionModeCallback< Task > callback )
   {
      super.onSelectionModeStopped( mode, callback );
      getListAdapter().setSelectable( false );
   }
   
   
   
   @Override
   protected ISelectableAdapter< Task > getSelectableListAdapter()
   {
      return getListAdapter();
   }
}
