package dev.drsoran.moloko.activities;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class TasksListActivity extends AbstractTasksListActivity implements
         DialogInterface.OnClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TasksListActivity.class.getSimpleName();
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = AbstractTasksListActivity.OptionsMenu.START_IDX + 1000;
      
      public final static int MENU_ORDER = AbstractTasksListActivity.OptionsMenu.MENU_ORDER - 1000;
      
      public final static int SHOW_LISTS = START_IDX + 0;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      final Intent intent = getIntent();
      
      // Intent extras have precedence before
      // stored values.
      if ( intent.getExtras() != null )
         configuration.putAll( intent.getExtras() );
      else if ( savedInstanceState != null )
         configuration.putAll( savedInstanceState );
      
      if ( shouldFillList() )
         fillList();
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      boolean ok = super.onCreateOptionsMenu( menu );
      
      if ( ok )
         menu.add( Menu.NONE,
                   OptionsMenu.SHOW_LISTS,
                   OptionsMenu.MENU_ORDER,
                   R.string.taskslist_menu_opt_lists )
             .setIcon( R.drawable.icon_list_black );
      
      return ok && addOptionsMenuIntents( menu );
   }
   


   @Override
   protected void fillList()
   {
      final String title = configuration.getString( TITLE );
      
      final int titleIconId = configuration.getInt( TITLE_ICON, -1 );
      
      if ( title != null )
      {
         UIUtils.setTitle( this, title, titleIconId );
      }
      else
      {
         UIUtils.setTitle( this, getString( R.string.taskslist_titlebar,
                                            getString( R.string.app_name ) ) );
      }
      
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
      
      if ( client != null )
      {
         final String smartFilter = configuration.getString( FILTER );
         
         String evaluatedFilter = configuration.getString( FILTER_EVALUATED );
         
         if ( smartFilter != null && evaluatedFilter == null )
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
                                                                     getSortOrder() );
         
         client.release();
         
         // TODO: Handle null. Show error?
         if ( tasks != null )
         {
            setListAdapter( new TasksListAdapter( this,
                                                  R.layout.taskslist_activity_listitem,
                                                  ListTask.fromTaskList( tasks ),
                                                  configuration.getBundle( ADAPTER_CONFIG ) ) );
         }
      }
   }
   


   private boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      final MenuItem item = menu.findItem( OptionsMenu.SHOW_LISTS );
      
      ok = item != null;
      
      if ( ok )
      {
         item.setIntent( new Intent( Intent.ACTION_VIEW,
                                     ListOverviews.CONTENT_URI ) );
      }
      
      return ok;
   }
   
}
