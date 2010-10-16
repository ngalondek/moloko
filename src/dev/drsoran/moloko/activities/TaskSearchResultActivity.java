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

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends TasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TaskSearchResultActivity.class.getSimpleName();
   
   private final static String QUERY_NOT_EVALUABLE = "query_not_evaluable";
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = TasksListActivity.OptionsMenu.START_IDX + 1000;
      
      public final static int MENU_ORDER = TasksListActivity.OptionsMenu.MENU_ORDER;
      
      public final static int NEW_SEARCH = START_IDX + 0;
      
      public final static int CLEAR_HISTORY = START_IDX + 1;
   }
   
   

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      menu.removeItem( TasksListActivity.OptionsMenu.SHOW_LISTS );
      
      menu.add( Menu.NONE,
                OptionsMenu.NEW_SEARCH,
                OptionsMenu.MENU_ORDER,
                R.string.menu_opt_search_task_title )
          .setIcon( R.drawable.ic_menu_search );
      
      menu.add( Menu.NONE,
                OptionsMenu.CLEAR_HISTORY,
                OptionsMenu.MENU_ORDER,
                R.string.tasksearchresult_menu_opt_clear_history_title )
          .setIcon( R.drawable.ic_menu_delete );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      // Handle item selection
      switch ( item.getItemId() )
      {
         case OptionsMenu.NEW_SEARCH:
            onSearchRequested();
            return true;
         case OptionsMenu.CLEAR_HISTORY:
            getRecentSuggestions().clearHistory();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   @Override
   protected void fillList()
   {
      if ( getIntent().getExtras().containsKey( QUERY_NOT_EVALUABLE ) )
      {
         UIUtils.setTitle( this,
                           getString( R.string.tasksearchresult_titlebar_error,
                                      getIntent().getExtras()
                                                 .getString( QUERY_NOT_EVALUABLE ),
                                      R.drawable.ic_title_error ) );
      }
      else
      {
         super.fillList();
      }
   }
   


   @Override
   protected void handleIntent( Intent intent )
   {
      if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) )
      {
         getIntent().getExtras().clear();
         
         final String query = intent.getStringExtra( SearchManager.QUERY );
         
         // try to evaluate the query
         final String evalQuery = RtmSmartFilter.evaluate( query, true );
         
         if ( evalQuery != null )
         {
            getRecentSuggestions().saveRecentQuery( query, null );
            
            // we put the query string as smart filter cause
            // smart lists are nothing else than saved searches.
            //
            // Tag the filter as already evaluated.
            getIntent().getExtras().putString( FILTER_EVALUATED, evalQuery );
            getIntent().getExtras()
                       .putString( TITLE,
                                   getString( R.string.tasksearchresult_titlebar,
                                              query ) );
         }
         else
         {
            getIntent().getExtras().putString( QUERY_NOT_EVALUABLE, query );
         }
         
         fillList();
      }
   }
   


   private final SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
}
