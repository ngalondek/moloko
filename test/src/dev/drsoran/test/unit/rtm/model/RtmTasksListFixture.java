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

package dev.drsoran.test.unit.rtm.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmTasksListFixture extends MolokoTestCase
{
   @Test
   public void testRtmTasksList()
   {
      createList();
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksList_NoId()
   {
      new RtmTasksList( RtmConstants.NO_ID,
                        1,
                        true,
                        true,
                        true,
                        "List",
                        "priority:1" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksList_NullName()
   {
      new RtmTasksList( "1", 1, true, true, true, null, "priority:1" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksList_EmptyName()
   {
      new RtmTasksList( "1", 1, true, true, true, "", "priority:1" );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createList().getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetName()
   {
      assertThat( createList().getName(), is( "List" ) );
   }
   
   
   
   @Test
   public void testGetPosition()
   {
      assertThat( createList().getPosition(), is( 1 ) );
   }
   
   
   
   @Test
   public void testIsDeleted()
   {
      assertThat( createList().isDeleted(), is( true ) );
   }
   
   
   
   @Test
   public void testIsLocked()
   {
      assertThat( createList().isLocked(), is( true ) );
   }
   
   
   
   @Test
   public void testIsArchived()
   {
      assertThat( createList().isArchived(), is( true ) );
   }
   
   
   
   @Test
   public void testIsSmartList()
   {
      assertThat( createList().isSmartList(), is( true ) );
      
      assertThat( new RtmTasksList( "1", 1, true, true, true, "List", null ).isSmartList(),
                  is( false ) );
   }
   
   
   
   @Test
   public void testGetSmartFilter()
   {
      assertThat( createList().getSmartFilter(), is( "priority:1" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createList().toString();
   }
   
   
   
   private RtmTasksList createList()
   {
      return new RtmTasksList( "1", 1, true, true, true, "List", "priority:1" );
   }
}
