/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.test.unit.rtm.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.service.RtmError;


public class RtmErrorFixture
{
   @Test
   public void testRtmError()
   {
      new RtmError( 10, "msg" );
   }
   
   
   
   @Test
   public void testGetCode()
   {
      assertThat( new RtmError( 10, "msg" ).getCode(), is( 10 ) );
   }
   
   
   
   @Test
   public void testGetMessage()
   {
      assertThat( new RtmError( 10, "msg" ).getMessage(), is( "msg" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmError( 10, "msg" ).toString();
   }
}
