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

package dev.drsoran.moloko.test.unit.grammar.datetime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.grammar.datetime.ParseReturn;
import dev.drsoran.moloko.test.MolokoTestCase;


public class ParseReturnFixture extends MolokoTestCase
{
   @Test
   public void testParseDateReturn()
   {
      new ParseReturn( 0, false );
   }
   
   
   
   @Test
   public void testNumParsedChars()
   {
      assertThat( new ParseReturn( 10, false ).numParsedChars, is( 10 ) );
   }
   
   
   
   @Test
   public void testIsEof()
   {
      assertThat( new ParseReturn( 10, true ).isEof, is( true ) );
      assertThat( new ParseReturn( 10, false ).isEof, is( false ) );
   }
}
