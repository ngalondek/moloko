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
   
   

   public ListTask( String id, String listName, boolean isSmartList,
      Date created, Date modified, String name, String source, String url,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      boolean posponed, String estimate, String locationName, float longitude,
      float latitude, String address, boolean isViewable, int zoom,
      List< String > tags, int numNotes )
   {
      super( id,
             listName,
             isSmartList,
             created,
             modified,
             name,
             source,
             url,
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
                           task.getListName(),
                           task.isSmartList(),
                           task.getCreated(),
                           task.getModified(),
                           task.getName(),
                           task.getSource(),
                           task.getUrl(),
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
