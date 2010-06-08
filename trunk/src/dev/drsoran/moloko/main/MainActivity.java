package dev.drsoran.moloko.main;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.prefs.AccountPreferencesActivity;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;


public class MainActivity extends ListActivity
{
   private final static String TAG = MainActivity.class.getSimpleName();
   
   private final static String[] PROJECTION_TASKSERIES =
   { TaskSeries._ID, TaskSeries.NAME, TaskSeries.LIST_ID };
   
   private final static String[] PROJECTION_LIST =
   { Lists._ID, Lists.NAME };
   
   

   /** Called when the activity is first created. */
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.main_activity );
      
      final ContentResolver contentResolver = getContentResolver();
      
      final Cursor cursorTaskSeries = contentResolver.query( TaskSeries.CONTENT_URI,
                                                             PROJECTION_TASKSERIES,
                                                             null,
                                                             null,
                                                             TaskSeries.LIST_ID );
      
      final Cursor cursorLists = contentResolver.query( Lists.CONTENT_URI,
                                                        PROJECTION_LIST,
                                                        null,
                                                        null,
                                                        Lists._ID );
      
      final CursorJoiner cursorJoiner = new CursorJoiner( cursorTaskSeries,
                                                          new String[]
                                                          { TaskSeries.LIST_ID },
                                                          cursorLists,
                                                          new String[]
                                                          { Lists._ID } );
      
      final MatrixCursor matrixCursor = new MatrixCursor( new String[]
                                                          { TaskSeries._ID,
                                                           "ts_name",
                                                           "li_name" },
                                                          cursorTaskSeries.getCount() );
      
      for ( CursorJoiner.Result joinResult : cursorJoiner )
      {
         switch ( joinResult )
         {
            case BOTH:
               final String taskseriesId = cursorTaskSeries.getString( 0 );
               final String taskseriesName = cursorTaskSeries.getString( 1 );
               final String listName = cursorLists.getString( 1 );
               
               matrixCursor.addRow( new String[]
               { taskseriesId, taskseriesName, listName } );
            default :
               break;
         }
      }
      
      cursorTaskSeries.close();
      cursorLists.close();
      
      startManagingCursor( matrixCursor );
      
      final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                   R.layout.main_activity_list_tasks_task,
                                                                   matrixCursor,
                                                                   new String[]
                                                                   {
                                                                    "ts_name",
                                                                    "li_name" },
                                                                   new int[]
                                                                   {
                                                                    R.id.main_list_tasks_task_desc,
                                                                    R.id.main_list_tasks_task_list_name } );
      
      setListAdapter( adapter );
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
   }
   


   @Override
   protected void onPause()
   {
      super.onPause();
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate( R.menu.main_menu_options, menu );
      
      return addOptionsMenuIntents( menu );
   }
   


   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      MenuItem syncItem = (MenuItem) menu.findItem( R.id.main_menu_opt_sync );
      syncItem.setEnabled( AccountPreferencesActivity.getRtmPermission( this ) != RtmAuth.Perms.nothing );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.main_menu_opt_sync:
            return true;
            
         default :
            return false;
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
            item.setIntent( new Intent( this, AccountPreferencesActivity.class ) );
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
