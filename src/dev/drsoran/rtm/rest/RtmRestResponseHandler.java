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

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.sax2.Driver;

import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;


public class RtmRestResponseHandler< T > implements IRtmResponseHandler< T >
{
   private final RtmContentHandler< T > contentHandler;
   
   private final XmlPullParser pullParser;
   
   
   
   public RtmRestResponseHandler( XmlPullParser pullParser,
      RtmContentHandler< T > contentHandler )
   {
      if ( pullParser == null )
      {
         throw new IllegalArgumentException( "pullParser" );
      }
      
      if ( contentHandler == null )
      {
         throw new IllegalArgumentException( "contentHandler" );
      }
      
      this.pullParser = pullParser;
      this.contentHandler = contentHandler;
   }
   
   
   
   @Override
   public RtmResponse< T > handleResponse( Reader responseReader ) throws RtmServiceException
   {
      try
      {
         pullParser.setInput( responseReader );
         pullParser.nextTag();
         
         checkResponse();
         checkResponseStatus();
         
         final RtmTransaction transaction = readTransaction();
         
         final RemoveWhiteSpaceXmlFilter xmlreader = new RemoveWhiteSpaceXmlFilter();
         
         final Driver saxDriver = new Driver();
         xmlreader.setParent( saxDriver );
         xmlreader.setContentHandler( contentHandler );
         
         saxDriver.parseSubTree( pullParser );
         
         final RtmResponse< T > response = createResponse( transaction );
         
         return response;
      }
      catch ( SAXException e )
      {
         throw new RtmServiceException( "Failed SAX parsing the response of a request",
                                        e );
      }
      catch ( XmlPullParserException e )
      {
         throw new RtmServiceException( "Failed parsing the response of a request",
                                        e );
      }
      catch ( IOException e )
      {
         throw new RtmServiceException( "IO error during the read of response of a request",
                                        e );
      }
   }
   
   
   
   private RtmTransaction readTransaction() throws XmlPullParserException,
                                           IOException
   {
      RtmTransaction transaction = null;
      
      if ( pullParser.next() == XmlPullParser.START_TAG
         && "transaction".equalsIgnoreCase( pullParser.getText() ) )
      {
         transaction = new RtmTransaction( pullParser.getAttributeValue( null,
                                                                         "id" ),
                                           pullParser.getAttributeValue( null,
                                                                         "undoable" )
                                                     .equalsIgnoreCase( "1" ) );
         pullParser.next();
      }
      
      return transaction;
   }
   
   
   
   private RtmResponse< T > createResponse( RtmTransaction transaction )
   {
      return new RtmResponse< T >( transaction,
                                   contentHandler.getContentElement() );
   }
   
   
   
   private void checkResponse() throws XmlPullParserException, IOException
   {
      pullParser.require( XmlPullParser.START_TAG, null, "rsp" );
   }
   
   
   
   private void checkResponseStatus() throws RtmServiceException,
                                     XmlPullParserException,
                                     IOException
   {
      final String status = pullParser.getAttributeValue( null, "stat" );
      
      if ( "fail".equals( status ) )
      {
         int eventType = pullParser.getEventType();
         while ( eventType != XmlPullParser.START_TAG
            || !"err".equals( pullParser.getName() ) )
         {
            pullParser.nextTag();
         }
         
         if ( "err".equals( pullParser.getName() ) )
         {
            int errorCode = getRtmErrorCodeFromResponse();
            throw new RtmServiceException( errorCode,
                                           pullParser.getAttributeValue( null,
                                                                         "msg" ) );
         }
         else
         {
            throw new RtmServiceException( "Unexpected response returned by RTM service" );
         }
      }
   }
   
   
   
   private int getRtmErrorCodeFromResponse()
   {
      try
      {
         return Integer.parseInt( pullParser.getAttributeValue( null, "code" ) );
      }
      catch ( NumberFormatException e )
      {
         return -1;
      }
   }
}
