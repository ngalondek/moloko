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
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends TasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskSearchResultActivity.class.getSimpleName();
   
   
   private final static class OptionsMenu
   {
      public final static int CLEAR_HISTORY = R.id.menu_clear_search_history;
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      menu.add( Menu.NONE,
                OptionsMenu.CLEAR_HISTORY,
                Menu.CATEGORY_ALTERNATIVE,
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
         case OptionsMenu.CLEAR_HISTORY:
            getRecentSuggestions().clearHistory();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   protected void configureByIntent( Intent intent )
   {
      // TODO: Repair
      if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) )
      {
         final RtmSmartFilter filter = new RtmSmartFilter( intent.getStringExtra( SearchManager.QUERY ) );
         
         // try to evaluate the query
         final String evalQuery = filter.getEvaluatedFilterString( true );
         
         if ( evalQuery != null )
         {
            getRecentSuggestions().saveRecentQuery( filter.getFilterString(),
                                                    null );
            
            // configureTitleBar( TitleBarLayout.BUTTON_ADD_LIST
            // | TitleBarLayout.BUTTON_ADD_TASK | TitleBarLayout.BUTTON_HOME
            // | TitleBarLayout.BUTTON_SEARCH, filter );
            
            final Intent newIntent = Intents.createSmartFilterIntent( this,
                                                                      filter,
                                                                      getString( R.string.tasksearchresult_titlebar,
                                                                                 filter.getFilterString() ),
                                                                      -1 );
            setIntent( newIntent );
            // super.handleIntent( newIntent );
         }
         else
         {
            UIUtils.setTitle( this,
                              getString( R.string.tasksearchresult_titlebar_error ),
                              R.drawable.ic_title_error );
            
            // configureTitleBar( TitleBarLayout.BUTTON_HOME
            // | TitleBarLayout.BUTTON_SEARCH, null );
            
            // showError( Html.fromHtml( String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
            // filter.getFilterString() ) ) );
         }
      }
   }
   
   
   
   private final SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
   
   
   
   private final void configureTitleBar( int buttonMask,
                                         RtmSmartFilter addSmartListFilter )
   {
      // final TitleBarLayout titleBarLayout = (TitleBarLayout) findViewById( R.id.app_title_bar );
      // titleBarLayout.setButtonsVisible( buttonMask );
   }
}
