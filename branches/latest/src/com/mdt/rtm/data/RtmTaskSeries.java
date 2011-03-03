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
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.grammar.RecurrenceParser;
import dev.drsoran.moloko.grammar.RecurrencePatternParser;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.ParcelableDate;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Tag;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskSeries extends RtmData implements
         IContentProviderSyncable< RtmTaskSeries >
{
   private final static String TAG = "Moloko."
      + RtmTaskSeries.class.getSimpleName();
   
   
   private static final class LessIdComperator implements
            Comparator< RtmTaskSeries >
   {
      public int compare( RtmTaskSeries object1, RtmTaskSeries object2 )
      {
         return object1.id.compareTo( object2.id );
      }
   }
   
   public static final Parcelable.Creator< RtmTaskSeries > CREATOR = new Parcelable.Creator< RtmTaskSeries >()
   {
      public RtmTaskSeries createFromParcel( Parcel source )
      {
         return new RtmTaskSeries( source );
      }
      


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
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
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
   
   private String recurrence;
   
   private boolean isEveryRecurrence;
   
   private final List< Tag > tags;
   
   private final ParticipantList participants;
   
   

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
      this.tags = createTags( tags );
      this.participants = participants == null ? new ParticipantList( id )
                                              : participants;
   }
   


   public RtmTaskSeries( Element elt, String listId )
   {
      this.id = textNullIfEmpty( elt, "id" );
      this.listId = listId;
      this.created = parseDate( elt.getAttribute( "created" ) );
      this.modified = parseDate( elt.getAttribute( "modified" ) );
      this.name = textNullIfEmpty( elt, "name" );
      this.source = textNullIfEmpty( elt, "source" );
      
      final Element recurrenceRule = child( elt, "rrule" );
      
      if ( recurrenceRule != null
         && recurrenceRule.getChildNodes().getLength() > 0 )
      {
         this.recurrence = textNullIfEmpty( recurrenceRule );
         
         try
         {
            this.isEveryRecurrence = Integer.parseInt( textNullIfEmpty( recurrenceRule,
                                                                        "every" ) ) != 0;
            this.recurrence = ensureRecurrencePatternOrder( recurrence );
         }
         catch ( NumberFormatException nfe )
         {
            this.recurrence = null;
            this.isEveryRecurrence = false;
            
            Log.e( TAG, "Error reading recurrence pattern from XML", nfe );
         }
      }
      else
      {
         this.recurrence = null;
         this.isEveryRecurrence = false;
      }
      
      final List< Element > tasks = children( elt, "task" );
      this.tasks = new ArrayList< RtmTask >( tasks.size() );
      
      for ( Element task : tasks )
      {
         this.tasks.add( new RtmTask( task, id, listId, modified ) );
      }
      
      this.notes = new RtmTaskNotes( child( elt, "notes" ), id );
      this.locationId = textNullIfEmpty( elt, "location_id" );
      this.url = textNullIfEmpty( elt, "url" );
      
      final Element elementTags = child( elt, "tags" );
      this.tags = new ArrayList< Tag >();
      
      if ( elementTags.getChildNodes().getLength() > 0 )
      {
         final List< Element > elementTagList = children( elementTags, "tag" );
         
         for ( Element elementTag : elementTagList )
         {
            final String tag = text( elementTag );
            if ( !TextUtils.isEmpty( tag ) )
            {
               this.tags.add( new Tag( id, tag ) );
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
      
      final List< Element > tasks = children( elt, "task" );
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
      created = source.readParcelable( null );
      modified = source.readParcelable( null );
      name = source.readString();
      this.source = source.readString();
      tasks = source.createTypedArrayList( RtmTask.CREATOR );
      notes = new RtmTaskNotes( source );
      locationId = source.readString();
      url = source.readString();
      recurrence = source.readString();
      isEveryRecurrence = source.readInt() != 0;
      tags = source.createTypedArrayList( Tag.CREATOR );
      participants = source.readParcelable( null );
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
   


   public Date getDeletedDate()
   {
      return tasks.get( 0 ).getDeletedDate();
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
   


   public List< Tag > getTags()
   {
      if ( tags == null )
         return Collections.emptyList();
      else
         return tags;
   }
   


   public List< String > getTagStrings()
   {
      if ( tags == null )
         return Collections.emptyList();
      else
      {
         final List< String > tagStrings = new ArrayList< String >( tags.size() );
         for ( Tag tag : tags )
         {
            tagStrings.add( tag.getTag() );
         }
         return tagStrings;
      }
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
   


   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


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
      dest.writeTypedList( tags );
      dest.writeParcelable( participants, flags );
   }
   


   @Override
   public String toString()
   {
      return "TaskSeries<" + id + "," + name + ">";
   }
   


   private final List< Tag > createTags( List< String > tagStrings )
   {
      final List< Tag > tags = new ArrayList< Tag >( tagStrings.size() );
      
      for ( String tagStr : tagStrings )
      {
         tags.add( new Tag( id, tagStr ) );
      }
      
      return tags;
   }
   


   private final static String ensureRecurrencePatternOrder( String recurrencePattern )
   {
      final String[] operators = recurrencePattern.split( RecurrencePatternParser.OPERATOR_SEP );
      Arrays.sort( operators, RecurrenceParser.CMP_OPERATORS );
      
      return TextUtils.join( RecurrencePatternParser.OPERATOR_SEP, operators );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newInsert();
      
      // Insert new taskseries
      {
         operation.add( ContentProviderOperation.newInsert( TaskSeries.CONTENT_URI )
                                                .withValues( RtmTaskSeriesProviderPart.getContentValues( this,
                                                                                                         true ) )
                                                .build() );
      }
      
      // Check for notes
      {
         final List< RtmTaskNote > notes = getNotes().getNotes();
         
         for ( RtmTaskNote rtmTaskNote : notes )
         {
            operation.add( ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                                   .withValues( RtmNotesProviderPart.getContentValues( rtmTaskNote,
                                                                                                       true ) )
                                                   .build() );
         }
      }
      
      // Check for tags
      {
         final List< Tag > tags = getTags();
         
         for ( Tag tag : tags )
         {
            operation.add( ContentProviderOperation.newInsert( Tags.CONTENT_URI )
                                                   .withValues( TagsProviderPart.getContentValues( tag,
                                                                                                   true ) )
                                                   .build() );
         }
      }
      
      // Check for participants
      {
         final ParticipantList participantList = getParticipants();
         operation.addAll( ParticipantsProviderPart.insertParticipants( participantList ) );
      }
      
      return operation.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // RtmTaskSeries gets deleted by a DB trigger if it references no more RawTasks.
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmTaskSeries serverElement )
   {
      final ContentProviderSyncOperation.Builder operations = ContentProviderSyncOperation.newUpdate();
      
      // Sync notes
      {
         final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( getNotes().getNotes(),
                                                                                                                          RtmTaskNote.LESS_ID );
         final List< IContentProviderSyncOperation > noteOperations = SyncDiffer.inDiff( serverElement.getNotes()
                                                                                                      .getNotes(),
                                                                                         syncNotesList,
                                                                                         true /* always full sync */);
         operations.add( noteOperations );
      }
      
      // Sync Tags
      {
         final ContentProviderSyncableList< Tag > syncList = new ContentProviderSyncableList< Tag >( getTags() );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( serverElement.getTags(),
                                                                                         syncList,
                                                                                         true /* always full sync */);
         operations.add( syncOperations );
      }
      
      // Sync participants
      {
         operations.add( participants.computeContentProviderUpdateOperation( serverElement.participants ) );
      }
      
      // Sync RtmTaskSeries
      {
         final Uri contentUri = Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                          id );
         
         if ( SyncUtils.hasChanged( serverElement.getListId(), getListId() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.LIST_ID,
                                                                serverElement.getListId() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getCreatedDate(),
                                    getCreatedDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.TASKSERIES_CREATED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.getCreatedDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getModifiedDate(),
                                    getModifiedDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.MODIFIED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.getModifiedDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getName(), getName() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.TASKSERIES_NAME,
                                                                serverElement.getName() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getSource(), getSource() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.SOURCE,
                                                                serverElement.getSource() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getLocationId(),
                                    getLocationId() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.LOCATION_ID,
                                                                serverElement.getLocationId() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getURL(), getURL() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.URL,
                                                                serverElement.getURL() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getRecurrence(),
                                    getRecurrence() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.RECURRENCE,
                                                                serverElement.getRecurrence() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( Boolean.valueOf( serverElement.isEveryRecurrence() ),
                                    Boolean.valueOf( isEveryRecurrence() ) ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                serverElement.isEveryRecurrence()
                                                                                                 ? 1
                                                                                                 : 0 )
                                                    .build() );
      }
      
      return operations.build();
   }
}
