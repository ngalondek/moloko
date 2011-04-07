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

import java.util.Date;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.RtmContactsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Contacts;
import dev.drsoran.provider.Rtm.Participants;


public class RtmContact extends RtmEntity implements
         IContentProviderSyncable< RtmContact >
{
   @SuppressWarnings( "unused" )
   private static final String TAG = "Moloko."
      + RtmContact.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmContact > CREATOR = new Parcelable.Creator< RtmContact >()
   {
      public RtmContact createFromParcel( Parcel source )
      {
         return new RtmContact( source );
      }
      


      public RtmContact[] newArray( int size )
      {
         return new RtmContact[ size ];
      }
   };
   
   public final static LessRtmIdComperator< RtmContact > LESS_RTM_ID = new LessRtmIdComperator< RtmContact >();
   
   private final String fullname;
   
   private final String username;
   
   

   public RtmContact( long id, String rtmId, String fullname, String username )
   {
      super( id, rtmId );
      
      this.fullname = fullname;
      this.username = username;
   }
   


   public RtmContact( Element elt )
   {
      super( textNullIfEmpty( elt, "id" ) );
      
      this.fullname = elt.getAttribute( "fullname" );
      this.username = elt.getAttribute( "username" );
   }
   


   public RtmContact( Parcel source )
   {
      super( source );
      
      this.fullname = source.readString();
      this.username = source.readString();
   }
   


   public String getFullname()
   {
      return fullname;
   }
   


   public String getUsername()
   {
      return username;
   }
   


   @Override
   public String toString()
   {
      return "RtmContact<" + super.toString() + ", " + fullname + ", "
         + username + ">";
   }
   


   @Override
   public int describeContents()
   {
      return 0;
   }
   


   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      super.writeToParcel( dest, flags );
      
      dest.writeString( fullname );
      dest.writeString( username );
   }
   


   public Date getDeletedDate()
   {
      return null;
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Contacts.CONTENT_URI )
                                                                             .withValues( RtmContactsProviderPart.getContentValues( this ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()

   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( Participants.CONTENT_URI,
                                                                                                                   id ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmContact update )
   {
      if ( !rtmId.equals( update.rtmId ) )
         throw new IllegalArgumentException( "Update id " + update.rtmId
            + " differs this id " + rtmId );
      
      final Uri uri = Queries.contentUriWithId( Participants.CONTENT_URI, id );
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( fullname, update.fullname ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Contacts.FULLNAME,
                                                         update.fullname )
                                             .build() );
      
      if ( SyncUtils.hasChanged( username, update.username ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Contacts.USERNAME,
                                                         update.fullname )
                                             .build() );
      
      return result.build();
   }
}
