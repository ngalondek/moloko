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
   
   public final static long TODAY;
   
   public final static MolokoCalendar DATE_TODAY;
   
   public final static long TOMORROW;
   
   public final static MolokoCalendar DATE_TOMORROW;
   
   public final static long YESTERDAY;
   
   public final static MolokoCalendar DATE_YESTERDAY;
   
   public final static long LATER = NOW + 3600 * 1000;
   
   public final static long EVEN_LATER = LATER + 3600 * 1000;
   
   public final static long NEVER = Constants.NO_TIME;
   
   public final static long NO_ID = Constants.NO_ID;
   
   static
   {
      DATE_TODAY = MolokoCalendar.getInstance();
      DATE_TODAY.setHasTime( false );
      
      TODAY = DATE_TODAY.getTimeInMillis();
      
      DATE_TOMORROW = DATE_TODAY.clone();
      DATE_TOMORROW.add( Calendar.DATE, 1 );
      
      TOMORROW = DATE_TOMORROW.getTimeInMillis();
      
      DATE_YESTERDAY = DATE_TODAY.clone();
      DATE_YESTERDAY.add( Calendar.DATE, -1 );
      
      YESTERDAY = DATE_YESTERDAY.getTimeInMillis();
   }
}
