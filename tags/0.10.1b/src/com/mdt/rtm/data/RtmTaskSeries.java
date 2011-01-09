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
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Element;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.grammar.RecurrenceParser;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.CompositeContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.util.ParamChecker;
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
   
   @SuppressWarnings( "unused" )
   private static final Logger log = Logger.getLogger( "TaskSeries" );
   
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
   
   private final boolean deleted;
   
   

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
      this.deleted = false;
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
      
      this.deleted = false;
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
      this.deleted = deleted;
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
      deleted = source.readInt() != 0;
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getListId()
   {
      return listId;
   }
   


   public Date getCreated()
   {
      return ( created != null ) ? created.getDate() : null;
   }
   


   public Date getModified()
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
      return deleted;
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
      dest.writeInt( deleted ? 1 : 0 );
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
      ContentProviderSyncOperation operation = null;
      
      final boolean ok = ParamChecker.checkParams( TAG,
                                                   "ContentProvider insert failed. ",
                                                   new Class[]
                                                   { String.class },
                                                   params );
      
      if ( ok )
      {
         final String listId = (String) params[ 0 ];
         
         try
         {
            operation = new ContentProviderSyncOperation( provider,
                                                          RtmTaskSeriesProviderPart.insertTaskSeries( provider,
                                                                                                      listId,
                                                                                                      this ),
                                                          IContentProviderSyncOperation.Op.INSERT );
         }
         catch ( NullPointerException e )
         {
            Log.e( TAG, "ContentProvider insert failed. ", e );
         }
         catch ( RemoteException e )
         {
            Log.e( TAG, "ContentProvider insert failed. ", e );
         }
      }
      
      return operation;
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
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               RtmTaskSeries update,
                                                                               Object... params )
   {
      CompositeContentProviderSyncOperation result = null;
      
      boolean ok = true;
      
      if ( !update.id.equals( id ) )
      {
         ok = false;
         Log.e( TAG,
                "ContentProvider update failed. Different RtmTaskSeries IDs." );
      }
      
      if ( ok )
      {
         // Update notes
         final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( provider,
                                                                                                                          notes.getNotes(),
                                                                                                                          RtmTaskNote.LESS_ID );
         
         final ArrayList< IContentProviderSyncOperation > noteOperations = SyncDiffer.diff( update.notes.getNotes(),
                                                                                            syncNotesList,
                                                                                            id );
         ok = noteOperations != null;
         
         if ( ok )
         {
            result = new CompositeContentProviderSyncOperation( provider,
                                                                IContentProviderSyncOperation.Op.UPDATE );
            
            if ( noteOperations.size() > 0 )
               result.addAll( noteOperations );
            
            // Update tags
            if ( ok )
            {
               final IContentProviderSyncOperation tagOperation = updateTags( provider,
                                                                              update );
               ok = tagOperation != null;
               if ( ok
                  && tagOperation.getOperationType() != IContentProviderSyncOperation.Op.NOOP )
                  result.add( tagOperation );
            }
            
            // Update tasks
            if ( ok )
            {
               final ContentProviderSyncableList< RtmTask > syncTasksList = new ContentProviderSyncableList< RtmTask >( provider,
                                                                                                                        tasks,
                                                                                                                        RtmTask.LESS_ID );
               final ArrayList< IContentProviderSyncOperation > taskOperations = SyncDiffer.diff( update.tasks,
                                                                                                  syncTasksList,
                                                                                                  id );
               ok = taskOperations != null;
               
               if ( ok && taskOperations.size() > 0 )
                  result.addAll( taskOperations );
            }
            
            // Update participants
            if ( ok )
            {
               final IContentProviderSyncOperation partOperation = participants.computeContentProviderUpdateOperation( provider,
                                                                                                                       update.participants );
               ok = partOperation != null;
               
               if ( ok
                  && !( partOperation instanceof NoopContentProviderSyncOperation ) )
                  result.add( partOperation );
            }
            
            // Update taskseries
            if ( ok )
            {
               final Uri uri = Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                         id );
               
               final CompositeContentProviderSyncOperation taskSeriesOperation = new CompositeContentProviderSyncOperation( provider,
                                                                                                                            IContentProviderSyncOperation.Op.UPDATE );
               SyncUtils.updateDate( created,
                                     update.created,
                                     uri,
                                     TaskSeries.TASKSERIES_CREATED_DATE,
                                     taskSeriesOperation );
               
               SyncUtils.updateDate( modified,
                                     update.modified,
                                     uri,
                                     TaskSeries.MODIFIED_DATE,
                                     taskSeriesOperation );
               
               if ( Strings.hasStringChanged( listId, update.listId ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.LIST_ID,
                                                                               update.listId )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( name, update.name ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.TASKSERIES_NAME,
                                                                               update.name )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( source, update.source ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.SOURCE,
                                                                               update.source )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( locationId, update.locationId ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.LOCATION_ID,
                                                                               update.locationId )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( url, update.url ) )
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.URL,
                                                                               update.url )
                                                                   .build() );
               
               if ( Strings.hasStringChanged( recurrence, update.recurrence ) )
               {
                  taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                   .withValue( TaskSeries.RECURRENCE,
                                                                               update.recurrence )
                                                                   .build() );
                  if ( TextUtils.isEmpty( update.recurrence ) )
                  {
                     taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                      .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                                  null )
                                                                      .build() );
                  }
                  else if ( isEveryRecurrence != update.isEveryRecurrence )
                     taskSeriesOperation.add( ContentProviderOperation.newUpdate( uri )
                                                                      .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                                  update.isEveryRecurrence
                                                                                                          ? 1
                                                                                                          : 0 )
                                                                      .build() );
               }
               
               if ( taskSeriesOperation.plainSize() > 0 )
                  result.add( taskSeriesOperation );
            }
         }
      }
      
      if ( !ok )
         return null;
      else
         return ( result == null || result.plainSize() > 0 )
                                                            ? result
                                                            : NoopContentProviderSyncOperation.INSTANCE;
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
}