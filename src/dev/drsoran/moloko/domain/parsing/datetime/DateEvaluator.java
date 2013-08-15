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

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.IDateFormatter;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateEndOfTheMonthOrWeekContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateIn_X_YMWD_distanceContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateNumericContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnMonthTheXstContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnTheXstOfMonthContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnWeekdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.NeverContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.NowContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.ParseDateContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.ParseDateWithinContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.TomorrowContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.YesterdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParserBaseVisitor;
import dev.drsoran.moloko.util.Pair;


public class DateEvaluator extends DateParserBaseVisitor< Void >
{
   private final MolokoCalendar cal;
   
   private final ILanguage dateLanguage;
   
   private final IDateFormatter dateFormatter;
   
   private final MolokoCalenderProvider calendarProvider;
   
   private boolean parsedNowLiteral;
   
   private boolean parsedNeverLiteral;
   
   private MolokoCalendar epochEnd;
   
   private boolean clearTime;
   
   
   
   public DateEvaluator( ILanguage dateLanguage, IDateFormatter dateFormatter,
      MolokoCalenderProvider calenderProvider )
   {
      this( dateLanguage,
            dateFormatter,
            calenderProvider,
            calenderProvider.getToday() );
   }
   
   
   
   public DateEvaluator( ILanguage dateLanguage, IDateFormatter dateFormatter,
      MolokoCalenderProvider calenderProvider, MolokoCalendar cal )
   {
      if ( dateLanguage == null )
      {
         throw new IllegalArgumentException( "dateLanguage" );
      }
      
      if ( dateFormatter == null )
      {
         throw new IllegalArgumentException( "dateFormatter" );
      }
      
      if ( calenderProvider == null )
      {
         throw new IllegalArgumentException( "calenderProvider" );
      }
      
      if ( cal == null )
      {
         throw new IllegalArgumentException( "cal" );
      }
      
      this.dateLanguage = dateLanguage;
      this.dateFormatter = dateFormatter;
      this.calendarProvider = calenderProvider;
      this.cal = cal;
   }
   
   
   
   public MolokoCalendar getCalendar()
   {
      return cal;
   }
   
   
   
   public MolokoCalendar getEpochStart()
   {
      return cal;
   }
   
   
   
   public MolokoCalendar getEpochEnd()
   {
      return epochEnd;
   }
   
   
   
   public boolean isClearTime()
   {
      return clearTime;
   }
   
   
   
   public void setClearTime( boolean clearTime )
   {
      this.clearTime = clearTime;
   }
   
   
   
   @Override
   public Void visitParseDate( ParseDateContext ctx )
   {
      super.visitParseDate( ctx );
      
      if ( !parsedNowLiteral && !parsedNeverLiteral )
      {
         cal.setHasDate( true );
      }
      
      if ( !parsedNowLiteral && clearTime )
      {
         cal.setHasTime( false );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitParseDateWithin( ParseDateWithinContext ctx )
   {
      if ( ctx.OF() != null )
      {
         visit( ctx.parseDate() );
      }
      
      if ( !parsedNeverLiteral )
      {
         final Pair< Integer, Integer > distance = getPointInDistance( ctx.amount,
                                                                       ctx.amountStr,
                                                                       ctx.y,
                                                                       ctx.m,
                                                                       ctx.w,
                                                                       ctx.d );
         
         epochEnd = calendarProvider.getNow();
         epochEnd.setTimeInMillis( cal.getTimeInMillis() );
         epochEnd.add( distance.second, distance.first );
      }
      else
      {
         epochEnd = MolokoCalendar.getDatelessAndTimelessInstance();
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateNumeric( DateNumericContext ctx )
   {
      parseNumericDate( cal,
                        ctx.pt1.getText(),
                        ctx.pt2.getText(),
                        ctx.pt3 != null ? ctx.pt3.getText() : null );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnTheXstOfMonth( DateOnTheXstOfMonthContext ctx )
   {
      cal.set( Calendar.DAY_OF_MONTH, asInteger( ctx.d ) );
      
      if ( ctx.m != null )
      {
         final int monthNum = parseTextMonth( ctx.m.getText() );
         cal.set( Calendar.MONTH, monthNum );
         
         if ( ctx.y != null )
         {
            final int year = parseYear( ctx.y.getText() );
            cal.set( Calendar.YEAR, year );
         }
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnMonthTheXst( DateOnMonthTheXstContext ctx )
   {
      final int monthNum = parseTextMonth( ctx.m.getText() );
      cal.set( Calendar.MONTH, monthNum );
      
      if ( ctx.d != null )
      {
         cal.set( Calendar.DAY_OF_MONTH, asInteger( ctx.d ) );
         
         if ( ctx.y != null )
         {
            final int year = parseYear( ctx.y.getText() );
            cal.set( Calendar.YEAR, year );
         }
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateOnWeekday( DateOnWeekdayContext ctx )
   {
      final boolean nextWeek = ctx.n != null;
      final int weekday = dateLanguage.getInteger( ctx.wd.getText() );
      
      // Read back for Calendar consistency
      cal.get( Calendar.WEEK_OF_YEAR );
      cal.set( Calendar.DAY_OF_WEEK, weekday );
      
      // if the next week is explicitly enforced
      if ( nextWeek )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateIn_X_YMWD_distance( DateIn_X_YMWD_distanceContext ctx )
   {
      final Pair< Integer, Integer > distance = getPointInDistance( ctx.amount,
                                                                    ctx.amountStr,
                                                                    ctx.y,
                                                                    ctx.m,
                                                                    ctx.w,
                                                                    ctx.d );
      cal.add( distance.second, distance.first );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDateEndOfTheMonthOrWeek( DateEndOfTheMonthOrWeekContext ctx )
   {
      if ( ctx.w != null )
      {
         rollToEndOf( Calendar.DAY_OF_WEEK );
      }
      else
      {
         rollToEndOf( Calendar.DAY_OF_MONTH );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitYesterday( YesterdayContext ctx )
   {
      cal.add( Calendar.DAY_OF_YEAR, -1 );
      return null;
   }
   
   
   
   @Override
   public Void visitTomorrow( TomorrowContext ctx )
   {
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      return null;
   }
   
   
   
   @Override
   public Void visitNever( NeverContext ctx )
   {
      cal.setHasDate( false );
      cal.setHasTime( false );
      
      parsedNeverLiteral = true;
      
      return null;
   }
   
   
   
   @Override
   public Void visitNow( NowContext ctx )
   {
      cal.setHasDate( true );
      // NOW has always a time.
      cal.setTimeInMillis( calendarProvider.getNow().getTimeInMillis() );
      cal.setHasTime( true );
      
      parsedNowLiteral = true;
      
      return null;
   }
   
   
   
   private void parseNumericDate( MolokoCalendar cal,
                                  String pt1,
                                  String pt2,
                                  String pt3 ) throws ParseCancellationException
   {
      try
      {
         final boolean withYear = pt3 != null;
         final DateFormat df;
         
         df = new SimpleDateFormat( dateFormatter.getNumericDateFormatPattern( withYear ) );
         
         final String dateInstance;
         if ( withYear )
         {
            dateInstance = dateFormatter.formatDateNumeric( pt1, pt2, pt3 );
         }
         else
         {
            dateInstance = dateFormatter.formatDateNumeric( pt1, pt2 );
         }
         
         df.parse( dateInstance );
         
         final Calendar dfCal = df.getCalendar();
         
         cal.set( Calendar.DAY_OF_MONTH, dfCal.get( Calendar.DAY_OF_MONTH ) );
         cal.set( Calendar.MONTH, dfCal.get( Calendar.MONTH ) );
         
         if ( withYear )
         {
            cal.set( Calendar.YEAR, dfCal.get( Calendar.YEAR ) );
         }
      }
      catch ( ParseException e )
      {
         throw new ParseCancellationException( e );
      }
   }
   
   
   
   private int parseTextMonth( String month ) throws ParseCancellationException
   {
      final int monthNum = dateLanguage.getInteger( month );
      
      if ( monthNum == -1 )
      {
         throw new ParseCancellationException();
      }
      
      return monthNum;
   }
   
   
   
   private int parseAmountString( Token token )
   {
      return dateLanguage.getInteger( token.getText() );
   }
   
   
   
   private int parseYear( String yearStr ) throws ParseCancellationException
   {
      try
      {
         final int len = yearStr.length();
         
         if ( len > 4 )
         {
            throw new ParseCancellationException( MessageFormat.format( "Invalid year {0}",
                                                                        yearStr ) );
         }
         
         int year = 0;
         
         if ( len < 4 )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat( "yy" );
            
            if ( len == 1 )
            {
               yearStr = "0" + yearStr;
            }
            else if ( len == 3 )
            {
               yearStr = yearStr.substring( 1, len );
            }
            
            sdf.parse( yearStr );
            year = sdf.getCalendar().get( Calendar.YEAR );
         }
         else
         {
            year = Integer.parseInt( yearStr );
         }
         
         return year;
      }
      catch ( ParseException pe )
      {
         throw new ParseCancellationException( pe );
      }
      catch ( NumberFormatException nfe )
      {
         throw new ParseCancellationException( nfe );
      }
   }
   
   
   
   private void rollToEndOf( int field )
   {
      final int ref = cal.get( field );
      final int max = cal.getActualMaximum( field );
      
      // set the field to the end.
      cal.set( field, max );
      
      // if already at the end
      if ( ref == max )
      {
         // we roll to the next
         cal.add( field, 1 );
      }
   }
   
   
   
   private Pair< Integer, Integer > getPointInDistance( Token amount,
                                                        Token amountStr,
                                                        Token year,
                                                        Token month,
                                                        Token week,
                                                        Token day )
   {
      final int amountInt;
      if ( amountStr != null )
      {
         amountInt = parseAmountString( amountStr );
      }
      else
      {
         amountInt = asInteger( amount );
      }
      
      final int calField;
      if ( year != null )
      {
         calField = Calendar.YEAR;
      }
      else if ( month != null )
      {
         calField = Calendar.MONTH;
      }
      else if ( week != null )
      {
         calField = Calendar.WEEK_OF_YEAR;
      }
      else
      {
         calField = Calendar.DAY_OF_YEAR;
      }
      
      return new Pair< Integer, Integer >( Integer.valueOf( amountInt ),
                                           Integer.valueOf( calField ) );
   }
   
   
   
   private int asInteger( Token token ) throws ParseCancellationException
   {
      try
      {
         return Integer.parseInt( token.getText() );
      }
      catch ( NumberFormatException e )
      {
         throw new ParseCancellationException( e );
      }
   }
   
}
