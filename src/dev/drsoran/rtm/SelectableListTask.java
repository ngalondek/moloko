/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.rtm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mdt.rtm.data.RtmTask.Priority;


public class SelectableListTask extends ListTask
{
   private boolean isSelected;
   
   

   public SelectableListTask( String id, String taskSeriesId, String listName,
      boolean isSmartList, Date created, Date modified, String name,
      String source, String url, String recurrence, boolean isEveryRecurrence,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      int posponed, String estimate, long estimateMillis, String locationName,
      float longitude, float latitude, String address, boolean isViewable,
      int zoom, List< String > tags, ParticipantList participants, int numNotes )
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
             participants,
             numNotes );
   }
   


   public final static SelectableListTask fromListTask( ListTask task )
   {
      return new SelectableListTask( task.getId(),
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
                                     task.getPosponed(),
                                     task.getEstimate(),
                                     task.getEstimateMillis(),
                                     task.getLocationName(),
                                     task.getLongitude(),
                                     task.getLatitude(),
                                     task.getAddress(),
                                     task.isViewable(),
                                     task.getZoom(),
                                     task.getTags(),
                                     task.getParticipants(),
                                     task.getNumberOfNotes() );
   }
   


   public final static int selectTasksById( List< ? extends SelectableListTask > tasks,
                                            List< String > taskIdsToSelect )
   {
      int numSelected = 0;
      
      for ( int i = 0, cnt = tasks.size(); i < cnt; ++i )
         tasks.get( i ).setSelected( false );
      
      for ( String s : taskIdsToSelect )
         for ( int i = 0, cnt = tasks.size(); i < cnt; ++i )
            if ( tasks.get( i ).getId().equals( s ) )
            {
               tasks.get( i ).setSelected( true );
               ++numSelected;
            }
      
      return numSelected;
   }
   


   public final static List< SelectableListTask > fromListTaskList( List< ? extends ListTask > list )
   {
      final List< SelectableListTask > listTaskList = new ArrayList< SelectableListTask >( list.size() );
      
      for ( ListTask task : list )
         listTaskList.add( fromListTask( task ) );
      
      return listTaskList;
   }
   


   public final static List< ListTask > asListTaskList( List< ? extends SelectableListTask > list )
   {
      final List< ListTask > listTaskList = new ArrayList< ListTask >( list.size() );
      
      for ( SelectableListTask task : list )
         listTaskList.add( task );
      
      return listTaskList;
   }
   


   public boolean isSelected()
   {
      return isSelected;
   }
   


   public void setSelected( boolean isSelected )
   {
      this.isSelected = isSelected;
   }
}
