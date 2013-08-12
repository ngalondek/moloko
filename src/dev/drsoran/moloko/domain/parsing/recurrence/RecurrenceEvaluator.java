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

package dev.drsoran.moloko.domain.parsing.recurrence;

import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_FRI;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_MON;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_SAT;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_SUN;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_THU;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_TUE;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.BYDAY_WED;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.DATE_PATTERN;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.IS_EVERY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTH;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTHDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_COUNT;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_FREQ;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_INTERVAL;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_UNTIL;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_DAILY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_MONTHLY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_WEEKLY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_YEARLY;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.antlr.v4.runtime.misc.ParseCancellationException;

import android.os.ParcelFormatException;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.BiweeklyContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.BuisinessDaysContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.EveryAfterContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.IntervalNumberContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.IntervalTextContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.MonthContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrDailyContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrMonthlyContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrMonthlyOnXstContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrOnWeekdaysContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrWeeklyContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrWeeklyOnWeekdaysContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RecurrYearlyContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.RepeatContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.UntilContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.WeekdayContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.WeekendContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser.XstContext;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParserBaseVisitor;
import dev.drsoran.moloko.util.Strings;


public class RecurrenceEvaluator extends RecurrenceParserBaseVisitor< Void >
{
   private final EvaluatorState evaluatorState = new EvaluatorState();
   
   private final IDateTimeParsing dateTimeParsing;
   
   private final ILanguage dateLanguage;
   
   
   
   public RecurrenceEvaluator( IDateTimeParsing dateTimeParsing,
      ILanguage dateLanguage )
   {
      this.dateTimeParsing = dateTimeParsing;
      this.dateLanguage = dateLanguage;
   }
   
   
   
   public Map< String, Object > getRecurrencePattern()
   {
      final Map< String, Object > pattern = new TreeMap< String, Object >( new RecurrencePatternOperatorComp() );
      
      pattern.put( IS_EVERY, Boolean.valueOf( evaluatorState.isEvery ) );
      pattern.put( OP_FREQ, evaluatorState.recurrence );
      pattern.put( OP_INTERVAL, Integer.valueOf( evaluatorState.interval ) );
      
      for ( String resolution : evaluatorState.resolution.keySet() )
      {
         pattern.put( resolution, evaluatorState.resolution.get( resolution ) );
      }
      
      if ( evaluatorState.until != null )
      {
         pattern.put( OP_UNTIL, evaluatorState.until );
      }
      
      if ( evaluatorState.count != null )
      {
         pattern.put( OP_COUNT, evaluatorState.count );
      }
      
      return pattern;
   }
   
   
   
   @Override
   public Void visitEveryAfter( EveryAfterContext ctx )
   {
      evaluatorState.isEvery = ctx.EVERY() != null;
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrDaily( RecurrDailyContext ctx )
   {
      evaluatorState.isEvery = true;
      evaluatorState.recurrence = VAL_DAILY;
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrWeekly( RecurrWeeklyContext ctx )
   {
      evaluatorState.recurrence = VAL_WEEKLY;
      return visitChildren( ctx );
   }
   
   
   
   @Override
   public Void visitRecurrWeeklyOnWeekdays( RecurrWeeklyOnWeekdaysContext ctx )
   {
      visitChildren( ctx );
      evaluatorState.addResolution( OP_BYDAY, evaluatorState.weekdays );
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrMonthly( RecurrMonthlyContext ctx )
   {
      evaluatorState.recurrence = VAL_MONTHLY;
      return visitChildren( ctx );
   }
   
   
   
   @Override
   public Void visitRecurrMonthlyOnXst( RecurrMonthlyOnXstContext ctx )
   {
      visitChildren( ctx );
      evaluatorState.addResolution( OP_BYMONTHDAY, evaluatorState.xsts );
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrYearly( RecurrYearlyContext ctx )
   {
      evaluatorState.recurrence = VAL_YEARLY;
      visitChildren( ctx );
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrOnWeekdays( RecurrOnWeekdaysContext ctx )
   {
      visit( ctx.multipleXst() );
      visit( ctx.weekdays() );
      
      int lastXst = evaluatorState.lastXstOrDefault( 1 );
      
      if ( ctx.LAST() != null )
      {
         lastXst = -lastXst;
      }
      
      final List< String > weekdaysWithXst = new ArrayList< String >( evaluatorState.weekdays.size() );
      final String lastXstStr = String.valueOf( lastXst );
      for ( String weekday : evaluatorState.weekdays )
      {
         weekdaysWithXst.add( lastXstStr + weekday );
      }
      
      evaluatorState.addResolution( OP_BYDAY, weekdaysWithXst );
      
      return null;
   }
   
   
   
   @Override
   public Void visitWeekday( WeekdayContext ctx )
   {
      if ( ctx.MONDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_MON );
      }
      else if ( ctx.TUESDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_TUE );
      }
      else if ( ctx.WEDNESDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_WED );
      }
      else if ( ctx.THURSDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_THU );
      }
      else if ( ctx.FRIDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_FRI );
      }
      else if ( ctx.SATURDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_SAT );
      }
      else if ( ctx.SUNDAY() != null )
      {
         evaluatorState.weekdays.add( BYDAY_SUN );
      }
      else
      {
         visitChildren( ctx );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitWeekend( WeekendContext ctx )
   {
      evaluatorState.weekdays.add( BYDAY_SAT );
      evaluatorState.weekdays.add( BYDAY_SUN );
      
      return null;
   }
   
   
   
   @Override
   public Void visitBuisinessDays( BuisinessDaysContext ctx )
   {
      evaluatorState.weekdays.add( BYDAY_MON );
      evaluatorState.weekdays.add( BYDAY_TUE );
      evaluatorState.weekdays.add( BYDAY_WED );
      evaluatorState.weekdays.add( BYDAY_THU );
      evaluatorState.weekdays.add( BYDAY_FRI );
      
      return null;
   }
   
   
   
   @Override
   public Void visitBiweekly( BiweeklyContext ctx )
   {
      evaluatorState.interval = 2;
      return null;
   }
   
   
   
   @Override
   public Void visitXst( XstContext ctx ) throws ParcelFormatException
   {
      final Integer xst;
      
      if ( ctx.INT() != null )
      {
         xst = limitMonthDay( Integer.parseInt( ctx.INT().getText() ) );
      }
      else if ( ctx.FIRST() != null )
      {
         xst = 1;
      }
      else if ( ctx.SECOND() != null || ctx.OTHER() != null )
      {
         xst = 2;
      }
      else if ( ctx.THIRD() != null )
      {
         xst = 3;
      }
      else if ( ctx.FOURTH() != null )
      {
         xst = 4;
      }
      else if ( ctx.FIFTH() != null )
      {
         xst = 5;
      }
      else
      {
         throw new ParcelFormatException( "Unhandled token " + ctx.getText() );
      }
      
      evaluatorState.xsts.add( xst );
      
      return null;
   }
   
   
   
   @Override
   public Void visitMonth( MonthContext ctx )
   {
      evaluatorState.addResolution( OP_BYMONTH,
                                    dateLanguage.getString( ctx.getText() ) );
      return null;
   }
   
   
   
   @Override
   public Void visitIntervalNumber( IntervalNumberContext ctx )
   {
      evaluatorState.interval = Integer.parseInt( ctx.INT().getText() );
      return null;
   }
   
   
   
   @Override
   public Void visitIntervalText( IntervalTextContext ctx )
   {
      if ( ctx.NUM_ONE() != null )
      {
         evaluatorState.interval = 1;
      }
      else if ( ctx.NUM_TWO() != null )
      {
         evaluatorState.interval = 2;
      }
      else if ( ctx.NUM_THREE() != null )
      {
         evaluatorState.interval = 3;
      }
      else if ( ctx.NUM_FOUR() != null )
      {
         evaluatorState.interval = 4;
      }
      else if ( ctx.NUM_FIVE() != null )
      {
         evaluatorState.interval = 5;
      }
      else if ( ctx.NUM_SIX() != null )
      {
         evaluatorState.interval = 6;
      }
      else if ( ctx.NUM_SEVEN() != null )
      {
         evaluatorState.interval = 7;
      }
      else if ( ctx.NUM_EIGHT() != null )
      {
         evaluatorState.interval = 8;
      }
      else if ( ctx.NUM_NINE() != null )
      {
         evaluatorState.interval = 9;
      }
      else if ( ctx.NUM_TEN() != null )
      {
         evaluatorState.interval = 10;
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitUntil( UntilContext ctx )
   {
      setUntil( ctx.s.getText() );
      return null;
   }
   
   
   
   @Override
   public Void visitRepeat( RepeatContext ctx ) throws ParseCancellationException
   {
      int cnt = Integer.parseInt( ctx.c.getText() );
      if ( cnt > 1 )
      {
         evaluatorState.count = Integer.valueOf( cnt );
      }
      
      return null;
   }
   
   
   
   private void setUntil( String dateTimeString )
   {
      try
      {
         final MolokoCalendar untilDate = dateTimeParsing.parseDateTime( dateTimeString );
         final SimpleDateFormat sdf = new SimpleDateFormat( DATE_PATTERN );
         
         evaluatorState.until = sdf.format( untilDate.getTime() );
      }
      catch ( GrammarException e )
      {
      }
   }
   
   
   
   private int limitMonthDay( int rawMonthDay )
   {
      if ( rawMonthDay < 1 )
      {
         return 1;
      }
      else if ( rawMonthDay > 31 )
      {
         return 31;
      }
      else
      {
         return rawMonthDay;
      }
   }
   
   
   private final static class EvaluatorState
   {
      public boolean isEvery;
      
      public String recurrence;
      
      public int interval = 1;
      
      public Map< String, String > resolution = new LinkedHashMap< String, String >();
      
      public final Set< String > weekdays = new LinkedHashSet< String >();
      
      public final SortedSet< Integer > xsts = new TreeSet< Integer >();
      
      public String until;
      
      public Integer count;
      
      
      
      public int lastXstOrDefault( int defaultVal )
      {
         return Integer.valueOf( xsts.last() );
      }
      
      
      
      public void addResolution( String resolution, String resolutionValue )
      {
         this.resolution.put( resolution, resolutionValue );
      }
      
      
      
      public < T > void addResolution( String resolution,
                                       Iterable< T > resolutionValues )
      {
         this.resolution.put( resolution, Strings.join( ",", resolutionValues ) );
      }
   }
}
