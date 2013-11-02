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

package dev.drsoran.moloko.app.task;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.loaders.TaskNotesLoader;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.actionmodes.BaseMultiChoiceModeListener;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;
import dev.drsoran.moloko.ui.fragments.MolokoMultiChoiceModalListFragment;
import dev.drsoran.moloko.ui.widgets.MolokoListView;


class NotesListFragment extends MolokoMultiChoiceModalListFragment< Note >
         implements IAbsViewPagerSupport, IOnSettingsChangedListener
{
   public final static NotesListFragment newInstance( Bundle config )
   {
      final NotesListFragment fragment = new NotesListFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   private boolean enableAbsViewPagerWorkaround;
   
   @InstanceState( key = Intents.Extras.KEY_TASK_ID )
   private long taskId;
   
   private INotesListsFragmentListener listener;
   
   private AppContext appContext;
   
   
   
   public NotesListFragment()
   {
      registerAnnotatedConfiguredInstance( this, NotesListFragment.class );
      setNoElementsResourceId( R.string.noteslist_no_notes );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      appContext = AppContext.get( activity );
      
      if ( activity instanceof INotesListsFragmentListener )
         listener = (INotesListsFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      enableAbsViewPagerWorkaround = getResources().getBoolean( R.bool.env_enable_abs_viewpager_workaround );
      
      super.onCreate( savedInstanceState );
      setHasOptionsMenu( !enableAbsViewPagerWorkaround );
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
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      super.onCreateOptionsMenu( menu, inflater );
      if ( !enableAbsViewPagerWorkaround )
      {
         onCreateOptionsMenuImpl( menu, inflater );
      }
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      appContext.getAppEvents()
                .registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
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
      appContext = null;
      super.onDetach();
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenuViewPagerSupportWorkaround( Menu menu,
                                                                 MenuInflater inflater )
   {
      onCreateOptionsMenuImpl( menu, inflater );
      return true;
   }
   
   
   
   private void onCreateOptionsMenuImpl( Menu menu, MenuInflater inflater )
   {
      if ( getListAdapter() != null && hasWritableAccess() )
      {
         inflater.inflate( R.menu.noteslist_fragment_rwd, menu );
      }
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenuViewPagerSupportWorkaround( Menu menu )
   {
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      if ( !enableAbsViewPagerWorkaround )
      {
         return onOptionsItemSelectedImpl( item );
      }
      else
      {
         return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelectedViewPagerSupportWorkaround( MenuItem item )
   {
      return onOptionsItemSelectedImpl( item );
   }
   
   
   
   private boolean onOptionsItemSelectedImpl( MenuItem item )
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
            return false;
      }
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      super.onListItemClick( l, v, position, id );
      
      if ( listener != null )
      {
         listener.onOpenNote( getListAdapter().getItem( position ), position );
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( ( which & IOnSettingsChangedListener.DATE_TIME_RELATED ) != 0 )
      {
         getListAdapter().notifyDataSetChanged();
      }
   }
   
   
   
   @Override
   public Loader< List< Note >> newLoaderInstance( int id, Bundle config )
   {
      return new TaskNotesLoader( getUiContext().asDomainContext(), taskId );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_note );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TaskNotesLoader.ID;
   }
   
   
   
   @Override
   public SwappableArrayAdapter< Note > createListAdapter()
   {
      final NotesListFragmentAdapter adapter = new NotesListFragmentAdapter( this );
      return adapter;
   }
   
   
   
   @Override
   public NotesListFragmentAdapter getListAdapter()
   {
      return (NotesListFragmentAdapter) super.getListAdapter();
   }
   
   
   
   @Override
   public int getChoiceMode()
   {
      return hasWritableAccess() ? MolokoListView.CHOICE_MODE_MULTIPLE_MODAL
                                : MolokoListView.CHOICE_MODE_NONE;
   }
   
   
   
   @Override
   public BaseMultiChoiceModeListener< Note > createMultiCoiceModalModeListener()
   {
      final NotesListActionModeCallback callback = new NotesListActionModeCallback( getMolokoListView() );
      callback.setNotesListActionModeListener( listener );
      
      return callback;
   }
}
