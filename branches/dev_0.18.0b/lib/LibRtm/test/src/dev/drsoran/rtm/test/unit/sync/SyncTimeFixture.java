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
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.sync.SyncTime;
import dev.drsoran.rtm.test.TestConstants;


public class SyncTimeFixture
{
   @Test
   public void testSyncTime()
   {
      new SyncTime( TestConstants.NOW, TestConstants.LATER );
   }
   
   
   
   @Test
   public void testGetLastSyncInMillis()
   {
      assertThat( new SyncTime( TestConstants.NOW, TestConstants.LATER ).getLastSyncInMillis(),
                  is( TestConstants.NOW ) );
   }
   
   
   
   @Test
   public void testHasEverSyncedIn()
   {
      assertThat( new SyncTime( TestConstants.NOW, TestConstants.LATER ).hasEverSyncedIn(),
                  is( true ) );
      assertThat( new SyncTime( TestConstants.NEVER, TestConstants.LATER ).hasEverSyncedIn(),
                  is( false ) );
   }
   
   
   
   @Test
   public void testGetLastSyncOutMillis()
   {
      assertThat( new SyncTime( TestConstants.NOW, TestConstants.LATER ).getLastSyncOutMillis(),
                  is( TestConstants.LATER ) );
   }
   
   
   
   @Test
   public void testHasEverSyncedOut()
   {
      assertThat( new SyncTime( TestConstants.NOW, TestConstants.LATER ).hasEverSyncedOut(),
                  is( true ) );
      assertThat( new SyncTime( TestConstants.NOW, TestConstants.NEVER ).hasEverSyncedOut(),
                  is( false ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new SyncTime( TestConstants.NOW, TestConstants.LATER ).toString();
      new SyncTime( TestConstants.NOW, TestConstants.NEVER ).toString();
      new SyncTime( TestConstants.NEVER, TestConstants.NEVER ).toString();
      new SyncTime( TestConstants.NEVER, TestConstants.LATER ).toString();
   }
}
