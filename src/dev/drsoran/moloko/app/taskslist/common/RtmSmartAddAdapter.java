/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.taskslist.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.nfc.Tag;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.domain.model.Priority;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTokenizer;
import dev.drsoran.moloko.util.MolokoDateUtils;


class RtmSmartAddAdapter extends BaseAdapter implements Filterable
{
   private final AppContext context;
   
   private final Filter filter = new Filter();
   
   // Icon ID, Text
   private List< Pair< Integer, String > > data = new ArrayList< Pair< Integer, String > >();
   
   private volatile List< Pair< String, Long > > due_dates;
   
   private volatile List< Pair< String, String > > priorities;
   
   private volatile List< Pair< String, String > > locations;
   
   private volatile List< Pair< String, Pair< String, Boolean > > > lists_and_tags;
   
   private volatile List< Pair< String, Pair< String, Boolean > > > repeats;
   
   private volatile List< Pair< String, Long > > estimates;
   
   
   
   public RtmSmartAddAdapter( AppContext context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public Object getItem( int position )
   {
      return data.get( position ).second;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return position;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view;
      
      if ( convertView == null )
      {
         view = LayoutInflater.from( context )
                              .inflate( R.layout.sherlock_spinner_dropdown_item,
                                        parent,
                                        false );
      }
      else
      {
         view = convertView;
      }
      
      final Pair< Integer, String > item = data.get( position );
      
      return UiUtils.setDropDownItemIconAndText( view, item );
   }
   
   
   
   @Override
   public Filter getFilter()
   {
      return filter;
   }
   
   
   
   @Override
   public int getCount()
   {
      return data.size();
   }
   
   
   
   public Object getSuggestionValue( int tokenType, String tokenText )
   {
      switch ( tokenType )
      {
         case RtmSmartAddTokenizer.DUE_DATE_TYPE:
            return findSuggestion( due_dates, tokenText );
         case RtmSmartAddTokenizer.PRIORITY_TYPE:
            return findSuggestion( priorities, tokenText );
         case RtmSmartAddTokenizer.LIST_TAGS_TYPE:
            return findSuggestion( lists_and_tags, tokenText );
         case RtmSmartAddTokenizer.LOCATION_TYPE:
            return findSuggestion( locations, tokenText );
         case RtmSmartAddTokenizer.REPEAT_TYPE:
            return findSuggestion( repeats, tokenText );
         case RtmSmartAddTokenizer.ESTIMATE_TYPE:
            return findSuggestion( estimates, tokenText );
         default :
            return null;
      }
   }
   
   
   
   private static < T > T findSuggestion( List< Pair< String, T > > list,
                                          String text )
   {
      if ( list == null )
      {
         return null;
      }
      
      for ( Pair< String, T > pair : list )
      {
         if ( pair.first.equalsIgnoreCase( text ) )
         {
            return pair.second;
         }
      }
      
      return null;
   }
   
   
   private final class Filter extends android.widget.Filter
   {
      private final int listsCnt = 0;
      
      
      
      /**
       * Runs in a background thread
       */
      @Override
      protected FilterResults performFiltering( CharSequence constraint )
      {
         final FilterResults results = new FilterResults();
         
         if ( !TextUtils.isEmpty( constraint ) )
         {
            List< Pair< Integer, String > > newData = null;
            final String opLessConstraint = TextUtils.substring( constraint,
                                                                 1,
                                                                 constraint.length() )
                                                     .toLowerCase();
            
            switch ( constraint.charAt( 0 ) )
            {
               case RtmSmartAddTokenizer.OP_DUE_DATE:
                  if ( due_dates == null )
                  {
                     due_dates = new LinkedList< Pair< String, Long > >();
                     
                     // Today
                     due_dates.add( Pair.create( context.getString( R.string.phr_today ),
                                                 Long.valueOf( System.currentTimeMillis() ) ) );
                     
                     final MolokoCalendar cal = MolokoCalendar.getInstance();
                     
                     // Tomorrow
                     cal.add( Calendar.DAY_OF_YEAR, 1 );
                     due_dates.add( Pair.create( context.getString( R.string.phr_tomorrow ),
                                                 Long.valueOf( cal.getTimeInMillis() ) ) );
                     
                     // The next 5 days after tomorrow
                     for ( int i = 0; i < 5; ++i )
                     {
                        cal.add( Calendar.DAY_OF_YEAR, 1 );
                        final String weekDay = MolokoDateUtils.getDayOfWeekString( cal.get( Calendar.DAY_OF_WEEK ),
                                                                                   false );
                        due_dates.add( Pair.create( weekDay,
                                                    Long.valueOf( cal.getTimeInMillis() ) ) );
                     }
                  }
                  
                  newData = filter( due_dates,
                                    opLessConstraint,
                                    R.drawable.ic_button_task_time );
                  break;
               
               case RtmSmartAddTokenizer.OP_PRIORITY:
                  if ( priorities == null )
                  {
                     priorities = new LinkedList< Pair< String, String > >();
                     priorities.add( Pair.create( Priority.High.toString(),
                                                  Priority.High.toString() ) );
                     priorities.add( Pair.create( Priority.Medium.toString(),
                                                  Priority.Medium.toString() ) );
                     priorities.add( Pair.create( Priority.Low.toString(),
                                                  Priority.Low.toString() ) );
                     priorities.add( Pair.create( Priority.None.toString(),
                                                  Priority.None.toString() ) );
                  }
                  
                  newData = filter( priorities,
                                    opLessConstraint,
                                    R.drawable.ic_button_task_priority );
                  break;
               
               case RtmSmartAddTokenizer.OP_LIST_TAGS:
                  if ( lists_and_tags == null )
                  {
                     lists_and_tags = new LinkedList< Pair< String, Pair< String, Boolean > > >();
                     
                     {
                        for ( TasksList tasksList : context.getContentRepository()
                                                           .getPhysicalTasksLists() )
                        {
                           lists_and_tags.add( Pair.create( tasksList.getName(),
                                                            Pair.create( String.valueOf( tasksList.getId() ),
                                                                         Boolean.TRUE ) ) );
                        }
                        
                        listsCnt = lists_and_tags.size();
                     }
                     {
                        final List< Tag > tags = TagsProviderPart.getAllTags( context.getContentResolver()
                                                                                     .acquireContentProviderClient( Tags.CONTENT_URI ),
                                                                              null,
                                                                              null,
                                                                              true );
                        if ( tags != null )
                        {
                           for ( Tag tag : tags )
                           {
                              lists_and_tags.add( Pair.create( tag.getTag(),
                                                               Pair.create( (String) null,
                                                                            Boolean.FALSE ) ) );
                           }
                        }
                     }
                  }
                  
                  newData = filter( lists_and_tags,
                                    opLessConstraint,
                                    listsCnt - 1,
                                    R.drawable.ic_button_task_list,
                                    R.drawable.ic_list_change_tags_tag );
                  break;
               
               case RtmSmartAddTokenizer.OP_LOCATION:
                  if ( locations == null )
                  {
                     final List< RtmLocation > dbLocs = RtmLocationsTable.getAllLocations( context.getContentResolver()
                                                                                                  .acquireContentProviderClient( Locations.CONTENT_URI ) );
                     if ( dbLocs != null )
                     {
                        locations = new LinkedList< Pair< String, String > >();
                        for ( RtmLocation rtmLocation : dbLocs )
                        {
                           locations.add( Pair.create( rtmLocation.name,
                                                       rtmLocation.id ) );
                        }
                     }
                  }
                  
                  newData = filter( locations,
                                    opLessConstraint,
                                    R.drawable.ic_button_task_location );
                  break;
               
               case RtmSmartAddTokenizer.OP_REPEAT:
                  if ( repeats == null )
                  {
                     repeats = new LinkedList< Pair< String, Pair< String, Boolean >> >();
                     
                     for ( String suggestion : context.getResources()
                                                      .getStringArray( R.array.app_quick_add_task_recurr_sugg_every ) )
                     {
                        addRecurrence( suggestion, true );
                     }
                     
                     for ( String suggestion : context.getResources()
                                                      .getStringArray( R.array.app_quick_add_task_recurr_sugg_after ) )
                     {
                        addRecurrence( suggestion, false );
                     }
                  }
                  
                  newData = filter( repeats,
                                    opLessConstraint,
                                    R.drawable.ic_button_task_recurrent );
                  break;
               
               case RtmSmartAddTokenizer.OP_ESTIMATE:
                  if ( estimates == null )
                  {
                     estimates = new LinkedList< Pair< String, Long > >();
                     
                     addEstimate( 2 * DateUtils.MINUTE_IN_MILLIS );
                     addEstimate( 5 * DateUtils.MINUTE_IN_MILLIS );
                     addEstimate( 10 * DateUtils.MINUTE_IN_MILLIS );
                     addEstimate( 15 * DateUtils.MINUTE_IN_MILLIS );
                     addEstimate( 30 * DateUtils.MINUTE_IN_MILLIS );
                     addEstimate( 45 * DateUtils.MINUTE_IN_MILLIS );
                     addEstimate( DateUtils.HOUR_IN_MILLIS );
                  }
                  
                  newData = filter( estimates,
                                    opLessConstraint,
                                    R.drawable.ic_button_task_thumb );
                  break;
               
               default :
                  newData = null;
                  break;
            }
            
            if ( newData != null )
            {
               results.values = newData;
               results.count = newData.size();
            }
            else
            {
               results.values = Collections.emptyList();
               results.count = 0;
            }
         }
         else
         {
            results.values = Collections.emptyList();
            results.count = 0;
         }
         
         return results;
      }
      
      
      
      /**
       * Runs in the UI thread
       */
      @SuppressWarnings( "unchecked" )
      @Override
      protected void publishResults( CharSequence constraint,
                                     FilterResults results )
      {
         data = (List< Pair< Integer, String >>) results.values;
         
         if ( results.count > 0 )
         {
            notifyDataSetChanged();
         }
         else
         {
            notifyDataSetInvalidated();
         }
      }
      
      
      
      private < T > List< Pair< Integer, String > > filter( List< Pair< String, T > > list,
                                                            String prefix,
                                                            int iconId )
      {
         List< Pair< Integer, String > > newData = new LinkedList< Pair< Integer, String > >();
         
         for ( Pair< String, T > value : list )
         {
            if ( value.first.toLowerCase().startsWith( prefix ) )
            {
               newData.add( Pair.create( iconId, value.first ) );
            }
         }
         
         return newData;
      }
      
      
      
      private < T > List< Pair< Integer, String > > filter( List< Pair< String, T > > list,
                                                            String prefix,
                                                            int toIdx,
                                                            int toIconId,
                                                            int elseIconId )
      {
         List< Pair< Integer, String > > newData = new LinkedList< Pair< Integer, String > >();
         
         for ( int i = 0, cnt = list.size(); i < cnt; i++ )
         {
            final Pair< String, T > value = list.get( i );
            
            if ( value.first.toLowerCase().startsWith( prefix ) )
            {
               newData.add( Pair.create( i <= toIdx ? toIconId : elseIconId,
                                         value.first ) );
            }
         }
         
         return newData;
      }
      
      
      
      private void addRecurrence( String sentence, boolean every )
      {
         IRecurrenceParsing recurrenceParsing = context.getParsingService()
                                                       .getRecurrenceParsing();
         final Pair< String, Boolean > result = recurrenceParsing.parseRecurrence( sentence );
         
         if ( result != null )
         {
            final String pattern = result.first;
            final String translatedSentence = recurrenceParsing.parseRecurrencePatternToSentence( pattern,
                                                                                                  every );
            if ( pattern != null && translatedSentence != null )
            {
               repeats.add( Pair.create( translatedSentence,
                                         Pair.create( pattern,
                                                      Boolean.valueOf( every ) ) ) );
            }
         }
      }
      
      
      
      private void addEstimate( long estimate )
      {
         estimates.add( Pair.create( context.getDateFormatter()
                                            .formatEstimated( estimate ),
                                     Long.valueOf( estimate ) ) );
      }
   }
}
