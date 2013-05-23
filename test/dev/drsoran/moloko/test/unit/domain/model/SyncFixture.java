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

package dev.drsoran.moloko.test.unit.domain.model;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Sync;


@RunWith( Theories.class )
public class SyncFixture
{
   @DataPoint
   public final static long NO_TIME = Constants.NO_TIME;
   
   @DataPoint
   public final static long NOW = System.currentTimeMillis();
   
   @DataPoint
   public final static long LATER = NOW + 3600 * 1000;
   
   
   
   @Theory
   public void testSync( long syncIn, long syncOut )
   {
      new Sync( syncIn, syncOut );
   }
   
   
   
   @Theory
   public void testGetLastSyncInMillis( long syncIn, long syncOut )
   {
      assertThat( new Sync( syncIn, syncOut ).getLastSyncInMillis(),
                  is( syncIn ) );
   }
   
   
   
   @Theory
   public void testHasEverSyncedInFalse( long syncIn, long syncOut )
   {
      assumeThat( syncIn, is( Constants.NO_TIME ) );
      assertThat( new Sync( syncIn, syncOut ).hasEverSyncedIn(), is( false ) );
   }
   
   
   
   @Theory
   public void testHasEverSyncedInTrue( long syncIn, long syncOut )
   {
      assumeThat( syncIn, not( Constants.NO_TIME ) );
      assertThat( new Sync( syncIn, syncOut ).hasEverSyncedIn(), is( true ) );
   }
   
   
   
   @Theory
   public void testGetLastSyncOutMillis( long syncIn, long syncOut )
   {
      assertThat( new Sync( syncIn, syncOut ).getLastSyncOutMillis(),
                  is( syncOut ) );
   }
   
   
   
   @Theory
   public void testHasEverSyncedOutFalse( long syncIn, long syncOut )
   {
      assumeThat( syncOut, is( Constants.NO_TIME ) );
      assertThat( new Sync( syncIn, syncOut ).hasEverSyncedOut(), is( false ) );
   }
   
   
   
   @Theory
   public void testHasEverSyncedOutTrue( long syncIn, long syncOut )
   {
      assumeThat( syncOut, not( Constants.NO_TIME ) );
      assertThat( new Sync( syncIn, syncOut ).hasEverSyncedOut(), is(
      
      true ) );
   }
   
   
   
   @Theory
   public void testToString( long syncIn, long syncOut )
   {
      new Sync( syncIn, syncOut ).toString();
   }
}
