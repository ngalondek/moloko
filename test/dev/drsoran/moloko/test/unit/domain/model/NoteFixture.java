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
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.util.Strings;


public class NoteFixture extends ModelTestCase
{
   @Test
   public void testNote()
   {
      new Note( ID, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNoId()
   {
      new Note( NO_ID, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNoCreatedMillis()
   {
      new Note( ID, NEVER );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new Note( ID, NOW ).getId(), is( ID ) );
   }
   
   
   
   @Test
   public void testGetTitle()
   {
      assertThat( new Note( ID, NOW ).getTitle(), is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testSetTitle()
   {
      final Note note = new Note( ID, NOW );
      note.setTitle( "title" );
      assertThat( note.getTitle(), is( "title" ) );
      
      note.setTitle( "" );
      assertThat( note.getTitle(), is( "" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetTitleNull()
   {
      final Note note = new Note( ID, NOW );
      note.setTitle( null );
   }
   
   
   
   @Test
   public void testGetText()
   {
      assertThat( new Note( ID, NOW ).getText(), is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testSetText()
   {
      final Note note = new Note( ID, NOW );
      note.setText( "text" );
      assertThat( note.getText(), is( "text" ) );
      note.setText( "" );
      assertThat( note.getText(), is( "" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetTextNull()
   {
      final Note note = new Note( ID, NOW );
      note.setText( null );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      assertThat( new Note( ID, NOW ).getCreatedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      assertThat( new Note( ID, NOW ).getModifiedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetModifiedMillisUtc()
   {
      final Note note = new Note( ID, NOW );
      note.setModifiedMillisUtc( LATER );
      assertThat( note.getModifiedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testIsModified()
   {
      final Note note = new Note( ID, NOW );
      assertFalse( note.isModified() );
      
      note.setModifiedMillisUtc( LATER );
      assertTrue( note.isModified() );
   }
   
   
   
   @Test
   public void testGetDeletedMillisUtc()
   {
      assertThat( new Note( ID, NOW ).getDeletedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetDeletedMillisUtc()
   {
      final Note note = new Note( ID, NOW );
      note.setDeletedMillisUtc( LATER );
      assertThat( note.getDeletedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testIsDeleted()
   {
      final Note note = new Note( ID, NOW );
      assertFalse( note.isDeleted() );
      
      note.setDeletedMillisUtc( LATER );
      assertTrue( note.isDeleted() );
   }
   
   
   
   @Test
   public void testToString()
   {
      new Note( ID, NOW ).toString();
   }
}
