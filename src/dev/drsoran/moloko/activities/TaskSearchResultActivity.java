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
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.TaskSearchResultListFragment;
import dev.drsoran.moloko.fragments.listeners.ITasksSearchResultListFragmentListener;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.Intents;


public class TaskSearchResultActivity extends
         AbstractFullDetailedTasksListActivity implements
         ITasksSearchResultListFragmentListener
{
   private boolean lastQuerySucceeded;
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      initialize();
      reloadWithNewQuery( intent );
   }
   
   
   
   @Override
   protected void initializeTitle()
   {
      setTitle( getQueryFromBundle( getIntent().getExtras() ) );
   }
   
   
   
   @Override
   protected void initializeActionBar()
   {
      setStandardNavigationMode();
   }
   
   
   
   @Override
   public void onBackStackChanged()
   {
      setTitle( getQueryFromBundle( getCurrentTasksListFragmentConfiguration() ) );
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      if ( isWritableAccess() )
      {
         getSupportMenuInflater().inflate( R.menu.tasksearchresult_activity_rwd,
                                           menu );
      }
      else
      {
         getSupportMenuInflater().inflate( R.menu.tasksearchresult_activity,
                                           menu );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      super.onPrepareOptionsMenu( menu );
      menu.setGroupVisible( R.id.menu_group_tasksearchresult_query_succeeded,
                            lastQuerySucceeded );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_clear_search_history:
            getRecentSuggestions().clearHistory();
            
            Toast.makeText( this,
                            R.string.tasksearchresult_toast_history_cleared,
                            Toast.LENGTH_SHORT ).show();
            return true;
            
         case R.id.menu_add_list:
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
   protected void onOpenChoosenTags( List< String > tags,
                                     String logicalOperation )
   {
      startActivity( Intents.createOpenTagsIntent( this, tags, logicalOperation ) );
   }
   
   
   
   private SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
   
   
   
   private String getQueryFromBundle( Bundle bundle )
   {
      return bundle.getString( SearchManager.QUERY );
   }
   
   
   
   private void reloadWithNewQuery( Intent intent )
   {
      final Bundle newConfig = getCurrentTasksListFragmentConfiguration();
      newConfig.putString( SearchManager.QUERY,
                           getQueryFromBundle( intent.getExtras() ) );
      
      reloadTasksListWithConfiguration( newConfig );
   }
   
   
   
   @Override
   protected Fragment createTasksListFragment( Bundle config )
   {
      return TaskSearchResultListFragment.newInstance( config );
   }
}
