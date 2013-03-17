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

import dev.drsoran.moloko.util.Strings;


public class Task extends LifeTimeManaged implements ITask
{
   private final long id;
   
   private final long addedMillisUtc;
   
   private final long listId;
   
   private final String listName;
   
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
   
   private Collection< INote > notes;
   
   private Collection< Participant > participants;
   
   private Location location;
   
   
   
   public Task( long id, String name, long createdMillisUtc,
      long addedMillisUtc, long listId, String listName )
   {
      super( createdMillisUtc );
      
      this.id = id;
      this.name = name;
      this.completedMillisUtc = createdMillisUtc;
      this.addedMillisUtc = addedMillisUtc;
      this.listId = listId;
      this.listName = listName;
   }
   
   
   
   @Override
   public long getId()
   {
      return id;
   }
   
   
   
   @Override
   public String getName()
   {
      return name;
   }
   
   
   
   public void setName( String name )
   {
      if ( name == null )
      {
         throw new IllegalArgumentException( "name" );
      }
      
      this.name = name;
   }
   
   
   
   @Override
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
   
   
   
   @Override
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
   
   
   
   @Override
   public long getAddedMillisUtc()
   {
      return addedMillisUtc;
   }
   
   
   
   @Override
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
   
   
   
   @Override
   public boolean isComplete()
   {
      return completedMillisUtc != Constants.NO_TIME;
   }
   
   
   
   @Override
   public Priority getPriority()
   {
      return priority;
   }
   
   
   
   public void setPriority( Priority priority )
   {
      this.priority = priority;
   }
   
   
   
   @Override
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
   
   
   
   @Override
   public boolean isPostponed()
   {
      return numPostponed > 0;
   }
   
   
   
   @Override
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
   
   
   
   @Override
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
   
   
   
   @Override
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
   
   
   
   @Override
   public Iterable< String > getTags()
   {
      return tags != null ? tags : Collections.< String > emptyList();
   }
   
   
   
   public void setTags( Collection< String > tags )
   {
      this.tags = tags;
   }
   
   
   
   @Override
   public Iterable< INote > getNotes()
   {
      return notes != null ? notes : Collections.< INote > emptyList();
   }
   
   
   
   public void setNotes( Iterable< ? extends INote > notes )
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
   
   
   
   @Override
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
   
   
   
   @Override
   public Location getLocation()
   {
      return location;
   }
   
   
   
   public void setLocation( Location location )
   {
      this.location = location;
   }
   
   
   
   @Override
   public long getListId()
   {
      return listId;
   }
   
   
   
   @Override
   public String getListName()
   {
      return listName;
   }
   
   
   
   private void ensureNotesContainer()
   {
      if ( notes == null )
      {
         notes = new ArrayList< INote >();
      }
   }
   
   
   
   private void ensureParticipantsContainer()
   {
      if ( participants == null )
      {
         participants = new ArrayList< Participant >();
      }
   }
   
   
   
   private void addNotes( Iterable< ? extends INote > notes )
   {
      for ( INote note : notes )
      {
         this.notes.add( note );
      }
   }
}
