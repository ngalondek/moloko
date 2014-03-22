/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.rtm.test.unit.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import dev.drsoran.rtm.service.RtmDateFormatter;
import dev.drsoran.rtm.test.PrivateCtorCaller;


public class RtmDateFormatterFixture
{
   private final static Calendar CAL;
   
   static
   {
      CAL = Calendar.getInstance( TimeZone.getTimeZone( "GMT" ) );
      CAL.set( Calendar.YEAR, 2010 );
      CAL.set( Calendar.MONTH, Calendar.NOVEMBER );
      CAL.set( Calendar.DATE, 1 );
      CAL.set( Calendar.HOUR_OF_DAY, 10 );
      CAL.set( Calendar.MINUTE, 20 );
      CAL.set( Calendar.SECOND, 30 );
   }
   
   
   
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( RtmDateFormatter.class );
   }
   
   
   
   @Test
   public void testParseDate()
   {
      final Date parseDate = RtmDateFormatter.parseDate( "2010-11-01T10:20:30" );
      assertThat( parseDate.toString(), equalTo( CAL.getTime().toString() ) );
   }
   
   
   
   @Test
   public void testParseDate_Empty()
   {
      final Date parseDate = RtmDateFormatter.parseDate( "" );
      assertThat( parseDate, is( nullValue() ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParseDate_NullDates()
   {
      RtmDateFormatter.parseDate( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParseDate_UnparsableDate()
   {
      RtmDateFormatter.parseDate( "2010-11-01 10:20:30" );
   }
   
   
   
   @Test
   public void testFormatDate()
   {
      String formatDate = RtmDateFormatter.formatDate( CAL.getTime() );
      assertThat( formatDate, is( "2010-11-01T10:20:30Z" ) );
   }
}
