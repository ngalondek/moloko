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

import java.text.MessageFormat;

import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmRestResponseHandlerFactory implements
         IRtmResponseHandlerFactory
{
   @SuppressWarnings( "unchecked" )
   @Override
   public < T > IRtmResponseHandler< T > createResponseHandler( IRtmRequest request,
                                                                Class< T > resultType )
   {
      if ( resultType == RtmTask[].class )
      {
         return (IRtmResponseHandler< T >) new RtmTaskSeriesListContentHandler();
      }
      else if ( resultType == RtmTasksList[].class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmTasksList[] >( new ArrayContentHandler< RtmTasksList >( "lists",
                                                                                                                                  new RtmListContentHandler() ) );
      }
      else if ( resultType == RtmContact[].class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmContact[] >( new ArrayContentHandler< RtmContact >( "contacts",
                                                                                                                              new RtmContactContentHandler() ) );
      }
      else if ( resultType == RtmLocation[].class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmLocation[] >( new ArrayContentHandler< RtmLocation >( "locations",
                                                                                                                                new RtmLocationContentHandler() ) );
      }
      
      throw new IllegalArgumentException( MessageFormat.format( "Unknown response data type {0}",
                                                                resultType.getName() ) );
   }
}
