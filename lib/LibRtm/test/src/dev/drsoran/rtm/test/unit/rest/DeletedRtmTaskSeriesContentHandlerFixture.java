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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.TimeZone;

import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.rtm.Iterables;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.rest.DeletedRtmTaskSeriesContentHandler;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.test.TestCalendarProvider;
import dev.drsoran.rtm.test.XmlFileResource;


public class DeletedRtmTaskSeriesContentHandlerFixture extends
         RtmContentHandlerTestCase< Collection< RtmTask >>
{
   @ClassRule
   public static final XmlFileResource deleted = new XmlFileResource( DeletedRtmTaskSeriesContentHandlerFixture.class,
                                                                      "RtmTaskSeries_deleted.xml" );
   
   
   
   @Test
   public void testReadDeleted() throws Exception
   {
      Calendar cal = Calendar.getInstance( TimeZone.getTimeZone( "GMT+0" ) );
      cal.set( Calendar.YEAR, 2006 );
      cal.set( Calendar.MONTH, Calendar.MAY );
      cal.set( Calendar.DATE, 7 );
      cal.set( Calendar.HOUR_OF_DAY, 10 );
      cal.set( Calendar.MINUTE, 19 );
      cal.set( Calendar.SECOND, 54 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      final Iterable< RtmTask > content = readContent( deleted );
      assertThat( Iterables.size( content ), is( 1 ) );
      
      final RtmTask task = Iterables.first( content );
      assertThat( task.getTaskSeriesId(), is( "987654321" ) );
      assertThat( task.getId(), is( "123456789" ) );
      assertThat( task.getDeletedMillisUtc(), is( cal.getTimeInMillis() ) );
      assertThat( task.getListId(), is( "1000" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< Collection< RtmTask >> createHandler()
   {
      return new DeletedRtmTaskSeriesContentHandler( "1000",
                                                     TestCalendarProvider.get() );
   }
   
   
   
   @Override
   protected RtmContentHandler< Collection< RtmTask >> createHandlerWithListener( IRtmContentHandlerListener< Collection< RtmTask >> listener )
   {
      return new DeletedRtmTaskSeriesContentHandler( "1000",
                                                     TestCalendarProvider.get(),
                                                     listener );
   }
   
   
   
   @Override
   protected Collection< RtmTask > createDummyContent()
   {
      return Collections.emptyList();
   }
}
