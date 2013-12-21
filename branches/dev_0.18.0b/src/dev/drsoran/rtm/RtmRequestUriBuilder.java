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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dev.drsoran.Strings;


public class RtmRequestUriBuilder
{
   private final static String ENCODING = "UTF-8";
   
   private final String apiKey;
   
   private final String sharedSecret;
   
   private final Collection< Param > params = new ArrayList< Param >();
   
   private String scheme;
   
   private String host;
   
   private int port;
   
   private String serviceUrl;
   
   private String authToken;
   
   
   
   public RtmRequestUriBuilder( String apiKey, String sharedSecret )
   {
      this.apiKey = apiKey;
      this.sharedSecret = sharedSecret;
   }
   
   
   
   public String getApiKey()
   {
      return apiKey;
   }
   
   
   
   public String getSharedSecret()
   {
      return sharedSecret;
   }
   
   
   
   public RtmRequestUriBuilder setHost( RtmConnectionProtocol protocol,
                                        String host )
   {
      this.scheme = protocol.toString();
      this.host = host;
      this.port = protocol.getPort();
      
      return this;
   }
   
   
   
   public String getHost()
   {
      return host;
   }
   
   
   
   public int getPort()
   {
      return port;
   }
   
   
   
   public void setScheme( String scheme )
   {
      this.scheme = scheme;
   }
   
   
   
   public String getScheme()
   {
      return scheme;
   }
   
   
   
   public RtmRequestUriBuilder setAuthToken( String authToken )
   {
      this.authToken = authToken;
      return this;
   }
   
   
   
   public String getAuthToken()
   {
      return authToken;
   }
   
   
   
   public RtmRequestUriBuilder setServiceUrl( String serviceUrl )
   {
      this.serviceUrl = serviceUrl;
      return this;
   }
   
   
   
   public String getServiceUrl()
   {
      return serviceUrl;
   }
   
   
   
   public RtmRequestUriBuilder addParam( Param param )
   {
      params.add( param );
      return this;
   }
   
   
   
   public RtmRequestUriBuilder addAll( Param... param )
   {
      params.addAll( Arrays.asList( param ) );
      return this;
   }
   
   
   
   public Collection< Param > getParams()
   {
      return params;
   }
   
   
   
   public String build()
   {
      final List< Param > parametersSorted = signAndSortParameters();
      
      final StringBuilder requestUri = new StringBuilder();
      
      if ( !Strings.isNullOrEmpty( host ) )
      {
         requestUri.append( scheme ).append( "://" ).append( host );
      }
      
      if ( !Strings.isNullOrEmpty( serviceUrl ) )
      {
         requestUri.append( serviceUrl );
      }
      
      if ( !parametersSorted.isEmpty() )
      {
         requestUri.append( "?" );
      }
      
      for ( Param param : parametersSorted )
      {
         try
         {
            requestUri.append( param.getName() )
                      .append( "=" )
                      .append( URLEncoder.encode( param.getValue(), ENCODING ) )
                      .append( "&" );
         }
         catch ( UnsupportedEncodingException e )
         {
            throw new RuntimeException( e );
         }
      }
      
      return requestUri.toString();
   }
   
   
   
   private List< Param > signAndSortParameters()
   {
      final List< Param > parametersSorted = new ArrayList< Param >( params );
      if ( !Strings.isNullOrEmpty( authToken ) )
      {
         parametersSorted.add( new Param( "auth_token", authToken ) );
      }
      
      parametersSorted.add( new Param( "api_key", apiKey ) );
      parametersSorted.add( new Param( "api_sig", calcApiSig( parametersSorted ) ) );
      
      Collections.sort( parametersSorted );
      return parametersSorted;
   }
   
   
   
   private String calcApiSig( Iterable< Param > params )
   {
      try
      {
         final MessageDigest digest = MessageDigest.getInstance( "md5" );
         
         digest.reset();
         digest.update( sharedSecret.getBytes( ENCODING ) );
         
         for ( Param param : params )
         {
            digest.update( param.getName().getBytes( ENCODING ) );
            digest.update( param.getValue().getBytes( ENCODING ) );
         }
         
         return convertToHex( digest.digest() );
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
