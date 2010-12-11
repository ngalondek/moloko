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

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.util.ParamChecker;
import dev.drsoran.provider.Rtm.Participants;


public class Participant implements IContentProviderSyncable< Participant >,
         Parcelable
{
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
   
   private final String contactId;
   
   

   public Participant( String contactId )
   {
      this.contactId = contactId;
   }
   


   public Participant( Parcel source )
   {
      this.contactId = source.readString();
   }
   


   public String getContactId()
   {
      return contactId;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( contactId );
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
         operation = new ContentProviderSyncOperation( provider,
                                                       ContentProviderOperation.newInsert( Participants.CONTENT_URI )
                                                                               .withValues( ParticipantsProviderPart.getContentValues( (String) params[ 0 ],
                                                                                                                                       contactId,
                                                                                                                                       true ) )
                                                                               .build(),
                                                       IContentProviderSyncOperation.Op.INSERT );
      }
      
      return operation;
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      ContentProviderSyncOperation operation = null;
      
      final boolean ok = ParamChecker.checkParams( TAG,
                                                   "ContentProvider delete failed. ",
                                                   new Class[]
                                                   { String.class },
                                                   params );
      
      if ( ok )
      {
         operation = new ContentProviderSyncOperation( provider,
                                                       ContentProviderOperation.newDelete( Participants.CONTENT_URI )
                                                                               .withSelection( Participants.TASKSERIES_ID
                                                                                                  + " = ? AND "
                                                                                                  + Participants.CONTACT_ID
                                                                                                  + " = ?",
                                                                                               new String[]
                                                                                               {
                                                                                                (String) params[ 0 ],
                                                                                                contactId } )
                                                                               .build(),
                                                       IContentProviderSyncOperation.Op.DELETE );
      }
      
      return operation;
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               Participant update,
                                                                               Object... params )
   {
      // This should not change a value.
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   
}
