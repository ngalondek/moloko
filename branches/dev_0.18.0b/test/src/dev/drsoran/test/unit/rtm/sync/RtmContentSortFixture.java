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

package dev.drsoran.test.unit.rtm.sync;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

import dev.drsoran.moloko.test.PrivateCtorCaller;
import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.sync.RtmContentSort;


public class RtmContentSortFixture
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( RtmContentSort.class );
   }
   
   
   
   @Test
   public void testGetRtmTaskIdSort()
   {
      assertThat( RtmContentSort.getRtmTaskIdSort().compare( newTask( "1" ),
                                                             newTask( "2" ) ),
                  is( "1".compareTo( "2" ) ) );
      assertThat( RtmContentSort.getRtmTaskIdSort().compare( newTask( "2" ),
                                                             newTask( "1" ) ),
                  is( "2".compareTo( "1" ) ) );
      assertThat( RtmContentSort.getRtmTaskIdSort().compare( newTask( null ),
                                                             newTask( "1" ) ),
                  is( -1 ) );
      assertThat( RtmContentSort.getRtmTaskIdSort().compare( newTask( "1" ),
                                                             newTask( null ) ),
                  is( 1 ) );
      assertThat( RtmContentSort.getRtmTaskIdSort().compare( newTask( null ),
                                                             newTask( null ) ),
                  is( 0 ) );
   }
   
   
   
   @Test
   public void testGetRtmTasksListIdSort()
   {
      assertThat( RtmContentSort.getRtmTasksListIdSort()
                                .compare( newList( "1" ), newList( "2" ) ),
                  is( "1".compareTo( "2" ) ) );
      assertThat( RtmContentSort.getRtmTasksListIdSort()
                                .compare( newList( "2" ), newList( "1" ) ),
                  is( "2".compareTo( "1" ) ) );
      assertThat( RtmContentSort.getRtmTasksListIdSort()
                                .compare( newList( null ), newList( "1" ) ),
                  is( -1 ) );
      assertThat( RtmContentSort.getRtmTasksListIdSort()
                                .compare( newList( "1" ), newList( null ) ),
                  is( 1 ) );
      assertThat( RtmContentSort.getRtmTasksListIdSort()
                                .compare( newList( null ), newList( null ) ),
                  is( 0 ) );
   }
   
   
   
   @Test
   public void testGetRtmLocationIdSort()
   {
      assertThat( RtmContentSort.getRtmLocationIdSort()
                                .compare( newLocation( "1" ), newLocation( "2" ) ),
                  is( "1".compareTo( "2" ) ) );
      assertThat( RtmContentSort.getRtmLocationIdSort()
                                .compare( newLocation( "2" ), newLocation( "1" ) ),
                  is( "2".compareTo( "1" ) ) );
   }
   
   
   
   @Test
   public void testGetRtmContactIdSort()
   {
      assertThat( RtmContentSort.getRtmContactIdSort()
                                .compare( newContact( "1" ), newContact( "2" ) ),
                  is( "1".compareTo( "2" ) ) );
      assertThat( RtmContentSort.getRtmContactIdSort()
                                .compare( newContact( "2" ), newContact( "1" ) ),
                  is( "2".compareTo( "1" ) ) );
   }
   
   
   
   private static RtmTask newTask( String id )
   {
      return new RtmTask( id,
                          "100",
                          TestConstants.NOW,
                          TestConstants.NOW,
                          TestConstants.NOW,
                          TestConstants.NEVER,
                          "1000",
                          null,
                          "Name",
                          null,
                          null,
                          TestConstants.NEVER,
                          Priority.High,
                          0,
                          TestConstants.NEVER,
                          false,
                          null,
                          false,
                          null,
                          Collections.< String > emptyList(),
                          Collections.< RtmNote > emptyList(),
                          Collections.< RtmContact > emptyList() );
   }
   
   
   
   private static RtmTasksList newList( String id )
   {
      return new RtmTasksList( id, 0, false, false, false, "Name", null );
   }
   
   
   
   private static RtmLocation newLocation( String id )
   {
      return new RtmLocation( id, "name", 1.0f, 2.0f, "a", true, 10 );
   }
   
   
   
   private static RtmContact newContact( String id )
   {
      return new RtmContact( id, "user", "full" );
   }
}
