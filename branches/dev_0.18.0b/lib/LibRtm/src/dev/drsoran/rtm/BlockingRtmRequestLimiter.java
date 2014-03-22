/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm;

public class BlockingRtmRequestLimiter implements IRtmRequestLimiter
{
   private final static long INVOCATION_INTERVAL = 2000;
   
   private final long invocationIntervalMs;
   
   private long lastInvocationMillisUtc = -1L;
   
   
   
   public BlockingRtmRequestLimiter()
   {
      this( INVOCATION_INTERVAL );
   }
   
   
   
   public BlockingRtmRequestLimiter( long invocationIntervalMs )
   {
      if ( invocationIntervalMs < 0L )
      {
         throw new IllegalArgumentException( "invocationIntervalMs" );
      }
      
      this.invocationIntervalMs = invocationIntervalMs;
   }
   
   
   
   @Override
   public void obeyRtmRequestLimit()
   {
      final long nowMillisUtc = System.currentTimeMillis();
      
      if ( lastInvocationMillisUtc > -1L )
      {
         final long millisSinceLastInvocation = nowMillisUtc
            - lastInvocationMillisUtc;
         
         if ( millisSinceLastInvocation < invocationIntervalMs )
         {
            try
            {
               Thread.sleep( invocationIntervalMs - millisSinceLastInvocation );
               
            }
            catch ( InterruptedException e )
            {
            }
         }
      }
      
      lastInvocationMillisUtc = nowMillisUtc;
   }
}
