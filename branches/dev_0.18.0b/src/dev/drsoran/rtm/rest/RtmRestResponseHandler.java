/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.rest;

import java.io.IOException;
import java.io.Reader;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.service.RtmError;


public class RtmRestResponseHandler< T > extends RtmContentHandler< T >
         implements IRtmResponseHandler< T >
{
   private final IRtmContentHandlerListener< RtmError > errorListener = new IRtmContentHandlerListener< RtmError >()
   {
      @Override
      public void onContentHandled( RtmError error )
      {
         handleResponseError( error );
         popNestedContentHandler();
      }
   };
   
   private final IRtmContentHandlerListener< RtmTransaction > transactionListener = new IRtmContentHandlerListener< RtmTransaction >()
   {
      @Override
      public void onContentHandled( RtmTransaction transaction )
      {
         RtmRestResponseHandler.this.transaction = transaction;
         popNestedContentHandler();
      }
   };
   
   private final IRtmContentHandlerListener< T > contentHandlerListener = new IRtmContentHandlerListener< T >()
   {
      @Override
      public void onContentHandled( T contentElement )
      {
         popNestedContentHandler();
      }
   };
   
   private final RtmContentHandler< T > contentHandler;
   
   private RtmTransaction transaction;
   
   private RtmServiceException pendingErrorException;
   
   
   
   public RtmRestResponseHandler( RtmContentHandler< T > contentHandler )
   {
      super( null );
      
      if ( contentHandler == null )
      {
         throw new IllegalArgumentException( "contentHandler" );
      }
      
      this.contentHandler = contentHandler;
   }
   
   
   
   @Override
   public RtmResponse< T > handleResponse( Reader responseReader ) throws RtmServiceException
   {
      try
      {
         final InputSource inputSource = new InputSource( responseReader );
         final XMLReader xmlReader = new RemoveWhiteSpaceXmlFilter( XMLReaderFactory.createXMLReader() );
         
         xmlReader.setContentHandler( this );
         xmlReader.parse( inputSource );
         
         if ( pendingErrorException != null )
         {
            throw pendingErrorException;
         }
         
         final RtmResponse< T > response = createResponse();
         return response;
      }
      catch ( SAXException e )
      {
         throw new RtmServiceException( "Failed SAX parsing the response of a request",
                                        e );
      }
      catch ( IOException e )
      {
         throw new RtmServiceException( "IO error during the read of response of a request",
                                        e );
      }
   }
   
   
   
   @Override
   protected void startElement( String qName, Attributes attributes ) throws SAXException
   {
      if ( pendingErrorException == null )
      {
         if ( "rsp".equalsIgnoreCase( qName ) )
         {
            checkResponseStatus( qName, attributes );
         }
         else if ( "transaction".equalsIgnoreCase( qName ) )
         {
            pushNestedContentHandlerAndStartElement( new RtmTransactionContentHandler( transactionListener ),
                                                     qName,
                                                     attributes );
         }
         else
         {
            contentHandler.setListener( contentHandlerListener );
            pushNestedContentHandlerAndStartElement( contentHandler,
                                                     qName,
                                                     attributes );
         }
      }
   }
   
   
   
   private RtmResponse< T > createResponse()
   {
      return new RtmResponse< T >( transaction,
                                   contentHandler.getContentElement() );
   }
   
   
   
   private void checkResponseStatus( String qName, Attributes attributes ) throws SAXException
   {
      if ( "fail".equalsIgnoreCase( XmlAttr.getOptString( attributes,
                                                          "stat",
                                                          null ) ) )
      {
         pushNestedContentHandlerAndStartElement( new RtmErrorContentHandler( errorListener ),
                                                  qName,
                                                  attributes );
      }
   }
   
   
   
   private void handleResponseError( RtmError error )
   {
      pendingErrorException = new RtmServiceException( error.getCode(),
                                                       error.getMessage(),
                                                       null );
   }
}
