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

import java.util.List;

import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmFrob;


public class RtmRestResponseHandlerFactory implements
         IRtmResponseHandlerFactory
{
   @Override
   public IRtmResponseHandler< List< RtmTask >> createRtmTasksResponseHandler()
   {
      return new RtmRestResponseHandler< List< RtmTask >>( new RtmTaskSeriesListContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< List< RtmTasksList >> createRtmTaskListsResponseHandler()
   {
      return new RtmRestResponseHandler< List< RtmTasksList > >( new XmlCollectionTagContentHandler< RtmTasksList >( "lists",
                                                                                                                     new RtmListContentHandler() ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmTasksList > createRtmTaskListResponseHandler()
   {
      return new RtmRestResponseHandler< RtmTasksList >( new RtmListContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< List< RtmContact >> createRtmContactsResponseHandler()
   {
      return new RtmRestResponseHandler< List< RtmContact > >( new XmlCollectionTagContentHandler< RtmContact >( "contacts",
                                                                                                                 new RtmContactContentHandler() ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< List< RtmLocation >> createRtmLocationsResponseHandler()
   {
      return new RtmRestResponseHandler< List< RtmLocation > >( new XmlCollectionTagContentHandler< RtmLocation >( "locations",
                                                                                                                   new RtmLocationContentHandler() ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmNote > createRtmNoteResponseHandler()
   {
      return new RtmRestResponseHandler< RtmNote >( new RtmNoteContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmTimeline > createRtmTimelineResponseHandler()
   {
      return new RtmRestResponseHandler< RtmTimeline >( new RtmTimelineContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmFrob > createRtmFrobResponseHandler()
   {
      return new RtmRestResponseHandler< RtmFrob >( new RtmFrobContentHandler( null ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmAuth > createRtmAuthResponseHandler()
   {
      return new RtmRestResponseHandler< RtmAuth >( new RtmAuthContentHandler( null ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmSettings > createRtmSettingsResponseHandler()
   {
      return new RtmRestResponseHandler< RtmSettings >( new RtmSettingsContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< Void > createVoidResponseHandler()
   {
      return new RtmRestResponseHandler< Void >( new VoidContentHandler( null ) );
   }
}
