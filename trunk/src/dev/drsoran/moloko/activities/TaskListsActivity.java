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

import java.util.HashMap;

import android.app.ExpandableListActivity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnGroupClickListener;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskListsActivity extends ExpandableListActivity implements
         IOnSettingsChangedListener, OnGroupClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TaskListsActivity.class.getSimpleName();
   
   private final Runnable queryListsRunnable = new Runnable()
   {
      public void run()
      {
         TaskListsActivity.this.queryLists();
      }
   };
   
   private ContentObserver dbObserver;
   
   
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
      
      public final static int MAKE_DEFAULT_LIST = 1 << 3;
      
      public final static int REMOVE_DEFAULT_LIST = 1 << 4;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tasklists_activity );
      
      registerForContextMenu( getExpandableListView() );
      getExpandableListView().setOnGroupClickListener( this );
      
      MolokoApp.getSettings()
               .registerOnSettingsChangedListener( Settings.SETTINGS_RTM_DEFAULTLIST,
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
         queryLists();
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getExpandableListView() );
      
      MolokoApp.getSettings().unregisterOnSettingsChangedListener( this );
      
      ListOverviewsProviderPart.unregisterContentObserver( this, dbObserver );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings )
          .setIcon( R.drawable.icon_settings_black )
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
      new Handler().post( new Runnable()
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
         
         intent.putExtra( AbstractTasksListActivity.TITLE,
                          getString( R.string.taskslist_titlebar, listName ) );
         intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                          R.drawable.icon_list_white );
         
         RtmSmartFilter filter = rtmList.getSmartFilter();
         
         // If we have no smart filter we use the list name as "list:" filter
         if ( filter == null )
         {
            filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_LIST_LIT
               + RtmSmartFilterLexer.quotify( listName ) );
            
            assert ( filter.getEvaluatedFilterString() != null );
            
            // We have a non-smart list. So we disable clicking the list name
            // cause we have no tasks from different lists in the result.
            final Bundle config = new Bundle();
            config.putBoolean( AbstractTasksListActivity.DISABLE_LIST_NAME,
                               true );
            
            intent.putExtra( AbstractTasksListActivity.ADAPTER_CONFIG, config );
         }
         
         intent.putExtra( AbstractTasksListActivity.FILTER_EVALUATED,
                          filter.getEvaluatedFilterString() );
         
         startActivity( intent );
      }
   }
   


   private void queryLists()
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( ListOverviews.CONTENT_URI );
      
      if ( client != null )
      {
         setListAdapter( new TaskListsAdapter( this,
                                               R.layout.tasklists_activity_group,
                                               R.layout.tasklists_activity_child,
                                               ListOverviewsProviderPart.getListsOverview( client,
                                                                                           null ) ) );
         client.release();
      }
   }
   


   private final RtmListWithTaskCount getRtmList( int flatPos )
   {
      return (RtmListWithTaskCount) getExpandableListAdapter().getGroup( flatPos );
   }
   
}
