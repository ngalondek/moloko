/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.text.format.DateUtils;
import android.util.Log;


/**
 * Handles the details of invoking a method on the RTM REST API.
 * 
 * @author Will Ross Jun 21, 2007
 */
public class Invoker
{
   private static final String TAG = "Moloko." + Invoker.class.getSimpleName();
   
   private static final DocumentBuilder builder;
   static
   {
      // Done this way because the builder is marked "final"
      DocumentBuilder aBuilder;
      try
      {
         final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware( false );
         factory.setValidating( false );
         aBuilder = factory.newDocumentBuilder();
      }
      catch ( Exception exception )
      {
         Log.e( TAG, "Unable to construct a document builder", exception );
         aBuilder = null;
      }
      builder = aBuilder;
   }
   
   public static final String REST_SERVICE_URL_POSTFIX = "/services/rest/";
   
   public static final String ENCODING = "UTF-8";
   
   public static String API_SIG_PARAM = "api_sig";
   
   public static final long INVOCATION_INTERVAL = 2000;
   
   private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
   
   private static final String ENCODING_GZIP = "gzip";
   
   private long lastInvocation;
   
   private final ApplicationInfo applicationInfo;
   
   private final MessageDigest digest;
   
   private final String hostname;
   
   private final int port;
   
   private final String serviceRelativeUri;
   
   private final HttpHost host;
   
   private final DefaultHttpClient client;
   
   private final BasicHttpParams httpParams;
   
   
   /**
    * Simple {@link HttpEntityWrapper} that inflates the wrapped {@link HttpEntity} by passing it through
    * {@link GZIPInputStream}.
    * 
    * Took from iosched - Google I/O Schedule App for Android
    * http://code.google.com/p/iosched/source/browse/trunk/src/com/google/android/apps/iosched/service/SyncService.java
    */
   private static class InflatingEntity extends HttpEntityWrapper
   {
      public InflatingEntity( HttpEntity wrapped )
      {
         super( wrapped );
      }
      


      @Override
      public InputStream getContent() throws IOException
      {
         return new GZIPInputStream( wrappedEntity.getContent() );
      }
      


      @Override
      public long getContentLength()
      {
         return -1;
      }
   }
   
   

   public Invoker( String serverHostName, int serverPortNumber,
      String serviceRelativeUri, ProxySettings proxySettings, boolean useHttp,
      ApplicationInfo applicationInfo ) throws ServiceInternalException
   {
      this.hostname = serverHostName;
      this.port = serverPortNumber;
      this.serviceRelativeUri = serviceRelativeUri;
      
      httpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout( httpParams,
                                                 20 * (int) DateUtils.SECOND_IN_MILLIS );
      HttpConnectionParams.setSoTimeout( httpParams,
                                         20 * (int) DateUtils.SECOND_IN_MILLIS );
      HttpConnectionParams.setSocketBufferSize( httpParams, 8192 );
      HttpProtocolParams.setVersion( httpParams, HttpVersion.HTTP_1_1 );
      HttpProtocolParams.setContentCharset( httpParams, ENCODING );
      HttpProtocolParams.setUserAgent( httpParams, "Jakarta-HttpComponents/4.0" );
      HttpProtocolParams.setUseExpectContinue( httpParams, true );
      
      host = new HttpHost( hostname, port, ( useHttp ? "http" : "https" ) );
      
      client = new DefaultHttpClient( httpParams );
      
      if ( proxySettings != null )
      {
         final String proxyHostName = proxySettings.getProxyHostName();
         
         if ( proxySettings.getProxyHostName() != null )
         {
            final int proxyPort = proxySettings.getProxyPortNumber();
            final HttpHost proxy = new HttpHost( proxyHostName, proxyPort );
            
            UsernamePasswordCredentials credentials = null;
            
            if ( proxySettings.getProxyLogin() != null )
               credentials = new UsernamePasswordCredentials( proxySettings.getProxyLogin(),
                                                              proxySettings.getProxyPassword() );
            
            // Sets an HTTP proxy and the credentials for authentication
            client.getCredentialsProvider()
                  .setCredentials( new AuthScope( proxyHostName, proxyPort ),
                                   credentials );
            client.getParams().setParameter( ConnRoutePNames.DEFAULT_PROXY,
                                             proxy );
         }
      }
      
      client.addRequestInterceptor( new HttpRequestInterceptor()
      {
         public void process( HttpRequest request, HttpContext context )
         {
            // Add header to accept gzip content
            if ( !request.containsHeader( HEADER_ACCEPT_ENCODING ) )
            {
               request.addHeader( HEADER_ACCEPT_ENCODING, ENCODING_GZIP );
            }
         }
      } );
      
      lastInvocation = System.currentTimeMillis();
      this.applicationInfo = applicationInfo;
      
      try
      {
         digest = MessageDigest.getInstance( "md5" );
      }
      catch ( NoSuchAlgorithmException e )
      {
         throw new ServiceInternalException( "Could not create properly the MD5 digest",
                                             e );
      }
   }
   


   private StringBuffer computeRequestUri( Param... params ) throws ServiceInternalException
   {
      final StringBuffer requestUri = new StringBuffer( serviceRelativeUri );
      if ( params.length > 0 )
      {
         requestUri.append( "?" );
      }
      for ( Param param : params )
      {
         try
         {
            requestUri.append( param.getName() )
                      .append( "=" )
                      .append( URLEncoder.encode( param.getValue(), ENCODING ) )
                      .append( "&" );
         }
         catch ( Exception exception )
         {
            final StringBuffer message = new StringBuffer( "Cannot encode properly the HTTP GET request URI: cannot execute query" );
            Log.e( TAG, message.toString(), exception );
            throw new ServiceInternalException( message.toString() );
         }
      }
      requestUri.append( API_SIG_PARAM )
                .append( "=" )
                .append( calcApiSig( params ) );
      return requestUri;
   }
   


   public Element invoke( Param... params ) throws ServiceException
   {
      final long timeSinceLastInvocation = System.currentTimeMillis()
         - lastInvocation;
      
      if ( timeSinceLastInvocation < INVOCATION_INTERVAL )
      {
         // In order not to invoke the RTM service too often
         try
         {
            Thread.sleep( INVOCATION_INTERVAL - timeSinceLastInvocation );
         }
         catch ( InterruptedException e )
         {
            throw new ServiceInternalException( "Unexpected interruption while attempting to pause for some time before invoking the RTM service back",
                                                e );
         }
      }
      
      Log.d( TAG, "Invoker running at " + new Date() );
      
      // We compute the URI
      final StringBuffer requestUri = computeRequestUri( params );
      final HttpGet request = new HttpGet( requestUri.toString() );
      
      // FIX: This line caused RTM to return HTTP code 400 - Bad request
      // request.setHeader( new BasicHeader( HTTP.CHARSET_PARAM, ENCODING ) );
      
      Element result;
      
      try
      {
         final String methodUri = request.getRequestLine().getUri();
         Log.i( TAG, "Executing the method:" + methodUri );
         
         final HttpResponse response = client.execute( host, request );
         
         final int statusCode = response.getStatusLine().getStatusCode();
         
         if ( statusCode != HttpStatus.SC_OK )
         {
            Log.e( TAG, "Method failed: " + response.getStatusLine() );
            throw new ServiceInternalException( "method failed: "
               + response.getStatusLine() );
         }
         
         // Inflate any responses compressed with gzip
         final HttpEntity entity = response.getEntity();
         final Header encoding = entity.getContentEncoding();
         
         if ( encoding != null )
         {
            for ( HeaderElement element : encoding.getElements() )
            {
               if ( element.getName().equalsIgnoreCase( ENCODING_GZIP ) )
               {
                  response.setEntity( new InflatingEntity( response.getEntity() ) );
                  break;
               }
            }
         }
         
         // THINK: this method is depreciated, but the only way to get the body
         // as a string, without consuming
         // the body input stream: the HttpMethodBase issues a warning but does
         // not let you call the
         // "setResponseStream()" method!
         final String responseBodyAsString = EntityUtils.toString( response.getEntity() );
         Log.i( TAG, "  Invocation response:\r\n" + responseBodyAsString );
         
         response.getEntity().consumeContent();
         
         final InputStream respContentStream = new ByteArrayInputStream( responseBodyAsString.getBytes() );
         final Document responseDoc = builder.parse( respContentStream );
         final Element wrapperElt = responseDoc.getDocumentElement();
         
         if ( !wrapperElt.getNodeName().equals( "rsp" ) )
         {
            throw new ServiceInternalException( "unexpected response returned by RTM service: "
               + responseBodyAsString );
         }
         else
         {
            String stat = wrapperElt.getAttribute( "stat" );
            
            if ( stat.equals( "fail" ) )
            {
               Node errElt = wrapperElt.getFirstChild();
               while ( errElt != null
                  && ( errElt.getNodeType() != Node.ELEMENT_NODE || !errElt.getNodeName()
                                                                           .equals( "err" ) ) )
               {
                  errElt = errElt.getNextSibling();
               }
               if ( errElt == null )
               {
                  throw new ServiceInternalException( "unexpected response returned by RTM service: "
                     + responseBodyAsString );
               }
               else
               {
                  throw new ServiceException( Integer.parseInt( ( (Element) errElt ).getAttribute( "code" ) ),
                                              ( (Element) errElt ).getAttribute( "msg" ) );
               }
            }
            else
            {
               Node dataElt = wrapperElt.getFirstChild();
               while ( dataElt != null
                  && ( dataElt.getNodeType() != Node.ELEMENT_NODE || dataElt.getNodeName()
                                                                            .equals( "transaction" ) == true ) )
               {
                  try
                  {
                     Node nextSibling = dataElt.getNextSibling();
                     if ( nextSibling == null )
                     {
                        break;
                     }
                     else
                     {
                        dataElt = nextSibling;
                     }
                  }
                  catch ( IndexOutOfBoundsException exception )
                  {
                     // Some implementation may throw this exception, instead of
                     // returning a null sibling
                     break;
                  }
               }
               if ( dataElt == null )
               {
                  throw new ServiceInternalException( "unexpected response returned by RTM service: "
                     + responseBodyAsString );
               }
               else
               {
                  result = (Element) dataElt;
               }
            }
         }
      }
      catch ( SSLException e )
      {
         throw new ServiceInternalException( "", e );
      }
      catch ( IOException e )
      {
         throw new ServiceInternalException( "", e );
      }
      catch ( SAXException e )
      {
         throw new ServiceInternalException( "", e );
      }
      finally
      {
      }
      
      lastInvocation = System.currentTimeMillis();
      return result;
   }
   


   final String calcApiSig( Param... params ) throws ServiceInternalException
   {
      try
      {
         digest.reset();
         digest.update( applicationInfo.getSharedSecret().getBytes( ENCODING ) );
         List< Param > sorted = Arrays.asList( params );
         Collections.sort( sorted );
         for ( Param param : sorted )
         {
            digest.update( param.getName().getBytes( ENCODING ) );
            digest.update( param.getValue().getBytes( ENCODING ) );
         }
         return convertToHex( digest.digest() );
      }
      catch ( UnsupportedEncodingException e )
      {
         throw new ServiceInternalException( "cannot hahdle properly the encoding",
                                             e );
      }
   }
   


   private static String convertToHex( byte[] data )
   {
      StringBuffer buf = new StringBuffer();
      for ( int i = 0; i < data.length; i++ )
      {
         int halfbyte = ( data[ i ] >>> 4 ) & 0x0F;
         int two_halfs = 0;
         do
         {
            if ( ( 0 <= halfbyte ) && ( halfbyte <= 9 ) )
               buf.append( (char) ( '0' + halfbyte ) );
            else
               buf.append( (char) ( 'a' + ( halfbyte - 10 ) ) );
            halfbyte = data[ i ] & 0x0F;
         }
         while ( two_halfs++ < 1 );
      }
      return buf.toString();
   }
   
}
