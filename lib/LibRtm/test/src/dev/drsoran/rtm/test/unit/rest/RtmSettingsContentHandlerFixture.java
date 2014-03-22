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

import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmSettingsContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmSettingsContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmSettings >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmSettingsContentHandlerFixture.class,
                                                                       "RtmSettings.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmSettingsContentHandlerIRtmContentHandlerListenerOfRtmSettings()
   {
      new RtmSettingsContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadSettings() throws Exception
   {
      final RtmSettings settings = readContent( testFile );
      
      assertThat( settings.getLanguage(), is( "en-US" ) );
      assertThat( settings.getTimezone(), is( "Australia/Sydney" ) );
      assertThat( settings.getDateFormat(), is( 0 ) );
      assertThat( settings.getTimeFormat(), is( 1 ) );
      assertThat( settings.getDefaultListId(), is( "123456" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmSettings > createHandler()
   {
      return new RtmSettingsContentHandler( null );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmSettings > createHandlerWithListener( IRtmContentHandlerListener< RtmSettings > listener )
   {
      return new RtmSettingsContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmSettings createDummyContent()
   {
      return new RtmSettings( 0L, "timezone", 0, 1, "defList", "lang" );
   }
}
