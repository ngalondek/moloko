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

package dev.drsoran.rtm.parsing.recurrence;

import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_FRI;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_MON;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_SAT;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_SUN;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_THU;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_TUE;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.BYDAY_WED;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.DATE_PATTERN;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.IS_EVERY;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_BYDAY;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTH;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTHDAY;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_COUNT;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_FREQ;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_INTERVAL;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.OP_UNTIL;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.VAL_DAILY;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.VAL_MONTHLY;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.VAL_WEEKLY;
import static dev.drsoran.rtm.parsing.recurrence.RecurrencePatternSyntax.VAL_YEARLY;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import dev.drsoran.Strings;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.BiWeeklyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.BuisinessDaysContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.CountContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.DailyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.EveryAfterContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.FreqDailyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.FreqMonthlyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.FreqWeeklyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.FreqYearlyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.IntervalNumberContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.IntervalTextContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.LastWeekdaysContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.MonthContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.MultipleXstWeekdaysContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.RecurrOnWeekdaysContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.RecurrOnXstContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.RecurrOnXstWeekdaysContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.RecurrOnXstWeekdaysOfMonthContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.UntilContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.WeekdayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.WeekendContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser.XstContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParserBaseVisitor;
import dev.drsoran.rtm.parsing.lang.ILanguage;


public class RecurrenceEvaluator extends RecurrenceParserBaseVisitor< Void >
{
   private final EvaluatorState evaluatorState = new EvaluatorState();
   
   private final IRtmDateTimeParsing dateTimeParsing;
   
   private final ILanguage dateLanguage;
   
   
   
   public RecurrenceEvaluator( IRtmDateTimeParsing dateTimeParsing,
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
   public Void visitDaily( DailyContext ctx )
   {
      evaluatorState.isEvery = true;
      evaluatorState.recurrence = VAL_DAILY;
      evaluatorState.interval = 1;
      
      return null;
   }
   
   
   
   @Override
   public Void visitBiWeekly( BiWeeklyContext ctx )
   {
      evaluatorState.isEvery = true;
      evaluatorState.recurrence = VAL_WEEKLY;
      evaluatorState.interval = 2;
      
      return null;
   }
   
   
   
   @Override
   public Void visitFreqDaily( FreqDailyContext ctx )
   {
      evaluatorState.recurrence = VAL_DAILY;
      return null;
   }
   
   
   
   @Override
   public Void visitFreqWeekly( FreqWeeklyContext ctx )
   {
      evaluatorState.recurrence = VAL_WEEKLY;
      return null;
   }
   
   
   
   @Override
   public Void visitFreqMonthly( FreqMonthlyContext ctx )
   {
      evaluatorState.recurrence = VAL_MONTHLY;
      return null;
   }
   
   
   
   @Override
   public Void visitFreqYearly( FreqYearlyContext ctx )
   {
      evaluatorState.recurrence = VAL_YEARLY;
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrOnXst( RecurrOnXstContext ctx )
   {
      visitChildren( ctx );
      
      evaluatorState.recurrence = VAL_MONTHLY;
      evaluatorState.addResolution( OP_BYMONTHDAY, evaluatorState.xsts );
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrOnWeekdays( RecurrOnWeekdaysContext ctx )
   {
      visitChildren( ctx );
      
      evaluatorState.recurrence = VAL_WEEKLY;
      evaluatorState.addResolution( OP_BYDAY, evaluatorState.weekdays );
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrOnXstWeekdays( RecurrOnXstWeekdaysContext ctx )
   {
      evaluatorState.recurrence = VAL_MONTHLY;
      
      visitChildren( ctx );
      
      return null;
   }
   
   
   
   @Override
   public Void visitRecurrOnXstWeekdaysOfMonth( RecurrOnXstWeekdaysOfMonthContext ctx )
   {
      evaluatorState.recurrence = VAL_YEARLY;
      evaluatorState.interval = 1;
      
      visitChildren( ctx );
      
      return null;
   }
   
   
   
   @Override
   public Void visitMultipleXstWeekdays( MultipleXstWeekdaysContext ctx )
   {
      visit( ctx.multipleXst() );
      visit( ctx.weekdays() );
      
      int lastXst = evaluatorState.lastXstOrDefault( 1 );
      
      if ( ctx.LAST() != null )
      {
         lastXst = -lastXst;
      }
      
      evaluatorState.addResolution( OP_BYDAY, addWeekdaysWithXst( lastXst ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitLastWeekdays( LastWeekdaysContext ctx )
   {
      visitChildren( ctx );
      evaluatorState.addResolution( OP_BYDAY, addWeekdaysWithXst( -1 ) );
      
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
      // Add 1 because the dateLanguage will return Calendar constants beginning with 0
      evaluatorState.addResolution( OP_BYMONTH,
                                    dateLanguage.getInteger( ctx.getText() ) + 1 );
      return null;
   }
   
   
   
   @Override
   public Void visitIntervalNumber( IntervalNumberContext ctx )
   {
      evaluatorState.interval = Math.max( Integer.parseInt( ctx.INT().getText() ),
                                          1 );
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
   public Void visitCount( CountContext ctx ) throws ParseCancellationException
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
         final RtmCalendar untilDate = dateTimeParsing.parseDateTime( dateTimeString );
         final SimpleDateFormat sdf = new SimpleDateFormat( DATE_PATTERN );
         
         evaluatorState.until = sdf.format( untilDate.getTime() );
      }
      catch ( GrammarException e )
      {
      }
   }
   
   
   
   private Collection< String > addWeekdaysWithXst( int xst )
   {
      final List< String > weekdaysWithXst = new ArrayList< String >( evaluatorState.weekdays.size() );
      final String lastXstStr = String.valueOf( xst );
      for ( String weekday : evaluatorState.weekdays )
      {
         weekdaysWithXst.add( lastXstStr + weekday );
      }
      
      return weekdaysWithXst;
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
      
      
      
      public void addResolution( String resolution, Integer resolutionValue )
      {
         this.resolution.put( resolution, String.valueOf( resolutionValue ) );
      }
      
      
      
      public < T > void addResolution( String resolution,
                                       Iterable< T > resolutionValues )
      {
         this.resolution.put( resolution, Strings.join( ",", resolutionValues ) );
      }
   }
}
