/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.content.db.ParticipantsTable;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncDiffer;


public class ParticipantList implements
         IContentProviderSyncable< ParticipantList >, Parcelable
{
   public static final Parcelable.Creator< ParticipantList > CREATOR = new Parcelable.Creator< ParticipantList >()
   {
      
      @Override
      public ParticipantList createFromParcel( Parcel source )
      {
         return new ParticipantList( source );
      }
      
      
      
      @Override
      public ParticipantList[] newArray( int size )
      {
         return new ParticipantList[ size ];
      }
      
   };
   
   private final String taskSeriesId;
   
   private final List< Participant > participants;
   
   
   
   public ParticipantList( String taskSeriesId )
   {
      this.taskSeriesId = taskSeriesId;
      this.participants = new ArrayList< Participant >( 0 );
   }
   
   
   
   public ParticipantList( String taskSeriesId, List< Participant > participants )
   {
      this.taskSeriesId = taskSeriesId;
      this.participants = new ArrayList< Participant >( participants );
   }
   
   
   
   public ParticipantList( String taskSeriesId, Element elt )
   {
      this.taskSeriesId = taskSeriesId;
      
      final List< Element > contacts = RtmData.children( elt, "contact" );
      
      this.participants = new ArrayList< Participant >( contacts.size() );
      
      for ( Element contact : contacts )
      {
         final String contactId = RtmData.textNullIfEmpty( contact, "id" );
         final String fullname = RtmData.textNullIfEmpty( contact, "fullname" );
         final String username = RtmData.textNullIfEmpty( contact, "username" );
         
         if ( !TextUtils.isEmpty( contactId ) && !TextUtils.isEmpty( fullname )
            && !TextUtils.isEmpty( username ) )
         {
            this.participants.add( new Participant( null,
                                                    taskSeriesId,
                                                    contactId,
                                                    fullname,
                                                    username ) );
         }
         else
         {
            MolokoApp.Log.e( getClass(),
                             "Invalid attribute 'id' in participating contact. "
                                + contactId );
         }
      }
   }
   
   
   
   public ParticipantList( Parcel source )
   {
      this.taskSeriesId = source.readString();
      this.participants = source.createTypedArrayList( Participant.CREATOR );
   }
   
   
   
   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   
   
   
   public List< Participant > getParticipants()
   {
      return Collections.unmodifiableList( participants );
   }
   
   
   
   public void addParticipant( Participant participant )
   {
      participants.add( participant );
   }
   
   
   
   public int getCount()
   {
      return participants.size();
   }
   
   
   
   @Override
   public int describeContents()
   {
      return 0;
   }
   
   
   
   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( taskSeriesId );
      dest.writeTypedList( participants );
   }
   
   
   
   @Override
   public Date getDeletedDate()
   {
      return null;
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ParticipantsTable.insertParticipants( this ) )
                                         .build();
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newDelete();
      
      for ( Participant participant : participants )
      {
         result.add( participant.computeContentProviderDeleteOperation() );
      }
      
      return result.build();
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ParticipantList update )
   {
      final ContentProviderSyncableList< Participant > syncList = new ContentProviderSyncableList< Participant >( participants,
                                                                                                                  Participant.LESS_CONTACT_ID );
      final List< IContentProviderSyncOperation > operations = SyncDiffer.inDiff( update.participants,
                                                                                  syncList,
                                                                                  true /* always full sync */);
      
      if ( operations.size() > 0 )
         return ContentProviderSyncOperation.newUpdate()
                                            .add( operations )
                                            .build();
      else
         return NoopContentProviderSyncOperation.INSTANCE;
   }
}
