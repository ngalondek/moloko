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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.IOnSelectionChangesListener;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.NotesListActionModeCallback;
import dev.drsoran.moloko.actionmodes.listener.INotesListActionModeListener;
import dev.drsoran.moloko.adapters.NotesListFragmentAdapter;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoListFragment;
import dev.drsoran.moloko.fragments.listeners.INotesListsFragmentListener;
import dev.drsoran.moloko.loaders.RtmTaskNotesLoader;


public class NotesListFragment extends MolokoListFragment< List< RtmTaskNote > >
         implements OnItemLongClickListener,
         IOnSelectionChangesListener< RtmTaskNote >,
         INotesListActionModeListener
{
   
   public static class Config
   {
      public final static String TASK_ID = "task_id";
      
      private final static String IS_SELECTION_MODE = "is_selection_mode";
      
      private final static String SELECTED_ITEMS = "selected_items";
   }
   
   
   
   public final static NotesListFragment newInstance( Bundle config )
   {
      final NotesListFragment fragment = new NotesListFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   @InstanceState( key = Config.TASK_ID )
   private String taskId;
   
   @InstanceState( key = Config.IS_SELECTION_MODE,
                   defaultValue = InstanceState.NO_DEFAULT )
   private boolean isSelectionMode;
   
   @InstanceState( key = Config.SELECTED_ITEMS,
                   defaultValue = InstanceState.NEW )
   private ArrayList< RtmTaskNote > selectedNotes;
   
   private ActionMode activeActionMode;
   
   private NotesListActionModeCallback actionModeCallback;
   
   private INotesListsFragmentListener listener;
   
   
   
   public NotesListFragment()
   {
      registerAnnotatedConfiguredInstance( this, NotesListFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof INotesListsFragmentListener )
         listener = (INotesListsFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setHasOptionsMenu( true );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.noteslist_fragment,
                                                  container,
                                                  false );
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      getListView().setOnItemLongClickListener( this );
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      super.onCreateOptionsMenu( menu, inflater );
      
      if ( getListAdapter() != null && isWritableAccess() )
      {
         if ( !isSelectionMode )
         {
            inflater.inflate( R.menu.noteslist_fragment_rwd, menu );
         }
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_add_note:
            if ( listener != null )
            {
               listener.onAddNote();
            }
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      super.onListItemClick( l, v, position, id );
      
      if ( !isSelectionMode && listener != null )
      {
         listener.onOpenNote( getListAdapter().getItem( position ), position );
      }
   }
   
   
   
   @Override
   public boolean onItemLongClick( AdapterView< ? > parent,
                                   View view,
                                   int position,
                                   long id )
   {
      final RtmTaskNote note = getListAdapter().getItem( position );
      final List< RtmTaskNote > selectedNotes = Collections.singletonList( note );
      
      startSelectionMode( selectedNotes );
      
      return true;
   }
   
   
   
   @Override
   public void onSelectionChanged( Collection< ? extends RtmTaskNote > notes,
                                   boolean isSelected )
   {
      updateSelectedNotes();
      
      if ( activeActionMode != null )
      {
         activeActionMode.invalidate();
      }
   }
   
   
   
   @Override
   public void onDeleteNotes( Collection< RtmTaskNote > notes )
   {
      if ( listener != null )
      {
         listener.onDeleteNotes( getListAdapter().getSelectedItems() );
      }
      
      deactivateSelectionMode();
   }
   
   
   
   public void startSelectionMode( List< RtmTaskNote > preselectedNotes )
   {
      if ( !isSelectionMode && isWritableAccess() )
      {
         activateSelectionMode( preselectedNotes );
      }
   }
   
   
   
   public void stopSelectionMode()
   {
      if ( isSelectionMode )
      {
         deactivateSelectionMode();
      }
   }
   
   
   
   @Override
   public void onFinishingActionMode()
   {
      deactivateSelectionMode();
   }
   
   
   
   @Override
   protected int getSettingsMask()
   {
      return super.getSettingsMask()
         | IOnSettingsChangedListener.DATE_TIME_RELATED;
   }
   
   
   
   @Override
   public Loader< List< RtmTaskNote >> newLoaderInstance( int id, Bundle config )
   {
      return new RtmTaskNotesLoader( getSherlockActivity(), taskId );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_note );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return RtmTaskNotesLoader.ID;
   }
   
   
   
   @Override
   public ListAdapter createListAdapterForResult( List< RtmTaskNote > result )
   {
      final NotesListFragmentAdapter adapter = new NotesListFragmentAdapter( this,
                                                                             R.layout.notelist_listitem,
                                                                             result );
      return adapter;
   }
   
   
   
   @Override
   public void onListAdapterCreated( ListAdapter listAdapter,
                                     List< RtmTaskNote > result )
   {
      super.onListAdapterCreated( listAdapter, result );
      
      if ( isSelectionMode )
      {
         if ( activeActionMode == null )
         {
            activateSelectionMode( selectedNotes );
         }
         else
         {
            configureListAdapterForSelectionMode();
            actionModeCallback.attachAdapter( getListAdapter() );
            activeActionMode.invalidate();
         }
      }
   }
   
   
   
   @Override
   public NotesListFragmentAdapter getListAdapter()
   {
      return (NotesListFragmentAdapter) super.getListAdapter();
   }
   
   
   
   private void activateSelectionMode( List< RtmTaskNote > preselectedNotes )
   {
      isSelectionMode = true;
      selectedNotes = new ArrayList< RtmTaskNote >( preselectedNotes );
      
      configureListAdapterForSelectionMode();
      
      actionModeCallback = new NotesListActionModeCallback( getSherlockActivity(),
                                                            getListAdapter() );
      actionModeCallback.setNotesListActionModeListener( this );
      
      activeActionMode = getSherlockActivity().startActionMode( actionModeCallback );
   }
   
   
   
   private void deactivateSelectionMode()
   {
      isSelectionMode = false;
      selectedNotes.clear();
      
      configureListAdapterForDefaultMode();
      
      actionModeCallback = null;
      activeActionMode = null;
   }
   
   
   
   private void updateSelectedNotes()
   {
      selectedNotes = new ArrayList< RtmTaskNote >( getListAdapter().getSelectedItems() );
   }
   
   
   
   private void configureListAdapterForSelectionMode()
   {
      final NotesListFragmentAdapter adapter = getListAdapter();
      
      adapter.setCheckable( true );
      adapter.selectBulk( selectedNotes );
      adapter.setOnSelectionChangesListener( this );
   }
   
   
   
   private void configureListAdapterForDefaultMode()
   {
      final NotesListFragmentAdapter adapter = getListAdapter();
      
      adapter.setOnSelectionChangesListener( null );
      adapter.deselectAll();
      adapter.setCheckable( false );
   }
}
