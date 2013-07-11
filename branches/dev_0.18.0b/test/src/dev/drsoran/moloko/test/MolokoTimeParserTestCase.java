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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.ParseReturn;


public abstract class MolokoTimeParserTestCase extends MolokoTestCase
{
   private ITimeParser timeParser;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      timeParser = createTimeParser();
   }
   
   
   
   public MolokoCalendar testParseTime( String timeToParse ) throws GrammarException
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      final ParseReturn ret = timeParser.parseTime( timeToParse, cal, false );
      
      verifyParseResult( timeToParse, cal, ret );
      
      return cal;
   }
   
   
   
   public MolokoCalendar testParseTimeSpec( String timeSpecToParse ) throws GrammarException
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      final ParseReturn ret = timeParser.parseTimeSpec( timeSpecToParse,
                                                        cal,
                                                        false );
      
      verifyParseResult( timeSpecToParse, cal, ret );
      
      return cal;
   }
   
   
   
   public void testParseTimeEstimate( String estimation, long expectedMillis ) throws GrammarException
   {
      final long diff = timeParser.parseTimeEstimate( estimation );
      assertThat( "Diff is wrong.", diff, is( expectedMillis ) );
   }
   
   
   
   private void verifyParseResult( String toOarse,
                                   final MolokoCalendar cal,
                                   final ParseReturn ret )
   {
      assertThat( "Calendar has no time.", cal.hasTime(), is( true ) );
      
      assertThat( ret.isEof, is( true ) );
      assertThat( ret.numParsedChars, is( toOarse.length() ) );
   }
   
   
   
   protected abstract ITimeParser createTimeParser();
}
