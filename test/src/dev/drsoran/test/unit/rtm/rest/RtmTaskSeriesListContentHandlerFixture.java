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
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.Iterables;
import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmTaskSeriesListContentHandler;


public class RtmTaskSeriesListContentHandlerFixture extends
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
   public static final XmlFileResource single = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                     "RtmTaskSeriesList_singleList.xml" );
   
   @ClassRule
   public static final XmlFileResource multiple = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                       "RtmTaskSeriesList_multipleLists.xml" );
   
   @ClassRule
   public static final XmlFileResource withDeleted = new XmlFileResource( RtmTaskSeriesListContentHandlerFixture.class,
                                                                          "RtmTaskSeriesList_withDeleted.xml" );
   
   
   
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
   
   
   
   @Override
   protected RtmContentHandler< Collection< RtmTask > > createHandler()
   {
      return new RtmTaskSeriesListContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< Collection< RtmTask > > createHandlerWithListener( IRtmContentHandlerListener< Collection< RtmTask > > listener )
   {
      return new RtmTaskSeriesListContentHandler( listener );
   }
   
   
   
   @Override
   protected Collection< RtmTask > createDummyContent()
   {
      return Collections.emptyList();
   }
}
