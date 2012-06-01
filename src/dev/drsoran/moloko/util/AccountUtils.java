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

package dev.drsoran.moloko.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;


public final class AccountUtils
{
   private AccountUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static void registerAccountListener( Context context,
                                                     Handler handler,
                                                     OnAccountsUpdateListener listener )
   {
      final AccountManager accountManager = AccountManager.get( context );
      
      if ( accountManager != null )
         accountManager.addOnAccountsUpdatedListener( listener, handler, true );
   }
   
   
   
   public final static void unregisterAccountListener( Context context,
                                                       OnAccountsUpdateListener listener )
   {
      final AccountManager accountManager = AccountManager.get( context );
      
      if ( accountManager != null )
         accountManager.removeOnAccountsUpdatedListener( listener );
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
      return MolokoApp.getSettings( context ).getSyncInterval() != dev.drsoran.moloko.sync.Constants.SYNC_INTERVAL_MANUAL;
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
         else
         {
            permission = Perms.nothing;
         }
      }
      
      return permission;
   }
   
   
   
   public final static boolean isReadOnlyAccess( Context context )
   {
      final Perms level = getAccessLevel( context );
      return isReadOnlyAccess( level )
         || context.getResources()
                   .getBoolean( R.bool.force_readable_rtm_access );
   }
   
   
   
   public final static boolean isReadOnlyAccess( RtmAuth.Perms level )
   {
      return level == Perms.nothing || level == Perms.read;
   }
   
   
   
   public final static boolean isWriteableAccess( Context context )
   {
      return !isReadOnlyAccess( context );
   }
   
   
   
   public final static boolean isWriteableAccess( RtmAuth.Perms level )
   {
      return !isReadOnlyAccess( level );
   }
}
