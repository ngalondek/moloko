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

package dev.drsoran.moloko.test.langs;

import java.util.Arrays;
import java.util.Collection;

import dev.drsoran.moloko.util.Strings;


public class RecurrenceParserTestLanguageEn implements
         IRecurrenceParserTestLanguage
{
   private final CommonLiteralsEn commonLiterals = new CommonLiteralsEn();
   
   
   
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
      return Arrays.asList( "every", "each" );
   }
   
   
   
   @Override
   public Collection< String > getAfter()
   {
      return Arrays.asList( "after" );
   }
   
   
   
   @Override
   public Collection< String > getDaily()
   {
      return Arrays.asList( "daily" );
   }
   
   
   
   @Override
   public Collection< String > getLast()
   {
      return Arrays.asList( " last " );
   }
   
   
   
   @Override
   public Collection< String > getWeekday()
   {
      return Arrays.asList( "weekday", "weekdays" );
   }
   
   
   
   @Override
   public Collection< String > getWeekend()
   {
      return Arrays.asList( "weekend", "weekends" );
   }
   
   
   
   @Override
   public Collection< String > getBiweekly()
   {
      return Arrays.asList( "fortnight", "biweekly" );
   }
   
   
   
   @Override
   public Collection< String > getYearLiterals()
   {
      return Arrays.asList( "years", "year", "yrs", "yr" );
   }
   
   
   
   @Override
   public Collection< String > getMonthLiterals()
   {
      return Arrays.asList( "months", "month", "mons" );
   }
   
   
   
   @Override
   public Collection< String > getWeekLiterals()
   {
      return Arrays.asList( "weeks", "week", "wks", "wk" );
   }
   
   
   
   @Override
   public Collection< String > getDayLiterals()
   {
      return Arrays.asList( "days", "day" );
   }
   
   
   
   @Override
   public Collection< String > getYearWeekdaysSeparators()
   {
      return Arrays.asList( " on the ", " on ", " the ", Strings.EMPTY_STRING );
   }
   
   
   
   @Override
   public Collection< String > getXst( int i )
   {
      switch ( i )
      {
         case 1:
            return Arrays.asList( "1.", "1st", "first" );
            
         case 2:
            return Arrays.asList( "2.", "2nd", "second", "other" );
            
         case 3:
            return Arrays.asList( "3.", "3rd", "third" );
            
         case 4:
            return Arrays.asList( "4.", "4th", "fourth" );
            
         case 5:
            return Arrays.asList( "5.", "5th", "fifth" );
            
         default :
            throw new IllegalArgumentException( "i" );
      }
   }
   
   
   
   @Override
   public Collection< String > getSeparators()
   {
      return Arrays.asList( ",", " and " );
   }
   
   
   
   @Override
   public Collection< String > getInOfMonth()
   {
      return Arrays.asList( " in ", " of ", Strings.EMPTY_STRING );
   }
}
