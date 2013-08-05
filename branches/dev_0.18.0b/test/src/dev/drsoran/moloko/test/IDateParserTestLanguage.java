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

package dev.drsoran.moloko.test;

import java.util.Collection;


public interface IDateParserTestLanguage
{
   Collection< String > getMonthStrings( int month );
   
   
   
   Collection< String > getWeekdayStrings( int weekday );
   
   
   
   Collection< String > getNumberStrings( int number );
   
   
   
   Collection< String > getNumericDateSeparators();
   
   
   
   Collection< String > getXst();
   
   
   
   Collection< String > getDayMonthSeparators();
   
   
   
   Collection< String > getMonthYearSeparators();
   
   
   
   Collection< String > getMonthDaySeparators();
   
   
   
   Collection< String > getDateConcatenators();
   
   
   
   Collection< String > getYearLiterals();
   
   
   
   Collection< String > getMonthLiterals();
   
   
   
   Collection< String > getWeekLiterals();
   
   
   
   Collection< String > getDayLiterals();
   
   
   
   Collection< String > getNext();
   
   
   
   Collection< String > getIn();
   
   
   
   Collection< String > getOf();
   
   
   
   Collection< String > getEndOfThe();
   
   
   
   Collection< String > getToday();
   
   
   
   Collection< String > getTomorrow();
   
   
   
   Collection< String > getYesterday();
   
   
   
   Collection< String > getNow();
   
   
   
   Collection< String > getNever();
}
