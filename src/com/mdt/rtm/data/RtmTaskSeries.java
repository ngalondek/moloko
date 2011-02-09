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
import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.grammar.RecurrenceParser;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.TypedDirectedSyncOperations;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.SyncUtils;
import dev.drsoran.moloko.util.SyncUtils.MergeProperties;
import dev.drsoran.moloko.util.SyncUtils.MergeResultDirection;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Tag;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskSeries extends RtmData implements
         ITwoWaySyncable< RtmTaskSeries >
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
   
   private final ArrayList< RtmTask > tasks;
   
   private final RtmTaskNotes notes;
   
   private final String locationId;
   
   private final String url;
   
   private String recurrence;
   
   private boolean isEveryRecurrence;
   
   private final List< Tag > tags;
   
   private final ParticipantList participants;
   
   private final ParcelableDate deleted;
   
   

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
      this.participants = participants;
      this.deleted = null;
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
         this.tasks.add( new RtmTask( task, id ) );
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
      
      this.deleted = null;
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
         this.tasks.add( new RtmTask( task, id, deleted ) );
      }
      
      source = null;
      locationId = null;
      notes = null;
      url = null;
      recurrence = null;
      isEveryRecurrence = false;
      tags = null;
      participants = null;
      this.deleted = new ParcelableDate( System.currentTimeMillis() );
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
      deleted = source.readParcelable( null );
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
   


   public boolean isDeleted()
   {
      return deleted != null;
   }
   


   public Date getDeletedDate()
   {
      return deleted != null ? deleted.getDate() : null;
   }
   


   public String getLocationId()
   {
      return locationId;
   }
   


   @Override
   public String toString()
   {
      return "TaskSeries<" + id + "," + name + "," + deleted + ">";
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
      dest.writeParcelable( deleted, flags );
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
      final String[] operators = recurrencePattern.split( RecurrenceParser.OPERATOR_SEP );
      Arrays.sort( operators, RecurrenceParser.CMP_OPERATORS );
      
      return TextUtils.join( RecurrenceParser.OPERATOR_SEP, operators );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( RtmTaskSeriesProviderPart.insertTaskSeries( this ) )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // Referenced parts like RawTasks and Notes are deleted by a DB trigger.
      // @see RtmTaskSeriesPart::create
      //
      // These deletions have not to be counted cause they are implementation
      // details that all belong to a taskseries.
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                                   id ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmTaskSeries serverElement )
   {
      return computeMergeOperations( null,
                                     null,
                                     serverElement,
                                     MergeDirection.LOCAL_ONLY ).getLocalOperation();
   }
   


   public TypedDirectedSyncOperations< RtmTaskSeries > computeMergeOperations( RtmTimeline timeLine,
                                                                               ModificationList modifications,
                                                                               RtmTaskSeries updateElement,
                                                                               MergeDirection mergeDirection )
   {
      SyncUtils.doMergePreCheck( id,
                                 updateElement.id,
                                 timeLine,
                                 modifications,
                                 mergeDirection );
      
      final Uri uri = Queries.contentUriWithId( TaskSeries.CONTENT_URI, id );
      
      final MergeProperties< RtmTaskSeries > mergeProperties = MergeProperties.newInstance( mergeDirection,
                                                                                            this,
                                                                                            updateElement,
                                                                                            uri,
                                                                                            modifications );
      switch ( mergeDirection )
      {
         case LOCAL_ONLY:
            mergeLocal( updateElement, uri, mergeProperties );
            break;
         case BOTH:
            mergeLocal( updateElement, uri, mergeProperties );
         case SERVER_ONLY:
            mergeServer( timeLine, updateElement, uri, mergeProperties );
            break;
      }
      
      return mergeProperties.getOperations();
   }
   


   private void mergeServer( RtmTimeline timeLine,
                             RtmTaskSeries updateElement,
                             Uri uri,
                             MergeProperties< RtmTaskSeries > mergeProperties )
   {
      if ( SyncUtils.MergeResultDirection.SERVER == SyncUtils.mergeModification( mergeProperties,
                                                                                 TaskSeries.TASKSERIES_NAME,
                                                                                 updateElement.name,
                                                                                 this.name,
                                                                                 String.class ) )
      {
         for ( RtmTask task : tasks )
         {
            mergeProperties.serverBuilder.add( timeLine.tasks_setName( listId,
                                                                       id,
                                                                       task.getId(),
                                                                       updateElement.name ) );
         }
      }
      
      SyncUtils.mergeModification( mergeProperties,
                                   TaskSeries.LIST_ID,
                                   updateElement.listId,
                                   this.listId,
                                   String.class );
      
      SyncUtils.mergeModification( mergeProperties,
                                   TaskSeries.LOCATION_ID,
                                   updateElement.locationId,
                                   this.locationId,
                                   String.class );
      
      SyncUtils.mergeModification( mergeProperties,
                                   TaskSeries.URL,
                                   updateElement.url,
                                   this.url,
                                   String.class );
      
      {
         final MergeResultDirection mergeRes = SyncUtils.mergeModification( mergeProperties,
                                                                            TaskSeries.RECURRENCE,
                                                                            updateElement.recurrence,
                                                                            this.recurrence,
                                                                            String.class );
         
         // If we take the server value we must correctly set the local flags.
         if ( mergeRes == MergeResultDirection.LOCAL )
         {
            if ( TextUtils.isEmpty( updateElement.recurrence ) )
               mergeProperties.localBuilder.add( ContentProviderOperation.newUpdate( uri )
                                                                         .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                                     null )
                                                                         .build() );
            else if ( isEveryRecurrence != updateElement.isEveryRecurrence )
               mergeProperties.localBuilder.add( ContentProviderOperation.newUpdate( uri )
                                                                         .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                                     updateElement.isEveryRecurrence
                                                                                                                    ? 1
                                                                                                                    : 0 )
                                                                         .build() );
         }
      }
   }
   


   private void mergeLocal( RtmTaskSeries updateElement,
                            Uri uri,
                            MergeProperties< RtmTaskSeries > mergeProperties )
   {
      // Update notes
      {
         final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( notes.getNotes(),
                                                                                                                          RtmTaskNote.LESS_ID );
         final List< IContentProviderSyncOperation > noteOperations = SyncDiffer.diff( updateElement.notes.getNotes(),
                                                                                       syncNotesList );
         mergeProperties.localBuilder.add( noteOperations );
      }
      
      // Update tags
      {
         final ContentProviderSyncableList< Tag > syncList = new ContentProviderSyncableList< Tag >( tags );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( updateElement.tags,
                                                                                       syncList );
         mergeProperties.localBuilder.add( syncOperations );
      }
      
      // Update tasks
      {
         final ContentProviderSyncableList< RtmTask > syncTasksList = new ContentProviderSyncableList< RtmTask >( tasks,
                                                                                                                  RtmTask.LESS_ID );
         final List< IContentProviderSyncOperation > taskOperations = SyncDiffer.diff( updateElement.tasks,
                                                                                       syncTasksList );
         mergeProperties.localBuilder.add( taskOperations );
      }
      
      // Update participants
      {
         mergeProperties.localBuilder.add( participants.computeContentProviderUpdateOperation( updateElement.participants ) );
      }
      
      // Update taskseries
      {
         SyncUtils.updateDate( created,
                               updateElement.created,
                               uri,
                               TaskSeries.TASKSERIES_CREATED_DATE,
                               mergeProperties.localBuilder );
         
         SyncUtils.updateDate( modified,
                               updateElement.modified,
                               uri,
                               TaskSeries.MODIFIED_DATE,
                               mergeProperties.localBuilder );
         
         // This can not be changed locally
         if ( SyncUtils.hasChanged( source, updateElement.source ) )
            mergeProperties.localBuilder.add( ContentProviderOperation.newUpdate( uri )
                                                                      .withValue( TaskSeries.SOURCE,
                                                                                  updateElement.source )
                                                                      .build() );
      }
   }
   


   @SuppressWarnings( "unchecked" )
   public IServerSyncOperation< RtmTaskSeries > computeServerInsertOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.INSTANCE;
   }
   


   @SuppressWarnings( "unchecked" )
   public IServerSyncOperation< RtmTaskSeries > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.INSTANCE;
   }
   


   public IServerSyncOperation< RtmTaskSeries > computeServerUpdateOperation( RtmTimeline timeLine,
                                                                              ModificationList modifications )
   {
      return computeMergeOperations( timeLine,
                                     modifications,
                                     this,
                                     MergeDirection.SERVER_ONLY ).getServerOperation();
   }
   


   public IContentProviderSyncOperation computeRemoveModificationsOperation( ModificationList modifications )
   {
      if ( modifications.hasModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                    id ) ) )
         return ContentProviderSyncOperation.newDelete( ModificationsProviderPart.getRemoveModificationOps( TaskSeries.CONTENT_URI,
                                                                                                            id ) )
                                            .build();
      else
         return NoopContentProviderSyncOperation.INSTANCE;
   }
}
