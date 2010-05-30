package dev.drsoran.moloko.auth;

import com.mdt.rtm.data.RtmAuth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.service.RtmService;
import dev.drsoran.moloko.service.async.AsyncRtmService;


public class Authenticator extends AbstractAccountAuthenticator
{
   private final Context context;
   
   

   public Authenticator( Context context )
   {
      super( context );
      this.context = context;
   }
   


   @Override
   public Bundle addAccount( AccountAuthenticatorResponse response,
                             String accountType,
                             String authTokenType,
                             String[] requiredFeatures,
                             Bundle options ) throws NetworkErrorException
   {
      // we do not have all information here to add a new account. We miss
      // user name and password. So we start an activity to request these from
      // the user.
      final Intent intent = new Intent( context, AuthenticatorActivity.class );
      intent.putExtra( AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE,
                       authTokenType );
      intent.putExtra( AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
                       response );
      
      final Bundle bundle = new Bundle();
      // This signals that an activity will return the result.
      bundle.putParcelable( AccountManager.KEY_INTENT, intent );
      
      return bundle;
   }
   


   @Override
   public Bundle confirmCredentials( AccountAuthenticatorResponse response,
                                     Account account,
                                     Bundle options ) throws NetworkErrorException
   {
      final Bundle result = new Bundle();
      
      if ( options != null )
      {
         final String authToken = options.getString( AccountManager.KEY_AUTHTOKEN );
         final String apiKey = options.getString( context.getString( R.string.key_api_key ) );
         final String sharedSecret = options.getString( context.getString( R.string.key_shared_secret ) );
         final RtmAuth currentAuth = options.getParcelable( context.getString( R.string.key_auth ) );
         
         final AsyncRtmService asyncRtmService = new AsyncRtmService( context,
                                                                      null );
         RtmAuth auth;
         
         try
         {
            auth = asyncRtmService.sync().checkAuthToken( authToken );
            result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, auth != null );
         }
         catch ( RemoteException e )
         {
            result.putInt( AccountManager.KEY_ERROR_CODE,
                           AccountManager.ERROR_CODE_REMOTE_EXCEPTION );
         }
      }
      // Launch AuthenticatorActivity to confirm credentials
      // final Intent intent = new Intent( mContext, AuthenticatorActivity.class );
      // intent.putExtra( AuthenticatorActivity.PARAM_USERNAME, account.name );
      // intent.putExtra( AuthenticatorActivity.PARAM_CONFIRMCREDENTIALS, true );
      // intent.putExtra( AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
      // response );
      // final Bundle bundle = new Bundle();
      // bundle.putParcelable( AccountManager.KEY_INTENT, intent );
      // return bundle;
      //      
      return result;
   }
   


   @Override
   public Bundle editProperties( AccountAuthenticatorResponse response,
                                 String accountType )
   {
      final Bundle result = new Bundle();
      
      result.putInt( AccountManager.KEY_ERROR_CODE,
                     AccountManager.ERROR_CODE_UNSUPPORTED_OPERATION );
      
      return result;
   }
   


   @Override
   public Bundle getAuthToken( AccountAuthenticatorResponse response,
                               Account account,
                               String authTokenType,
                               Bundle options ) throws NetworkErrorException
   {
      final Bundle result = new Bundle();
      
      if ( authTokenType.equals( Constants.AUTH_TOKEN_TYPE ) )
      {
         
      }
      else
      {
         result.putString( AccountManager.KEY_ERROR_MESSAGE,
                           context.getString( R.string.auth_err_cause_inv_auth_token ) );
      }
      
      final AccountManager accountManager = AccountManager.get( context );
      
      // final String authToken = accountManager.
      //      
      // if ( password != null )
      // {
      // final boolean verified = onlineConfirmPassword( account.name, password );
      // if ( verified )
      // {
      // final Bundle result = new Bundle();
      // result.putString( AccountManager.KEY_ACCOUNT_NAME, account.name );
      // result.putString( AccountManager.KEY_ACCOUNT_TYPE,
      // Constants.ACCOUNT_TYPE );
      // result.putString( AccountManager.KEY_AUTHTOKEN, password );
      // return result;
      // }
      // }
      // // the password was missing or incorrect, return an Intent to an
      // // Activity that will prompt the user for the password.
      // final Intent intent = new Intent( mContext, AuthenticatorActivity.class );
      // intent.putExtra( AuthenticatorActivity.PARAM_USERNAME, account.name );
      // intent.putExtra( AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE,
      // authTokenType );
      // intent.putExtra( AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
      // response );
      // final Bundle bundle = new Bundle();
      // bundle.putParcelable( AccountManager.KEY_INTENT, intent );
      // return bundle;
      
      return result;
   }
   


   @Override
   public String getAuthTokenLabel( String authTokenType )
   {
      if ( authTokenType.equals( Constants.AUTH_TOKEN_TYPE ) )
      {
         return context.getString( R.string.moloko_rtm );
      }
      
      return null;
   }
   


   @Override
   public Bundle hasFeatures( AccountAuthenticatorResponse response,
                              Account account,
                              String[] features ) throws NetworkErrorException
   {
      
      final Bundle result = new Bundle();
      result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, false );
      return result;
   }
   


   @Override
   public Bundle updateCredentials( AccountAuthenticatorResponse response,
                                    Account account,
                                    String authTokenType,
                                    Bundle options ) throws NetworkErrorException
   {
      final Intent intent = new Intent( context, AuthenticatorActivity.class );
      
      intent.putExtra( AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE,
                       authTokenType );
      intent.putExtra( AuthenticatorActivity.PARAM_CONFIRMCREDENTIALS, false );
      
      final Bundle bundle = new Bundle();
      bundle.putParcelable( AccountManager.KEY_INTENT, intent );
      return bundle;
   }
}
