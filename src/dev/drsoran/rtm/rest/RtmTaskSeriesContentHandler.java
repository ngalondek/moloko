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
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;


public class RtmTaskSeriesContentHandler extends
         RtmNestedContentHandler< Collection< RtmTask > >
{
   private enum CharactersContext
   {
      None, Recurrence, Tag
   }
   
   private final String listId;
   
   private final Collection< RtmTask > tasks = new ArrayList< RtmTask >( 1 );
   
   private final IRtmContentHandlerListener< RtmNote > noteFinishedHandler = new IRtmContentHandlerListener< RtmNote >()
   {
      @Override
      public void onContentHandled( RtmNote contentElement )
      {
         addNote( contentElement );
         popContentHandler();
      }
   };
   
   private final IRtmContentHandlerListener< RtmContact > participantFinishedHandler = new IRtmContentHandlerListener< RtmContact >()
   {
      @Override
      public void onContentHandled( RtmContact contentElement )
      {
         addParticipant( contentElement );
         popContentHandler();
      }
   };
   
   private CharactersContext charactersContext = CharactersContext.None;
   
   private Attributes taskSeriesIdAttributes;
   
   private String taskSeriesId;
   
   private String recurrencePattern;
   
   private boolean isEveryRecurrence;
   
   private Collection< String > tags;
   
   private Collection< RtmNote > notes;
   
   private Collection< RtmContact > participants;
   
   private Attributes taskAttributes;
   
   
   
   public RtmTaskSeriesContentHandler( String listId )
   {
      this.listId = listId;
   }
   
   
   
   protected RtmTaskSeriesContentHandler( String listId,
      IRtmContentHandlerListener< Collection< RtmTask >> listener )
   {
      super( listener );
      this.listId = listId;
   }
   
   
   
   @Override
   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      switch ( charactersContext )
      {
         case Recurrence:
            recurrencePattern = new String( ch, start, length );
            break;
         
         case Tag:
            addTag( new String( ch, start, length ) );
            break;
         
         default :
            super.characters( ch, start, length );
            break;
      }
      
      charactersContext = CharactersContext.None;
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( localName ) )
      {
         taskSeriesIdAttributes = attributes;
         taskSeriesId = XmlAttr.getStringNotNull( taskSeriesIdAttributes, "id" );
      }
      else if ( "rrule".equalsIgnoreCase( localName ) )
      {
         isEveryRecurrence = XmlAttr.getBoolean( attributes, "every" );
         charactersContext = CharactersContext.Recurrence;
      }
      else if ( "tag".equalsIgnoreCase( localName ) )
      {
         charactersContext = CharactersContext.Tag;
      }
      else if ( "note".equalsIgnoreCase( localName ) )
      {
         pushContentHandler( new RtmNoteContentHandler( taskSeriesId,
                                                        noteFinishedHandler ) );
         super.startElement( uri, localName, qName, attributes );
      }
      else if ( "contact".equalsIgnoreCase( localName ) )
      {
         pushContentHandler( new RtmContactContentHandler( participantFinishedHandler ) );
         super.startElement( uri, localName, qName, attributes );
      }
      else if ( "task".equalsIgnoreCase( localName ) )
      {
         taskAttributes = attributes;
      }
      else
      {
         super.startElement( uri, localName, qName, attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( "task".equalsIgnoreCase( localName ) )
      {
         addTask();
      }
      else if ( "taskseries".equalsIgnoreCase( localName ) )
      {
         setContentElementAndNotify( tasks );
      }
      else
      {
         super.endElement( uri, localName, qName );
      }
   }
   
   
   
   private void addTag( final String tag )
   {
      if ( tags == null )
      {
         tags = new ArrayList< String >();
      }
      
      tags.add( tag );
   }
   
   
   
   private void addNote( RtmNote note )
   {
      if ( notes == null )
      {
         notes = new ArrayList< RtmNote >();
      }
      
      notes.add( note );
   }
   
   
   
   private void addParticipant( RtmContact participant )
   {
      if ( participants == null )
      {
         participants = new ArrayList< RtmContact >();
      }
      
      participants.add( participant );
   }
   
   
   
   private void addTask() throws SAXException
   {
      final RtmTask task = new RtmTask( XmlAttr.getStringNotNull( taskAttributes,
                                                                  "id" ),
                                        taskSeriesId,
                                        XmlAttr.getOptMillisUtc( taskSeriesIdAttributes,
                                                                 "created" ),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "added" ),
                                        XmlAttr.getOptMillisUtc( taskSeriesIdAttributes,
                                                                 "modified" ),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "deleted" ),
                                        listId,
                                        XmlAttr.getOptString( taskSeriesIdAttributes,
                                                              "location_id",
                                                              RtmConstants.NO_ID ),
                                        XmlAttr.getStringNotNull( taskSeriesIdAttributes,
                                                                  "name" ),
                                        XmlAttr.getOptString( taskSeriesIdAttributes,
                                                              "source",
                                                              Strings.EMPTY_STRING ),
                                        XmlAttr.getOptString( taskSeriesIdAttributes,
                                                              "url",
                                                              Strings.EMPTY_STRING ),
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
                                        recurrencePattern,
                                        isEveryRecurrence,
                                        XmlAttr.getOptString( taskAttributes,
                                                              "estimation",
                                                              null ),
                                        tags,
                                        notes,
                                        participants );
      tasks.add( task );
   }
}
