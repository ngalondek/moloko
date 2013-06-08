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

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;
import dev.drsoran.moloko.util.DelayedRun;


public class DelayedRunFixture extends MolokoTestCase
{
   private final static Runnable R = new Runnable()
   {
      @Override
      public void run()
      {
      }
   };
   
   
   
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( DelayedRun.class );
   }
   
   
   
   @Test
   public void testRun()
   {
      IHandlerToken handlerTokenMock = EasyMock.createStrictMock( IHandlerToken.class );
      handlerTokenMock.removeRunnable( R );
      EasyMock.expect( handlerTokenMock.postDelayed( R, 1000 ) )
              .andReturn( true );
      EasyMock.replay( handlerTokenMock );
      
      final Runnable r1 = DelayedRun.run( handlerTokenMock, R, 1000 );
      assertNotNull( r1 );
      
      EasyMock.verify( handlerTokenMock );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRunNullHandler()
   {
      DelayedRun.run( null, R, 1000 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRunNullRunnable()
   {
      DelayedRun.run( EasyMock.createNiceMock( IHandlerToken.class ),
                      null,
                      1000 );
   }
}
