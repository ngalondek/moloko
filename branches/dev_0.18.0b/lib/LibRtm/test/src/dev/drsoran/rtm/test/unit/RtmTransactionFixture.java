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

import dev.drsoran.rtm.RtmTransaction;


public class RtmTransactionFixture
{
   @Test
   public void testRtmTransaction()
   {
      new RtmTransaction( "1", true );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTransaction_NUllId()
   {
      new RtmTransaction( null, true );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTransaction_EmptyId()
   {
      new RtmTransaction( "", true );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new RtmTransaction( "1", true ).getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testIsUndoable()
   {
      assertThat( new RtmTransaction( "1", true ).isUndoable(), is( true ) );
      assertThat( new RtmTransaction( "1", false ).isUndoable(), is( false ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmTransaction( "1", true ).toString();
   }
}
