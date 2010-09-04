package dev.drsoran.moloko.activities;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskActivity extends Activity
{
   private final static String TAG = TaskActivity.class.getSimpleName();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.task_activity );
   }
   


   @Override
   public void onResume()
   {
      super.onResume();
      
      final Uri taskUri = getIntent().getData();
      
      // TODO: Show error if URI or ContentProviderClient is null.
      if ( taskUri != null )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
         
         if ( client != null )
         {
            final Task task = TasksProviderPart.getTask( client,
                                                         String.valueOf( ContentUris.parseId( taskUri ) ) );
            
            // TODO: Show error if task could not be found.
            if ( task != null )
            {
               View priorityBar;
               TextView createdDate;
               TextView description;
               TextView listName;
               ViewGroup tagsLayout;
               
               try
               {
                  priorityBar = findViewById( R.id.task_overview_priority_bar );
                  createdDate = (TextView) findViewById( R.id.task_overview_created_date );
                  description = (TextView) findViewById( R.id.task_overview_desc );
                  listName = (TextView) findViewById( R.id.task_overview_list_name );
                  tagsLayout = (ViewGroup) findViewById( R.id.task_overview_tags );
               }
               catch ( ClassCastException e )
               {
                  Log.e( TAG, "Invalid layout spec.", e );
                  throw e;
               }
               
               createdDate.setText( UIUtils.getFullDateWithTime( this,
                                                                 task.getCreated()
                                                                     .getTime() ) );
               
               UIUtils.setPriorityColor( priorityBar, task );
               
               UIUtils.setTaskDescription( description, task, null );
               
               listName.setText( task.getListName() );
               
               UIUtils.inflateTags( this, tagsLayout, task, null, null );
            }
         }
      }
   }
}
