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


public class RtmTask
{
   private final RtmTaskSeries taskSeries;
   
   private final RtmRawTask rawTask;
   
   
   
   public RtmTask( String id, String taskSeriesId, long createdMillisUtc,
      long addedMillisUtc, long modifiedMillisUtc, long deletedMillisUtc,
      String listId, String locationId, String name, String source, String url,
      long completedMillisUtc, Priority priority, int numPostponed,
      long dueMillisUtc, boolean hasDueTime, String recurrencePattern,
      boolean isEveryRecurrence, String estimationSentence,
      Collection< String > tags, Collection< RtmNote > notes,
      Collection< RtmContact > participants )
   {
      this( new RtmTaskSeries( taskSeriesId,
                               createdMillisUtc,
                               modifiedMillisUtc,
                               listId,
                               locationId,
                               name,
                               source,
                               url,
                               recurrencePattern,
                               isEveryRecurrence,
                               tags,
                               notes,
                               participants ),
            new RtmRawTask( id,
                            addedMillisUtc,
                            deletedMillisUtc,
                            completedMillisUtc,
                            priority,
                            numPostponed,
                            dueMillisUtc,
                            hasDueTime,
                            estimationSentence ) );
   }
   
   
   
   public RtmTask( RtmTaskSeries taskSeries, RtmRawTask rawTask )
   {
      if ( taskSeries == null )
      {
         throw new IllegalArgumentException( "taskSeries" );
      }
      
      if ( rawTask == null )
      {
         throw new IllegalArgumentException( "rawTask" );
      }
      
      this.taskSeries = taskSeries;
      this.rawTask = rawTask;
   }
   
   
   
   public String getId()
   {
      return rawTask.getId();
   }
   
   
   
   public String getTaskSeriesId()
   {
      return taskSeries.getId();
   }
   
   
   
   public String getName()
   {
      return taskSeries.getName();
   }
   
   
   
   public String getSource()
   {
      return taskSeries.getSource();
   }
   
   
   
   public String getUrl()
   {
      return taskSeries.getUrl();
   }
   
   
   
   public long getCreatedMillisUtc()
   {
      return taskSeries.getCreatedMillisUtc();
   }
   
   
   
   public long getAddedMillisUtc()
   {
      return rawTask.getAddedMillisUtc();
   }
   
   
   
   public long getModifiedMillisUtc()
   {
      return taskSeries.getModifiedMillisUtc();
   }
   
   
   
   public long getDeletedMillisUtc()
   {
      return rawTask.getDeletedMillisUtc();
   }
   
   
   
   public long getCompletedMillisUtc()
   {
      return rawTask.getCompletedMillisUtc();
   }
   
   
   
   public Priority getPriority()
   {
      return rawTask.getPriority();
   }
   
   
   
   public int getPostponedCount()
   {
      return rawTask.getPostponedCount();
   }
   
   
   
   public long getDueMillisUtc()
   {
      return rawTask.getDueMillisUtc();
   }
   
   
   
   public boolean hasDueTime()
   {
      return rawTask.hasDueTime();
   }
   
   
   
   public String getRecurrencePattern()
   {
      return taskSeries.getRecurrencePattern();
   }
   
   
   
   public String getRecurrenceSentence()
   {
      return taskSeries.getRecurrenceSentence();
   }
   
   
   
   public void setRecurrenceSentence( String recurrenceSentence )
   {
      taskSeries.setRecurrenceSentence( recurrenceSentence );
   }
   
   
   
   public boolean isEveryRecurrence()
   {
      return taskSeries.isEveryRecurrence();
   }
   
   
   
   public String getEstimationSentence()
   {
      return rawTask.getEstimationSentence();
   }
   
   
   
   public Collection< String > getTags()
   {
      return taskSeries.getTags();
   }
   
   
   
   public String getTagsJoined()
   {
      return taskSeries.getTagsJoined();
   }
   
   
   
   public Collection< ? extends RtmNote > getNotes()
   {
      return taskSeries.getNotes();
   }
   
   
   
   public RtmNote getNote( String noteId )
   {
      return taskSeries.getNote( noteId );
   }
   
   
   
   public boolean hasNote( String noteId )
   {
      return taskSeries.hasNote( noteId );
   }
   
   
   
   public void addNote( RtmNote note )
   {
      taskSeries.addNote( note );
   }
   
   
   
   public Collection< RtmContact > getParticipants()
   {
      return taskSeries.getParticipants();
   }
   
   
   
   public RtmContact getParticipant( String participantId )
   {
      return taskSeries.getParticipant( participantId );
   }
   
   
   
   public String getLocationId()
   {
      return taskSeries.getLocationId();
   }
   
   
   
   public String getListId()
   {
      return taskSeries.getListId();
   }
   
   
   
   public RtmTaskSeries getTaskSeries()
   {
      return taskSeries;
   }
   
   
   
   public RtmRawTask getRawTask()
   {
      return rawTask;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmTask [id=%s, name=%s, added=%s, completed=%s, listId=%s, notes=%s]",
                            getId(),
                            getName(),
                            getAddedMillisUtc(),
                            getCompletedMillisUtc(),
                            getListId(),
                            getNotes().size() );
   }
}
