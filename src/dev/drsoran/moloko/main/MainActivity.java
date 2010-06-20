package dev.drsoran.moloko.main;

import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.prefs.AccountPreferencesActivity;
import dev.drsoran.provider.Rtm.Tasks;


public class MainActivity extends ListActivity
{
   private final static String TAG = MainActivity.class.getSimpleName();
   
   private final static String[] PROJECTION =
   { Tasks._ID, Tasks.TASKSERIES_NAME, Tasks.LIST_NAME, Tasks.DUE_DATE,
    Tasks.PRIORITY };
   
   
   private final class TaskViewBinder implements ViewBinder
   {
      public boolean setViewValue( View view, Cursor cursor, int columnIndex )
      {
         switch ( columnIndex )
         {
            // +1 due to _id
            case 3:
               // due date
               final TextView dueField = (TextView) view;
               
               if ( !cursor.isNull( columnIndex ) )
                  dueField.setText( DateFormat.getDateFormat( MainActivity.this )
                                              .format( new Date( cursor.getLong( columnIndex ) ) ) );
               
               else
                  dueField.setText( "" );
               
               return true;
               
            case 4:
               // priority
               
               final Button prioBar = (Button) view;
               
               switch ( RtmTask.convertPriority( cursor.getString( columnIndex ) ) )
               {
                  case High:
                     prioBar.setBackgroundResource( R.color.priority_1 );
                     return true;
                  case Medium:
                     prioBar.setBackgroundResource( R.color.priority_2 );
                     return true;
                  case Low:
                     prioBar.setBackgroundResource( R.color.priority_3 );
                     return true;
                  case None:
                  default :
                     prioBar.setBackgroundResource( R.color.priority_none );
                     return true;
               }
               
            default :
               // SimpleCursorAdapter will bind
               return false;
         }
      }
   }
   
   

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
                                                                       Tasks.LIST_NAME,
                                                                       Tasks.DUE_DATE,
                                                                       Tasks.PRIORITY },
                                                                      new int[]
                                                                      {
                                                                       R.id.main_list_tasks_task_desc,
                                                                       R.id.main_list_tasks_task_list_name,
                                                                       R.id.main_list_tasks_task_due_date,
                                                                       R.id.main_list_tasks_task_priority } );
         
         adapter.setViewBinder( new TaskViewBinder() );
         
         setListAdapter( adapter );
      }
   }
}
