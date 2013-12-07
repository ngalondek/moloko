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

package dev.drsoran.rtm;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.sax2.Driver;

import android.util.Xml;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.util.Pair;


/**
 * This class is based on the rtmjava library.
 * 
 * @see href="http://sourceforge.net/projects/rtmjava/
 */
public class RtmConnection implements IRtmConnection
{
   private final static String SERVER_HOST_NAME = "www.rememberthemilk.com";
   
   private final static int SERVER_PORT_NUMBER_HTTP = 80;
   
   private final static int SERVER_PORT_NUMBER_HTTPS = 443;
   
   private final static String REST_SERVICE_URL_POSTFIX = "/services/rest/";
   
   private final static String API_SIG_PARAM = "api_sig";
   
   private final static String ENCODING = "UTF-8";
   
   private final ILog log;
   
   private final IConnectionFactory connectionFactory;
   
   private final IRtmRequestLimiter requestLimiter;
   
   private final RtmClientInfo clientInfo;
   
   
   
   public RtmConnection( ILog log, IConnectionFactory connectionFactory,
      IRtmRequestLimiter requestLimiter, RtmClientInfo clientInfo )
   {
      this.log = log;
      this.connectionFactory = connectionFactory;
      this.requestLimiter = requestLimiter;
      this.clientInfo = clientInfo;
   }
   
   
   
   @Override
   public void invoke( ContentHandler contentHandler, Param... params ) throws RtmServiceException
   {
      requestLimiter.obeyRtmRequestLimit();
      
      log.d( getClass(),
             MessageFormat.format( "Invoker running at {0}", new Date() ) );
      
      Reader responseReader = null;
      IConnection connection = null;
      
      try
      {
         final Param[] requestParams = buildRequestParams( params );
         final String requestUri = computeRequestUri( requestParams );
         
         connection = createConnection();
         responseReader = connection.execute( requestUri );
         
         final XmlPullParser parser = Xml.newPullParser();
         parser.setInput( responseReader );
         parser.nextTag();
         
         checkResponse( parser );
         checkResponseStatus( parser );
         
         final Driver saxDriver = new Driver();
         
         saxDriver.setContentHandler( contentHandler );
         saxDriver.parseSubTree( parser );
      }
      catch ( UnsupportedEncodingException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IOException e )
      {
         throw new RtmServiceException( e.getLocalizedMessage(), e );
      }
      catch ( XmlPullParserException e )
      {
         throw new RtmServiceException( e.getLocalizedMessage(), e );
      }
      catch ( SAXException e )
      {
         throw new RtmServiceException( e.getLocalizedMessage(), e );
      }
      catch ( NoSuchAlgorithmException e )
      {
         throw new RuntimeException( e );
      }
      finally
      {
         if ( responseReader != null )
         {
            try
            {
               responseReader.close();
            }
            catch ( IOException e )
            {
            }
         }
         
         if ( connection != null )
         {
            connection.close();
         }
      }
   }
   
   
   
   private Param[] buildRequestParams( Param[] params )
   {
      final Param[] apiSignedParams = Arrays.copyOf( params, params.length + 2 );
      apiSignedParams[ params.length ] = new Param( "auth_token",
                                                    clientInfo.getAuthToken() );
      apiSignedParams[ params.length + 1 ] = new Param( "api_key",
                                                        clientInfo.getApiKey() );
      
      return apiSignedParams;
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
      
      if ( ( "fail" ).equals( status ) )
      {
         int eventType = parser.getEventType();
         while ( eventType != XmlPullParser.START_TAG
            || !"err".equals( parser.getName() ) )
         {
            parser.nextTag();
         }
         
         if ( "err".equals( parser.getName() ) )
         {
            throw new RtmServiceException( Integer.parseInt( parser.getAttributeValue( null,
                                                                                       "code" ) ),
                                           parser.getAttributeValue( null,
                                                                     "msg" ) );
         }
         else
         {
            throw new RtmServiceException( "unexpected response returned by RTM service: " );
         }
      }
   }
   
   
   
   private IConnection createConnection()
   {
      final String scheme = clientInfo.isUseHttps() ? "https" : "http";
      final int port = clientInfo.isUseHttps() ? SERVER_PORT_NUMBER_HTTPS
                                              : SERVER_PORT_NUMBER_HTTP;
      
      final IConnection connection = connectionFactory.createConnection( scheme,
                                                                         SERVER_HOST_NAME,
                                                                         port );
      return connection;
   }
   
   
   
   private String computeRequestUri( Param... params ) throws NoSuchAlgorithmException,
                                                      UnsupportedEncodingException
   {
      final StringBuilder requestUri = new StringBuilder( REST_SERVICE_URL_POSTFIX );
      
      if ( params.length > 0 )
      {
         requestUri.append( "?" );
      }
      
      for ( Param param : params )
      {
         requestUri.append( param.getName() )
                   .append( "=" )
                   .append( URLEncoder.encode( param.getValue(), ENCODING ) )
                   .append( "&" );
      }
      
      requestUri.append( API_SIG_PARAM )
                .append( "=" )
                .append( calcApiSig( clientInfo.getSharedSecret(), params ) );
      
      return requestUri.toString();
   }
   
   
   
   private MessageDigest getDigest() throws NoSuchAlgorithmException
   {
      return MessageDigest.getInstance( "md5" );
   }
   
   
   
   /**
    * @param params
    *           Pair, where the {@link Pair.first} is the parameter name and the {@link Pair.second} the parameter value
    * @throws UnsupportedEncodingException
    * @throws NoSuchAlgorithmException
    */
   private String calcApiSig( String sharedSecret, Param... params ) throws NoSuchAlgorithmException,
                                                                    UnsupportedEncodingException
   {
      final MessageDigest digest = getDigest();
      
      digest.reset();
      digest.update( sharedSecret.getBytes( ENCODING ) );
      
      final List< Param > sorted = Arrays.asList( params );
      Collections.sort( sorted );
      
      for ( Param param : sorted )
      {
         digest.update( param.getName().getBytes( ENCODING ) );
         digest.update( param.getValue().getBytes( ENCODING ) );
      }
      
      return convertToHex( digest.digest() );
   }
   
   
   
   private static String convertToHex( byte[] data )
   {
      final StringBuilder buf = new StringBuilder();
      for ( int i = 0; i < data.length; i++ )
      {
         int halfbyte = ( data[ i ] >>> 4 ) & 0x0F;
         int two_halfs = 0;
         do
         {
            if ( ( 0 <= halfbyte ) && ( halfbyte <= 9 ) )
            {
               buf.append( (char) ( '0' + halfbyte ) );
            }
            else
            {
               buf.append( (char) ( 'a' + ( halfbyte - 10 ) ) );
            }
            
            halfbyte = data[ i ] & 0x0F;
         }
         while ( two_halfs++ < 1 );
      }
      
      return buf.toString();
   }
}
