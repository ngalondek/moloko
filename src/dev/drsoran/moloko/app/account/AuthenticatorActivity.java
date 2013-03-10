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

package dev.drsoran.moloko.app.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.Window;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.provider.Rtm;


public class AuthenticatorActivity extends AccountAuthenticatorFragmentActivity
         implements IStartAuthenticationFragmentListener,
         IRtmWebLoginFragmentListener
{
   public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
   
   private AccountManager accountManager;
   
   private boolean isNewAccount;
   
   
   
   @Override
   public void onCreate( Bundle icicle )
   {
      super.onCreate( icicle );
      
      requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
      setContentView( R.layout.authenticator_activity );
      setSupportProgressBarIndeterminateVisibility( false );
      
      accountManager = AccountManager.get( this );
      
      createStartFragment();
      
      isNewAccount = getIntent().getStringExtra( AccountManager.KEY_ACCOUNT_NAME ) == null;
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
         case R.id.error:
            finish();
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, tag, which );
      }
   }
   
   
   
   @Override
   public Object onRetainCustomNonConfigurationInstance()
   {
      final AuthFragment authFragment = (AuthFragment) getSupportFragmentManager().findFragmentById( R.id.frag_multi_container );
      if ( authFragment != null )
      {
         return authFragment.onRetainNonConfigurationInstance();
      }
      else
      {
         return null;
      }
   }
   
   
   
   @Override
   public void onStartAuthentication( RtmAuth.Perms permission )
   {
      if ( getAppContext().getConnectionService().hasInternetConnection() )
      {
         final Bundle config = new Bundle( 1 );
         config.putString( Constants.FEAT_PERMISSION, permission.toString() );
         
         getSupportFragmentManager().beginTransaction()
                                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                                    .replace( R.id.frag_multi_container,
                                              RtmWebLoginFragment.newInstance( config ) )
                                    .commit();
      }
      else
      {
         showNotConnectedDialog();
      }
   }
   
   
   
   /**
    * Response is received from the server for authentication request. Sets the AccountAuthenticatorResult which is sent
    * back to the caller. Also sets the authToken in AccountManager for this account.
    */
   @Override
   public void onAuthenticationFinished( RtmAuth rtmAuth )
   {
      final Account account = new Account( rtmAuth.getUser().getUsername(),
                                           Constants.ACCOUNT_TYPE );
      try
      {
         boolean ok = true;
         
         if ( isNewAccount )
         {
            ok = accountManager.addAccountExplicitly( account,
                                                      rtmAuth.getToken(),
                                                      null );
            if ( ok )
            {
               ContentResolver.setSyncAutomatically( account,
                                                     Rtm.AUTHORITY,
                                                     true );
            }
         }
         
         if ( ok )
         {
            accountManager.setUserData( account,
                                        Constants.FEAT_API_KEY,
                                        MolokoApp.getRtmApiKey( this ) );
            accountManager.setUserData( account,
                                        Constants.FEAT_SHARED_SECRET,
                                        MolokoApp.getRtmSharedSecret( this ) );
            accountManager.setUserData( account,
                                        Constants.FEAT_PERMISSION,
                                        rtmAuth.getPerms().toString() );
            accountManager.setUserData( account,
                                        Constants.ACCOUNT_USER_ID,
                                        rtmAuth.getUser().getId() );
            accountManager.setUserData( account,
                                        Constants.ACCOUNT_FULLNAME,
                                        rtmAuth.getUser().getFullname() );
            
            final Intent intent = new Intent();
            
            intent.putExtra( AccountManager.KEY_ACCOUNT_NAME,
                             rtmAuth.getUser().getUsername() );
            // We store the authToken as password
            intent.putExtra( AccountManager.KEY_PASSWORD, rtmAuth.getToken() );
            intent.putExtra( AccountManager.KEY_ACCOUNT_TYPE,
                             Constants.ACCOUNT_TYPE );
            intent.putExtra( AccountManager.KEY_AUTHTOKEN, rtmAuth.getToken() );
            
            intent.putExtra( AccountManager.KEY_BOOLEAN_RESULT, true );
            
            setAccountAuthenticatorResult( intent.getExtras() );
            setResult( RESULT_OK, intent );
         }
      }
      catch ( SecurityException e )
      {
         Log().e( getClass(), e.getLocalizedMessage() );
         onAuthenticationFailed( getString( R.string.auth_err_cause_scurity ) );
      }
      finally
      {
         finish();
      }
   }
   
   
   
   @Override
   public void onAuthenticationCanceled()
   {
      setResult( RESULT_CANCELED, null );
      finish();
   }
   
   
   
   @Override
   public void onAuthenticationFailed( String localizedCause )
   {
      new AlertDialogFragment.Builder( R.id.error ).setTitle( getString( R.string.err_error ) )
                                                   .setIcon( R.drawable.ic_black_error )
                                                   .setMessage( getString( R.string.auth_err_with_cause,
                                                                           localizedCause ) )
                                                   .setNeutralButton( R.string.btn_back )
                                                   .show( this );
   }
   
   
   
   private void createStartFragment()
   {
      final FragmentManager fragmentManager = getSupportFragmentManager();
      
      if ( fragmentManager.findFragmentById( R.id.frag_multi_container ) == null )
      {
         final Fragment startFragment = chooseStartFragmentByIntentData();
         
         fragmentManager.beginTransaction()
                        .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                        .add( R.id.frag_multi_container, startFragment )
                        .commit();
      }
   }
   
   
   
   private Fragment chooseStartFragmentByIntentData()
   {
      final Fragment chosenFragment;
      final Intent intent = getIntent();
      
      if ( intent.getBooleanExtra( Intents.Extras.AUTH_MISSINGCREDENTIALS,
                                   false )
         || intent.getBooleanExtra( Intents.Extras.AUTH_TOKEN_EXPIRED, false ) )
      {
         chosenFragment = AccountIssueFragment.newInstance( getIntent().getExtras() );
      }
      
      else if ( getAppContext().getAccountService().getRtmAccount() != null )
      {
         final Bundle config = new Bundle( 1 );
         config.putBoolean( AccountIssueFragment.Config.ACCOUNT_ALREADY_EXISTS,
                            true );
         
         chosenFragment = AccountIssueFragment.newInstance( config );
      }
      
      else
      {
         chosenFragment = ChooseRtmPermissionFragment.newInstance( getIntent().getExtras() );
      }
      
      return chosenFragment;
   }
}
