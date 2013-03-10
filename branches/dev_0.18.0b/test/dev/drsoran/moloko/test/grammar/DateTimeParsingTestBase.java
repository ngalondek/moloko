/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.grammar;

import java.util.Calendar;
import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;

import com.xtremelabs.robolectric.Robolectric;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.DateTimeParsing;
import dev.drsoran.moloko.grammar.IDateFormatter;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.IDateTimeParserRepository;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.ui.UiContext;


abstract class DateTimeParsingTestBase extends MolokoTestCase
{
   private IDateTimeParsing dateTimeParsing;
   
   private IDateFormatter dateFormatContext;
   
   
   
   @Override
   @Before
   public void setUp()
   {
      super.setUp();
      createDateTimeParsing();
   }
   
   
   
   public IDateFormatter getDateFormatContext()
   {
      return dateFormatContext;
   }
   
   
   
   public static MolokoCalendar getDateParserCalendar()
   {
      MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setHasTime( false );
      return cal;
   }
   
   
   
   public void parseDate( String string, int d, int m, int y )
   {
      parseDate( string, d, m, y, -1, -1, -1 );
   }
   
   
   
   public void parseDate( String dateString,
                          int d,
                          int m,
                          int y,
                          int h,
                          int min,
                          int s )
   {
      parseDate( dateString, d, m, y, h, min, s, true, h != -1 );
   }
   
   
   
   public void parseDate( String dateString,
                          int d,
                          int m,
                          int y,
                          int h,
                          int min,
                          int s,
                          boolean hasDate,
                          boolean hasTime )
   {
      final MolokoCalendar cal = dateTimeParsing.parseDateTimeSpec( dateString );
      if ( cal != null )
      {
         Assert.assertEquals( "Day is wrong.",
                              d,
                              cal.get( Calendar.DAY_OF_MONTH ) );
         Assert.assertEquals( "Month is wrong.", m, cal.get( Calendar.MONTH ) );
         Assert.assertEquals( "Year is wrong.", y, cal.get( Calendar.YEAR ) );
         
         if ( hasDate )
            Assert.assertTrue( "Calendar has no date.", cal.hasDate() );
         else
            Assert.assertFalse( "Calendar has date.", cal.hasDate() );
         
         if ( hasTime )
            Assert.assertTrue( "Calendar has no time.", cal.hasTime() );
         else
            Assert.assertFalse( "Calendar has time.", cal.hasTime() );
         
         if ( h != -1 )
            Assert.assertEquals( "Hour is wrong.",
                                 h,
                                 cal.get( Calendar.HOUR_OF_DAY ) );
         if ( min != -1 )
            Assert.assertEquals( "Minute is wrong.",
                                 min,
                                 cal.get( Calendar.MINUTE ) );
         if ( s != -1 )
            Assert.assertEquals( "Second is wrong.",
                                 s,
                                 cal.get( Calendar.SECOND ) );
      }
      else
      {
         Assert.fail( "Parsing '" + dateString + "' failed!" );
      }
   }
   
   
   
   public void parseDateWithin( String dateWithinString,
                                boolean past,
                                int sy,
                                int sm,
                                int sw,
                                int sd,
                                int ey,
                                int em,
                                int ew,
                                int ed )
   {
      final ParseDateWithinReturn result = dateTimeParsing.parseDateWithin( dateWithinString,
                                                                            past );
      
      if ( result != null )
      {
         final MolokoCalendar start = result.startEpoch;
         final MolokoCalendar end = result.endEpoch;
         
         // System.out.println( dateWithinString + "_start: "
         // + start.getTimeInMillis() );
         // System.out.println( dateWithinString + "_start: " + start.getTime() );
         // System.out.println( dateWithinString + "_start has time: "
         // + start.hasTime() );
         //
         // System.out.println( dateWithinString + "_end: "
         // + end.getTimeInMillis() );
         // System.out.println( dateWithinString + "_end: " + end.getTime() );
         // System.out.println( dateWithinString + "_end has time: "
         // + end.hasTime() );
         
         Assert.assertEquals( "Day is wrong.",
                              sd,
                              start.get( Calendar.DAY_OF_YEAR ) );
         Assert.assertEquals( "Week is wrong.",
                              sw,
                              start.get( Calendar.WEEK_OF_YEAR ) );
         Assert.assertEquals( "Month is wrong.", sm, start.get( Calendar.MONTH ) );
         Assert.assertEquals( "Year is wrong.", sy, start.get( Calendar.YEAR ) );
         
         Assert.assertEquals( "Day is wrong.",
                              ed,
                              end.get( Calendar.DAY_OF_YEAR ) );
         Assert.assertEquals( "Week is wrong.",
                              ew,
                              end.get( Calendar.WEEK_OF_YEAR ) );
         Assert.assertEquals( "Month is wrong.", em, end.get( Calendar.MONTH ) );
         Assert.assertEquals( "Year is wrong.", ey, end.get( Calendar.YEAR ) );
      }
      else
      {
         Assert.fail( "Parsing '" + dateWithinString + "' failed!" );
      }
   }
   
   
   
   private void createDateTimeParsing()
   {
      dateFormatContext = UiContext.get( Robolectric.application )
                                   .getDateFormatter();
      
      final IDateTimeParserRepository repo = EasyMock.createNiceMock( IDateTimeParserRepository.class );
      
      EasyMock.expect( repo.getDateParsers() )
              .andReturn( Collections.singletonList( createDateParser( dateFormatContext ) ) );
      EasyMock.expect( repo.getTimeParsers() )
              .andReturn( Collections.singletonList( createTimeParser() ) );
      
      dateTimeParsing = new DateTimeParsing( repo );
   }
   
   
   
   public abstract IDateParser createDateParser( IDateFormatter dateFormatContext );
   
   
   
   public abstract ITimeParser createTimeParser();
}
