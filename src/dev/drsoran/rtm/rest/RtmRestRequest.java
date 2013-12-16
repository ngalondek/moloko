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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.Param;


public class RtmRestRequest implements IRtmRequest
{
   private final static String REST_SERVICE_URL_POSTFIX = "/services/rest/";
   
   private final static String ENCODING = "UTF-8";
   
   private final String rtmMethod;
   
   private final Collection< Param > parameters;
   
   
   
   public RtmRestRequest( String rtmMethod, Collection< Param > parametersSorted )
   {
      this.rtmMethod = rtmMethod;
      this.parameters = parametersSorted;
   }
   
   
   
   @Override
   public String getRtmMethod()
   {
      return rtmMethod;
   }
   
   
   
   @Override
   public String getMethodExecutionUri()
   {
      try
      {
         final String requestUri = computeRequestUri();
         return requestUri;
      }
      catch ( NoSuchAlgorithmException e )
      {
         throw new RuntimeException( e );
      }
      catch ( UnsupportedEncodingException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   @Override
   public Collection< Param > getParameters()
   {
      return parameters;
   }
   
   
   
   @Override
   public String toString()
   {
      return "RtmRestRequest [rtmMethod=" + rtmMethod + ", parameters="
         + parameters + "]";
   }
   
   
   
   private String computeRequestUri() throws NoSuchAlgorithmException,
                                     UnsupportedEncodingException
   {
      final StringBuilder requestUri = new StringBuilder( REST_SERVICE_URL_POSTFIX );
      
      if ( !parameters.isEmpty() )
      {
         requestUri.append( "?" );
      }
      
      for ( Param param : parameters )
      {
         requestUri.append( param.getName() )
                   .append( "=" )
                   .append( URLEncoder.encode( param.getValue(), ENCODING ) )
                   .append( "&" );
      }
      
      return requestUri.toString();
   }
}
