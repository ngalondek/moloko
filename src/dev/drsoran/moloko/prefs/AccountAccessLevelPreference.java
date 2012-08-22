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

package dev.drsoran.moloko.prefs;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.util.AccountUtils;


class AccountAccessLevelPreference extends InfoTextPreference implements
         AccountManagerCallback< Bundle >
{
   private WeakReference< AccountManager > accountManager;
   
   private AccountManagerFuture< Bundle > changeAccountHandle;
   
   private final Handler handler = new Handler();
   
   
   
   public AccountAccessLevelPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   protected void onBindView( View view )
   {
      final AccountManager accountManager = getAccountManager();
      
      if ( accountManager != null )
      {
         final Account account = AccountUtils.getRtmAccount( accountManager );
         
         if ( account != null )
         {
            final String perm = getPermissionEntry( accountManager.getUserData( account,
                                                                                Constants.FEAT_PERMISSION ) );
            
            if ( !TextUtils.isEmpty( perm ) )
            {
               setInfoText( getContext().getString( R.string.acc_pref_perm_value,
                                                    perm ) );
            }
            else
            {
               setInfoText( getContext().getString( R.string.acc_pref_perm_value,
                                                    getContext().getString( R.string.acc_pref_perm_has_not ) ) );
            }
         }
         else
         {
            setInfoText( getContext().getString( R.string.g_no_account ) );
         }
      }
      else
      {
         setInfoText( getContext().getString( R.string.err_error ) );
         view.setEnabled( false );
      }
      
      // Call this after setting infoText
      super.onBindView( view );
   }
   
   
   
   @Override
   protected void onClick()
   {
      final AccountManager accountManager = getAccountManager();
      
      if ( accountManager != null && changeAccountHandle == null )
      {
         final Account account = AccountUtils.getRtmAccount( accountManager );
         final Context context = getContext();
         
         if ( account != null && context instanceof Activity )
         {
            final Bundle bundle = new Bundle();
            bundle.putBoolean( Constants.FEAT_PERMISSION, true );
            
            changeAccountHandle = accountManager.updateCredentials( account,
                                                                    Constants.AUTH_TOKEN_TYPE,
                                                                    bundle,
                                                                    (Activity) context,
                                                                    this,
                                                                    handler );
         }
      }
   }
   
   
   
   @Override
   public void run( AccountManagerFuture< Bundle > future )
   {
      changeAccountHandle = null;
      
      if ( future.isDone() )
      {
         try
         {
            future.getResult();
         }
         catch ( OperationCanceledException e )
         {
            Toast.makeText( getContext(),
                            R.string.err_add_account_canceled,
                            Toast.LENGTH_SHORT ).show();
         }
         catch ( AuthenticatorException e )
         {
            // According to the doc this can only happen
            // if there is no authenticator registered for the
            // account type. This should not happen.
            Toast.makeText( getContext(),
                            R.string.err_unexpected,
                            Toast.LENGTH_LONG ).show();
         }
         catch ( IOException e )
         {
            // Will be notified in the AuthenticatorActivity
         }
      }
   }
   
   
   
   private String getPermissionEntry( String permValue )
   {
      String entry = null;
      
      if ( !TextUtils.isEmpty( permValue ) )
      {
         final String[] permValues = getContext().getResources()
                                                 .getStringArray( R.array.rtm_permissions_values );
         
         boolean found = false;
         
         int i = 0;
         
         for ( ; i < permValues.length && !found; )
         {
            found = permValue.equals( permValues[ i ] );
            
            if ( !found )
               ++i;
         }
         
         if ( found )
         {
            entry = getContext().getResources()
                                .getStringArray( R.array.rtm_permissions )[ i ].toString();
         }
      }
      
      return ( entry );
   }
   
   
   
   private AccountManager getAccountManager()
   {
      if ( accountManager == null || accountManager.get() == null )
      {
         accountManager = new WeakReference< AccountManager >( AccountManager.get( getContext() ) );
      }
      
      return accountManager.get();
   }
}
