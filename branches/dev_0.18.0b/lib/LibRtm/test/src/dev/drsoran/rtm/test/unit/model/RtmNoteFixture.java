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

package dev.drsoran.rtm.test.unit.model;

import static dev.drsoran.rtm.test.TestConstants.LATER;
import static dev.drsoran.rtm.test.TestConstants.NEVER;
import static dev.drsoran.rtm.test.TestConstants.NOW;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.model.RtmNote;


public class RtmNoteFixture
{
   @Test
   public void testNote()
   {
      createNote();
   }
   
   
   
   @Test
   public void testNoteNullId()
   {
      new RtmNote( null, NOW, NEVER, "title", "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNoCreatedMillis()
   {
      new RtmNote( "1", NEVER, NEVER, "title", "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNullTitle()
   {
      new RtmNote( "1", NOW, NEVER, null, "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNullText()
   {
      new RtmNote( "1", NOW, NEVER, "title", null );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createNote().getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetTitle()
   {
      assertThat( createNote().getTitle(), is( "title" ) );
   }
   
   
   
   @Test
   public void testGetText()
   {
      assertThat( createNote().getText(), is( "text" ) );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      assertThat( createNote().getCreatedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      assertThat( createNote().getModifiedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createNote().toString();
   }
   
   
   
   private RtmNote createNote()
   {
      return new RtmNote( "1", NOW, LATER, "title", "text" );
   }
}
