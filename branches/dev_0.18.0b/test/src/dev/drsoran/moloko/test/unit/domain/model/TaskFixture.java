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

import static dev.drsoran.moloko.test.IterableAsserts.assertEmpty;
import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NEVER;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static dev.drsoran.moloko.test.TestConstants.NO_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.model.Priority;


public class TaskFixture extends MolokoTestCase
{
   @Test
   public void testTask()
   {
      createTask();
   }
   
   
   
   @Test
   public void testTaskNoId()
   {
      new Task( NO_ID, NOW, NOW, "task", 1L, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskNoCreatedMillis()
   {
      new Task( 1, NEVER, NOW, "task", 1L, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskNoAddedMillis()
   {
      new Task( 1, NOW, NEVER, "task", 1L, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskNullTaskName()
   {
      new Task( 1, NOW, NOW, null, 1L, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskEmptyTaskName()
   {
      new Task( 1, NOW, NOW, "", 1L, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskNoListId()
   {
      new Task( 1, NOW, NOW, "task", NO_ID, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskNullListName()
   {
      new Task( 1, NOW, NOW, "task", 1L, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTaskEmptyListName()
   {
      new Task( 1, NOW, NOW, "task", 1L, "" );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createTask().getId(), is( 1L ) );
   }
   
   
   
   @Test
   public void testGetName()
   {
      assertThat( createTask().getName(), is( "task" ) );
   }
   
   
   
   @Test
   public void testSetName()
   {
      final Task task = createTask();
      task.setName( "task" );
      
      assertThat( task.getName(), is( "task" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetNameNull()
   {
      createTask().setName( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetNameEmpty()
   {
      createTask().setName( "" );
   }
   
   
   
   @Test
   public void testGetSource()
   {
      assertThat( createTask().getSource(), is( "" ) );
   }
   
   
   
   @Test
   public void testSetSource()
   {
      final Task task = createTask();
      
      task.setSource( "" );
      assertThat( task.getSource(), is( "" ) );
      
      task.setSource( "src" );
      assertThat( task.getSource(), is( "src" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetSourceNull()
   {
      final Task task = createTask();
      task.setSource( null );
   }
   
   
   
   @Test
   public void testGetUrl()
   {
      assertThat( createTask().getUrl(), is( "" ) );
   }
   
   
   
   @Test
   public void testSetUrl()
   {
      final Task task = createTask();
      task.setUrl( "" );
      assertThat( task.getUrl(), is( "" ) );
      
      task.setUrl( "url" );
      assertThat( task.getUrl(), is( "url" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetUrlNull()
   {
      final Task task = createTask();
      task.setUrl( null );
   }
   
   
   
   @Test
   public void testGetAddedMillisUtc()
   {
      assertThat( new Task( 1, NOW, LATER, "task", 1L, "list" ).getAddedMillisUtc(),
                  is( LATER ) );
   }
   
   
   
   @Test
   public void testGetCompletedMillisUtc()
   {
      assertThat( createTask().getCompletedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetCompletedMillisUtc()
   {
      final Task task = createTask();
      task.setCompletedMillisUtc( NOW );
      assertThat( task.getCompletedMillisUtc(), is( NOW ) );
      
      task.setCompletedMillisUtc( NEVER );
      assertThat( task.getCompletedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testIsComplete()
   {
      final Task task = createTask();
      assertThat( task.isComplete(), is( false ) );
      
      task.setCompletedMillisUtc( NOW );
      assertThat( task.isComplete(), is( true ) );
      
      task.setCompletedMillisUtc( NEVER );
      assertThat( task.isComplete(), is( false ) );
   }
   
   
   
   @Test
   public void testGetPriority()
   {
      assertThat( createTask().getPriority(), is( Priority.None ) );
   }
   
   
   
   @Test
   public void testSetPriority()
   {
      final Task task = createTask();
      task.setPriority( Priority.High );
      assertThat( task.getPriority(), is( Priority.High ) );
   }
   
   
   
   @Test
   public void testGetPostponedCount()
   {
      assertThat( createTask().getPostponedCount(), is( 0 ) );
   }
   
   
   
   @Test
   public void testSetPostponedCountIncreasing()
   {
      final Task task = createTask();
      task.setPostponedCount( 0 );
      assertThat( task.getPostponedCount(), is( 0 ) );
      
      task.setPostponedCount( 1 );
      assertThat( task.getPostponedCount(), is( 1 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetPostponedCountDecreasing()
   {
      final Task task = createTask();
      task.setPostponedCount( 1 );
      task.setPostponedCount( 0 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetPostponedCountInvalid()
   {
      final Task task = createTask();
      task.setPostponedCount( -1 );
   }
   
   
   
   @Test
   public void testIsPostponed()
   {
      Task task = createTask();
      assertThat( task.isPostponed(), is( false ) );
      
      task.setPostponedCount( 0 );
      assertThat( task.isPostponed(), is( false ) );
      
      task.setPostponedCount( 1 );
      assertThat( task.isPostponed(), is( true ) );
      
      task = createTask();
      task.setPostponedCount( 1 );
      assertThat( task.isPostponed(), is( true ) );
   }
   
   
   
   @Test
   public void testGetDue()
   {
      assertThat( createTask().getDue(), nullValue() );
   }
   
   
   
   @Test
   public void testSetDue()
   {
      final Task task = createTask();
      task.setDue( new Due( NOW, true ) );
      
      final Due due = task.getDue();
      assertThat( due.getMillisUtc(), is( NOW ) );
      assertThat( due.hasDueTime(), is( true ) );
      
      task.setDue( null );
      assertNull( task.getDue() );
   }
   
   
   
   @Test
   public void testGetRecurence()
   {
      assertThat( createTask().getRecurrence(), nullValue() );
   }
   
   
   
   @Test
   public void testSetRecurrence()
   {
      final Task task = createTask();
      task.setRecurrence( new Recurrence( "pattern", true ) );
      
      final Recurrence recurrence = task.getRecurrence();
      assertThat( recurrence.getPattern(), is( "pattern" ) );
      assertThat( recurrence.isEveryRecurrence(), is( true ) );
      
      task.setRecurrence( null );
      assertNull( task.getRecurrence() );
   }
   
   
   
   @Test
   public void testGetEstimation()
   {
      assertThat( createTask().getEstimation(), nullValue() );
   }
   
   
   
   @Test
   public void testSetEstimation()
   {
      final Task task = createTask();
      task.setEstimation( new Estimation( "sentence", NOW ) );
      
      final Estimation estimation = task.getEstimation();
      assertThat( estimation.getSentence(), is( "sentence" ) );
      assertThat( estimation.getMillis(), is( NOW ) );
      
      task.setEstimation( null );
      assertNull( task.getEstimation() );
   }
   
   
   
   @Test
   public void testGetTags()
   {
      assertEmpty( createTask().getTags() );
   }
   
   
   
   @Test
   public void testSetTags()
   {
      final Task task = createTask();
      task.setTags( null );
      assertNotNull( task.getTags() );
      assertEmpty( task.getTags() );
      
      task.setTags( Arrays.asList( new String[]
      { "tag1", "tag2" } ) );
      assertEqualSet( task.getTags(), "tag1", "tag2" );
   }
   
   
   
   @Test
   public void testGetNotesEmpty()
   {
      final Task task = createTask();
      assertNotNull( task.getNotes() );
      assertEmpty( task.getNotes() );
   }
   
   
   
   @Test
   public void testSetNotes()
   {
      final Task task = createTask();
      task.setNotes( null );
      assertNotNull( task.getNotes() );
      assertEmpty( task.getNotes() );
      
      final Note note1 = new Note( 1, NOW );
      final Note note2 = new Note( 2, NOW );
      
      task.setNotes( Arrays.asList( new Note[]
      { note1, note2 } ) );
      
      assertEqualSet( task.getNotes(), note1, note2 );
      
      final Note note3 = new Note( 3, NOW );
      final Note note4 = new Note( 4, NOW );
      
      task.setNotes( Arrays.asList( new Note[]
      { note3, note4 } ) );
      
      assertEqualSet( task.getNotes(), note3, note4 );
      
      task.setNotes( null );
      assertNotNull( task.getNotes() );
      assertEmpty( task.getNotes() );
   }
   
   
   
   @Test
   public void testAddNote()
   {
      final Task task = createTask();
      final Note note1 = new Note( 1, NOW );
      task.addNote( note1 );
      assertEqualSet( task.getNotes(), note1 );
      
      final Note note2 = new Note( 2, NOW );
      task.addNote( note2 );
      assertEqualSet( task.getNotes(), note1, note2 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddNoteNull()
   {
      final Task task = createTask();
      task.addNote( null );
   }
   
   
   
   @Test
   public void testGetNote()
   {
      final Task task = createTask();
      task.setNotes( null );
      assertNull( task.getNote( 1 ) );
      
      final Note note1 = new Note( 1, NOW );
      final Note note2 = new Note( 2, NOW );
      task.setNotes( Arrays.asList( new Note[]
      { note1, note2 } ) );
      
      assertEquals( task.getNote( 1 ), note1 );
      assertEquals( task.getNote( 2 ), note2 );
      assertNull( task.getNote( NO_ID ) );
   }
   
   
   
   @Test
   public void testHasNote()
   {
      final Task task = createTask();
      task.setNotes( null );
      assertFalse( task.hasNote( 1 ) );
      
      final Note note1 = new Note( 1, NOW );
      final Note note2 = new Note( 2, NOW );
      task.setNotes( Arrays.asList( new Note[]
      { note1, note2 } ) );
      
      assertTrue( task.hasNote( 1 ) );
      assertTrue( task.hasNote( 2 ) );
      assertFalse( task.hasNote( NO_ID ) );
   }
   
   
   
   @Test
   public void testRemoveNote()
   {
      final Task task = createTask();
      task.setNotes( null );
      
      final Note note1 = new Note( 1, NOW );
      task.removeNote( note1 );
      
      final Note note2 = new Note( 2, NOW );
      
      task.setNotes( Arrays.asList( new Note[]
      { note1, note2 } ) );
      
      task.removeNote( note1 );
      assertEqualSet( task.getNotes(), note2 );
      
      task.removeNote( note1 );
      assertEqualSet( task.getNotes(), note2 );
      
      task.removeNote( note2 );
      assertEmpty( task.getNotes() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRemoveNoteNull()
   {
      final Task task = createTask();
      task.removeNote( null );
   }
   
   
   
   @Test
   public void testGetParticipantsEmpty()
   {
      final Task task = createTask();
      assertNotNull( task.getParticipants() );
      assertEmpty( task.getParticipants() );
   }
   
   
   
   @Test
   public void testAddParticipant()
   {
      final Task task = createTask();
      final Participant participant1 = new Participant( 1, "full", "user" );
      task.addParticipant( participant1 );
      assertEqualSet( task.getParticipants(), participant1 );
      
      final Participant participant2 = new Participant( 2, "full1", "user1" );
      task.addParticipant( participant2 );
      assertEqualSet( task.getParticipants(), participant1, participant2 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddParticipantNull()
   {
      final Task task = createTask();
      task.addParticipant( null );
   }
   
   
   
   @Test
   public void testGetParticipant()
   {
      final Task task = createTask();
      assertNull( task.getParticipant( NO_ID ) );
      assertNull( task.getParticipant( 1 ) );
      
      final Participant participant1 = new Participant( 1, "full", "user" );
      final Participant participant2 = new Participant( 2, "full1", "user1" );
      
      task.addParticipant( participant1 );
      task.addParticipant( participant2 );
      
      assertEquals( task.getParticipant( 1 ), participant1 );
      assertEquals( task.getParticipant( 2 ), participant2 );
      assertNull( task.getParticipant( NO_ID ) );
   }
   
   
   
   @Test
   public void testIsParticipating()
   {
      final Task task = createTask();
      assertFalse( task.isParticipating( NO_ID ) );
      assertFalse( task.isParticipating( 1 ) );
      
      final Participant participant1 = new Participant( 1, "full", "user" );
      task.addParticipant( participant1 );
      assertTrue( task.isParticipating( 1 ) );
   }
   
   
   
   @Test
   public void testGetLocationId()
   {
      final Task task = createTask();
      assertThat( task.getLocationId(), is( NO_ID ) );
      
      task.setLocationStub( 1L, "loc" );
      assertThat( task.getLocationId(), is( 1L ) );
   }
   
   
   
   @Test
   public void testGetLocationName()
   {
      final Task task = createTask();
      assertNull( task.getLocationName() );
      
      task.setLocationStub( 1L, "loc" );
      assertThat( task.getLocationName(), is( "loc" ) );
   }
   
   
   
   @Test
   public void testIsLocated()
   {
      final Task task = createTask();
      assertFalse( task.isLocated() );
      
      task.setLocationStub( 1L, "loc" );
      assertTrue( task.isLocated() );
      
      task.setLocationStub( NO_ID, null );
      assertFalse( task.isLocated() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetLocationNoIdButName()
   {
      final Task task = createTask();
      task.setLocationStub( NO_ID, "loc" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetLocationIdButNullName()
   {
      final Task task = createTask();
      task.setLocationStub( 1L, null );
   }
   
   
   
   @Test
   public void testSetLocationIdButEmptyName()
   {
      final Task task = createTask();
      task.setLocationStub( 1L, "" );
   }
   
   
   
   @Test
   public void testGetListId()
   {
      assertThat( createTask().getListId(), is( 1L ) );
   }
   
   
   
   @Test
   public void testGetListName()
   {
      assertThat( createTask().getListName(), is( "list" ) );
   }
   
   
   
   @Test
   public void testSetList()
   {
      final Task task = createTask();
      task.setList( 10L, "otherList" );
      
      assertThat( task.getListId(), is( 10L ) );
      assertThat( task.getListName(), is( "otherList" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetListNoId()
   {
      final Task task = createTask();
      task.setList( NO_ID, "list" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetListIdButNullName()
   {
      final Task task = createTask();
      task.setList( 1L, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetListIdButEmptyName()
   {
      final Task task = createTask();
      task.setList( 1L, "" );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      final Task task = createTask();
      assertThat( task.getCreatedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      final Task task = createTask();
      assertThat( task.getModifiedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetModifiedMillisUtc()
   {
      final Task task = createTask();
      task.setModifiedMillisUtc( NOW );
      assertThat( task.getModifiedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testIsModified()
   {
      final Task task = createTask();
      assertFalse( task.isModified() );
      
      task.setModifiedMillisUtc( NOW );
      assertThat( task.getModifiedMillisUtc(), is( NOW ) );
      assertTrue( task.isModified() );
   }
   
   
   
   @Test
   public void testGetDeletedMillisUtc()
   {
      final Task task = createTask();
      assertThat( task.getDeletedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetDeletedMillisUtc()
   {
      final Task task = createTask();
      task.setDeletedMillisUtc( NOW );
      assertThat( task.getDeletedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testIsDeleted()
   {
      final Task task = createTask();
      assertFalse( task.isDeleted() );
      
      task.setDeletedMillisUtc( NOW );
      assertTrue( task.isDeleted() );
   }
   
   
   
   private Task createTask()
   {
      final Task task = new Task( 1, NOW, LATER, "task", 1L, "list" );
      return task;
   }
   
   
   
   @Test
   public void testToString()
   {
      createTask().toString();
      
      final Task task = createTask();
      final Note note1 = new Note( 1, NOW );
      final Note note2 = new Note( 2, NOW );
      task.setNotes( Arrays.asList( new Note[]
      { note1, note2 } ) );
      task.toString();
   }
}
