package dev.drsoran.moloko.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
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
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.taskslist_activity );
      
      if ( savedInstanceState != null )
         configuration.putAll( savedInstanceState );
      
      handleIntent( getIntent() );
   }
   


   @Override
   protected void onNewIntent( Intent intent )
   {
      setIntent( intent );
      handleIntent( intent );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate( R.menu.tasksearchresult_menu_options, menu );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      // Handle item selection
      switch ( item.getItemId() )
      {
         case R.id.tasksearchresults_menu_opt_search_task:
            onSearchRequested();
            return true;
         case R.id.tasksearchresults_menu_opt_clear_history:
            getRecentSuggestions().clearHistory();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   private void handleIntent( Intent intent )
   {
      if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) )
      {
         configuration.clear();
         
         final String query = intent.getStringExtra( SearchManager.QUERY );
         
         // try to evaluate the query
         final String evalQuery = RtmSmartFilter.evaluate( query );
         
         if ( evalQuery != null )
         {
            getRecentSuggestions().saveRecentQuery( query, null );
            
            // we put the query string as smart filter cause
            // smart lists are nothing else than saved searches.
            //
            // Tag the filter as already evaluated.
            configuration.putString( FILTER_EVALUATED, evalQuery );
            configuration.putString( TITLE,
                                     getString( R.string.tasksearchresult_titlebar,
                                                query ) );
         }
         else
         {
            configuration.putString( QUERY_NOT_EVALUABLE, query );
         }
      }
   }
   


   protected void refresh()
   {
      if ( configuration.containsKey( QUERY_NOT_EVALUABLE ) )
      {
         UIUtils.setTitle( this,
                           getString( R.string.tasksearchresult_titlebar_error,
                                      configuration.getString( QUERY_NOT_EVALUABLE ),
                                      R.drawable.icon_sad_face_white ) );
      }
      else
      {
         super.refresh();
      }
   }
   


   private final SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
}
