package dev.drsoran.moloko.main.taskslist;

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
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.Tasks;


public class TasksListActivity extends ListActivity
{
   private final static String TAG = TasksListActivity.class.getSimpleName();
   
   private final static ContentUiMapper contentUiMapper = new ContentUiMapper( new String[]
                                                                               {
                                                                                Tasks._ID,
                                                                                Tasks.TASKSERIES_NAME,
                                                                                Tasks.LIST_NAME,
                                                                                Tasks.DUE_DATE,
                                                                                Tasks.PRIORITY,
                                                                                Tasks.COMPLETED_DATE },
                                                                               new int[]
                                                                               {
                                                                                R.id.taskslist_listitem_desc,
                                                                                R.id.taskslist_listitem_list_name,
                                                                                R.id.taskslist_listitem_due_date,
                                                                                R.id.taskslist_listitem_priority,
                                                                                R.id.taskslist_listitem_check } );
   
   

   /** Called when the activity is first created. */
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
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
      inflater.inflate( R.menu.taskslist_menu_options, menu );
      
      return addOptionsMenuIntents( menu );
   }
   


   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      MenuItem syncItem = (MenuItem) menu.findItem( R.id.taskslist_menu_opt_sync );
      syncItem.setEnabled( AccountPreferencesActivity.getRtmPermission( this ) != RtmAuth.Perms.nothing );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.taskslist_menu_opt_sync:
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
         MenuItem item = menu.findItem( R.id.taskslist_menu_opt_prefs );
         
         ok = item != null;
         
         if ( ok )
         {
            item.setIntent( new Intent( this, AccountPreferencesActivity.class ) );
         }
         else
         {
            Log.e( TAG, getString( R.string.log_e_resource_not_found )
               + ": taskslist_menu_opt_prefs" );
         }
      }
      
      return ok;
   }
   


   private final void refresh()
   {
      final Cursor c = managedQuery( Tasks.CONTENT_URI,
                                     contentUiMapper.PROJECTION,
                                     null,
                                     null,
                                     Tasks.PRIORITY + " ASC" );
      
      // TODO: Handle null cursor. Show error?
      if ( c != null )
      {
         final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                      R.layout.taskslist_activity_listitem,
                                                                      c,
                                                                      contentUiMapper.UI_COLUMNS,
                                                                      contentUiMapper.RESSOURCE_IDS );
         
         adapter.setViewBinder( new TaskListItemViewBinder( this, contentUiMapper ) );
         
         setListAdapter( adapter );
      }
   }
}
