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

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.content.loaders.AbstractLoader;
import dev.drsoran.moloko.app.content.loaders.ContactsLoader;
import dev.drsoran.moloko.content.ContentProviderUtils;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.IContact;
import dev.drsoran.moloko.domain.services.IContentRepository;


class LinkedContactsLoader extends AbstractLoader< List< LinkedContact > >
{
   public final static int ID = R.id.loader_linked_contacts;
   
   private final ContactsLoader contactsLoader;
   
   private final static String ANDROID_CONTACTS_NOTE_CONTAINS_RTM;
   
   static
   {
      ANDROID_CONTACTS_NOTE_CONTAINS_RTM = ContactsContract.Data.MIMETYPE
         + "='" + ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE
         + "' AND ( "
         // fullname
         + ContactsContract.CommonDataKinds.Note.NOTE + " like '%RTM:%?%' OR "
         // username
         + ContactsContract.CommonDataKinds.Note.NOTE + " like '%RTM:%?%' )";
   }
   
   
   
   public LinkedContactsLoader( DomainContext context,
      ContactsLoader contactsLoader )
   {
      super( context );
      this.contactsLoader = contactsLoader;
   }
   
   
   
   @Override
   protected List< LinkedContact > queryResultInBackground( IContentRepository contentRepository )
   {
      final List< IContact > contacts = contactsLoader.loadInBackground();
      
      if ( contactsLoader.hasContentException() )
      {
         throw contactsLoader.getContentException();
      }
      
      final List< LinkedContact > linkedContacts = new ArrayList< LinkedContact >( contacts.size() );
      
      for ( IContact contact : contacts )
      {
         final int numParticipatingTasks = contentRepository.getNumTasksContactIsParticipating( contact.getId() );
         linkedContacts.add( linkRtmContact( contact, numParticipatingTasks ) );
      }
      
      return linkedContacts;
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
   
   
   
   private LinkedContact linkRtmContact( IContact contact,
                                         int numParticipatingTasks )
   {
      LinkedContact linkedContact = new LinkedContact( contact,
                                                       numParticipatingTasks );
      
      String lookUpKey = null;
      String photoId = null;
      boolean isLinkedByNotes = false;
      
      Cursor c = null;
      try
      {
         final Uri lookUpUri = Uri.withAppendedPath( ContactsContract.Contacts.CONTENT_FILTER_URI,
                                                     Uri.encode( linkedContact.getFullName() ) );
         final ContentResolver contentResolver = getContext().getContentResolver();
         
         c = contentResolver.query( lookUpUri, new String[]
         { ContactsContract.Contacts._ID, ContactsContract.Contacts.PHOTO_ID,
          ContactsContract.Contacts.LOOKUP_KEY }, null, null, null );
         
         // Check if a contact with the same full name is in the phonebook and is
         // unique.
         if ( c.getCount() == 1 && c.moveToFirst() )
         {
            photoId = c.getString( 1 );
            lookUpKey = c.getString( 2 );
         }
         
         // Check if a contact with a note starting with RTM: is in the phonebook
         else
         {
            c.close();
            
            final String selection = ContentProviderUtils.bindAll( ANDROID_CONTACTS_NOTE_CONTAINS_RTM,
                                                                   new String[]
                                                                   {
                                                                    linkedContact.getFullName(),
                                                                    linkedContact.getUserName() } );
            
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
            
            if ( c.moveToFirst() )
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
         {
            c.close();
         }
      }
      
      if ( photoId != null )
      {
         setContactPhoto( photoId, linkedContact );
      }
      
      linkedContact.setLookUpKey( lookUpKey, isLinkedByNotes );
      
      return linkedContact;
   }
   
   
   
   private void setContactPhoto( String photoId, LinkedContact contact )
   {
      final Uri photoUri = ContentProviderUtils.contentUriWithId( ContactsContract.Data.CONTENT_URI,
                                                                  photoId );
      
      Cursor c = null;
      try
      {
         c = getContext().getContentResolver().query( photoUri, new String[]
         { ContactsContract.CommonDataKinds.Photo.PHOTO }, null, null, null );
         
         if ( c.moveToNext() )
         {
            final byte[] data = c.getBlob( 0 );
            
            if ( data != null )
            {
               contact.setPhoto( data );
            }
         }
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
}
