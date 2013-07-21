package dev.drsoran.moloko.test.comp.grammar.datetime.de;

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
import dev.drsoran.moloko.grammar.datetime.de.TimeLexer;
import dev.drsoran.moloko.grammar.datetime.de.TimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserImpl;
import dev.drsoran.moloko.test.Lambda.Func;
import dev.drsoran.moloko.test.MolokoTimeParserTestCase;


public class TimeParserTest extends MolokoTimeParserTestCase
{
   @Test
   public void test_parseTime_literal() throws GrammarException
   {
      testParseWithLeadingAt( "mittag", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "mittags", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 12 ) ) );
            assertThat( cal, is( minute( 0 ) ) );
            assertThat( cal, is( second( 0 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "mitternacht", new Func< MolokoCalendar >()
      {
         @Override
         public void call( MolokoCalendar cal )
         {
            assertThat( cal, is( hour( 23 ) ) );
            assertThat( cal, is( minute( 59 ) ) );
            assertThat( cal, is( second( 59 ) ) );
         }
      } );
      
      testParseWithLeadingAt( "mitternachts", new Func< MolokoCalendar >()
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
      final ParseReturn ret = parseTime( cal, "nie", false );
      verifyParseResult( "nie", cal, ret, false );
      
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
      
      testParseWithLeadingAt( "12.13vorm", new Func< MolokoCalendar >()
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
      
      testParseWithLeadingAt( "12.13.25nachm.", new Func< MolokoCalendar >()
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
      
      testParseWithLeadingAt( "110vormittag", new Func< MolokoCalendar >()
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
      
      testParseWithLeadingAt( "1210nachmittags", new Func< MolokoCalendar >()
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
      testParseWithLeadingAt( "12 std", verifier );
      testParseWithLeadingAt( "12 stunde", verifier );
      testParseWithLeadingAt( "12 stunden", verifier );
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
      testParseWithLeadingAt( "13 minute", verifier );
      testParseWithLeadingAt( "13 minuten", verifier );
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
      testParseWithLeadingAt( "25 sek", verifier );
      testParseWithLeadingAt( "25 sekunde", verifier );
      testParseWithLeadingAt( "25 sekunden", verifier );
   }
   
   
   
   @Test
   public void test_parseTime_HMS_separated_mixed() throws GrammarException
   {
      testParseWithLeadingAt( "12 h 13 minuten 25 sec",
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
      
      testParseWithLeadingAt( "13 minute 12 h 25 sek",
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
      
      testParseWithLeadingAt( "12 stunden 25 sekunde 1 h",
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
      testParseWithLeadingAt( "1.5 stunden 25 sec",
                              new Func< MolokoCalendar >()
                              {
                                 @Override
                                 public void call( MolokoCalendar cal )
                                 {
                                    assertThat( cal, is( hour( 1 ) ) );
                                    assertThat( cal, is( minute( 30 ) ) );
                                    assertThat( cal, is( second( 25 ) ) );
                                 }
                              } );
      
      testParseWithLeadingAt( "1,5 stunden 25 sec",
                              new Func< MolokoCalendar >()
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
      testParseTime( "12 stunden 13 minuten 3 tage" );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_day() throws GrammarException
   {
      testParseTimeEstimate( "1d", DateUtils.DAY_IN_MILLIS );
      testParseTimeEstimate( "1 tag", DateUtils.DAY_IN_MILLIS );
      testParseTimeEstimate( "1 tage", DateUtils.DAY_IN_MILLIS );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_day_min() throws GrammarException
   {
      testParseTimeEstimate( "1 tag 15 min", DateUtils.DAY_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
      
      testParseTimeEstimate( "1 tag und 15 min", DateUtils.DAY_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
      
      testParseTimeEstimate( "1 tag,15 min", DateUtils.DAY_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_min_days_decimal_hour() throws GrammarException
   {
      testParseTimeEstimate( "1 h 15 min 2 tage 1.5 stunden ",
                             DateUtils.HOUR_IN_MILLIS + 15
                                * DateUtils.MINUTE_IN_MILLIS + 2
                                * DateUtils.DAY_IN_MILLIS
                                + DateUtils.HOUR_IN_MILLIS + 30
                                * DateUtils.MINUTE_IN_MILLIS );
      
      testParseTimeEstimate( "1 h, 15 min 2 tage und 1.5 stunden ",
                             DateUtils.HOUR_IN_MILLIS + 15
                                * DateUtils.MINUTE_IN_MILLIS + 2
                                * DateUtils.DAY_IN_MILLIS
                                + DateUtils.HOUR_IN_MILLIS + 30
                                * DateUtils.MINUTE_IN_MILLIS );
   }
   
   
   
   @Test
   public void test_parteTimeEstimate_min_sec() throws GrammarException
   {
      testParseTimeEstimate( "1 min 1 sekunde", DateUtils.MINUTE_IN_MILLIS
         + DateUtils.SECOND_IN_MILLIS );
   }
   
   
   
   @Override
   protected ITimeParser createTimeParser()
   {
      final AbstractANTLRTimeParser antlrTimeParser = new TimeParser();
      final Lexer antlrTimeLexer = new TimeLexer();
      return new TimeParserImpl( Locale.GERMAN, antlrTimeParser, antlrTimeLexer );
   }
   
   
   
   private void testParseWithLeadingAt( String timeString,
                                        Func< MolokoCalendar > verifier ) throws GrammarException
   {
      verifier.call( testParseTime( timeString ) );
      verifier.call( testParseTime( "@" + timeString ) );
      verifier.call( testParseTime( "um " + timeString ) );
      verifier.call( testParseTime( "am " + timeString ) );
      verifier.call( testParseTime( "an " + timeString ) );
      verifier.call( testParseTime( "," + timeString ) );
   }
}
