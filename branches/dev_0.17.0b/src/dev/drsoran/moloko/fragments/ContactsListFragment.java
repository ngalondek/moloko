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

package dev.drsoran.moloko.fragments;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ContactsListAdapter;
import dev.drsoran.moloko.fragments.base.MolokoListFragment;
import dev.drsoran.moloko.fragments.listeners.IContactsListFragmentListener;
import dev.drsoran.moloko.loaders.ContactsLoader;
import dev.drsoran.rtm.Contact;


public class ContactsListFragment extends MolokoListFragment< List< Contact > >
{
   private static class CtxtMenu
   {
      public final static int SHOW_PHONEBOOK_CONTACT = R.id.ctx_menu_show_phonebook_contact;
   }
   
   private final static int CONTACTS_LOADER_ID = 1;
   
   private IContactsListFragmentListener listener;
   
   
   
   public final static ContactsListFragment newInstance( Bundle config )
   {
      final ContactsListFragment fragment = new ContactsListFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IContactsListFragmentListener )
         listener = (IContactsListFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      registerForContextMenu( getListView() );
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.contactslist_fragment,
                                                  container,
                                                  false );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      final Contact contact = getListAdapter().getItem( info.position );
      
      if ( contact.getLookUpKey() != null )
      {
         final String fullname = contact.getFullname();
         
         menu.add( Menu.NONE,
                   CtxtMenu.SHOW_PHONEBOOK_CONTACT,
                   Menu.NONE,
                   getString( R.string.contactslist_listitem_show_phonebook_contact,
                              fullname ) );
      }
   }
   
   
   
   @Override
   public boolean onContextItemSelected( android.view.MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.SHOW_PHONEBOOK_CONTACT:
            final Contact contact = getListAdapter().getItem( info.position );
            
            if ( listener != null )
               listener.onShowPhoneBookEntryOfContact( contact.getLookUpKey() );
            
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      if ( listener != null )
      {
         final Contact contact = getListAdapter().getItem( position );
         listener.onShowTasksOfContact( contact.getFullname(),
                                        contact.getUsername() );
      }
   }
   
   
   
   @Override
   public ListAdapter createEmptyListAdapter()
   {
      return new ContactsListAdapter( getSherlockActivity(),
                                      R.layout.contactslist_activity_listitem,
                                      Collections.< Contact > emptyList() );
   }
   
   
   
   @Override
   public ListAdapter createListAdapterForResult( List< Contact > result )
   {
      return new ContactsListAdapter( getSherlockActivity(),
                                      R.layout.contactslist_activity_listitem,
                                      result );
   }
   
   
   
   @Override
   public Loader< List< Contact >> newLoaderInstance( int id, Bundle config )
   {
      return new ContactsLoader( getSherlockActivity() );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_contacts );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return CONTACTS_LOADER_ID;
   }
   
   
   
   @Override
   public ContactsListAdapter getListAdapter()
   {
      return (ContactsListAdapter) super.getListAdapter();
   }
}
