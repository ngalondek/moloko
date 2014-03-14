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

import static dev.drsoran.moloko.test.TestConstants.EVEN_LATER;
import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NEVER;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static dev.drsoran.moloko.test.TestConstants.NO_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import dev.drsoran.Strings;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.test.MolokoTestCase;


public class NoteFixture extends MolokoTestCase
{
   @Test
   public void testNote()
   {
      new Note( 1, NOW );
   }
   
   
   
   public void testNoteNoId()
   {
      new Note( NO_ID, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNoteNoCreatedMillis()
   {
      new Note( 1, NEVER );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new Note( 1, NOW ).getId(), is( 1L ) );
   }
   
   
   
   @Test
   public void testGetTitle()
   {
      assertThat( new Note( 1, NOW ).getTitle(), is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testSetTitle()
   {
      final Note note = new Note( 1, NOW );
      note.setTitle( "title" );
      assertThat( note.getTitle(), is( "title" ) );
      
      note.setTitle( "" );
      assertThat( note.getTitle(), is( "" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetTitleNull()
   {
      final Note note = new Note( 1, NOW );
      note.setTitle( null );
   }
   
   
   
   @Test
   public void testGetText()
   {
      assertThat( new Note( 1, NOW ).getText(), is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testSetText()
   {
      final Note note = new Note( 1, NOW );
      note.setText( "text" );
      assertThat( note.getText(), is( "text" ) );
      note.setText( "" );
      assertThat( note.getText(), is( "" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetTextNull()
   {
      final Note note = new Note( 1, NOW );
      note.setText( null );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      assertThat( new Note( 1, NOW ).getCreatedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      assertThat( new Note( 1, NOW ).getModifiedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetModifiedMillisUtc()
   {
      final Note note = new Note( 1, NOW );
      note.setModifiedMillisUtc( LATER );
      assertThat( note.getModifiedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testIsModified()
   {
      final Note note = new Note( 1, NOW );
      assertFalse( note.isModified() );
      
      note.setModifiedMillisUtc( LATER );
      assertTrue( note.isModified() );
   }
   
   
   
   @Test
   public void testGetDeletedMillisUtc()
   {
      assertThat( new Note( 1, NOW ).getDeletedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetDeletedMillisUtc()
   {
      final Note note = new Note( 1, NOW );
      note.setDeletedMillisUtc( LATER );
      assertThat( note.getDeletedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testIsDeleted()
   {
      final Note note = new Note( 1, NOW );
      assertFalse( note.isDeleted() );
      
      note.setDeletedMillisUtc( LATER );
      assertTrue( note.isDeleted() );
   }
   
   
   
   @Test
   public void testToString()
   {
      new Note( 1, NOW ).toString();
   }
   
   
   
   @Test
   public void testSerializeDeserialize() throws IOException,
                                         ClassNotFoundException
   {
      final ByteArrayOutputStream memStream = new ByteArrayOutputStream();
      final ObjectOutputStream oos = new ObjectOutputStream( memStream );
      
      final Note note = new Note( 1L, NOW );
      note.setModifiedMillisUtc( LATER );
      note.setDeletedMillisUtc( EVEN_LATER );
      note.setTitle( "title" );
      note.setText( "text" );
      
      oos.writeObject( note );
      oos.close();
      
      byte[] serialized = memStream.toByteArray();
      memStream.close();
      
      final ByteArrayInputStream memIStream = new ByteArrayInputStream( serialized );
      final ObjectInputStream ois = new ObjectInputStream( memIStream );
      
      final Note deserialized = (Note) ois.readObject();
      
      ois.close();
      memIStream.close();
      
      assertThat( deserialized, is( notNullValue() ) );
      assertThat( deserialized.getId(), is( 1L ) );
      assertThat( deserialized.getCreatedMillisUtc(), is( NOW ) );
      assertThat( deserialized.getModifiedMillisUtc(), is( LATER ) );
      assertThat( deserialized.getDeletedMillisUtc(), is( EVEN_LATER ) );
      assertThat( deserialized.getTitle(), is( "title" ) );
      assertThat( deserialized.getText(), is( "text" ) );
   }
}
