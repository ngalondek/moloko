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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import dev.drsoran.rtm.Strings;


public class RecurrenceParserTestLanguageDe implements
         IRecurrenceParserTestLanguage
{
   private final CommonLiteralsDe commonLiterals = new CommonLiteralsDe();
   
   
   
   @Override
   public Collection< String > getMonthStrings( int month )
   {
      return commonLiterals.getMonthStrings( month );
   }
   
   
   
   @Override
   public Collection< String > getWeekdayStrings( int weekday )
   {
      return commonLiterals.getWeekdayStrings( weekday == 7 ? 1 : weekday + 1 /* map to Calendar */);
   }
   
   
   
   @Override
   public Collection< String > getNumberStrings( int number )
   {
      return commonLiterals.getNumberStrings( number );
   }
   
   
   
   @Override
   public Collection< String > getEvery()
   {
      return Arrays.asList( "jede", "jedes", "alle" );
   }
   
   
   
   @Override
   public Collection< String > getAfter()
   {
      return Arrays.asList( "nach" );
   }
   
   
   
   @Override
   public Collection< String > getDaily()
   {
      return Arrays.asList( "täglich", "taeglich", "taglich" );
   }
   
   
   
   @Override
   public Collection< String > getLast()
   {
      return Arrays.asList( " letzten ", " letzte ", " letztes " );
   }
   
   
   
   @Override
   public Collection< String > getBiweekly()
   {
      return Collections.< String > emptyList();
   }
   
   
   
   @Override
   public Collection< String > getYearLiterals()
   {
      return Arrays.asList( "Jahre", "Jahr", "Jahren" );
   }
   
   
   
   @Override
   public Collection< String > getMonthLiterals()
   {
      return Arrays.asList( "Monate", "Monat", "Monaten" );
   }
   
   
   
   @Override
   public Collection< String > getWeekLiterals()
   {
      return Arrays.asList( "Wochen", "Woche" );
   }
   
   
   
   @Override
   public Collection< String > getDayLiterals()
   {
      return Arrays.asList( "Tagen", "Tag", "Tage" );
   }
   
   
   
   @Override
   public Collection< String > getBusinessDayLiterals()
   {
      return Arrays.asList( "Wochentage", "Wochentag", "wochentags" );
   }
   
   
   
   @Override
   public Collection< String > getWeekendLiterals()
   {
      return Arrays.asList( "Wochenende", "Wochenenden" );
   }
   
   
   
   @Override
   public Collection< String > getOnThe()
   {
      return Arrays.asList( " am ", " an ", " vom ", Strings.EMPTY_STRING );
   }
   
   
   
   @Override
   public Collection< String > getXst( int i )
   {
      switch ( i )
      {
         case 1:
            return Arrays.asList( "1.", "erster", "erste" );
            
         case 2:
            return Arrays.asList( "2.", "zweiter", "zweite", "anderer" );
            
         case 3:
            return Arrays.asList( "3.", "dritter", "dritte" );
            
         case 4:
            return Arrays.asList( "4.", "vierter", "vierte" );
            
         case 5:
            return Arrays.asList( "5.", "fünfter", "funfte" );
            
         default :
            throw new IllegalArgumentException( "i" );
      }
   }
   
   
   
   @Override
   public Collection< String > getSeparators()
   {
      return Arrays.asList( ",", " und " );
   }
   
   
   
   @Override
   public Collection< String > getInOfMonth()
   {
      return Arrays.asList( " in ", " im ", Strings.EMPTY_STRING );
   }
   
   
   
   @Override
   public Collection< String > getUntil()
   {
      return Arrays.asList( " bis " );
   }
   
   
   
   @Override
   public Collection< String > getFor()
   {
      return Arrays.asList( " fur ", " für " );
   }
   
   
   
   @Override
   public Collection< String > getTimes()
   {
      return Arrays.asList( " mal ", Strings.EMPTY_STRING );
   }
   
}
