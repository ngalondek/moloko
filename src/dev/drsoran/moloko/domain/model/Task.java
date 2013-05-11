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

package dev.drsoran.moloko.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.text.TextUtils;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.util.Strings;


public class Task extends LifeTimeManaged
{
   private final long id;
   
   private final long addedMillisUtc;
   
   private long listId;
   
   private String listName;
   
   private long locationId;
   
   private String locationName;
   
   private String name;
   
   private String source = Strings.EMPTY_STRING;
   
   private String url = Strings.EMPTY_STRING;
   
   private long completedMillisUtc = Constants.NO_TIME;
   
   private Priority priority = Priority.None;
   
   private int numPostponed;
   
   private Due due;
   
   private Recurrence recurrence;
   
   private Estimation estimation;
   
   private Collection< String > tags;
   
   private Collection< Note > notes;
   
   private Collection< Participant > participants;
   
   
   
   public Task( long id, long createdMillisUtc, long addedMillisUtc )
   {
      super( createdMillisUtc );
      
      this.id = id;
      this.completedMillisUtc = createdMillisUtc;
      this.addedMillisUtc = addedMillisUtc;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public void setName( String name )
   {
      if ( TextUtils.isEmpty( name ) )
      {
         throw new IllegalArgumentException( "name" );
      }
      
      this.name = name;
   }
   
   
   
   public String getSource()
   {
      return source;
   }
   
   
   
   public void setSource( String source )
   {
      if ( source == null )
      {
         throw new IllegalArgumentException( "source" );
      }
      
      this.source = source;
   }
   
   
   
   public String getUrl()
   {
      return url;
   }
   
   
   
   public void setUrl( String url )
   {
      if ( url == null )
      {
         throw new IllegalArgumentException( "url" );
      }
      
      this.url = url;
   }
   
   
   
   public long getAddedMillisUtc()
   {
      return addedMillisUtc;
   }
   
   
   
   public long getCompletedMillisUtc()
   {
      return completedMillisUtc;
   }
   
   
   
   public void setCompletedMillisUtc( long completedMillisUtc )
   {
      if ( completedMillisUtc < Constants.NO_TIME )
      {
         throw new IllegalArgumentException( "completedMillisUtc" );
      }
      
      this.completedMillisUtc = completedMillisUtc;
   }
   
   
   
   public boolean isComplete()
   {
      return completedMillisUtc != Constants.NO_TIME;
   }
   
   
   
   public Priority getPriority()
   {
      return priority;
   }
   
   
   
   public void setPriority( Priority priority )
   {
      this.priority = priority;
   }
   
   
   
   public int getPostponedCount()
   {
      return numPostponed;
   }
   
   
   
   public void setPostponedCount( int numPostponed )
   {
      if ( numPostponed < this.numPostponed )
      {
         throw new IllegalArgumentException( "numPostponed" );
      }
      
      this.numPostponed = numPostponed;
   }
   
   
   
   public boolean isPostponed()
   {
      return numPostponed > 0;
   }
   
   
   
   public Due getDue()
   {
      return due;
   }
   
   
   
   public void setDue( Due due )
   {
      if ( due == null )
      {
         throw new IllegalArgumentException( "due" );
      }
      
      this.due = due;
   }
   
   
   
   public Recurrence getRecurrence()
   {
      return recurrence;
   }
   
   
   
   public void setRecurrence( Recurrence recurrence )
   {
      if ( recurrence == null )
      {
         throw new IllegalArgumentException( "recurrence" );
      }
      
      this.recurrence = recurrence;
   }
   
   
   
   public Estimation getEstimation()
   {
      return estimation;
   }
   
   
   
   public void setEstimation( Estimation estimation )
   {
      if ( estimation == null )
      {
         throw new IllegalArgumentException( "estimation" );
      }
      
      this.estimation = estimation;
   }
   
   
   
   public Iterable< String > getTags()
   {
      return tags != null ? tags : Collections.< String > emptyList();
   }
   
   
   
   public void setTags( Collection< String > tags )
   {
      this.tags = tags;
   }
   
   
   
   public Iterable< Note > getNotes()
   {
      return notes != null ? notes : Collections.< Note > emptyList();
   }
   
   
   
   public Note getNote( long noteId )
   {
      for ( Note note : getNotes() )
      {
         if ( note.getId() == noteId )
         {
            return note;
         }
      }
      
      return null;
   }
   
   
   
   public boolean hasNote( long noteId )
   {
      return getNote( noteId ) != null;
   }
   
   
   
   public void setNotes( Iterable< ? extends Note > notes )
   {
      if ( notes == null )
      {
         throw new IllegalArgumentException( "notes" );
      }
      
      if ( this.notes == null )
      {
         ensureNotesContainer();
      }
      else
      {
         this.notes.clear();
      }
      
      addNotes( notes );
   }
   
   
   
   public boolean addNote( Note note )
   {
      if ( note == null )
      {
         throw new IllegalArgumentException( "note" );
      }
      
      ensureNotesContainer();
      return notes.add( note );
   }
   
   
   
   public boolean removeNote( Note note )
   {
      if ( note == null )
      {
         throw new IllegalArgumentException( "note" );
      }
      
      if ( notes != null )
      {
         return notes.remove( note );
      }
      
      return false;
   }
   
   
   
   public Iterable< Participant > getParticipants()
   {
      return participants != null ? participants
                                 : Collections.< Participant > emptyList();
   }
   
   
   
   public boolean addParticipant( Participant participant )
   {
      if ( participant == null )
      {
         throw new IllegalArgumentException( "participant" );
      }
      
      ensureParticipantsContainer();
      return participants.add( participant );
   }
   
   
   
   public Participant getParticipant( long participantId )
   {
      for ( Participant participant : getParticipants() )
      {
         if ( participant.getId() == participantId )
         {
            return participant;
         }
      }
      
      return null;
   }
   
   
   
   public boolean isParticipating( long participantId )
   {
      return getParticipant( participantId ) != null;
   }
   
   
   
   public long getLocationId()
   {
      return locationId;
   }
   
   
   
   public String getLocationName()
   {
      return locationName;
   }
   
   
   
   public boolean isLocated()
   {
      return locationId != Constants.NO_ID;
   }
   
   
   
   public void setLocation( long locationId, String locationName )
   {
      if ( locationId != Constants.NO_ID && locationName == null )
      {
         throw new IllegalArgumentException( "locationName" );
      }
      
      this.locationId = locationId;
      this.locationName = ( locationId != Constants.NO_ID ) ? locationName
                                                           : null;
   }
   
   
   
   public long getListId()
   {
      return listId;
   }
   
   
   
   public String getListName()
   {
      return listName;
   }
   
   
   
   public void setList( long listId, String listName )
   {
      if ( listId < 1 )
      {
         throw new IllegalArgumentException( "listId" );
      }
      
      if ( TextUtils.isEmpty( listName ) )
      {
         throw new IllegalArgumentException( "listName" );
      }
      
      this.listId = listId;
      this.listName = listName;
      
   }
   
   
   
   private void ensureNotesContainer()
   {
      if ( notes == null )
      {
         notes = new ArrayList< Note >();
      }
   }
   
   
   
   private void ensureParticipantsContainer()
   {
      if ( participants == null )
      {
         participants = new ArrayList< Participant >();
      }
   }
   
   
   
   private void addNotes( Iterable< ? extends Note > notes )
   {
      for ( Note note : notes )
      {
         this.notes.add( note );
      }
   }
}
