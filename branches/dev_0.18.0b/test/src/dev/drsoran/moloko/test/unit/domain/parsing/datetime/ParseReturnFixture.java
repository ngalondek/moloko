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

package dev.drsoran.moloko.test.unit.domain.parsing.datetime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.datetime.ParseReturn;
import dev.drsoran.moloko.test.MolokoTestCase;


public class ParseReturnFixture extends MolokoTestCase
{
   @Test
   public void testParseDateReturn()
   {
      new ParseReturn( 0, false, MolokoCalendar.getInstance() );
   }
   
   
   
   @Test
   public void testNumParsedChars()
   {
      assertThat( new ParseReturn( 10, false, MolokoCalendar.getInstance() ).numParsedChars,
                  is( 10 ) );
   }
   
   
   
   @Test
   public void testIsEof()
   {
      assertThat( new ParseReturn( 10, true, MolokoCalendar.getInstance() ).isEof,
                  is( true ) );
      assertThat( new ParseReturn( 10, false, MolokoCalendar.getInstance() ).isEof,
                  is( false ) );
   }
   
   
   
   @Test
   public void testCal()
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      assertThat( new ParseReturn( 10, true, cal ).cal,
                  is( sameInstance( cal ) ) );
      assertThat( new ParseReturn( 10, false, null ).cal,
                  is( (MolokoCalendar) null ) );
   }
}
