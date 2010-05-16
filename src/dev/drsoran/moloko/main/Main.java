package dev.drsoran.moloko.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.prefs.Preferences;
import dev.drsoran.moloko.service.AsyncRtmService;
import dev.drsoran.moloko.util.ResultCallback;


public class Main extends ListActivity
{
   private final static String TAG = Main.class.getSimpleName();
   
   private final static String ITEM_KEY = "ITEM_KEY";
   
   private final ArrayList< HashMap< String, String > > tasks =
      new ArrayList< HashMap< String, String > >();
   
   private SimpleAdapter adapter = null;
   
   private AsyncRtmService asyncRtmService = null;
   
   

   /** Called when the activity is first created. */
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.main );
      
      asyncRtmService =
         new AsyncRtmService( this,
                              Preferences.getRtmApplicationInfo( this ),
                              Preferences.getRtmPermission( this ) );
      
      adapter =
         new SimpleAdapter( this,
                            tasks,
                            R.layout.main_list_tasks_task,
                            new String[]
                            { ITEM_KEY },
                            new int[]
                            { R.id.main_list_tasks_task_desc } );
      
      setListAdapter( adapter );
   }
   


   @Override
   protected void onDestroy()
   {
      asyncRtmService.shutdown();
      
      super.onDestroy();
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate( R.menu.main_menu_options, menu );
      
      return addOptionsMenuIntents( menu );
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.main_menu_opt_sync:
            asyncRtmService.task_getList( null,
                                          null,
                                          null,
                                          new ResultCallback< RtmTasks >()
                                          {
                                             public void run()
                                             {
                                                onTaskGetList( result,
                                                               exception );
                                             }
                                          } );
         default :
            return false;
      }
   }
   


   private void onTaskGetList( RtmTasks result, Exception exception )
   {
      if ( exception == null )
      {
         tasks.clear();
         
         final List< RtmTaskList > lists = result.getLists();
         
         // for each task list
         for ( RtmTaskList rtmTaskList : lists )
         {
            final List< RtmTaskSeries > receivedTasks = rtmTaskList.getSeries();
            
            // for each task
            for ( RtmTaskSeries rtmTaskSeries : receivedTasks )
            {
               HashMap< String, String > task = new HashMap< String, String >();
               task.put( ITEM_KEY, rtmTaskSeries.getName() );
               tasks.add( task );
            }
         }
         
         adapter.notifyDataSetChanged();
      }
      else
      {
         new AlertDialog.Builder( this ).setTitle( getString( R.string.err_error ) )
            .setMessage( "Tasks konnten nicht geholt werden" )
            .show();
      }
   }
   


   private final boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      if ( ok )
      {
         MenuItem item = menu.findItem( R.id.main_menu_opt_prefs );
         
         ok = item != null;
         
         if ( ok )
         {
            item.setIntent( new Intent( this, Preferences.class ) );
         }
         else
         {
            Log.e( TAG, getString( R.string.log_e_resource_not_found )
               + ": menu_opt_main_prefs" );
         }
      }
      
      return ok;
   }
}
