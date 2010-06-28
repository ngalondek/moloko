package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;


public class TaskListsActivity extends ListActivity implements
         OnItemClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TaskListsActivity.class.getSimpleName();
   
   private final static ContentUiMapper contentUiMapper = new ContentUiMapper( new String[]
                                                                               {
                                                                                ListOverviews._ID,
                                                                                ListOverviews.SMART,
                                                                                ListOverviews.LIST_NAME,
                                                                                ListOverviews.TASKS_COUNT },
                                                                               new String[]
                                                                               {
                                                                                ListOverviews.LIST_NAME,
                                                                                ListOverviews.TASKS_COUNT },
                                                                               new int[]
                                                                               {
                                                                                R.id.tasklists_listitem_list_name,
                                                                                R.id.tasklists_listitem_num_tasks } );
   
   private final int CTX_MENU_OPEN_LIST = 0;
   
   static
   {
      assert ( contentUiMapper.UI_COLUMNS.length == contentUiMapper.RESSOURCE_IDS.length );
   }
   
   

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
      final Cursor c = managedQuery( ListOverviews.CONTENT_URI,
                                     contentUiMapper.PROJECTION,
                                     null,
                                     null,
                                     ListOverviews.DEFAULT_SORT_ORDER );
      
      // TODO: Handle null cursor. Show error?
      if ( c != null )
      {
         final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                      R.layout.tasklists_activity_listitem,
                                                                      c,
                                                                      contentUiMapper.UI_COLUMNS,
                                                                      contentUiMapper.RESSOURCE_IDS );
         
         adapter.setViewBinder( new TaskListsItemViewBinder( this,
                                                             contentUiMapper ) );
         
         setListAdapter( adapter );
      }
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
