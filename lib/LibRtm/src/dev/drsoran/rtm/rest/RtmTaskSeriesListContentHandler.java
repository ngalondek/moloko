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
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;


public class RtmTaskSeriesListContentHandler extends
         RtmContentHandler< List< RtmTask > >
{
   private final IRtmContentHandlerListener< Collection< RtmTask > > taskSeriesListener = new IRtmContentHandlerListener< Collection< RtmTask > >()
   {
      @Override
      public void onContentHandled( Collection< RtmTask > contentElement ) throws SAXException
      {
         addTasks( contentElement );
         popNestedContentHandler();
      }
   };
   
   private final IRtmCalendarProvider calendarProvider;
   
   private String activeListId;
   
   private boolean expectMultipleLists;
   
   
   
   public RtmTaskSeriesListContentHandler( IRtmCalendarProvider calendarProvider )
   {
      this( calendarProvider, null );
   }
   
   
   
   public RtmTaskSeriesListContentHandler(
      IRtmCalendarProvider calendarProvider,
      IRtmContentHandlerListener< List< RtmTask >> listener )
   {
      super( listener );
      
      this.calendarProvider = calendarProvider;
      setContentElement( new ArrayList< RtmTask >() );
   }
   
   
   
   @Override
   public void startElement( String qName, Attributes attributes ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         pushNestedContentHandlerAndStartElement( new RtmTaskSeriesContentHandler( activeListId,
                                                                                   taskSeriesListener ),
                                                  qName,
                                                  attributes );
      }
      else if ( "deleted".equalsIgnoreCase( qName ) )
      {
         pushNestedContentHandlerAndStartElement( new DeletedRtmTaskSeriesContentHandler( activeListId,
                                                                                          calendarProvider,
                                                                                          taskSeriesListener ),
                                                  qName,
                                                  attributes );
      }
      else if ( "generated".equalsIgnoreCase( qName ) )
      {
         final RtmTask referenceTask = getReferenceTask();
         
         pushNestedContentHandlerAndStartElement( new GeneratedRtmTaskSeriesContentHandler( referenceTask,
                                                                                            taskSeriesListener ),
                                                  qName,
                                                  attributes );
      }
      else if ( "list".equalsIgnoreCase( qName ) )
      {
         activeListId = XmlAttr.getStringNotNull( attributes, "id" );
      }
      else if ( "tasks".equalsIgnoreCase( qName ) )
      {
         expectMultipleLists = true;
      }
   }
   
   
   
   @Override
   public void endElement( String qName ) throws SAXException
   {
      if ( "list".equalsIgnoreCase( qName ) )
      {
         activeListId = null;
         
         if ( !expectMultipleLists )
         {
            notifyContentElementSet();
         }
      }
      else if ( expectMultipleLists && "tasks".equalsIgnoreCase( qName ) )
      {
         notifyContentElementSet();
      }
   }
   
   
   
   @Override
   protected void cleanUpState()
   {
      activeListId = null;
      expectMultipleLists = false;
   }
   
   
   
   private RtmTask getReferenceTask() throws SAXException
   {
      if ( getContentElement().isEmpty() )
      {
         throw new SAXException( "Expected at least one reference task for generated task" );
      }
      
      return getContentElement().get( 0 );
   }
   
   
   
   private void addTasks( Collection< RtmTask > tasks ) throws SAXException
   {
      for ( RtmTask rtmTask : tasks )
      {
         getContentElement().add( rtmTask );
      }
   }
}
