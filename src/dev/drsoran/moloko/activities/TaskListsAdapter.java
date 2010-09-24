/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.moloko.activities;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class TaskListsAdapter extends ArrayAdapter< RtmListWithTaskCount >
{
   private final static String TAG = TaskListsAdapter.class.getName();
   
   // private final Context context;
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   

   public TaskListsAdapter( Context context, int resourceId,
      List< RtmListWithTaskCount > lists )
   {
      super( context, 0, lists );
      
      // this.context = context;
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = inflater.inflate( resourceId, parent, false );
      
      TextView listName;
      TextView tasksCount;
      ViewGroup iconsContainer;
      
      try
      {
         listName = (TextView) view.findViewById( R.id.tasklists_listitem_list_name );
         tasksCount = (TextView) view.findViewById( R.id.tasklists_listitem_num_tasks );
         iconsContainer = (ViewGroup) view.findViewById( R.id.tasklists_listitem_icons_container );
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
      
      // If we have a smart filter we check if it could
      // be evaluated. If so add the filter to show in list
      // as name. Otherwise mark it explicitly with null
      // as bad filter
      if ( rtmList.hasSmartFilter() )
      {
         if ( rtmList.isSmartFilterValid() )
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart );
         }
         else
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_listitem_numtasks_bgnd_smart_fail );
            tasksCount.setText( "?" );
         }
      }
      
      if ( rtmList.getId().equals( MolokoApp.getSettings().getDefaultListId() ) )
      {
         addIcon( iconsContainer, R.drawable.icon_flag_black );
      }
      
      if ( rtmList.getLocked() != 0 )
      {
         addIcon( iconsContainer, R.drawable.icon_lock_black );
      }
      
      return view;
   }
   


   private void addIcon( ViewGroup container, int resId )
   {
      container.setVisibility( View.VISIBLE );
      
      final ImageView icon = (ImageView) ImageView.inflate( getContext(),
                                                            R.layout.tasklists_activity_listitem_icon,
                                                            null );
      
      if ( icon != null )
      {
         icon.setImageResource( resId );
         container.addView( icon );
      }
   }
}
