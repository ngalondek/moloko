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

package dev.drsoran.moloko.sync.elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.db.CreationsProviderPart;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.content.db.RtmNotesTable;
import dev.drsoran.moloko.content.db.TableColumns.Notes;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.ModificationSet;
import dev.drsoran.moloko.domain.model.ModificationsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.NoteServerSyncOperation;
import dev.drsoran.moloko.sync.operation.ServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;


public class SyncNote implements IContentProviderSyncable< SyncNote >,
         IServerSyncable< SyncNote, RtmTaskNote >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = SyncNote.class.toString();
   
   
   private final static class LessIdComperator implements Comparator< SyncNote >
   {
      public int compare( SyncNote object1, SyncNote object2 )
      {
         return object1.note.getId().compareTo( object2.note.getId() );
      }
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final RtmTaskSeries taskSeries;
   
   private final RtmTaskNote note;
   
   
   
   public SyncNote( RtmTaskSeries taskSeries, RtmTaskNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      // The taskSeries is only needed for the case we add a new node
      // to RTM. But this is only the local->server direction.
      // So this may be null in case of server->local direction.
      this.taskSeries = taskSeries;
      this.note = note;
   }
   
   
   
   public String getId()
   {
      return note.getId();
   }
   
   
   
   public String getTaskSeriesId()
   {
      return note.getTaskSeriesId();
   }
   
   
   
   public String getTaskId()
   {
      return taskSeries != null ? taskSeries.getTasks().get( 0 ).getId() : null;
   }
   
   
   
   public String getListId()
   {
      return taskSeries != null ? taskSeries.getListId() : null;
   }
   
   
   
   public Date getCreatedDate()
   {
      return note.getCreatedDate();
   }
   
   
   
   public Date getModifiedDate()
   {
      return note.getModifiedDate();
   }
   
   
   
   public Date getDeletedDate()
   {
      return note.getDeletedDate();
   }
   
   
   
   public String getTitle()
   {
      return note.getTitle();
   }
   
   
   
   public String getText()
   {
      return note.getText();
   }
   
   
   
   public boolean hasModification( ModificationSet modificationSet )
   {
      return modificationSet.hasModification( DbUtils.contentUriWithId( Notes.CONTENT_URI,
                                                                        note.getId() ) );
   }
   
   
   
   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                                                             .withValues( RtmNotesTable.getContentValues( note,
                                                                                                                                 true ) )
                                                                             .build() )
                                         .build();
   }
   
   
   
   public IContentProviderSyncOperation handleAfterServerInsert( SyncNote serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      /**
       * Change the ID of the local note to the ID of the server note.
       **/
      operation.add( ContentProviderOperation.newUpdate( DbUtils.contentUriWithId( Notes.CONTENT_URI,
                                                                                   note.getId() ) )
                                             .withValue( Notes._ID,
                                                         serverElement.note.getId() )
                                             .build() );
      
      /** Remove the old note from the creations table, marking this note as send **/
      operation.add( CreationsProviderPart.deleteCreation( Notes.CONTENT_URI,
                                                           note.getId() ) );
      
      /** Remove all modifications with the old note ID **/
      operation.add( ModificationsProviderPart.getRemoveModificationOps( Notes.CONTENT_URI,
                                                                         note.getId() ) );
      
      return operation.build();
   }
   
   
   
   public IContentProviderSyncOperation computeContentProviderUpdateOperation( SyncNote serverElement )
   {
      if ( !note.getId().equals( serverElement.note.getId() ) )
         throw new IllegalArgumentException( "Update id "
            + serverElement.note.getId() + " differs this id " + note.getId() );
      
      final Uri uri = DbUtils.contentUriWithId( Notes.CONTENT_URI, note.getId() );
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.isDifferent( note.getCreatedDate(),
                                 serverElement.getCreatedDate() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_CREATED_DATE,
                                                         MolokoDateUtils.getTime( serverElement.getCreatedDate() ) )
                                             .build() );
      
      if ( SyncUtils.isDifferent( note.getModifiedDate(),
                                 serverElement.getModifiedDate() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_MODIFIED_DATE,
                                                         MolokoDateUtils.getTime( serverElement.getModifiedDate() ) )
                                             .build() );
      
      if ( SyncUtils.isDifferent( note.getTitle(), serverElement.note.getTitle() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_TITLE,
                                                         serverElement.note.getTitle() )
                                             .build() );
      
      if ( SyncUtils.isDifferent( note.getText(), serverElement.note.getText() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_TEXT,
                                                         serverElement.note.getText() )
                                             .build() );
      
      return result.build();
   }
   
   
   
   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( DbUtils.contentUriWithId( Notes.CONTENT_URI,
                                                                                                                   note.getId() ) )
                                                                             .build() )
                                         .build();
   }
   
   
   
   public IServerSyncOperation< RtmTaskNote > computeServerUpdateOperation( RtmTimeline timeline,
                                                                            ModificationSet modifications,
                                                                            SyncNote serverElement )
   {
      ServerSyncOperation.Builder< RtmTaskNote > operation = ServerSyncOperation.newUpdate();
      
      // In case we have no server element (incremental sync)
      if ( serverElement == null )
         serverElement = this;
      
      final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                         ? null
                                                                                         : serverElement.getModifiedDate(),
                                                                    getModifiedDate(),
                                                                    DbUtils.contentUriWithId( Notes.CONTENT_URI,
                                                                                              note.getId() ),
                                                                    modifications );
      // Title and Text
      if ( SyncUtils.getSyncDirection( properties,
                                       Notes.NOTE_TITLE,
                                       serverElement.note.getTitle(),
                                       note.getTitle(),
                                       String.class ) == SyncResultDirection.SERVER
         || SyncUtils.getSyncDirection( properties,
                                        Notes.NOTE_TEXT,
                                        serverElement.note.getText(),
                                        note.getText(),
                                        String.class ) == SyncResultDirection.SERVER )
      {
         final List< Modification > modsList = new ArrayList< Modification >( 2 );
         
         if ( properties.getModification( Notes.NOTE_TITLE ) != null )
            modsList.add( properties.getModification( Notes.NOTE_TITLE ) );
         
         if ( properties.getModification( Notes.NOTE_TEXT ) != null )
            modsList.add( properties.getModification( Notes.NOTE_TEXT ) );
         
         operation.add( timeline.tasks_notes_edit( note.getTaskSeriesId(),
                                                   note.getId(),
                                                   Strings.emptyIfNull( note.getTitle() ),
                                                   Strings.emptyIfNull( note.getText() ) ),
                        modsList );
      }
      
      return operation.build( NoteServerSyncOperation.class );
   }
   
   
   
   public IServerSyncOperation< RtmTaskNote > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return ServerSyncOperation.newDelete( timeLine.tasks_notes_delete( note.getTaskSeriesId(),
                                                                         note.getId() ) )
                                .build( NoteServerSyncOperation.class );
   }
}
