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

import java.util.Collection;

import android.util.Xml;
import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmFrob;


public class RtmRestResponseHandlerFactory implements
         IRtmResponseHandlerFactory
{
   @Override
   public IRtmResponseHandler< Collection< RtmTask >> createRtmTasksResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< Collection< RtmTask >>( Xml.newPullParser(),
                                                                 new RtmTaskSeriesListContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< Collection< RtmTasksList >> createRtmTaskListsResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< Collection< RtmTasksList > >( Xml.newPullParser(),
                                                                       new CollectionContentHandler< RtmTasksList >( "lists",
                                                                                                                     new RtmListContentHandler() ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmTasksList > createRtmTaskListResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< RtmTasksList >( Xml.newPullParser(),
                                                         new RtmListContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< Collection< RtmContact >> createRtmContactsResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< Collection< RtmContact > >( Xml.newPullParser(),
                                                                     new CollectionContentHandler< RtmContact >( "contacts",
                                                                                                                 new RtmContactContentHandler() ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< Collection< RtmLocation >> createRtmLocationsResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< Collection< RtmLocation > >( Xml.newPullParser(),
                                                                      new CollectionContentHandler< RtmLocation >( "locations",
                                                                                                                   new RtmLocationContentHandler() ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmNote > createRtmNoteResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< RtmNote >( Xml.newPullParser(),
                                                    new RtmNoteContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmTimeline > createRtmTimelineResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< RtmTimeline >( Xml.newPullParser(),
                                                        new RtmTimelineContentHandler() );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmFrob > createRtmFrobResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< RtmFrob >( Xml.newPullParser(),
                                                    new RtmFrobContentHandler( null ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< RtmAuth > createRtmAuthResponseHandler( IRtmRequest request )
   {
      return new RtmRestResponseHandler< RtmAuth >( Xml.newPullParser(),
                                                    new RtmAuthContentHandler( null ) );
   }
   
   
   
   @Override
   public IRtmResponseHandler< Void > createVoidResponseHandler( IRtmRequest request )
   {
      // TODO Auto-generated method stub
      
   }
}
