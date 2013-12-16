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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.IRtmRequestFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmClientInfo;


public class RtmRestRequestFactory implements IRtmRequestFactory
{
   private final static String ENCODING = "UTF-8";
   
   private final RtmClientInfo clientInfo;
   
   
   
   public RtmRestRequestFactory( RtmClientInfo clientInfo )
   {
      if ( clientInfo == null )
      {
         throw new IllegalArgumentException( "clientInfo" );
      }
      
      this.clientInfo = clientInfo;
   }
   
   
   
   @Override
   public IRtmRequest createRequest( String rtmMethod, Param... params )
   {
      try
      {
         List< Param > parametersSorted = new ArrayList< Param >();
         for ( Param param : params )
         {
            parametersSorted.add( param );
         }
         
         parametersSorted = signRequestParams( parametersSorted );
         Collections.sort( parametersSorted );
         
         return new RtmRestRequest( rtmMethod, parametersSorted );
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
   
   
   
   private List< Param > signRequestParams( List< Param > params ) throws NoSuchAlgorithmException,
                                                                  UnsupportedEncodingException
   {
      params.add( new Param( "auth_token", clientInfo.getAuthToken() ) );
      params.add( new Param( "api_key", clientInfo.getApiKey() ) );
      params.add( new Param( "api_sig", calcApiSig( params ) ) );
      
      return params;
   }
   
   
   
   private String calcApiSig( List< Param > params ) throws NoSuchAlgorithmException,
                                                    UnsupportedEncodingException
   {
      final MessageDigest digest = getDigest();
      
      digest.reset();
      digest.update( clientInfo.getSharedSecret().getBytes( ENCODING ) );
      
      for ( Param param : params )
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
