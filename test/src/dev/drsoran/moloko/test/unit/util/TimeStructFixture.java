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

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.util.TimeStruct;


public class TimeStructFixture extends MolokoTestCase
{
   
   @Test
   public void testTimeStruct()
   {
      new TimeStruct( 1, 2, 3, 4 );
      new TimeStruct( 0, 2, 3, 4 );
      new TimeStruct( 1, 0, 3, 4 );
      new TimeStruct( 1, 2, 0, 4 );
      new TimeStruct( 1, 2, 3, 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTimeStructNegDays()
   {
      new TimeStruct( -1, 2, 3, 4 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTimeStructNegHours()
   {
      new TimeStruct( 1, -2, 3, 4 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTimeStructNegMinutes()
   {
      new TimeStruct( 1, 2, -3, 4 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTimeStructNegSeconds()
   {
      new TimeStruct( 1, 2, 3, -4 );
   }
   
   
   
   @Test
   public void testTimeStructDays()
   {
      assertThat( new TimeStruct( 1, 2, 3, 4 ).days, is( 1 ) );
   }
   
   
   
   @Test
   public void testTimeStructHours()
   {
      assertThat( new TimeStruct( 1, 2, 3, 4 ).hours, is( 2 ) );
   }
   
   
   
   @Test
   public void testTimeStructMinutes()
   {
      assertThat( new TimeStruct( 1, 2, 3, 4 ).minutes, is( 3 ) );
   }
   
   
   
   @Test
   public void testTimeStructSeconds()
   {
      assertThat( new TimeStruct( 1, 2, 3, 4 ).seconds, is( 4 ) );
   }
}
