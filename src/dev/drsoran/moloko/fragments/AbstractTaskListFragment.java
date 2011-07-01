/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.MolokoPreferencesActivity;
import dev.drsoran.moloko.fragments.listeners.ITasksListListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListListener;
import dev.drsoran.moloko.loaders.ListTasksLoader;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


public abstract class AbstractTaskListFragment extends ListFragment implements
         IOnSettingsChangedListener, LoaderCallbacks< List< ListTask > >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTaskListFragment.class.getSimpleName();
   
   public static final String FILTER = "filter";
   
   public static final String TASK_SORT_ORDER = "task_sort_order";
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      private final static int MENU_ORDER_STATIC = 10000;
      
      public final static int MENU_ORDER = MENU_ORDER_STATIC - 1;
      
      protected final static int MENU_ORDER_FRONT = 1000;
      
      public final static int SORT = START_IDX + 1;
      
      public final static int SETTINGS = START_IDX + 2;
      
      public final static int SYNC = START_IDX + 3;
      
      public final static int EDIT_MULTIPLE_TASKS = START_IDX + 4;
   }
   
   private final static int TASKS_LOADER_ID = 1;
   
   private ITasksListListener listener;
   
   private Bundle configuration;
   
   

   protected AbstractTaskListFragment( Bundle configuration )
   {
      setConfiguration( configuration );
   }
   


   protected void setListener( ITasksListListener listener )
   {
      if ( listener == null )
         listener = new NullTasksListListener();
      
      this.listener = listener;
   }
   


   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITasksListListener )
         listener = (ITasksListListener) activity;
      else
         listener = new NullTasksListListener();
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   


   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setHasOptionsMenu( true );
      
      MolokoApp.get( getActivity() )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.TASK_SORT,
                                                   this );
      
      createInitialConfiguration( savedInstanceState );
      
      setListAdapter( createEmptyListAdapter() );
      
      getLoaderManager().initLoader( TASKS_LOADER_ID, configuration, this );
   }
   


   protected CharSequence getEmptyListText()
   {
      return getString( R.string.abstaskslist_no_tasks );
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( getConfiguration() );
   }
   


   @Override
   public void onDestroy()
   {
      super.onDestroy();
      MolokoApp.get( getActivity() ).unregisterOnSettingsChangedListener( this );
   }
   


   protected void createInitialConfiguration( Bundle savedInstanceState )
   {
      setConfiguration( savedInstanceState );
      
      if ( configuration.containsKey( TASK_SORT_ORDER ) )
         configuration.putInt( TASK_SORT_ORDER, getDefaultTaskSort() );
   }
   


   protected Bundle getConfiguration()
   {
      return configuration;
   }
   


   private void setConfiguration( Bundle configuration )
   {
      this.configuration = new Bundle( configuration );
   }
   


   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings ).setIcon( R.drawable.ic_menu_settings );
      
      addOptionsMenuIntents( menu );
   }
   


   @Override
   public void onPrepareOptionsMenu( Menu menu )
   {
      UIUtils.addSyncMenuItem( getActivity(),
                               menu,
                               OptionsMenu.SYNC,
                               OptionsMenu.MENU_ORDER_FRONT );
      
      addOptionalMenuItem( menu,
                           OptionsMenu.SORT,
                           getString( R.string.abstaskslist_menu_opt_sort ),
                           OptionsMenu.MENU_ORDER,
                           R.drawable.ic_menu_sort,
                           hasMultipleTasks() );
      
      addOptionalMenuItem( menu,
                           OptionsMenu.EDIT_MULTIPLE_TASKS,
                           getString( R.string.abstaskslist_menu_opt_edit_multiple ),
                           OptionsMenu.MENU_ORDER,
                           R.drawable.ic_menu_edit_multiple_tasks,
                           Intents.createSelectMultipleTasksIntent( getActivity(),
                                                                    getFilter(),
                                                                    getTaskSortConfiguration() ),
                           hasMultipleTasks() && hasRtmWriteAccess() );
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.SORT:
            showChooseTaskSortDialog();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   protected void showChooseTaskSortDialog()
   {
      final Context context = getActivity();
      new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_sort )
                                        .setTitle( R.string.abstaskslist_dlg_sort_title )
                                        .setSingleChoiceItems( R.array.app_sort_options,
                                                               getTaskSortIndex( getTaskSortConfiguration() ),
                                                               chooseTaskSortDialogListener )
                                        .setNegativeButton( R.string.btn_cancel,
                                                            chooseTaskSortDialogListener )
                                        .show();
   }
   


   protected void addOptionalMenuItem( Menu menu,
                                       int id,
                                       String title,
                                       int order,
                                       int iconId,
                                       boolean show )
   {
      addOptionalMenuItem( menu, id, title, order, iconId, null, show );
   }
   


   protected void addOptionalMenuItem( Menu menu,
                                       int id,
                                       String title,
                                       int order,
                                       int iconId,
                                       Intent intent,
                                       boolean show )
   {
      if ( show )
      {
         MenuItem item = menu.findItem( id );
         
         if ( item == null )
         {
            item = menu.add( Menu.NONE, id, order, title );
            
            if ( iconId != -1 )
               item.setIcon( iconId );
         }
         
         item.setTitle( title );
         
         if ( intent != null )
            item.setIntent( intent );
      }
      else
      {
         menu.removeItem( id );
      }
   }
   


   private void addOptionsMenuIntents( Menu menu )
   {
      final MenuItem item = menu.findItem( OptionsMenu.SETTINGS );
      
      if ( item != null )
         item.setIntent( new Intent( getActivity(),
                                     MolokoPreferencesActivity.class ) );
   }
   


   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      listener.onOpenTask( position );
   }
   


   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.TASK_SORT:
            // Check if this list was sorted by now changed task sort.
            // If so, we must re-sort it.
            final Integer oldTaskSort = (Integer) oldValues.get( IOnSettingsChangedListener.TASK_SORT );
            if ( oldTaskSort != null
               && isTaskSortConfigured( oldTaskSort.intValue() ) )
            {
               final int newTaskSort = MolokoApp.getSettings().getTaskSort();
               listener.onTaskSortChanged( newTaskSort );
            }
            break;
         
         default :
            break;
      }
   }
   


   public IFilter getFilter()
   {
      return configuration.getParcelable( FILTER );
   }
   


   public RtmSmartFilter getRtmSmartFilter()
   {
      final IFilter filter = getFilter();
      
      return ( filter instanceof RtmSmartFilter ) ? (RtmSmartFilter) filter
                                                 : null;
   }
   


   public boolean hasTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 0;
   }
   


   public boolean hasMultipleTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 1;
   }
   


   public ListTask getTask( int pos )
   {
      return (ListTask) getListAdapter().getItem( pos );
   }
   


   public boolean hasRtmWriteAccess()
   {
      return !AccountUtils.isReadOnlyAccess( getActivity() );
   }
   


   public void reload( Bundle config )
   {
      if ( config != null )
      {
         getLoaderManager().restartLoader( TASKS_LOADER_ID, config, this );
      }
   }
   


   public int getTaskSortConfiguration()
   {
      return configuration.getInt( TASK_SORT_ORDER );
   }
   


   public boolean isTaskSortConfigured( int taskSort )
   {
      return getTaskSortConfiguration() == taskSort;
   }
   


   protected abstract int getDefaultTaskSort();
   


   protected String resolveTaskSortToSqlite( int taskSort )
   {
      return Queries.resolveTaskSortToSqlite( taskSort );
   }
   


   public int getTaskSortValue( int idx )
   {
      return TaskSortPreference.getValueOfIndex( idx );
   }
   


   public int getTaskSortIndex( int value )
   {
      return TaskSortPreference.getIndexOfValue( value );
   }
   


   public void showError( CharSequence text )
   {
      setEmptyText( text );
      getLoaderManager().destroyLoader( TASKS_LOADER_ID );
   }
   


   protected abstract ListAdapter createEmptyListAdapter();
   


   protected abstract ListAdapter createListAdapterForResult( List< ListTask > result,
                                                              IFilter filter );
   


   public Loader< List< ListTask >> onCreateLoader( int id, Bundle config )
   {
      final IFilter filter = config.getParcelable( FILTER );
      final String selection = filter != null ? filter.getSqlSelection() : null;
      final String order = resolveTaskSortToSqlite( config.getInt( TASK_SORT_ORDER ) );
      
      return new ListTasksLoader( getActivity(), selection, order );
   }
   


   public void onLoadFinished( Loader< List< ListTask >> loader,
                               List< ListTask > data )
   {
      setListAdapter( createListAdapterForResult( data, getFilter() ) );
   }
   


   public void onLoaderReset( Loader< List< ListTask >> loader )
   {
      setListAdapter( createEmptyListAdapter() );
   }
   
   private final DialogInterface.OnClickListener chooseTaskSortDialogListener = new OnClickListener()
   {
      public void onClick( DialogInterface dialog, int which )
      {
         switch ( which )
         {
            case Dialog.BUTTON_NEGATIVE:
               break;
            
            default :
               final int newTaskSort = getTaskSortValue( which );
               
               dialog.dismiss();
               
               if ( newTaskSort != -1 && !isTaskSortConfigured( newTaskSort ) )
                  listener.onTaskSortChanged( newTaskSort );
               
               break;
         }
      }
   };
}
