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

package dev.drsoran.moloko.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;


public final class AccountUtils
{
   private AccountUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   


   public final static Account getRtmAccount( Context context )
   {
      final AccountManager accountManager = AccountManager.get( context );
      
      if ( accountManager != null )
         return getRtmAccount( accountManager );
      else
         return null;
   }
   


   public final static Account getRtmAccount( AccountManager accountManager )
   {
      Account account = null;
      
      if ( accountManager != null )
      {
         final Account[] accounts = accountManager.getAccountsByType( Constants.ACCOUNT_TYPE );
         
         // TODO: We simple take the first one. Think about showing a choose
         // dialog.
         if ( accounts != null && accounts.length > 0 )
            account = accounts[ 0 ];
         else
            account = null;
      }
      
      return account;
   }
   


   public final static boolean isSyncAutomatic( Context context )
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         return Integer.valueOf( prefs.getString( context.getString( R.string.key_sync_inverval ),
                                                  context.getString( R.string.acc_pref_sync_interval_default_value ) ) ) != dev.drsoran.moloko.service.sync.Constants.SYNC_INTERVAL_MANUAL;
      }
      else
      {
         return false;
      }
   }
   


   public final static RtmAuth.Perms getAccessLevel( Context context )
   {
      RtmAuth.Perms permission = Perms.nothing;
      
      final AccountManager accountManager = AccountManager.get( context );
      
      if ( accountManager != null )
      {
         final Account account = getRtmAccount( accountManager );
         
         if ( account != null )
         {
            final String permStr = accountManager.getUserData( account,
                                                               Constants.FEAT_PERMISSION );
            if ( !TextUtils.isEmpty( permStr ) )
            {
               permission = RtmAuth.Perms.valueOf( permStr );
            }
         }
      }
      
      return permission;
   }
}
