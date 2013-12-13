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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.IRtmRequestFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmClientInfo;


public class RtmRestRequestFactory implements IRtmRequestFactory
{
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
      List< Param > parametersSorted = new ArrayList< Param >();
      for ( Param param : params )
      {
         parametersSorted.add( param );
      }
      
      parametersSorted = signRequestParams( parametersSorted );
      Collections.sort( parametersSorted );
      
      return new RtmRestRequest( clientInfo, rtmMethod, parametersSorted );
   }
   
   
   
   private List< Param > signRequestParams( List< Param > params )
   {
      params.add( new Param( "auth_token", clientInfo.getAuthToken() ) );
      params.add( new Param( "api_key", clientInfo.getApiKey() ) );
      
      return params;
   }
}
