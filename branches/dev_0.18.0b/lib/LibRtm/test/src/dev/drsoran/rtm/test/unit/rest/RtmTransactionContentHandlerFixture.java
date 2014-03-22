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

import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmTransactionContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmTransactionContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmTransaction >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmTransactionContentHandlerFixture.class,
                                                                       "RtmTransaction.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmTransactionContentHandlerIRtmContentHandlerListenerOfRtmTransaction()
   {
      new RtmTransactionContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadTransaction() throws Exception
   {
      final RtmTransaction transaction = readContent( testFile );
      
      assertThat( transaction.getId(), is( "449135792" ) );
      assertThat( transaction.isUndoable(), is( true ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmTransaction > createHandler()
   {
      return new RtmTransactionContentHandler( null );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmTransaction > createHandlerWithListener( IRtmContentHandlerListener< RtmTransaction > listener )
   {
      return new RtmTransactionContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmTransaction createDummyContent()
   {
      return new RtmTransaction( "1", false );
   }
}
