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
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContactContentHandler;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmNoteContentHandler;
import dev.drsoran.rtm.test.TestConstants;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmNoteContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmNote >
{
   @ClassRule
   public static final XmlFileResource fullNote = new XmlFileResource( RtmNoteContentHandlerFixture.class,
                                                                       "RtmNote_full.xml" );
   
   @ClassRule
   public static final XmlFileResource noTitle = new XmlFileResource( RtmNoteContentHandlerFixture.class,
                                                                      "RtmNote_noTitle.xml" );
   
   @ClassRule
   public static final XmlFileResource noText = new XmlFileResource( RtmNoteContentHandlerFixture.class,
                                                                     "RtmNote_noText.xml" );
   
   private final static Calendar RTM_CAL;
   
   static
   {
      RTM_CAL = Calendar.getInstance( TimeZone.getTimeZone( "GMT+0" ) );
      RTM_CAL.set( Calendar.YEAR, 2006 );
      RTM_CAL.set( Calendar.MONTH, Calendar.MAY );
      RTM_CAL.set( Calendar.DATE, 7 );
      RTM_CAL.set( Calendar.HOUR_OF_DAY, 11 );
      RTM_CAL.set( Calendar.MINUTE, 26 );
      RTM_CAL.set( Calendar.SECOND, 49 );
      RTM_CAL.set( Calendar.MILLISECOND, 0 );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmContactContentHandlerIRtmContentHandlerListenerOfRtmContact()
   {
      new RtmContactContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadNote_Full() throws Exception
   {
      final RtmNote content = readContent( fullNote );
      
      assertThat( content.getId(), is( "169624" ) );
      assertThat( content.getCreatedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( content.getModifiedMillisUtc(),
                  is( RTM_CAL.getTimeInMillis() ) );
      assertThat( content.getTitle(), is( "Note Title" ) );
      assertThat( content.getText(), is( "Note Body" ) );
   }
   
   
   
   @Test
   public void testReadNote_noTitle() throws Exception
   {
      final RtmNote content = readContent( noTitle );
      
      assertThat( content.getId(), is( "169624" ) );
      assertThat( content.getCreatedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( content.getModifiedMillisUtc(),
                  is( RTM_CAL.getTimeInMillis() ) );
      assertThat( content.getTitle(), is( "" ) );
      assertThat( content.getText(), is( "Note Body" ) );
   }
   
   
   
   @Test
   public void testReadNote_noText() throws Exception
   {
      final RtmNote content = readContent( noText );
      
      assertThat( content.getId(), is( "169624" ) );
      assertThat( content.getCreatedMillisUtc(), is( RTM_CAL.getTimeInMillis() ) );
      assertThat( content.getModifiedMillisUtc(),
                  is( RTM_CAL.getTimeInMillis() ) );
      assertThat( content.getTitle(), is( "Note Title" ) );
      assertThat( content.getText(), is( "" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmNote > createHandler()
   {
      return new RtmNoteContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmNote > createHandlerWithListener( IRtmContentHandlerListener< RtmNote > listener )
   {
      return new RtmNoteContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmNote createDummyContent()
   {
      return new RtmNote( "1",
                          TestConstants.NOW,
                          TestConstants.LATER,
                          "title",
                          "text" );
   }
}
