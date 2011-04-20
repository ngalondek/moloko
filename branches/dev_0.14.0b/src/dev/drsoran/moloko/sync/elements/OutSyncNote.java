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

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.sync.operation.ServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Notes;


public class OutSyncNote implements IServerSyncable< OutSyncNote, RtmTaskNote >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = OutSyncNote.class.toString();
   
   
   private final static class LessIdComperator implements
            Comparator< OutSyncNote >
   {
      public int compare( OutSyncNote object1, OutSyncNote object2 )
      {
         return object1.note.getId().compareTo( object2.note.getId() );
      }
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final RtmTaskNote note;
   
   

   public OutSyncNote( RtmTaskNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      this.note = note;
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
   


   public IContentProviderSyncOperation computeServerInsertModification( OutSyncNote serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      return operation.build();
   }
   


   public IServerSyncOperation< RtmTaskNote > computeServerUpdateOperation( RtmTimeline timeline,
                                                                            ModificationSet modifications,
                                                                            OutSyncNote serverElement )
   {
      ServerSyncOperation.Builder< RtmTaskNote > operation = ServerSyncOperation.newUpdate();
      
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
      
      return operation.build();
   }
   


   public IServerSyncOperation< RtmTaskNote > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.newInstance();
   }
}
