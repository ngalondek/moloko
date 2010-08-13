package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.search.TasksSearchRecentSuggestionsProvider;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends ListActivity implements
         OnClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TaskSearchResultActivity.class.getSimpleName();
   
   private final static ContentUiMapper contentUiMapper = new ContentUiMapper( new String[]
                                                                               {
                                                                                Tasks._ID,
                                                                                Tasks.HAS_DUE_TIME,
                                                                                Tasks.TASKSERIES_NAME,
                                                                                Tasks.LIST_NAME,
                                                                                Tasks.DUE_DATE,
                                                                                Tasks.PRIORITY,
                                                                                Tasks.COMPLETED_DATE },
                                                                               new String[]
                                                                               {
                                                                                Tasks.TASKSERIES_NAME,
                                                                                Tasks.LIST_NAME,
                                                                                Tasks.DUE_DATE,
                                                                                Tasks.PRIORITY,
                                                                                Tasks.COMPLETED_DATE },
                                                                               new int[]
                                                                               {
                                                                                R.id.taskslist_listitem_desc,
                                                                                R.id.taskslist_listitem_btn_list_name,
                                                                                R.id.taskslist_listitem_due_date,
                                                                                R.id.taskslist_listitem_priority,
                                                                                R.id.taskslist_listitem_check } );
   
   private final Bundle configuration = new Bundle();
   
   

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
   protected void onResume()
   {
      super.onResume();
      
      refresh();
   }
   


   @Override
   protected void onNewIntent( Intent intent )
   {
      setIntent( intent );
      handleIntent( intent );
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      outState.putAll( configuration );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      if ( state != null )
      {
         configuration.clear();
         configuration.putAll( state );
         
         refresh();
      }
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
   


   public void onClick( View v )
   {
      // List name has been clicked
      if ( v.getId() == R.id.taskslist_listitem_btn_list_name )
      {
         final Button listNameBtn = (Button) v;
         
         final Intent intent = new Intent( Intent.ACTION_VIEW,
                                           Tasks.CONTENT_URI );
         intent.putExtra( Tasks.LIST_NAME, listNameBtn.getText() );
         
         startActivity( intent );
      }
   }
   


   private void handleIntent( Intent intent )
   {
      if ( Intent.ACTION_SEARCH.equals( intent.getAction() ) )
      {
         final String query = intent.getStringExtra( SearchManager.QUERY );
         
         // try to evaluate the query
         final String evalQuery = RtmSmartFilter.evaluate( query );
         
         if ( query != null )
         {
            getRecentSuggestions().saveRecentQuery( query, null );
            
            configuration.clear();
            
            // we put the query string as smart filter cause
            // smart lists are nothing else than saved searches.
            configuration.putString( Lists.FILTER, evalQuery );
            configuration.putString( Tasks.LIST_NAME, query );
            
            refresh();
         }
         else
         {
            setTitle( R.string.tasksearchresult_titlebar_error );
         }
      }
   }
   


   private final void refresh()
   {
      final String listName = configuration.getString( Tasks.LIST_NAME );
      final String smartFilter = configuration.getString( Lists.FILTER );
      
      final Cursor c = managedQuery( Tasks.CONTENT_URI,
                                     contentUiMapper.getProjectionArray(),
                                     smartFilter,
                                     null,
                                     Tasks.PRIORITY + ", "
                                        + Tasks.TASKSERIES_NAME );
      
      // TODO: Handle null cursor. Show error?
      if ( c != null )
      {
         final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                      R.layout.taskslist_activity_listitem,
                                                                      c,
                                                                      contentUiMapper.getUiColumnsArray(),
                                                                      contentUiMapper.RESSOURCE_IDS );
         
         adapter.setViewBinder( new TasksListItemViewBinder( this,
                                                             contentUiMapper,
                                                             0 ) );
         
         setListAdapter( adapter );
         
         setTitle( getString( R.string.tasksearchresult_titlebar, listName ) );
      }
   }
   


   private final SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
}
