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

import java.text.MessageFormat;
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
         RtmContentHandler< Collection< RtmTask > >
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
         popNestedContentHandler();
      }
   };
   
   private final IRtmContentHandlerListener< RtmContact > participantFinishedHandler = new IRtmContentHandlerListener< RtmContact >()
   {
      @Override
      public void onContentHandled( RtmContact contentElement )
      {
         addParticipant( contentElement );
         popNestedContentHandler();
      }
   };
   
   private CharactersContext charactersContext = CharactersContext.None;
   
   private Attributes taskSeriesAttributes;
   
   private String recurrencePattern;
   
   private boolean isEveryRecurrence;
   
   private Collection< String > tags;
   
   private Collection< RtmNote > notes;
   
   private Collection< RtmContact > participants;
   
   private Attributes taskAttributes;
   
   
   
   public RtmTaskSeriesContentHandler( String listId )
   {
      this( listId, null );
   }
   
   
   
   public RtmTaskSeriesContentHandler( String listId,
      IRtmContentHandlerListener< Collection< RtmTask >> listener )
   {
      super( listener );
      this.listId = listId;
   }
   
   
   
   @Override
   protected void characters( String string ) throws SAXException
   {
      switch ( charactersContext )
      {
         case Recurrence:
            recurrencePattern = string;
            break;
         
         case Tag:
            addTag( string );
            break;
         
         default :
            throw new SAXException( MessageFormat.format( "Unexpected character context {0}",
                                                          charactersContext ) );
      }
      
      charactersContext = CharactersContext.None;
   }
   
   
   
   @Override
   protected void startElement( String qName, Attributes attributes ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         taskSeriesAttributes = XmlAttr.copy( attributes );
      }
      else if ( "rrule".equalsIgnoreCase( qName ) )
      {
         isEveryRecurrence = XmlAttr.getBoolean( attributes, "every" );
         charactersContext = CharactersContext.Recurrence;
      }
      else if ( "tag".equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Tag;
      }
      else if ( "note".equalsIgnoreCase( qName ) )
      {
         pushNestedContentHandler( new RtmNoteContentHandler( noteFinishedHandler ) ).startElement( qName,
                                                                                                    attributes );
      }
      else if ( "contact".equalsIgnoreCase( qName ) )
      {
         pushNestedContentHandler( new RtmContactContentHandler( participantFinishedHandler ) ).startElement( qName,
                                                                                                              attributes );
      }
      else if ( "task".equalsIgnoreCase( qName ) )
      {
         taskAttributes = XmlAttr.copy( attributes );
      }
   }
   
   
   
   @Override
   protected void endElement( String qName ) throws SAXException
   {
      if ( "task".equalsIgnoreCase( qName ) )
      {
         addTask();
         taskAttributes = null;
      }
      else if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         setContentElementAndNotify( tasks );
         taskSeriesAttributes = null;
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
                                        XmlAttr.getStringNotNull( taskSeriesAttributes,
                                                                  "id" ),
                                        XmlAttr.getOptMillisUtc( taskSeriesAttributes,
                                                                 "created" ),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "added" ),
                                        XmlAttr.getOptMillisUtc( taskSeriesAttributes,
                                                                 "modified" ),
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "deleted" ),
                                        listId,
                                        XmlAttr.getOptString( taskSeriesAttributes,
                                                              "location_id",
                                                              RtmConstants.NO_ID ),
                                        XmlAttr.getStringNotNull( taskSeriesAttributes,
                                                                  "name" ),
                                        XmlAttr.getOptString( taskSeriesAttributes,
                                                              "source",
                                                              Strings.EMPTY_STRING ),
                                        XmlAttr.getOptString( taskSeriesAttributes,
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
                                        Strings.nullIfEmpty( XmlAttr.getOptString( taskAttributes,
                                                                                   "estimate",
                                                                                   null ) ),
                                        tags,
                                        notes,
                                        participants );
      tasks.add( task );
   }
}
