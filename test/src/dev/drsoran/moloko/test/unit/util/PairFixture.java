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

package dev.drsoran.moloko.test.unit.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.test.EqualsHashCodeTestCase;
import dev.drsoran.moloko.util.Pair;


@RunWith( Theories.class )
public class PairFixture extends EqualsHashCodeTestCase
{
   @DataPoint
   public final static Integer intNull = null;
   
   @DataPoint
   public final static Integer int1 = 1;
   
   @DataPoint
   public final static Short shortNull = null;
   
   @DataPoint
   public final static Short short2 = 2;
   
   
   
   @Theory
   public void testPair( Integer i, Short s )
   {
      final Pair< Integer, Short > p = new Pair< Integer, Short >( i, s );
      assertThat( p.first, is( i ) );
      assertThat( p.second, is( s ) );
   }
   
   
   
   @Theory
   public void testToString( Integer i, Short s )
   {
      final Pair< Integer, Short > p = new Pair< Integer, Short >( i, s );
      p.toString();
   }
   
   
   
   @Theory
   public void testCreate( Integer i, Short s )
   {
      final Pair< Integer, Short > p = Pair.create( i, s );
      assertThat( p.first, is( i ) );
      assertThat( p.second, is( s ) );
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return Pair.create( 1, (Short) null );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return Pair.create( 1, (short) 2 );
   }
}
