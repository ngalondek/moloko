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

import static dev.drsoran.rtm.test.IterableAsserts.assertEmpty;
import static dev.drsoran.rtm.test.IterableAsserts.assertNotEmpty;
import static dev.drsoran.rtm.test.TestConstants.EVEN_LATER;
import static dev.drsoran.rtm.test.TestConstants.LATER;
import static dev.drsoran.rtm.test.TestConstants.NEVER;
import static dev.drsoran.rtm.test.TestConstants.NOW;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;


public class RtmTaskFixture
{
   @Test
   public void testRtmTask()
   {
      createTask();
   }
   
   
   
   @Test
   public void testRtmTask_NoId()
   {
      new RtmTask( RtmConstants.NO_ID,
                   "10",
                   NOW,
                   NOW,
                   NOW,
                   NEVER,
                   "100",
                   RtmConstants.NO_ID,
                   "Task",
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test
   public void testRtmTask_NoTaskSeriesId()
   {
      new RtmTask( "1",
                   RtmConstants.NO_ID,
                   NOW,
                   NOW,
                   NOW,
                   NEVER,
                   "100",
                   RtmConstants.NO_ID,
                   "Task",
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTask_NoCreatedMillis()
   {
      new RtmTask( "1",
                   "10",
                   NEVER,
                   NOW,
                   NOW,
                   NEVER,
                   "100",
                   RtmConstants.NO_ID,
                   "Task",
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTask_NoAddedMillis()
   {
      new RtmTask( "1",
                   "10",
                   NOW,
                   NEVER,
                   NOW,
                   NEVER,
                   "100",
                   RtmConstants.NO_ID,
                   "Task",
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTask_NoListId()
   {
      new RtmTask( "1",
                   "10",
                   NOW,
                   NOW,
                   NOW,
                   NEVER,
                   RtmConstants.NO_ID,
                   RtmConstants.NO_ID,
                   "Task",
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTask_NullName()
   {
      new RtmTask( "1",
                   "10",
                   NOW,
                   NOW,
                   NOW,
                   NEVER,
                   "100",
                   RtmConstants.NO_ID,
                   null,
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTask_NegativePostponed()
   {
      new RtmTask( "1",
                   "10",
                   NOW,
                   NOW,
                   NOW,
                   NEVER,
                   "100",
                   RtmConstants.NO_ID,
                   "Task",
                   "TestCase",
                   null,
                   NEVER,
                   Priority.None,
                   -1,
                   NEVER,
                   false,
                   null,
                   false,
                   null,
                   null,
                   null,
                   null );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createTask().getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetTaskSeriesId()
   {
      assertThat( createTask().getTaskSeriesId(), is( "10" ) );
   }
   
   
   
   @Test
   public void testGetName()
   {
      assertThat( createTask().getName(), is( "Task" ) );
   }
   
   
   
   @Test
   public void testGetSource()
   {
      assertThat( createTask().getSource(), is( "TestCase" ) );
   }
   
   
   
   @Test
   public void testGetUrl()
   {
      assertThat( createTask().getUrl(), is( "http://www.moloko.com" ) );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      assertThat( createTask().getCreatedMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testGetAddedMillisUtc()
   {
      assertThat( createTask().getAddedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      assertThat( createTask().getModifiedMillisUtc(), is( EVEN_LATER ) );
   }
   
   
   
   @Test
   public void testGetDeletedMillisUtc()
   {
      assertThat( createTask().getDeletedMillisUtc(), is( NEVER ) );
   }
   
   
   
   @Test
   public void testGetCompletedMillisUtc()
   {
      assertThat( createTask().getCompletedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testGetPriority()
   {
      assertThat( createTask().getPriority(), is( Priority.Medium ) );
   }
   
   
   
   @Test
   public void testGetPostponedCount()
   {
      assertThat( createTask().getPostponedCount(), is( 1 ) );
   }
   
   
   
   @Test
   public void testGetDueMillisUtc()
   {
      assertThat( createTask().getDueMillisUtc(), is( NOW ) );
   }
   
   
   
   @Test
   public void testHasDueTime()
   {
      assertThat( createTask().hasDueTime(), is( true ) );
   }
   
   
   
   @Test
   public void testGetRecurrencePattern()
   {
      assertThat( createTask().getRecurrencePattern(), is( "FREQ=DAILY" ) );
   }
   
   
   
   @Test
   public void testIsEveryRecurrence()
   {
      assertThat( createTask().isEveryRecurrence(), is( true ) );
   }
   
   
   
   @Test
   public void testGetEstimationSentence()
   {
      assertThat( createTask().getEstimationSentence(), is( "1 day" ) );
   }
   
   
   
   @Test
   public void testGetTags()
   {
      assertThat( createTask().getTags(), hasItems( "tag1", "tag2" ) );
   }
   
   
   
   @Test
   public void testGetTags_Empty()
   {
      assertEmpty( createMinimalTask().getTags() );
   }
   
   
   
   @Test
   public void testGetNotes()
   {
      assertNotEmpty( createTask().getNotes() );
   }
   
   
   
   @Test
   public void testGetNotes_Empty()
   {
      assertEmpty( createMinimalTask().getNotes() );
   }
   
   
   
   @Test
   public void testGetNote()
   {
      assertThat( createTask().getNote( "200" ), is( notNullValue() ) );
      assertThat( createTask().getNote( "201" ), is( nullValue() ) );
   }
   
   
   
   @Test
   public void testGetNote_Empty()
   {
      assertThat( createMinimalTask().getNote( "200" ), is( nullValue() ) );
   }
   
   
   
   @Test
   public void testHasNote()
   {
      assertThat( createTask().hasNote( "200" ), is( true ) );
      assertThat( createTask().hasNote( "201" ), is( false ) );
   }
   
   
   
   @Test
   public void testHasNote_Empty()
   {
      assertThat( createMinimalTask().hasNote( "200" ), is( false ) );
   }
   
   
   
   @Test
   public void testGetParticipants()
   {
      assertNotEmpty( createTask().getParticipants() );
   }
   
   
   
   @Test
   public void testGetParticipants_Empty()
   {
      assertEmpty( createMinimalTask().getParticipants() );
   }
   
   
   
   @Test
   public void testGetParticipant()
   {
      assertThat( createTask().getParticipant( "300" ), is( notNullValue() ) );
      assertThat( createTask().getParticipant( "301" ), is( nullValue() ) );
   }
   
   
   
   @Test
   public void testGetParticipant_Empty()
   {
      assertThat( createMinimalTask().getParticipant( "300" ), is( nullValue() ) );
   }
   
   
   
   @Test
   public void testGetLocationId()
   {
      assertThat( createTask().getLocationId(), is( "1000" ) );
   }
   
   
   
   @Test
   public void testGetListId()
   {
      assertThat( createTask().getListId(), is( "100" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createTask().toString();
      createMinimalTask().toString();
   }
   
   
   
   private RtmTask createTask()
   {
      return new RtmTask( "1",
                          "10",
                          NOW,
                          LATER,
                          EVEN_LATER,
                          NEVER,
                          "100",
                          "1000",
                          "Task",
                          "TestCase",
                          "http://www.moloko.com",
                          LATER,
                          Priority.Medium,
                          1,
                          NOW,
                          true,
                          "FREQ=DAILY",
                          true,
                          "1 day",
                          Arrays.asList( new String[]
                          { "tag1", "tag2" } ),
                          Arrays.asList( new RtmNote( "200",
                                                      NOW,
                                                      NOW,
                                                      "title",
                                                      "text" ) ),
                          Arrays.asList( new RtmContact( "300", "name", "user" ) ) );
   }
   
   
   
   private RtmTask createMinimalTask()
   {
      return new RtmTask( "1",
                          "10",
                          NOW,
                          LATER,
                          EVEN_LATER,
                          NEVER,
                          "100",
                          RtmConstants.NO_ID,
                          "Task",
                          "TestCase",
                          null,
                          NEVER,
                          Priority.None,
                          0,
                          NEVER,
                          false,
                          null,
                          false,
                          null,
                          null,
                          null,
                          null );
   }
}
