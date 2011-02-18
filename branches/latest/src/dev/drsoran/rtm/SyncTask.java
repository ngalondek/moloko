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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.text.TextUtils;

import com.mdt.rtm.data.RtmTask.Priority;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.TypedDirectedSyncOperations;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.provider.Rtm.Tasks;


public class SyncTask implements ITwoWaySyncable< SyncTask >
{
   private final String id;
   
   private final String taskSeriesId;
   
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
   
   private final int hasDueTime;
   
   private final Date added;
   
   private final Date completed;
   
   private final Date deleted;
   
   private final Priority priority;
   
   private final int posponed;
   
   private final String estimate;
   
   private final long estimateMillis;
   
   private final List< String > tags;
   
   private final ParticipantList participants;
   
   

   public SyncTask( String id, String taskSeriesId, Date created,
      Date modified, String name, String source, String url, String recurrence,
      boolean isEveryRecurrence, String locationId, String listId, Date due,
      int hasDueTime, Date added, Date completed, Date deleted,
      Priority priority, int posponed, String estimate, long estimateMillis,
      String tags, ParticipantList participants )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
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
      
      if ( !TextUtils.isEmpty( tags ) )
      {
         this.tags = Collections.unmodifiableList( Arrays.asList( TextUtils.split( tags,
                                                                                   Tasks.TAGS_DELIMITER ) ) );
      }
      else
      {
         this.tags = Collections.emptyList();
      }
      
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
   


   public Date getCreatedDate()
   {
      return created;
   }
   


   public Date getModifiedDate()
   {
      return modified;
   }
   


   public Date getDueDate()
   {
      return due;
   }
   


   public int isHasDueTime()
   {
      return hasDueTime;
   }
   


   public Date getAddedDate()
   {
      return added;
   }
   


   public Date getCompletedDate()
   {
      return completed;
   }
   


   public Date getDeletedDate()
   {
      return deleted;
   }
   


   public int getPosponed()
   {
      return posponed;
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
   


   public int hasDueTime()
   {
      return hasDueTime;
   }
   


   public Priority getPriority()
   {
      return priority;
   }
   


   public String getEstimate()
   {
      return estimate;
   }
   


   public long getEstimateMillis()
   {
      return estimateMillis;
   }
   


   public List< String > getTags()
   {
      return tags;
   }
   


   public ParticipantList getParticipants()
   {
      if ( participants == null )
         return new ParticipantList( taskSeriesId );
      else
         return participants;
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( SyncTask serverElement )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IServerSyncOperation< SyncTask > computeServerInsertOperation( RtmTimeline timeLine )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IServerSyncOperation< SyncTask > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IServerSyncOperation< SyncTask > computeServerUpdateOperation( RtmTimeline timeLine,
                                                                         ModificationList modifictaions )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public TypedDirectedSyncOperations< SyncTask > computeMergeOperations( RtmTimeline timeLine,
                                                                          ModificationList modifications,
                                                                          SyncTask updateElement,
                                                                          dev.drsoran.moloko.service.sync.syncable.IServerSyncable.MergeDirection mergeDirection )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IContentProviderSyncOperation computeRemoveModificationsOperation( ModificationList modifications )
   {
      // TODO Auto-generated method stub
      return null;
   }
}
