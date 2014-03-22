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

package dev.drsoran.rtm.test.unit.parsing.lang.de;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.rtm.parsing.lang.ILanguage;
import dev.drsoran.rtm.parsing.lang.de.DateLanguage;


public class DateLanguageFixture
{
   private ILanguage dateLanguage;
   
   
   
   @Before
   public void setUp() throws Exception
   {
      dateLanguage = new DateLanguage();
   }
   
   
   
   @Test
   public void testGetLocale()
   {
      assertThat( dateLanguage.getLocale(), is( Locale.GERMAN ) );
   }
   
   
   
   @Test
   public void testGetIntegerNumbers()
   {
      assertThat( dateLanguage.getInteger( "eins" ), is( 1 ) );
      assertThat( dateLanguage.getInteger( "zwei" ), is( 2 ) );
      assertThat( dateLanguage.getInteger( "drei" ), is( 3 ) );
      assertThat( dateLanguage.getInteger( "vier" ), is( 4 ) );
      assertThat( dateLanguage.getInteger( "funf" ), is( 5 ) );
      assertThat( dateLanguage.getInteger( "fuenf" ), is( 5 ) );
      assertThat( dateLanguage.getInteger( "fünf" ), is( 5 ) );
      assertThat( dateLanguage.getInteger( "sechts" ), is( 6 ) );
      assertThat( dateLanguage.getInteger( "sieben" ), is( 7 ) );
      assertThat( dateLanguage.getInteger( "acht" ), is( 8 ) );
      assertThat( dateLanguage.getInteger( "neun" ), is( 9 ) );
      assertThat( dateLanguage.getInteger( "zehn" ), is( 10 ) );
   }
   
   
   
   @Test
   public void testGetIntegerWeekdays()
   {
      assertThat( dateLanguage.getInteger( "montag" ), is( Calendar.MONDAY ) );
      assertThat( dateLanguage.getInteger( "mo" ), is( Calendar.MONDAY ) );
      assertThat( dateLanguage.getInteger( "dienstag" ), is( Calendar.TUESDAY ) );
      assertThat( dateLanguage.getInteger( "di" ), is( Calendar.TUESDAY ) );
      assertThat( dateLanguage.getInteger( "mittwoch" ),
                  is( Calendar.WEDNESDAY ) );
      assertThat( dateLanguage.getInteger( "mi" ), is( Calendar.WEDNESDAY ) );
      assertThat( dateLanguage.getInteger( "donnerstag" ),
                  is( Calendar.THURSDAY ) );
      assertThat( dateLanguage.getInteger( "do" ), is( Calendar.THURSDAY ) );
      assertThat( dateLanguage.getInteger( "fritag" ), is( Calendar.FRIDAY ) );
      assertThat( dateLanguage.getInteger( "fr" ), is( Calendar.FRIDAY ) );
      assertThat( dateLanguage.getInteger( "samstag" ), is( Calendar.SATURDAY ) );
      assertThat( dateLanguage.getInteger( "sa" ), is( Calendar.SATURDAY ) );
      assertThat( dateLanguage.getInteger( "sonntag" ), is( Calendar.SUNDAY ) );
      assertThat( dateLanguage.getInteger( "so" ), is( Calendar.SUNDAY ) );
   }
   
   
   
   @Test
   public void testGetIntegerMonths()
   {
      assertThat( dateLanguage.getInteger( "januar" ), is( Calendar.JANUARY ) );
      assertThat( dateLanguage.getInteger( "jan" ), is( Calendar.JANUARY ) );
      assertThat( dateLanguage.getInteger( "februar" ), is( Calendar.FEBRUARY ) );
      assertThat( dateLanguage.getInteger( "feb" ), is( Calendar.FEBRUARY ) );
      assertThat( dateLanguage.getInteger( "marz" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "maerz" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "märz" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "mar" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "maer" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "mär" ), is( Calendar.MARCH ) );
      assertThat( dateLanguage.getInteger( "april" ), is( Calendar.APRIL ) );
      assertThat( dateLanguage.getInteger( "apr" ), is( Calendar.APRIL ) );
      assertThat( dateLanguage.getInteger( "mai" ), is( Calendar.MAY ) );
      assertThat( dateLanguage.getInteger( "juni" ), is( Calendar.JUNE ) );
      assertThat( dateLanguage.getInteger( "jun" ), is( Calendar.JUNE ) );
      assertThat( dateLanguage.getInteger( "juli" ), is( Calendar.JULY ) );
      assertThat( dateLanguage.getInteger( "jul" ), is( Calendar.JULY ) );
      assertThat( dateLanguage.getInteger( "august" ), is( Calendar.AUGUST ) );
      assertThat( dateLanguage.getInteger( "aug" ), is( Calendar.AUGUST ) );
      assertThat( dateLanguage.getInteger( "september" ),
                  is( Calendar.SEPTEMBER ) );
      assertThat( dateLanguage.getInteger( "sep" ), is( Calendar.SEPTEMBER ) );
      assertThat( dateLanguage.getInteger( "oktober" ), is( Calendar.OCTOBER ) );
      assertThat( dateLanguage.getInteger( "okt" ), is( Calendar.OCTOBER ) );
      assertThat( dateLanguage.getInteger( "november" ), is( Calendar.NOVEMBER ) );
      assertThat( dateLanguage.getInteger( "nov" ), is( Calendar.NOVEMBER ) );
      assertThat( dateLanguage.getInteger( "dezember" ), is( Calendar.DECEMBER ) );
      assertThat( dateLanguage.getInteger( "dez" ), is( Calendar.DECEMBER ) );
   }
   
   
   
   @Test
   public void testGetString()
   {
      assertThat( dateLanguage.getString( "eins" ), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetPluralStringStringStringInt()
   {
      assertThat( dateLanguage.getPluralString( "eins", "", 0 ), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetPluralStringStringStringString()
   {
      assertThat( dateLanguage.getPluralString( "eins", "", "" ), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetStrings()
   {
      assertThat( dateLanguage.getStrings( "eins" ).get( 0 ), is( "1" ) );
      assertThat( dateLanguage.getStrings( "eins" ).size(), is( 1 ) );
   }
   
}
