package dev.drsoran.moloko.main;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.prefs.AccountPreferencesActivity;
import dev.drsoran.provider.Rtm.Tasks;


public class MainActivity extends ListActivity
{
   private final static String TAG = MainActivity.class.getSimpleName();
   
   private final static String[] PROJECTION =
   { Tasks._ID, Tasks.TASKSERIES_NAME, Tasks.LIST_NAME, Tasks.LOCATION_ID };
   
   

   /** Called when the activity is first created. */
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.main_activity );
      
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
      
      refresh();
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
            refresh();
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
   


   private final void refresh()
   {
      final Cursor c = managedQuery( Tasks.CONTENT_URI,
                                     PROJECTION,
                                     null,
                                     null,
                                     null );
      
      if ( c != null )
      {
         final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                      R.layout.main_activity_list_tasks_task,
                                                                      c,
                                                                      new String[]
                                                                      {
                                                                       Tasks.TASKSERIES_NAME,
                                                                       Tasks.LIST_NAME },
                                                                      new int[]
                                                                      {
                                                                       R.id.main_list_tasks_task_desc,
                                                                       R.id.main_list_tasks_task_list_name } );
         
         setListAdapter( adapter );
      }
   }
}
