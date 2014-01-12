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

import java.util.Locale;

import org.easymock.EasyMock;

import dev.drsoran.moloko.ILog;
import dev.drsoran.rtm.parsing.lang.RecurrenceSentenceLanguage;


public class RecurrenceSentenceTestLanguageEn
{
   private RecurrenceSentenceTestLanguageEn()
   {
   }
   
   
   
   // TODO: This should be read from the resource XML
   public static RecurrenceSentenceLanguage get()
   {
      final RecurrenceSentenceLanguage lang = new RecurrenceSentenceLanguage( Locale.ENGLISH,
                                                                              EasyMock.createNiceMock( ILog.class ) );
      
      // every
      lang.add( "every_year_1", "every year" );
      lang.add( "every_year_n", "every %s years" );
      
      lang.add( "every_month_1", "every month" );
      lang.add( "every_month_n", "every %s months" );
      
      lang.add( "every_week_1", "every week" );
      lang.add( "every_week_n", "every %s weeks" );
      
      lang.add( "every_day_1", "daily" );
      lang.add( "every_day_n", "every %s days" );
      
      // after
      lang.add( "after_year_1", "after one year" );
      lang.add( "after_year_n", "after %s years" );
      
      lang.add( "after_month_1", "after one month" );
      lang.add( "after_month_n", "after %s months" );
      
      lang.add( "after_week_1", "after one week" );
      lang.add( "after_week_n", "after %s weeks" );
      
      lang.add( "after_day_1", "after one day" );
      lang.add( "after_day_n", "after %s days" );
      
      // weekdays
      lang.add( "MO", "Monday" );
      lang.add( "TU", "Tuesday" );
      lang.add( "WE", "Wednesday" );
      lang.add( "TH", "Thrusday" );
      lang.add( "FR", "Friday" );
      lang.add( "SA", "Saturday" );
      lang.add( "SU", "Sunday" );
      
      // months
      lang.add( "m1", "January" );
      lang.add( "m2", "February" );
      lang.add( "m3", "March" );
      lang.add( "m4", "April" );
      lang.add( "m5", "May" );
      lang.add( "m6", "June" );
      lang.add( "m7", "July" );
      lang.add( "m8", "August" );
      lang.add( "m9", "September" );
      lang.add( "m10", "October" );
      lang.add( "m11", "November" );
      lang.add( "m12", "December" );
      
      // others
      lang.add( "last", "last" );
      lang.add( "on_the", "on the" );
      lang.add( "in", "in" );
      lang.add( "until", "until" );
      lang.add( "for", "for" );
      lang.add( "times", "times" );
      
      return lang;
   }
}
