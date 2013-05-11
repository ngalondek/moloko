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
import java.util.Date;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.content.db.RtmContactsTable;
import dev.drsoran.moloko.content.db.TableColumns.Contacts;
import dev.drsoran.moloko.content.db.TableColumns.Participants;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;


public class RtmContact extends RtmData implements
         IContentProviderSyncable< RtmContact >
{
   @SuppressWarnings( "unused" )
   private static final String TAG = "Moloko."
      + RtmContact.class.getSimpleName();
   
   
   private final static class LessIdComperator implements
            Comparator< RtmContact >
   {
      public int compare( RtmContact object1, RtmContact object2 )
      {
         return object1.id.compareTo( object2.id );
      }
   }
   
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
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final String fullname;
   
   private final String username;
   
   

   public RtmContact( String id, String fullname, String username )
   {
      this.id = id;
      this.fullname = fullname;
      this.username = username;
   }
   


   public RtmContact( Element elt )
   {
      this.id = elt.getAttribute( "id" );
      this.fullname = elt.getAttribute( "fullname" );
      this.username = elt.getAttribute( "username" );
   }
   


   public RtmContact( Parcel source )
   {
      this.id = source.readString();
      this.fullname = source.readString();
      this.username = source.readString();
   }
   


   public String getId()
   {
      return id;
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
      return "RtmContact<" + id + ", " + fullname + ", " + username + ">";
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
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
                                                                             .withValues( RtmContactsTable.getContentValues( this,
                                                                                                                                    true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()

   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( DbUtils.contentUriWithId( Participants.CONTENT_URI,
                                                                                                                   id ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmContact update )
   {
      if ( !id.equals( update.id ) )
         throw new IllegalArgumentException( "Update id " + update.id
            + " differs this id " + id );
      
      final Uri uri = DbUtils.contentUriWithId( Participants.CONTENT_URI, id );
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.isDifferent( fullname, update.fullname ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Contacts.FULLNAME,
                                                         update.fullname )
                                             .build() );
      
      if ( SyncUtils.isDifferent( username, update.username ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Contacts.USERNAME,
                                                         update.fullname )
                                             .build() );
      
      return result.build();
   }
}
