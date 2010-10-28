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
import java.util.StringTokenizer;

import android.text.TextUtils;

import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.provider.Rtm.Tasks;


public class Task
{
   private final String id;
   
   private final String taskSeriesId;
   
   private final String listName;
   
   private final boolean isSmartList;
   
   private final Date created;
   
   private final Date modified;
   
   private final String name;
   
   private final String source;
   
   private final String url;
   
   private final String recurrence;
   
   private final boolean isEveryRecurrence;
   
   private final String locationId;
   
   private final String listId;
   
   private final Date due;
   
   private final boolean hasDueTime;
   
   private final Date added;
   
   private final Date completed;
   
   private final Date deleted;
   
   private final Priority priority;
   
   private final boolean posponed;
   
   private final String estimate;
   
   private final long estimateMillis;
   
   private final String locationName;
   
   private final float longitude;
   
   private final float latitude;
   
   private final String address;
   
   private final boolean isViewable;
   
   private final int zoom;
   
   private final ArrayList< String > tags;
   
   private final int numNotes;
   
   

   public Task( String id, String taskSeriesId, String listName,
      boolean isSmartList, Date created, Date modified, String name,
      String source, String url, String recurrence, boolean isEveryRecurrence,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      boolean posponed, String estimate, long estimateMillis,
      String locationName, float longitude, float latitude, String address,
      boolean isViewable, int zoom, String tags, int numNotes )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.listName = listName;
      this.isSmartList = isSmartList;
      this.created = created;
      this.modified = modified;
      this.name = name;
      this.source = source;
      this.url = url;
      this.recurrence = recurrence;
      this.isEveryRecurrence = isEveryRecurrence;
      this.locationId = locationId;
      this.listId = listId;
      this.due = due;
      this.hasDueTime = hasDueTime;
      this.added = added;
      this.completed = completed;
      this.deleted = deleted;
      this.priority = priority;
      this.posponed = posponed;
      this.estimate = estimate;
      this.estimateMillis = estimateMillis;
      this.locationName = locationName;
      this.longitude = longitude;
      this.latitude = latitude;
      this.address = address;
      this.isViewable = isViewable;
      this.zoom = zoom;
      
      if ( !TextUtils.isEmpty( tags ) )
      {
         this.tags = new ArrayList< String >();
         
         final StringTokenizer tokenizer = new StringTokenizer( tags,
                                                                Tasks.TAGS_DELIMITER );
         
         while ( tokenizer.hasMoreTokens() )
         {
            this.tags.add( tokenizer.nextToken() );
         }
      }
      else
      {
         this.tags = null;
      }
      
      this.numNotes = numNotes;
   }
   


   public Task( String id, String taskSeriesId, String listName,
      boolean isSmartList, Date created, Date modified, String name,
      String source, String url, String recurrence, boolean isEveryRecurrence,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      boolean posponed, String estimate, long estimateMillis,
      String locationName, float longitude, float latitude, String address,
      boolean isViewable, int zoom, List< String > tags, int numNotes )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.listName = listName;
      this.isSmartList = isSmartList;
      this.created = created;
      this.modified = modified;
      this.name = name;
      this.source = source;
      this.url = url;
      this.recurrence = recurrence;
      this.isEveryRecurrence = isEveryRecurrence;
      this.locationId = locationId;
      this.listId = listId;
      this.due = due;
      this.hasDueTime = hasDueTime;
      this.added = added;
      this.completed = completed;
      this.deleted = deleted;
      this.priority = priority;
      this.posponed = posponed;
      this.estimate = estimate;
      this.estimateMillis = estimateMillis;
      this.locationName = locationName;
      this.longitude = longitude;
      this.latitude = latitude;
      this.address = address;
      this.isViewable = isViewable;
      this.zoom = zoom;
      this.numNotes = numNotes;
      
      if ( tags != null && tags.size() > 0 )
      {
         this.tags = new ArrayList< String >( tags );
      }
      else
      {
         this.tags = null;
      }
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public String getListName()
   {
      return listName;
   }
   


   public boolean isSmartList()
   {
      return isSmartList;
   }
   


   public Date getCreated()
   {
      return created;
   }
   


   public Date getModified()
   {
      return modified;
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
   


   public String getRecurrence()
   {
      return recurrence;
   }
   


   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
   }
   


   public String getLocationId()
   {
      return locationId;
   }
   


   public String getListId()
   {
      return listId;
   }
   


   public Date getDue()
   {
      return due;
   }
   


   public boolean hasDueTime()
   {
      return hasDueTime;
   }
   


   public Date getAdded()
   {
      return added;
   }
   


   public Date getCompleted()
   {
      return completed;
   }
   


   public Date getDeleted()
   {
      return deleted;
   }
   


   public Priority getPriority()
   {
      return priority;
   }
   


   public boolean isPosponed()
   {
      return posponed;
   }
   


   public String getEstimate()
   {
      return estimate;
   }
   


   public long getEstimateMillis()
   {
      return estimateMillis;
   }
   


   public String getLocationName()
   {
      return locationName;
   }
   


   public float getLongitude()
   {
      return longitude;
   }
   


   public float getLatitude()
   {
      return latitude;
   }
   


   public String getAddress()
   {
      return address;
   }
   


   public boolean isViewable()
   {
      return isViewable;
   }
   


   public int getZoom()
   {
      return zoom;
   }
   


   public List< String > getTags()
   {
      if ( tags != null )
         return Collections.unmodifiableList( tags );
      else
         return Collections.emptyList();
   }
   


   public int getNumberOfNotes()
   {
      return numNotes;
   }
}
