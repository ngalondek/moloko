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

package dev.drsoran.moloko.ui.rtmsmartadd;

import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_DUE_DATE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_ESTIMATE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_LIST_TAGS;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_LOCATION;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_PRIORITY;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_REPEAT;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import android.content.Context;
import android.text.format.DateUtils;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;


public class RtmSmartAddSuggestor
{
   private final Context context;
   
   private final IContentRepository contentRepository;
   
   private final IRecurrenceParsing recurrenceParsing;
   
   private final IDateFormatterService dateFormatter;
   
   private final IRtmCalendarProvider calendarProvider;
   
   
   
   public RtmSmartAddSuggestor( Context context,
      IContentRepository contentRepository,
      IRecurrenceParsing recurrenceParsing,
      IDateFormatterService dateFormatter, IRtmCalendarProvider calendarProvider )
   {
      this.context = context;
      this.contentRepository = contentRepository;
      this.recurrenceParsing = recurrenceParsing;
      this.dateFormatter = dateFormatter;
      this.calendarProvider = calendarProvider;
   }
   
   
   
   public Collection< RtmSmartAddSuggestion > getSuggestions( int rtmSmartAddTokenType ) throws IllegalArgumentException
   {
      switch ( rtmSmartAddTokenType )
      {
         case OP_DUE_DATE:
            return createDueDateSuggestions();
            
         case OP_PRIORITY:
            return createPrioritySuggestions();
            
         case OP_LIST_TAGS:
            return createListAndTagsSuggestions();
            
         case OP_LOCATION:
            return createLocationSuggestions();
            
         case OP_REPEAT:
            return createRecurrenceSuggestions();
            
         case OP_ESTIMATE:
            return createEstimationSuggestions();
            
         default :
            throw new IllegalArgumentException( MessageFormat.format( "Unknown RTM smart add token type {0}",
                                                                      rtmSmartAddTokenType ) );
      }
   }
   
   
   
   private Collection< RtmSmartAddSuggestion > createDueDateSuggestions()
   {
      final Collection< RtmSmartAddSuggestion > suggestions = new ArrayList< RtmSmartAddSuggestion >( 7 );
      
      final RtmCalendar cal = calendarProvider.getToday();
      
      // Today
      suggestions.add( new RtmSmartAddSuggestion( context.getString( R.string.phr_today ),
                                                  Long.valueOf( cal.getTimeInMillis() ) ) );
      
      // Tomorrow
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      suggestions.add( new RtmSmartAddSuggestion( context.getString( R.string.phr_tomorrow ),
                                                  Long.valueOf( cal.getTimeInMillis() ) ) );
      
      // The next 5 days after tomorrow
      for ( int i = 0; i < 5; ++i )
      {
         cal.add( Calendar.DAY_OF_YEAR, 1 );
         final String weekDay = MolokoDateUtils.getDayOfWeekString( cal.get( Calendar.DAY_OF_WEEK ) );
         suggestions.add( new RtmSmartAddSuggestion( weekDay,
                                                     Long.valueOf( cal.getTimeInMillis() ) ) );
      }
      
      return suggestions;
   }
   
   
   
   private Collection< RtmSmartAddSuggestion > createPrioritySuggestions()
   {
      final Collection< RtmSmartAddSuggestion > suggestions = new ArrayList< RtmSmartAddSuggestion >( 4 );
      
      suggestions.add( new RtmSmartAddSuggestion( Priority.High.toString(),
                                                  null ) );
      suggestions.add( new RtmSmartAddSuggestion( Priority.Medium.toString(),
                                                  null ) );
      suggestions.add( new RtmSmartAddSuggestion( Priority.Low.toString(), null ) );
      suggestions.add( new RtmSmartAddSuggestion( Priority.None.toString(),
                                                  null ) );
      
      return suggestions;
   }
   
   
   
   private Collection< RtmSmartAddSuggestion > createListAndTagsSuggestions()
   {
      final Collection< RtmSmartAddSuggestion > suggestions = new ArrayList< RtmSmartAddSuggestion >();
      
      for ( TasksList tasksList : contentRepository.getPhysicalTasksLists() )
      {
         suggestions.add( new RtmSmartAddSuggestion( tasksList.getName(),
                                                     Long.valueOf( tasksList.getId() ) ) );
      }
      
      for ( String tag : contentRepository.getAllTags() )
      {
         suggestions.add( new RtmSmartAddSuggestion( tag, null ) );
      }
      
      return suggestions;
   }
   
   
   
   private Collection< RtmSmartAddSuggestion > createLocationSuggestions()
   {
      final Collection< RtmSmartAddSuggestion > suggestions = new ArrayList< RtmSmartAddSuggestion >();
      
      for ( Location rtmLocation : contentRepository.getAllLocations() )
      {
         suggestions.add( new RtmSmartAddSuggestion( rtmLocation.getName(),
                                                     Long.valueOf( rtmLocation.getId() ) ) );
      }
      
      return suggestions;
   }
   
   
   
   private Collection< RtmSmartAddSuggestion > createRecurrenceSuggestions()
   {
      final Collection< RtmSmartAddSuggestion > suggestions = new ArrayList< RtmSmartAddSuggestion >();
      
      for ( String suggestion : context.getResources()
                                       .getStringArray( R.array.app_quick_add_task_recurr_sugg_every ) )
      {
         addRecurrence( suggestions, suggestion );
      }
      
      for ( String suggestion : context.getResources()
                                       .getStringArray( R.array.app_quick_add_task_recurr_sugg_after ) )
      {
         addRecurrence( suggestions, suggestion );
      }
      
      return suggestions;
   }
   
   
   
   private Collection< RtmSmartAddSuggestion > createEstimationSuggestions()
   {
      final Collection< RtmSmartAddSuggestion > suggestions = new ArrayList< RtmSmartAddSuggestion >( 7 );
      
      addEstimate( suggestions, 2 * DateUtils.MINUTE_IN_MILLIS );
      addEstimate( suggestions, 5 * DateUtils.MINUTE_IN_MILLIS );
      addEstimate( suggestions, 10 * DateUtils.MINUTE_IN_MILLIS );
      addEstimate( suggestions, 15 * DateUtils.MINUTE_IN_MILLIS );
      addEstimate( suggestions, 30 * DateUtils.MINUTE_IN_MILLIS );
      addEstimate( suggestions, 45 * DateUtils.MINUTE_IN_MILLIS );
      addEstimate( suggestions, DateUtils.HOUR_IN_MILLIS );
      
      return suggestions;
   }
   
   
   
   private void addRecurrence( Collection< RtmSmartAddSuggestion > suggestions,
                               String sentence )
   {
      try
      {
         final Recurrence result = recurrenceParsing.parseRecurrence( sentence );
         final String translatedSentence = recurrenceParsing.parseRecurrencePatternToSentence( result.getPattern(),
                                                                                               result.isEveryRecurrence() );
         suggestions.add( new RtmSmartAddSuggestion( translatedSentence, result ) );
      }
      catch ( GrammarException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   private void addEstimate( Collection< RtmSmartAddSuggestion > suggestions,
                             long estimate )
   {
      suggestions.add( new RtmSmartAddSuggestion( dateFormatter.formatEstimated( estimate ),
                                                  Long.valueOf( estimate ) ) );
   }
}
