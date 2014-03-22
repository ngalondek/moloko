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

import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmTimelineContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmTimelineContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmTimeline >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmTimelineContentHandlerFixture.class,
                                                                       "RtmTimeline.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmTimelineContentHandlerIRtmContentHandlerListenerOfRtmTimeline()
   {
      new RtmTimelineContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadAuth() throws Exception
   {
      final RtmTimeline timeline = readContent( testFile );
      
      assertThat( timeline.getId(), is( "12741021" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmTimeline > createHandler()
   {
      return new RtmTimelineContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmTimeline > createHandlerWithListener( IRtmContentHandlerListener< RtmTimeline > listener )
   {
      return new RtmTimelineContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmTimeline createDummyContent()
   {
      return new RtmTimeline( "1" );
   }
}
