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
import dev.drsoran.moloko.prefs.Preferences;
import dev.drsoran.provider.Rtm.Tasks;


public class Main extends ListActivity
{
   private final static String TAG = Main.class.getSimpleName();
   
   private final static String[] PROJECTION = new String[]
   { Tasks._ID, Tasks.COMPLETED_DATE };
   
   

   /** Called when the activity is first created. */
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.main );
      
      final Cursor cursor = managedQuery( Tasks.CONTENT_URI,
                                          PROJECTION,
                                          null,
                                          null,
                                          Tasks.DEFAULT_SORT_ORDER );
      
      SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                             R.layout.main_list_tasks_task,
                                                             cursor,
                                                             new String[]
                                                             { Tasks.COMPLETED_DATE },
                                                             new int[]
                                                             { R.id.main_list_tasks_task_desc } );
      
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
      syncItem.setEnabled( Preferences.getRtmPermission( this ) != RtmAuth.Perms.nothing );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.main_menu_opt_sync:
            onTaskGetList();
         default :
            return false;
      }
   }
   


   private void onTaskGetList()
   {
      
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
