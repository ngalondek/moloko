/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.test.grammar.datetime;

import org.junit.Before;

import com.xtremelabs.robolectric.Robolectric;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.test.MolokoTestCase;


public abstract class DateParserTestBase extends MolokoTestCase
{
   private IDateFormatContext dateFormatContext;
   
   
   
   @Override
   @Before
   public void setUp()
   {
      super.setUp();
      dateFormatContext = ( (MolokoApp) Robolectric.application ).getDateFormatContext();
   }
   
   
   
   public IDateFormatContext getDateFormatContext()
   {
      return dateFormatContext;
   }
   
   
   
   public void parseDate( String string, int d, int m, int y )
   {
      parseDate( string, d, m, y, -1, -1, -1 );
   }
   
   
   
   public void parseDate( String string,
                          int d,
                          int m,
                          int y,
                          int h,
                          int min,
                          int s )
   {
      DateTimeTestHelper.parseDate( dateFormatContext,
                                    string,
                                    d,
                                    m,
                                    y,
                                    h,
                                    min,
                                    s );
   }
   
   
   
   public void parseDate( String string,
                          int d,
                          int m,
                          int y,
                          int h,
                          int min,
                          int s,
                          boolean hasDate,
                          boolean hasTime )
   {
      DateTimeTestHelper.parseDate( dateFormatContext,
                                    string,
                                    d,
                                    m,
                                    y,
                                    h,
                                    min,
                                    s,
                                    hasDate,
                                    hasTime );
   }
   
   
   
   public void parseDateWithin( String string,
                                boolean past,
                                int sy,
                                int sm,
                                int sw,
                                int sd,
                                int ey,
                                int em,
                                int ew,
                                int ed )
   {
      DateTimeTestHelper.parseDateWithin( dateFormatContext,
                                          string,
                                          past,
                                          sy,
                                          sm,
                                          sw,
                                          sd,
                                          ey,
                                          em,
                                          ew,
                                          ed );
   }
}
