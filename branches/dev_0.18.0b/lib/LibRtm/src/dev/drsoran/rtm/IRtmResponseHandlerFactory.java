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

import java.util.List;

import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmFrob;


public interface IRtmResponseHandlerFactory
{
   IRtmResponseHandler< List< RtmTask > > createRtmTasksResponseHandler();
   
   
   
   IRtmResponseHandler< List< RtmTasksList > > createRtmTaskListsResponseHandler();
   
   
   
   IRtmResponseHandler< RtmTasksList > createRtmTaskListResponseHandler();
   
   
   
   IRtmResponseHandler< List< RtmContact > > createRtmContactsResponseHandler();
   
   
   
   IRtmResponseHandler< List< RtmLocation > > createRtmLocationsResponseHandler();
   
   
   
   IRtmResponseHandler< RtmNote > createRtmNoteResponseHandler();
   
   
   
   IRtmResponseHandler< RtmTimeline > createRtmTimelineResponseHandler();
   
   
   
   IRtmResponseHandler< RtmFrob > createRtmFrobResponseHandler();
   
   
   
   IRtmResponseHandler< RtmSettings > createRtmSettingsResponseHandler();
   
   
   
   IRtmResponseHandler< RtmAuth > createRtmAuthResponseHandler();
   
   
   
   IRtmResponseHandler< Void > createVoidResponseHandler();
}
