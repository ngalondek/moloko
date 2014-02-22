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
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;


public class RtmContactsSyncHandler implements IRtmSyncHandler
{
   private final Comparator< RtmContact > comparator;
   
   private final IRtmSyncPartner syncPartner;
   
   
   
   public RtmContactsSyncHandler( IRtmSyncPartner syncPartner,
      Comparator< RtmContact > comparator )
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
      
      final List< RtmContact > rtmContacts;
      try
      {
         rtmContacts = contentRepository.contacts_getList();
      }
      catch ( RtmServiceException e )
      {
         return RtmSyncResult.newFailed( e );
      }
      
      final List< RtmContact > syncPartnerContacts = syncPartner.getContacts();
      
      sortLists( syncPartnerContacts, rtmContacts );
      
      for ( int i = 0, count = rtmContacts.size(); i < count; i++ )
      {
         final RtmContact rtmContact = rtmContacts.get( i );
         final int posPartnerLocation = Collections.binarySearch( syncPartnerContacts,
                                                                  rtmContact,
                                                                  comparator );
         RtmContact syncPartnerContact = null;
         if ( posPartnerLocation > -1 )
         {
            syncPartnerContact = syncPartnerContacts.get( posPartnerLocation );
         }
         
         // INSERT: The server element is not contained in the partner list.
         if ( syncPartnerContact == null )
         {
            syncPartner.insertContact( rtmContact );
         }
         
         // UPDATE: The RTM element is contained in the partner list.
         else
         {
            syncPartner.updateContact( syncPartnerContact, rtmContact );
            syncPartnerContacts.remove( posPartnerLocation );
         }
         
         // DELETE: The partner list elements are and no longer at RTM side.
         for ( RtmContact untouchedContact : syncPartnerContacts )
         {
            syncPartner.deleteContact( untouchedContact );
         }
      }
      
      return RtmSyncResult.newSucceeded();
   }
   
   
   
   @Override
   public RtmSyncResult handleOutgoingSync( IRtmContentEditService contentEditService,
                                            String timelineId,
                                            long lastOutSyncMillis )
   {
      throw new UnsupportedOperationException( "Outgoing sync of RtmContact is not supported." );
   }
   
   
   
   private void sortLists( List< RtmContact > syncPartnerElements,
                           List< RtmContact > rtmElements )
   {
      Collections.sort( syncPartnerElements, comparator );
      Collections.sort( rtmElements, comparator );
   }
}
