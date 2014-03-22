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

package dev.drsoran.rtm.test.unit.rest;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.rtm.Iterables;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmTaskSeriesContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmTaskSeriesContentHandlerFixture extends
         RtmContentHandlerTestCase< Collection< RtmTask > >
{
   private final static Calendar RTM_CAL;
   
   private final static Calendar RTM_CAL_TASK;
   
   static
   {
      RTM_CAL = Calendar.getInstance( TimeZone.getTimeZone( "GMT+0" ) );
      RTM_CAL.set( Calendar.YEAR, 2006 );
      RTM_CAL.set( Calendar.MONTH, Calendar.MAY );
      RTM_CAL.set( Calendar.DATE, 7 );
      RTM_CAL.set( Calendar.HOUR_OF_DAY, 10 );
      RTM_CAL.set( Calendar.MINUTE, 19 );
      RTM_CAL.set( Calendar.SECOND, 54 );
      RTM_CAL.set( Calendar.MILLISECOND, 0 );
      
      RTM_CAL_TASK = Calendar.getInstance( TimeZone.getTimeZone( "GMT+0" ) );
      RTM_CAL_TASK.set( Calendar.YEAR, 2006 );
      RTM_CAL_TASK.set( Calendar.MONTH, Calendar.MAY );
      RTM_CAL_TASK.set( Calendar.DATE, 7 );
      RTM_CAL_TASK.set( Calendar.HOUR_OF_DAY, 12 );
      RTM_CAL_TASK.set( Calendar.MINUTE, 20 );
      RTM_CAL_TASK.set( Calendar.SECOND, 00 );
      RTM_CAL_TASK.set( Calendar.MILLISECOND, 0 );
   }
   
   @ClassRule
   public static final XmlFileResource full = new XmlFileResource( RtmTaskSeriesContentHandlerFixture.class,
                                                                   "RtmTaskSeries_full.xml" );
   
   @ClassRule
   public static final XmlFileResource minimal = new XmlFileResource( RtmTaskSeriesContentHandlerFixture.class,
                                                                      "RtmTaskSeries_minimal.xml" );
   
   
   
   @Test
   public void testReadFull() throws Exception
   {
      final List< RtmTask > content = Iterables.asList( readContent( full ) );
      assertThat( content.size(), is( 2 ) );
      
      for ( RtmTask task : content )
      {
         assertThat( task.getTaskSeriesId(), is( "987654321" ) );
         assertThat( task.getCreatedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
         assertThat( task.getModifiedMillisUtc(),
                     is( RTM_CAL.getTimeInMillis() ) );
         assertThat( task.getName(), is( "Get Bananas" ) );
         assertThat( task.getListId(), is( "1" ) );
         assertThat( task.getSource(), is( "api" ) );
         assertThat( task.getUrl(),
                     is( "http://www.myfavoritecoffeeplace.com/" ) );
         assertThat( task.getLocationId(), is( "123456" ) );
         assertThat( task.getTags(), hasItems( "A Tag", "Another Tag" ) );
         assertThat( task.getRecurrencePattern(), is( "FREQ=DAILY;INTERVAL=1" ) );
         assertThat( task.isEveryRecurrence(), is( true ) );
         
         assertThat( task.getParticipants().size(), is( 2 ) );
         
         final RtmContact contact1 = task.getParticipant( "1" );
         assertThat( contact1, is( notNullValue() ) );
         assertThat( contact1.getId(), is( "1" ) );
         assertThat( contact1.getFullname(), is( "Omar Kilani" ) );
         assertThat( contact1.getUsername(), is( "omar" ) );
         
         final RtmContact contact2 = task.getParticipant( "2" );
         assertThat( contact2, is( notNullValue() ) );
         assertThat( contact2.getId(), is( "2" ) );
         assertThat( contact2.getFullname(), is( "Hans Meiser" ) );
         assertThat( contact2.getUsername(), is( "hamei" ) );
         
         assertThat( task.getNotes().size(), is( 2 ) );
         
         final RtmNote note1 = task.getNote( "169624" );
         assertThat( note1, is( notNullValue() ) );
         assertThat( note1.getId(), is( "169624" ) );
         assertThat( note1.getCreatedMillisUtc(),
                     is( RTM_CAL.getTimeInMillis() ) );
         assertThat( note1.getModifiedMillisUtc(),
                     is( RTM_CAL.getTimeInMillis() ) );
         assertThat( note1.getTitle(), is( "Note Title" ) );
         assertThat( note1.getText(), is( "Note Body1" ) );
         
         final RtmNote note2 = task.getNote( "169625" );
         assertThat( note2, is( notNullValue() ) );
         assertThat( note2.getId(), is( "169625" ) );
         assertThat( note2.getCreatedMillisUtc(),
                     is( RTM_CAL.getTimeInMillis() ) );
         assertThat( note2.getModifiedMillisUtc(),
                     is( RTM_CAL.getTimeInMillis() ) );
         assertThat( note2.getTitle(), is( "" ) );
         assertThat( note2.getText(), is( "Note Body2" ) );
      }
      
      assertThat( content.size(), is( 2 ) );
      
      final RtmTask task1 = content.get( 0 );
      assertThat( task1.getId(), is( "123456789" ) );
      assertThat( task1.getDueMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( task1.hasDueTime(), is( false ) );
      assertThat( task1.getAddedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( task1.getCompletedMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( task1.getDeletedMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( task1.getPriority(), is( Priority.None ) );
      assertThat( task1.getPostponedCount(), is( 0 ) );
      assertThat( task1.getEstimationSentence(), is( nullValue() ) );
      
      final RtmTask task2 = content.get( 1 );
      assertThat( task2.getId(), is( "123456790" ) );
      assertThat( task2.getDueMillisUtc(), is( RTM_CAL_TASK.getTimeInMillis() ) );
      assertThat( task2.hasDueTime(), is( true ) );
      
      Calendar cal = Calendar.getInstance( TimeZone.getTimeZone( "GMT+0" ) );
      cal.setTimeInMillis( RTM_CAL_TASK.getTimeInMillis() );
      cal.add( Calendar.HOUR_OF_DAY, 1 );
      assertThat( task2.getAddedMillisUtc(), is( cal.getTimeInMillis() ) );
      
      cal.add( Calendar.HOUR_OF_DAY, 1 );
      assertThat( task2.getCompletedMillisUtc(), is( cal.getTimeInMillis() ) );
      
      cal.add( Calendar.HOUR_OF_DAY, 1 );
      assertThat( task2.getDeletedMillisUtc(), is( cal.getTimeInMillis() ) );
      assertThat( task2.getPriority(), is( Priority.High ) );
      assertThat( task2.getPostponedCount(), is( 2 ) );
      assertThat( task2.getEstimationSentence(), is( "5 days" ) );
   }
   
   
   
   @Test
   public void testReadMinimal() throws Exception
   {
      final Collection< RtmTask > content = readContent( minimal );
      assertThat( content.size(), is( 1 ) );
      
      final RtmTask task = Iterables.first( content );
      assertThat( task.getTaskSeriesId(), is( "987654321" ) );
      assertThat( task.getCreatedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( task.getModifiedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( task.getName(), is( "Get Bananas" ) );
      assertThat( task.getListId(), is( "1" ) );
      assertThat( task.getSource(), is( "api" ) );
      assertThat( task.getUrl(), is( "" ) );
      assertThat( task.getLocationId(), is( RtmConstants.NO_ID ) );
      assertThat( task.getTags().size(), is( 0 ) );
      assertThat( task.getRecurrencePattern(), is( nullValue() ) );
      assertThat( task.isEveryRecurrence(), is( false ) );
      assertThat( task.getParticipants().size(), is( 0 ) );
      assertThat( task.getNotes().size(), is( 0 ) );
      assertThat( task.getId(), is( "123456789" ) );
      assertThat( task.getDueMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( task.hasDueTime(), is( false ) );
      assertThat( task.getAddedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( task.getCompletedMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( task.getDeletedMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( task.getPriority(), is( Priority.None ) );
      assertThat( task.getPostponedCount(), is( 0 ) );
      assertThat( task.getEstimationSentence(), is( nullValue() ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< Collection< RtmTask > > createHandler()
   {
      return new RtmTaskSeriesContentHandler( "1" );
   }
   
   
   
   @Override
   protected RtmContentHandler< Collection< RtmTask > > createHandlerWithListener( IRtmContentHandlerListener< Collection< RtmTask > > listener )
   {
      return new RtmTaskSeriesContentHandler( "1", listener );
   }
   
   
   
   @Override
   protected Collection< RtmTask > createDummyContent()
   {
      return Collections.emptyList();
   }
}
