/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

import java.util.Comparator;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Participants;


public class Participant implements IContentProviderSyncable< Participant >,
         Parcelable
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + Participant.class.getSimpleName();
   
   
   private static final class LessIdComperator implements
            Comparator< Participant >
   {
      public int compare( Participant object1, Participant object2 )
      {
         return object1.contactId.compareTo( object2.contactId );
      }
      
   }
   
   public static final Parcelable.Creator< Participant > CREATOR = new Parcelable.Creator< Participant >()
   {
      
      public Participant createFromParcel( Parcel source )
      {
         return new Participant( source );
      }
      


      public Participant[] newArray( int size )
      {
         return new Participant[ size ];
      }
      
   };
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final String taskSeriesId;
   
   private final String contactId;
   
   private final String fullname;
   
   private final String username;
   
   

   public Participant( String id, String taskSeriesId, String contactId,
      String fullname, String username )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.contactId = contactId;
      this.fullname = fullname;
      this.username = username;
   }
   


   public Participant( Parcel source )
   {
      this.id = source.readString();
      this.taskSeriesId = source.readString();
      this.contactId = source.readString();
      this.fullname = source.readString();
      this.username = source.readString();
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public String getContactId()
   {
      return contactId;
   }
   


   public String getFullname()
   {
      return fullname;
   }
   


   public String getUsername()
   {
      return username;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( taskSeriesId );
      dest.writeString( contactId );
      dest.writeString( fullname );
      dest.writeString( username );
   }
   


   public Uri getContentUriWithId()
   {
      return Queries.contentUriWithId( Participants.CONTENT_URI, id );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Participants.CONTENT_URI )
                                                                             .withValues( ParticipantsProviderPart.getContentValues( this,
                                                                                                                                     true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Participants.CONTENT_URI )
                                                                             .withSelection( Participants.TASKSERIES_ID
                                                                                                + " = ? AND "
                                                                                                + Participants.CONTACT_ID
                                                                                                + " = ?",
                                                                                             new String[]
                                                                                             {
                                                                                              taskSeriesId,
                                                                                              contactId } )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( Participant update )
   {
      final Uri uri = getContentUriWithId();
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( contactId, update.contactId ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Participants.CONTACT_ID,
                                                         update.contactId )
                                             .build() );
      
      if ( SyncUtils.hasChanged( fullname, update.fullname ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Participants.FULLNAME,
                                                         update.fullname )
                                             .build() );
      
      if ( SyncUtils.hasChanged( username, update.username ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Participants.USERNAME,
                                                         update.username )
                                             .build() );
      
      return result.build();
   }
   
}
