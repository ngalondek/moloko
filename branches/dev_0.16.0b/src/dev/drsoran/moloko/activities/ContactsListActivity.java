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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IContactsListFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;


public class ContactsListActivity extends MolokoFragmentActivity implements
         IContactsListFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + ContactsListActivity.class.getSimpleName();
   
   
   private static class OptionsMenu
   {
      public final static int SETTINGS = R.id.menu_settings;
      
      public final static int SYNC = R.id.menu_sync;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.contactslist_activity );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                Menu.NONE,
                R.string.phr_settings )
          .setIcon( R.drawable.ic_menu_settings )
          .setIntent( new Intent( this, MolokoPreferencesActivity.class ) )
          .setShowAsAction( MenuItem.SHOW_AS_ACTION_NEVER );
      
      UIUtils.addSyncMenuItem( this,
                               menu,
                               OptionsMenu.SYNC,
                               Menu.NONE,
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
