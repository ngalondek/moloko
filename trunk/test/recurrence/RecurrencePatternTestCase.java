/* 
 * Copyright (c) 2011 Ronny Röhricht
 *
 * This file is part of Moloko.
 *
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.grammar.lang.RecurrPatternLanguage;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternLexer;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;


public class RecurrencePatternTestCase
{
   private final static RecurrPatternLanguage LANG = new RecurrPatternLanguage();
   
   private final static SimpleDateFormat SDF_FORMAT = new SimpleDateFormat( RecurrencePatternParser.DATE_PATTERN );
   
   private static IDateFormatContext DATE_FORMAT_CONTEXT;
   
   private static SimpleDateFormat SDF_PARSE;
   
   
   
   private static void parseRecurrencePattern( String string,
                                               String expected,
                                               boolean isEvery )
   {
      final RecurrencePatternLexer lexer = new RecurrencePatternLexer( new ANTLRStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      
      final RecurrencePatternParser parser = new RecurrencePatternParser( antlrTokens );
      parser.setDateFormatContext( DATE_FORMAT_CONTEXT );
      
      System.out.println( ">input: " + string );
      
      try
      {
         final String result = parser.parseRecurrencePattern( LANG, isEvery );
         
         System.out.println( string + ": " + result );
         
         Asserts.assertEquals( result.toLowerCase(), expected, "" );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
   
   
   
   private static String buildPattern( String freq,
                                       int interval,
                                       String... resolution )
   {
      StringBuilder result = new StringBuilder( RecurrencePatternParser.OP_FREQ_LIT );
      
      result.append( "=" ).append( freq ).append( ";" );
      result.append( RecurrencePatternParser.OP_INTERVAL_LIT )
            .append( "=" )
            .append( String.valueOf( interval ) )
            .append( resolution.length > 0 ? ";" : "" );
      
      for ( int i = 0; i < resolution.length; i++ )
      {
         result.append( resolution[ i ] );
         
         if ( i < resolution.length - 1 )
         {
            result.append( ";" );
         }
      }
      
      return result.toString();
   }
   
   
   
   public final static void execute() throws ParseException
   {
      DATE_FORMAT_CONTEXT = new TestDateFormaterContext_en();
      SDF_PARSE = new SimpleDateFormat( DATE_FORMAT_CONTEXT.getNumericDateFormatPattern( true ) );
      
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_YEARLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=1MO,1FR",
                                            RecurrencePatternParser.OP_BYMONTH_LIT
                                               + "=1" ),
                              "every year on the 1st monday, friday in january",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_YEARLY_LIT,
                                            1 ),
                              "every year",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_YEARLY_LIT,
                                            2 ),
                              "every 2 years",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYMONTHDAY_LIT
                                               + "=1,25" ),
                              "every month on the 1st, 25th",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=3TU" ),
                              "every month on the 3rd tuesday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=-1MO" ),
                              "every month on the last monday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=-2FR" ),
                              "every month on the 2nd last friday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=1FR" ),
                              "every month on the 1st friday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            2 ),
                              "every 2 weeks",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=TU" ),
                              "every week on the tuesday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=MO,WE" ),
                              "every week on the monday, wednesday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            2,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=FR" ),
                              "every 2 weeks on the friday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_DAILY_LIT,
                                            1 ),
                              "every day",
                              true );
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=MO,WE",
                                            RecurrencePatternParser.OP_UNTIL_LIT
                                               + "="
                                               + SDF_FORMAT.format( SDF_PARSE.parse( "10/10/2010" ) ) ),
                              "every week on the monday, wednesday until 10/10/2010",
                              true );
      {
         final Calendar cal = Calendar.getInstance();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         
         cal.set( Calendar.HOUR, 0 );
         cal.set( Calendar.HOUR_OF_DAY, 0 );
         cal.set( Calendar.MINUTE, 0 );
         cal.set( Calendar.SECOND, 0 );
         cal.set( Calendar.MILLISECOND, 0 );
         
         parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_DAILY_LIT,
                                               1,
                                               RecurrencePatternParser.OP_UNTIL_LIT
                                                  + "="
                                                  + SDF_FORMAT.format( cal.getTime() ) ),
                                 "every day until "
                                    + SDF_PARSE.format( cal.getTime() ),
                                 true );
      }
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_DAILY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_COUNT_LIT
                                               + "=10" ),
                              "every day for 10 times",
                              true );
   }
}
