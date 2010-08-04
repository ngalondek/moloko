package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;


public class TasksListActivity extends ListActivity implements OnClickListener
{
   @SuppressWarnings( "unused" )
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
   
   private final Bundle configuration = new Bundle();
   
   

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
   protected void onResume()
   {
      super.onResume();
      refresh();
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate( R.menu.taskslist_menu_options, menu );
      
      return addOptionsMenuIntents( menu );
   }
   


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
   


   private final void refresh()
   {
      StringBuffer selection = new StringBuffer();
      
      final String listName = configuration.getString( Tasks.LIST_NAME );
      final String smartFilter = configuration.getString( Lists.FILTER );
      
      // The smart filter has precedence
      if ( smartFilter != null )
      {
         selection.append( smartFilter );
      }
      else if ( listName != null )
      {
         selection.append( Tasks.LIST_NAME )
                  .append( " = '" )
                  .append( listName )
                  .append( "'" );
      }
      
      final Cursor c = managedQuery( Tasks.CONTENT_URI,
                                     contentUiMapper.getProjectionArray(),
                                     ( selection.length() > 0 )
                                                               ? selection.toString()
                                                               : null,
                                     null,
                                     Tasks.PRIORITY + ", "
                                        + Tasks.TASKSERIES_NAME );
      
      // TODO: Handle null cursor. Show error?
      if ( c != null )
      {
         final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                      R.layout.taskslist_activity_listitem,
                                                                      c,
                                                                      contentUiMapper.getUiColumnsArray(),
                                                                      contentUiMapper.RESSOURCE_IDS );
         
         int flags = 0x0;
         
         // If we have been started with a concrete list name, then
         // we do not need to click it. Otherwise we would call the
         // same list again. But this only applies to non smart
         // lists
         if ( listName != null && smartFilter == null )
            flags |= TasksListItemViewBinder.NO_CLICKABLE_LIST_NAME;
         
         adapter.setViewBinder( new TasksListItemViewBinder( this,
                                                             contentUiMapper,
                                                             flags ) );
         
         setListAdapter( adapter );
         
         if ( smartFilter != null )
         {
            setTitle( getString( R.string.taskslist_titlebar_listname_smart,
                                 listName ) );
         }
         else if ( listName != null )
         {
            setTitle( getString( R.string.taskslist_titlebar_listname, listName ) );
         }
         else
         {
            setTitle( getString( R.string.taskslist_titlebar_listname,
                                 getString( R.string.app_name ) ) );
         }
      }
   }
}
