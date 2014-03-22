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

import dev.drsoran.rtm.Strings;


public class TimeParserTestLanguageDe implements ITimeParserTestLanguage
{
   
   @Override
   public Collection< String > getAts()
   {
      return Arrays.asList( Strings.EMPTY_STRING, "@", "um ", "am ", "an " );
   }
   
   
   
   @Override
   public Collection< String > getMidday()
   {
      return Arrays.asList( "mittag", "mittags" );
   }
   
   
   
   @Override
   public Collection< String > getMidnight()
   {
      return Arrays.asList( "mitternacht", "mitternachts" );
   }
   
   
   
   @Override
   public Collection< String > getNever()
   {
      return Arrays.asList( "nie" );
   }
   
   
   
   @Override
   public Collection< String > getAm()
   {
      return Arrays.asList( "vorm", "vorm.", "vormittag", "vormittags" );
   }
   
   
   
   @Override
   public Collection< String > getPm()
   {
      return Arrays.asList( "nachm", "nachm.", "nachmittag", "nachmittags" );
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
      return Arrays.asList( "tage", "tag", "d" );
   }
   
   
   
   @Override
   public Collection< String > getHoursLiteral()
   {
      return Arrays.asList( "stunden", "stunde", "std", "h" );
   }
   
   
   
   @Override
   public Collection< String > getMinutesLiteral()
   {
      return Arrays.asList( "minuten", "minute", "min", "m" );
   }
   
   
   
   @Override
   public Collection< String > getSecondsLiteral()
   {
      return Arrays.asList( "sekunden", "sekunde", "sek", "sec", "s" );
   }
   
   
   
   @Override
   public Collection< String > getTimeConcatenators()
   {
      return Arrays.asList( Strings.EMPTY_STRING, ",", " und " );
   }
}
