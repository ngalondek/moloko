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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;


abstract class AbstractTaskListFragment extends ListFragment implements
         DialogInterface.OnClickListener, View.OnClickListener,
         IOnSettingsChangedListener
{
   private final static String TAG = "Moloko."
      + AbstractTaskListFragment.class.getSimpleName();
   
   public static final String TITLE = "title";
   
   public static final String TITLE_ICON = "title_icon";
   
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
   

   protected static class CtxtMenu
   {
      public final static int OPEN_TASK = 1;
      
      public final static int EDIT_TASK = 2;
      
      public final static int COMPLETE_TASK = 3;
      
      public final static int POSTPONE_TASK = 4;
      
      public final static int DELETE_TASK = 5;
      
      public final static int OPEN_LIST = 6;
      
      public final static int TAGS = 7;
      
      public final static int TASKS_AT_LOCATION = 8;
      
      public final static int NOTES = 9;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setEmptyText( getString( R.string.abstaskslist_no_tasks ) );
      
      registerForContextMenu( getListView() );
      
      MolokoApp.get( getActivity() )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.RTM_TIMEZONE
                                                      | IOnSettingsChangedListener.RTM_DATEFORMAT
                                                      | IOnSettingsChangedListener.RTM_TIMEFORMAT
                                                      | IOnSettingsChangedListener.TASK_SORT,
                                                   this );
      
      TasksProviderPart.registerContentObserver( this, dbObserver );
      
      if ( getIntent().getExtras() == null )
         // Put an empty bundle, this prevents null pointer checking
         // in later steps.
         getIntent().putExtras( new Bundle() );
      
      if ( savedInstanceState != null )
         getIntent().putExtras( savedInstanceState );
      
      if ( !getIntent().hasExtra( TASK_SORT_ORDER ) )
         setTaskSort( MolokoApp.getSettings().getTaskSort(), false );
      
      onNewIntent( getIntent() );
   }
   


   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist, container, false );
   }
   


   @Override
   public void onStop()
   {
      super.onStop();
   }
   


   @Override
   public void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getListView() );
      
      MolokoApp.get( getActivity() ).unregisterOnSettingsChangedListener( this );
      
      TasksProviderPart.unregisterContentObserver( this, dbObserver );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings ).setIcon( R.drawable.ic_menu_settings );
      
      return addOptionsMenuIntents( menu );
   }
   


   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      UIUtils.addSyncMenuItem( this,
                               menu,
                               OptionsMenu.SYNC,
                               OptionsMenu.MENU_ORDER_FRONT );
      
      if ( getListAdapter() != null )
      {
         addOptionalMenuItem( menu,
                              OptionsMenu.SORT,
                              getString( R.string.abstaskslist_menu_opt_sort ),
                              OptionsMenu.MENU_ORDER,
                              R.drawable.ic_menu_sort,
                              getListAdapter().getCount() > 1 );
         
         addOptionalMenuItem( menu,
                              OptionsMenu.EDIT_MULTIPLE_TASKS,
                              getString( R.string.abstaskslist_menu_opt_edit_multiple ),
                              OptionsMenu.MENU_ORDER,
                              R.drawable.ic_menu_edit_multiple_tasks,
                              Intents.createSelectMultipleTasksIntent( this,
                                                                       (IFilter) getIntent().getParcelableExtra( FILTER ),
                                                                       getTaskSort() ),
                              !AccountUtils.isReadOnlyAccess( this )
                                 && getListAdapter().getCount() > 1 );
      }
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.SORT:
            final Context context = getActivity();
            new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_sort )
                                              .setTitle( R.string.abstaskslist_dlg_sort_title )
                                              .setSingleChoiceItems( R.array.app_sort_options,
                                                                     getTaskSortIndex( getTaskSort() ),
                                                                     context )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  context )
                                              .show();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
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
   


   public void onClick( DialogInterface dialog, int which )
   {
      // TODO Auto-generated method stub
      
   }
   


   public void onClick( View v )
   {
      // TODO Auto-generated method stub
      
   }
   


   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.TASK_SORT:
            // Check if this list was sorted by now changed task sort.
            // If so, we must re-sort it.
            if ( getTaskSort() == (Integer) oldVlaues.get( Integer.valueOf( which ) ) )
            {
               setTaskSort( MolokoApp.getSettings().getTaskSort(), true );
               onContentChanged();
            }
            
            break;
         
         default :
            onContentChanged();
      }
   }
}
