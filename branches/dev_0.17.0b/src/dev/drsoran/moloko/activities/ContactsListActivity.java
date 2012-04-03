/*
 * Copyright (c) 2011 Ronny Röhricht
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

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IContactsListFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.UIUtils;


public class ContactsListActivity extends MolokoFragmentActivity implements
         IContactsListFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + ContactsListActivity.class.getSimpleName();
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.contactslist_activity );
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      UIUtils.addSettingsMenuItem( this,
                                   menu,
                                   MenuCategory.ALTERNATIVE,
                                   MenuItem.SHOW_AS_ACTION_NEVER );
      
      UIUtils.addSyncMenuItem( this,
                               menu,
                               MenuCategory.NONE,
                               MenuItem.SHOW_AS_ACTION_IF_ROOM );
      return true;
   }
   
   
   
   @Override
   public void onShowPhoneBookEntryOfContact( String lookUpKey )
   {
      startActivity( Intents.createShowPhonebookContactIntent( lookUpKey ) );
   }
   
   
   
   @Override
   public void onShowTasksOfContact( String fullname, String username )
   {
      startActivity( Intents.createOpenContactIntent( this, fullname, username ) );
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_contactslist };
   }
}
