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

package dev.drsoran.test.unit.rtm.parsing.recurrence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import dev.drsoran.moloko.test.EqualsHashCodeTestCase;
import dev.drsoran.rtm.parsing.recurrence.OperandByDayValue;


public class OperandByDayValueFixture extends EqualsHashCodeTestCase
{
   @Test
   public void testOperandByDayValue()
   {
      new OperandByDayValue( null, 1 );
   }
   
   
   
   @Test
   public void testQualifier()
   {
      assertThat( new OperandByDayValue( null, 1 ).qualifier, nullValue() );
      assertThat( new OperandByDayValue( 1, 1 ).qualifier, is( 1 ) );
   }
   
   
   
   @Test
   public void testWeekday()
   {
      assertThat( new OperandByDayValue( null, 1 ).weekday, is( 1 ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new OperandByDayValue( null, 1 ).toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new OperandByDayValue( null, 1 );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new OperandByDayValue( 1, 2 );
   }
}
