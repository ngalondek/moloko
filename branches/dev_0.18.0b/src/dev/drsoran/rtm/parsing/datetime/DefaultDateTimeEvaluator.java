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

package dev.drsoran.rtm.parsing.datetime;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import dev.drsoran.Pair;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.IDateFormatter;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.AMContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateEndOfTheMonthOrWeekContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateIn_X_YMWD_distanceContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateNeverContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateNumericContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateOnMonthTheXstContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateOnTheXstOfMonthContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.DateOnWeekdayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.MiddayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.MidnightContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.NowContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.PMContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.ParseDateWithinContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.SeparatedTimeHMContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.SeparatedTimeHMSContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TimeContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecFloatHoursContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecUnitHoursContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecUnitMinutesContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TimeNaturalSpecUnitSecondsContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TimeNeverContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.TomorrowContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.UnSeparatedTimeContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser.YesterdayContext;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParserBaseVisitor;
import dev.drsoran.rtm.parsing.lang.ILanguage;


public class DefaultDateTimeEvaluator extends DateTimeParserBaseVisitor< Void >
{
   private final RtmCalendar cal;
   
   private final ILanguage dateLanguage;
   
   private final IDateFormatter dateFormatter;
   
   private final IRtmCalendarProvider calendarProvider;
   
   private boolean parsedNowLiteral;
   
   private boolean parsedNeverLiteral;
   
   private RtmCalendar epochEnd;
   
   
   
   public DefaultDateTimeEvaluator( ILanguage dateLanguage,
      IDateFormatter dateFormatter, IRtmCalendarProvider calenderProvider )
   {
      this( dateLanguage,
            dateFormatter,
            calenderProvider,
            calenderProvider.getToday().clone() );
   }
   
   
   
   public DefaultDateTimeEvaluator( ILanguage dateLanguage,
      IDateFormatter dateFormatter, IRtmCalendarProvider calenderProvider,
      RtmCalendar cal )
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
      
      this.cal.setHasDate( false );
      this.cal.setHasTime( false );
      
      setCalendarToToday();
   }
   
   
   
   public RtmCalendar getCalendar()
   {
      return cal;
   }
   
   
   
   public RtmCalendar getEpochStart()
   {
      return cal;
   }
   
   
   
   public RtmCalendar getEpochEnd()
   {
      return epochEnd;
   }
   
   
   
   @Override
   public Void visitDate( DateContext ctx )
   {
      visitChildren( ctx );
      
      if ( !parsedNowLiteral && !parsedNeverLiteral )
      {
         cal.setHasDate( true );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitTime( TimeContext ctx )
   {
      visitChildren( ctx );
      
      if ( !parsedNeverLiteral )
      {
         cal.setHasTime( true );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitParseDateWithin( ParseDateWithinContext ctx )
   {
      if ( ctx.OF() != null )
      {
         visit( ctx.date() );
      }
      
      if ( !parsedNeverLiteral )
      {
         cal.setHasDate( true );
         
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
         epochEnd = RtmCalendar.getDatelessAndTimelessInstance();
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
   public Void visitDateNever( DateNeverContext ctx )
   {
      parsedNever();
      return null;
   }
   
   
   
   @Override
   public Void visitNow( NowContext ctx )
   {
      cal.setHasDate( true );
      
      if ( !cal.hasTime() )
      {
         // NOW has always a time.
         cal.setTimeInMillis( calendarProvider.getNow().getTimeInMillis() );
         cal.setHasTime( true );
      }
      
      parsedNowLiteral = true;
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNever( TimeNeverContext ctx )
   {
      parsedNever();
      return null;
   }
   
   
   
   @Override
   public Void visitMidnight( MidnightContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, 23 );
      cal.set( Calendar.MINUTE, 59 );
      cal.set( Calendar.SECOND, 59 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitMidday( MiddayContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, 12 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitSeparatedTimeHM( SeparatedTimeHMContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( ctx.h.getText() ) );
      cal.set( Calendar.MINUTE, Integer.parseInt( ctx.m.getText() ) );
      cal.set( Calendar.SECOND, 0 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitSeparatedTimeHMS( SeparatedTimeHMSContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( ctx.h.getText() ) );
      cal.set( Calendar.MINUTE, Integer.parseInt( ctx.m.getText() ) );
      cal.set( Calendar.SECOND, Integer.parseInt( ctx.s.getText() ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitUnSeparatedTime( UnSeparatedTimeContext ctx )
   {
      final String pointInTime = ctx.INT().getText();
      
      final int len = pointInTime.length();
      
      SimpleDateFormat sdf = null;
      
      try
      {
         if ( len < 3 )
         {
            sdf = new SimpleDateFormat( "HH" );
         }
         else if ( len > 3 )
         {
            sdf = new SimpleDateFormat( "HHmm" );
         }
         else
         {
            sdf = new SimpleDateFormat( "Hmm" );
         }
         
         sdf.setTimeZone( cal.getTimeZone() );
         sdf.parse( pointInTime );
         
         final Calendar sdfCal = sdf.getCalendar();
         cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
         cal.set( Calendar.MINUTE, sdfCal.get( Calendar.MINUTE ) );
         cal.set( Calendar.SECOND, 0 );
         
         // This additional get is necessary to have the calendar apply the sdfCal HOUR.
         cal.get( Calendar.HOUR_OF_DAY );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException( null, null, null );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitAM( AMContext ctx )
   {
      // This get is needed to prevent stale calendar values
      cal.get( Calendar.AM_PM );
      cal.set( Calendar.AM_PM, Calendar.AM );
      
      return null;
   }
   
   
   
   @Override
   public Void visitPM( PMContext ctx )
   {
      // This get is needed to prevent stale calendar values
      cal.get( Calendar.AM_PM );
      cal.set( Calendar.AM_PM, Calendar.PM );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecFloatHours( TimeNaturalSpecFloatHoursContext ctx )
   {
      final int hours = Integer.parseInt( ctx.INT( 0 ).getText() );
      cal.add( Calendar.HOUR, hours );
      
      final int deciHours = Integer.parseInt( ctx.INT( 1 ).getText() );
      cal.add( Calendar.MINUTE, (int) ( 60.0f * ( deciHours / 10.0f ) ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitHours( TimeNaturalSpecUnitHoursContext ctx )
   {
      final int hours = Integer.parseInt( ctx.INT().getText() );
      cal.add( Calendar.HOUR, hours );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitMinutes( TimeNaturalSpecUnitMinutesContext ctx )
   {
      final int minutes = Integer.parseInt( ctx.INT().getText() );
      cal.add( Calendar.MINUTE, minutes );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitSeconds( TimeNaturalSpecUnitSecondsContext ctx )
   {
      final int seconds = Integer.parseInt( ctx.INT().getText() );
      cal.add( Calendar.SECOND, seconds );
      
      return null;
   }
   
   
   
   private void parsedNever()
   {
      cal.setHasDate( false );
      cal.setHasTime( false );
      
      parsedNeverLiteral = true;
   }
   
   
   
   private void parseNumericDate( RtmCalendar cal,
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
   
   
   
   private void setCalendarToToday()
   {
      final RtmCalendar today = calendarProvider.getToday();
      
      cal.set( Calendar.YEAR, today.get( Calendar.YEAR ) );
      cal.set( Calendar.MONTH, today.get( Calendar.MONTH ) );
      cal.set( Calendar.DATE, today.get( Calendar.DATE ) );
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
