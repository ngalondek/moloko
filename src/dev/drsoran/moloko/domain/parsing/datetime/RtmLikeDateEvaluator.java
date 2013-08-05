/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.domain.parsing.datetime;

import java.util.Calendar;

import org.antlr.v4.runtime.Token;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.IDateFormatter;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateNumericContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnMonthTheXstContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnTheXstOfMonthContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnWeekdayContext;


public class RtmLikeDateEvaluator extends DateEvaluator
{
   private final MolokoCalenderProvider calendarProvider;
   
   
   
   public RtmLikeDateEvaluator( ILanguage dateLanguage,
      IDateFormatter dateFormatter, MolokoCalenderProvider calenderProvider )
   {
      this( dateLanguage,
            dateFormatter,
            calenderProvider,
            calenderProvider.getNow() );
   }
   
   
   
   public RtmLikeDateEvaluator( ILanguage dateLanguage,
      IDateFormatter dateFormatter, MolokoCalenderProvider calenderProvider,
      MolokoCalendar cal )
   {
      super( dateLanguage, dateFormatter, calenderProvider, cal );
      this.calendarProvider = calenderProvider;
   }
   
   
   
   @Override
   public Void visitDateNumeric( DateNumericContext ctx )
   {
      super.visitDateNumeric( ctx );
      
      final MolokoCalendar cal = getCalendar();
      
      // if year is missing and the date is
      // before now we roll to the next year.
      if ( ctx.pt3 == null )
      {
         final MolokoCalendar now = calendarProvider.getNow();
         
         if ( cal.before( now ) )
         {
            cal.add( Calendar.YEAR, 1 );
         }
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnTheXstOfMonth( DateOnTheXstOfMonthContext ctx )
   {
      super.visitDateOnTheXstOfMonth( ctx );
      adjustForDatesInPast( ctx.m, ctx.y );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnMonthTheXst( DateOnMonthTheXstContext ctx )
   {
      super.visitDateOnMonthTheXst( ctx );
      adjustForDatesInPast( ctx.m, ctx.y );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnWeekday( DateOnWeekdayContext ctx )
   {
      super.visitDateOnWeekday( ctx );
      
      final MolokoCalendar now = calendarProvider.getNow();
      final int currentWeekDay = now.get( Calendar.DAY_OF_WEEK );
      
      final MolokoCalendar cal = getCalendar();
      final int weekday = cal.get( Calendar.DAY_OF_WEEK );
      
      // If:
      // - the weekday is before today or today,
      // - today is Sunday
      // we adjust to next week.
      if ( weekday <= currentWeekDay || currentWeekDay == Calendar.SUNDAY )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }
      
      return null;
   }
   
   
   
   private void adjustForDatesInPast( Token month, Token year )
   {
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( year == null )
      {
         final MolokoCalendar cal = getCalendar();
         final MolokoCalendar now = calendarProvider.getNow();
         
         if ( cal.before( now ) )
         {
            // if we have a month, we roll to next year.
            if ( month != null )
            {
               cal.add( Calendar.YEAR, 1 );
            }
            // if we only have a day, we roll to next month.
            else
            {
               cal.add( Calendar.MONTH, 1 );
            }
         }
      }
   }
}
