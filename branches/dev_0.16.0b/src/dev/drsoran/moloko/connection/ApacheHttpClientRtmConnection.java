/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.util.Log;


class ApacheHttpClientRtmConnection implements IRtmConnection
{
   private static final String TAG = "Moloko."
      + ApacheHttpClientRtmConnection.class.getSimpleName();
   
   private HttpHost httpHost;
   
   private AndroidHttpClient httpClient;
   
   
   
   public ApacheHttpClientRtmConnection( String scheme, String hostname,
      int port )
   {
      setupHost( scheme, hostname, port );
      setupHttpClient();
   }
   
   
   
   @Override
   public InputStream execute( String requestUri ) throws IOException
   {
      final HttpGet request = new HttpGet( requestUri );
      
      logRequest( request );
      
      AndroidHttpClient.modifyRequestToAcceptGzipResponse( request );
      final HttpResponse response = httpClient.execute( httpHost, request );
      
      checkStatusCode( response );
      
      // logResponse( response );
      
      final InputStream inputStream = AndroidHttpClient.getUngzippedContent( response.getEntity() );
      
      return inputStream;
   }
   
   
   
   @Override
   public void close()
   {
      if ( httpClient != null )
         httpClient.close();
   }
   
   
   
   private void setupHost( String scheme, String hostname, int port )
   {
      httpHost = new HttpHost( hostname, port, scheme );
   }
   
   
   
   private void setupHttpClient()
   {
      httpClient = AndroidHttpClient.newInstance( ConnectionUtil.getHttpUserAgent() );
   }
   
   
   
   private void checkStatusCode( final HttpResponse response ) throws IOException
   {
      final int statusCode = response.getStatusLine().getStatusCode();
      if ( statusCode != HttpStatus.SC_OK )
      {
         final String reason = response.getStatusLine().getReasonPhrase();
         Log.e( TAG, "Method failed: " + reason );
         throw new IOException( "method failed: " + reason );
      }
   }
   
   
   
   private void logRequest( HttpGet request )
   {
      final String methodUri = request.getRequestLine().getUri();
      Log.i( TAG, "Executing the method:" + methodUri );
   }
   
   
   
   private void logResponse( HttpResponse response ) throws IOException
   {
      InputStream inputStream = null;
      BufferedReader bufferedReader = null;
      
      try
      {
         inputStream = AndroidHttpClient.getUngzippedContent( response.getEntity() );
         bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
         
         Log.i( TAG, "  Invocation response:\r\n" );
         
         String line = null;
         
         while ( ( line = bufferedReader.readLine() ) != null )
         {
            Log.i( TAG, line + "\n" );
         }
      }
      finally
      {
         if ( bufferedReader != null )
            bufferedReader.close();
         
         if ( inputStream != null )
            inputStream.close();
      }
   }
}
