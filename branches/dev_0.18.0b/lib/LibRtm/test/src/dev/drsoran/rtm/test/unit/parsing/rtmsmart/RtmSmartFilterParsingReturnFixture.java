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

package dev.drsoran.rtm.test.unit.parsing.rtmsmart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterParsingReturn;


public class RtmSmartFilterParsingReturnFixture
{
   @Test
   public void testRtmSmartFilterParsingReturn()
   {
      assertThat( new RtmSmartFilterParsingReturn( true ).hasCompletedOperator,
                  is( true ) );
      
      assertThat( new RtmSmartFilterParsingReturn( false ).hasCompletedOperator,
                  is( false ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmSmartFilterParsingReturn( true ).toString();
   }
}
