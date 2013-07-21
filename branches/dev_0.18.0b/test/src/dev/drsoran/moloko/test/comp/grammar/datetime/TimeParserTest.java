package dev.drsoran.moloko.test.comp.grammar.datetime;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.hour;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.minute;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.second;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.antlr.runtime.Lexer;
import org.junit.Test;

import android.text.format.DateUtils;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.datetime.AbstractANTLRTimeParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.ParseReturn;
import dev.drsoran.moloko.grammar.datetime.TimeLexer;
import dev.drsoran.moloko.grammar.datetime.TimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserImpl;
import dev.drsoran.moloko.test.Lambda.Func;
import dev.drsoran.moloko.test.MolokoTimeParserTestCase;


public class TimeParserTest extends MolokoTimeParserTestCase
{
   @Test
   public void test_parseTime_literal() throws GrammarException
   {
      testParseWithLeadingAt( "midday", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "noon", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "midnight", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 23 ) ) );
            assertThat( cal, is( minute( 59 ) ) );
            assertThat( cal, is( second( 59 ) ) );
         }
      } );
      
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      final ParseReturn ret = parseTime( cal, "never", false );
      verifyParseResult( "never", cal, ret, false );
      
      assertThat( cal, is( hour( 0 ) ) );
      assertThat( cal, is( minute( 0 ) ) );
      assertThat( cal, is( second( 0 ) ) );
      assertThat( cal.hasDate(), is( false ) );
   }
   
   
   
   @Test
   public void test_parseTime_separated_dot() throws GrammarException
   {
      testParseWithLeadingAt( "12.13", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "12.13a", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 0 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "12.13.25", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "12.13.25p", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      } );
   }
   
   
   
   @Test
   public void test_parseTime_separated_colon() throws GrammarException
   {
      testParseWithLeadingAt( "12:13", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "12:13:25", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      } );
   }
   
   
   
   @Test
   public void test_parseTime_separated_mixed() throws GrammarException
   {
      testParseWithLeadingAt( "12:13.25", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "12.13:25", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      } );
   }
   
   
   
   @Test
   public void test_parseTime_unseparated() throws GrammarException
   {
      testParseWithLeadingAt( "13", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 13 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "110", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 1 ) ) );
            assertThat( cal, is( minute( 10 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "110am", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 1 ) ) );
            assertThat( cal, is( minute( 10 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "1310", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 13 ) ) );
            assertThat( cal, is( minute( 10 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "1210pm", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 10 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
   }
   
   
   
   @Test
   public void test_parseTime_HMS_separated_hour() throws GrammarException
   {
      final Func< MolokoCalendar > verifier = new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      };
      
      testParseWithLeadingAt( "12h", verifier );
      testParseWithLeadingAt( "12 hr", verifier );
      testParseWithLeadingAt( "12 hrs", verifier );
      testParseWithLeadingAt( "12 hour", verifier );
      testParseWithLeadingAt( "12 hours", verifier );
   }
   
   
   
   @Test
   public void test_parseTime_HMS_separated_minute() throws GrammarException
   {
      final Func< MolokoCalendar > verifier = new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 0 ) ) );
            assertThat( cal, is( minute( 13 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      };
      
      testParseWithLeadingAt( "13m", verifier );
      testParseWithLeadingAt( "13 min", verifier );
      testParseWithLeadingAt( "13 mins", verifier );
      testParseWithLeadingAt( "13 minute", verifier );
      testParseWithLeadingAt( "13 minutes", verifier );
   }
   
   
   
   @Test
   public void test_parseTime_HMS_separated_second() throws GrammarException
   {
      final Func< MolokoCalendar > verifier = new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 0 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      };
      
      testParseWithLeadingAt( "25s", verifier );
      testParseWithLeadingAt( "25 sec", verifier );
      testParseWithLeadingAt( "25 secs", verifier );
      testParseWithLeadingAt( "25 second", verifier );
      testParseWithLeadingAt( "25 seconds", verifier );
   }
   
   
   
   @Test
   public void test_parseTime_HMS_separated_mixed() throws GrammarException
   {
      testParseWithLeadingAt( "12 h 13 minutes 25 sec",
                              new Func< MolokoCalendar >()
                              {
                                 @Override
                                 public void call( MolokoCalendar cal )
                                 {
                                    assertThat( cal, is( hour( 12 ) ) );
                                    assertThat( cal, is( minute( 13 ) ) );
                                    assertThat( cal, is( second( 25 ) ) );
                                 }
                              } );
      
      testParseWithLeadingAt( "13 minutes 12 h 25 sec",
                              new Func< MolokoCalendar >()
                              {
                                 @Override
                                 public void call( MolokoCalendar cal )
                                 {
                                    assertThat( cal, is( hour( 12 ) ) );
                                    assertThat( cal, is( minute( 13 ) ) );
                                    assertThat( cal, is( second( 25 ) ) );
                                 }
                              } );
      
      testParseWithLeadingAt( "12 hours 25 sec 1 h",
                              new Func< MolokoCalendar >()
                              {
                                 @Override
                                 public void call( MolokoCalendar cal )
                                 {
                                    assertThat( cal, is( hour( 13 ) ) );
                                    assertThat( cal, is( minute( 0 ) ) );
                                    assertThat( cal, is( second( 25 ) ) );
                                 }
                              } );
   }
   
   
   
   @Test
   public void test_parseTime_HMS_separated_float() throws GrammarException
   {
      testParseWithLeadingAt( "1.5 hours 25 sec", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 1 ) ) );
            assertThat( cal, is( minute( 30 ) ) );
            assertThat( cal, is( second( 25 ) ) );
         }
      } );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void test_parseTime_HMS_separated_days() throws GrammarException
   {
      testParseTime( "12 hours 13 minutes 3 days" );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_day() throws GrammarException
   {
      testParseTimeEstimate( "1d", DateUtils.DAY_IN_MILLIS );
      testParseTimeEstimate( "1 day", DateUtils.DAY_IN_MILLIS );
      testParseTimeEstimate( "1 days", DateUtils.DAY_IN_MILLIS );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_day_min() throws GrammarException
   {
      testParseTimeEstimate( "1 day 15 min", DateUtils.DAY_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
      
      testParseTimeEstimate( "1 day and 15 min", DateUtils.DAY_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
      
      testParseTimeEstimate( "1 day,15 min", DateUtils.DAY_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_min_days_decimal_hour() throws GrammarException
   {
      testParseTimeEstimate( "1 h 15 min 2 days 1.5 hours ",
                             DateUtils.HOUR_IN_MILLIS + 15
                                * DateUtils.MINUTE_IN_MILLIS + 2
                                * DateUtils.DAY_IN_MILLIS
                                + DateUtils.HOUR_IN_MILLIS + 30
                                * DateUtils.MINUTE_IN_MILLIS );
      
      testParseTimeEstimate( "1 h, 15 min 2 days and 1.5 hours ",
                             DateUtils.HOUR_IN_MILLIS + 15
                                * DateUtils.MINUTE_IN_MILLIS + 2
                                * DateUtils.DAY_IN_MILLIS
                                + DateUtils.HOUR_IN_MILLIS + 30
                                * DateUtils.MINUTE_IN_MILLIS );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_min_sec() throws GrammarException
   {
      testParseTimeEstimate( "1 min 1 second", DateUtils.MINUTE_IN_MILLIS
         + DateUtils.SECOND_IN_MILLIS );
   }
   
   
   
   @Override
   protected ITimeParser createTimeParser()
   {
      final AbstractANTLRTimeParser antlrTimeParser = new TimeParser();
      final Lexer antlrTimeLexer = new TimeLexer();
      return new TimeParserImpl( Locale.ENGLISH,
                                 antlrTimeParser,
                                 antlrTimeLexer );
   }
   
   
   
   private void testParseWithLeadingAt( String timeString,
                                        Func< MolokoCalendar > verifier ) throws GrammarException
   {
      verifier.call( testParseTime( timeString ) );
      verifier.call( testParseTime( "@" + timeString ) );
      verifier.call( testParseTime( "at " + timeString ) );
      verifier.call( testParseTime( "," + timeString ) );
   }
}
