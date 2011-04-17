/*
 * Copyright (c) 2010 Ronny Röhricht
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
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.dialogs.LocationChooser;
import dev.drsoran.moloko.dialogs.MultiChoiceDialog;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


public abstract class AbstractTasksListActivity extends ListActivity implements
         DialogInterface.OnClickListener, View.OnClickListener,
         IOnSettingsChangedListener
{
   private final static String TAG = "Moloko."
      + AbstractTasksListActivity.class.getSimpleName();
   
   
   protected final static class AsyncFillListResult
   {
      public final List< ListTask > tasks;
      
      public final RtmSmartFilter filter;
      
      public final Bundle configuration;
      
      

      public AsyncFillListResult( List< ListTask > tasks,
         RtmSmartFilter filter, Bundle configuration )
      {
         this.tasks = tasks;
         this.filter = filter;
         this.configuration = configuration;
      }
   }
   

   protected class AsyncFillList extends
            AsyncTask< ContentResolver, Void, AsyncFillListResult >
   {
      private final Bundle configuration;
      
      

      public AsyncFillList( Bundle configuration )
      {
         this.configuration = configuration;
      }
      


      @Override
      protected void onPreExecute()
      {
         beforeQueryTasksAsync( configuration );
      }
      


      @Override
      protected AsyncFillListResult doInBackground( ContentResolver... params )
      {
         if ( params.length < 1 )
            throw new IllegalArgumentException( "Expected 1 parameter of type ContentResolver" );
         
         return queryTasksAsync( params[ 0 ], configuration );
      }
      


      @Override
      protected void onPostExecute( AsyncFillListResult result )
      {
         setTasksResult( result );
         AbstractTasksListActivity.this.asyncFillList = null;
      }
   }
   

   protected class ClearAndAsyncFillList extends AsyncFillList
   {
      public ClearAndAsyncFillList( Bundle configuration )
      {
         super( configuration );
      }
      


      @Override
      protected void onPreExecute()
      {
         super.onPreExecute();
         
         // Show loading spinner
         clearList( AbstractTasksListActivity.this.findViewById( android.R.id.empty ) );
      }
   }
   
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
   
   protected final Runnable dontClearAndfillListRunnable = new Runnable()
   {
      public void run()
      {
         if ( asyncFillList != null )
         {
            Log.w( TAG, "Canceled AsyncFillList task." );
            asyncFillList.cancel( true );
         }
         
         asyncFillList = new AsyncFillList( AbstractTasksListActivity.this.getIntent()
                                                                          .getExtras() );
         asyncFillList.execute( getContentResolver() );
      }
   };
   
   protected View emptyListView;
   
   protected AsyncFillList asyncFillList;
   
   protected final Handler handler = new Handler();
   
   protected final ContentObserver dbObserver = new ContentObserver( handler )
   {
      @Override
      public void onChange( boolean selfChange )
      {
         // Aggregate several calls to a single update.
         DelayedRun.run( handler, dontClearAndfillListRunnable, 1000 );
      }
   };
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
      
      emptyListView = findViewById( R.id.taskslist_activity_no_elements );
      
      registerForContextMenu( getListView() );
      
      MolokoApp.get( this )
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
   protected void onStop()
   {
      super.onStop();
      
      if ( asyncFillList != null )
         asyncFillList.cancel( true );
      
      asyncFillList = null;
      
      UIUtils.showTitleBarAddTask( this, false );
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getListView() );
      
      MolokoApp.get( this ).unregisterOnSettingsChangedListener( this );
      
      TasksProviderPart.unregisterContentObserver( this, dbObserver );
   }
   


   @Override
   protected void onNewIntent( Intent intent )
   {
      UIUtils.showTitleBarAddTask( this, false );
      
      setIntent( intent );
      handleIntent( intent );
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( getIntent().getExtras() );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      
      if ( state != null )
      {
         getIntent().putExtras( state );
         
         handleIntent( getIntent() );
      }
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
                                                                       (RtmSmartFilter) getIntent().getParcelableExtra( FILTER ),
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
            new AlertDialog.Builder( this ).setIcon( R.drawable.ic_dialog_sort )
                                           .setTitle( R.string.abstaskslist_dlg_sort_title )
                                           .setSingleChoiceItems( R.array.app_sort_options,
                                                                  getTaskSortIndex( getTaskSort() ),
                                                                  this )
                                           .setNegativeButton( R.string.btn_cancel,
                                                               this )
                                           .show();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
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
      
      if ( !AccountUtils.isReadOnlyAccess( this ) )
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
      
      final RtmSmartFilter filter = getIntent().getParcelableExtra( FILTER );
      
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
                      CtxtMenu.TAGS,
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
            onTaskClicked( info.position );
            return true;
            
         case CtxtMenu.EDIT_TASK:
            onTaskEdit( info.position );
            return true;
            
         case CtxtMenu.COMPLETE_TASK:
            onTaskComplete( info.position );
            return true;
            
         case CtxtMenu.POSTPONE_TASK:
            onTaskPostpone( info.position );
            return true;
            
         case CtxtMenu.DELETE_TASK:
            onTaskDelete( info.position );
            return true;
            
         case CtxtMenu.OPEN_LIST:
            onListNameClicked( info.targetView.findViewById( R.id.taskslist_listitem_btn_list_name ) );
            return true;
            
         case CtxtMenu.TAGS:
            final List< String > tags = getTask( info.position ).getTags();
            
            if ( tags.size() == 1 )
               onTagClicked( tags.get( 0 ) );
            else
               openMultipleTags( tags );
            return true;
            
         case CtxtMenu.TASKS_AT_LOCATION:
            onLocationClicked( info.position );
            return true;
            
         case CtxtMenu.NOTES:
            openNotes( info.position, null );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   @Override
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
      onTaskClicked( position );
   }
   


   public void onClick( DialogInterface dialog, int which )
   {
      if ( dialog instanceof MultiChoiceDialog )
      {
         final MultiChoiceDialog tagsChoice = (MultiChoiceDialog) dialog;
         
         final CharSequence[] tags = tagsChoice.getItems();
         final boolean[] states = tagsChoice.getStates();
         
         final ArrayList< String > chosenTags = new ArrayList< String >();
         
         // filter out the chosen tags
         for ( int i = 0; i < states.length; i++ )
         {
            if ( states[ i ] )
            {
               chosenTags.add( tags[ i ].toString() );
            }
         }
         
         final int chosenTagsSize = chosenTags.size();
         
         if ( chosenTagsSize == 1 )
         {
            onTagClicked( chosenTags.get( 0 ) );
         }
         else if ( chosenTagsSize > 1 )
         {
            final boolean andLink = ( which == DialogInterface.BUTTON1 );
            final Intent intent = new Intent( Intent.ACTION_VIEW,
                                              Tasks.CONTENT_URI );
            intent.putExtra( TITLE_ICON, R.drawable.ic_title_tag );
            
            final StringBuilder filter = new StringBuilder();
            
            for ( int i = 0; i < chosenTagsSize; ++i )
            {
               final String chosenTag = chosenTags.get( i );
               
               filter.append( RtmSmartFilterLexer.OP_TAG_LIT )
                     .append( chosenTag );
               
               // not last element
               if ( i < chosenTagsSize - 1 )
               {
                  if ( andLink )
                     filter.append( " " )
                           .append( RtmSmartFilterLexer.AND_LIT )
                           .append( " " );
                  else
                     filter.append( " " )
                           .append( RtmSmartFilterLexer.OR_LIT )
                           .append( " " );
               }
            }
            
            intent.putExtra( FILTER, new RtmSmartFilter( filter.toString() ) );
            intent.putExtra( TITLE,
                             getString( R.string.taskslist_titlebar,
                                        TextUtils.join( ( andLink )
                                                                   ? " "
                                                                      + getString( R.string.abstaskslist_dlg_show_tags_and )
                                                                      + " "
                                                                   : " "
                                                                      + getString( R.string.abstaskslist_dlg_show_tags_or )
                                                                      + " ",
                                                        chosenTags ) ) );
            
            startActivity( intent );
         }
      }
      
      // Sort dialog
      else if ( dialog instanceof AlertDialog )
      {
         switch ( which )
         {
            case Dialog.BUTTON_NEGATIVE:
               break;
            
            default :
               final int newTaskSort = getTaskSortValue( which );
               
               if ( newTaskSort != -1
                  && !isSameTaskSortLikeCurrent( newTaskSort ) )
               {
                  setTaskSort( newTaskSort, true );
               }
               
               dialog.dismiss();
               break;
         }
      }
   }
   


   public void onClick( View view )
   {
      if ( view.getId() == R.id.tags_layout_btn_tag )
      {
         onTagClicked( view );
      }
   }
   


   public void onTaskClicked( int pos )
   {
      startActivity( Intents.createOpenTaskIntent( this, getTask( pos ).getId() ) );
   }
   


   public void onTaskClicked( View view )
   {
      onTaskClicked( getListView().getPositionForView( view ) );
   }
   


   private void onTaskEdit( int pos )
   {
      startActivity( Intents.createEditTaskIntent( this, getTask( pos ).getId() ) );
   }
   


   private void onTaskComplete( int pos )
   {
      final ListTask task = getTask( pos );
      TaskEditUtils.setTaskCompletion( this, task, task.getCompleted() == null );
   }
   


   private void onTaskPostpone( int pos )
   {
      TaskEditUtils.postponeTask( this, getTask( pos ) );
   }
   


   private void onTaskDelete( int pos )
   {
      final ListTask task = getTask( pos );
      
      new AlertDialog.Builder( this ).setMessage( getString( R.string.abstaskslist_dlg_delete,
                                                             task.getName() ) )
                                     .setPositiveButton( R.string.btn_delete,
                                                         new OnClickListener()
                                                         {
                                                            public void onClick( DialogInterface dialog,
                                                                                 int which )
                                                            {
                                                               TaskEditUtils.deleteTask( AbstractTasksListActivity.this,
                                                                                         task );
                                                            }
                                                         } )
                                     .setNegativeButton( R.string.btn_cancel,
                                                         null )
                                     .show();
   }
   


   public void onListNameClicked( View view )
   {
      final int pos = getListView().getPositionForView( view );
      final ListTask listTask = getTask( pos );
      
      if ( listTask != null )
      {
         final Intent intent = Intents.createOpenListIntent( this,
                                                             listTask.getListId(),
                                                             null );
         
         if ( intent != null )
            startActivity( intent );
      }
   }
   


   public void onTagClicked( View view )
   {
      final TextView tagCtrl = (TextView) view;
      
      onTagClicked( tagCtrl.getText() );
   }
   


   public void onTagClicked( CharSequence tag )
   {
      startActivity( Intents.createOpenTagIntent( this, tag.toString() ) );
   }
   


   public void gotoLocation( int pos )
   {
      LocationChooser.showChooser( this, getTask( pos ).getLocationName(), true );
   }
   


   public void onLocationClicked( int pos )
   {
      startActivity( Intents.createOpenLocationIntentByName( this,
                                                             getTask( pos ).getLocationName() ) );
   }
   


   public void onLocationClicked( View view )
   {
      final TextView location = (TextView) view;
      startActivity( Intents.createOpenLocationIntentByName( this,
                                                             location.getText()
                                                                     .toString() ) );
   }
   


   public void openMultipleTags( List< String > tags )
   {
      final ArrayList< CharSequence > tmp = new ArrayList< CharSequence >();
      
      for ( final String tag : tags )
      {
         tmp.add( tag );
      }
      
      new MultiChoiceDialog( this, tmp, this ).setTitle( getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                                                           tags.size() ) )
                                              .setIcon( R.drawable.ic_dialog_tag )
                                              .setButtonText( getString( R.string.abstaskslist_dlg_show_tags_and ) )
                                              .setButton2Text( getString( R.string.abstaskslist_dlg_show_tags_or ) )
                                              .show();
   }
   


   public void openNotes( int position, String startNoteId )
   {
      final ListTask task = getTask( position );
      final List< RtmTaskNote > notes = task.getNotes( this );
      
      if ( notes.size() > 0 )
      {
         // Show only this note, the task has only this one.
         if ( notes.size() == 1 )
         {
            final Intent intent = new Intent( Intent.ACTION_VIEW,
                                              Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                        notes.get( 0 )
                                                                             .getId() ) );
            
            startActivity( intent );
         }
         
         // Show all notes of the task.
         else
         {
            
            final Intent intent = new Intent( Intent.ACTION_VIEW,
                                              Notes.CONTENT_URI );
            intent.putExtra( Notes.TASKSERIES_ID, task.getTaskSeriesId() );
            
            // If a startNoteId is given we use this as entry point if
            // multiple notes are available.
            if ( startNoteId != null )
               intent.putExtra( Notes._ID, startNoteId );
            
            startActivity( intent );
         }
      }
      else
      {
         Log.e( TAG, "Tried to open notes from a task with no notes." );
      }
   }
   


   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldVlaues )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.TASK_SORT:
            // Check if this list was sorted by now changed task sort.
            // If so, we must re-sort it.
            if ( getTaskSort() == (Integer) oldVlaues.get( Integer.valueOf( which ) ) )
            {
               setTaskSort( MolokoApp.getSettings().getTaskSort(), true );
            }
            else
            {
               break;
            }
            
         default :
            onContentChanged();
      }
   }
   


   /**
    * Note: This will run in the GUI thread.
    */
   protected void beforeQueryTasksAsync( Bundle configuration )
   {
      UIUtils.setTitle( AbstractTasksListActivity.this, R.string.phr_loading );
   }
   


   /**
    * Note: This will run in a background thread.
    */
   abstract protected AsyncFillListResult queryTasksAsync( ContentResolver contentResolver,
                                                           Bundle configuration );
   


   /**
    * Note: This will run in the GUI thread.
    */
   abstract protected void setTasksResult( AsyncFillListResult result );
   


   abstract protected ListAdapter createListAdapter( AsyncFillListResult result );
   


   abstract protected void handleIntent( Intent intent );
   


   protected void clearList( View emptyView )
   {
      setListAdapter( createListAdapter( null ) );
      switchEmptyView( emptyView );
   }
   


   protected void fillListAsync()
   {
      handler.post( new Runnable()
      {
         public void run()
         {
            if ( asyncFillList != null )
            {
               Log.w( TAG, "Canceled AsyncFillList task." );
               asyncFillList.cancel( true );
            }
            
            asyncFillList = new ClearAndAsyncFillList( getIntent().getExtras() );
            asyncFillList.execute( getContentResolver() );
         }
      } );
   }
   


   private boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      final MenuItem item = menu.findItem( OptionsMenu.SETTINGS );
      
      ok = item != null;
      
      if ( ok )
      {
         item.setIntent( new Intent( this, MolokoPreferencesActivity.class ) );
      }
      
      return ok;
   }
   


   protected final ListTask getTask( int pos )
   {
      return (ListTask) getListAdapter().getItem( pos );
   }
   


   protected int getTaskSortValue( int idx )
   {
      return TaskSortPreference.getValueOfIndex( idx );
   }
   


   protected int getTaskSortIndex( int value )
   {
      return TaskSortPreference.getIndexOfValue( value );
   }
   


   protected int getTaskSort()
   {
      return getIntent().getIntExtra( TASK_SORT_ORDER,
                                      Settings.TASK_SORT_DEFAULT );
   }
   


   protected void setTaskSort( int taskSort, boolean refillList )
   {
      getIntent().putExtra( TASK_SORT_ORDER, taskSort );
      
      if ( refillList )
         fillListAsync();
   }
   


   protected boolean isSameTaskSortLikeCurrent( int sortOrder )
   {
      return getTaskSort() == sortOrder;
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
   


   protected void switchEmptyView( View newEmptyView )
   {
      final View currentView = getListView().getEmptyView();
      
      final boolean change = ( currentView != null && newEmptyView == null )
         || ( currentView == null && newEmptyView != null )
         || ( currentView.getId() != newEmptyView.getId() );
      
      if ( change )
      {
         if ( currentView != null )
            currentView.setVisibility( View.GONE );
         
         getListView().setEmptyView( newEmptyView );
      }
   }
}
