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

package dev.drsoran.test.unit.rtm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dev.drsoran.Compare;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;


public class CompareFixture extends MolokoTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( Compare.class );
   }
   
   
   
   @Test
   public void testIsDifferentVV()
   {
      assertTrue( Compare.isDifferent( (String) null, "test" ) );
      assertTrue( Compare.isDifferent( "test", (String) null ) );
      assertTrue( Compare.isDifferent( "test", "test1" ) );
      assertTrue( Compare.isDifferent( "test1", "test" ) );
      assertFalse( Compare.isDifferent( null, null ) );
      assertFalse( Compare.isDifferent( "test", "test" ) );
   }
   
   
   
   @Test
   public void testIsDifferentIntInt()
   {
      assertTrue( Compare.isDifferent( 1, 0 ) );
      assertTrue( Compare.isDifferent( 0, 1 ) );
      assertFalse( Compare.isDifferent( 1, 1 ) );
   }
   
   
   
   @Test
   public void testIsDifferentLongLong()
   {
      assertTrue( Compare.isDifferent( 1L, 0L ) );
      assertTrue( Compare.isDifferent( 0L, 1L ) );
      assertFalse( Compare.isDifferent( 1L, 1L ) );
   }
   
   
   
   @Test
   public void testIsDifferentDoubleDouble()
   {
      assertTrue( Compare.isDifferent( 1.0, 0.0 ) );
      assertTrue( Compare.isDifferent( 0.0, 1.0 ) );
      assertFalse( Compare.isDifferent( 1.0, 1.0 ) );
   }
   
   
   
   @Test
   public void testIsDifferentFloatFloat()
   {
      assertTrue( Compare.isDifferent( 1.0f, 0.0f ) );
      assertTrue( Compare.isDifferent( 0.0f, 1.0f ) );
      assertFalse( Compare.isDifferent( 1.0f, 1.0f ) );
   }
   
   
   
   @Test
   public void testIsDifferentBooleanBoolean()
   {
      assertTrue( Compare.isDifferent( true, false ) );
      assertTrue( Compare.isDifferent( false, true ) );
      assertFalse( Compare.isDifferent( false, false ) );
      assertFalse( Compare.isDifferent( true, true ) );
   }
}
