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

package dev.drsoran.rtm.test.testdatasources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class CommonLiteralsDe
{
   private final static Map< Integer, Collection< String > > MONTHS;
   
   private final static Map< Integer, Collection< String > > WEEKDAYS;
   
   private final static List< Collection< String > > NUM_STRINGS;
   
   static
   {
      MONTHS = new LinkedHashMap< Integer, Collection< String > >();
      
      MONTHS.put( 1, Arrays.asList( "Januar", "Jan", "Januars" ) );
      MONTHS.put( 2, Arrays.asList( "Februar", "Feb", "Februars" ) );
      MONTHS.put( 3, Arrays.asList( "Marz",
                                    "März",
                                    "Maerz",
                                    "Mar",
                                    "Mär",
                                    "Märzes" ) );
      MONTHS.put( 4, Arrays.asList( "April", "Apr", "Aprils" ) );
      MONTHS.put( 5, Arrays.asList( "Mai", "Mais" ) );
      MONTHS.put( 6, Arrays.asList( "Juni", "Jun", "Junis" ) );
      MONTHS.put( 7, Arrays.asList( "Juli", "Jul", "Julis" ) );
      MONTHS.put( 8, Arrays.asList( "August", "Aug", "Augusts" ) );
      MONTHS.put( 9, Arrays.asList( "September", "Sep", "Septembers" ) );
      MONTHS.put( 10, Arrays.asList( "Oktober", "Okt", "Oktobers" ) );
      MONTHS.put( 11, Arrays.asList( "November", "Nov", "Novembers" ) );
      MONTHS.put( 12, Arrays.asList( "Dezember", "Dez", "Dezembers" ) );
      
      WEEKDAYS = new LinkedHashMap< Integer, Collection< String > >();
      WEEKDAYS.put( Calendar.MONDAY, Arrays.asList( "Montag", "mo", "Montags" ) );
      WEEKDAYS.put( Calendar.TUESDAY,
                    Arrays.asList( "Dienstag", "di", "Dienstags" ) );
      WEEKDAYS.put( Calendar.WEDNESDAY,
                    Arrays.asList( "Mittwoch", "mi", "Mittwochs" ) );
      WEEKDAYS.put( Calendar.THURSDAY,
                    Arrays.asList( "Donnerstag", "do", "Donnerstags" ) );
      WEEKDAYS.put( Calendar.FRIDAY,
                    Arrays.asList( "Freitag", "fr", "Freitags" ) );
      WEEKDAYS.put( Calendar.SATURDAY,
                    Arrays.asList( "Samstag", "sa", "Samstags" ) );
      WEEKDAYS.put( Calendar.SUNDAY,
                    Arrays.asList( "Sonntag", "so", "Sonntags" ) );
      
      NUM_STRINGS = new ArrayList< Collection< String > >( 10 );
      NUM_STRINGS.add( Arrays.asList( "eins" ) );
      NUM_STRINGS.add( Arrays.asList( "zwei" ) );
      NUM_STRINGS.add( Arrays.asList( "drei" ) );
      NUM_STRINGS.add( Arrays.asList( "vier" ) );
      NUM_STRINGS.add( Arrays.asList( "funf", "fuenf", "fünf" ) );
      NUM_STRINGS.add( Arrays.asList( "sechs" ) );
      NUM_STRINGS.add( Arrays.asList( "sieben" ) );
      NUM_STRINGS.add( Arrays.asList( "acht" ) );
      NUM_STRINGS.add( Arrays.asList( "neun" ) );
      NUM_STRINGS.add( Arrays.asList( "zehn" ) );
   }
   
   
   
   public Collection< String > getMonthStrings( int month )
   {
      return MONTHS.get( month );
   }
   
   
   
   public Collection< String > getWeekdayStrings( int weekday )
   {
      return WEEKDAYS.get( weekday );
   }
   
   
   
   public Collection< String > getNumberStrings( int number )
   {
      return NUM_STRINGS.get( number - 1 );
   }
}
