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

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;


public class RtmSettingsSyncHandler implements IRtmSyncHandler
{
   private final IRtmSyncPartner syncPartner;
   
   
   
   public RtmSettingsSyncHandler( IRtmSyncPartner syncPartner )
   {
      if ( syncPartner == null )
      {
         throw new IllegalArgumentException( "syncPartner" );
      }
      
      this.syncPartner = syncPartner;
   }
   
   
   
   @Override
   public RtmSyncResult handleIncomingSync( IRtmContentRepository contentRepository,
                                            long lastInSyncMillis )
   {
      if ( contentRepository == null )
      {
         throw new IllegalArgumentException( "contentRepository" );
      }
      
      final RtmSettings settings;
      try
      {
         settings = contentRepository.settings_getList();
      }
      catch ( RtmServiceException e )
      {
         return RtmSyncResult.newFailed( e );
      }
      
      syncPartner.updateSettings( syncPartner.getSettings(), settings );
      
      return RtmSyncResult.newSucceeded();
   }
   
   
   
   @Override
   public RtmSyncResult handleOutgoingSync( IRtmContentEditService contentEditService,
                                            String timelineId,
                                            long lastOutSyncMillis )
   {
      throw new UnsupportedOperationException( "Outgoing sync of RtmLocation is not supported." );
   }
}
