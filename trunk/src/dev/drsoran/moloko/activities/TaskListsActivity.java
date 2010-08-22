package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;


public class TaskListsActivity extends ListActivity implements
         OnItemClickListener
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
      getListView().setOnItemClickListener( this );
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
      queryLists();
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
                           getListProperties( info.targetView ).getString( ListOverviews.LIST_NAME ) ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CTX_MENU_OPEN_LIST:
            openList( getListProperties( info.targetView ) );
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
      openList( getListProperties( view ) );
   }
   


   private void openList( Bundle properties )
   {
      String filter = null;
      
      if ( properties.containsKey( ListOverviews.FILTER ) )
      {
         filter = properties.getString( ListOverviews.FILTER );
         
         // Check if the smart filter could be parsed. Otherwise
         // it is null and we do not fire the intent.
         if ( filter == null )
            return;
      }
      
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      intent.putExtra( Tasks.LIST_NAME,
                       properties.getString( ListOverviews.LIST_NAME ) );
      
      if ( filter != null )
         intent.putExtra( Lists.FILTER, filter );
      
      startActivity( intent );
   }
   


   private void queryLists()
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( ListOverviews.CONTENT_URI );
      
      if ( client != null )
      {
         setListAdapter( new TaskListsAdapter( this,
                                               R.layout.tasklists_activity_listitem,
                                               ListOverviewsProviderPart.getListsOverview( client ) ) );
      }
   }
   


   private final static Bundle getListProperties( View parent )
   {
      return (Bundle) parent.getTag();
   }
}
