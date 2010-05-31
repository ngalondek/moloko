package dev.drsoran.moloko.auth;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.service.async.AsyncRtmService;
import dev.drsoran.moloko.util.ResultCallback;
import dev.drsoran.provider.Rtm;


/**
 * Activity which displays login screen to the user.
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity
         implements TextWatcher
{
   public static final String PARAM_AUTH_TOKEN_EXPIRED = "authTokenExpired";
   
   public static final String PARAM_MISSING_CREDENTIALS = "missingCredentials";
   
   public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
   
   public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
   
   private static final String TAG = AuthenticatorActivity.class.getSimpleName();
   
   private AccountManager accountManager;
   
   private AsyncRtmService asyncRtmService;
   
   private EditText apiKeyEdit;
   
   private EditText sharedSecrectEdit;
   
   private Spinner permDropDown;
   
   private Button continueBtn;
   
   private TextView messageText;
   
   

   @Override
   public void onCreate( Bundle icicle )
   {
      super.onCreate( icicle );
      
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
      
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
      
      if ( prefs != null )
      {
         apiKeyEdit.setText( prefs.getString( getString( R.string.auth_pref_api_key_key ),
                                              "" ) );
         sharedSecrectEdit.setText( prefs.getString( getString( R.string.auth_pref_api_shared_secret_key ),
                                                     "" ) );
      }
      
      continueBtn.setEnabled( checkButtonEnabled() );
   }
   


   @Override
   protected void onDestroy()
   {
      shutDownRtmService();
      super.onDestroy();
   }
   


   public void onAuthenticate( View view )
   {
      messageText.setText( null );
      
      final ApplicationInfo applicationInfo = new ApplicationInfo( apiKeyEdit.getText()
                                                                             .toString(),
                                                                   sharedSecrectEdit.getText()
                                                                                    .toString(),
                                                                   getString( R.string.app_name ),
                                                                   null );
      
      shutDownRtmService();
      
      asyncRtmService = new AsyncRtmService( this, applicationInfo );
      
      asyncRtmService.auth().beginAuthorization( getSelectedPermission(),
                                                 new ResultCallback< String >()
                                                 {
                                                    public void run()
                                                    {
                                                       onLoginUrlReceived( result,
                                                                           exception );
                                                    }
                                                 } );
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
         asyncRtmService.auth()
                        .completeAuthorization( new ResultCallback< String >()
                        {
                           public void run()
                           {
                              onAuthTokenReceived( result, exception );
                           }
                        } );
      }
   }
   


   private void onLoginUrlReceived( String loginUrl, Exception exception )
   {
      if ( exception != null )
      {
         messageText.setText( getErrorMessage( exception ) );
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
   


   private void onAuthTokenReceived( String authToken, Exception exception )
   {
      Log.d( TAG, "AuthToken: " + authToken );
      
      boolean ok = exception == null && authToken != null;
      
      if ( ok )
      {
         // We want to get the complete RtmAuth instance
         asyncRtmService.auth().checkAuthToken( authToken,
                                                new ResultCallback< RtmAuth >()
                                                {
                                                   public void run()
                                                   {
                                                      onRtmAuthReceived( result,
                                                                         exception );
                                                   }
                                                } );
         
      }
      else
      {
         if ( exception != null )
         {
            messageText.setText( getErrorMessage( exception ) );
         }
         
         else if ( authToken == null )
         {
            messageText.setText( getErrorMessage( R.string.auth_err_cause_inv_auth_token ) );
         }
      }
   }
   


   private void onRtmAuthReceived( RtmAuth rtmAuth, Exception exception )
   {
      if ( exception == null && rtmAuth != null )
      {
         // Response is received from the server for authentication request.
         // Sets the AccountAuthenticatorResult which is sent back to the caller.
         // Also sets the authToken in AccountManager for this account.
         final Account account = new Account( rtmAuth.getUser().getUsername(),
                                              Constants.ACCOUNT_TYPE );
         
         try
         {
            if ( accountManager.addAccountExplicitly( account, null, null ) )
            {
               accountManager.setUserData( account,
                                           getString( R.string.key_api_key ),
                                           apiKeyEdit.getText().toString() );
               accountManager.setUserData( account,
                                           getString( R.string.key_shared_secret ),
                                           sharedSecrectEdit.getText()
                                                            .toString() );
               accountManager.setUserData( account,
                                           getString( R.string.key_permission ),
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
               
               // Set RTM sync for this account.
               ContentResolver.setSyncAutomatically( account,
                                                     Rtm.AUTHORITY,
                                                     true );
               
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
      else
      {
         if ( exception != null )
         {
            messageText.setText( getErrorMessage( exception ) );
         }
         
         else if ( rtmAuth == null )
         {
            messageText.setText( getErrorMessage( R.string.auth_err_cause_inv_auth_token ) );
         }
      }
   }
   


   private boolean checkButtonEnabled()
   {
      return apiKeyEdit.length() > 0 && sharedSecrectEdit.length() > 0;
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
   


   // TODO: Remove
   // private void finsihWithException( Exception exception )
   // {
   // final Intent intent = new Intent();
   //      
   // if ( exception instanceof RtmServiceException )
   // {
   // final RtmServiceException rtmServiceException = (RtmServiceException) exception;
   //         
   // intent.putExtra( AccountManager.KEY_ERROR_CODE,
   // AsyncRtmService.getExceptionCode( exception ) );
   // intent.putExtra( AccountManager.KEY_ERROR_MESSAGE,
   // getErrorMessage( rtmServiceException ) );
   // }
   // else
   // {
   // intent.putExtra( AccountManager.KEY_ERROR_MESSAGE,
   // getString( R.string.auth_err_with_cause,
   // exception.getLocalizedMessage() ) );
   // }
   //      
   // intent.putExtra( AccountManager.KEY_BOOLEAN_RESULT, false );
   //      
   // setAccountAuthenticatorResult( intent.getExtras() );
   // setResult( RESULT_OK, intent );
   //      
   // finish();
   // }
   
   private String getErrorMessage( Exception exception )
   {
      return getString( R.string.auth_err_with_cause,
                        AsyncRtmService.getExceptionCause( exception ) );
   }
   


   private String getErrorMessage( int resId )
   {
      return getString( R.string.auth_err_with_cause, getString( resId ) );
   }
   


   private void shutDownRtmService()
   {
      if ( asyncRtmService != null )
      {
         asyncRtmService.shutdown();
         asyncRtmService = null;
      }
   }
}
