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

import android.util.Xml;
import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmFrob;


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
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmTasksList[] >( Xml.newPullParser(),
                                                                                         new ArrayContentHandler< RtmTasksList >( "lists",
                                                                                                                                  new RtmListContentHandler() ) );
      }
      else if ( resultType == RtmContact[].class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmContact[] >( Xml.newPullParser(),
                                                                                       new ArrayContentHandler< RtmContact >( "contacts",
                                                                                                                              new RtmContactContentHandler() ) );
      }
      else if ( resultType == RtmLocation[].class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmLocation[] >( Xml.newPullParser(),
                                                                                        new ArrayContentHandler< RtmLocation >( "locations",
                                                                                                                                new RtmLocationContentHandler() ) );
      }
      else if ( resultType == RtmFrob.class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmFrob >( Xml.newPullParser(),
                                                                                  new RtmFrobContentHandler( null ) );
      }
      else if ( resultType == RtmAuth.class )
      {
         return (IRtmResponseHandler< T >) new RtmRestResponseHandler< RtmAuth >( Xml.newPullParser(),
                                                                                  new RtmAuthContentHandler( null ) );
      }
      
      throw new IllegalArgumentException( MessageFormat.format( "Unknown response data type {0}",
                                                                resultType.getName() ) );
   }
}
