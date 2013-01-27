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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;


public class Task implements Parcelable
{
   public static final Parcelable.Creator< Task > CREATOR = new Parcelable.Creator< Task >()
   {
      public Task createFromParcel( Parcel source )
      {
         return new Task( source );
      }
      


      public Task[] newArray( int size )
      {
         return new Task[ size ];
      }
   };
   
   private final String id;
   
   private final String taskSeriesId;
   
   private final String listName;
   
   private final boolean isSmartList;
   
   private final ParcelableDate created;
   
   private final ParcelableDate modified;
   
   private final String name;
   
   private final String source;
   
   private final String url;
   
   private final String recurrence;
   
   private final boolean isEveryRecurrence;
   
   private final String locationId;
   
   private final String listId;
   
   private final ParcelableDate due;
   
   private final boolean hasDueTime;
   
   private final ParcelableDate added;
   
   private final ParcelableDate completed;
   
   private final ParcelableDate deleted;
   
   private final Priority priority;
   
   private final int posponed;
   
   private final String estimate;
   
   private final long estimateMillis;
   
   private final String locationName;
   
   private final float longitude;
   
   private final float latitude;
   
   private final String address;
   
   private final boolean isViewable;
   
   private final int zoom;
   
   private final List< String > tags;
   
   private final ParticipantList participants;
   
   private final List< String > noteIds;
   
   

   public Task( String id, String taskSeriesId, String listName,
      boolean isSmartList, Date created, Date modified, String name,
      String source, String url, String recurrence, boolean isEveryRecurrence,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      int posponed, String estimate, long estimateMillis, String locationName,
      float longitude, float latitude, String address, boolean isViewable,
      int zoom, String tags, ParticipantList participants, String noteIds )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.listName = listName;
      this.isSmartList = isSmartList;
      this.created = ParcelableDate.newInstanceIfNotNull( created );
      this.modified = ParcelableDate.newInstanceIfNotNull( modified );
      this.name = name;
      this.source = source;
      this.url = url;
      this.recurrence = recurrence;
      this.isEveryRecurrence = isEveryRecurrence;
      this.locationId = locationId;
      this.listId = listId;
      this.due = ParcelableDate.newInstanceIfNotNull( due );
      this.hasDueTime = hasDueTime;
      this.added = ParcelableDate.newInstanceIfNotNull( added );
      this.completed = ParcelableDate.newInstanceIfNotNull( completed );
      this.deleted = ParcelableDate.newInstanceIfNotNull( deleted );
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
         this.tags = Arrays.asList( TextUtils.split( tags, Tags.TAGS_SEPARATOR ) );
      else
         this.tags = new ArrayList< String >( 0 );
      
      this.participants = participants != null
                                              ? participants
                                              : new ParticipantList( taskSeriesId );
      if ( !TextUtils.isEmpty( noteIds ) )
         this.noteIds = Arrays.asList( TextUtils.split( noteIds,
                                                        Tasks.NOTE_IDS_DELIMITER ) );
      else
         this.noteIds = new ArrayList< String >( 0 );
   }
   


   public Task( String id, String taskSeriesId, String listName,
      boolean isSmartList, Date created, Date modified, String name,
      String source, String url, String recurrence, boolean isEveryRecurrence,
      String locationId, String listId, Date due, boolean hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      int posponed, String estimate, long estimateMillis, String locationName,
      float longitude, float latitude, String address, boolean isViewable,
      int zoom, List< String > tags, ParticipantList participants,
      List< String > noteIds )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.listName = listName;
      this.isSmartList = isSmartList;
      this.created = ParcelableDate.newInstanceIfNotNull( created );
      this.modified = ParcelableDate.newInstanceIfNotNull( modified );
      this.name = name;
      this.source = source;
      this.url = url;
      this.recurrence = recurrence;
      this.isEveryRecurrence = isEveryRecurrence;
      this.locationId = locationId;
      this.listId = listId;
      this.due = ParcelableDate.newInstanceIfNotNull( due );
      this.hasDueTime = hasDueTime;
      this.added = ParcelableDate.newInstanceIfNotNull( added );
      this.completed = ParcelableDate.newInstanceIfNotNull( completed );
      this.deleted = ParcelableDate.newInstanceIfNotNull( deleted );
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
      
      if ( tags != null )
         this.tags = new ArrayList< String >( tags );
      else
         this.tags = new ArrayList< String >( 0 );
      
      this.participants = participants != null
                                              ? participants
                                              : new ParticipantList( taskSeriesId );
      if ( noteIds != null )
         this.noteIds = new ArrayList< String >( noteIds );
      else
         this.noteIds = new ArrayList< String >( 0 );
   }
   


   public Task( Parcel source )
   {
      this.id = source.readString();
      this.taskSeriesId = source.readString();
      this.listName = source.readString();
      this.isSmartList = source.readInt() != 0;
      this.created = ParcelableDate.fromParcel( source );
      this.modified = ParcelableDate.fromParcel( source );
      this.name = source.readString();
      this.source = source.readString();
      this.url = source.readString();
      this.recurrence = source.readString();
      this.isEveryRecurrence = source.readInt() != 0;
      this.locationId = source.readString();
      this.listId = source.readString();
      this.due = ParcelableDate.fromParcel( source );
      this.hasDueTime = source.readInt() != 0;
      this.added = ParcelableDate.fromParcel( source );
      this.completed = ParcelableDate.fromParcel( source );
      this.deleted = ParcelableDate.fromParcel( source );
      this.priority = Priority.valueOf( source.readString() );
      this.posponed = source.readInt();
      this.estimate = source.readString();
      this.estimateMillis = source.readLong();
      this.locationName = source.readString();
      this.longitude = source.readFloat();
      this.latitude = source.readFloat();
      this.address = source.readString();
      this.isViewable = source.readInt() != 0;
      this.zoom = source.readInt();
      
      this.tags = source.createStringArrayList();
      this.participants = source.readParcelable( ParticipantList.class.getClassLoader() );
      this.noteIds = source.createStringArrayList();
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
      return MolokoDateUtils.getDate( created );
   }
   


   public Date getModified()
   {
      return MolokoDateUtils.getDate( modified );
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
      return MolokoDateUtils.getDate( due );
   }
   


   public boolean hasDueTime()
   {
      return hasDueTime;
   }
   


   public Date getAdded()
   {
      return MolokoDateUtils.getDate( added );
   }
   


   public Date getCompleted()
   {
      return MolokoDateUtils.getDate( completed );
   }
   


   public Date getDeleted()
   {
      return MolokoDateUtils.getDate( deleted );
   }
   


   public Priority getPriority()
   {
      return priority;
   }
   


   public int getPosponed()
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
   


   public String getLocationAddress()
   {
      return address;
   }
   


   public boolean isLocationViewable()
   {
      return isViewable;
   }
   


   public int getZoom()
   {
      return zoom;
   }
   


   public List< String > getTags()
   {
      return Collections.unmodifiableList( tags );
   }
   


   public ParticipantList getParticipants()
   {
      return participants;
   }
   


   public int getNumberOfNotes()
   {
      return noteIds.size();
   }
   


   public List< String > getNoteIds()
   {
      return Collections.unmodifiableList( noteIds );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( taskSeriesId );
      dest.writeString( listName );
      dest.writeInt( isSmartList ? 1 : 0 );
      dest.writeParcelable( created, flags );
      dest.writeParcelable( modified, flags );
      dest.writeString( name );
      dest.writeString( source );
      dest.writeString( url );
      dest.writeString( recurrence );
      dest.writeInt( isEveryRecurrence ? 1 : 0 );
      dest.writeString( locationId );
      dest.writeString( listId );
      dest.writeParcelable( due, flags );
      dest.writeInt( hasDueTime ? 1 : 0 );
      dest.writeParcelable( added, flags );
      dest.writeParcelable( completed, flags );
      dest.writeParcelable( deleted, flags );
      dest.writeString( priority.toString() );
      dest.writeInt( posponed );
      dest.writeString( estimate );
      dest.writeLong( estimateMillis );
      dest.writeString( locationName );
      dest.writeFloat( longitude );
      dest.writeFloat( latitude );
      dest.writeString( address );
      dest.writeInt( isViewable ? 1 : 0 );
      dest.writeInt( zoom );
      dest.writeStringList( tags );
      dest.writeParcelable( participants, flags );
      dest.writeStringList( noteIds );
   }
}
