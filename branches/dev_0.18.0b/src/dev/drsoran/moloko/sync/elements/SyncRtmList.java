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

import java.util.Comparator;
import java.util.Date;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.db.CreationsProviderPart;
import dev.drsoran.moloko.content.db.DbHelper;
import dev.drsoran.moloko.content.db.ModificationsProviderPart;
import dev.drsoran.moloko.content.db.RtmListsTable;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.RtmListServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class SyncRtmList implements IContentProviderSyncable< SyncRtmList >,
         IServerSyncable< SyncRtmList, RtmList >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = SyncRtmList.class.toString();
   
   
   private final static class LessIdComperator implements
            Comparator< SyncRtmList >
   {
      public int compare( SyncRtmList object1, SyncRtmList object2 )
      {
         return object1.list.getId().compareTo( object2.list.getId() );
      }
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final RtmList list;
   
   

   public SyncRtmList( RtmList list )
   {
      if ( list == null )
         throw new NullPointerException( "list is null" );
      
      this.list = list;
   }
   


   public String getId()
   {
      return list.getId();
   }
   


   public String getName()
   {
      return list.getName();
   }
   


   public RtmSmartFilter getSmartFilter()
   {
      return list.getSmartFilter();
   }
   


   public Date getCreatedDate()
   {
      return list.getCreatedDate();
   }
   


   public Date getModifiedDate()
   {
      return list.getModifiedDate();
   }
   


   public Date getDeletedDate()
   {
      return list.getDeletedDate();
   }
   


   public boolean hasModification( ModificationSet modificationSet )
   {
      return modificationSet.hasModification( DbHelper.contentUriWithId( Lists.CONTENT_URI,
                                                                        list.getId() ) );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Lists.CONTENT_URI )
                                                                             .withValues( RtmListsTable.getContentValues( list,
                                                                                                                                 true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation handleAfterServerInsert( SyncRtmList serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      /**
       * Change the ID of the local list to the ID of the server list.
       **/
      operation.add( ContentProviderOperation.newUpdate( DbHelper.contentUriWithId( Lists.CONTENT_URI,
                                                                                   list.getId() ) )
                                             .withValue( Lists._ID,
                                                         serverElement.list.getId() )
                                             .build() );
      
      /** Remove the old list from the creations table, marking this list as send **/
      operation.add( CreationsProviderPart.deleteCreation( Lists.CONTENT_URI,
                                                           list.getId() ) );
      
      /** Remove all modifications with the old list ID **/
      operation.add( ModificationsProviderPart.getRemoveModificationOps( Lists.CONTENT_URI,
                                                                         list.getId() ) );
      
      return operation.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( SyncRtmList serverElement )
   {
      if ( !getId().equals( serverElement.getId() ) )
         throw new IllegalArgumentException( "Update id "
            + serverElement.getId() + " differs this id " + getId() );
      
      final Uri uri = DbHelper.contentUriWithId( Lists.CONTENT_URI, getId() );
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( list.getName(), serverElement.list.getName() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.LIST_NAME,
                                                         serverElement.list.getName() )
                                             .build() );
      
      if ( SyncUtils.hasChanged( MolokoDateUtils.getTime( list.getCreatedDate() ),
                                 MolokoDateUtils.getTime( serverElement.list.getCreatedDate() ) ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.CREATED_DATE,
                                                         MolokoDateUtils.getTime( serverElement.list.getCreatedDate() ) )
                                             .build() );
      
      if ( SyncUtils.hasChanged( MolokoDateUtils.getTime( list.getModifiedDate() ),
                                 MolokoDateUtils.getTime( serverElement.list.getModifiedDate() ) ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.MODIFIED_DATE,
                                                         MolokoDateUtils.getTime( serverElement.list.getModifiedDate() ) )
                                             .build() );
      
      if ( SyncUtils.hasChanged( MolokoDateUtils.getTime( list.getDeletedDate() ),
                                 MolokoDateUtils.getTime( serverElement.list.getDeletedDate() ) ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.LIST_DELETED,
                                                         MolokoDateUtils.getTime( serverElement.list.getDeletedDate() ) )
                                             .build() );
      
      if ( SyncUtils.hasChanged( list.getLocked(),
                                 serverElement.list.getLocked() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.LOCKED,
                                                         serverElement.list.getLocked() )
                                             .build() );
      
      if ( SyncUtils.hasChanged( list.getArchived(),
                                 serverElement.list.getArchived() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.ARCHIVED,
                                                         serverElement.list.getArchived() )
                                             .build() );
      
      if ( SyncUtils.hasChanged( list.getPosition(),
                                 serverElement.list.getPosition() ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.POSITION,
                                                         serverElement.list.getPosition() )
                                             .build() );
      
      // This list becomes smart
      if ( list.getSmartFilter() == null
         && serverElement.list.getSmartFilter() != null )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.IS_SMART_LIST, 1 )
                                             .build() );
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.FILTER,
                                                         serverElement.list.getSmartFilter()
                                                                           .getFilterString() )
                                             .build() );
      }
      
      // This list becomes stupid
      else if ( list.getSmartFilter() != null
         && serverElement.list.getSmartFilter() == null )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.IS_SMART_LIST, 0 )
                                             .build() );
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.FILTER, null )
                                             .build() );
      }
      
      // Filter has changed
      else if ( list.getSmartFilter() != null
         && serverElement.list.getSmartFilter() != null
         && !list.getSmartFilter()
                 .getFilterString()
                 .equals( serverElement.list.getSmartFilter().getFilterString() ) )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.FILTER,
                                                         serverElement.list.getSmartFilter()
                                                                           .getFilterString() )
                                             .build() );
      }
      
      return result.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( DbHelper.contentUriWithId( Lists.CONTENT_URI,
                                                                                                                   list.getId() ) )
                                                                             .build() )
                                         .build();
   }
   


   public IServerSyncOperation< RtmList > computeServerUpdateOperation( RtmTimeline timeline,
                                                                        ModificationSet modifications,
                                                                        SyncRtmList serverElement )
   {
      RtmListServerSyncOperation.Builder< RtmList > operation = RtmListServerSyncOperation.newUpdate();
      
      // In case we have no server element (incremental sync)
      if ( serverElement == null )
         serverElement = this;
      
      final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                         ? null
                                                                                         : serverElement.getModifiedDate(),
                                                                    getModifiedDate(),
                                                                    DbHelper.contentUriWithId( Lists.CONTENT_URI,
                                                                                              list.getId() ),
                                                                    modifications );
      // List Name
      if ( SyncUtils.getSyncDirection( properties,
                                       Lists.LIST_NAME,
                                       serverElement.list.getName(),
                                       list.getName(),
                                       String.class ) == SyncResultDirection.SERVER )
      {
         operation.add( timeline.lists_setName( list.getId(), list.getName() ),
                        properties.getModification( Lists.LIST_NAME ) );
      }
      
      return operation.build( RtmListServerSyncOperation.class );
   }
   


   public IServerSyncOperation< RtmList > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return RtmListServerSyncOperation.newDelete( timeLine.lists_delete( list.getId() ) )
                                       .build( RtmListServerSyncOperation.class );
   }
}
