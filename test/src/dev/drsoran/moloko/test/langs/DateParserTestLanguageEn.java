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


public class DateParserTestLanguageEn implements IDateParserTestLanguage
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
      return commonLiterals.getWeekdayStrings( weekday );
   }
   
   
   
   @Override
   public Collection< String > getNumberStrings( int number )
   {
      return commonLiterals.getNumberStrings( number );
   }
   
   
   
   @Override
   public Collection< String > getNumericDateSeparators()
   {
      return Arrays.asList( ".", "-", "/" );
   }
   
   
   
   @Override
   public Collection< String > getXst()
   {
      return Arrays.asList( "st", "nd", "th", "rd" );
   }
   
   
   
   @Override
   public Collection< String > getDayMonthSeparators()
   {
      return Arrays.asList( " of ", "-a ", "-", ",", "." );
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
      return Arrays.asList( " and ", "," );
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
      return Arrays.asList( "days", "day", "d" );
   }
   
   
   
   @Override
   public Collection< String > getNext()
   {
      return Arrays.asList( "next " );
   }
   
   
   
   @Override
   public Collection< String > getIn()
   {
      return Arrays.asList( "in " );
   }
   
   
   
   @Override
   public Collection< String > getOf()
   {
      return Arrays.asList( "of " );
   }
   
   
   
   @Override
   public Collection< String > getEndOfThe()
   {
      return Arrays.asList( "end ", "end of ", "end of the " );
   }
   
   
   
   @Override
   public Collection< String > getToday()
   {
      return Arrays.asList( "today", "tod", "tonight", "ton" );
   }
   
   
   
   @Override
   public Collection< String > getTomorrow()
   {
      return Arrays.asList( "tom", "tmr", "tomorrow", "tommorow" );
   }
   
   
   
   @Override
   public Collection< String > getYesterday()
   {
      return Arrays.asList( "yesterday" );
   }
   
   
   
   @Override
   public Collection< String > getNow()
   {
      return Arrays.asList( "now" );
   }
   
   
   
   @Override
   public Collection< String > getNever()
   {
      return Arrays.asList( "never" );
   }
   
}
