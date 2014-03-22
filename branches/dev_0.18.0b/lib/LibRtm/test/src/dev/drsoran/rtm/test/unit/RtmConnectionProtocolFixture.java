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

package dev.drsoran.rtm.test.unit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.RtmConnectionProtocol;


public class RtmConnectionProtocolFixture
{
   
   @Test
   public void testRtmConnectionProtocol()
   {
      @SuppressWarnings( "unused" )
      RtmConnectionProtocol http = RtmConnectionProtocol.http;
   }
   
   
   
   @Test
   public void testGetPort()
   {
      assertThat( RtmConnectionProtocol.http.getPort(), is( 80 ) );
      assertThat( RtmConnectionProtocol.https.getPort(), is( 443 ) );
   }
}
