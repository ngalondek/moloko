/* 
 *	Copyright (c) 2014 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.rtm.service;

import dev.drsoran.rtm.sync.IRtmSyncPartner;


public interface IRtmService
{
   IRtmAuthenticationService getAuthenticationService();
   
   
   
   IRtmContentRepository getContentRepository( String authToken );
   
   
   
   IRtmContentEditService getContentEditService( String authToken );
   
   
   
   IRtmSyncService getSyncService( IRtmSyncPartner syncPartner, String authToken );
}
