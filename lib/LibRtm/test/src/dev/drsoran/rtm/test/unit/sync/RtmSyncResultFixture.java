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

package dev.drsoran.rtm.test.unit.sync;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.sync.RtmSyncResult;


public class RtmSyncResultFixture
{
   @Test
   public void testGetException()
   {
      RtmSyncResult syncResult = RtmSyncResult.newSucceeded();
      assertThat( syncResult.getException(), is( nullValue() ) );
      
      syncResult = RtmSyncResult.newFailed( new Exception() );
      assertThat( syncResult.getException(), is( notNullValue() ) );
   }
   
   
   
   @Test
   public void testGetTransactions()
   {
      RtmSyncResult syncResult = RtmSyncResult.newSucceeded();
      assertThat( syncResult.getTransactions().size(), is( 0 ) );
      
      syncResult = RtmSyncResult.newSucceeded( Arrays.asList( new RtmTransaction( "1",
                                                                                  true ) ) );
      assertThat( syncResult.getTransactions().size(), is( 1 ) );
      
      syncResult = RtmSyncResult.newFailed( new Exception() );
      assertThat( syncResult.getTransactions().size(), is( 0 ) );
      
      syncResult = RtmSyncResult.newFailed( new Exception(),
                                            Arrays.asList( new RtmTransaction( "1",
                                                                               true ) ) );
      assertThat( syncResult.getTransactions().size(), is( 1 ) );
   }
   
   
   
   @Test
   public void testHasFailed()
   {
      RtmSyncResult syncResult = RtmSyncResult.newSucceeded();
      assertThat( syncResult.hasFailed(), is( false ) );
      
      syncResult = RtmSyncResult.newFailed( new Exception() );
      assertThat( syncResult.hasFailed(), is( true ) );
   }
   
   
   
   @Test
   public void testHasSucceeded()
   {
      RtmSyncResult syncResult = RtmSyncResult.newSucceeded();
      assertThat( syncResult.hasSucceeded(), is( true ) );
      
      syncResult = RtmSyncResult.newFailed( new Exception() );
      assertThat( syncResult.hasSucceeded(), is( false ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewSucceededListOfRtmTransaction_NullTransactions()
   {
      RtmSyncResult.newSucceeded( null );
   }
   
   
   
   public void testNewSucceededListOfRtmTransaction()
   {
      final RtmSyncResult syncResult = RtmSyncResult.newSucceeded( Collections.< RtmTransaction > emptyList() );
      assertThat( syncResult, is( notNullValue() ) );
   }
   
   
   
   @Test
   public void testNewSucceeded()
   {
      final RtmSyncResult syncResult = RtmSyncResult.newSucceeded();
      assertThat( syncResult, is( notNullValue() ) );
   }
   
   
   
   @Test
   public void testNewFailedException()
   {
      final RtmSyncResult syncResult = RtmSyncResult.newFailed( new Exception() );
      assertThat( syncResult, is( notNullValue() ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewFailedException_NullException()
   {
      RtmSyncResult.newFailed( null );
   }
   
   
   
   @Test
   public void testNewFailedExceptionListOfRtmTransaction()
   {
      final RtmSyncResult syncResult = RtmSyncResult.newFailed( new Exception(),
                                                                Collections.< RtmTransaction > emptyList() );
      assertThat( syncResult, is( notNullValue() ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewFailedExceptionListOfRtmTransaction_NullTransactions()
   {
      RtmSyncResult.newFailed( new Exception(), null );
   }
}
