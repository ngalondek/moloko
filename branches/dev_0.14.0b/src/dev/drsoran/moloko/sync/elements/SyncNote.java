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
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.sync.operation.NoteServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Notes;


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
   
   private final RtmTaskNote note;
   
   

   public SyncNote( RtmTaskNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      this.note = note;
   }
   


   public String getId()
   {
      return note.getId();
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
   


   public boolean isNew()
   {
      return false;
   }
   


   public boolean hasModification( ModificationSet modificationSet )
   {
      return modificationSet.hasModification( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                        note.getId() ) );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                                                             .withValues( RtmNotesProviderPart.getContentValues( note,
                                                                                                                                 true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( SyncNote serverElement )
   {
      if ( !note.getId().equals( serverElement.note.getId() ) )
         throw new IllegalArgumentException( "Update id "
            + serverElement.note.getId() + " differs this id " + note.getId() );
      
      final Uri uri = Queries.contentUriWithId( Notes.CONTENT_URI, note.getId() );
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( note.getCreatedDate(),
                                 serverElement.getCreatedDate() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_CREATED_DATE,
                                                         MolokoDateUtils.getTime( serverElement.getCreatedDate() ) )
                                             .build() );
      
      if ( SyncUtils.hasChanged( note.getModifiedDate(),
                                 serverElement.getModifiedDate() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_MODIFIED_DATE,
                                                         MolokoDateUtils.getTime( serverElement.getModifiedDate() ) )
                                             .build() );
      
      if ( SyncUtils.hasChanged( note.getTitle(), serverElement.note.getTitle() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_TITLE,
                                                         serverElement.note.getTitle() )
                                             .build() );
      
      if ( SyncUtils.hasChanged( note.getText(), serverElement.note.getText() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_TEXT,
                                                         serverElement.note.getText() )
                                             .build() );
      
      return result.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                                                   note.getId() ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeServerInsertModification( SyncNote serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      return operation.build();
   }
   


   public IServerSyncOperation< RtmTaskNote > computeServerUpdateOperation( RtmTimeline timeline,
                                                                            ModificationSet modifications,
                                                                            SyncNote serverElement )
   {
      NoteServerSyncOperation.Builder< RtmTaskNote > operation = NoteServerSyncOperation.newUpdate();
      
      // In case we have no server element (incremental sync)
      if ( serverElement == null )
         serverElement = this;
      
      final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                         ? null
                                                                                         : serverElement.getModifiedDate(),
                                                                    getModifiedDate(),
                                                                    Queries.contentUriWithId( Notes.CONTENT_URI,
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
      return NoopServerSyncOperation.newInstance();
   }
   
}
