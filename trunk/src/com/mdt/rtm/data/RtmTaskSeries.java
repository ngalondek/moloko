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
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.grammar.RecurrenceParser;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.CompositeContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.SyncUtils;
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
   
   private final List< String > tags;
   
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
      this.tags = tags;
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
         this.tasks.add( new RtmTask( task ) );
      }
      
      this.notes = new RtmTaskNotes( child( elt, "notes" ) );
      this.locationId = textNullIfEmpty( elt, "location_id" );
      this.url = textNullIfEmpty( elt, "url" );
      
      final Element elementTags = child( elt, "tags" );
      
      if ( elementTags.getChildNodes().getLength() > 0 )
      {
         final List< Element > elementTagList = children( elementTags, "tag" );
         this.tags = new ArrayList< String >( elementTagList.size() );
         
         for ( Element elementTag : elementTagList )
         {
            final String tag = text( elementTag );
            if ( !TextUtils.isEmpty( tag ) )
            {
               this.tags.add( tag );
            }
         }
      }
      else
      {
         // do not use Collections.emptyList() here cause we
         // need a mutable list.
         this.tags = new ArrayList< String >( 0 );
      }
      
      final Element elementParticipants = child( elt, "participants" );
      
      if ( elementParticipants.getChildNodes().getLength() > 0 )
      {
         this.participants = new ParticipantList( id, elementParticipants );
      }
      else
      {
         this.participants = new ParticipantList( id );
      }
      
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
         this.tasks.add( new RtmTask( task, deleted ) );
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
      tags = source.createStringArrayList();
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
      return notes;
   }
   


   public List< String > getTags()
   {
      return tags;
   }
   


   public ParticipantList getParticipants()
   {
      return participants;
   }
   


   public boolean isDeleted()
   {
      return deleted != null;
   }
   


   public Date getDeletedDate()
   {
      return deleted != null ? deleted.getDate() : null;
   }
   


   public ArrayList< Tag > getTagObjects()
   {
      ArrayList< Tag > tags = new ArrayList< Tag >( this.tags != null
                                                                     ? this.tags.size()
                                                                     : 0 );
      
      if ( this.tags != null && this.tags.size() > 0 )
      {
         for ( String tagStr : this.tags )
         {
            tags.add( new Tag( id, tagStr ) );
         }
      }
      
      return tags;
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
      dest.writeStringList( tags );
      dest.writeParcelable( participants, flags );
      dest.writeParcelable( deleted, flags );
   }
   


   private final static String ensureRecurrencePatternOrder( String recurrencePattern )
   {
      final String[] operators = recurrencePattern.split( RecurrenceParser.OPERATOR_SEP );
      Arrays.sort( operators, RecurrenceParser.CMP_OPERATORS );
      
      return TextUtils.join( RecurrenceParser.OPERATOR_SEP, operators );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      try
      {
         return new ContentProviderSyncOperation( provider,
                                                  RtmTaskSeriesProviderPart.insertTaskSeries( provider,
                                                                                              this ),
                                                  IContentProviderSyncOperation.Op.INSERT );
         
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, "ContentProvider insert failed. ", e );
         return null;
      }
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      // Referenced parts like RawTasks and Notes are deleted by a DB trigger.
      // @see RtmTaskSeriesPart::create
      //
      // These deletions have not to be counted cause they are implementation
      // details that all belong to a taskseries.
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newDelete( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                             id ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.DELETE );
   }
   


   public DirectedSyncOperations computeMergeOperations( Service service,
                                                         ContentProviderClient provider,
                                                         RtmTaskSeries serverElement,
                                                         Object... params )
   {
      final List< ISyncOperation > serverOps = new LinkedList< ISyncOperation >();
      final List< IContentProviderSyncOperation > localOps = new LinkedList< IContentProviderSyncOperation >();
      
      boolean ok = true;
      
      if ( ok )
      {
         // Update notes
         final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( provider,
                                                                                                                          notes.getNotes(),
                                                                                                                          RtmTaskNote.LESS_ID );
         
         final ArrayList< IContentProviderSyncOperation > noteOperations = SyncDiffer.diff( serverElement.notes.getNotes(),
                                                                                            syncNotesList,
                                                                                            id );
         ok = noteOperations != null;
         
         if ( ok )
         {
            if ( noteOperations.size() > 0 )
               localOps.addAll( noteOperations );
            
            // Update tags
            if ( ok )
            {
               final IContentProviderSyncOperation tagOperation = updateTags( provider,
                                                                              serverElement );
               ok = tagOperation != null;
               if ( ok
                  && tagOperation.getOperationType() != IContentProviderSyncOperation.Op.NOOP )
                  localOps.add( tagOperation );
            }
            
            // Update tasks
            if ( ok )
            {
               final ContentProviderSyncableList< RtmTask > syncTasksList = new ContentProviderSyncableList< RtmTask >( provider,
                                                                                                                        tasks,
                                                                                                                        RtmTask.LESS_ID );
               final ArrayList< IContentProviderSyncOperation > taskOperations = SyncDiffer.diff( serverElement.tasks,
                                                                                                  syncTasksList,
                                                                                                  id );
               ok = taskOperations != null;
               
               if ( ok && taskOperations.size() > 0 )
                  localOps.addAll( taskOperations );
            }
            
            // Update participants
            if ( ok )
            {
               final IContentProviderSyncOperation partOperation = participants.computeContentProviderUpdateOperation( provider,
                                                                                                                       serverElement.participants );
               ok = partOperation != null;
               
               if ( ok
                  && !( partOperation instanceof NoopContentProviderSyncOperation ) )
                  localOps.add( partOperation );
            }
            
            // Update taskseries
            if ( ok )
            {
               final Uri uri = Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                         id );
               
               final CompositeContentProviderSyncOperation taskSeriesOperation = new CompositeContentProviderSyncOperation( provider,
                                                                                                                            IContentProviderSyncOperation.Op.UPDATE );
               SyncUtils.updateDate( created,
                                     serverElement.created,
                                     uri,
                                     TaskSeries.TASKSERIES_CREATED_DATE,
                                     taskSeriesOperation );
               
               SyncUtils.updateDate( modified,
                                     serverElement.modified,
                                     uri,
                                     TaskSeries.MODIFIED_DATE,
                                     taskSeriesOperation );
               
               if ( Strings.hasStringChanged( listId, serverElement.listId ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.LIST_ID,
                                                                               serverElement.listId )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( name, serverElement.name ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.TASKSERIES_NAME,
                                                                               serverElement.name )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( source, serverElement.source ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.SOURCE,
                                                                               serverElement.source )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( locationId,
                                              serverElement.locationId ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.LOCATION_ID,
                                                                               serverElement.locationId )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( url, serverElement.url ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.URL,
                                                                               serverElement.url )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( recurrence,
                                              serverElement.recurrence ) )
               {
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.RECURRENCE,
                                                                               serverElement.recurrence )
                                                                   .build() );
                  if ( TextUtils.isEmpty( serverElement.recurrence ) )
                  {
                     taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                      .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                                  null )
                                                                      .build() );
                  }
                  else if ( isEveryRecurrence != serverElement.isEveryRecurrence )
                     taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                      .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                                  serverElement.isEveryRecurrence
                                                                                                                 ? 1
                                                                                                                 : 0 )
                                                                      .build() );
               }
               
               if ( taskSeriesOperation.plainSize() > 0 )
                  localOps.add( taskSeriesOperation );
            }
         }
      }
      
      if ( !ok )
         return null;
      else
         return new DirectedSyncOperations( serverOps, localOps );
   }
   


   private IContentProviderSyncOperation updateTags( ContentProviderClient provider,
                                                     RtmTaskSeries update )
   {
      final ArrayList< Tag > tags = TagsProviderPart.getAllTags( provider, id );
      
      if ( tags != null )
      {
         final ContentProviderSyncableList< Tag > syncList = new ContentProviderSyncableList< Tag >( provider,
                                                                                                     tags );
         
         final ArrayList< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( update.getTagObjects(),
                                                                                            syncList );
         
         if ( syncOperations != null )
         {
            if ( syncOperations.size() > 0 )
               return new CompositeContentProviderSyncOperation( provider,
                                                                 syncOperations,
                                                                 IContentProviderSyncOperation.Op.UPDATE );
            else
               return NoopContentProviderSyncOperation.INSTANCE;
         }
      }
      
      return null;
   }
   


   public ISyncOperation computeServerInsertOperation( Service service,
                                                       Object... params )
   {
      return NoopSyncOperation.INSTANCE;
   }
   


   public ISyncOperation computeServerDeleteOperation( Service service,
                                                       Object... params )
   {
      return NoopSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation removeModifications( ContentProviderClient provider )
   {
      try
      {
         return ModificationsProviderPart.isModified( provider,
                                                      Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                id ) )
                                                                                      ? new ContentProviderSyncOperation( provider,
                                                                                                                          ModificationsProviderPart.getRemoveModificationOps( TaskSeries.CONTENT_URI,
                                                                                                                                                                              id ),
                                                                                                                          IContentProviderSyncOperation.Op.DELETE )
                                                                                      : NoopContentProviderSyncOperation.INSTANCE;
      }
      catch ( RemoteException e )
      {
         
         return null;
      }
   }
}
