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

import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;
import org.xmlpull.v1.XmlPullParser;

import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmRestResponseHandler;


@Config( manifest = Config.NONE )
public class RtmRestResponseHandlerFixture extends MolokoRoboTestCase
{
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmRestResponseHandler()
   {
      new RtmRestResponseHandler< Integer >( EasyMock.createNiceMock( XmlPullParser.class ),
                                             EasyMock.createNiceMock( RtmContentHandler.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmRestResponseHandler_NullHandler()
   {
      new RtmRestResponseHandler< Integer >( EasyMock.createNiceMock( XmlPullParser.class ),
                                             null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testRtmRestResponseHandler_NullParser()
   {
      new RtmRestResponseHandler< Integer >( null,
                                             EasyMock.createNiceMock( RtmContentHandler.class ) );
   }
}
