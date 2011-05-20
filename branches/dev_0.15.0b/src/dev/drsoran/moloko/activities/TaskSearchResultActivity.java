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
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.layouts.TitleBarLayout;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends TasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskSearchResultActivity.class.getSimpleName();
   
   
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
   protected void handleIntent( Intent intent )
   {
      if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) )
      {
         final RtmSmartFilter filter = new RtmSmartFilter( intent.getStringExtra( SearchManager.QUERY ) );
         
         // try to evaluate the query
         final String evalQuery = filter.getEvaluatedFilterString( true );
         
         if ( evalQuery != null )
         {
            getRecentSuggestions().saveRecentQuery( filter.getFilterString(),
                                                    null );
            
            configureTitleBar( TitleBarLayout.BUTTON_ADD_LIST
               | TitleBarLayout.BUTTON_ADD_TASK | TitleBarLayout.BUTTON_HOME
               | TitleBarLayout.BUTTON_SEARCH, filter );
            
            final Intent newIntent = Intents.createSmartFilterIntent( this,
                                                                      filter,
                                                                      getString( R.string.tasksearchresult_titlebar,
                                                                                 filter.getFilterString() ),
                                                                      -1 );
            setIntent( newIntent );
            super.handleIntent( newIntent );
         }
         else
         {
            UIUtils.setTitle( this,
                              getString( R.string.tasksearchresult_titlebar_error ),
                              R.drawable.ic_title_error );
            
            configureTitleBar( TitleBarLayout.BUTTON_HOME
               | TitleBarLayout.BUTTON_SEARCH, null );
            
            final View wrongSyntaxView = findViewById( R.id.tasksearchresult_wrong_syntax );
            final TextView text = (TextView) wrongSyntaxView.findViewById( R.id.title_with_text_text );
            
            final String msgStr = String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
                                                 filter.getFilterString() );
            final CharSequence msg = Html.fromHtml( msgStr );
            
            text.setText( msg );
            
            clearList( wrongSyntaxView );
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
      final TitleBarLayout titleBarLayout = (TitleBarLayout) findViewById( R.id.app_title_bar );
      titleBarLayout.setButtonsVisible( buttonMask );
      titleBarLayout.setAddSmartListFilter( addSmartListFilter );
   }
}
