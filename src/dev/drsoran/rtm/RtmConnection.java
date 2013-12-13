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
import java.text.MessageFormat;
import java.util.Date;

import dev.drsoran.moloko.ILog;


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
   
   private final ILog log;
   
   private final IConnectionFactory connectionFactory;
   
   private final IRtmRequestLimiter requestLimiter;
   
   private final IRtmRequestFactory requestFactory;
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   private final RtmClientInfo clientInfo;
   
   
   
   public RtmConnection( ILog log, IConnectionFactory connectionFactory,
      IRtmRequestFactory requestFactory,
      IRtmResponseHandlerFactory responseHandlerFactory,
      IRtmRequestLimiter requestLimiter, RtmClientInfo clientInfo )
   {
      this.log = log;
      this.connectionFactory = connectionFactory;
      this.requestFactory = requestFactory;
      this.responseHandlerFactory = responseHandlerFactory;
      this.requestLimiter = requestLimiter;
      this.clientInfo = clientInfo;
   }
   
   
   
   @Override
   public < T > T executeMethod( Class< T > returnType,
                                 String rtmMethod,
                                 Param... params ) throws RtmServiceException
   {
      final IRtmRequest request = requestFactory.createRequest( rtmMethod,
                                                                params );
      requestLimiter.obeyRtmRequestLimit();
      
      log.d( getClass(), MessageFormat.format( "Request [{0}] send at {1}",
                                               request,
                                               new Date() ) );
      
      Reader responseReader = null;
      IConnection connection = null;
      
      try
      {
         connection = createConnection();
         responseReader = connection.execute( request.getMethodExecutionUri() );
         
         final IRtmResponseHandler< T > responseHandler = responseHandlerFactory.createResponseHandler( request,
                                                                                                        returnType );
         return responseHandler.handleResponse( responseReader );
      }
      catch ( IOException e )
      {
         throw new RtmServiceException( e.getLocalizedMessage(), e );
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
               log.e( getClass(), "Failed to close response reader.", e );
            }
         }
         
         if ( connection != null )
         {
            connection.close();
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
}
