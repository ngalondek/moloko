/*
 * Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.contactslist;

import android.content.Intent;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.home.IntentHandlerBase;


public class ContactsListIntentHandler extends IntentHandlerBase implements
         IContactsListFragmentListener
{
   public ContactsListIntentHandler( MolokoActivity activity, int fragmentId )
   {
      super( activity, fragmentId );
   }
   
   
   
   @Override
   public void handleIntent( Intent intent )
   {
      final ContactsListFragment fragment = ContactsListFragment.newInstance( intent.getExtras() );
      fragment.setListener( this );
      
      getActivity().showFragment( getFragmentId(), fragment );
      getActivity().setTitle( R.string.app_contacts );
   }
   
   
   
   @Override
   public void onShowPhoneBookEntryOfContact( String lookUpKey )
   {
      getActivity().startActivity( Intents.createShowPhonebookContactIntent( lookUpKey ) );
   }
   
   
   
   @Override
   public void onShowTasksOfContact( String fullname, String username )
   {
      getActivity().startActivity( Intents.createShowTasksOfContactIntent( getActivity(),
                                                                           fullname,
                                                                           username ) );
   }
}
