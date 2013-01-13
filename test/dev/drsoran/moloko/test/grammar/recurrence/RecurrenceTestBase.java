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

package dev.drsoran.moloko.test.grammar.recurrence;

import java.text.SimpleDateFormat;

import org.junit.Before;

import com.xtremelabs.robolectric.Robolectric;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.test.MolokoTestCase;


public abstract class RecurrenceTestBase extends MolokoTestCase
{
   private final static SimpleDateFormat SDF_FORMAT = new SimpleDateFormat( RecurrencePatternParser.DATE_PATTERN );
   
   private SimpleDateFormat dateParse;
   
   private IDateFormatContext dateFormatContext;
   
   
   
   @Override
   @Before
   public void setUp()
   {
      super.setUp();
      dateFormatContext = ( (MolokoApp) Robolectric.application ).getDateFormatContext();
      dateParse = new SimpleDateFormat( dateFormatContext.getNumericDateFormatPattern( true ) );
   }
   
   
   
   public SimpleDateFormat getRecurrenceDateFormat()
   {
      return SDF_FORMAT;
   }
   
   
   
   public SimpleDateFormat getDateParse()
   {
      return dateParse;
   }
   
   
   
   public IDateFormatContext getDateFormatContext()
   {
      return dateFormatContext;
   }
   
   
   
   public void parseRecurrence( String string,
                                String freq,
                                int interval,
                                String res,
                                String resVal,
                                boolean isEvery )
   {
      parseRecurrence( string, freq, interval, res, resVal, null, null, isEvery );
   }
   
   
   
   public void parseRecurrence( String string,
                                String freq,
                                int interval,
                                String res,
                                String resVal,
                                String resEx,
                                String resExVal,
                                boolean isEvery )
   {
      parseRecurrence( string,
                       freq,
                       interval,
                       res,
                       resVal,
                       resEx,
                       resExVal,
                       null,
                       -1,
                       isEvery );
   }
   
   
   
   public void parseRecurrence( String string,
                                String freq,
                                int interval,
                                String res,
                                String resVal,
                                String resEx,
                                String resExVal,
                                String until,
                                int forVal,
                                boolean isEvery )
   {
      RecurrenceTestHelper.parseRecurrence( dateFormatContext,
                                            string,
                                            freq,
                                            interval,
                                            res,
                                            resVal,
                                            resEx,
                                            resExVal,
                                            until,
                                            forVal,
                                            isEvery );
   }
}
