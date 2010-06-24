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
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.main.lists.TaskListsActivity;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.Tasks;


public class TasksListActivity extends ListActivity
{
   private final static String TAG = TasksListActivity.class.getSimpleName();
   
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
                                                                                R.id.taskslist_listitem_list_name,
                                                                                R.id.taskslist_listitem_due_date,
                                                                                R.id.taskslist_listitem_priority,
                                                                                R.id.taskslist_listitem_check } );
   
   static
   {
      assert ( contentUiMapper.UI_COLUMNS.length == contentUiMapper.RESSOURCE_IDS.length );
   }
   
   private final Bundle configuration = new Bundle();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.taskslist_activity );
      
      final Intent intent = getIntent();
      
      if ( intent.getExtras() != null )
         configuration.putAll( intent.getExtras() );
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
   


   private final boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      if ( ok )
      {
         MenuItem item = menu.findItem( R.id.taskslist_menu_opt_lists );
         
         ok = item != null;
         
         if ( ok )
         {
            item.setIntent( new Intent( this, TaskListsActivity.class ) );
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
      String selection = null;
      
      if ( configuration.containsKey( Tasks.LIST_NAME ) )
         selection = Tasks.LIST_NAME + " = '"
            + configuration.getString( Tasks.LIST_NAME ) + "'";
      
      final Cursor c = managedQuery( Tasks.CONTENT_URI,
                                     contentUiMapper.PROJECTION,
                                     selection,
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
         
         adapter.setViewBinder( new TaskListItemViewBinder( this,
                                                            contentUiMapper ) );
         
         setListAdapter( adapter );
      }
   }
}
