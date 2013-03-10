/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.grammar.RecurrenceParsing;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.ParcelableDate;
import dev.drsoran.rtm.ParticipantList;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskSeries extends RtmData
{
   public static final Parcelable.Creator< RtmTaskSeries > CREATOR = new Parcelable.Creator< RtmTaskSeries >()
   {
      @Override
      public RtmTaskSeries createFromParcel( Parcel source )
      {
         return new RtmTaskSeries( source );
      }
      
      
      
      @Override
      public RtmTaskSeries[] newArray( int size )
      {
         return new RtmTaskSeries[ size ];
      }
   };
   
   
   
   public static RtmTaskSeries findTask( String taskSeriesId, RtmTasks rtmTasks )
   {
      for ( RtmTaskList list : rtmTasks.getLists() )
      {
         for ( RtmTaskSeries series : list.getSeries() )
         {
            if ( taskSeriesId != null )
            {
               if ( series.getId().equals( taskSeriesId ) )
               {
                  return series;
               }
            }
            else
            {
               return series;
            }
         }
      }
      return null;
   }
   
   private final String id;
   
   private final String listId;
   
   private final ParcelableDate created;
   
   private final ParcelableDate modified;
   
   private final String name;
   
   private final String source;
   
   private final List< RtmTask > tasks;
   
   private final RtmTaskNotes notes;
   
   private final String locationId;
   
   private final String url;
   
   private final String recurrence;
   
   private final boolean isEveryRecurrence;
   
   private final List< String > tags;
   
   private final ParticipantList participants;
   
   
   
   public RtmTaskSeries( String id, String listId, Date created, Date modified,
      String name, String source, List< RtmTask > tasks, RtmTaskNotes notes,
      String locationId, String url, String recurrence,
      boolean isEveryRecurrence, String tags, ParticipantList participants )
   {
      this( id,
            listId,
            created,
            modified,
            name,
            source,
            tasks,
            notes,
            locationId,
            url,
            recurrence,
            isEveryRecurrence,
            tags != null
                        ? Arrays.asList( TextUtils.split( tags,
                                                          TaskSeries.TAGS_SEPARATOR ) )
                        : Collections.< String > emptyList(),
            participants );
   }
   
   
   
   public RtmTaskSeries( String id, String listId, Date created, Date modified,
      String name, String source, List< RtmTask > tasks, RtmTaskNotes notes,
      String locationId, String url, String recurrence,
      boolean isEveryRecurrence, List< String > tags,
      ParticipantList participants )
   {
      
      this.id = id;
      this.listId = listId;
      this.created = ( created != null ) ? new ParcelableDate( created ) : null;
      this.modified = ( modified != null ) ? new ParcelableDate( modified )
                                          : null;
      this.name = name;
      this.source = source;
      this.tasks = new ArrayList< RtmTask >( tasks );
      this.notes = notes;
      this.locationId = locationId;
      this.url = url;
      this.recurrence = recurrence;
      this.isEveryRecurrence = isEveryRecurrence;
      this.tags = tags;
      this.participants = participants == null ? new ParticipantList( id )
                                              : participants;
   }
   
   
   
   public RtmTaskSeries( Element elt, String listId )
   {
      this.id = textNullIfEmpty( elt, "id" );
      this.listId = listId;
      this.created = parseParcableDate( elt.getAttribute( "created" ) );
      this.modified = parseParcableDate( elt.getAttribute( "modified" ) );
      this.name = textNullIfEmpty( elt, "name" );
      this.source = textNullIfEmpty( elt, "source" );
      
      final Element recurrenceRule = child( elt, "rrule" );
      
      String recurrence = null;
      boolean isEveryRecurrence = false;
      
      if ( recurrenceRule != null
         && recurrenceRule.getChildNodes().getLength() > 0 )
      {
         recurrence = textNullIfEmpty( recurrenceRule );
         
         try
         {
            isEveryRecurrence = Integer.parseInt( textNullIfEmpty( recurrenceRule,
                                                                   "every" ) ) != 0;
            recurrence = RecurrenceParsing.ensureRecurrencePatternOrder( recurrence );
         }
         catch ( NumberFormatException nfe )
         {
            recurrence = null;
            isEveryRecurrence = false;
            
            MolokoApp.Log().e( getClass(),
                               "Error reading recurrence pattern from XML",
                               nfe );
         }
      }
      
      this.recurrence = recurrence;
      this.isEveryRecurrence = isEveryRecurrence;
      
      final List< Element > tasks = children( elt, "task" );
      this.tasks = new ArrayList< RtmTask >( tasks.size() );
      
      for ( Element task : tasks )
      {
         this.tasks.add( new RtmTask( task, id, listId ) );
      }
      
      this.notes = new RtmTaskNotes( child( elt, "notes" ), id );
      this.locationId = textNullIfEmpty( elt, "location_id" );
      this.url = textNullIfEmpty( elt, "url" );
      
      final Element elementTags = child( elt, "tags" );
      this.tags = new ArrayList< String >();
      
      if ( elementTags.getChildNodes().getLength() > 0 )
      {
         final List< Element > elementTagList = children( elementTags, "tag" );
         
         for ( Element elementTag : elementTagList )
         {
            final String tag = text( elementTag );
            if ( !TextUtils.isEmpty( tag ) )
            {
               this.tags.add( tag );
            }
         }
      }
      
      final Element elementParticipants = child( elt, "participants" );
      
      if ( elementParticipants.getChildNodes().getLength() > 0 )
         this.participants = new ParticipantList( id, elementParticipants );
      else
         this.participants = new ParticipantList( id );
   }
   
   
   
   public RtmTaskSeries( Element elt, String listId, boolean deleted )
   {
      id = elt.getAttribute( "id" );
      
      this.listId = listId;
      created = null;
      modified = null;
      name = null;
      
      List< Element > tasks = children( elt, "task" );
      
      // Fallback for alternative API
      if ( tasks.size() == 0 )
         tasks = children( elt, "tasks" );
      
      if ( tasks.size() == 0 )
         throw new IllegalStateException( String.format( "Found deleted RtmTaskSeries '%s' with no tasks.",
                                                         id ) );
      
      this.tasks = new ArrayList< RtmTask >( tasks.size() );
      
      for ( Element task : tasks )
      {
         this.tasks.add( new RtmTask( task, id, listId, deleted ) );
      }
      
      source = null;
      locationId = null;
      notes = null;
      url = null;
      recurrence = null;
      isEveryRecurrence = false;
      tags = null;
      participants = null;
   }
   
   
   
   public RtmTaskSeries( Parcel source )
   {
      id = source.readString();
      listId = source.readString();
      created = ParcelableDate.fromParcel( source );
      modified = ParcelableDate.fromParcel( source );
      name = source.readString();
      this.source = source.readString();
      tasks = source.createTypedArrayList( RtmTask.CREATOR );
      notes = new RtmTaskNotes( source );
      locationId = source.readString();
      url = source.readString();
      recurrence = source.readString();
      isEveryRecurrence = source.readInt() != 0;
      tags = source.createStringArrayList();
      participants = source.readParcelable( ParticipantList.class.getClassLoader() );
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public String getListId()
   {
      return listId;
   }
   
   
   
   public Date getCreatedDate()
   {
      return ( created != null ) ? created.getDate() : null;
   }
   
   
   
   public Date getModifiedDate()
   {
      return ( modified != null ) ? modified.getDate() : null;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public String getSource()
   {
      return source;
   }
   
   
   
   public List< RtmTask > getTasks()
   {
      return tasks;
   }
   
   
   
   public List< RtmTask > getDeletedTasks()
   {
      List< RtmTask > deletedTasks = new LinkedList< RtmTask >();
      
      for ( RtmTask task : tasks )
      {
         if ( task.isDeleted() )
            deletedTasks.add( task );
      }
      
      return deletedTasks;
   }
   
   
   
   public List< RtmTask > getAndRemoveDeletedTasks()
   {
      List< RtmTask > deletedTasks = new LinkedList< RtmTask >();
      
      for ( Iterator< RtmTask > i = tasks.iterator(); i.hasNext(); )
      {
         final RtmTask rtmTask = i.next();
         if ( rtmTask.isDeleted() )
         {
            deletedTasks.add( rtmTask );
            i.remove();
         }
      }
      
      return deletedTasks;
   }
   
   
   
   public RtmTask getTask( String id )
   {
      for ( RtmTask task : tasks )
         if ( task.getId().equals( id ) )
            return task;
      
      return null;
   }
   
   
   
   public RtmTaskNotes getNotes()
   {
      return notes == null ? new RtmTaskNotes() : notes;
   }
   
   
   
   public List< String > getTags()
   {
      if ( tags == null )
         return Collections.emptyList();
      else
         return tags;
   }
   
   
   
   public String getTagsJoined()
   {
      if ( !hasTags() )
         return Strings.EMPTY_STRING;
      else
         return TextUtils.join( TaskSeries.TAGS_SEPARATOR, tags );
   }
   
   
   
   public boolean hasTags()
   {
      return tags != null && !tags.isEmpty();
   }
   
   
   
   public ParticipantList getParticipants()
   {
      return participants == null ? new ParticipantList( id ) : participants;
   }
   
   
   
   public String getLocationId()
   {
      return locationId;
   }
   
   
   
   public String getURL()
   {
      return url;
   }
   
   
   
   public String getRecurrence()
   {
      return recurrence;
   }
   
   
   
   public String getRecurrenceSentence()
   {
      final String repeat;
      
      if ( recurrence != null )
         repeat = RecurrenceParsing.parseRecurrencePatternToSentence( recurrence,
                                                            isEveryRecurrence );
      else
         repeat = null;
      
      return repeat;
   }
   
   
   
   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
   }
   
   
   
   @Override
   public int describeContents()
   {
      return 0;
   }
   
   
   
   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( listId );
      dest.writeParcelable( created, flags );
      dest.writeParcelable( modified, flags );
      dest.writeString( name );
      dest.writeString( source );
      dest.writeTypedList( tasks );
      notes.writeToParcel( dest, flags );
      dest.writeString( locationId );
      dest.writeString( url );
      dest.writeString( recurrence );
      dest.writeInt( isEveryRecurrence ? 1 : 0 );
      dest.writeStringList( tags );
      dest.writeParcelable( participants, flags );
   }
   
   
   
   @Override
   public String toString()
   {
      return "TaskSeries<" + id + "," + name + ">";
   }
}
