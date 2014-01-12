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

package dev.drsoran.test.unit.rtm.parsing.grammar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.parsing.grammar.ANTLRNoCaseStringStream;


public class ANTLRNoCaseStringStreamFixture extends MolokoTestCase
{
   @Test
   public void testANTLRNoCaseStringStream()
   {
      new ANTLRNoCaseStringStream( "testString" );
   }
   
   
   
   @Test( expected = NullPointerException.class )
   public void testANTLRNoCaseStringStreamNullString()
   {
      new ANTLRNoCaseStringStream( null );
   }
   
   
   
   @Test
   public void testLA()
   {
      final String testString = "TestStriNG W1tH     SpaCES";
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( testString );
      
      final char[] testStringLowerCharArray = testString.toLowerCase()
                                                        .toCharArray();
      
      final char[] stringStreamCharArray = new char[ testStringLowerCharArray.length ];
      
      int i = 0;
      int streamChar;
      while ( ( streamChar = stream.LA( 1 ) ) != ANTLRNoCaseStringStream.EOF )
      {
         stringStreamCharArray[ i ] = (char) streamChar;
         stream.consume();
         ++i;
      }
      
      assertArrayEquals( testStringLowerCharArray, stringStreamCharArray );
   }
   
   
   
   @Test
   public void testLA_0()
   {
      assertThat( new ANTLRNoCaseStringStream( "Test" ).LA( 0 ), is( 0 ) );
   }
   
   
   
   @Test
   public void testLA_minus_1()
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( "T" );
      stream.consume();
      
      assertThat( stream.LA( -1 ), is( (int) 't' ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( "Test" );
      stream.toString();
      
      stream.consume();
      stream.toString();
   }
}
