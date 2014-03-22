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

import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import dev.drsoran.rtm.IConnection;
import dev.drsoran.rtm.ILog;


class HttpUrlConnection implements IConnection
{
   private final ILog log;
   
   private final IReaderFactory readerFactory;
   
   private HttpURLConnection openConnection;
   
   
   
   public HttpUrlConnection( IReaderFactory readerFactory, ILog log )
   {
      this.readerFactory = readerFactory;
      this.log = log;
   }
   
   
   
   @Override
   public Reader execute( String requestUri ) throws IOException
   {
      final URL url;
      try
      {
         url = new URL( requestUri );
      }
      catch ( MalformedURLException e )
      {
         final String message = MessageFormat.format( "Invalid request URI {0}",
                                                      requestUri );
         log.e( getClass(), message, e );
         throw new IOException( message, e );
      }
      
      logRequest( url );
      
      openConnection = (HttpURLConnection) url.openConnection();
      final Reader reader = readerFactory.createReader( openConnection.getInputStream() );
      
      return reader;
   }
   
   
   
   @Override
   public void close()
   {
      disconnectOpenConnection();
   }
   
   
   
   private void disconnectOpenConnection()
   {
      if ( openConnection != null )
      {
         log.d( getClass(),
                MessageFormat.format( "Disconnected from {0}",
                                      openConnection.getURL() ) );
         
         openConnection.disconnect();
         openConnection = null;
      }
   }
   
   
   
   private void logRequest( URL url )
   {
      log.d( getClass(), "Executing the method:" + url.getFile() );
   }
}
