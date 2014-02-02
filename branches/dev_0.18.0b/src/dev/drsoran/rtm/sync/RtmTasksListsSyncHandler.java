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

import static dev.drsoran.rtm.content.ContentProperties.RtmTasksListProperties.NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.service.RtmErrorCodes;


public class RtmTasksListsSyncHandler implements IRtmSyncHandler
{
   private final Comparator< RtmTasksList > comparator;
   
   private final IRtmSyncPartner syncPartner;
   
   
   
   public RtmTasksListsSyncHandler( IRtmSyncPartner syncPartner,
      Comparator< RtmTasksList > comparator )
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
                                            long lastInSyncMillis )
   {
      if ( contentRepository == null )
      {
         throw new IllegalArgumentException( "contentRepository" );
      }
      
      try
      {
         final List< RtmTasksList > syncPartnerElements = syncPartner.getTasksLists( RtmConstants.NO_TIME );
         final List< RtmTasksList > rtmElements = contentRepository.lists_getList();
         
         sortLists( syncPartnerElements, rtmElements );
         
         for ( int i = 0, count = rtmElements.size(); i < count; i++ )
         {
            final RtmTasksList rtmTasksList = rtmElements.get( i );
            final int posPartnerList = Collections.binarySearch( syncPartnerElements,
                                                                 rtmTasksList,
                                                                 comparator );
            RtmTasksList syncPartnerList = null;
            if ( posPartnerList > -1 )
            {
               syncPartnerList = syncPartnerElements.get( posPartnerList );
            }
            
            // Check if the RTM element is not deleted
            if ( !rtmTasksList.isDeleted() )
            {
               // INSERT: The server element is not contained in the partner list.
               if ( syncPartnerList == null )
               {
                  syncPartner.insertTasksList( rtmTasksList );
               }
               
               // UPDATE: The RTM element is contained in the partner list.
               else
               {
                  syncPartner.updateTasksList( syncPartnerList, rtmTasksList );
                  syncPartnerElements.remove( posPartnerList );
               }
            }
            else
            {
               // DELETE: The RTM element is contained in the partner list and is deleted at RTM side.
               if ( syncPartnerList != null )
               {
                  syncPartner.deleteTasksList( syncPartnerList );
                  syncPartnerElements.remove( posPartnerList );
               }
            }
            
            // DELETE: The partner list elements are and no longer at RTM side.
            for ( RtmTasksList untouchedSyncPartnerList : syncPartnerElements )
            {
               syncPartner.deleteTasksList( untouchedSyncPartnerList );
            }
         }
         
         return RtmSyncResult.newSucceeded();
      }
      catch ( RtmServiceException e )
      {
         return RtmSyncResult.newFailed( e );
      }
   }
   
   
   
   @Override
   public RtmSyncResult handleOutgoingSync( IRtmContentEditService contentEditService,
                                            String timelineId,
                                            long lastOutSyncMillis )
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
      
      try
      {
         final List< RtmTasksList > syncPartnerElements = syncPartner.getTasksLists( lastOutSyncMillis );
         
         for ( int i = 0, count = syncPartnerElements.size(); i < count; i++ )
         {
            final RtmTasksList syncPartnerTasksList = syncPartnerElements.get( i );
            RtmResponse< RtmTasksList > rtmResponse = null;
            
            try
            {
               // Check if the partner element is not deleted
               if ( !syncPartnerTasksList.isDeleted() )
               {
                  // INSERT: The partner element is new.
                  if ( syncPartnerTasksList.getId() == RtmConstants.NO_ID )
                  {
                     rtmResponse = contentEditService.lists_add( timelineId,
                                                                 syncPartnerTasksList.getName(),
                                                                 syncPartnerTasksList.getSmartFilter() );
                     addIfTransactional( transactions, rtmResponse );
                  }
                  
                  // UPDATE: The partner element is already known at RTM.
                  else
                  {
                     rtmResponse = sendModifications( contentEditService,
                                                      timelineId,
                                                      syncPartnerTasksList );
                     addIfTransactional( transactions, rtmResponse );
                  }
               }
               else if ( syncPartnerTasksList.getId() != RtmConstants.NO_ID )
               {
                  // DELETE: The partner element is deleted and not new.
                  rtmResponse = contentEditService.lists_delete( timelineId,
                                                                 syncPartnerTasksList.getId() );
                  addIfTransactional( transactions, rtmResponse );
               }
            }
            catch ( RtmServiceException e )
            {
               // We may get an invalid list ID error from RTM if the list has been deleted
               // on RTM side by another partner.
               if ( e.getResponseCode() != RtmErrorCodes.LIST_INVALID_ID )
               {
                  throw e;
               }
            }
            
            if ( rtmResponse != null )
            {
               syncPartner.updateTasksList( syncPartnerTasksList,
                                            rtmResponse.getElement() );
            }
         }
         
         return RtmSyncResult.newSucceeded( transactions );
      }
      catch ( Exception e )
      {
         return RtmSyncResult.newFailed( e, transactions );
      }
   }
   
   
   
   private RtmResponse< RtmTasksList > sendModifications( IRtmContentEditService contentEditService,
                                                          String timelineId,
                                                          RtmTasksList syncPartnerTasksList ) throws RtmServiceException
   {
      RtmResponse< RtmTasksList > rtmResponse = null;
      
      final List< IModification > modifications = syncPartner.getModificationsOfTasksList( syncPartnerTasksList.getId() );
      
      for ( IModification modification : modifications )
      {
         if ( NAME.equalsIgnoreCase( modification.getPropertyName() ) )
         {
            rtmResponse = contentEditService.lists_setName( timelineId,
                                                            syncPartnerTasksList.getId(),
                                                            syncPartnerTasksList.getName() );
         }
      }
      
      return rtmResponse;
   }
   
   
   
   private void addIfTransactional( Collection< RtmTransaction > transactions,
                                    RtmResponse< RtmTasksList > rtmResponse )
   {
      if ( rtmResponse.isTransactional() )
      {
         transactions.add( rtmResponse.getTransaction() );
      }
   }
   
   
   
   private void sortLists( List< RtmTasksList > syncPartnerElements,
                           List< RtmTasksList > rtmElements )
   {
      Collections.sort( syncPartnerElements, comparator );
      Collections.sort( rtmElements, comparator );
   }
}
