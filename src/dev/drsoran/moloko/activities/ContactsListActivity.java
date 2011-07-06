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

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ContactsListAdapter;
import dev.drsoran.moloko.content.ContactOverviewsProviderPart;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.content.RtmContactsProviderPart;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ContactOverviews;
import dev.drsoran.rtm.Contact;
import dev.drsoran.rtm.RtmContactWithTaskCount;


public class ContactsListActivity extends ListActivity
{
   private final static String TAG = "Moloko."
      + ContactsListActivity.class.getSimpleName();
   
   
   private final class AsyncQueryContacts extends
            AsyncTask< ContentResolver, Void, List< Contact > >
   {
      
      @Override
      protected void onPreExecute()
      {
         ContactsListActivity.this.getListView()
                                  .setEmptyView( ContactsListActivity.this.findViewById( android.R.id.empty ) );
      }
      


      @Override
      protected List< Contact > doInBackground( ContentResolver... params )
      {
         List< Contact > contacts = null;
         
         final ContentProviderClient client = ( params[ 0 ] ).acquireContentProviderClient( ContactOverviews.CONTENT_URI );
         
         if ( client != null )
         {
            final List< RtmContactWithTaskCount > rtmContacts = ContactOverviewsProviderPart.getContactOverviews( client,
                                                                                                                  null );
            client.release();
            
            if ( rtmContacts != null )
            {
               // Try to link the RTM contacts to the phonebook contacts
               contacts = new ArrayList< Contact >( rtmContacts.size() );
               
               for ( RtmContactWithTaskCount rtmContact : rtmContacts )
               {
                  contacts.add( linkRtmContact( rtmContact ) );
               }
            }
            else
            {
               LogUtils.logDBError( ContactsListActivity.this, TAG, "Contacts" );
            }
         }
         
         return contacts;
      }
      


      @Override
      protected void onPostExecute( List< Contact > result )
      {
         final ContactsListActivity activity = ContactsListActivity.this;
         
         setListAdapter( new ContactsListAdapter( activity,
                                                  R.layout.contactslist_activity_listitem,
                                                  result != null
                                                                ? result
                                                                : Collections.< Contact > emptyList() ) );
         activity.getListView()
                 .setEmptyView( activity.findViewById( R.id.contactslist_activity_no_contacts ) );
         
         activity.asyncQueryContacts = null;
      }
   }
   
   private final Runnable queryContactsRunnable = new Runnable()
   {
      @Override
      public void run()
      {
         if ( asyncQueryContacts != null )
         {
            Log.w( TAG, "Canceled AsyncQueryContacts task." );
            asyncQueryContacts.cancel( true );
         }
         
         asyncQueryContacts = new AsyncQueryContacts();
         asyncQueryContacts.execute( ContactsListActivity.this.getContentResolver() );
      }
   };
   
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
   
   
   protected static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      private final static int MENU_ORDER_STATIC = 10000;
      
      public final static int MENU_ORDER = MENU_ORDER_STATIC - 1;
      
      public final static int SETTINGS = START_IDX + 1;
      
      public final static int SYNC = START_IDX + 2;
   }
   

   protected static class CtxtMenu
   {
      public final static int OPEN_TASKS = 0;
      
      public final static int OPEN_CONTACT = 1;
   }
   
   private ContentObserver dbContactsObserver;
   
   private AsyncQueryContacts asyncQueryContacts;
   
   private final Handler handler = new Handler();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.contactslist_activity );
      
      registerForContextMenu( getListView() );
      
      dbContactsObserver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            // Aggregate several calls to a single update.
            DelayedRun.run( getListView().getHandler(),
                            queryContactsRunnable,
                            1000 );
         }
      };
      
      RtmContactsProviderPart.registerContentObserver( this, dbContactsObserver );
      ParticipantsProviderPart.registerContentObserver( this,
                                                        dbContactsObserver );
      
      if ( !( getListAdapter() instanceof ContactsListAdapter ) )
         asyncQueryContacts();
   }
   


   @Override
   protected void onStop()
   {
      super.onStop();
      
      if ( asyncQueryContacts != null )
         asyncQueryContacts.cancel( true );
      
      asyncQueryContacts = null;
      
      UIUtils.showTitleBarAddTask( this, false );
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      unregisterForContextMenu( getListView() );
      
      RtmContactsProviderPart.unregisterContentObserver( this,
                                                         dbContactsObserver );
      ParticipantsProviderPart.unregisterContentObserver( this,
                                                          dbContactsObserver );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings )
          .setIcon( R.drawable.ic_menu_settings )
          .setIntent( new Intent( this, MolokoPreferencesActivity.class ) );
      
      return true;
   }
   


   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      // TODO: Repair
      // UIUtils.addSyncMenuItem( this,
      // menu,
      // OptionsMenu.SYNC,
      // OptionsMenu.MENU_ORDER_STATIC - 1 );
      
      return true;
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      final Contact contact = getContact( info.position );
      final String fullname = contact.getFullname();
      
      menu.add( Menu.NONE,
                CtxtMenu.OPEN_TASKS,
                Menu.NONE,
                getString( R.string.contactslist_listitem_open_tasks, fullname ) );
      
      if ( contact.getLookUpKey() != null )
      {
         menu.add( Menu.NONE,
                   CtxtMenu.OPEN_CONTACT,
                   Menu.NONE,
                   getString( R.string.contactslist_listitem_open_contact,
                              fullname ) );
      }
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      final Contact contact = getContact( info.position );
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.OPEN_TASKS:
            startActivity( Intents.createOpenContactIntent( this,
                                                            contact.getFullname(),
                                                            contact.getUsername() ) );
            break;
         
         case CtxtMenu.OPEN_CONTACT:
            final Intent intent = new Intent( Intent.ACTION_VIEW,
                                              Uri.withAppendedPath( ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                                                                    contact.getLookUpKey() ) );
            startActivity( intent );
            break;
         
         default :
            return super.onContextItemSelected( item );
      }
      
      return true;
   }
   


   @Override
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
      final Contact contact = getContact( position );
      
      startActivity( Intents.createOpenContactIntent( this,
                                                      contact.getFullname(),
                                                      contact.getUsername() ) );
   }
   


   private void asyncQueryContacts()
   {
      handler.post( queryContactsRunnable );
   }
   


   private final Contact getContact( int pos )
   {
      return (Contact) getListAdapter().getItem( pos );
   }
   


   private Contact linkRtmContact( RtmContactWithTaskCount rtmContact )
   {
      Contact contact = new Contact( rtmContact );
      
      Cursor c = null;
      
      String lookUpKey = null;
      String photoId = null;
      boolean isLinkedByNotes = false;
      
      final ContentResolver contentResolver = getContentResolver();
      
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
         photoCursor = getContentResolver().query( photoUri, new String[]
         { ContactsContract.CommonDataKinds.Photo.PHOTO }, null, null, null );
         
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
