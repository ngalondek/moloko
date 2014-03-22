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


public class DateParserTestLanguageDe implements IDateParserTestLanguage
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
