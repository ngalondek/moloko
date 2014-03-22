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

import java.util.Collection;


public interface ITimeParserTestLanguage
{
   Collection< String > getAts();
   
   
   
   Collection< String > getMidday();
   
   
   
   Collection< String > getMidnight();
   
   
   
   Collection< String > getNever();
   
   
   
   Collection< String > getAm();
   
   
   
   Collection< String > getPm();
   
   
   
   Collection< String > getTimeSeparators();
   
   
   
   Collection< String > getDecimalSigns();
   
   
   
   Collection< String > getDaysLiteral();
   
   
   
   Collection< String > getHoursLiteral();
   
   
   
   Collection< String > getMinutesLiteral();
   
   
   
   Collection< String > getSecondsLiteral();
   
   
   
   Collection< String > getTimeConcatenators();
}
