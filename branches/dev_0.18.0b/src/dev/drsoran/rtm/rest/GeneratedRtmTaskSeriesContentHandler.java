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

import java.util.ArrayList;
import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.Strings;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;


public class GeneratedRtmTaskSeriesContentHandler extends
         RtmContentHandler< Collection< RtmTask > >
{
   private final RtmTask referenceTask;
   
   private final Collection< RtmTask > tasks = new ArrayList< RtmTask >( 1 );
   
   private Attributes taskSeriesAttributes;
   
   
   
   public GeneratedRtmTaskSeriesContentHandler( RtmTask referenceTask )
   {
      this( referenceTask, null );
   }
   
   
   
   public GeneratedRtmTaskSeriesContentHandler( RtmTask referenceTask,
      IRtmContentHandlerListener< Collection< RtmTask >> listener )
   {
      super( listener );
      this.referenceTask = referenceTask;
   }
   
   
   
   @Override
   public void startElement( String name, Attributes attributes ) throws SAXException
   {
      if ( "task".equalsIgnoreCase( name ) )
      {
         addTask( attributes );
      }
      else if ( "taskseries".equalsIgnoreCase( name ) )
      {
         taskSeriesAttributes = XmlAttr.copy( attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String qName ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         setContentElementAndNotify( tasks );
         taskSeriesAttributes = null;
      }
   }
   
   
   
   private void addTask( Attributes taskAttributes ) throws SAXException
   {
      final RtmTask task = new RtmTask( XmlAttr.getStringNotNull( taskAttributes,
                                                                  "id" ),
                                        XmlAttr.getStringNotNull( taskSeriesAttributes,
                                                                  "id" ),
                                        referenceTask.getCreatedMillisUtc(),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "added" ),
                                        XmlAttr.getOptMillisUtc( taskSeriesAttributes,
                                                                 "modified" ),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "deleted" ),
                                        referenceTask.getListId(),
                                        referenceTask.getLocationId(),
                                        referenceTask.getName(),
                                        referenceTask.getSource(),
                                        referenceTask.getUrl(),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "completed" ),
                                        Priority.fromString( XmlAttr.getStringNotNull( taskAttributes,
                                                                                       "priority" ) ),
                                        XmlAttr.getInt( taskAttributes,
                                                        "postponed" ),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "due" ),
                                        XmlAttr.getBoolean( taskAttributes,
                                                            "has_due_time" ),
                                        referenceTask.getRecurrencePattern(),
                                        referenceTask.isEveryRecurrence(),
                                        Strings.nullIfEmpty( XmlAttr.getOptString( taskAttributes,
                                                                                   "estimate",
                                                                                   null ) ),
                                        new ArrayList< String >( referenceTask.getTags() ),
                                        new ArrayList< RtmNote >( referenceTask.getNotes() ),
                                        new ArrayList< RtmContact >( referenceTask.getParticipants() ) );
      tasks.add( task );
   }
}
