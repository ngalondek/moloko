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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.test.MolokoTestCase;


public class ExtendedTaskCountFixture extends MolokoTestCase
{
   @Test
   public void testExtendedTaskCount()
   {
      createExtTaskCount();
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testExtendedTaskCountNegIncompl()
   {
      new ExtendedTaskCount( -1, 0, 0, 0, 0, 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testExtendedTaskCountNegCompl()
   {
      new ExtendedTaskCount( 0, -1, 0, 0, 0, 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testExtendedTaskCountNegDueTod()
   {
      new ExtendedTaskCount( 0, 0, -1, 0, 0, 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testExtendedTaskCountNegDueTom()
   {
      new ExtendedTaskCount( 0, 0, 0, -1, 0, 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testExtendedTaskCountNegOverdue()
   {
      new ExtendedTaskCount( 0, 0, 0, 0, -1, 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testExtendedTaskCountNegSum()
   {
      new ExtendedTaskCount( 0, 0, 0, 0, 0, -1 );
   }
   
   
   
   @Test
   public void testGetIncompleteTaskCount()
   {
      assertThat( createExtTaskCount().getIncompleteTaskCount(), is( 0 ) );
   }
   
   
   
   @Test
   public void testGetCompletedTaskCount()
   {
      assertThat( createExtTaskCount().getCompletedTaskCount(), is( 1 ) );
   }
   
   
   
   @Test
   public void testGetDueTodayTaskCount()
   {
      assertThat( createExtTaskCount().getDueTodayTaskCount(), is( 2 ) );
   }
   
   
   
   @Test
   public void testGetDueTomorrowTaskCount()
   {
      assertThat( createExtTaskCount().getDueTomorrowTaskCount(), is( 3 ) );
   }
   
   
   
   @Test
   public void testGetOverDueTaskCount()
   {
      assertThat( createExtTaskCount().getOverDueTaskCount(), is( 4 ) );
   }
   
   
   
   @Test
   public void testGetSumEstimated()
   {
      assertThat( createExtTaskCount().getSumEstimated(), is( 1000L ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createExtTaskCount().toString();
   }
   
   
   
   private ExtendedTaskCount createExtTaskCount()
   {
      return new ExtendedTaskCount( 0, 1, 2, 3, 4, 1000 );
   }
}
