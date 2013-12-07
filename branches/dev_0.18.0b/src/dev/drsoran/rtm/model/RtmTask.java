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

import java.util.Collection;
import java.util.Collections;

import dev.drsoran.Strings;


public class RtmTask
{
   private final String id;
   
   private final String taskSeriesId;
   
   private final long createdMillisUtc;
   
   private final long addedMillisUtc;
   
   private final long modifiedMillisUtc;
   
   private final long deletedMillisUtc;
   
   private final String listId;
   
   private final String locationId;
   
   private final String name;
   
   private final String source;
   
   private final String url;
   
   private final long completedMillisUtc;
   
   private final Priority priority;
   
   private final int numPostponed;
   
   private final long dueMillisUtc;
   
   private final boolean hasDueTime;
   
   private final String recurrencePattern;
   
   private final boolean isEveryRecurrence;
   
   private final String estimationSentence;
   
   private final Collection< String > tags;
   
   private final Collection< RtmNote > notes;
   
   private final Collection< RtmContact > participants;
   
   
   
   public RtmTask( String id, String taskSeriesId, long createdMillisUtc,
      long addedMillisUtc, long modifiedMillisUtc, long deletedMillisUtc,
      String listId, String locationId, String name, String source, String url,
      long completedMillisUtc, Priority priority, int numPostponed,
      long dueMillisUtc, boolean hasDueTime, String recurrencePattern,
      boolean isEveryRecurrence, String estimationSentence,
      Collection< String > tags, Collection< RtmNote > notes,
      Collection< RtmContact > participants )
   {
      if ( id == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "id" );
      }
      
      if ( taskSeriesId == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "taskSeriesId" );
      }
      
      if ( createdMillisUtc == RtmConstants.NO_TIME )
      {
         throw new IllegalArgumentException( "createdMillisUtc" );
      }
      
      if ( addedMillisUtc == RtmConstants.NO_TIME )
      {
         throw new IllegalArgumentException( "addedMillisUtc" );
      }
      
      if ( listId == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "listId" );
      }
      
      if ( locationId == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "locationId" );
      }
      
      if ( Strings.isNullOrEmpty( name ) )
      {
         throw new IllegalArgumentException( "name" );
      }
      
      if ( listId == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "listId" );
      }
      
      if ( numPostponed < 0 )
      {
         throw new IllegalArgumentException( "numPostponed" );
      }
      
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.createdMillisUtc = createdMillisUtc;
      this.addedMillisUtc = addedMillisUtc;
      this.modifiedMillisUtc = modifiedMillisUtc;
      this.deletedMillisUtc = deletedMillisUtc;
      this.listId = listId;
      this.locationId = locationId;
      this.name = name;
      this.source = source;
      this.url = Strings.emptyIfNull( url );
      this.completedMillisUtc = completedMillisUtc;
      this.priority = priority;
      this.numPostponed = numPostponed;
      this.dueMillisUtc = dueMillisUtc;
      this.hasDueTime = hasDueTime;
      this.recurrencePattern = recurrencePattern;
      this.isEveryRecurrence = isEveryRecurrence;
      this.estimationSentence = estimationSentence;
      this.tags = tags;
      this.notes = notes;
      this.participants = participants;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public String getTaskSeriesId()
   {
      return taskSeriesId;
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
   
   
   
   public long getAddedMillisUtc()
   {
      return addedMillisUtc;
   }
   
   
   
   public long getModifiedMillisUtc()
   {
      return modifiedMillisUtc;
   }
   
   
   
   public long getDeletedMillisUtc()
   {
      return deletedMillisUtc;
   }
   
   
   
   public long getCompletedMillisUtc()
   {
      return completedMillisUtc;
   }
   
   
   
   public Priority getPriority()
   {
      return priority;
   }
   
   
   
   public int getPostponedCount()
   {
      return numPostponed;
   }
   
   
   
   public long getDueMillisUtc()
   {
      return dueMillisUtc;
   }
   
   
   
   public boolean hasDueTime()
   {
      return hasDueTime;
   }
   
   
   
   public String getRecurrencePattern()
   {
      return recurrencePattern;
   }
   
   
   
   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
   }
   
   
   
   public String getEstimationSentence()
   {
      return estimationSentence;
   }
   
   
   
   public Collection< String > getTags()
   {
      return tags != null ? tags : Collections.< String > emptyList();
   }
   
   
   
   public Iterable< ? extends RtmNote > getNotes()
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
      return String.format( "RtmTask [id=%s, name=%s, added=%s, completed=%s, listId=%s, notes=%s]",
                            id,
                            name,
                            addedMillisUtc,
                            completedMillisUtc,
                            listId,
                            notes != null ? notes.size() : 0 );
   }
}
