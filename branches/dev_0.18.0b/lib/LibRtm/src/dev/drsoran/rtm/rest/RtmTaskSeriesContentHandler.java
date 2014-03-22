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

import static dev.drsoran.rtm.content.ContentProperties.BaseProperties.ID;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.ADDED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.COMPLETED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.CREATED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.DELETED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.DUE_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.ESTIMATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.HAS_DUE_TIME;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.LOCATION_ID;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.MODIFIED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.NAME;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.POSTPONED;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.PRIORITY;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.RECURRENCE_EVERY;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.SOURCE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.URL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTaskSeries;


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
   
   private RtmTaskSeries taskSeries;
   
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
         isEveryRecurrence = XmlAttr.getBoolean( attributes, RECURRENCE_EVERY );
         charactersContext = CharactersContext.Recurrence;
      }
      else if ( "tag".equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Tag;
      }
      else if ( "note".equalsIgnoreCase( qName ) )
      {
         pushNestedContentHandlerAndStartElement( new RtmNoteContentHandler( noteFinishedHandler ),
                                                  qName,
                                                  attributes );
      }
      else if ( "contact".equalsIgnoreCase( qName ) )
      {
         pushNestedContentHandlerAndStartElement( new RtmContactContentHandler( participantFinishedHandler ),
                                                  qName,
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
         setContentElementAndNotify( new ArrayList< RtmTask >( tasks ) );
      }
   }
   
   
   
   @Override
   protected void cleanUpState()
   {
      tasks.clear();
      charactersContext = CharactersContext.None;
      taskSeriesAttributes = null;
      taskSeries = null;
      recurrencePattern = null;
      isEveryRecurrence = false;
      tags = null;
      notes = null;
      participants = null;
      taskAttributes = null;
   }
   
   
   
   private void createTaskSeries() throws SAXException
   {
      taskSeries = new RtmTaskSeries( XmlAttr.getStringNotNull( taskSeriesAttributes,
                                                                ID ),
                                      XmlAttr.getOptMillisUtc( taskSeriesAttributes,
                                                               CREATED_DATE ),
                                      XmlAttr.getOptMillisUtc( taskSeriesAttributes,
                                                               MODIFIED_DATE ),
                                      listId,
                                      XmlAttr.getOptString( taskSeriesAttributes,
                                                            LOCATION_ID,
                                                            RtmConstants.NO_ID ),
                                      XmlAttr.getStringNotNull( taskSeriesAttributes,
                                                                NAME ),
                                      XmlAttr.getOptString( taskSeriesAttributes,
                                                            SOURCE,
                                                            Strings.EMPTY_STRING ),
                                      XmlAttr.getOptString( taskSeriesAttributes,
                                                            URL,
                                                            Strings.EMPTY_STRING ),
                                      recurrencePattern,
                                      isEveryRecurrence,
                                      tags,
                                      notes,
                                      participants );
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
      if ( taskSeries == null )
      {
         createTaskSeries();
      }
      
      final RtmTask task = new RtmTask( taskSeries,
                                        new RtmRawTask( XmlAttr.getStringNotNull( taskAttributes,
                                                                                  ID ),
                                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                                 ADDED_DATE ),
                                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                                 DELETED_DATE ),
                                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                                 COMPLETED_DATE ),
                                                        Priority.fromString( XmlAttr.getStringNotNull( taskAttributes,
                                                                                                       PRIORITY ) ),
                                                        XmlAttr.getInt( taskAttributes,
                                                                        POSTPONED ),
                                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                                 DUE_DATE ),
                                                        XmlAttr.getBoolean( taskAttributes,
                                                                            HAS_DUE_TIME ),
                                                        Strings.nullIfEmpty( XmlAttr.getOptString( taskAttributes,
                                                                                                   ESTIMATE,
                                                                                                   null ) ) ) );
      tasks.add( task );
   }
}
