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
import dev.drsoran.rtm.RtmServiceException;


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
   public T handleResponse( Reader responseReader ) throws RtmServiceException
   {
      try
      {
         pullParser.setInput( responseReader );
         pullParser.nextTag();
         
         checkResponse( pullParser );
         checkResponseStatus( pullParser );
         
         final RemoveWhiteSpaceXmlFilter xmlreader = new RemoveWhiteSpaceXmlFilter();
         
         final Driver saxDriver = new Driver();
         xmlreader.setParent( saxDriver );
         xmlreader.setContentHandler( contentHandler );
         
         saxDriver.parseSubTree( pullParser );
         
         return contentHandler.getContentElement();
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
   
   
   
   private void checkResponse( XmlPullParser parser ) throws XmlPullParserException,
                                                     IOException
   {
      parser.require( XmlPullParser.START_TAG, null, "rsp" );
   }
   
   
   
   private void checkResponseStatus( XmlPullParser parser ) throws RtmServiceException,
                                                           XmlPullParserException,
                                                           IOException
   {
      final String status = parser.getAttributeValue( null, "stat" );
      
      if ( "fail".equals( status ) )
      {
         int eventType = parser.getEventType();
         while ( eventType != XmlPullParser.START_TAG
            || !"err".equals( parser.getName() ) )
         {
            parser.nextTag();
         }
         
         if ( "err".equals( parser.getName() ) )
         {
            int errorCode = getRtmErrorCodeFromResponse( parser );
            throw new RtmServiceException( errorCode,
                                           parser.getAttributeValue( null,
                                                                     "msg" ) );
         }
         else
         {
            throw new RtmServiceException( "Unexpected response returned by RTM service" );
         }
      }
   }
   
   
   
   private int getRtmErrorCodeFromResponse( XmlPullParser parser )
   {
      try
      {
         return Integer.parseInt( parser.getAttributeValue( null, "code" ) );
      }
      catch ( NumberFormatException e )
      {
         return -1;
      }
   }
}
