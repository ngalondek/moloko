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

import android.app.ListActivity;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.content.RtmContactsProviderPart;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Contacts;
import dev.drsoran.rtm.Contact;
import dev.drsoran.rtm.RtmContact;


public class ContactsListActivity extends ListActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + ContactsListActivity.class.getSimpleName();
   
   private final Runnable queryContactsRunnable = new Runnable()
   {
      public void run()
      {
         ContactsListActivity.this.queryContacts();
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
      
   }
   
   private ContentObserver dbContactsObserver;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.contactslist_activity );
      
      registerForContextMenu( getListView() );
      
      dbContactsObserver = new ContentObserver( getListView().getHandler() )
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
         queryContacts();
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
      UIUtils.addSyncMenuItem( this,
                               menu,
                               OptionsMenu.SYNC,
                               OptionsMenu.MENU_ORDER_STATIC - 1 );
      
      return true;
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      // final ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
      // final RtmListWithTaskCount list = getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition )
      // );
      //      
      // if ( getExpandableListView().isGroupExpanded( ExpandableListView.getPackedPositionGroup( info.packedPosition )
      // ) )
      // {
      // // show collapse before open
      // menu.add( Menu.NONE,
      // CtxtMenu.COLLAPSE,
      // Menu.NONE,
      // getString( R.string.tasklists_menu_ctx_collapse,
      // list.getName() ) );
      // menu.add( Menu.NONE,
      // CtxtMenu.OPEN_LIST,
      // Menu.NONE,
      // getString( R.string.phr_open_with_name, list.getName() ) );
      // }
      // else
      // {
      // menu.add( Menu.NONE,
      // CtxtMenu.OPEN_LIST,
      // Menu.NONE,
      // getString( R.string.phr_open_with_name, list.getName() ) );
      //         
      // menu.add( Menu.NONE,
      // CtxtMenu.EXPAND,
      // Menu.NONE,
      // getString( R.string.tasklists_menu_ctx_expand,
      // list.getName() ) );
      // }
      //      
      // if ( list.getId().equals( MolokoApp.getSettings().getDefaultListId() ) )
      // menu.add( Menu.NONE,
      // CtxtMenu.REMOVE_DEFAULT_LIST,
      // Menu.NONE,
      // getString( R.string.tasklists_menu_ctx_remove_def_list ) );
      // else
      // menu.add( Menu.NONE,
      // CtxtMenu.MAKE_DEFAULT_LIST,
      // Menu.NONE,
      // getString( R.string.tasklists_menu_ctx_make_def_list ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         // case CtxtMenu.OPEN_LIST:
         // openList( getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ) );
         // return true;
         //            
         // case CtxtMenu.EXPAND:
         // getExpandableListView().expandGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
         // return true;
         //            
         // case CtxtMenu.COLLAPSE:
         // getExpandableListView().collapseGroup( ExpandableListView.getPackedPositionGroup( info.packedPosition ) );
         // return true;
         //            
         // case CtxtMenu.MAKE_DEFAULT_LIST:
         // MolokoApp.getSettings()
         // .setDefaultListId( getRtmList( ExpandableListView.getPackedPositionGroup( info.packedPosition ) ).getId() );
         // return true;
         //            
         // case CtxtMenu.REMOVE_DEFAULT_LIST:
         // MolokoApp.getSettings()
         // .setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
         // return true;
         
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   private void queryContacts()
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Contacts.CONTENT_URI );
      
      if ( client != null )
      {
         final ArrayList< RtmContact > rtmContacts = RtmContactsProviderPart.getAllContacts( client,
                                                                                             null );
         
         if ( rtmContacts != null )
         {
            // Try to link the RTM contacts to the phonebook contacts
            final ArrayList< Contact > contacts = new ArrayList< Contact >( rtmContacts.size() );
            
            for ( RtmContact rtmContact : rtmContacts )
            {
               contacts.add( linkRtmContact( rtmContact ) );
            }
            
            setListAdapter( new ContactsListAdapter( this,
                                                     R.layout.contactslist_activity_listitem,
                                                     contacts ) );
         }
         else
         {
            // TODO: Show error
         }
         
         client.release();
      }
   }
   


   private final Contact getContact( int pos )
   {
      return (Contact) getListAdapter().getItem( pos );
   }
   


   private Contact linkRtmContact( RtmContact rtmContact )
   {
      Contact contact = new Contact( rtmContact.getId(),
                                     rtmContact.getFullname(),
                                     rtmContact.getUsername() );
      Cursor c = null;
      
      String lookUpKey = null;
      String photoId = null;
      
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
      
      contact.setLookUpKey( lookUpKey );
      
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
