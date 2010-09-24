/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.moloko.auth;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ConnectionChecker;
import dev.drsoran.provider.Rtm;


/**
 * Activity which displays login screen to the user.
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity
         implements TextWatcher
{
   private static final String TAG = AuthenticatorActivity.class.getSimpleName();
   
   public static final String PARAM_AUTH_TOKEN_EXPIRED = "authTokenExpired";
   
   public static final String PARAM_MISSINGCREDENTIALS = "missingCredentials";
   
   public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
   
   public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
   
   private AccountManager accountManager;
   
   private AsyncRtmAuthenticator authenticator;
   
   private EditText apiKeyEdit;
   
   private EditText sharedSecrectEdit;
   
   private Spinner permDropDown;
   
   private Button continueBtn;
   
   private TextView messageText;
   
   private boolean newAccount;
   
   

   @Override
   public void onCreate( Bundle icicle )
   {
      super.onCreate( icicle );
      
      // Initialize UI
      requestWindowFeature( Window.FEATURE_LEFT_ICON );
      setContentView( R.layout.authenticator_activity );
      getWindow().setFeatureDrawableResource( Window.FEATURE_LEFT_ICON,
                                              R.drawable.rtm );
      
      accountManager = AccountManager.get( this );
      
      apiKeyEdit = (EditText) findViewById( R.id.auth_edit_api_key );
      apiKeyEdit.addTextChangedListener( this );
      
      sharedSecrectEdit = (EditText) findViewById( R.id.auth_edit_shared_secret );
      sharedSecrectEdit.addTextChangedListener( this );
      
      permDropDown = (Spinner) findViewById( R.id.auth_spin_permission );
      
      continueBtn = (Button) findViewById( R.id.auth_btn_continue );
      
      messageText = (TextView) findViewById( R.id.auth_text_message );
      
      // TODO: Remove this:
      {
         final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
         
         if ( prefs != null )
         {
            apiKeyEdit.setText( prefs.getString( getString( R.string.key_api_key ),
                                                 "4070cf4b39d83778df99e7b988539cb7" ) );
            sharedSecrectEdit.setText( prefs.getString( getString( R.string.key_shared_secret ),
                                                        "8d263cfe92b542fa" ) );
         }
      }
      
      initializeGui();
      
      newAccount = getIntent().getStringExtra( AccountManager.KEY_ACCOUNT_NAME ) == null;
      
      continueBtn.setEnabled( checkButtonEnabled() );
   }
   


   @Override
   protected void onDestroy()
   {
      shutDownRtmAuthenticator();
      super.onDestroy();
   }
   


   @Override
   protected Dialog onCreateDialog( int id )
   {
      final ProgressDialog dialog = new ProgressDialog( this );
      dialog.setMessage( getString( R.string.auth_dlg_get_auth_token,
                                    getSelectedPermissionText() ) );
      dialog.setIndeterminate( true );
      dialog.setCancelable( true );
      dialog.setOnCancelListener( new DialogInterface.OnCancelListener()
      {
         public void onCancel( DialogInterface dialog )
         {
            Log.d( TAG, "cancel has been invoked" );
            
            if ( authenticator != null )
               authenticator.cancelExecution();
            
            removeDialog( 0 );
         }
      } );
      return dialog;
   }
   


   public void onAuthenticate( View view )
   {
      messageText.setText( null );
      
      if ( ConnectionChecker.isConnected( this ) )
      {
         final ApplicationInfo applicationInfo = new ApplicationInfo( apiKeyEdit.getText()
                                                                                .toString(),
                                                                      sharedSecrectEdit.getText()
                                                                                       .toString(),
                                                                      getString( R.string.app_name ),
                                                                      null );
         
         try
         {
            final ServiceImpl serviceImpl = new ServiceImpl( applicationInfo );
            authenticator = new AsyncRtmAuthenticator( this, serviceImpl );
            authenticator.beginAuthentication( getSelectedPermission() );
         }
         catch ( ServiceInternalException e )
         {
            messageText.setText( getErrorMessage( e ) );
         }
      }
      else
      {
         new AlertDialog.Builder( this ).setTitle( R.string.err_not_connected )
                                        .setMessage( R.string.phr_establish_connection )
                                        .setIcon( android.R.drawable.ic_dialog_alert )
                                        .show();
      }
   }
   


   public void onCancel( View view )
   {
      finish();
   }
   


   public void onPreBeginAuthentication()
   {
      showDialog( 0 );
   }
   


   public void onPostBeginAuthentication( String loginUrl, Exception e )
   {
      if ( e != null )
      {
         removeDialog( 0 );
         messageText.setText( getErrorMessage( e ) );
      }
      else if ( TextUtils.isEmpty( loginUrl ) )
      {
         removeDialog( 0 );
         messageText.setText( getErrorMessage( R.string.auth_err_cause_inv_login_url ) );
      }
      else
      {
         Log.d( TAG, "LoginURL: " + loginUrl );
         
         final Intent intent = new Intent( android.content.Intent.ACTION_VIEW,
                                           Uri.parse( loginUrl ),
                                           this,
                                           RtmWebLoginActivity.class );
         
         startActivityForResult( intent, RtmWebLoginActivity.ReqType.OPEN_URL );
      }
   }
   


   public void onPostCompleteAuthentication( String authToken,
                                             Exception exception )
   {
      Log.d( TAG, "AuthToken: " + authToken );
      
      if ( exception != null )
      {
         removeDialog( 0 );
         messageText.setText( getErrorMessage( exception ) );
      }
      else if ( TextUtils.isEmpty( authToken ) )
      {
         removeDialog( 0 );
         messageText.setText( getErrorMessage( R.string.auth_err_cause_inv_auth_token ) );
      }
      else
      {
         // We want to get the complete RtmAuth instance
         authenticator.checkAuthToken( authToken );
      }
   }
   


   public void onPostCheckAuthToken( RtmAuth rtmAuth, Exception exception )
   {
      removeDialog( 0 );
      
      if ( exception != null )
      {
         messageText.setText( getErrorMessage( exception ) );
      }
      else if ( rtmAuth == null )
      {
         messageText.setText( getErrorMessage( R.string.auth_err_cause_inv_auth_token ) );
      }
      else
      {
         // Response is received from the server for authentication request.
         // Sets the AccountAuthenticatorResult which is sent back to the caller.
         // Also sets the authToken in AccountManager for this account.
         final Account account = new Account( rtmAuth.getUser().getUsername(),
                                              Constants.ACCOUNT_TYPE );
         try
         {
            boolean ok = true;
            
            if ( newAccount )
            {
               ok = accountManager.addAccountExplicitly( account,
                                                         rtmAuth.getToken(),
                                                         null );
               
               if ( ok )
               {
                  // Set RTM sync for this account.
                  ContentResolver.setSyncAutomatically( account,
                                                        Rtm.AUTHORITY,
                                                        true );
               }
            }
            
            if ( ok )
            {
               accountManager.setUserData( account,
                                           Constants.FEAT_API_KEY,
                                           apiKeyEdit.getText().toString() );
               accountManager.setUserData( account,
                                           Constants.FEAT_SHARED_SECRET,
                                           sharedSecrectEdit.getText()
                                                            .toString() );
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
               intent.putExtra( AccountManager.KEY_AUTHTOKEN,
                                rtmAuth.getToken() );
               
               intent.putExtra( AccountManager.KEY_BOOLEAN_RESULT, true );
               
               setAccountAuthenticatorResult( intent.getExtras() );
               setResult( RESULT_OK, intent );
               finish();
            }
         }
         catch ( SecurityException e )
         {
            messageText.setText( getErrorMessage( R.string.auth_err_cause_scurity )
               + e.getLocalizedMessage() );
         }
      }
   }
   


   public void afterTextChanged( Editable s )
   {
      continueBtn.setEnabled( checkButtonEnabled() );
   }
   


   public void beforeTextChanged( CharSequence s,
                                  int start,
                                  int count,
                                  int after )
   {
   }
   


   public void onTextChanged( CharSequence s, int start, int before, int count )
   {
   }
   


   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      if ( requestCode == RtmWebLoginActivity.ReqType.OPEN_URL
         && resultCode == RtmWebLoginActivity.ReturnCode.SUCCESS )
      {
         authenticator.completeAuthentication();
      }
      else
      {
         // TODO: Set error message here.
         removeDialog( 0 );
      }
   }
   


   private void initializeGui()
   {
      // Check the intent parameters
      final Intent intent = getIntent();
      
      // Fill the widgets with all information we have
      final String apiKey = intent.getStringExtra( Constants.FEAT_API_KEY );
      final String sharedSecret = intent.getStringExtra( Constants.FEAT_SHARED_SECRET );
      final String permission = intent.getStringExtra( Constants.FEAT_PERMISSION );
      
      if ( apiKey != null )
         apiKeyEdit.setText( apiKey );
      if ( sharedSecret != null )
         sharedSecrectEdit.setText( sharedSecret );
      if ( permission != null )
         selectPermission( permission );
      
      if ( intent.getBooleanExtra( PARAM_MISSINGCREDENTIALS, false ) )
      {
         messageText.setText( R.string.auth_missing_credential );
      }
      else if ( intent.getBooleanExtra( PARAM_AUTH_TOKEN_EXPIRED, false ) )
      {
         messageText.setText( getString( R.string.auth_expired_auth_token,
                                         getString( R.string.app_name ) ) );
      }
   }
   


   private boolean checkButtonEnabled()
   {
      return apiKeyEdit.length() > 0 && sharedSecrectEdit.length() > 0;
   }
   


   private String getSelectedPermissionText()
   {
      final String[] rtmPermissions = getResources().getStringArray( R.array.rtm_permissions );
      
      String text = rtmPermissions[ 0 ];
      
      final int selectedIdx = permDropDown.getSelectedItemPosition();
      
      if ( selectedIdx != Spinner.INVALID_POSITION )
      {
         if ( rtmPermissions.length > selectedIdx )
         {
            text = rtmPermissions[ selectedIdx ];
         }
      }
      
      return text;
   }
   


   private RtmAuth.Perms getSelectedPermission()
   {
      RtmAuth.Perms perm = RtmAuth.Perms.nothing;
      
      final int selectedIdx = permDropDown.getSelectedItemPosition();
      
      if ( selectedIdx != Spinner.INVALID_POSITION )
      {
         final String[] rtmPermissionsVals = getResources().getStringArray( R.array.rtm_permissions_values );
         
         if ( rtmPermissionsVals.length > selectedIdx )
         {
            try
            {
               perm = RtmAuth.Perms.valueOf( rtmPermissionsVals[ selectedIdx ] );
            }
            catch ( IllegalArgumentException e )
            {
            }
         }
      }
      
      return perm;
   }
   


   private int selectPermission( String permissionValue )
   {
      int position = Spinner.INVALID_POSITION;
      
      final String[] rtmPermissionsVals = getResources().getStringArray( R.array.rtm_permissions_values );
      
      for ( int i = 0; i < rtmPermissionsVals.length
         && position == Spinner.INVALID_POSITION; i++ )
      {
         if ( rtmPermissionsVals[ i ].equals( permissionValue ) )
            position = i;
      }
      
      if ( position != Spinner.INVALID_POSITION )
         permDropDown.setSelection( position );
      
      return position;
   }
   


   private String getErrorMessage( Exception exception )
   {
      return getString( R.string.auth_err_with_cause,
                        AsyncRtmAuthenticator.getExceptionCause( exception ) );
   }
   


   private String getErrorMessage( int resId )
   {
      return getString( R.string.auth_err_with_cause, getString( resId ) );
   }
   


   private void shutDownRtmAuthenticator()
   {
      if ( authenticator != null )
         authenticator.cancelExecution();
      
      authenticator = null;
   }
}
