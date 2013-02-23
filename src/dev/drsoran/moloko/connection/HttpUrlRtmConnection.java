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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.util.Strings;


class HttpUrlRtmConnection implements IRtmConnection
{
   private final String scheme;
   
   private final String host;
   
   private final int port;
   
   
   
   public HttpUrlRtmConnection( String scheme, String hostname, int port )
   {
      this.scheme = scheme;
      this.host = hostname;
      this.port = port;
   }
   
   
   
   @Override
   public Reader execute( String requestUri ) throws IOException
   {
      final URL url;
      try
      {
         url = assembleUrl( requestUri );
      }
      catch ( MalformedURLException e )
      {
         MolokoApp.Log.e( getClass(), "Invalid request URI " + requestUri, e );
         throw new IOException( Strings.EMPTY_STRING, e );
      }
      
      logRequest( url );
      
      final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      
      final InputStream inputStream = new BufferedInputStream( urlConnection.getInputStream(),
                                                               4096 );
      
      final Reader decoratedReader = new LoggingReader( new InputStreamReader( inputStream ),
                                                        getClass() );
      
      final Reader reader = new HttpUrlConnectionReader( decoratedReader,
                                                         urlConnection );
      return reader;
   }
   
   
   
   @Override
   public void close()
   {
   }
   
   
   
   private URL assembleUrl( String requestUri ) throws MalformedURLException
   {
      return new URL( scheme, host, port, requestUri );
   }
   
   
   
   private void logRequest( URL url )
   {
      MolokoApp.Log.d( getClass(), "Executing the method:" + url.getFile() );
   }
}
