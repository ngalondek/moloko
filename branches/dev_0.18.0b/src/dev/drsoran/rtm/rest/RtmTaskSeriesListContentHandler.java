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

import dev.drsoran.rtm.model.RtmTask;


public class RtmTaskSeriesListContentHandler extends
         RtmNestedContentHandler< Collection< RtmTask > >
{
   private final IRtmContentHandlerListener< Collection< RtmTask > > taskSeriesListener = new IRtmContentHandlerListener< Collection< RtmTask > >()
   {
      @Override
      public void onContentHandled( Collection< RtmTask > contentElement )
      {
         addTasks( contentElement );
      }
   };
   
   private final Collection< RtmTask > tasks = new ArrayList< RtmTask >();
   
   private String activeListId;
   
   private boolean expectMultipleLists;
   
   
   
   public RtmTaskSeriesListContentHandler()
   {
      this( null );
   }
   
   
   
   public RtmTaskSeriesListContentHandler(
      IRtmContentHandlerListener< Collection< RtmTask >> listener )
   {
      super( listener );
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         pushContentHandler( new RtmTaskSeriesContentHandler( activeListId,
                                                              taskSeriesListener ) );
      }
      else if ( "deleted".equalsIgnoreCase( qName ) )
      {
         pushContentHandler( new DeletedRtmTaskSeriesContentHandler( activeListId,
                                                                     taskSeriesListener ) );
      }
      else if ( "list".equalsIgnoreCase( qName ) )
      {
         activeListId = XmlAttr.getStringNotNull( attributes, "id" );
      }
      else if ( "tasks".equalsIgnoreCase( qName ) )
      {
         expectMultipleLists = true;
      }
      else
      {
         super.startElement( uri, localName, qName, attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         popContentHandler();
      }
      else if ( "deleted".equalsIgnoreCase( qName ) )
      {
         popContentHandler();
      }
      else if ( "list".equalsIgnoreCase( qName ) )
      {
         activeListId = null;
         
         if ( !expectMultipleLists )
         {
            setContentElementAndNotify( tasks );
         }
      }
      else if ( expectMultipleLists && "tasks".equalsIgnoreCase( qName ) )
      {
         setContentElementAndNotify( tasks );
      }
      else
      {
         super.endElement( uri, localName, qName );
      }
   }
   
   
   
   private void addTasks( Collection< RtmTask > tasks )
   {
      tasks.addAll( tasks );
   }
}
