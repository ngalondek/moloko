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
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmRestResponseHandler;
import dev.drsoran.rtm.rest.VoidContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmRestResponseHandlerFixture
{
   @ClassRule
   public static final XmlFileResource responseError = new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                                                            "RtmRestResponseHandler_responseError.xml" );
   
   @ClassRule
   public static final XmlFileResource responseErrorWithContent = new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                                                                       "RtmRestResponseHandler_responseErrorWithContent.xml" );
   
   @ClassRule
   public static final XmlFileResource transaction = new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                                                          "RtmRestResponseHandler_transaction.xml" );
   
   @ClassRule
   public static final XmlFileResource saxException = new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                                                           "RtmRestResponseHandler_transaction.xml" );
   
   @ClassRule
   public static final XmlFileResource noTransaction = new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                                                            "RtmRestResponseHandler_noTransaction.xml" );
   
   @ClassRule
   public static final XmlFileResource voidResp = new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                                                       "RtmRestResponseHandler_void.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmRestResponseHandler()
   {
      new RtmRestResponseHandler< Integer >( EasyMock.createNiceMock( RtmContentHandler.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmRestResponseHandler_NullHandler()
   {
      new RtmRestResponseHandler< Integer >( null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testResponseError() throws Exception
   {
      final RtmContentHandler< Integer > handler = EasyMock.createStrictMock( RtmContentHandler.class );
      EasyMock.replay( handler );
      
      try
      {
         readContent( responseError, handler );
      }
      finally
      {
         EasyMock.verify( handler );
      }
   }
   
   
   
   @Test( expected = RtmServiceException.class )
   public void testSAXException() throws Exception
   {
      readContent( saxException, new ThrowingHandler() );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testIOException() throws Exception
   {
      final RtmContentHandler< Integer > handler = EasyMock.createNiceMock( RtmContentHandler.class );
      EasyMock.replay( handler );
      
      readContent( new XmlFileResource( RtmRestResponseHandlerFixture.class,
                                        "wrongFile.xml" ), handler );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testDontParseOnError() throws Exception
   {
      final RtmContentHandler< Integer > handler = EasyMock.createStrictMock( RtmContentHandler.class );
      EasyMock.replay( handler );
      
      try
      {
         readContent( responseErrorWithContent, handler );
      }
      finally
      {
         EasyMock.verify( handler );
      }
   }
   
   
   
   @Test
   public void testTransaction() throws Exception
   {
      final RtmContentHandler< Integer > handler = new IntegerHandler();
      final RtmResponse< Integer > content = readContent( transaction, handler );
      
      assertThat( content, is( notNullValue() ) );
      assertThat( content.getTransaction(), is( notNullValue() ) );
      assertThat( content.getTransaction().getId(), is( "449135792" ) );
      assertThat( content.getTransaction().isUndoable(), is( true ) );
      assertThat( content.getElement(), is( 10 ) );
   }
   
   
   
   @Test
   public void testNoTransaction() throws Exception
   {
      final RtmContentHandler< Integer > handler = new IntegerHandler();
      final RtmResponse< Integer > content = readContent( noTransaction,
                                                          handler );
      
      assertThat( content, is( notNullValue() ) );
      assertThat( content.getTransaction(), is( nullValue() ) );
      assertThat( content.getElement(), is( 10 ) );
   }
   
   
   
   @Test
   public void testVoid() throws Exception
   {
      final RtmContentHandler< Void > handler = new VoidContentHandler( null );
      final RtmResponse< Void > content = readContent( voidResp, handler );
      
      assertThat( content, is( notNullValue() ) );
      assertThat( content.getTransaction(), is( nullValue() ) );
      assertThat( content.getElement(), is( nullValue() ) );
   }
   
   
   
   private < T > RtmResponse< T > readContent( XmlFileResource file,
                                               RtmContentHandler< T > contentHandler ) throws Exception
   {
      final IRtmResponseHandler< T > responseHandler = new RtmRestResponseHandler< T >( contentHandler );
      return responseHandler.handleResponse( file.getReader() );
   }
   
   
   private final static class IntegerHandler extends
            RtmContentHandler< Integer >
   {
      public IntegerHandler()
      {
         super( null );
      }
      
      
      
      @Override
      protected void startElement( String qName, Attributes attributes ) throws SAXException
      {
         setContentElementAndNotify( Integer.parseInt( attributes.getValue( "value" ) ) );
      }
      
      
      
      @Override
      protected void cleanUpState()
      {
      }
   }
   
   
   private final static class ThrowingHandler extends
            RtmContentHandler< Integer >
   {
      
      public ThrowingHandler()
      {
         super( null );
      }
      
      
      
      @Override
      protected void startElement( String qName, Attributes attributes ) throws SAXException
      {
         throw new SAXException();
      }
      
      
      
      @Override
      protected void cleanUpState()
      {
      }
   }
}
