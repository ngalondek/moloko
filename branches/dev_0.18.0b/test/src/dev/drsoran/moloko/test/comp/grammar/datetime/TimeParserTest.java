package dev.drsoran.moloko.test.comp.grammar.datetime;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.hourIs;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.minuteIs;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.secondIs;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.antlr.runtime.Lexer;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import android.text.format.DateUtils;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.datetime.AbstractANTLRTimeParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeLexer;
import dev.drsoran.moloko.grammar.datetime.TimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserImpl;
import dev.drsoran.moloko.test.MolokoTimeParserTestCase;


@RunWith( Theories.class )
public class TimeParserTest extends MolokoTimeParserTestCase
{
   
   @Test
   public void at_Time_hour_min_24h_no_seperator() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "@1310" );
      
      assertThat( cal, hourIs( 13 ) );
      assertThat( cal, minuteIs( 10 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void at_Time_hour_min_12h_colon() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "at 11:00 am" );
      
      assertThat( cal, hourIs( 11 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void at_Time_hour_12h() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "@11a" );
      
      assertThat( cal, hourIs( 11 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void at_Time_textual() throws GrammarException
   {
      MolokoCalendar cal = testParseTime( "at noon" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
      
      cal = testParseTime( "@midnight" );
      
      assertThat( cal, hourIs( 23 ) );
      assertThat( cal, minuteIs( 59 ) );
      assertThat( cal, secondIs( 59 ) );
   }
   
   
   
   @Test
   public void time_hour_min_sec_24h_colon() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "12:13:25" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 25 ) );
   }
   
   
   
   @Test
   public void time_hour_min_24h_dot() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "12.13" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void time_hour_min_12h_no_seperator() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "1100p" );
      
      assertThat( cal, hourIs( 23 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void time_textual() throws GrammarException
   {
      final MolokoCalendar cal = testParseTime( "midday" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void timespec_hour() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void timespec_hour_min_no_seperator() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "1210" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 10 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void timespec_hour_min_colon() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12:13" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_colon() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12:13:25" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 25 ) );
   }
   
   
   
   @Test
   public void timespec_hour_unit() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12 h" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void timespec_hour_min_unit() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12 h 13 minutes" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 0 ) );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_unit() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12 h 13 minutes 25 sec" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 25 ) );
   }
   
   
   
   @Test
   public void timespec_min_hour_sec_unit() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "13 minutes 12 h 25 sec" );
      
      assertThat( cal, hourIs( 12 ) );
      assertThat( cal, minuteIs( 13 ) );
      assertThat( cal, secondIs( 25 ) );
   }
   
   
   
   @Test
   public void timespec_hour_sec_hour_unit() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "12 hours 25 sec 1 h" );
      
      assertThat( cal, hourIs( 13 ) );
      assertThat( cal, minuteIs( 0 ) );
      assertThat( cal, secondIs( 25 ) );
   }
   
   
   
   @Test
   public void timespec_decimal_hour_sec_unit() throws GrammarException
   {
      final MolokoCalendar cal = testParseTimeSpec( "1.5 hours 25 sec" );
      
      assertThat( cal, hourIs( 1 ) );
      assertThat( cal, minuteIs( 30 ) );
      assertThat( cal, secondIs( 25 ) );
   }
   
   
   
   @Test
   public void timeEstiate_day_min() throws GrammarException
   {
      testParseTimeEstimate( "1 day 15 min", 24 * DateUtils.HOUR_IN_MILLIS + 15
         * DateUtils.MINUTE_IN_MILLIS );
   }
   
   
   
   @Test
   public void timeEstiate_hour_min_days_decimal_hour() throws GrammarException
   {
      testParseTimeEstimate( "1 h 15 min 2 days 1.5 hours ", 50
         * DateUtils.HOUR_IN_MILLIS + 45 * DateUtils.MINUTE_IN_MILLIS );
   }
   
   
   
   @Test
   public void timeEstiate_min_sec() throws GrammarException
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
}
