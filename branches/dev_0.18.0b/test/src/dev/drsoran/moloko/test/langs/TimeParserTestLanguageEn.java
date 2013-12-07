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

import dev.drsoran.Strings;


public class TimeParserTestLanguageEn implements ITimeParserTestLanguage
{
   
   @Override
   public Collection< String > getAts()
   {
      return Arrays.asList( Strings.EMPTY_STRING, "@", "at " );
   }
   
   
   
   @Override
   public Collection< String > getMidday()
   {
      return Arrays.asList( "midday", "noon" );
   }
   
   
   
   @Override
   public Collection< String > getMidnight()
   {
      return Arrays.asList( "midnight" );
   }
   
   
   
   @Override
   public Collection< String > getNever()
   {
      return Arrays.asList( "never" );
   }
   
   
   
   @Override
   public Collection< String > getAm()
   {
      return Arrays.asList( "a", "am" );
   }
   
   
   
   @Override
   public Collection< String > getPm()
   {
      return Arrays.asList( "p", "pm" );
   }
   
   
   
   @Override
   public Collection< String > getTimeSeparators()
   {
      return Arrays.asList( ":", "." );
   }
   
   
   
   @Override
   public Collection< String > getDecimalSigns()
   {
      return Arrays.asList( ".", "," );
   }
   
   
   
   @Override
   public Collection< String > getDaysLiteral()
   {
      return Arrays.asList( "days", "day", "d" );
   }
   
   
   
   @Override
   public Collection< String > getHoursLiteral()
   {
      return Arrays.asList( "hours", "hour", "hrs", "hr", "h" );
   }
   
   
   
   @Override
   public Collection< String > getMinutesLiteral()
   {
      return Arrays.asList( "minutes", "minute", "mins", "min", "m" );
   }
   
   
   
   @Override
   public Collection< String > getSecondsLiteral()
   {
      return Arrays.asList( "seconds", "second", "secs", "sec", "s" );
   }
   
   
   
   @Override
   public Collection< String > getTimeConcatenators()
   {
      return Arrays.asList( Strings.EMPTY_STRING, ",", " and " );
   }
}
