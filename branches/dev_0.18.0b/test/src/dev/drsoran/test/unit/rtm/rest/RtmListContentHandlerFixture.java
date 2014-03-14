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

import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmListContentHandler;


public class RtmListContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmTasksList >
{
   @ClassRule
   public static final XmlFileResource testNoSmartList = new XmlFileResource( RtmListContentHandlerFixture.class,
                                                                              "RtmList_NoSmart.xml" );
   
   @ClassRule
   public static final XmlFileResource testSmartList = new XmlFileResource( RtmListContentHandlerFixture.class,
                                                                            "RtmList_Smart.xml" );
   
   @ClassRule
   public static final XmlFileResource testSmartListEmptyFilter = new XmlFileResource( RtmListContentHandlerFixture.class,
                                                                                       "RtmList_Smart_emptyFilter.xml" );
   
   
   
   @Test
   public void testReadNotSmartList() throws Exception
   {
      final RtmTasksList content = readContent( testNoSmartList );
      
      assertThat( content.getId(), is( "100653" ) );
      assertThat( content.getName(), is( "Inbox" ) );
      assertThat( content.isDeleted(), is( false ) );
      assertThat( content.isLocked(), is( true ) );
      assertThat( content.isArchived(), is( false ) );
      assertThat( content.getPosition(), is( -1 ) );
      assertThat( content.isSmartList(), is( false ) );
      assertThat( content.getSmartFilter(), is( nullValue() ) );
   }
   
   
   
   @Test
   public void testReadSmartList() throws Exception
   {
      final RtmTasksList content = readContent( testSmartList );
      
      assertThat( content.getId(), is( "100" ) );
      assertThat( content.getName(), is( "SmartList" ) );
      assertThat( content.isDeleted(), is( true ) );
      assertThat( content.isLocked(), is( false ) );
      assertThat( content.isArchived(), is( true ) );
      assertThat( content.getPosition(), is( 1 ) );
      assertThat( content.isSmartList(), is( true ) );
      assertThat( content.getSmartFilter(), is( "(priority:1)" ) );
   }
   
   
   
   @Test
   public void testReadSmartListEmptyFilter() throws Exception
   {
      final RtmTasksList content = readContent( testSmartListEmptyFilter );
      
      assertThat( content.getId(), is( "100" ) );
      assertThat( content.getName(), is( "SmartList" ) );
      assertThat( content.isDeleted(), is( true ) );
      assertThat( content.isLocked(), is( false ) );
      assertThat( content.isArchived(), is( true ) );
      assertThat( content.getPosition(), is( 1 ) );
      assertThat( content.isSmartList(), is( true ) );
      assertThat( content.getSmartFilter(), is( "" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmTasksList > createHandler()
   {
      return new RtmListContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmTasksList > createHandlerWithListener( IRtmContentHandlerListener< RtmTasksList > listener )
   {
      return new RtmListContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmTasksList createDummyContent()
   {
      return new RtmTasksList( "1", -1, false, false, false, "list", null );
   }
}
