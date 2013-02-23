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

package dev.drsoran.moloko.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import dev.drsoran.moloko.util.Strings;


public class Task extends LifeTimeManaged
{
   private final long id;
   
   private final long addedMillisUtc;
   
   private String name;
   
   private String source = Strings.EMPTY_STRING;
   
   private String url = Strings.EMPTY_STRING;
   
   private long completedMillisUtc = Constants.NO_TIME;
   
   private Priority priority = Priority.None;
   
   private int numPostponed;
   
   private Due due;
   
   private Recurrence recurrence;
   
   private Estimation estimation;
   
   private Collection< Note > notes;
   
   
   
   public Task( long id, String name, long createdMillisUtc, long addedMillisUtc )
   {
      super( createdMillisUtc );
      
      this.id = id;
      this.name = name;
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
      this.name = name;
   }
   
   
   
   public String getSource()
   {
      return source;
   }
   
   
   
   public void setSource( String source )
   {
      this.source = source;
   }
   
   
   
   public String getUrl()
   {
      return url;
   }
   
   
   
   public void setUrl( String url )
   {
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
      this.due = due;
   }
   
   
   
   public Recurrence getRecurrence()
   {
      return recurrence;
   }
   
   
   
   public void setRecurrence( Recurrence recurrence )
   {
      this.recurrence = recurrence;
   }
   
   
   
   public Estimation getEstimation()
   {
      return estimation;
   }
   
   
   
   public void setEstimation( Estimation estimation )
   {
      this.estimation = estimation;
   }
   
   
   
   public Iterable< Note > getNotes()
   {
      return notes != null ? notes : Collections.< Note > emptyList();
   }
   
   
   
   public void setNotes( Iterable< ? extends Note > notes )
   {
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
      ensureNotesContainer();
      return notes.add( note );
   }
   
   
   
   public boolean removeNote( Note note )
   {
      if ( notes != null )
      {
         return notes.remove( note );
      }
      
      return false;
   }
   
   
   
   private void ensureNotesContainer()
   {
      if ( this.notes == null )
      {
         notes = new ArrayList< Note >();
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
