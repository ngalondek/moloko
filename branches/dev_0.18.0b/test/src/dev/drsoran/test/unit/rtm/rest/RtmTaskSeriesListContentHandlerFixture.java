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

package dev.drsoran.test.unit.rtm.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.Iterables;
import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmTaskSeriesListContentHandler;


public class RtmTaskSeriesListContentHandlerFixture extends
         RtmContentHandlerTestCase< List< RtmTask > >
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
   public static final XmlFileResource single = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                     "RtmTaskSeriesList_singleList.xml" );
   
   @ClassRule
   public static final XmlFileResource multiple = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                       "RtmTaskSeriesList_multipleLists.xml" );
   
   @ClassRule
   public static final XmlFileResource withDeleted = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                          "RtmTaskSeriesList_withDeleted.xml" );
   
   @ClassRule
   public static final XmlFileResource withDeletedAndGenerated = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                                      "RtmTaskSeriesList_withDeletedAndGenerated.xml" );
   
   
   
   @Test
   public void testReadSingle() throws Exception
   {
      final List< RtmTask > content = Iterables.asList( readContent( single ) );
      assertThat( content.size(), is( 2 ) );
      
      for ( RtmTask task : content )
      {
         assertThat( task.getListId(), is( "1000" ) );
      }
   }
   
   
   
   @Test
   public void testReadMultiple() throws Exception
   {
      final List< RtmTask > content = Iterables.asList( readContent( multiple ) );
      assertThat( content.size(), is( 3 ) );
      
      int listId1Cnt = 0, listId2Cnt = 0;
      
      for ( RtmTask task : content )
      {
         if ( task.getListId().equals( "1000" ) )
         {
            ++listId1Cnt;
         }
         if ( task.getListId().equals( "1001" ) )
         {
            ++listId2Cnt;
         }
      }
      
      assertThat( listId1Cnt, is( 2 ) );
      assertThat( listId2Cnt, is( 1 ) );
   }
   
   
   
   @Test
   public void testReadWithDeleted() throws Exception
   {
      final List< RtmTask > content = Iterables.asList( readContent( withDeleted ) );
      assertThat( content.size(), is( 2 ) );
      
      for ( RtmTask task : content )
      {
         assertThat( task.getListId(), is( "1001" ) );
         if ( task.getId().equals( "815255" ) )
         {
            assertThat( task.getDeletedMillisUtc(),
                        is( RTM_CAL.getTimeInMillis() ) );
         }
         else
         {
            assertThat( task.getDeletedMillisUtc(), is( RtmConstants.NO_TIME ) );
         }
      }
   }
   
   
   
   @Test
   public void testReadWithDeletedAndGenerated() throws Exception
   {
      final List< RtmTask > content = Iterables.asList( readContent( withDeletedAndGenerated ) );
      assertThat( content.size(), is( 3 ) );
      
      RtmTask refTask = null;
      RtmTask generatedTask = null;
      
      for ( RtmTask task : content )
      {
         assertThat( task.getListId(), is( "1001" ) );
         if ( task.getId().equals( "815255" ) )
         {
            assertThat( task.getDeletedMillisUtc(),
                        is( RTM_CAL.getTimeInMillis() ) );
            refTask = task;
         }
         else if ( task.getId().equals( "123456789" ) )
         {
            assertThat( task.getTaskSeriesId(), is( "987654321" ) );
            generatedTask = task;
         }
         else
         {
            assertThat( task.getDeletedMillisUtc(), is( RtmConstants.NO_TIME ) );
         }
      }
      
      assertThat( generatedTask.getTaskSeriesId(), is( "987654321" ) );
      assertThat( generatedTask.getCreatedMillisUtc(),
                  is( refTask.getCreatedMillisUtc() ) );
      assertThat( generatedTask.getModifiedMillisUtc(),
                  is( RTM_CAL_TASK.getTimeInMillis() ) );
      assertThat( generatedTask.getName(), is( refTask.getName() ) );
      assertThat( generatedTask.getListId(), is( refTask.getListId() ) );
      assertThat( generatedTask.getSource(), is( refTask.getSource() ) );
      assertThat( generatedTask.getUrl(), is( refTask.getSource() ) );
      assertThat( generatedTask.getLocationId(), is( refTask.getLocationId() ) );
      assertThat( generatedTask.getTags().size(), is( refTask.getTags().size() ) );
      assertThat( generatedTask.getRecurrencePattern(),
                  is( refTask.getRecurrencePattern() ) );
      assertThat( generatedTask.isEveryRecurrence(),
                  is( refTask.isEveryRecurrence() ) );
      assertThat( generatedTask.getParticipants().size(),
                  is( refTask.getParticipants().size() ) );
      assertThat( generatedTask.getNotes().size(), is( refTask.getNotes()
                                                              .size() ) );
      assertThat( generatedTask.getId(), is( "2445789" ) );
      assertThat( generatedTask.getDueMillisUtc(), is( RtmConstants.NO_TIME ) );
      assertThat( generatedTask.hasDueTime(), is( false ) );
      assertThat( generatedTask.getAddedMillisUtc(),
                  is( RTM_CAL.getTimeInMillis() ) );
      assertThat( generatedTask.getCompletedMillisUtc(),
                  is( RtmConstants.NO_TIME ) );
      assertThat( generatedTask.getDeletedMillisUtc(),
                  is( RtmConstants.NO_TIME ) );
      assertThat( generatedTask.getPriority(), is( Priority.High ) );
      assertThat( generatedTask.getPostponedCount(), is( 2 ) );
      assertThat( generatedTask.getEstimationSentence(), is( nullValue() ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< List< RtmTask > > createHandler()
   {
      return new RtmTaskSeriesListContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< List< RtmTask > > createHandlerWithListener( IRtmContentHandlerListener< List< RtmTask > > listener )
   {
      return new RtmTaskSeriesListContentHandler( listener );
   }
   
   
   
   @Override
   protected List< RtmTask > createDummyContent()
   {
      return Collections.emptyList();
   }
}
