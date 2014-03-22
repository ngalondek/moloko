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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dev.drsoran.rtm.BlockingRtmRequestLimiter;


public class BlockingRtmRequestLimiterFixture
{
   @Test
   public void testBlockingRtmRequestLimiter()
   {
      new BlockingRtmRequestLimiter();
   }
   
   
   
   @Test
   public void testBlockingRtmRequestLimiterLong()
   {
      new BlockingRtmRequestLimiter( 1000L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBlockingRtmRequestLimiterLong_Neg()
   {
      new BlockingRtmRequestLimiter( -1L );
   }
   
   
   
   @Test
   public void testObeyRtmRequestLimit_firstIncocation()
   {
      final long start = System.nanoTime();
      
      new BlockingRtmRequestLimiter( 100L ).obeyRtmRequestLimit();
      
      final long diff = ( System.nanoTime() - start ) / 1000000L;
      
      assertTrue( diff < 100L );
   }
   
   
   
   @Test
   public void testObeyRtmRequestLimit_severalIncocations()
   {
      final BlockingRtmRequestLimiter blockingRtmRequestLimiter = new BlockingRtmRequestLimiter( 100L );
      
      long start = System.nanoTime();
      blockingRtmRequestLimiter.obeyRtmRequestLimit();
      long diff = ( System.nanoTime() - start ) / 1000000L;
      
      assertTrue( diff < 10L );
      
      start = System.nanoTime();
      blockingRtmRequestLimiter.obeyRtmRequestLimit();
      diff = ( System.nanoTime() - start ) / 1000000L;
      
      assertTrue( diff > 80L );
      assertTrue( diff < 120L );
   }
   
   
   
   @Test
   public void testObeyRtmRequestLimit_longIncocations() throws InterruptedException
   {
      final BlockingRtmRequestLimiter blockingRtmRequestLimiter = new BlockingRtmRequestLimiter( 100L );
      
      long start = System.nanoTime();
      blockingRtmRequestLimiter.obeyRtmRequestLimit();
      long diff = ( System.nanoTime() - start ) / 1000000L;
      
      assertTrue( diff < 10L );
      
      Thread.sleep( 200 );
      
      start = System.nanoTime();
      blockingRtmRequestLimiter.obeyRtmRequestLimit();
      diff = ( System.nanoTime() - start ) / 1000000L;
      
      assertTrue( diff < 10L );
   }
}
