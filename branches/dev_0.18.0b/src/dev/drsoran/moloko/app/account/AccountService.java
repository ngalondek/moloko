/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import dev.drsoran.Strings;
import dev.drsoran.moloko.app.services.AccountCredentials;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.rtm.service.RtmServicePermission;


public class AccountService implements IAccountService
{
   private final Context context;
   
   private RtmServicePermission forcedAccessLevel;
   
   
   
   public AccountService( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public Account getRtmAccount()
   {
      final AccountManager accountManager = AccountManager.get( context );
      final Account account = getRtmAccount( accountManager );
      
      return account;
   }
   
   
   
   @Override
   public AccountCredentials getCredentials( Account account )
   {
      final AccountManager accountManager = AccountManager.get( context );
      final String apiKey = accountManager.getUserData( account,
                                                        AuthConstants.FEAT_API_KEY );
      final String sharedSecret = accountManager.getUserData( account,
                                                              AuthConstants.FEAT_SHARED_SECRET );
      
      return new AccountCredentials( apiKey, sharedSecret );
   }
   
   
   
   @Override
   public boolean isAccountAuthenticated( Account account )
   {
      return !Strings.isNullOrEmpty( getAuthToken( account ) );
   }
   
   
   
   @Override
   public boolean invalidateAccount( Account account )
   {
      final String authToken = getAuthToken( account );
      if ( authToken != null )
      {
         final AccountManager accountManager = AccountManager.get( context );
         accountManager.invalidateAuthToken( AuthConstants.ACCOUNT_TYPE,
                                             authToken );
         return true;
      }
      
      return false;
   }
   
   
   
   @Override
   public void notifyInvalidAuthToken( Account account )
   {
      final AccountManager accountManager = AccountManager.get( context );
      accountManager.getAuthToken( account,
                                   AuthConstants.AUTH_TOKEN_TYPE,
                                   Bundle.EMPTY,
                                   true,
                                   null,
                                   null );
   }
   
   
   
   @Override
   public String getAuthToken( Account account )
   {
      final AccountManager accountManager = AccountManager.get( context );
      return accountManager.peekAuthToken( account,
                                           AuthConstants.AUTH_TOKEN_TYPE );
   }
   
   
   
   @Override
   public RtmServicePermission getAccessLevel( Account account )
   {
      RtmServicePermission permission;
      
      if ( forcedAccessLevel != null )
      {
         permission = forcedAccessLevel;
      }
      else
      {
         permission = getAccessLevelFromAccount( account );
      }
      
      return permission;
   }
   
   
   
   @Override
   public boolean isReadOnlyAccess( Account account )
   {
      final RtmServicePermission level = getAccessLevel( account );
      return isReadOnlyAccess( level );
   }
   
   
   
   @Override
   public boolean isWriteableAccess( Account account )
   {
      return !isReadOnlyAccess( account );
   }
   
   
   
   @Override
   public void setForcedAccessLevel( RtmServicePermission forcedAccessLevel )
   {
      this.forcedAccessLevel = forcedAccessLevel;
   }
   
   
   
   @Override
   public void releaseForcedAccessLevel()
   {
      forcedAccessLevel = null;
   }
   
   
   
   private Account getRtmAccount( final AccountManager accountManager )
   {
      Account account;
      final Account[] accounts = accountManager.getAccountsByType( AuthConstants.ACCOUNT_TYPE );
      
      // TODO: We simple take the first one. Think about showing a choose
      // dialog.
      if ( accounts != null && accounts.length > 0 )
      {
         account = accounts[ 0 ];
      }
      else
      {
         account = null;
      }
      return account;
   }
   
   
   
   private RtmServicePermission getAccessLevelFromAccount( Account account )
   {
      RtmServicePermission permission = RtmServicePermission.nothing;
      
      if ( account != null )
      {
         final AccountManager accountManager = AccountManager.get( context );
         final String permStr = accountManager.getUserData( account,
                                                            AuthConstants.FEAT_PERMISSION );
         if ( !TextUtils.isEmpty( permStr ) )
         {
            permission = RtmServicePermission.valueOf( permStr );
         }
      }
      
      return permission;
   }
   
   
   
   private final static boolean isReadOnlyAccess( RtmServicePermission level )
   {
      return level == RtmServicePermission.nothing
         || level == RtmServicePermission.read;
   }
}
