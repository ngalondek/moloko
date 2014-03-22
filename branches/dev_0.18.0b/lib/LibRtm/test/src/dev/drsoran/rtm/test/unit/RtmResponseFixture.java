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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmTransaction;


public class RtmResponseFixture
{
   @Test
   public void testRtmResponseT()
   {
      new RtmResponse< Integer >( 1 );
   }
   
   
   
   @Test
   public void testRtmResponseRtmTransactionT()
   {
      new RtmResponse< Integer >( EasyMock.createMock( RtmTransaction.class ),
                                  1 );
   }
   
   
   
   @Test
   public void testGetTransaction()
   {
      RtmTransaction trans = EasyMock.createMock( RtmTransaction.class );
      
      assertThat( new RtmResponse< Integer >( 1 ).getTransaction(),
                  is( nullValue() ) );
      assertThat( new RtmResponse< Integer >( trans, 1 ).getTransaction(),
                  is( trans ) );
   }
   
   
   
   @Test
   public void testIsTransactional()
   {
      RtmTransaction trans = EasyMock.createMock( RtmTransaction.class );
      
      assertThat( new RtmResponse< Integer >( 1 ).isTransactional(), is( false ) );
      assertThat( new RtmResponse< Integer >( trans, 1 ).isTransactional(),
                  is( true ) );
   }
   
   
   
   @Test
   public void testIsUndoable()
   {
      RtmTransaction nonTrans = new RtmTransaction( "1", false );
      RtmTransaction trans = new RtmTransaction( "1", true );
      
      assertThat( new RtmResponse< Integer >( 1 ).isUndoable(), is( false ) );
      assertThat( new RtmResponse< Integer >( nonTrans, 1 ).isUndoable(),
                  is( false ) );
      assertThat( new RtmResponse< Integer >( trans, 1 ).isUndoable(),
                  is( true ) );
   }
   
   
   
   @Test
   public void testGetElement()
   {
      assertThat( new RtmResponse< Integer >( 1 ).getElement(), is( 1 ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmResponse< Integer >( 1 ).toString();
   }
   
}
