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

import java.util.Calendar;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.content.Constants;


public final class TestConstants
{
   public final static long NOW = System.currentTimeMillis();
   
   public final static long YESTERDAY;
   
   public final static long TODAY;
   
   public final static long TOMORROW;
   
   public final static long LATER = NOW + 3600 * 1000;
   
   public final static long EVEN_LATER = LATER + 3600 * 1000;
   
   public final static long NEVER = Constants.NO_TIME;
   
   public final static long NO_ID = Constants.NO_ID;
   
   static
   {
      MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setHasTime( false );
      
      TODAY = cal.getTimeInMillis();
      
      cal.add( Calendar.DATE, 1 );
      
      TOMORROW = cal.getTimeInMillis();
      
      cal.add( Calendar.DATE, -2 );
      
      YESTERDAY = cal.getTimeInMillis();
   }
}
