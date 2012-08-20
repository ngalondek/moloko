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

package dev.drsoran.moloko.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmLocation;
import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.content.RtmLocationsProviderPart;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.format.MolokoDateFormatter;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.rtm.Tag;


public class RtmSmartAddAdapter extends BaseAdapter implements Filterable
{
   private final Context context;
   
   private final Filter filter = new Filter();
   
   // Icon ID, Text
   private List< Pair< Integer, String > > data = new ArrayList< Pair< Integer, String > >();
   
   private volatile List< Pair< String, Long > > due_dates;
   
   private volatile List< Pair< String, String > > priorities;
   
   private volatile List< Pair< String, String > > locations;
   
   private volatile List< Pair< String, Pair< String, Boolean > > > lists_and_tags;
   
   private volatile List< Pair< String, Pair< String, Boolean > > > repeats;
   
   private volatile List< Pair< String, Long > > estimates;
   
   
   
   public RtmSmartAddAdapter( Context context )
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
         view = LayoutInflater.from( context )
                              .inflate( R.layout.sherlock_spinner_dropdown_item,
                                        parent,
                                        false );
      else
         view = convertView;
      
      final Pair< Integer, String > item = data.get( position );
      
      return UIUtils.setDropDownItemIconAndText( view, item );
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
   
   
   
   private final static < T > T findSuggestion( List< Pair< String, T > > list,
                                                String text )
   {
      if ( list == null )
         return null;
      
      for ( Pair< String, T > pair : list )
      {
         if ( pair.first.equalsIgnoreCase( text ) )
            return pair.second;
      }
      
      return null;
   }
   
   
   private final class Filter extends android.widget.Filter
   {
      private int listsCnt = 0;
      
      
      
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
                     cal.roll( Calendar.DAY_OF_YEAR, true );
                     due_dates.add( Pair.create( context.getString( R.string.phr_tomorrow ),
                                                 Long.valueOf( cal.getTimeInMillis() ) ) );
                     
                     // The next 5 days after tomorrow
                     for ( int i = 0; i < 5; ++i )
                     {
                        cal.roll( Calendar.DAY_OF_YEAR, true );
                        final String weekDay = MolokoDateUtils.getDayOfWeekString( cal.get( Calendar.DAY_OF_WEEK ),
                                                                                   false );
                        due_dates.add( Pair.create( weekDay,
                                                    Long.valueOf( cal.getTimeInMillis() ) ) );
                     }
                  }
                  
                  if ( due_dates != null )
                     newData = filter( due_dates,
                                       opLessConstraint,
                                       R.drawable.ic_button_task_time );
                  break;
               
               case RtmSmartAddTokenizer.OP_PRIORITY:
                  if ( priorities == null )
                  {
                     priorities = new LinkedList< Pair< String, String > >();
                     priorities.add( Pair.create( RtmTask.convertPriority( Priority.High ),
                                                  RtmTask.convertPriority( Priority.High ) ) );
                     priorities.add( Pair.create( RtmTask.convertPriority( Priority.Medium ),
                                                  RtmTask.convertPriority( Priority.Medium ) ) );
                     priorities.add( Pair.create( RtmTask.convertPriority( Priority.Low ),
                                                  RtmTask.convertPriority( Priority.Low ) ) );
                     priorities.add( Pair.create( RtmTask.convertPriority( Priority.None ),
                                                  RtmTask.convertPriority( Priority.None ) ) );
                  }
                  
                  if ( priorities != null )
                     newData = filter( priorities,
                                       opLessConstraint,
                                       R.drawable.ic_button_task_priority );
                  break;
               
               case RtmSmartAddTokenizer.OP_LIST_TAGS:
                  if ( lists_and_tags == null )
                  {
                     {
                        final RtmLists lists = RtmListsProviderPart.getAllLists( context.getContentResolver()
                                                                                        .acquireContentProviderClient( Lists.CONTENT_URI ),
                                                                                 Lists.IS_SMART_LIST
                                                                                    + "=0 AND "
                                                                                    + RtmListsProviderPart.SELECTION_EXCLUDE_DELETED_AND_ARCHIVED );
                        if ( lists != null )
                        {
                           lists_and_tags = new LinkedList< Pair< String, Pair< String, Boolean > > >();
                           for ( RtmList rtmList : lists.getListsPlain() )
                              lists_and_tags.add( Pair.create( rtmList.getName(),
                                                               Pair.create( rtmList.getId(),
                                                                            Boolean.TRUE ) ) );
                           
                           listsCnt = lists_and_tags.size();
                        }
                     }
                     {
                        final List< Tag > tags = TagsProviderPart.getAllTags( context.getContentResolver()
                                                                                     .acquireContentProviderClient( Tags.CONTENT_URI ),
                                                                              null,
                                                                              null,
                                                                              true );
                        if ( tags != null )
                        {
                           if ( lists_and_tags == null )
                              lists_and_tags = new LinkedList< Pair< String, Pair< String, Boolean > > >();
                           
                           for ( Tag tag : tags )
                              lists_and_tags.add( Pair.create( tag.getTag(),
                                                               Pair.create( (String) null,
                                                                            Boolean.FALSE ) ) );
                        }
                     }
                  }
                  
                  if ( lists_and_tags != null )
                     newData = filter( lists_and_tags,
                                       opLessConstraint,
                                       listsCnt - 1,
                                       R.drawable.ic_button_task_list,
                                       R.drawable.ic_list_change_tags_tag );
                  break;
               
               case RtmSmartAddTokenizer.OP_LOCATION:
                  if ( locations == null )
                  {
                     final List< RtmLocation > dbLocs = RtmLocationsProviderPart.getAllLocations( context.getContentResolver()
                                                                                                         .acquireContentProviderClient( Locations.CONTENT_URI ) );
                     if ( dbLocs != null )
                     {
                        locations = new LinkedList< Pair< String, String > >();
                        for ( RtmLocation rtmLocation : dbLocs )
                           locations.add( Pair.create( rtmLocation.name,
                                                       rtmLocation.id ) );
                     }
                  }
                  
                  if ( locations != null )
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
                  
                  if ( repeats != null )
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
                  
                  if ( estimates != null )
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
            if ( value.first.toLowerCase().startsWith( prefix ) )
               newData.add( Pair.create( iconId, value.first ) );
         
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
               newData.add( Pair.create( i <= toIdx ? toIconId : elseIconId,
                                         value.first ) );
         }
         
         return newData;
      }
      
      
      
      private void addRecurrence( String sentence, boolean every )
      {
         final Pair< String, Boolean > result = RecurrenceParsing.parseRecurrence( sentence );
         
         if ( result != null )
         {
            final String pattern = result.first;
            final String translatedSentence = RecurrenceParsing.parseRecurrencePattern( context,
                                                                                        pattern,
                                                                                        every );
            if ( pattern != null && translatedSentence != null )
               repeats.add( Pair.create( translatedSentence,
                                         Pair.create( pattern,
                                                      Boolean.valueOf( every ) ) ) );
         }
      }
      
      
      
      private void addEstimate( long estimate )
      {
         estimates.add( Pair.create( MolokoDateFormatter.formatEstimated( context,
                                                                          estimate ),
                                     Long.valueOf( estimate ) ) );
      }
   }
}
