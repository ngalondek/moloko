/*
 * Copyright (c) 2012 Ronny Röhricht
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

import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.listeners.ITasksSearchResultListFragmentListener;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.MolokoMenuItemBuilder;


public class TaskSearchResultActivity extends
         AbstractFullDetailedTasksListActivity implements
         ITasksSearchResultListFragmentListener
{
   private static class OptionsMenu
   {
      public final static int ADD_LIST = R.id.menu_add_list;
      
      public final static int CLEAR_HISTORY = R.id.menu_clear_search_history;
   }
   
   @InstanceState( key = SearchManager.QUERY, defaultValue = InstanceState.NULL )
   private String lastQuery;
   
   private boolean lastQuerySucceeded = true;
   
   
   
   public TaskSearchResultActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskSearchResultActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setQueryAsActivityTitle();
   }
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      setQueryAsActivityTitle();
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final MolokoMenuItemBuilder builder = new MolokoMenuItemBuilder();
      
      builder.setItemId( OptionsMenu.ADD_LIST )
             .setTitle( getString( R.string.tasksearchresult_menu_add_smart_list ) )
             .setIconId( R.drawable.ic_menu_add_list )
             .setOrder( MenuCategory.CONTAINER )
             .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_IF_ROOM )
             .setShow( lastQuerySucceeded
                && AccountUtils.isWriteableAccess( this ) )
             .build( menu );
      
      builder.setItemId( OptionsMenu.CLEAR_HISTORY )
             .setTitle( getString( R.string.tasksearchresult_menu_opt_clear_history_title ) )
             .setIconId( R.drawable.ic_menu_delete )
             .setOrder( MenuCategory.ALTERNATIVE )
             .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_NEVER )
             .setShow( true )
             .build( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.CLEAR_HISTORY:
            getRecentSuggestions().clearHistory();
            
            Toast.makeText( this,
                            R.string.tasksearchresult_toast_history_cleared,
                            Toast.LENGTH_SHORT ).show();
            return true;
            
         case OptionsMenu.ADD_LIST:
            showAddListDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onQuerySucceeded( String queryString )
   {
      getRecentSuggestions().saveRecentQuery( queryString, null );
      
      lastQuerySucceeded = true;
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public void onQueryFailed( String queryString )
   {
      lastQuerySucceeded = false;
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public void onOpenList( int pos, String listId )
   {
      startActivity( Intents.createOpenListIntentById( this, listId, null ) );
   }
   
   
   
   @Override
   public void onOpenLocation( int pos, String locationId )
   {
      startActivity( Intents.createOpenLocationIntentByName( this,
                                                             getTask( pos ).getLocationName() ) );
   }
   
   
   
   @Override
   protected void onOpenChoosenTags( List< String > tags,
                                     String logicalOperation )
   {
      startActivity( Intents.createOpenTagsIntent( this, tags, logicalOperation ) );
   }
   
   
   
   private void setQueryAsActivityTitle()
   {
      if ( !TextUtils.isEmpty( lastQuery ) )
      {
         // TODO:
         // setActivityTitle( lastQuery );
      }
   }
   
   
   
   private final SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
}
