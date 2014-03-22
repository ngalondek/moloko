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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class RtmRequestUriBuilder
{
   private final static String ENCODING = "UTF-8";
   
   private final Set< Param > params = new TreeSet< Param >();
   
   private final String sharedSecret;
   
   private String scheme;
   
   private String host;
   
   private String serviceUrl;
   
   
   
   public RtmRequestUriBuilder( String apiKey, String sharedSecret )
   {
      params.add( new Param( "api_key", apiKey ) );
      this.sharedSecret = sharedSecret;
   }
   
   
   
   public RtmRequestUriBuilder setHost( RtmConnectionProtocol protocol,
                                        String host )
   {
      this.scheme = protocol.toString();
      this.host = host;
      
      return this;
   }
   
   
   
   public RtmRequestUriBuilder setScheme( String scheme )
   {
      this.scheme = scheme;
      return this;
   }
   
   
   
   public RtmRequestUriBuilder setMethod( String method )
   {
      if ( method != null )
      {
         params.add( new Param( "method", method ) );
      }
      else
      {
         params.remove( new Param( "method", Strings.EMPTY_STRING ) );
      }
      
      return this;
   }
   
   
   
   public RtmRequestUriBuilder setAuthToken( String authToken )
   {
      if ( !Strings.isNullOrEmpty( authToken ) )
      {
         params.add( new Param( "auth_token", authToken ) );
      }
      else
      {
         params.remove( new Param( "auth_token", Strings.EMPTY_STRING ) );
      }
      
      return this;
   }
   
   
   
   public RtmRequestUriBuilder setServiceUrl( String serviceUrl )
   {
      this.serviceUrl = serviceUrl;
      return this;
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
   
   
   
   public String build()
   {
      final StringBuilder requestUri = new StringBuilder();
      
      if ( !Strings.isNullOrEmpty( host ) )
      {
         requestUri.append( scheme ).append( "://" ).append( host );
      }
      
      if ( !Strings.isNullOrEmpty( serviceUrl ) )
      {
         requestUri.append( serviceUrl );
      }
      
      params.add( calcApiSig( params ) );
      requestUri.append( "?" );
      
      try
      {
         for ( Iterator< Param > i = params.iterator(); i.hasNext(); )
         {
            final Param param = i.next();
            
            requestUri.append( param.getName() )
                      .append( "=" )
                      .append( URLEncoder.encode( param.getValue(), ENCODING ) );
            
            if ( i.hasNext() )
            {
               requestUri.append( "&" );
            }
         }
      }
      catch ( UnsupportedEncodingException e )
      {
         throw new RuntimeException( e );
      }
      
      return requestUri.toString();
   }
   
   
   
   private Param calcApiSig( Iterable< Param > params )
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
         
         return new Param( "api_sig", convertToHex( digest.digest() ) );
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
