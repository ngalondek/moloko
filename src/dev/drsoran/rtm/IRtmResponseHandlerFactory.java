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

package dev.drsoran.rtm;

import java.util.Collection;

import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmFrob;


public interface IRtmResponseHandlerFactory
{
   IRtmResponseHandler< Collection< RtmTask > > createRtmTasksResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< Collection< RtmTasksList > > createRtmTaskListsResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< RtmTasksList > createRtmTaskListResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< Collection< RtmContact > > createRtmContactsResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< Collection< RtmLocation > > createRtmLocationsResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< RtmNote > createRtmNoteResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< RtmTimeline > createRtmTimelineResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< RtmFrob > createRtmFrobResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< RtmAuth > createRtmAuthResponseHandler( IRtmRequest request );
   
   
   
   IRtmResponseHandler< Void > createVoidResponseHandler( IRtmRequest request );
}
