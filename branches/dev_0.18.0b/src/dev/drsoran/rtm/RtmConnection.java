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
   private final ILog log;
   
   private final IConnectionFactory connectionFactory;
   
   private final IRtmRequestLimiter requestLimiter;
   
   private final IRtmRequestFactory requestFactory;
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   private final RtmRequestUriBuilder requestUriBuilder;
   
   
   
   public RtmConnection( ILog log, IConnectionFactory connectionFactory,
      IRtmRequestFactory requestFactory,
      IRtmResponseHandlerFactory responseHandlerFactory,
      IRtmRequestLimiter requestLimiter, RtmRequestUriBuilder requestUriBuilder )
   {
      this.log = log;
      this.connectionFactory = connectionFactory;
      this.requestFactory = requestFactory;
      this.responseHandlerFactory = responseHandlerFactory;
      this.requestLimiter = requestLimiter;
      this.requestUriBuilder = requestUriBuilder;
   }
   
   
   
   @Override
   public < T > RtmResponse< T > executeMethod( Class< T > returnType,
                                                String rtmMethod,
                                                Param... params ) throws RtmServiceException
   {
      final IRtmRequest request = requestFactory.createRequest( rtmMethod,
                                                                requestUriBuilder.addAll( params ) );
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
      final IConnection connection = connectionFactory.createConnection( requestUriBuilder.getScheme(),
                                                                         requestUriBuilder.getHost(),
                                                                         requestUriBuilder.getPort() );
      return connection;
   }
}
