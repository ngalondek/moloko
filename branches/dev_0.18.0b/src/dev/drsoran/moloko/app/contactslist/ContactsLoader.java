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

package dev.drsoran.moloko.app.contactslist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContactOverviewsProviderPart;
import dev.drsoran.moloko.loaders.AbstractLoader;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.ContactOverviews;
import dev.drsoran.rtm.Contact;
import dev.drsoran.rtm.RtmContactWithTaskCount;


class ContactsLoader extends AbstractLoader< List< Contact > >
{
   public final static int ID = R.id.loader_contacts;
   
   private final static String CONTACTS_NOTE_CONTAINS_RTM;
   
   static
   {
      CONTACTS_NOTE_CONTAINS_RTM = ContactsContract.Data.MIMETYPE + "='"
         + ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE + "' AND ( "
         // fullname
         + ContactsContract.CommonDataKinds.Note.NOTE + " like '%RTM:%?%' OR "
         // username
         + ContactsContract.CommonDataKinds.Note.NOTE + " like '%RTM:%?%' )";
   }
   
   private final String selection;
   
   
   
   public ContactsLoader( Context context )
   {
      this( context, null );
   }
   
   
   
   public ContactsLoader( Context context, String selection )
   {
      super( context );
      this.selection = selection;
   }
   
   
   
   @Override
   protected List< Contact > queryResultInBackground( ContentProviderClient client )
   {
      List< Contact > contacts = null;
      final List< RtmContactWithTaskCount > rtmContacts = ContactOverviewsProviderPart.getContactOverviews( client,
                                                                                                            selection );
      if ( rtmContacts != null )
      {
         // Try to link the RTM contacts to the phonebook contacts
         contacts = new ArrayList< Contact >( rtmContacts.size() );
         
         for ( RtmContactWithTaskCount rtmContact : rtmContacts )
         {
            contacts.add( linkRtmContact( rtmContact ) );
         }
      }
      
      return contacts;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContactOverviews.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      ContactOverviewsProviderPart.registerContentObserver( getContext(),
                                                            observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      ContactOverviewsProviderPart.unregisterContentObserver( getContext(),
                                                              observer );
   }
   
   
   
   private Contact linkRtmContact( RtmContactWithTaskCount rtmContact )
   {
      Contact contact = new Contact( rtmContact );
      
      Cursor c = null;
      
      String lookUpKey = null;
      String photoId = null;
      boolean isLinkedByNotes = false;
      
      final ContentResolver contentResolver = getContext().getContentResolver();
      
      try
      {
         Uri lookUpUri = Uri.withAppendedPath( ContactsContract.Contacts.CONTENT_FILTER_URI,
                                               Uri.encode( contact.getFullname() ) );
         
         c = contentResolver.query( lookUpUri, new String[]
         { ContactsContract.Contacts._ID, ContactsContract.Contacts.PHOTO_ID,
          ContactsContract.Contacts.LOOKUP_KEY }, null, null, null );
         
         // Check if a contact with the same full name is in the phonebook. And that it is
         // unique.
         if ( c != null && c.getCount() == 1 && c.moveToFirst() )
         {
            photoId = c.getString( 1 );
            lookUpKey = c.getString( 2 );
         }
         
         // Check if a contact with a note starting with RTM: is in the phonebook
         else
         {
            final String selection = Queries.bindAll( CONTACTS_NOTE_CONTAINS_RTM,
                                                      new String[]
                                                      { contact.getFullname(),
                                                       contact.getUsername() } );
            
            c = contentResolver.query( ContactsContract.Data.CONTENT_URI,
                                       new String[]
                                       {
                                        ContactsContract.Data._ID,
                                        ContactsContract.Data.PHOTO_ID,
                                        ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Note.NOTE,
                                        ContactsContract.Data.LOOKUP_KEY },
                                       selection,
                                       null,
                                       null );
            
            if ( c != null && c.moveToFirst() )
            {
               photoId = c.getString( 1 );
               lookUpKey = c.getString( 4 );
               isLinkedByNotes = true;
            }
         }
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      if ( photoId != null )
      {
         setContactPhoto( photoId, contact );
      }
      
      contact.setLookUpKey( lookUpKey, isLinkedByNotes );
      
      return contact;
   }
   
   
   
   private void setContactPhoto( String photoId, Contact contact )
   {
      Cursor photoCursor = null;
      
      final Uri photoUri = Queries.contentUriWithId( ContactsContract.Data.CONTENT_URI,
                                                     photoId );
      
      try
      {
         photoCursor = getContext().getContentResolver()
                                   .query( photoUri,
                                           new String[]
                                           { ContactsContract.CommonDataKinds.Photo.PHOTO },
                                           null,
                                           null,
                                           null );
         
         if ( photoCursor != null && photoCursor.moveToNext() )
         {
            final byte[] data = photoCursor.getBlob( 0 );
            
            if ( data != null )
            {
               contact.setPhoto( data );
            }
         }
      }
      finally
      {
         if ( photoCursor != null )
            photoCursor.close();
      }
   }
}
