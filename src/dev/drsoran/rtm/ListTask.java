/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTask.Priority;


public class ListTask extends Task
{
   private ArrayList< RtmTaskNote > notes;
   
   

   public ListTask( String id, String taskSeriesId, String listName,
      boolean isSmartList, Date created, Date modified, String name,
      String source, String url, String recurrence, boolean isEveryRecurrence,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      boolean posponed, String estimate, long estimateMillis,
      String locationName, float longitude, float latitude, String address,
      boolean isViewable, int zoom, List< String > tags, int numNotes )
   {
      super( id,
             taskSeriesId,
             listName,
             isSmartList,
             created,
             modified,
             name,
             source,
             url,
             recurrence,
             isEveryRecurrence,
             locationId,
             listId,
             due,
             hasDueTime,
             added,
             completed,
             deleted,
             priority,
             posponed,
             estimate,
             estimateMillis,
             locationName,
             longitude,
             latitude,
             address,
             isViewable,
             zoom,
             tags,
             numNotes );
   }
   


   public final static ListTask fromTask( Task task )
   {
      return new ListTask( task.getId(),
                           task.getTaskSeriesId(),
                           task.getListName(),
                           task.isSmartList(),
                           task.getCreated(),
                           task.getModified(),
                           task.getName(),
                           task.getSource(),
                           task.getUrl(),
                           task.getRecurrence(),
                           task.isEveryRecurrence(),
                           task.getLocationId(),
                           task.getListId(),
                           task.getDue(),
                           task.hasDueTime(),
                           task.getAdded(),
                           task.getCompleted(),
                           task.getDeleted(),
                           task.getPriority(),
                           task.isPosponed(),
                           task.getEstimate(),
                           task.getEstimateMillis(),
                           task.getLocationName(),
                           task.getLongitude(),
                           task.getLatitude(),
                           task.getAddress(),
                           task.isViewable(),
                           task.getZoom(),
                           task.getTags(),
                           task.getNumberOfNotes() );
   }
   


   public final static ArrayList< ListTask > fromTaskList( List< Task > list )
   {
      ArrayList< ListTask > listTaskList = new ArrayList< ListTask >( list.size() );
      
      for ( Task task : list )
      {
         listTaskList.add( fromTask( task ) );
      }
      
      return listTaskList;
   }
   


   public List< RtmTaskNote > getNotes()
   {
      if ( notes != null )
         return Collections.unmodifiableList( notes );
      else
         return Collections.emptyList();
   }
   


   public void setNotes( List< RtmTaskNote > notes )
   {
      this.notes = new ArrayList< RtmTaskNote >( notes );
   }
   
}
