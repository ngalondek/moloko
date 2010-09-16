package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskListsActivity extends ListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = TaskListsActivity.class.getSimpleName();
   
   private final int CTX_MENU_OPEN_LIST = 0;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tasklists_activity );
      registerForContextMenu( getListView() );
      
      UIUtils.setTitle( this, R.string.app_tasklists );
      
      if ( !( getListAdapter() instanceof TaskListsAdapter ) )
         queryLists();
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      
      menu.add( Menu.NONE,
                CTX_MENU_OPEN_LIST,
                Menu.NONE,
                getString( R.string.phr_open_with_name,
                           getRtmList( info.position ).getName() ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CTX_MENU_OPEN_LIST:
            openList( getRtmList( info.position ) );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   @Override
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
      openList( getRtmList( position ) );
   }
   


   public void onTitleBarSearchClicked( View view )
   {
      onSearchRequested();
   }
   


   private void openList( RtmListWithTaskCount rtmList )
   {
      // Check if the smart filter could be parsed. Otherwise
      // we do not fire the intent.
      if ( rtmList.isSmartFilterValid() )
      {
         final String listName = rtmList.getName();
         
         final Intent intent = new Intent( Intent.ACTION_VIEW,
                                           Tasks.CONTENT_URI );
         
         intent.putExtra( AbstractTasksListActivity.TITLE,
                          getString( R.string.taskslist_titlebar, listName ) );
         intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                          R.drawable.icon_list_white );
         
         RtmSmartFilter filter = rtmList.getSmartFilter();
         
         // If we have no smart filter we use the list name as "list:" filter
         if ( filter == null )
         {
            filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_LIST_LIT
               + RtmSmartFilterLexer.quotify( listName ) );
            
            assert ( filter.getEvaluatedFilterString() != null );
            
            // We have a non-smart list. So we disable clicking the list name
            // cause we have no tasks from different lists in the result.
            final Bundle config = new Bundle();
            config.putBoolean( AbstractTasksListActivity.DISABLE_LIST_NAME,
                               true );
            
            intent.putExtra( AbstractTasksListActivity.ADAPTER_CONFIG, config );
         }
         
         intent.putExtra( AbstractTasksListActivity.FILTER_EVALUATED,
                          filter.getEvaluatedFilterString() );
         
         startActivity( intent );
      }
   }
   


   private void queryLists()
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( ListOverviews.CONTENT_URI );
      
      if ( client != null )
      {
         setListAdapter( new TaskListsAdapter( this,
                                               R.layout.tasklists_activity_listitem,
                                               ListOverviewsProviderPart.getListsOverview( client ) ) );
         client.release();
      }
   }
   


   private final RtmListWithTaskCount getRtmList( int pos )
   {
      return (RtmListWithTaskCount) getListAdapter().getItem( pos );
   }
}
