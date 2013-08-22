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
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.AMContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateEndOfTheMonthOrWeekContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateIn_X_YMWDContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateIn_X_YMWD_distanceContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateNeverContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateNumericContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnMonthTheXstContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnTheXstOfMonthContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnWeekdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.HmsSeparatedTimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.MiddayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.MidnightContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.NowContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.PMContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.ParseDateContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.ParseDateTimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.ParseDateWithinContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.ParseTimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.ParseTimeEstimateContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.SeparatedTimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.SeparatedTimeHMContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.SeparatedTimeHMSContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecFloatHoursContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecUnitHoursContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecUnitMinutesContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecUnitSecondsContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TimeNeverContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TodayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.TomorrowContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.UnSeparatedTimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.YesterdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParserVisitor;


public class RtmLikeDateTimeEvaluator implements DateTimeParserVisitor< Void >
{
   private final DateTimeEvaluator decorated;
   
   private final MolokoCalenderProvider calendarProvider;
   
   private boolean hasVisitedDate;
   
   private boolean hasVisitedTime;
   
   
   
   public RtmLikeDateTimeEvaluator( DateTimeEvaluator decorated,
      MolokoCalenderProvider calenderProvider )
   {
      this.decorated = decorated;
      this.calendarProvider = calenderProvider;
   }
   
   
   
   @Override
   public Void visitParseDateTime( ParseDateTimeContext ctx )
   {
      decorated.visitParseDateTime( ctx );
      
      // If we haven't parsed a date yet, e.g. today@12, the evaluator can adjust the day of week for times in the past.
      // E.g. @12.
      final MolokoCalendar cal = getCalendar();
      
      if ( hasVisitedTime && !hasVisitedDate )
      {
         final MolokoCalendar now = calendarProvider.getNow();
         if ( cal.before( now ) )
         {
            cal.add( Calendar.DAY_OF_WEEK, 1 );
         }
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDate( DateContext ctx )
   {
      hasVisitedDate = true;
      return decorated.visitDate( ctx );
   }
   
   
   
   @Override
   public Void visitTime( TimeContext ctx )
   {
      hasVisitedTime = true;
      return decorated.visitTime( ctx );
   }
   
   
   
   @Override
   public Void visitDateNumeric( DateNumericContext ctx )
   {
      decorated.visitDateNumeric( ctx );
      adjustForDatesInPast( ctx.pt2, ctx.pt3 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnTheXstOfMonth( DateOnTheXstOfMonthContext ctx )
   {
      decorated.visitDateOnTheXstOfMonth( ctx );
      adjustForDatesInPast( ctx.m, ctx.y );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnMonthTheXst( DateOnMonthTheXstContext ctx )
   {
      decorated.visitDateOnMonthTheXst( ctx );
      adjustForDatesInPast( ctx.m, ctx.y );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnWeekday( DateOnWeekdayContext ctx )
   {
      decorated.visitDateOnWeekday( ctx );
      
      final MolokoCalendar now = calendarProvider.getNow();
      final MolokoCalendar cal = decorated.getCalendar();
      
      // If the weekday is before today or today we adjust to next week.
      if ( cal.compareTo( now.toCalendar() ) <= 0 )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateIn_X_YMWD( DateIn_X_YMWDContext ctx )
   {
      return decorated.visitDateIn_X_YMWD( ctx );
   }
   
   
   
   @Override
   public Void visitDateOn( DateOnContext ctx )
   {
      return decorated.visitDateOn( ctx );
   }
   
   
   
   @Override
   public Void visitToday( TodayContext ctx )
   {
      return decorated.visitToday( ctx );
   }
   
   
   
   public MolokoCalendar getCalendar()
   {
      return decorated.getCalendar();
   }
   
   
   
   public MolokoCalendar getEpochStart()
   {
      return decorated.getEpochStart();
   }
   
   
   
   public MolokoCalendar getEpochEnd()
   {
      return decorated.getEpochEnd();
   }
   
   
   
   @Override
   public Void visitYesterday( YesterdayContext ctx )
   {
      return decorated.visitYesterday( ctx );
   }
   
   
   
   @Override
   public Void visitNow( NowContext ctx )
   {
      return decorated.visitNow( ctx );
   }
   
   
   
   @Override
   public Void visit( ParseTree tree )
   {
      return decorated.visit( tree );
   }
   
   
   
   @Override
   public Void visitDateIn_X_YMWD_distance( DateIn_X_YMWD_distanceContext ctx )
   {
      return decorated.visitDateIn_X_YMWD_distance( ctx );
   }
   
   
   
   @Override
   public Void visitDateEndOfTheMonthOrWeek( DateEndOfTheMonthOrWeekContext ctx )
   {
      return decorated.visitDateEndOfTheMonthOrWeek( ctx );
   }
   
   
   
   @Override
   public Void visitTomorrow( TomorrowContext ctx )
   {
      return decorated.visitTomorrow( ctx );
   }
   
   
   
   @Override
   public Void visitDateNever( DateNeverContext ctx )
   {
      return decorated.visitDateNever( ctx );
   }
   
   
   
   @Override
   public Void visitTimeNever( TimeNeverContext ctx )
   {
      return decorated.visitTimeNever( ctx );
   }
   
   
   
   @Override
   public Void visitTerminal( TerminalNode node )
   {
      return decorated.visitTerminal( node );
   }
   
   
   
   @Override
   public Void visitHmsSeparatedTime( HmsSeparatedTimeContext ctx )
   {
      return decorated.visitHmsSeparatedTime( ctx );
   }
   
   
   
   @Override
   public Void visitParseTimeEstimate( ParseTimeEstimateContext ctx )
   {
      return decorated.visitParseTimeEstimate( ctx );
   }
   
   
   
   @Override
   public Void visitSeparatedTime( SeparatedTimeContext ctx )
   {
      return decorated.visitSeparatedTime( ctx );
   }
   
   
   
   @Override
   public Void visitMidnight( MidnightContext ctx )
   {
      return decorated.visitMidnight( ctx );
   }
   
   
   
   @Override
   public Void visitMidday( MiddayContext ctx )
   {
      return decorated.visitMidday( ctx );
   }
   
   
   
   @Override
   public Void visitSeparatedTimeHM( SeparatedTimeHMContext ctx )
   {
      return decorated.visitSeparatedTimeHM( ctx );
   }
   
   
   
   @Override
   public Void visitSeparatedTimeHMS( SeparatedTimeHMSContext ctx )
   {
      return decorated.visitSeparatedTimeHMS( ctx );
   }
   
   
   
   @Override
   public Void visitUnSeparatedTime( UnSeparatedTimeContext ctx )
   {
      return decorated.visitUnSeparatedTime( ctx );
   }
   
   
   
   @Override
   public Void visitAM( AMContext ctx )
   {
      return decorated.visitAM( ctx );
   }
   
   
   
   @Override
   public Void visitPM( PMContext ctx )
   {
      return decorated.visitPM( ctx );
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecFloatHours( TimeNaturalSpecFloatHoursContext ctx )
   {
      return decorated.visitTimeNaturalSpecFloatHours( ctx );
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitHours( TimeNaturalSpecUnitHoursContext ctx )
   {
      return decorated.visitTimeNaturalSpecUnitHours( ctx );
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitMinutes( TimeNaturalSpecUnitMinutesContext ctx )
   {
      return decorated.visitTimeNaturalSpecUnitMinutes( ctx );
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitSeconds( TimeNaturalSpecUnitSecondsContext ctx )
   {
      return decorated.visitTimeNaturalSpecUnitSeconds( ctx );
   }
   
   
   
   @Override
   public Void visitParseDate( ParseDateContext ctx )
   {
      return decorated.visitParseDate( ctx );
   }
   
   
   
   @Override
   public Void visitParseDateWithin( ParseDateWithinContext ctx )
   {
      return decorated.visitParseDateWithin( ctx );
   }
   
   
   
   @Override
   public Void visitParseTime( ParseTimeContext ctx )
   {
      return decorated.visitParseTime( ctx );
   }
   
   
   
   @Override
   public Void visitChildren( RuleNode arg0 )
   {
      return decorated.visitChildren( arg0 );
   }
   
   
   
   @Override
   public Void visitErrorNode( ErrorNode node )
   {
      return decorated.visitErrorNode( node );
   }
   
   
   
   private void adjustForDatesInPast( Token month, Token year )
   {
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( year == null )
      {
         final MolokoCalendar cal = decorated.getCalendar();
         final MolokoCalendar today = calendarProvider.getToday();
         
         if ( cal.before( today ) )
         {
            // if we have a month ...
            if ( month != null )
            {
               // ... and the current month is different, we roll to next year
               if ( cal.get( Calendar.MONTH ) != today.get( Calendar.MONTH ) )
               {
                  cal.add( Calendar.YEAR, 1 );
               }
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
