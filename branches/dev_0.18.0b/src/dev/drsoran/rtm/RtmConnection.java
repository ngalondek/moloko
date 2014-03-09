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
   
   private final RtmRequestUriBuilder requestUriBuilder;
   
   
   
   public RtmConnection( ILog log, IConnectionFactory connectionFactory,
      IRtmRequestFactory requestFactory, IRtmRequestLimiter requestLimiter,
      RtmRequestUriBuilder requestUriBuilder )
   {
      if ( log == null )
      {
         throw new IllegalArgumentException( "log" );
      }
      if ( connectionFactory == null )
      {
         throw new IllegalArgumentException( "connectionFactory" );
      }
      if ( requestFactory == null )
      {
         throw new IllegalArgumentException( "requestFactory" );
      }
      if ( requestLimiter == null )
      {
         throw new IllegalArgumentException( "requestLimiter" );
      }
      if ( requestUriBuilder == null )
      {
         throw new IllegalArgumentException( "requestUriBuilder" );
      }
      
      this.log = log;
      this.connectionFactory = connectionFactory;
      this.requestFactory = requestFactory;
      this.requestLimiter = requestLimiter;
      this.requestUriBuilder = requestUriBuilder;
   }
   
   
   
   @Override
   public < T > RtmResponse< T > executeMethod( IRtmResponseHandler< T > responseHandler,
                                                String rtmMethod,
                                                Param... params ) throws RtmServiceException
   {
      final IRtmRequest request = requestFactory.createRequest( rtmMethod,
                                                                requestUriBuilder.addAll( params ) );
      log.d( getClass(), MessageFormat.format( "Request [{0}] send at {1}",
                                               request,
                                               new Date() ) );
      
      requestLimiter.obeyRtmRequestLimit();
      
      Reader responseReader = null;
      IConnection connection = null;
      
      try
      {
         connection = createConnection();
         responseReader = connection.execute( request.getMethodExecutionUri() );
         
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
      final IConnection connection = connectionFactory.createConnection();
      return connection;
   }
}
