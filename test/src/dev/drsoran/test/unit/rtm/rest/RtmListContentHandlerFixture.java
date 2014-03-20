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

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.xml.sax.InputSource;

import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RemoveWhiteSpaceXmlFilter;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmListContentHandler;
import dev.drsoran.rtm.rest.XmlCollectionTagContentHandler;


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
   
   @ClassRule
   public static final XmlFileResource testMultiple = new XmlFileResource( RtmListContentHandlerFixture.class,
                                                                           "RtmList_multiple.xml" );
   
   
   
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
   
   
   
   @Test
   public void testReadSmartListsMultiple() throws Exception
   {
      final XmlCollectionTagContentHandler< RtmTasksList > handler = new XmlCollectionTagContentHandler< RtmTasksList >( "lists",
                                                                                                                         createHandler() );
      
      final RemoveWhiteSpaceXmlFilter filter = new RemoveWhiteSpaceXmlFilter( testMultiple.getXmlReader() );
      filter.setContentHandler( handler );
      filter.parse( new InputSource( testMultiple.getReader() ) );
      
      final List< RtmTasksList > elements = handler.getContentElement();
      
      assertThat( elements.size(), is( 3 ) );
      
      assertThat( elements.get( 0 ).getId(), is( "100653" ) );
      assertThat( elements.get( 0 ).getName(), is( "Inbox" ) );
      assertThat( elements.get( 0 ).isDeleted(), is( false ) );
      assertThat( elements.get( 0 ).isLocked(), is( true ) );
      assertThat( elements.get( 0 ).isArchived(), is( false ) );
      assertThat( elements.get( 0 ).getPosition(), is( -1 ) );
      assertThat( elements.get( 0 ).isSmartList(), is( false ) );
      assertThat( elements.get( 0 ).getSmartFilter(), is( nullValue() ) );
      
      assertThat( elements.get( 1 ).getId(), is( "100654" ) );
      assertThat( elements.get( 1 ).getName(), is( "List" ) );
      assertThat( elements.get( 1 ).isDeleted(), is( true ) );
      assertThat( elements.get( 1 ).isLocked(), is( false ) );
      assertThat( elements.get( 1 ).isArchived(), is( true ) );
      assertThat( elements.get( 1 ).getPosition(), is( 0 ) );
      assertThat( elements.get( 1 ).isSmartList(), is( true ) );
      assertThat( elements.get( 1 ).getSmartFilter(), is( "(name:Test)" ) );
      
      assertThat( elements.get( 2 ).getId(), is( "100655" ) );
      assertThat( elements.get( 2 ).getName(), is( "Sent" ) );
      assertThat( elements.get( 2 ).isDeleted(), is( false ) );
      assertThat( elements.get( 2 ).isLocked(), is( true ) );
      assertThat( elements.get( 2 ).isArchived(), is( false ) );
      assertThat( elements.get( 2 ).getPosition(), is( 1 ) );
      assertThat( elements.get( 2 ).isSmartList(), is( false ) );
      assertThat( elements.get( 2 ).getSmartFilter(), is( nullValue() ) );
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
