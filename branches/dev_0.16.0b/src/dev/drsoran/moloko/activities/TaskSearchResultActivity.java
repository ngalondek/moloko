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
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.text.Html;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends
         AbstractFullDetailedTasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskSearchResultActivity.class.getSimpleName();
   
   
   private static class OptionsMenu
   {
      public final static int CLEAR_HISTORY = R.id.menu_clear_search_history;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      final Intent smartFilterIntent = transformQueryIntentToSmartFilterIntent( getIntent() );
      
      if ( smartFilterIntent != null )
         setIntent( smartFilterIntent );
      
      super.onCreate( savedInstanceState );
   }
   


   @Override
   protected void onNewIntent( Intent intent )
   {
      final Intent smartFilterIntent = transformQueryIntentToSmartFilterIntent( intent );
      
      if ( smartFilterIntent != null )
         reloadTasksListWithConfiguration( smartFilterIntent.getExtras() );
      else
         super.onNewIntent( intent );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.CLEAR_HISTORY,
                                   getString( R.string.tasksearchresult_menu_opt_clear_history_title ),
                                   Menu.CATEGORY_ALTERNATIVE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_delete,
                                   MenuItem.SHOW_AS_ACTION_NEVER,
                                   true );
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.CLEAR_HISTORY:
            getRecentSuggestions().clearHistory();
            // TODO: Show toast that history was cleared
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   private Intent transformQueryIntentToSmartFilterIntent( Intent queryIntent )
   {
      Intent smartFilterIntent = null;
      
      if ( Intent.ACTION_SEARCH.equals( queryIntent.getAction() ) )
      {
         final RtmSmartFilter filter = new RtmSmartFilter( getConfiguredQueryString( queryIntent ) );
         
         // Try to evaluate the query
         // Collect tokens for the quick add task fragment
         final String evalQuery = filter.getEvaluatedFilterString( true );
         
         if ( evalQuery != null )
         {
            getRecentSuggestions().saveRecentQuery( filter.getFilterString(),
                                                    null );
            
            smartFilterIntent = Intents.createSmartFilterIntent( this,
                                                                 filter,
                                                                 getString( R.string.tasksearchresult_titlebar,
                                                                            filter.getFilterString() ) );
         }
         else
         {
            showCustomError( Html.fromHtml( String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
                                                           filter.getFilterString() ) ) );
         }
      }
      
      return smartFilterIntent;
   }
   


   private String getConfiguredQueryString( Intent queryIntent )
   {
      return queryIntent.getStringExtra( SearchManager.QUERY );
   }
   


   private final SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
}
