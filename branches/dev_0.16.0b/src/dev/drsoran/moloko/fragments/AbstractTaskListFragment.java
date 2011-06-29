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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.MolokoPreferencesActivity;
import dev.drsoran.moloko.dialogs.MultiChoiceDialog;
import dev.drsoran.moloko.fragments.listeners.ITaskListListener;
import dev.drsoran.moloko.fragments.listeners.NullTaskListListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


abstract class AbstractTaskListFragment extends ListFragment implements
         View.OnClickListener, IOnSettingsChangedListener
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
      
      public final static int TAG = 7;
      
      public final static int TAGS = 8;
      
      public final static int TASKS_AT_LOCATION = 9;
      
      public final static int NOTES = 10;
   }
   
   private ITaskListListener listener;
   
   private Bundle configuration;
   
   

   protected AbstractTaskListFragment( Bundle configuration )
   {
      this.configuration = new Bundle( configuration );
   }
   


   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITaskListListener )
         listener = (ITaskListListener) activity;
      else
         listener = new NullTaskListListener();
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
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
      
      // TasksProviderPart.registerContentObserver( this, dbObserver );
      
      configuration = new Bundle( savedInstanceState );
      
      configureTaskSort();
   }
   


   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist, container, false );
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   @Override
   public void onStop()
   {
      super.onStop();
      // TODO: Close TitleBar?
   }
   


   @Override
   public void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getListView() );
      
      MolokoApp.get( getActivity() ).unregisterOnSettingsChangedListener( this );
      
      // TasksProviderPart.unregisterContentObserver( this, dbObserver );
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
                                                                    getTaskSort() ),
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
                                                               getTaskSortIndex( getTaskSort() ),
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
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      
      final ListTask task = getTask( info.position );
      
      menu.add( Menu.NONE,
                CtxtMenu.OPEN_TASK,
                Menu.NONE,
                getString( R.string.phr_open_with_name, task.getName() ) );
      
      if ( hasRtmWriteAccess() )
      {
         menu.add( Menu.NONE,
                   CtxtMenu.EDIT_TASK,
                   Menu.NONE,
                   getString( R.string.phr_edit_with_name, task.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.COMPLETE_TASK,
                   Menu.NONE,
                   getString( task.getCompleted() == null
                                                         ? R.string.abstaskslist_listitem_ctx_complete_task
                                                         : R.string.abstaskslist_listitem_ctx_uncomplete_task,
                              task.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.POSTPONE_TASK,
                   Menu.NONE,
                   getString( R.string.abstaskslist_listitem_ctx_postpone_task,
                              task.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.DELETE_TASK,
                   Menu.NONE,
                   getString( R.string.phr_delete_with_name, task.getName() ) );
      }
      
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
      
      final int notesCount = task.getNumberOfNotes();
      if ( notesCount > 0 )
         menu.add( Menu.NONE,
                   CtxtMenu.NOTES,
                   Menu.NONE,
                   getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_notes,
                                                     notesCount ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.OPEN_TASK:
            listener.onOpenTask( info.position );
            return true;
            
         case CtxtMenu.EDIT_TASK:
            listener.onEditTask( info.position );
            return true;
            
         case CtxtMenu.COMPLETE_TASK:
            onCompleteTask( info.position );
            return true;
            
         case CtxtMenu.POSTPONE_TASK:
            onPostponeTask( info.position );
            return true;
            
         case CtxtMenu.DELETE_TASK:
            onDeleteTask( info.position );
            return true;
            
         case CtxtMenu.OPEN_LIST:
            listener.onOpenList( info.position,
                                 getTask( info.position ).getListId() );
            return true;
            
         case CtxtMenu.TAG:
            listener.onConfigurationChanged( Intents.createOpenTagsIntentConfig( getActivity(),
                                                                                 getTask( info.position ).getTags(),
                                                                                 RtmSmartFilterLexer.AND_LIT ) );
            return true;
            
         case CtxtMenu.TAGS:
            showChooseTagsDialog( getTask( info.position ).getTags() );
            return true;
            
         case CtxtMenu.TASKS_AT_LOCATION:
            listener.onOpenLocation( info.position,
                                     getTask( info.position ).getLocationId() );
            return true;
            
         case CtxtMenu.NOTES:
            listener.onOpenNotes( info.position,
                                  getTask( info.position ).getNotes( getActivity() ) );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   protected void showChooseTagsDialog( List< String > tags )
   {
      final List< CharSequence > tmp = new ArrayList< CharSequence >( tags.size() );
      
      for ( String tag : tags )
         tmp.add( tag );
      
      new MultiChoiceDialog( getActivity(),
                             tmp,
                             chooseMultipleTagsDialogListener ).setTitle( getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                                                                            tags.size() ) )
                                                               .setIcon( R.drawable.ic_dialog_tag )
                                                               .setButtonText( getString( R.string.abstaskslist_dlg_show_tags_and ) )
                                                               .setButton2Text( getString( R.string.abstaskslist_dlg_show_tags_or ) )
                                                               .show();
   }
   


   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      listener.onOpenTask( position );
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
            final Integer oldTaskSort = (Integer) oldValues.get( IOnSettingsChangedListener.TASK_SORT );
            if ( oldTaskSort != null && isTaskSortSet( oldTaskSort.intValue() ) )
            {
               sortTasks( MolokoApp.getSettings().getTaskSort() );
            }
            break;
         
         default :
            break;
      }
      
      onContentChanged();
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
   


   protected final ListTask getTask( int pos )
   {
      return (ListTask) getListAdapter().getItem( pos );
   }
   


   public boolean hasRtmWriteAccess()
   {
      return !AccountUtils.isReadOnlyAccess( getActivity() );
   }
   


   protected void configureTaskSort()
   {
      if ( !configuration.containsKey( TASK_SORT_ORDER ) )
         configuration.putInt( TASK_SORT_ORDER, getDefaultTaskSort() );
   }
   


   public void sortTasks( int taskSort )
   {
      configuration.putInt( TASK_SORT_ORDER, taskSort );
   }
   


   public int getTaskSort()
   {
      return configuration.getInt( TASK_SORT_ORDER );
   }
   


   public boolean isTaskSortSet( int taskSort )
   {
      return getTaskSort() == taskSort;
   }
   


   protected abstract int getDefaultTaskSort();
   


   protected int getTaskSortValue( int idx )
   {
      return TaskSortPreference.getValueOfIndex( idx );
   }
   


   protected int getTaskSortIndex( int value )
   {
      return TaskSortPreference.getIndexOfValue( value );
   }
   


   private void onCompleteTask( int pos )
   {
      final ListTask task = getTask( pos );
      TaskEditUtils.setTaskCompletion( getActivity(),
                                       task,
                                       task.getCompleted() == null );
   }
   


   private void onPostponeTask( int pos )
   {
      TaskEditUtils.postponeTask( getActivity(), getTask( pos ) );
   }
   


   private void onDeleteTask( int pos )
   {
      final ListTask task = getTask( pos );
      final Activity activity = getActivity();
      
      UIUtils.newDeleteElementDialog( activity, task.getName(), new Runnable()
      {
         public void run()
         {
            TaskEditUtils.deleteTask( activity, task );
         }
      }, null ).show();
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
               
               if ( newTaskSort != -1 && !isTaskSortSet( newTaskSort ) )
                  sortTasks( newTaskSort );
               
               dialog.dismiss();
               break;
         }
      }
   };
   
   private final DialogInterface.OnClickListener chooseMultipleTagsDialogListener = new OnClickListener()
   {
      public void onClick( DialogInterface dialog, int which )
      {
         final boolean andLink = ( which == DialogInterface.BUTTON1 );
         final List< String > chosenTags = new LinkedList< String >();
         
         {
            final MultiChoiceDialog tagsChoice = (MultiChoiceDialog) dialog;
            final CharSequence[] tags = tagsChoice.getItems();
            final boolean[] states = tagsChoice.getStates();
            
            // filter out the chosen tags
            for ( int i = 0; i < states.length; i++ )
               if ( states[ i ] )
                  chosenTags.add( tags[ i ].toString() );
         }
         
         listener.onConfigurationChanged( Intents.createOpenTagsIntentConfig( getActivity(),
                                                                              chosenTags,
                                                                              andLink
                                                                                     ? RtmSmartFilterLexer.AND_LIT
                                                                                     : RtmSmartFilterLexer.OR_LIT ) );
      }
   };
   
}
