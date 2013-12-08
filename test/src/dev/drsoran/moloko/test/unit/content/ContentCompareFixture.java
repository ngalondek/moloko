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

package dev.drsoran.moloko.test.unit.content;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dev.drsoran.moloko.content.ContentCompare;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;


public class ContentCompareFixture extends MolokoTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( ContentCompare.class );
   }
   
   
   
   @Test
   public void testIsDifferentVV()
   {
      assertTrue( ContentCompare.isDifferent( (String) null, "test" ) );
      assertTrue( ContentCompare.isDifferent( "test", (String) null ) );
      assertTrue( ContentCompare.isDifferent( "test", "test1" ) );
      assertTrue( ContentCompare.isDifferent( "test1", "test" ) );
      assertFalse( ContentCompare.isDifferent( null, null ) );
      assertFalse( ContentCompare.isDifferent( "test", "test" ) );
   }
   
   
   
   @Test
   public void testIsDifferentIntInt()
   {
      assertTrue( ContentCompare.isDifferent( 1, 0 ) );
      assertTrue( ContentCompare.isDifferent( 0, 1 ) );
      assertFalse( ContentCompare.isDifferent( 1, 1 ) );
   }
   
   
   
   @Test
   public void testIsDifferentLongLong()
   {
      assertTrue( ContentCompare.isDifferent( 1L, 0L ) );
      assertTrue( ContentCompare.isDifferent( 0L, 1L ) );
      assertFalse( ContentCompare.isDifferent( 1L, 1L ) );
   }
   
   
   
   @Test
   public void testIsDifferentDoubleDouble()
   {
      assertTrue( ContentCompare.isDifferent( 1.0, 0.0 ) );
      assertTrue( ContentCompare.isDifferent( 0.0, 1.0 ) );
      assertFalse( ContentCompare.isDifferent( 1.0, 1.0 ) );
   }
   
   
   
   @Test
   public void testIsDifferentFloatFloat()
   {
      assertTrue( ContentCompare.isDifferent( 1.0f, 0.0f ) );
      assertTrue( ContentCompare.isDifferent( 0.0f, 1.0f ) );
      assertFalse( ContentCompare.isDifferent( 1.0f, 1.0f ) );
   }
   
   
   
   @Test
   public void testIsDifferentBooleanBoolean()
   {
      assertTrue( ContentCompare.isDifferent( true, false ) );
      assertTrue( ContentCompare.isDifferent( false, true ) );
      assertFalse( ContentCompare.isDifferent( false, false ) );
      assertFalse( ContentCompare.isDifferent( true, true ) );
   }
}
