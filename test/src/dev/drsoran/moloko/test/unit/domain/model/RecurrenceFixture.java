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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.test.EqualsHashCodeTestCase;
import dev.drsoran.rtm.parsing.recurrence.RtmRecurrence;


public class RecurrenceFixture extends EqualsHashCodeTestCase
{
   
   @Test
   public void testRecurrence()
   {
      new Recurrence( "pattern", true );
   }
   
   
   
   @Test
   public void testRecurrenceRtmRecurrence()
   {
      Recurrence recurrence = new Recurrence( new RtmRecurrence( "pattern",
                                                                 true ) );
      assertThat( recurrence.getPattern(), is( "pattern" ) );
      assertThat( recurrence.isEveryRecurrence(), is( true ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRecurrenceNullRtmRecurrence()
   {
      new Recurrence( null );
   }
   
   
   
   @Test
   public void testGetPattern()
   {
      assertThat( new Recurrence( "pattern", true ).getPattern(),
                  is( "pattern" ) );
   }
   
   
   
   @Test
   public void testIsEveryRecurrence()
   {
      assertTrue( new Recurrence( "pattern", true ).isEveryRecurrence() );
   }
   
   
   
   @Test
   public void testToString()
   {
      new Recurrence( "pattern", true ).toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new Recurrence( "pattern", true );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new Recurrence( "pattern1", false );
   }
}
