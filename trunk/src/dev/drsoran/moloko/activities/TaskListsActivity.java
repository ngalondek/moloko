package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import dev.drsoran.moloko.R;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;


public class TaskListsActivity extends ListActivity implements
         OnItemClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TaskListsActivity.class.getSimpleName();
   
   private final static String TASKS_COUNT = "tasks_count";
   
   private final static String[] PROJECTION_LISTS =
   { Lists._ID, Lists.LIST_NAME };
   
   private final static String[] PROJECTION_TASKSERIES =
   { TaskSeries._ID, TaskSeries.LIST_ID };
   
   private final static int[] RESSOURCE_IDS =
   { R.id.tasklists_listitem_list_name, R.id.tasklists_listitem_num_tasks };
   
   private final int CTX_MENU_OPEN_LIST = 0;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tasklists_activity );
      registerForContextMenu( getListView() );
      getListView().setOnItemClickListener( this );
   }
   


   @Override
   protected void onResume()
   {
      queryLists();
      super.onResume();
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      
      menu.add( 0,
                CTX_MENU_OPEN_LIST,
                0,
                getString( R.string.tasklists_ctxmenu_listitem_open,
                           getListName( info.targetView ) ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CTX_MENU_OPEN_LIST:
            openList( getListName( info.targetView ) );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   @Override
   public void onItemClick( AdapterView< ? > parent,
                            View view,
                            int pos,
                            long rowId )
   {
      openList( getListName( view ) );
   }
   


   private void openList( String listname )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( Tasks.LIST_NAME, listname );
      
      startActivity( intent );
   }
   


   private void queryLists()
   {
      final Cursor listsCursor = getContentResolver().query( Lists.CONTENT_URI,
                                                             PROJECTION_LISTS,
                                                             null,
                                                             null,
                                                             Lists.DEFAULT_SORT_ORDER );
      
      final Cursor taskSeriesCursor = getContentResolver().query( TaskSeries.CONTENT_URI,
                                                                  PROJECTION_TASKSERIES,
                                                                  null,
                                                                  null,
                                                                  TaskSeries.LIST_ID
                                                                     + " ASC" );
      
      final CursorJoiner joiner = new CursorJoiner( listsCursor, new String[]
      { Lists._ID }, taskSeriesCursor, new String[]
      { TaskSeries.LIST_ID } );
      
      final HashMap< String, Integer > listItemsWithCount = new HashMap< String, Integer >();
      String listName = null;
      
      for ( CursorJoiner.Result res : joiner )
      {
         Integer count = null;
         
         switch ( res )
         {
            case LEFT:
               listName = listsCursor.getString( 1 );
               count = new Integer( -1 );
               break;
            
            case BOTH:
               listName = listsCursor.getString( 1 );
               count = listItemsWithCount.get( listName );
               
               if ( count == null )
                  count = new Integer( 0 );
               break;
            
            case RIGHT:
               count = listItemsWithCount.get( listName );
               
               if ( count == null )
                  count = new Integer( -1 );
               break;
         }
         
         listItemsWithCount.put( listName, count + 1 );
      }
      
      final ArrayList< HashMap< String, String > > listItems = new ArrayList< HashMap< String, String > >();
      
      final Set< String > keys = listItemsWithCount.keySet();
      
      for ( String key : keys )
      {
         final HashMap< String, String > listItem = new HashMap< String, String >();
         
         listItem.put( Lists.LIST_NAME, key );
         listItem.put( TASKS_COUNT, listItemsWithCount.get( key ).toString() );
         
         listItems.add( listItem );
      }
      
      listsCursor.close();
      taskSeriesCursor.close();
      
      final SimpleAdapter simpleAdapter = new SimpleAdapter( this,
                                                             listItems,
                                                             R.layout.tasklists_activity_listitem,
                                                             new String[]
                                                             { Lists.LIST_NAME,
                                                              TASKS_COUNT },
                                                             RESSOURCE_IDS );
      
      setListAdapter( simpleAdapter );
   }
   


   private final static String getListName( View parent )
   {
      String listname = "";
      
      final View view = parent.findViewById( R.id.tasklists_listitem_list_name );
      
      if ( view instanceof TextView )
      {
         listname = ( (TextView) view ).getText().toString();
      }
      
      return listname;
   }
}
