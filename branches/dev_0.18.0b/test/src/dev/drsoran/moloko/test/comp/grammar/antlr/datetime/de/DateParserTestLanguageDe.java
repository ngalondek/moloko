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

package dev.drsoran.moloko.test.comp.grammar.antlr.datetime.de;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.drsoran.moloko.test.IDateParserTestLanguage;


public class DateParserTestLanguageDe implements IDateParserTestLanguage
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
   
   
   
   @Override
   public Collection< String > getMonthStrings( int month )
   {
      return MONTHS.get( month );
   }
   
   
   
   @Override
   public Collection< String > getWeekdayStrings( int weekday )
   {
      return WEEKDAYS.get( weekday );
   }
   
   
   
   @Override
   public Collection< String > getNumberStrings( int number )
   {
      return NUM_STRINGS.get( number - 1 );
   }
   
   
   
   @Override
   public Collection< String > getNumericDateSeparators()
   {
      return Arrays.asList( ".", "-", "/" );
   }
   
   
   
   @Override
   public Collection< String > getXst()
   {
      return Arrays.asList( " " );
   }
   
   
   
   @Override
   public Collection< String > getDayMonthSeparators()
   {
      return Arrays.asList( " von ", " ab ", " seit ", "des ", "-", ",", "." );
   }
   
   
   
   @Override
   public Collection< String > getMonthYearSeparators()
   {
      return Arrays.asList( "-", "." );
   }
   
   
   
   @Override
   public Collection< String > getMonthDaySeparators()
   {
      return Arrays.asList( "-", ",", "." );
   }
   
   
   
   @Override
   public Collection< String > getDateConcatenators()
   {
      return Arrays.asList( " und ", "," );
   }
   
   
   
   @Override
   public Collection< String > getYearLiterals()
   {
      return Arrays.asList( "jahre", "jahr", "jahres" );
   }
   
   
   
   @Override
   public Collection< String > getMonthLiterals()
   {
      return Arrays.asList( "monate", "monat", "monats" );
   }
   
   
   
   @Override
   public Collection< String > getWeekLiterals()
   {
      return Arrays.asList( "wochen", "woche" );
   }
   
   
   
   @Override
   public Collection< String > getDayLiterals()
   {
      return Arrays.asList( "tage", "tag", "tages" );
   }
   
   
   
   @Override
   public Collection< String > getNext()
   {
      return Arrays.asList( "nächste ", "naechste ", "nachstem " );
   }
   
   
   
   @Override
   public Collection< String > getIn()
   {
      return Arrays.asList( "in " );
   }
   
   
   
   @Override
   public Collection< String > getOf()
   {
      return Arrays.asList( "von ", "ab ", "des " );
   }
   
   
   
   @Override
   public Collection< String > getEndOfThe()
   {
      return Arrays.asList( "ende ", "ende des ", "ende von dem " );
   }
   
   
   
   @Override
   public Collection< String > getToday()
   {
      return Arrays.asList( "heute" );
   }
   
   
   
   @Override
   public Collection< String > getTomorrow()
   {
      return Arrays.asList( "morgen", "mrg" );
   }
   
   
   
   @Override
   public Collection< String > getYesterday()
   {
      return Arrays.asList( "gestern" );
   }
   
   
   
   @Override
   public Collection< String > getNow()
   {
      return Arrays.asList( "jetzt" );
   }
   
   
   
   @Override
   public Collection< String > getNever()
   {
      return Arrays.asList( "nie" );
   }
}
