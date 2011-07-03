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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnGroupClickListener;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.activities.AbstractTasksListActivity.Config;
import dev.drsoran.moloko.adapters.TaskListsAdapter;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.dialogs.AddRenameListDialog;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskListsActivity extends ExpandableListActivity implements
         IOnSettingsChangedListener, OnGroupClickListener
{
   private final static String TAG = "Moloko."
      + TaskListsActivity.class.getSimpleName();
   
   
   private final class AsyncQueryLists extends
            AsyncTask< ContentResolver, Void, List< RtmListWithTaskCount > >
   {
      
      @Override
      protected List< RtmListWithTaskCount > doInBackground( ContentResolver... params )
      {
         final ContentProviderClient client = params[ 0 ].acquireContentProviderClient( ListOverviews.CONTENT_URI );
         
         if ( client != null )
         {
            final List< RtmListWithTaskCount > res = ListOverviewsProviderPart.getListsOverview( client,
                                                                                                 RtmListsProviderPart.SELECTION_EXCLUDE_DELETED );
            client.release();
            return res;
         }
         
         return null;
      }
      
      
      
      @Override
      protected void onPostExecute( List< RtmListWithTaskCount > result )
      {
         final TaskListsActivity activity = TaskListsActivity.this;
         
         activity.setListAdapter( new TaskListsAdapter( activity,
                                                        R.layout.tasklists_activity_group,
                                                        R.layout.tasklists_activity_child,
                                                        result != null
                                                                      ? result
                                                                      : Collections.< RtmListWithTaskCount > emptyList() ) );
         
         activity.findViewById( android.R.id.empty ).setVisibility( View.GONE );
         activity.getExpandableListView()
                 .setEmptyView( activity.findViewById( R.id.tasklists_activity_no_lists ) );
         
         activity.asyncQueryLists = null;
      }
   }
   
   private final Runnable queryListsRunnable = new Runnable()
   {
      public void run()
      {
         if ( asyncQueryLists != null )
         {
            Log.w( TAG, "Canceled AsyncQueryLists task." );
            asyncQueryLists.cancel( true );
         }
         
         asyncQueryLists = new AsyncQueryLists();
         asyncQueryLists.execute( getContentResolver() );
      }
   };
   
   private ContentObserver dbObserver;
   
   private AsyncQueryLists asyncQueryLists;
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      private final static int MENU_ORDER_STATIC = 10000;
      
      public final static int MENU_ORDER = MENU_ORDER_STATIC - 1;
      
      public final static int SETTINGS = START_IDX + 1;
      
      public final static int SYNC = START_IDX + 2;
   }
   
   
   protected static class CtxtMenu
   {
      public final static int OPEN_LIST = 1 << 0;
      
      public final static int EXPAND = 1 << 1;
      
      public final static int COLLAPSE = 1 << 2;
      
      public final static int DELETE = 1 << 3;
      
      public final static int RENAME = 1 << 4;
      
      public final static int MAKE_DEFAULT_LIST = 1 << 5;
      
      public final static int REMOVE_DEFAULT_LIST = 1 << 6;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tasklists_activity );
      
      registerForContextMenu( getExpandableListView() );
      getExpandableListView().setOnGroupClickListener( this );
      
      MolokoApp.get( this )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.RTM_DEFAULTLIST,
                                                   this );
      
      dbObserver = new ContentObserver( getExpandableListView().getHandler() )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            // Aggregate several calls to a single update.
            DelayedRun.run( getExpandableListView().getHandler(),
                            queryListsRunnable,
                            1000 );
         }
      };
      
      ListOverviewsProviderPart.registerContentObserver( this, dbObserver );
      
      if ( !( getExpandableListAdapter() instanceof TaskListsAdapter ) )
      {
         asyncQueryLists = new AsyncQueryLists();
         asyncQueryLists.execute( getContentResolver() );
      }
   }
   
   
   
   @Override
   protected void onStop()
   {
      super.onStop();
      
      if ( asyncQueryLists != null )
         asyncQueryLists.cancel( true );
      
      asyncQueryLists = null;
   }
   
   
   
   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getExpandableListView() );
      
      MolokoApp.get( this ).unregisterOnSettingsChangedListener( this );
      
      ListOverviewsProviderPart.unregisterContentObserver( this, dbObserver );
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings )
          .setIcon( R.drawable.ic_menu_settings )
          .setIntent( new Intent( this, MolokoPreferencesActivity.class ) );
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      UIUtils.addSyncMenuItem( this,
                               menu,
                               OptionsMenu.SYNC,
                               OptionsMenu.MENU_ORDER_STATIC - 1 );
      
      return true;
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
      
      if ( list.getLocked() == 0 && !AccountUtils.isReadOnlyAccess( this ) )
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
      
      if ( list.getId().equals( MolokoApp.getSettings().getDefaultListId() ) )
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
   public boolean onContextItemSelected( MenuItem item )
   {
      final ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.OPEN_LIST:
            openList( getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ) );
            return true;
            
         case CtxtMenu.EXPAND:
            getExpandableListView().expandGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.COLLAPSE:
            getExpandableListView().collapseGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
            return true;
            
         case CtxtMenu.DELETE:
            deleteList( getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ) );
            return true;
            
         case CtxtMenu.RENAME:
            AddRenameListDialog.newDialogWithList( this,
                                                   getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ).getRtmList() )
                               .show();
            return true;
            
         case CtxtMenu.MAKE_DEFAULT_LIST:
            MolokoApp.getSettings()
                     .setDefaultListId( getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ).getId() );
            return true;
            
         case CtxtMenu.REMOVE_DEFAULT_LIST:
            MolokoApp.getSettings()
                     .setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   
   
   
   @Override
   public boolean onChildClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id )
   {
      final Intent intent = ( (TaskListsAdapter) getExpandableListAdapter() ).getChildIntent( groupPosition,
                                                                                              childPosition );
      
      if ( intent != null )
      {
         startActivity( intent );
         return true;
      }
      else
         return super.onChildClick( parent, v, groupPosition, childPosition, id );
   }
   
   
   
   public boolean onGroupClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                long id )
   {
      openList( getRtmList( groupPosition ) );
      return true;
   }
   
   
   
   public void onGroupIndicatorClicked( View v )
   {
      final ExpandableListView listView = getExpandableListView();
      final int pos = ExpandableListView.getPackedPositionGroup( listView.getExpandableListPosition( listView.getPositionForView( v ) ) );
      
      if ( listView.isGroupExpanded( pos ) )
      {
         listView.collapseGroup( pos );
      }
      else
      {
         listView.expandGroup( pos );
      }
   }
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      getExpandableListView().getHandler().post( new Runnable()
      {
         public void run()
         {
            onContentChanged();
         }
      } );
   }
   
   
   
   private void openList( RtmListWithTaskCount rtmList )
   {
      // Check if the smart filter could be parsed. Otherwise
      // we do not fire the intent.
      if ( rtmList.isSmartFilterValid() )
      {
         final String listName = rtmList.getName();
         
         final Intent intent = new Intent( Intent.ACTION_VIEW,
                                           Tasks.CONTENT_URI );
         
         intent.putExtra( Config.TITLE,
                          getString( R.string.taskslist_titlebar, listName ) );
         intent.putExtra( Config.TITLE_ICON, R.drawable.ic_title_list );
         
         RtmSmartFilter filter = rtmList.getSmartFilter();
         
         // If we have no smart filter we use the list name as "list:" filter
         if ( filter == null )
         {
            filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_LIST_LIT
               + RtmSmartFilterLexer.quotify( listName ) );
         }
         
         intent.putExtra( Lists.LIST_NAME, rtmList.getName() );
         intent.putExtra( Config.FILTER, filter );
         
         startActivity( intent );
      }
   }
   
   
   
   private void deleteList( final RtmListWithTaskCount rtmList )
   {
      UIUtils.newDeleteElementDialog( this, rtmList.getName(), new Runnable()
      {
         public void run()
         {
            RtmListEditUtils.deleteList( TaskListsActivity.this,
                                         rtmList.getRtmList() );
         }
      },
                                      null ).show();
   }
   
   
   
   private final RtmListWithTaskCount getRtmList( int flatPos )
   {
      return (RtmListWithTaskCount) getExpandableListAdapter().getGroup( flatPos );
   }
   
}
