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
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Reflection;


public class ListenerListFixture extends MolokoTestCase
{
   private final static int MASK_A = 0x1;
   
   private final static int MASK_B = 0x2;
   
   private final static int MASK_C = 0x3;
   
   private ListenerList< TestListener > listenerListMask;
   
   private ListenerList< TestListener > listenerListMaskAndValue;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      listenerListMask = new ListenerList< TestListener >( Reflection.findMethod( TestListener.class,
                                                                                  "onEvent" ) );
      listenerListMaskAndValue = new ListenerList< TestListener >( Reflection.findMethod( TestListener.class,
                                                                                          "onEventWithValue" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testListenerList()
   {
      new ListenerList< TestListener >( null );
   }
   
   
   
   @Test
   public void testRegisterListener()
   {
      listenerListMask.registerListener( MASK_A, new TestListener( MASK_A ) );
      assertThat( listenerListMask.size(), is( 1 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRegisterListenerNull()
   {
      listenerListMask.registerListener( MASK_A, null );
   }
   
   
   
   @Test
   public void testUnregisterListener()
   {
      final TestListener testListener = new TestListener( MASK_A );
      
      listenerListMask.registerListener( MASK_A, testListener );
      assertThat( listenerListMask.size(), is( 1 ) );
      
      listenerListMask.unregisterListener( testListener );
      assertThat( listenerListMask.size(), is( 0 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUnregisterListenerNull()
   {
      listenerListMask.unregisterListener( null );
   }
   
   
   
   @Test
   public void testClear()
   {
      final TestListener testListener = new TestListener( MASK_A );
      
      listenerListMask.registerListener( MASK_A, testListener );
      assertThat( listenerListMask.size(), is( 1 ) );
      
      listenerListMask.clear();
      assertThat( listenerListMask.size(), is( 0 ) );
      
      listenerListMask.clear();
      assertThat( listenerListMask.size(), is( 0 ) );
   }
   
   
   
   @Test
   public void testNotifyListenersInt()
   {
      final TestListener testListener = new TestListener( MASK_A );
      listenerListMask.registerListener( MASK_A, testListener );
      listenerListMask.notifyListeners( MASK_A );
      
      assertThat( testListener.EventCnt, is( 1 ) );
   }
   
   
   
   @Test
   public void testNotifyListenersIntSubMaskMatch()
   {
      final TestListener testListener = new TestListener( MASK_A );
      listenerListMask.registerListener( MASK_C, testListener );
      listenerListMask.notifyListeners( MASK_A );
      
      assertThat( testListener.EventCnt, is( 1 ) );
   }
   
   
   
   @Test
   public void testNotifyListenersIntObject()
   {
      final Object value = new Object();
      final TestListener testListener = new TestListener( MASK_A, value );
      listenerListMaskAndValue.registerListener( MASK_A, testListener );
      listenerListMaskAndValue.notifyListeners( MASK_A, value );
      
      assertThat( testListener.EventValueCnt, is( 1 ) );
   }
   
   
   
   @Test
   public void testNotifyListeners_DisjointMasks()
   {
      final TestListener testListener1 = new TestListener( MASK_A );
      final TestListener testListener2 = new TestListener( MASK_B );
      
      listenerListMask.registerListener( MASK_A, testListener1 );
      listenerListMask.registerListener( MASK_B, testListener2 );
      
      listenerListMask.notifyListeners( MASK_A );
      assertThat( testListener1.EventCnt, is( 1 ) );
      assertThat( testListener2.EventCnt, is( 0 ) );
      
      listenerListMask.notifyListeners( MASK_B );
      assertThat( testListener1.EventCnt, is( 1 ) );
      assertThat( testListener2.EventCnt, is( 1 ) );
      
      listenerListMask.notifyListeners( MASK_C );
      assertThat( testListener1.EventCnt, is( 2 ) );
      assertThat( testListener2.EventCnt, is( 2 ) );
   }
   
   
   
   @Test
   public void testNotifyListeners_OverlappingMasks()
   {
      final TestListener testListener1 = new TestListener( MASK_A );
      final TestListener testListener2 = new TestListener( MASK_C );
      
      listenerListMask.registerListener( MASK_A, testListener1 );
      listenerListMask.registerListener( MASK_C, testListener2 );
      
      listenerListMask.notifyListeners( MASK_A );
      assertThat( testListener1.EventCnt, is( 1 ) );
      assertThat( testListener2.EventCnt, is( 1 ) );
      
      listenerListMask.notifyListeners( MASK_B );
      assertThat( testListener1.EventCnt, is( 1 ) );
      assertThat( testListener2.EventCnt, is( 2 ) );
      
      listenerListMask.notifyListeners( MASK_C );
      assertThat( testListener1.EventCnt, is( 2 ) );
      assertThat( testListener2.EventCnt, is( 3 ) );
   }
   
   
   
   @Test
   public void testNotifyListenersWeakReference()
   {
      final TestListener testListener1 = new TestListener( MASK_A );
      TestListener testListener2 = new TestListener( MASK_A );
      
      listenerListMask.registerListener( MASK_A, testListener1 );
      listenerListMask.registerListener( MASK_A, testListener2 );
      
      assertThat( listenerListMask.size(), is( 2 ) );
      
      testListener2 = null;
      System.gc();
      
      assertThat( listenerListMask.size(), is( 2 ) );
      listenerListMask.notifyListeners( MASK_A );
      
      assertThat( testListener1.EventCnt, is( 1 ) );
      assertThat( listenerListMask.size(), is( 1 ) );
   }
   
   
   public final class TestListener
   {
      private final int mask;
      
      private final Object value;
      
      public int EventCnt;
      
      public int EventValueCnt;
      
      
      
      public TestListener( int mask )
      {
         this( mask, null );
      }
      
      
      
      public TestListener( int mask, Object value )
      {
         this.mask = mask;
         this.value = value;
      }
      
      
      
      public void onEvent( int mask )
      {
         ++EventCnt;
         assertThat( ( this.mask & mask ), not( 0 ) );
      }
      
      
      
      public void onEventWithValue( int mask, Object value )
      {
         ++EventValueCnt;
         assertThat( ( this.mask & mask ), not( 0 ) );
         assertEquals( this.value, value );
      }
   }
}
