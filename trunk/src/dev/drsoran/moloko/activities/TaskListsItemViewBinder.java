package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


public final class TaskListsItemViewBinder implements ViewBinder
{
   private final ListActivity context;
   
   private final int LIST_NAME;
   
   private final int TASK_COUNT;
   
   private final int SMART;
   
   private final int FILTER;
   
   

   public TaskListsItemViewBinder( ListActivity context, ContentUiMapper mapper )
      throws NullPointerException
   {
      if ( context == null || mapper == null )
         throw new NullPointerException();
      
      this.LIST_NAME = mapper.getProjectionColumnIndex( ListOverviews.LIST_NAME );
      this.TASK_COUNT = mapper.getProjectionColumnIndex( ListOverviews.TASKS_COUNT );
      this.SMART = mapper.getProjectionColumnIndex( ListOverviews.IS_SMART_LIST );
      this.FILTER = mapper.getProjectionColumnIndex( ListOverviews.FILTER );
      
      this.context = context;
   }
   


   public boolean setViewValue( View view, Cursor cursor, int columnIndex )
   {
      Bundle listProperties = (Bundle) ( (View) view.getParent() ).getTag();
      
      if ( listProperties == null )
      {
         listProperties = new Bundle();
         ( (View) view.getParent() ).setTag( listProperties );
      }
      
      if ( columnIndex == LIST_NAME )
      {
         final String listName = cursor.getString( LIST_NAME );
         
         listProperties.putString( ListOverviews.LIST_NAME, listName );
         
         final TextView listNameView = (TextView) view;
         listNameView.setText( listName );
         
         return true;
      }
      else if ( columnIndex == TASK_COUNT )
      {
         final TextView numTasks = (TextView) view;
         
         if ( cursor.getInt( SMART ) != 0 )
         {
            final String evalFilter = RtmSmartFilter.evaluate( cursor.getString( FILTER ) );
            
            boolean badFilter = evalFilter == null;
            
            if ( !badFilter )
            {
               try
               {
                  final Cursor smartListTasks = context.getContentResolver()
                                                       .query( Tasks.CONTENT_URI,
                                                               new String[]
                                                               { Tasks._ID },
                                                               evalFilter,
                                                               null,
                                                               null );
                  
                  numTasks.setText( Integer.toString( smartListTasks.getCount() ) );
                  smartListTasks.close();
                  
                  numTasks.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart );
                  
                  listProperties.putString( ListOverviews.FILTER, evalFilter );
               }
               catch ( SQLiteException e )
               {
                  badFilter = true;
               }
            }
            
            if ( badFilter )
            {
               numTasks.setText( "?" );
               numTasks.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart_fail );
               
               listProperties.putString( ListOverviews.FILTER, null );
            }
         }
         else
         {
            numTasks.setText( cursor.getString( TASK_COUNT ) );
         }
         
         return true;
      }
      
      else
         // SimpleCursorAdapter will bind
         return true;
   }
}
