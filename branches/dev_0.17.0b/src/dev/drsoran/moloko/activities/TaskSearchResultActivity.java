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

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.FullDetailedTasksListFragment;
import dev.drsoran.moloko.fragments.TaskSearchResultFailedFragment;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends
         AbstractFullDetailedTasksListActivity
{
   @InstanceState( key = "succeeded_query_stack",
                   defaultValue = InstanceState.NO_DEFAULT )
   private ArrayList< String > succeededQueryStack;
   
   private boolean lastQuerySucceeded;
   
   
   
   public TaskSearchResultActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskSearchResultActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      evaluateAndStoreQuery();
      getSupportFragmentManager().addOnBackStackChangedListener( this );
      
      super.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   protected void onDestroy()
   {
      getSupportFragmentManager().removeOnBackStackChangedListener( this );
      super.onDestroy();
   }
   
   
   
   @Override
   public void onBackStackChanged()
   {
      setTitle( popSucceededQuery() );
   }
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      final String lastSucceededQuery = getLastSucceededQuery();
      if ( !lastSucceededQuery.equalsIgnoreCase( getQueryFromIntent() ) )
      {
         evaluateAndStoreQuery();
         // ??
         reloadWithNewQuery( intent );
      }
   }
   
   
   
   private boolean queryHasChanged( RtmSmartFilter oldFilter )
   {
      return ( oldFilter != null && filter == null )
         || ( oldFilter == null && filter != null )
         || ( oldFilter != null && filter != null && !oldFilter.equals( filter ) );
   }
   
   
   
   @Override
   protected void initializeTitle()
   {
      setTitle( getQueryFromIntent() );
   }
   
   
   
   @Override
   protected void initializeActionBar()
   {
      setStandardNavigationMode();
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
   
   
   
   private SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
   
   
   
   private void evaluateAndStoreQuery()
   {
      final RtmSmartFilter filter = evaluateRtmSmartFilter();
      if ( filter != null )
      {
         onQuerySucceeded();
      }
      else
      {
         onQueryFailed();
      }
   }
   
   
   
   private RtmSmartFilter evaluateRtmSmartFilter()
   {
      RtmSmartFilter filter = new RtmSmartFilter( getQueryFromIntent() );
      
      // Collect tokens for the quick add task fragment
      final String evalQuery = filter.getEvaluatedFilterString( true );
      
      if ( evalQuery == null )
      {
         filter = null;
      }
      
      return filter;
   }
   
   
   
   private void reloadWithNewQuery( Intent intent )
   {
      final Bundle newConfig = getCurrentTasksListFragmentConfiguration();
      putTransformedQueryFromSmartFilter( newConfig );
      
      reloadTasksListWithConfiguration( newConfig );
   }
   
   
   
   private Bundle putTransformedQueryFromSmartFilter( Bundle config )
   {
      config.putParcelable( Intents.Extras.KEY_FILTER, filter );
      return config;
   }
   
   
   
   private String getQueryFromIntent()
   {
      return getIntent().getExtras().getString( SearchManager.QUERY );
   }
   
   
   
   private String getLastSucceededQuery()
   {
      return succeededQueryStack.get( succeededQueryStack.size() - 1 );
   }
   
   
   
   private void pushSucceededQuery( String query )
   {
      succeededQueryStack.add( query );
   }
   
   
   
   private String popSucceededQuery()
   {
      return succeededQueryStack.remove( succeededQueryStack.size() - 1 );
   }
   
   
   
   private void onQuerySucceeded()
   {
      final String queryString = getQueryFromIntent();
      getRecentSuggestions().saveRecentQuery( queryString, null );
      
      pushSucceededQuery( queryString );
      lastQuerySucceeded = true;
      
      invalidateOptionsMenu();
   }
   
   
   
   private void onQueryFailed()
   {
      lastQuerySucceeded = false;
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   protected Fragment createTasksListFragment( Bundle config )
   {
      if ( lastQuerySucceeded )
      {
         putTransformedQueryFromSmartFilter( config );
         return FullDetailedTasksListFragment.newInstance( config );
      }
      else
      {
         config = new Bundle( 1 );
         config.putString( SearchManager.QUERY, getQueryFromIntent() );
         
         return TaskSearchResultFailedFragment.newInstance( config );
      }
   }
}
