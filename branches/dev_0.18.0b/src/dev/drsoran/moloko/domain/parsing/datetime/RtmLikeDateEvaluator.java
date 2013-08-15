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
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateEndOfTheMonthOrWeekContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateIn_X_YMWDContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateIn_X_YMWD_distanceContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateNumericContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnMonthTheXstContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnTheXstOfMonthContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnWeekdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.NeverContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.NowContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.ParseDateContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.ParseDateWithinContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.TodayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.TomorrowContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.YesterdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParserVisitor;


public class RtmLikeDateEvaluator implements DateParserVisitor< Void >
{
   private final DateEvaluator decorated;
   
   private final MolokoCalenderProvider calendarProvider;
   
   
   
   public RtmLikeDateEvaluator( DateEvaluator decorated,
      MolokoCalenderProvider calenderProvider )
   {
      this.decorated = decorated;
      this.calendarProvider = calenderProvider;
   }
   
   
   
   @Override
   public Void visitDateNumeric( DateNumericContext ctx )
   {
      decorated.visitDateNumeric( ctx );
      
      // if year is missing and the date is
      // before now we roll to the next year.
      if ( ctx.pt3 == null )
      {
         final MolokoCalendar cal = decorated.getCalendar();
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
      final int currentWeekDay = now.get( Calendar.DAY_OF_WEEK );
      
      final MolokoCalendar cal = decorated.getCalendar();
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
   
   
   
   public boolean isClearTime()
   {
      return decorated.isClearTime();
   }
   
   
   
   public void setClearTime( boolean clearTime )
   {
      decorated.setClearTime( clearTime );
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
   public Void visitChildren( RuleNode arg0 )
   {
      return decorated.visitChildren( arg0 );
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
   public Void visitErrorNode( ErrorNode node )
   {
      return decorated.visitErrorNode( node );
   }
   
   
   
   @Override
   public Void visitTomorrow( TomorrowContext ctx )
   {
      return decorated.visitTomorrow( ctx );
   }
   
   
   
   @Override
   public Void visitNever( NeverContext ctx )
   {
      return decorated.visitNever( ctx );
   }
   
   
   
   @Override
   public Void visitTerminal( TerminalNode node )
   {
      return decorated.visitTerminal( node );
   }
   
   
   
   private void adjustForDatesInPast( Token month, Token year )
   {
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( year == null )
      {
         final MolokoCalendar cal = decorated.getCalendar();
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
