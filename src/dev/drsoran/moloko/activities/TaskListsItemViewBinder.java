package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.ListOverviews;


public final class TaskListsItemViewBinder implements ViewBinder
{
   @SuppressWarnings( "unused" )
   private final ListActivity context;
   
   private final int TASK_COUNT;
   
   private final int SMART;
   
   

   public TaskListsItemViewBinder( ListActivity context, ContentUiMapper mapper )
      throws NullPointerException
   {
      if ( context == null || mapper == null )
         throw new NullPointerException();
      
      this.TASK_COUNT = 8;// mapper.UI_COL_INDICES.get( ListOverviews.TASKS_COUNT );
      
      // TODO: Remove this magic number and replace by PROJECTION COL_INDICES.
      this.SMART = 6;
      this.context = context;
   }
   


   public boolean setViewValue( View view, Cursor cursor, int columnIndex )
   {
      if ( columnIndex == TASK_COUNT && cursor.getInt( SMART ) != 0 )
      {
         final TextView numTasks = (TextView) view;
         numTasks.setText( cursor.getString( TASK_COUNT ) );
         numTasks.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart );
         
         return true;
      }
      
      else
         // SimpleCursorAdapter will bind
         return false;
   }
}
