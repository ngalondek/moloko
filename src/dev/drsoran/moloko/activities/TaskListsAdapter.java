package dev.drsoran.moloko.activities;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class TaskListsAdapter extends ArrayAdapter< RtmListWithTaskCount >
{
   private final static String TAG = TaskListsAdapter.class.getName();
   
   //private final Context context;
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   

   public TaskListsAdapter( Context context, int resourceId,
      List< RtmListWithTaskCount > lists )
   {
      super( context, 0, lists );
      
      //this.context = context;
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = inflater.inflate( resourceId, parent, false );
      
      TextView listName;
      TextView tasksCount;
      
      try
      {
         listName = (TextView) view.findViewById( R.id.tasklists_listitem_list_name );
         tasksCount = (TextView) view.findViewById( R.id.tasklists_listitem_num_tasks );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final RtmListWithTaskCount rtmList = getItem( position );
      
      final String listNameStr = rtmList.getName();
      final int numTasks = rtmList.getTaskCount();
      
      listName.setText( listNameStr );
      tasksCount.setText( String.valueOf( numTasks ) );
      
      if ( rtmList.getSmartFilter() != null )
      {
         // check if the RTM smart filter could be evaluated
         if ( numTasks != -1 )
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart );
         }
         else
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart_fail );
            tasksCount.setText( "?" );
         }
      }
      
      Bundle listProperties = (Bundle) view.getTag();
      
      if ( listProperties == null )
      {
         listProperties = new Bundle();
         ( (View) view ).setTag( listProperties );
         
         listProperties.putString( Lists.LIST_NAME, listNameStr );
         
         // If we have a smart filter we check if it could
         // be evaluated. If so add the filter to show in list
         // as name. Otherwise mark it explicitly with null
         // as bad filter.
         if ( rtmList.getSmartFilter() != null )
            if ( numTasks != -1 )
               listProperties.putString( ListOverviews.FILTER,
                                         rtmList.getSmartFilter()
                                                .getEvaluatedFilterString() );
            else
               listProperties.putString( ListOverviews.FILTER, null );
      }
      
      return view;
   }
   
}
