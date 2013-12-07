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

import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NEVER;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.Strings;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.model.RtmNote;


public class RtmNoteFixture extends MolokoTestCase
{
   @Test
   public void testNote()
   {
      new RtmNote( "1", "10", NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNullId()
   {
      new RtmNote( null, "10", NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteEmptyId()
   {
      new RtmNote( "", "10", NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNullTaskSeriesId()
   {
      new RtmNote( "1", null, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteEmptyTaskSeriesId()
   {
      new RtmNote( "1", "", NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNoCreatedMillis()
   {
      new RtmNote( "1", "10", NEVER );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetTaskSeriesId()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getTaskSeriesId(), is( "10" ) );
   }
   
   
   
   @Test
   public void testGetTitle()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getTitle(),
                  is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testSetTitle()
   {
      final RtmNote note = new RtmNote( "1", "10", NOW );
      note.setTitle( "title" );
      assertThat( note.getTitle(), is( "title" ) );
      
      note.setTitle( "" );
      assertThat( note.getTitle(), is( "" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetTitleNull()
   {
      final RtmNote note = new RtmNote( "1", "10", NOW );
      note.setTitle( null );
   }
   
   
   
   @Test
   public void testGetText()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getText(),
                  is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testSetText()
   {
      final RtmNote note = new RtmNote( "1", "10", NOW );
      note.setText( "text" );
      assertThat( note.getText(), is( "text" ) );
      note.setText( "" );
      assertThat( note.getText(), is( "" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetTextNull()
   {
      final RtmNote note = new RtmNote( "1", "10", NOW );
      note.setText( null );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getCreatedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getModifiedMillisUtc(),
                  is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetModifiedMillisUtc()
   {
      final RtmNote note = new RtmNote( "1", "10", NOW );
      note.setModifiedMillisUtc( LATER );
      assertThat( note.getModifiedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testGetDeletedMillisUtc()
   {
      assertThat( new RtmNote( "1", "10", NOW ).getDeletedMillisUtc(),
                  is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetDeletedMillisUtc()
   {
      final RtmNote note = new RtmNote( "1", "10", NOW );
      note.setDeletedMillisUtc( LATER );
      assertThat( note.getDeletedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmNote( "1", "10", NOW ).toString();
   }
}
