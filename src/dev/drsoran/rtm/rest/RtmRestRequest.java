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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmClientInfo;


public class RtmRestRequest implements IRtmRequest
{
   private final static String REST_SERVICE_URL_POSTFIX = "/services/rest/";
   
   private final static String ENCODING = "UTF-8";
   
   private final static String API_SIG_PARAM = "api_sig";
   
   private final RtmClientInfo clientInfo;
   
   private final String rtmMethod;
   
   private final Collection< Param > parameters;
   
   
   
   public RtmRestRequest( RtmClientInfo clientInfo, String rtmMethod,
      Collection< Param > parametersSorted )
   {
      this.clientInfo = clientInfo;
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
      
      requestUri.append( API_SIG_PARAM ).append( "=" ).append( calcApiSig() );
      
      return requestUri.toString();
   }
   
   
   
   private String calcApiSig() throws NoSuchAlgorithmException,
                              UnsupportedEncodingException
   {
      final MessageDigest digest = getDigest();
      
      digest.reset();
      digest.update( clientInfo.getSharedSecret().getBytes( ENCODING ) );
      
      for ( Param param : parameters )
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
   
   
   
   private MessageDigest getDigest() throws NoSuchAlgorithmException
   {
      return MessageDigest.getInstance( "md5" );
   }
}
