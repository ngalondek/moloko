/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.test.unit.domain.parsing.lang;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.domain.parsing.lang.DateLanguage;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.test.MolokoTestCase;


public class DateLanguageFixture extends MolokoTestCase
{
   private ILanguage dateLanguage;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      dateLanguage = new DateLanguage();
   }
   
   
   
   @Test
   public void testGetLocale()
   {
      assertThat( dateLanguage.getLocale(), is( Locale.ENGLISH ) );
   }
   
   
   
   @Test
   public void testGetIntegerNumbers()
   {
      assertThat( dateLanguage.getInteger( "one" ), is( 1 ) );
      assertThat( dateLanguage.getInteger( "two" ), is( 2 ) );
      assertThat( dateLanguage.getInteger( "three" ), is( 3 ) );
      assertThat( dateLanguage.getInteger( "four" ), is( 4 ) );
      assertThat( dateLanguage.getInteger( "five" ), is( 5 ) );
      assertThat( dateLanguage.getInteger( "six" ), is( 6 ) );
      assertThat( dateLanguage.getInteger( "seven" ), is( 7 ) );
      assertThat( dateLanguage.getInteger( "eight" ), is( 8 ) );
      assertThat( dateLanguage.getInteger( "nine" ), is( 9 ) );
      assertThat( dateLanguage.getInteger( "ten" ), is( 10 ) );
   }
   
   
   
   @Test
   public void testGetIntegerWeekdays()
   {
      assertThat( dateLanguage.getInteger( "monday" ), is( Calendar.MONDAY ) );
      assertThat( dateLanguage.getInteger( "mon" ), is( Calendar.MONDAY ) );
      assertThat( dateLanguage.getInteger( "tuesday" ), is( Calendar.TUESDAY ) );
      assertThat( dateLanguage.getInteger( "tue" ), is( Calendar.TUESDAY ) );
      assertThat( dateLanguage.getInteger( "wednesday" ),
                  is( Calendar.WEDNESDAY ) );
      assertThat( dateLanguage.getInteger( "wed" ), is( Calendar.WEDNESDAY ) );
      assertThat( dateLanguage.getInteger( "thursday" ), is( Calendar.THURSDAY ) );
      assertThat( dateLanguage.getInteger( "thu" ), is( Calendar.THURSDAY ) );
      assertThat( dateLanguage.getInteger( "friday" ), is( Calendar.FRIDAY ) );
      assertThat( dateLanguage.getInteger( "fri" ), is( Calendar.FRIDAY ) );
      assertThat( dateLanguage.getInteger( "saturday" ), is( Calendar.SATURDAY ) );
      assertThat( dateLanguage.getInteger( "sat" ), is( Calendar.SATURDAY ) );
      assertThat( dateLanguage.getInteger( "sunday" ), is( Calendar.SUNDAY ) );
      assertThat( dateLanguage.getInteger( "sun" ), is( Calendar.SUNDAY ) );
   }
   
   
   
   @Test
   public void testGetIntegerMonths()
   {
      assertThat( dateLanguage.getInteger( "january" ), is( Calendar.JANUARY ) );
      assertThat( dateLanguage.getInteger( "jan" ), is( Calendar.JANUARY ) );
      assertThat( dateLanguage.getInteger( "february" ), is( Calendar.FEBRUARY ) );
      assertThat( dateLanguage.getInteger( "feb" ), is( Calendar.FEBRUARY ) );
      assertThat( dateLanguage.getInteger( "march" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "mar" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "april" ), is( Calendar.APRIL ) );
      assertThat( dateLanguage.getInteger( "apr" ), is( Calendar.APRIL ) );
      assertThat( dateLanguage.getInteger( "may" ), is( Calendar.MAY ) );
      assertThat( dateLanguage.getInteger( "june" ), is( Calendar.JUNE ) );
      assertThat( dateLanguage.getInteger( "jun" ), is( Calendar.JUNE ) );
      assertThat( dateLanguage.getInteger( "july" ), is( Calendar.JULY ) );
      assertThat( dateLanguage.getInteger( "jul" ), is( Calendar.JULY ) );
      assertThat( dateLanguage.getInteger( "august" ), is( Calendar.AUGUST ) );
      assertThat( dateLanguage.getInteger( "aug" ), is( Calendar.AUGUST ) );
      assertThat( dateLanguage.getInteger( "september" ),
                  is( Calendar.SEPTEMBER ) );
      assertThat( dateLanguage.getInteger( "sept" ), is( Calendar.SEPTEMBER ) );
      assertThat( dateLanguage.getInteger( "sep" ), is( Calendar.SEPTEMBER ) );
      assertThat( dateLanguage.getInteger( "october" ), is( Calendar.OCTOBER ) );
      assertThat( dateLanguage.getInteger( "oct" ), is( Calendar.OCTOBER ) );
      assertThat( dateLanguage.getInteger( "november" ), is( Calendar.NOVEMBER ) );
      assertThat( dateLanguage.getInteger( "nov" ), is( Calendar.NOVEMBER ) );
      assertThat( dateLanguage.getInteger( "december" ), is( Calendar.DECEMBER ) );
      assertThat( dateLanguage.getInteger( "dec" ), is( Calendar.DECEMBER ) );
   }
   
   
   
   @Test
   public void testGetString()
   {
      assertThat( dateLanguage.getString( "one" ), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetPluralStringStringStringInt()
   {
      assertThat( dateLanguage.getPluralString( "one", "", 0 ), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetPluralStringStringStringString()
   {
      assertThat( dateLanguage.getPluralString( "one", "", "" ), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetStrings()
   {
      assertThat( dateLanguage.getStrings( "one" ).get( 0 ), is( "1" ) );
      assertThat( dateLanguage.getStrings( "one" ).size(), is( 1 ) );
   }
   
}
