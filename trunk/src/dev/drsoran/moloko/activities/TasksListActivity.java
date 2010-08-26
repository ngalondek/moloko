package dev.drsoran.moloko.activities;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class TasksListActivity extends AbstractTasksListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TasksListActivity.class.getSimpleName();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
      
      final Intent intent = getIntent();
      
      // Intent extras have precedence before
      // stored values.
      if ( intent.getExtras() != null )
         configuration.putAll( intent.getExtras() );
      else if ( savedInstanceState != null )
         configuration.putAll( savedInstanceState );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate( R.menu.taskslist_menu_options, menu );
      
      return addOptionsMenuIntents( menu );
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      // Handle item selection
      switch ( item.getItemId() )
      {
         case R.id.taskslist_menu_opt_search_task:
            onSearchRequested();
            return true;
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   protected void refresh()
   {
      final String title = configuration.getString( TITLE );
      
      if ( title != null )
      {
         setTitle( title );
      }
      else
      {
         setTitle( getString( R.string.taskslist_titlebar,
                              getString( R.string.app_name ) ) );
      }
      
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
      
      if ( client != null )
      {
         final String smartFilter = configuration.getString( Lists.FILTER );
         
         String evaluatedFilter = null;
         
         if ( smartFilter != null )
         {
            // try to evaluate the filter
            evaluatedFilter = RtmSmartFilter.evaluate( smartFilter );
            
            if ( evaluatedFilter == null )
            {
               // RETURN: evaluation failed
               return;
            }
         }
         
         final ArrayList< Task > tasks = TasksProviderPart.getTasks( client,
                                                                     evaluatedFilter,
                                                                     Tasks.PRIORITY
                                                                        + ", "
                                                                        + Tasks.TASKSERIES_NAME );
         
         // TODO: Handle null. Show error?
         if ( tasks != null )
         {
            final int flags = configuration.getInt( FLAGS );
            
            setListAdapter( new TasksListAdapter( this,
                                                  R.layout.taskslist_activity_listitem,
                                                  tasks,
                                                  flags ) );
         }
      }
   }
   


   private final boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      if ( ok )
      {
         MenuItem item = menu.findItem( R.id.taskslist_menu_opt_lists );
         
         ok = item != null;
         
         if ( ok )
         {
            item.setIntent( new Intent( Intent.ACTION_VIEW,
                                        ListOverviews.CONTENT_URI ) );
         }
      }
      
      if ( ok )
      {
         MenuItem item = menu.findItem( R.id.taskslist_menu_opt_smart_filter_test );
         
         ok = item != null;
         
         if ( ok )
         {
            item.setIntent( new Intent( this, RtmSmartFilterTestActivity.class ) );
         }
      }
      
      return ok;
   }
   
}
