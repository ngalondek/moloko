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

package dev.drsoran.rtm.sync;

import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.LIST_ID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;


public class RtmTasksSyncHandler implements IRtmSyncHandler
{
   private final Comparator< RtmTask > comparator;
   
   private final IRtmSyncPartner syncPartner;
   
   
   
   public RtmTasksSyncHandler( IRtmSyncPartner syncPartner,
      Comparator< RtmTask > comparator )
   {
      if ( syncPartner == null )
      {
         throw new IllegalArgumentException( "syncPartner" );
      }
      if ( comparator == null )
      {
         throw new IllegalArgumentException( "comparator" );
      }
      
      this.syncPartner = syncPartner;
      this.comparator = comparator;
   }
   
   
   
   @Override
   public RtmSyncResult handleIncomingSync( IRtmContentRepository contentRepository,
                                            long lastInSyncMillisUtc ) throws RtmServiceException
   {
      if ( contentRepository == null )
      {
         throw new IllegalArgumentException( "contentRepository" );
      }
      
      final List< RtmTask > syncPartnerElements = syncPartner.getTasks( lastInSyncMillisUtc );
      final List< RtmTask > rtmElements = contentRepository.tasks_getList( lastInSyncMillisUtc );
      
      sortLists( syncPartnerElements, rtmElements );
      
      for ( int i = 0, count = rtmElements.size(); i < count; i++ )
      {
         final RtmTask rtmTask = rtmElements.get( i );
         final int posPartnerList = Collections.binarySearch( syncPartnerElements,
                                                              rtmTask,
                                                              comparator );
         RtmTask syncPartnerTask = null;
         if ( posPartnerList > -1 )
         {
            syncPartnerTask = syncPartnerElements.get( posPartnerList );
         }
         
         // Check if the RTM element is not deleted
         if ( rtmTask.getDeletedMillisUtc() == RtmConstants.NO_TIME )
         {
            // INSERT: The RTM element is not contained in the partner list.
            if ( syncPartnerTask == null )
            {
               syncPartner.insertTask( rtmTask );
            }
            
            // UPDATE: The RTM element is contained in the partner list.
            else
            {
               syncPartner.updateTask( syncPartnerTask, rtmTask );
            }
         }
         else
         {
            // DELETE: The RTM element is contained in the partner list and is deleted at RTM side.
            if ( syncPartnerTask != null )
            {
               syncPartner.deleteTask( syncPartnerTask );
            }
         }
      }
   }
   
   
   
   @Override
   public RtmSyncResult handleOutgoingSync( IRtmContentEditService contentEditService,
                                            String timelineId,
                                            long lastOutSyncMillis ) throws RtmServiceException
   {
      if ( contentEditService == null )
      {
         throw new IllegalArgumentException( "contentEditService" );
      }
      if ( timelineId == null )
      {
         throw new IllegalArgumentException( "timelineId" );
      }
      
      final Collection< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      final List< RtmTask > syncPartnerElements = syncPartner.getTasks( lastOutSyncMillis );
      
      for ( int i = 0, count = syncPartnerElements.size(); i < count; i++ )
      {
         final RtmTask syncPartnerTask = syncPartnerElements.get( i );
         RtmResponse< List< RtmTask > > rtmResponse = null;
         
         // Check if the partner element is not deleted
         if ( syncPartnerTask.getDeletedMillisUtc() == RtmConstants.NO_TIME )
         {
            // INSERT: The partner element is new.
            if ( syncPartnerTask.getId() == RtmConstants.NO_ID )
            {
               rtmResponse = contentEditService.tasks_add( timelineId,
                                                           syncPartnerTask.getListId(),
                                                           syncPartnerTask.getName() );
               if ( rtmResponse.isTransactional() )
               {
                  transactions.add( rtmResponse.getTransaction() );
               }
            }
            
            // UPDATE: The partner element is known at RTM side.
            else
            {
               final Collection< RtmTransaction > sendModificationsTransactions = sendModifications( contentEditService,
                                                                                                     timelineId,
                                                                                                     syncPartnerTask );
               transactions.addAll( sendModificationsTransactions );
            }
         }
         else if ( syncPartnerTask.getId() != RtmConstants.NO_ID )
         {
            // DELETE: The partner element is deleted and not new.
            rtmResponse = contentEditService.lists_delete( timelineId,
                                                           syncPartnerTask.getId() );
            
         }
         
         if ( rtmResponse != null )
         {
            for ( RtmTask rtmTask : rtmResponse.getElement() )
            {
               // TODO: perform an in sync
               syncPartner.updateTask( syncPartnerTask, rtmTask );
            }
         }
      }
      
      return transactions;
   }
   
   
   
   private Collection< RtmResponse< List<> > > sendModifications( IRtmContentEditService contentEditService,
                                                                  String timelineId,
                                                                  RtmTask syncPartnerTask ) throws RtmServiceException
   {
      final Collection< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      final List< IModification > modifications = syncPartner.getModificationsOfTask( syncPartnerTask.getId() );
      
      for ( IModification modification : modifications )
      {
         final String property = modification.getPropertyName();
         
         if ( LIST_ID.equalsIgnoreCase( property ) )
         {
            final RtmResponse< List< RtmTask >> response = contentEditService.tasks_moveTo( timelineId,
                                                                                            modification.getSyncedValue(),
                                                                                            syncPartnerTask.getListId(),
                                                                                            syncPartnerTask.getTaskSeriesId(),
                                                                                            syncPartnerTask.getId() );
         }
      }
      
      return rtmResponse;
   }
   
   
   
   private void sortLists( List< RtmTask > syncPartnerElements,
                           List< RtmTask > rtmElements )
   {
      Collections.sort( syncPartnerElements, comparator );
      Collections.sort( rtmElements, comparator );
   }
}
