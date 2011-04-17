/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


public class TasksListAdapter extends ArrayAdapter< ListTask >
{
   private final static String TAG = "Moloko."
      + TasksListAdapter.class.getName();
   
   public final static int FLAG_NO_FILTER = 1 << 0;
   
   public final static int FLAG_NO_CLICKABLES = 1 << 1;
   
   private final Context context;
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   private final RtmSmartFilter filter;
   
   private final Time now = MolokoDateUtils.newTime();
   
   private final String[] tagsToRemove;
   
   private final int flags;
   
   

   public TasksListAdapter( Context context, int resourceId,
      List< ListTask > tasks, RtmSmartFilter filter )
   {
      this( context, resourceId, tasks, filter, 0 );
   }
   


   public TasksListAdapter( Context context, int resourceId,
      List< ListTask > tasks, RtmSmartFilter filter, int flags )
   {
      super( context, 0, tasks );
      
      this.context = context;
      this.flags = flags;
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      this.filter = filter;
      
      if ( ( flags & FLAG_NO_FILTER ) != FLAG_NO_FILTER )
      {
         final ArrayList< RtmSmartFilterToken > tokens = filter.getTokens();
         final List< String > tagsToRemove = new ArrayList< String >();
         
         for ( RtmSmartFilterToken token : tokens )
         {
            if ( token.operatorType == RtmSmartFilterLexer.OP_TAG
               && !token.isNegated )
            {
               tagsToRemove.add( token.value );
            }
         }
         
         this.tagsToRemove = new String[ tagsToRemove.size() ];
         tagsToRemove.toArray( this.tagsToRemove );
      }
      else
      {
         this.tagsToRemove = null;
      }
   }
   


   public LayoutInflater getInflater()
   {
      return inflater;
   }
   


   public int getLayoutRessource()
   {
      return resourceId;
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
         convertView = inflater.inflate( resourceId, parent, false );
      
      final View priority = convertView.findViewById( R.id.taskslist_listitem_priority );
      
      ImageView completed;
      TextView description;
      ViewGroup additionalsLayout;
      TextView listName;
      TextView location;
      ImageView recurrent;
      ImageView hasNotes;
      ImageView postponed;
      TextView dueDate;
      
      try
      {
         completed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_check );
         description = (TextView) convertView.findViewById( R.id.taskslist_listitem_desc );
         additionalsLayout = (ViewGroup) convertView.findViewById( R.id.taskslist_listitem_additionals_container );
         listName = (TextView) convertView.findViewById( R.id.taskslist_listitem_btn_list_name );
         location = (TextView) convertView.findViewById( R.id.taskslist_listitem_location );
         recurrent = (ImageView) convertView.findViewById( R.id.taskslist_listitem_recurrent );
         hasNotes = (ImageView) convertView.findViewById( R.id.taskslist_listitem_has_notes );
         postponed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_postponed );
         dueDate = (TextView) convertView.findViewById( R.id.taskslist_listitem_due_date );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final ListTask task = getItem( position );
      
      UIUtils.setTaskDescription( description, task, now );
      
      if ( task.getRecurrence() != null )
         recurrent.setVisibility( View.VISIBLE );
      else
         recurrent.setVisibility( View.GONE );
      
      if ( task.getNumberOfNotes() > 0 )
         hasNotes.setVisibility( View.VISIBLE );
      else
         hasNotes.setVisibility( View.GONE );
      
      if ( task.getPosponed() > 0 )
         postponed.setVisibility( View.VISIBLE );
      else
         postponed.setVisibility( View.GONE );
      
      setListName( listName, task );
      
      setDueDate( dueDate, task );
      
      UIUtils.setPriorityColor( priority, task );
      
      setTags( additionalsLayout, task );
      
      setLocation( location, task );
      
      // Completed
      setCompleted( completed, task );
      
      return convertView;
   }
   


   private final void setListName( TextView view, ListTask task )
   {
      if ( ( flags & FLAG_NO_FILTER ) == FLAG_NO_FILTER
         || !RtmSmartFilterParsing.hasOperatorAndValue( filter.getTokens(),
                                                        RtmSmartFilterLexer.OP_LIST,
                                                        task.getListName(),
                                                        false ) )
      {
         view.setVisibility( View.VISIBLE );
         view.setText( task.getListName() );
         
         if ( ( flags & FLAG_NO_CLICKABLES ) == FLAG_NO_CLICKABLES )
            view.setClickable( false );
      }
      else
         view.setVisibility( View.GONE );
   }
   


   private final void setDueDate( TextView view, ListTask task )
   {
      // if has a due date
      if ( task.getDue() != null )
      {
         view.setVisibility( View.VISIBLE );
         
         String dueText = null;
         
         final long dueMillis = task.getDue().getTime();
         final boolean hasDueTime = task.hasDueTime();
         
         // Today
         if ( MolokoDateUtils.isToday( dueMillis ) )
         {
            // If it has a time, we show the time
            if ( hasDueTime )
               dueText = MolokoDateUtils.formatTime( dueMillis );
            else
               // We only show the 'Today' phrase
               dueText = context.getString( R.string.phr_today );
         }
         else
         {
            final Time dueTime = MolokoDateUtils.newTime( dueMillis );
            
            // If it is the same year
            if ( dueTime.year == now.year )
            {
               // If the same week and in the future
               if ( now.getWeekNumber() == dueTime.getWeekNumber()
                  && dueTime.after( now ) )
               {
                  // we only show the week day
                  dueText = DateUtils.getRelativeTimeSpanString( dueMillis,
                                                                 System.currentTimeMillis(),
                                                                 DateUtils.WEEK_IN_MILLIS,
                                                                 DateUtils.FORMAT_SHOW_WEEKDAY )
                                     .toString();
               }
               
               // Not the same week or same week but in the past
               else
               {
                  // we show the date but w/o year
                  dueText = MolokoDateUtils.formatDate( dueMillis,
                                                        MolokoDateUtils.FORMAT_ABR_MONTH );
               }
            }
            
            // Not the same year
            else
            {
               // we show the full date with year
               dueText = MolokoDateUtils.formatDate( dueMillis,
                                                     MolokoDateUtils.FORMAT_ABR_MONTH
                                                        | MolokoDateUtils.FORMAT_WITH_YEAR );
            }
         }
         
         view.setText( dueText );
      }
      
      // has no due date
      else
         view.setVisibility( View.GONE );
   }
   


   private void setLocation( TextView view, ListTask task )
   {
      // If the task has no location
      if ( ( flags & FLAG_NO_FILTER ) != FLAG_NO_FILTER
         && ( TextUtils.isEmpty( task.getLocationName() ) || RtmSmartFilterParsing.hasOperatorAndValue( filter.getTokens(),
                                                                                                        RtmSmartFilterLexer.OP_LOCATION,
                                                                                                        task.getLocationName(),
                                                                                                        false ) ) )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         view.setText( task.getLocationName() );
         
         if ( ( flags & FLAG_NO_CLICKABLES ) == FLAG_NO_CLICKABLES )
            view.setClickable( false );
      }
   }
   


   private void setTags( ViewGroup tagsLayout, ListTask task )
   {
      final Bundle tagsConfig = new Bundle();
      
      if ( tagsToRemove != null && tagsToRemove.length > 0 )
         tagsConfig.putStringArray( UIUtils.REMOVE_TAGS_EQUALS, tagsToRemove );
      
      if ( ( flags & FLAG_NO_CLICKABLES ) == FLAG_NO_CLICKABLES )
         tagsConfig.putBoolean( UIUtils.DISABLE_ALL_TAGS, Boolean.TRUE );
      
      UIUtils.inflateTags( context,
                           tagsLayout,
                           task.getTags(),
                           tagsConfig,
                           (OnClickListener) context );
   }
   


   private void setCompleted( ImageView view, ListTask task )
   {
      view.setEnabled( task.getCompleted() != null );
   }
}
