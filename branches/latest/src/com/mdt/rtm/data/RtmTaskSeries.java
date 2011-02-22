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
import dev.drsoran.moloko.util.SyncUtils.SyncProperties;
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
         this.tasks.add( new RtmTask( task, id, modified ) );
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
   


   private SyncResultDirection syncListId( SyncProperties< RtmTaskSeries > properties,
                                           String serverValue,
                                           String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.LIST_ID,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      if ( dir == SyncResultDirection.SERVER )
         for ( RtmTask task : tasks )
            properties.serverBuilder.add( properties.timeline.tasks_moveTo( localValue,
                                                                            serverValue,
                                                                            id,
                                                                            task.getId() ) );
      return dir;
   }
   


   public Date getCreatedDate()
   {
      return ( created != null ) ? created.getDate() : null;
   }
   


   private static SyncResultDirection syncCreatedDate( SyncProperties< RtmTaskSeries > properties,
                                                       ParcelableDate serverValue,
                                                       ParcelableDate localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.TASKSERIES_CREATED_DATE,
                                                           serverValue,
                                                           localValue,
                                                           ParcelableDate.class );
      return dir;
   }
   


   public Date getModifiedDate()
   {
      return ( modified != null ) ? modified.getDate() : null;
   }
   


   private static SyncResultDirection syncModifiedDate( SyncProperties< RtmTaskSeries > properties,
                                                        ParcelableDate serverValue,
                                                        ParcelableDate localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.MODIFIED_DATE,
                                                           serverValue,
                                                           localValue,
                                                           ParcelableDate.class );
      return dir;
   }
   


   public Date getDeletedDate()
   {
      // This returns always null since a RtmTaskSeries gets deleted if it contains
      // no more tasks and not explicitly.
      return null;
   }
   


   public String getName()
   {
      return name;
   }
   


   private SyncResultDirection syncName( SyncProperties< RtmTaskSeries > properties,
                                         String serverValue,
                                         String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.TASKSERIES_NAME,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      if ( dir == SyncResultDirection.SERVER )
         for ( RtmTask task : tasks )
            properties.serverBuilder.add( properties.timeline.tasks_setName( listId,
                                                                             id,
                                                                             task.getId(),
                                                                             localValue ) );
      return dir;
   }
   


   public String getSource()
   {
      return source;
   }
   


   private static SyncResultDirection syncSource( SyncProperties< RtmTaskSeries > properties,
                                                  String serverValue,
                                                  String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.SOURCE,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      return dir;
   }
   


   public List< RtmTask > getTasks()
   {
      return tasks;
   }
   


   private static void syncTasks( SyncProperties< RtmTaskSeries > properties,
                                  List< RtmTask > serverValues,
                                  List< RtmTask > localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            final ContentProviderSyncableList< RtmTask > syncList = new ContentProviderSyncableList< RtmTask >( localValues,
                                                                                                                RtmTask.LESS_ID );
            final List< IContentProviderSyncOperation > operations = SyncDiffer.diff( serverValues,
                                                                                      syncList );
            properties.localBuilder.add( operations );
         }
            break;
         default :
            break;
      }
   }
   


   public RtmTaskNotes getNotes()
   {
      return notes == null ? new RtmTaskNotes() : notes;
   }
   


   private static void syncNotes( SyncProperties< RtmTaskSeries > properties,
                                  List< RtmTaskNote > serverValues,
                                  List< RtmTaskNote > localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( localValues,
                                                                                                                             RtmTaskNote.LESS_ID );
            final List< IContentProviderSyncOperation > noteOperations = SyncDiffer.diff( serverValues,
                                                                                          syncNotesList );
            properties.localBuilder.add( noteOperations );
         }
            break;
         default :
            break;
      }
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
   


   private static void syncTags( SyncProperties< RtmTaskSeries > properties,
                                 List< Tag > serverValues,
                                 List< Tag > localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            final ContentProviderSyncableList< Tag > syncList = new ContentProviderSyncableList< Tag >( localValues );
            final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( serverValues,
                                                                                          syncList );
            properties.localBuilder.add( syncOperations );
         }
            break;
         default :
            break;
      }
   }
   


   public ParticipantList getParticipants()
   {
      return participants == null ? new ParticipantList( id ) : participants;
   }
   


   private static void syncParticiapants( SyncProperties< RtmTaskSeries > properties,
                                          ParticipantList serverValues,
                                          ParticipantList localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            properties.localBuilder.add( localValues.computeContentProviderUpdateOperation( serverValues ) );
         }
            break;
         default :
            break;
      }
   }
   


   public String getLocationId()
   {
      return locationId;
   }
   


   private static SyncResultDirection syncLocationId( SyncProperties< RtmTaskSeries > properties,
                                                      String serverValue,
                                                      String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.LOCATION_ID,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      return dir;
   }
   


   public String getURL()
   {
      return url;
   }
   


   private static SyncResultDirection syncUrl( SyncProperties< RtmTaskSeries > properties,
                                               String serverValue,
                                               String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.URL,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      return dir;
   }
   


   public String getRecurrence()
   {
      return recurrence;
   }
   


   private static SyncResultDirection syncRecurrence( SyncProperties< RtmTaskSeries > properties,
                                                      String serverValue,
                                                      String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.RECURRENCE,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      return dir;
   }
   


   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
   }
   


   private static SyncResultDirection syncIsEveryRecurrence( SyncProperties< RtmTaskSeries > properties,
                                                             boolean serverValue,
                                                             boolean localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           TaskSeries.RECURRENCE_EVERY,
                                                           serverValue ? 1 : 0,
                                                           localValue ? 1 : 0,
                                                           Integer.class );
      return dir;
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
      final SyncProperties< RtmTaskSeries > properties = SyncProperties.newLocalOnlyInstance( serverElement,
                                                                                              this,
                                                                                              Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                                        id ) );
      return syncImpl( serverElement, this, properties ).localBuilder.build();
   }
   


   public TypedDirectedSyncOperations< RtmTaskSeries > computeMergeOperations( RtmTimeline timeline,
                                                                               ModificationList modifications,
                                                                               RtmTaskSeries serverElement,
                                                                               RtmTaskSeries localElement )
   {
      final SyncProperties< RtmTaskSeries > properties = SyncProperties.newInstance( SyncDirection.BOTH,
                                                                                     serverElement,
                                                                                     localElement,
                                                                                     Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                               id ),
                                                                                     modifications,
                                                                                     timeline );
      return syncImpl( serverElement, localElement, properties ).getOperations();
   }
   


   public IServerSyncOperation< RtmTaskSeries > computeServerUpdateOperation( RtmTimeline timeline,
                                                                              ModificationList modifications )
   {
      final SyncProperties< RtmTaskSeries > properties = SyncProperties.newInstance( SyncDirection.SERVER_ONLY,
                                                                                     this,
                                                                                     this,
                                                                                     Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                               id ),
                                                                                     modifications,
                                                                                     timeline );
      return syncImpl( this, this, properties ).serverBuilder.build();
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
   


   private SyncProperties< RtmTaskSeries > syncImpl( RtmTaskSeries serverElement,
                                                     RtmTaskSeries localElement,
                                                     SyncProperties< RtmTaskSeries > properties )
   {
      SyncUtils.doPreSyncCheck( localElement.id, serverElement.id, properties );
      
      syncListId( properties, serverElement.listId, localElement.listId );
      
      syncCreatedDate( properties, serverElement.created, localElement.created );
      
      syncModifiedDate( properties,
                        serverElement.modified,
                        localElement.modified );
      
      syncName( properties, serverElement.name, localElement.name );
      
      syncSource( properties, serverElement.source, localElement.source );
      
      // TODO: sync tasks
      
      syncNotes( properties,
                 serverElement.notes.getNotes(),
                 localElement.notes.getNotes() );
      
      syncLocationId( properties,
                      serverElement.locationId,
                      localElement.locationId );
      
      syncUrl( properties, serverElement.url, localElement.url );
      
      syncRecurrence( properties,
                      serverElement.recurrence,
                      localElement.recurrence );
      
      syncIsEveryRecurrence( properties,
                             serverElement.isEveryRecurrence,
                             localElement.isEveryRecurrence );
      
      syncTags( properties, serverElement.tags, localElement.tags );
      
      syncParticiapants( properties,
                         serverElement.participants,
                         localElement.participants );
      
      return properties;
   }
}
