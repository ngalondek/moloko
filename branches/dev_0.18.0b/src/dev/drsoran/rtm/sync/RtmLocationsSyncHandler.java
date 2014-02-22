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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;


public class RtmLocationsSyncHandler implements IRtmSyncHandler
{
   private final Comparator< RtmLocation > comparator;
   
   private final IRtmSyncPartner syncPartner;
   
   
   
   public RtmLocationsSyncHandler( IRtmSyncPartner syncPartner,
      Comparator< RtmLocation > comparator )
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
      
      final List< RtmLocation > rtmElements;
      try
      {
         rtmElements = contentRepository.locations_getList();
      }
      catch ( RtmServiceException e )
      {
         return RtmSyncResult.newFailed( e );
      }
      
      final List< RtmLocation > syncPartnerLocations = syncPartner.getLocations();
      
      sortLists( syncPartnerLocations, rtmElements );
      
      for ( int i = 0, count = rtmElements.size(); i < count; i++ )
      {
         final RtmLocation rtmLocation = rtmElements.get( i );
         final int posPartnerLocation = Collections.binarySearch( syncPartnerLocations,
                                                                  rtmLocation,
                                                                  comparator );
         RtmLocation syncPartnerLocation = null;
         if ( posPartnerLocation > -1 )
         {
            syncPartnerLocation = syncPartnerLocations.get( posPartnerLocation );
         }
         
         // INSERT: The server element is not contained in the partner list.
         if ( syncPartnerLocation == null )
         {
            syncPartner.insertLocation( rtmLocation );
         }
         
         // UPDATE: The RTM element is contained in the partner list.
         else
         {
            syncPartner.updateLocation( syncPartnerLocation, rtmLocation );
            syncPartnerLocations.remove( posPartnerLocation );
         }
         
         // DELETE: The partner list elements are and no longer at RTM side.
         for ( RtmLocation untouchedLocation : syncPartnerLocations )
         {
            syncPartner.deleteLocation( untouchedLocation );
         }
      }
      
      return RtmSyncResult.newSucceeded();
   }
   
   
   
   @Override
   public RtmSyncResult handleOutgoingSync( IRtmContentEditService contentEditService,
                                            String timelineId,
                                            long lastOutSyncMillis )
   {
      throw new UnsupportedOperationException( "Outgoing sync of RtmLocation is not supported." );
   }
   
   
   
   private void sortLists( List< RtmLocation > syncPartnerElements,
                           List< RtmLocation > rtmElements )
   {
      Collections.sort( syncPartnerElements, comparator );
      Collections.sort( rtmElements, comparator );
   }
}
