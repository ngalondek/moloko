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

import java.util.List;

import android.content.Loader;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.loaders.ContactsLoader;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;
import dev.drsoran.moloko.ui.fragments.MolokoListFragment;


public class ContactsListFragment extends MolokoListFragment< LinkedContact >
{
   public final static ContactsListFragment newInstance( Bundle config )
   {
      final ContactsListFragment fragment = new ContactsListFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public ContactsListFragment()
   {
      setNoElementsResourceId( R.string.contactslist_no_contacts );
      setHasOptionsMenu( false );
   }
   
   
   
   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      registerForContextMenu( getListView() );
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
      final LinkedContact contact = getListAdapter().getItem( info.position );
      
      if ( contact.getLookUpKey() != null )
      {
         final String fullname = contact.getFullName();
         menu.add( Menu.NONE,
                   R.id.ctx_menu_show_phonebook_contact,
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
         case R.id.ctx_menu_show_phonebook_contact:
            final LinkedContact contact = getListAdapter().getItem( info.position );
            onShowPhoneBookEntryOfContact( contact.getLookUpKey() );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      final LinkedContact contact = getListAdapter().getItem( position );
      onShowTasksOfContact( contact.getFullName(), contact.getUserName() );
   }
   
   
   
   @Override
   public SwappableArrayAdapter< LinkedContact > createListAdapter()
   {
      return new ContactsListAdapter( this );
   }
   
   
   
   @Override
   public Loader< List< LinkedContact >> newLoaderInstance( int id,
                                                            Bundle config )
   {
      final DomainContext domainContext = getUiContext().asDomainContext();
      final ContactsLoader contactsLoader = new ContactsLoader( domainContext );
      contactsLoader.setRespectContentChanges( true );
      
      return new LinkedContactsLoader( domainContext, contactsLoader );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_contacts );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return ContactsLoader.ID;
   }
   
   
   
   @Override
   public ContactsListAdapter getListAdapter()
   {
      return (ContactsListAdapter) super.getListAdapter();
   }
   
   
   
   public void onCallButtonClicked( LinkedContact contact )
   {
      onShowPhoneBookEntryOfContact( contact.getLookUpKey() );
   }
   
   
   
   @Override
   public int getChoiceMode()
   {
      return ListView.CHOICE_MODE_NONE;
   }
   
   
   
   private void onShowPhoneBookEntryOfContact( String lookUpKey )
   {
      startActivity( Intents.createShowPhonebookContactIntent( lookUpKey ) );
   }
   
   
   
   private void onShowTasksOfContact( String fullname, String username )
   {
      startActivity( Intents.createShowTasksOfContactIntent( getActivity(),
                                                             fullname,
                                                             username ) );
   }
}
