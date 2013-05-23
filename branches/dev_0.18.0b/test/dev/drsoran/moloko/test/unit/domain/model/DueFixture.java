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

package dev.drsoran.moloko.test.unit.domain.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.test.unit.EqualsHashCodeTestCase;


@RunWith( Theories.class )
public class DueFixture extends EqualsHashCodeTestCase
{
   @DataPoint
   public final static long NOW = System.currentTimeMillis();
   
   @DataPoint
   public final static long NEVER = Constants.NO_TIME;
   
   @DataPoint
   public final static boolean HAS_TIME = true;
   
   @DataPoint
   public final static boolean HAS_NOT_TIME = false;
   
   
   
   @Theory
   public void testDue( long due, boolean hasDueTime )
   {
      Assume.assumeTrue( !( due == NEVER && HAS_TIME ) );
      new Due( due, hasDueTime );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testDueNoTime()
   {
      new Due( Constants.NO_TIME, true );
   }
   
   
   
   @Theory
   public void testGetMillisUtc( long due, boolean hasDueTime )
   {
      Assume.assumeTrue( !( due == NEVER && HAS_TIME ) );
      assertThat( new Due( due, hasDueTime ).getMillisUtc(), is( due ) );
   }
   
   
   
   @Theory
   public void testHasDueTime( long due, boolean hasDueTime )
   {
      Assume.assumeTrue( !( due == NEVER && HAS_TIME ) );
      assertThat( new Due( due, hasDueTime ).hasDueTime(), is( hasDueTime ) );
   }
   
   
   
   @Theory
   public void testToString( long due, boolean hasDueTime )
   {
      Assume.assumeTrue( !( due == NEVER && HAS_TIME ) );
      new Due( due, hasDueTime ).toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new Due( NOW, HAS_TIME );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new Due( NEVER, HAS_NOT_TIME );
   }
}
