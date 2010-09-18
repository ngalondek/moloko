package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.Queries;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.MultiChoiceDialog;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ListTask;


public abstract class AbstractTasksListActivity extends ListActivity implements
         DialogInterface.OnClickListener, View.OnClickListener,
         IOnSettingsChangedListener
{
   private final static String TAG = AbstractTasksListActivity.class.getSimpleName();
   
   public static final String TITLE = "title";
   
   public static final String TITLE_ICON = "title_icon";
   
   public static final String FILTER = "filter";
   
   public static final String FILTER_EVALUATED = "filter_eval";
   
   public static final String SORT_ORDER_IDX = "sort_order_idx";
   
   public static final String ADAPTER_CONFIG = "adapter_config";
   
   /**
    * If we have a concrete list name, then we do not need to click it. Otherwise we would call the same list again. But
    * this only applies to non smart lists
    */
   public static final String DISABLE_LIST_NAME = "disable_list_name";
   
   protected static final String[] SORT_ORDER_VALUES =
   { Tasks.DEFAULT_SORT_ORDER, Tasks.SORT_DUE_DATE, Tasks.SORT_TASK_NAME };
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      private final static int MENU_ORDER_STATIC = 10000;
      
      public final static int MENU_ORDER = MENU_ORDER_STATIC - 1;
      
      public final static int SORT = START_IDX + 1;
      
      public final static int SETTINGS = START_IDX + 2;
   }
   

   protected static class CtxtMenu
   {
      public final static int TOGGLE_TASK_COMPLETED = 0;
      
      public final static int OPEN_TASK = 1;
      
      public final static int RENAME_TASK = 2;
      
      public final static int OPEN_LIST = 3;
      
      public final static int TAGS = 4;
      
      public final static int NOTES = 5;
   }
   
   private final ContentObserver dbObserver = new ContentObserver( new Handler() )
   {
      @Override
      public void onChange( boolean selfChange )
      {
         // Aggregate several calls to a single update.
         // @see http://developer.android.com/resources/articles/timed-ui-updates.html
      }
   };
   
   protected final Bundle configuration = new Bundle();
   
   /**
    * Variable holds the temporary selected sort mode from the sort order AlertDialog. This will be made persistent if
    * the dialog is closed with OK.
    */
   private int tempSelectedSortOrder = -1;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
      
      registerForContextMenu( getListView() );
      
      MolokoApp.getSettings()
               .registerOnSettingsChangedListener( Settings.SETTINGS_RTM_TIMEZONE
                                                      | Settings.SETTINGS_RTM_LANGUAGE,
                                                   this );
      
      TasksProviderPart.registerContentObserver( this, dbObserver );
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getListView() );
      
      MolokoApp.getSettings().unregisterOnSettingsChangedListener( this );
      
      TasksProviderPart.unregisterContentObserver( this, dbObserver );
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      
      if ( state != null )
      {
         configuration.clear();
         configuration.putAll( state );
         
         if ( shouldFillList() )
            fillList();
      }
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings )
          .setIcon( R.drawable.icon_settings_black );
      
      return addOptionsMenuIntents( menu );
   }
   


   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      addOptionalMenuItem( menu,
                           OptionsMenu.SORT,
                           getString( R.string.abstaskslist_menu_opt_sort ),
                           OptionsMenu.MENU_ORDER,
                           R.drawable.icon_sort_black,
                           getListAdapter().getCount() > 1 );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      // Handle item selection
      switch ( item.getItemId() )
      {
         case OptionsMenu.SORT:
            new AlertDialog.Builder( this ).setIcon( R.drawable.icon_sort_white )
                                           .setTitle( R.string.abstaskslist_dlg_sort_title )
                                           .setSingleChoiceItems( R.array.abstaskslist_dlg_sort_options,
                                                                  getSortOrderIdx(),
                                                                  this )
                                           .setPositiveButton( R.string.phr_ok,
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
      
      // TODO: Make this menu item dependent on the access level set
      /**
       * menu.add( Menu.NONE, CTX_MENU_TOGGLE_TASK_COMPLETED, Menu.NONE, ( task.getCompleted() == null ) ? getString(
       * R.string.taskslist_listitem_ctx_set_task_completed ) : getString(
       * R.string.taskslist_listitem_ctx_set_task_incomplete ) );
       */
      
      menu.add( Menu.NONE,
                CtxtMenu.OPEN_TASK,
                Menu.NONE,
                getString( R.string.phr_open_with_name, task.getName() ) )
          .setTitleCondensed( getString( R.string.abstaskslist_listitem_ctx_open_task ) );
      
      final Bundle adapterConfig = configuration.getBundle( ADAPTER_CONFIG );
      
      if ( adapterConfig == null
         || !adapterConfig.getBoolean( DISABLE_LIST_NAME ) )
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
         menu.add( Menu.NONE,
                   CtxtMenu.TAGS,
                   Menu.NONE,
                   getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                     tagsCount ) );
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
         case CtxtMenu.TOGGLE_TASK_COMPLETED:
            onCompletedClicked( info.targetView.findViewById( R.id.taskslist_listitem_check ) );
            return true;
            
         case CtxtMenu.OPEN_TASK:
            onTaskClicked( info.position );
            return true;
            
         case CtxtMenu.RENAME_TASK:
            onTaskRename( info.position );
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
            intent.putExtra( TITLE_ICON, R.drawable.icon_tag_white );
            
            final StringBuffer filter = new StringBuffer();
            
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
            
            intent.putExtra( FILTER, filter.toString() );
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
            case Dialog.BUTTON_POSITIVE:
               if ( tempSelectedSortOrder > -1
                  && tempSelectedSortOrder < SORT_ORDER_VALUES.length )
               {
                  if ( !isSameSortOrderLikeCurrent( tempSelectedSortOrder ) )
                  {
                     configuration.putInt( SORT_ORDER_IDX,
                                           tempSelectedSortOrder );
                     fillList();
                  }
               }
               else
               {
                  Log.e( TAG, "Unknown sort option selected " + which );
               }
               
               tempSelectedSortOrder = -1;
               
               break;
            
            case Dialog.BUTTON_NEGATIVE:
               tempSelectedSortOrder = -1;
               break;
            
            default :
               tempSelectedSortOrder = which;
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
   


   public void onTitleBarSearchClicked( View view )
   {
      onSearchRequested();
   }
   


   public void onTaskClicked( int pos )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW,
                                        Queries.contentUriWithId( Tasks.CONTENT_URI,
                                                                  getTask( pos ).getId() ) );
      startActivity( intent );
   }
   


   public void onTaskClicked( View view )
   {
      onTaskClicked( getListView().getPositionForView( view ) );
   }
   


   public void onCompletedClicked( View view )
   {
      final CheckBox checkBox = (CheckBox) view;
      
      if ( checkBox.isChecked() )
      {
         
      }
      else
      {
         
      }
   }
   


   private void onTaskRename( int pos )
   {
      
   }
   


   public void onListNameClicked( View view )
   {
      final TextView listNameCtrl = (TextView) view;
      
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( FILTER, RtmSmartFilterLexer.OP_LIST_LIT
         + RtmSmartFilterLexer.quotify( listNameCtrl.getText().toString() ) );
      intent.putExtra( TITLE, getString( R.string.taskslist_titlebar,
                                         listNameCtrl.getText() ) );
      intent.putExtra( TITLE_ICON, R.drawable.icon_list_white );
      
      final Bundle config = new Bundle();
      config.putBoolean( DISABLE_LIST_NAME, true );
      
      intent.putExtra( ADAPTER_CONFIG, config );
      
      startActivity( intent );
   }
   


   public void onTagClicked( View view )
   {
      final TextView tagCtrl = (TextView) view;
      
      onTagClicked( tagCtrl.getText() );
   }
   


   public void onTagClicked( CharSequence tag )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( FILTER, RtmSmartFilterLexer.OP_TAG_LIT + tag );
      intent.putExtra( TITLE, getString( R.string.taskslist_titlebar, tag ) );
      intent.putExtra( TITLE_ICON, R.drawable.icon_tag_white );
      
      final Bundle config = new Bundle();
      config.putString( UIUtils.DISABLE_TAG_EQUALS, tag.toString() );
      
      intent.putExtra( ADAPTER_CONFIG, config );
      
      startActivity( intent );
   }
   


   public void onNoteClicked( View view )
   {
      // get the ID of the note clicked
      final int pos = getListView().getPositionForView( view );
      
      final List< RtmTaskNote > notes = getTask( pos ).getNotes();
      
      String id = null;
      for ( Iterator< RtmTaskNote > i = notes.iterator(); i.hasNext()
         && id == null; )
      {
         final RtmTaskNote rtmTaskNote = i.next();
         
         if ( rtmTaskNote.getText().equals( ( (TextView) view ).getText() ) )
         {
            id = rtmTaskNote.getId();
         }
      }
      
      openNotes( pos, id );
   }
   


   public void openMultipleTags( List< String > tags )
   {
      final ArrayList< CharSequence > tmp = new ArrayList< CharSequence >();
      
      for ( String tag : tags )
      {
         tmp.add( tag );
      }
      
      new MultiChoiceDialog( this, tmp, this ).setTitle( getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                                                           tags.size() ) )
                                              .setIcon( R.drawable.icon_tag_white )
                                              .setButtonText( getString( R.string.abstaskslist_dlg_show_tags_and ) )
                                              .setButton2Text( getString( R.string.abstaskslist_dlg_show_tags_or ) )
                                              .show();
   }
   


   public void openNotes( int position, String startNoteId )
   {
      final ListTask task = getTask( position );
      final List< RtmTaskNote > notes = task.getNotes();
      
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
            intent.putExtra( Notes.TASKSERIES_ID, task.getId() );
            
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
   


   public void onSettingsChanged( int which )
   {
      onContentChanged();
   }
   


   protected boolean isListFilled()
   {
      return getListAdapter() instanceof TasksListAdapter;
   }
   


   protected boolean shouldFillList()
   {
      return !isListFilled();
   }
   


   abstract protected void fillList();
   


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
   


   protected String getSortOrder()
   {
      String sortOrder = SORT_ORDER_VALUES[ 0 ];
      
      final int sortOrderIdx = configuration.getInt( SORT_ORDER_IDX, -1 );
      
      if ( sortOrderIdx > -1 && sortOrderIdx < SORT_ORDER_VALUES.length )
      {
         sortOrder = SORT_ORDER_VALUES[ sortOrderIdx ];
      }
      
      return sortOrder;
   }
   


   protected int getSortOrderIdx()
   {
      final String sortOrder = getSortOrder();
      
      int idx = 0;
      boolean found = false;
      while ( idx < SORT_ORDER_VALUES.length && !found )
      {
         found = SORT_ORDER_VALUES[ idx ].equals( sortOrder );
         if ( !found )
            ++idx;
      }
      
      if ( !found )
      {
         idx = 0;
      }
      
      return idx;
   }
   


   private boolean isSameSortOrderLikeCurrent( int idx )
   {
      return configuration.getInt( SORT_ORDER_IDX, -1 ) == idx;
   }
   


   protected void addOptionalMenuItem( Menu menu,
                                       int id,
                                       String title,
                                       int order,
                                       int iconId,
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
      }
      else
      {
         menu.removeItem( id );
      }
   }
}
