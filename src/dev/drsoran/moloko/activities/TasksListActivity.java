package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.Tasks;


public class TasksListActivity extends ListActivity implements OnClickListener
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
                                                                                R.id.taskslist_listitem_btn_list_name,
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
   


   @Override
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
      
      boolean hasListName = configuration.containsKey( Tasks.LIST_NAME );
      
      if ( hasListName )
         selection = Tasks.LIST_NAME + " = '"
            + configuration.getString( Tasks.LIST_NAME ) + "'";
      
      final Cursor c = managedQuery( Tasks.CONTENT_URI,
                                     contentUiMapper.PROJECTION,
                                     selection,
                                     null,
                                     Tasks.PRIORITY + ", "
                                        + Tasks.TASKSERIES_NAME );
      
      // TODO: Handle null cursor. Show error?
      if ( c != null )
      {
         final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                      R.layout.taskslist_activity_listitem,
                                                                      c,
                                                                      contentUiMapper.UI_COLUMNS,
                                                                      contentUiMapper.RESSOURCE_IDS );
         
         int flags = 0x0;
         
         if ( hasListName )
            // If we have been started with a concrete list name, then
            // we do not need to click it. Otherwise we would call the
            // same list again.
            flags |= TaskListItemViewBinder.NO_CLICKABLE_LIST_NAME;
         
         adapter.setViewBinder( new TaskListItemViewBinder( this,
                                                            contentUiMapper,
                                                            flags ) );
         
         setListAdapter( adapter );
      }
   }
   
}
