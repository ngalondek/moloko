/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.rtm.service;

import java.util.List;

import dev.drsoran.rtm.IRtmConnection;
import dev.drsoran.rtm.IRtmConnectionFactory;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmContentRepository implements IRtmContentRepository
{
   private final IRtmConnectionFactory connectionFactory;
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   
   
   public RtmContentRepository( IRtmConnectionFactory connectionFactory,
      IRtmResponseHandlerFactory responseHandlerFactory )
   {
      if ( connectionFactory == null )
      {
         throw new IllegalArgumentException( "connectionFactory" );
      }
      
      if ( responseHandlerFactory == null )
      {
         throw new IllegalArgumentException( "responseHandlerFactory" );
      }
      
      this.connectionFactory = connectionFactory;
      this.responseHandlerFactory = responseHandlerFactory;
   }
   
   
   
   @Override
   public List< RtmContact > contacts_getList() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< List< RtmContact > > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmContactsResponseHandler(),
                                                                                      "rtm.contacts.getList" );
      
      return response.getElement();
   }
   
   
   
   @Override
   public List< RtmTasksList > lists_getList() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< List< RtmTasksList > > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmTaskListsResponseHandler(),
                                                                                        "rtm.lists.getList" );
      
      return response.getElement();
   }
   
   
   
   @Override
   public List< RtmTask > tasks_getList( long lastSyncMillisUtc ) throws RtmServiceException
   {
      if ( lastSyncMillisUtc != RtmConstants.NO_TIME )
      {
         return getTasks( new Param( "last_sync", lastSyncMillisUtc ) );
      }
      
      return getTasks();
   }
   
   
   
   @Override
   public List< RtmTask > tasks_getListByFilter( String filter,
                                                 long lastSyncMillisUtc ) throws RtmServiceException
   {
      if ( Strings.isNullOrEmpty( filter ) )
      {
         throw new IllegalArgumentException( "filter" );
      }
      
      final Param filterParam = new Param( "filter", filter );
      
      if ( lastSyncMillisUtc != RtmConstants.NO_TIME )
      {
         return getTasks( filterParam, new Param( "last_sync",
                                                  lastSyncMillisUtc ) );
      }
      
      return getTasks( filterParam );
   }
   
   
   
   @Override
   public List< RtmTask > tasks_getListByListId( String listId,
                                                 long lastSyncMillisUtc ) throws RtmServiceException
   {
      if ( Strings.isNullOrEmpty( listId ) )
      {
         throw new IllegalArgumentException( "listId" );
      }
      
      final Param listIdParam = new Param( "list_id", listId );
      
      if ( lastSyncMillisUtc != RtmConstants.NO_TIME )
      {
         return getTasks( listIdParam, new Param( "last_sync",
                                                  lastSyncMillisUtc ) );
      }
      
      return getTasks( listIdParam );
   }
   
   
   
   @Override
   public List< RtmLocation > locations_getList() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< List< RtmLocation > > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmLocationsResponseHandler(),
                                                                                       "rtm.locations.getList" );
      
      return response.getElement();
   }
   
   
   
   @Override
   public RtmSettings settings_getList() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmSettings > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmSettingsResponseHandler(),
                                                                               "rtm.settings.getList" );
      
      return response.getElement();
   }
   
   
   
   private List< RtmTask > getTasks( Param... params ) throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< List< RtmTask > > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmTasksResponseHandler(),
                                                                                   "rtm.tasks.getList",
                                                                                   params );
      
      return response.getElement();
   }
}
