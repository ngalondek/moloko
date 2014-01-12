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

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import dev.drsoran.rtm.parsing.IDateFormatter;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternBaseVisitor;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.FirstWeekdayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.OpCountContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.OpIntervalContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.OpMonthContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.OpMonthDayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.OpUntilContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.OpWeekdaysContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.PatternDailyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.PatternMonthlyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.PatternWeeklyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.PatternYearlyContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.WeekdayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternParser.XStContext;
import dev.drsoran.rtm.parsing.lang.IRecurrenceSentenceLanguage;


public class RecurrenceSentenceEvaluator extends
         RecurrencePatternBaseVisitor< Void >
{
   private final static String DATE_PATTERN = "yyyyMMdd'T'HHmmss";
   
   private final StringBuilder sentence = new StringBuilder();
   
   private final IDateFormatter dateFormatter;
   
   private final IRecurrenceSentenceLanguage language;
   
   private final boolean isEvery;
   
   private String sentenceLanguageInterval;
   
   
   
   public RecurrenceSentenceEvaluator( IDateFormatter dateFormatter,
      IRecurrenceSentenceLanguage language, boolean isEvery )
   {
      this.dateFormatter = dateFormatter;
      this.language = language;
      this.isEvery = isEvery;
   }
   
   
   
   public String getSentence()
   {
      return sentence.toString();
   }
   
   
   
   @Override
   public Void visitPatternYearly( PatternYearlyContext ctx )
   {
      sentenceLanguageInterval = "year";
      return visitChildren( ctx );
   }
   
   
   
   @Override
   public Void visitPatternMonthly( PatternMonthlyContext ctx )
   {
      sentenceLanguageInterval = "month";
      return visitChildren( ctx );
   }
   
   
   
   @Override
   public Void visitPatternWeekly( PatternWeeklyContext ctx )
   {
      sentenceLanguageInterval = "week";
      return visitChildren( ctx );
   }
   
   
   
   @Override
   public Void visitPatternDaily( PatternDailyContext ctx )
   {
      sentenceLanguageInterval = "day";
      return visitChildren( ctx );
   }
   
   
   
   @Override
   public Void visitOpInterval( OpIntervalContext ctx )
   {
      if ( isEvery )
      {
         language.appendEvery( sentence,
                               sentenceLanguageInterval,
                               ctx.i.getText() );
      }
      else
      {
         language.appendAfter( sentence,
                               sentenceLanguageInterval,
                               ctx.i.getText() );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitOpMonth( OpMonthContext ctx )
   {
      final int monthNum = Integer.parseInt( ctx.m.getText() );
      
      if ( monthNum < 1 || monthNum > 12 )
      {
         throw new ParseCancellationException( MessageFormat.format( "Invalid month number {0} in token {1}",
                                                                     monthNum,
                                                                     ctx.m.getText() ) );
      }
      
      sentence.append( " " );
      language.append( sentence, "in" );
      sentence.append( " " );
      language.append( sentence, "m" + ctx.m.getText() );
      
      return null;
   }
   
   
   
   @Override
   public Void visitOpMonthDay( OpMonthDayContext ctx )
   {
      appendOnThe();
      concatChildrenByComma( ctx );
      
      return null;
   }
   
   
   
   @Override
   public Void visitOpWeekdays( OpWeekdaysContext ctx )
   {
      concatChildrenByComma( ctx );
      return null;
   }
   
   
   
   @Override
   public Void visitOpUntil( OpUntilContext ctx )
   {
      final String formatedDate = formatDateUntil( ctx.date.getText() );
      
      if ( formatedDate != null )
      {
         sentence.append( " " );
         language.append( sentence, "until" );
         sentence.append( " " ).append( formatedDate );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitOpCount( OpCountContext ctx )
   {
      sentence.append( " " );
      language.append( sentence, "for" );
      sentence.append( " " ).append( ctx.count.getText() ).append( " " );
      language.append( sentence, "times" );
      
      return null;
   }
   
   
   
   @Override
   public Void visitFirstWeekday( FirstWeekdayContext ctx )
   {
      appendOnThe();
      
      if ( ctx.xSt() != null )
      {
         visit( ctx.xSt() );
         sentence.append( " " );
      }
      
      visit( ctx.weekday() );
      
      return null;
   }
   
   
   
   @Override
   public Void visitWeekday( WeekdayContext ctx )
   {
      language.append( sentence, ctx.wd.getText() );
      return null;
   }
   
   
   
   @Override
   public Void visitXSt( XStContext ctx )
   {
      final int xSt = Integer.parseInt( ctx.x.getText() );
      
      if ( xSt < 0 )
      {
         if ( xSt < -1 )
         {
            language.appendStToX( sentence, xSt * -1 );
            sentence.append( " " );
         }
         
         language.append( sentence, "last" );
      }
      else
      {
         language.appendStToX( sentence, xSt );
      }
      
      return null;
   }
   
   
   
   private void appendOnThe()
   {
      sentence.append( " " );
      language.append( sentence, "on_the" );
      sentence.append( " " );
   }
   
   
   
   private void concatChildrenByComma( ParserRuleContext ctx )
   {
      boolean isFirst = true;
      for ( ParseTree child : ctx.children )
      {
         if ( !( child instanceof TerminalNode ) )
         {
            if ( !isFirst )
            {
               sentence.append( ", " );
            }
            
            visit( child );
            isFirst = false;
         }
      }
   }
   
   
   
   private final String formatDateUntil( String value )
   {
      final SimpleDateFormat sdf = new SimpleDateFormat( DATE_PATTERN );
      sdf.setTimeZone( TimeZone.getDefault() );
      
      try
      {
         sdf.parse( value );
         return dateFormatter.formatDateNumeric( sdf.getCalendar()
                                                    .getTimeInMillis() );
      }
      catch ( ParseException e )
      {
         return null;
      }
   }
}
