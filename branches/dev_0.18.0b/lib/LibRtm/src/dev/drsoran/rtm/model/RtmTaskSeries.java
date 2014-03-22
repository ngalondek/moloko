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

package dev.drsoran.rtm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties;


public class RtmTaskSeries
{
   private final String id;
   
   private final long createdMillisUtc;
   
   private final long modifiedMillisUtc;
   
   private final String listId;
   
   private final String locationId;
   
   private final String name;
   
   private final String source;
   
   private final String url;
   
   private final String recurrencePattern;
   
   private final boolean isEveryRecurrence;
   
   private final Collection< String > tags;
   
   private final Collection< RtmContact > participants;
   
   private Collection< RtmNote > notes;
   
   private String recurrenceSentence;
   
   
   
   public RtmTaskSeries( String id, long createdMillisUtc,
      long modifiedMillisUtc, String listId, String locationId, String name,
      String source, String url, String recurrencePattern,
      boolean isEveryRecurrence, Collection< String > tags,
      Collection< RtmNote > notes, Collection< RtmContact > participants )
   {
      if ( createdMillisUtc == RtmConstants.NO_TIME )
      {
         throw new IllegalArgumentException( "createdMillisUtc" );
      }
      if ( listId == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "listId" );
      }
      if ( Strings.isNullOrEmpty( name ) )
      {
         throw new IllegalArgumentException( "name" );
      }
      if ( Strings.EMPTY_STRING.equals( locationId ) )
      {
         throw new IllegalArgumentException( "locationId" );
      }
      
      this.id = id;
      this.createdMillisUtc = createdMillisUtc;
      this.modifiedMillisUtc = modifiedMillisUtc;
      this.listId = listId;
      this.locationId = locationId;
      this.name = name;
      this.source = source;
      this.url = Strings.emptyIfNull( url );
      this.recurrencePattern = recurrencePattern;
      this.isEveryRecurrence = isEveryRecurrence;
      this.tags = tags;
      this.notes = notes;
      this.participants = participants;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public String getSource()
   {
      return source;
   }
   
   
   
   public String getUrl()
   {
      return url;
   }
   
   
   
   public long getCreatedMillisUtc()
   {
      return createdMillisUtc;
   }
   
   
   
   public long getModifiedMillisUtc()
   {
      return modifiedMillisUtc;
   }
   
   
   
   public String getRecurrencePattern()
   {
      return recurrencePattern;
   }
   
   
   
   public String getRecurrenceSentence()
   {
      return recurrenceSentence;
   }
   
   
   
   public void setRecurrenceSentence( String recurrenceSentence )
   {
      this.recurrenceSentence = recurrenceSentence;
   }
   
   
   
   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
   }
   
   
   
   public Collection< String > getTags()
   {
      return tags != null ? tags : Collections.< String > emptyList();
   }
   
   
   
   public String getTagsJoined()
   {
      return Strings.join( RtmTaskProperties.TAGS_SEPARATOR, getTags() );
   }
   
   
   
   public Collection< ? extends RtmNote > getNotes()
   {
      return notes != null ? notes : Collections.< RtmNote > emptyList();
   }
   
   
   
   public RtmNote getNote( String noteId )
   {
      for ( RtmNote note : getNotes() )
      {
         if ( note.getId().equals( noteId ) )
         {
            return note;
         }
      }
      
      return null;
   }
   
   
   
   public boolean hasNote( String noteId )
   {
      return getNote( noteId ) != null;
   }
   
   
   
   public void addNote( RtmNote note )
   {
      if ( note == null )
      {
         throw new IllegalArgumentException( "note" );
      }
      
      if ( notes == null )
      {
         notes = new ArrayList< RtmNote >();
      }
      
      notes.add( note );
   }
   
   
   
   public Collection< RtmContact > getParticipants()
   {
      return participants != null ? participants
                                 : Collections.< RtmContact > emptyList();
   }
   
   
   
   public RtmContact getParticipant( String participantId )
   {
      for ( RtmContact participant : getParticipants() )
      {
         if ( participant.getId().equals( participantId ) )
         {
            return participant;
         }
      }
      
      return null;
   }
   
   
   
   public String getLocationId()
   {
      return locationId;
   }
   
   
   
   public String getListId()
   {
      return listId;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmTaskSeries [id=%s, name=%s, listId=%s, notes=%s]",
                            id,
                            name,
                            listId,
                            notes != null ? notes.size() : 0 );
   }
}
