package dev.drsoran.moloko.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.service.RtmServiceConstants;


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
      throw new UnsupportedOperationException( "confirmCredentials" );
   }
   


   @Override
   public Bundle updateCredentials( AccountAuthenticatorResponse response,
                                    Account account,
                                    String authTokenType,
                                    Bundle options ) throws NetworkErrorException
   {
      throw new UnsupportedOperationException( "updateCredentials" );
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
         final AccountManager accountManager = AccountManager.get( context );
         final String authToken = accountManager.getPassword( account );
         final String apiKey = accountManager.getUserData( account,
                                                           Constants.FEAT_API_KEY );
         final String sharedSecret = accountManager.getUserData( account,
                                                                 Constants.FEAT_SHARED_SECRET );
         final String permission = accountManager.getUserData( account,
                                                               Constants.FEAT_PERMISSION );
         
         final boolean missingCredential = apiKey == null
            || sharedSecret == null || permission == null;
         
         boolean authTokenExpired = authToken == null;
         
         if ( !missingCredential && !authTokenExpired )
         {
            try
            {
               new ServiceImpl( new ApplicationInfo( apiKey, sharedSecret, null ) ).auth_checkToken( authToken );
               
               if ( !authTokenExpired )
               {
                  result.putString( AccountManager.KEY_ACCOUNT_NAME,
                                    account.name );
                  result.putString( AccountManager.KEY_ACCOUNT_TYPE,
                                    Constants.ACCOUNT_TYPE );
                  result.putString( AccountManager.KEY_AUTHTOKEN, authToken );
               }
            }
            catch ( Exception e )
            {
               if ( e instanceof ServiceException )
               {
                  final ServiceException se = (ServiceException) e;
                  
                  if ( se.responseCode == RtmServiceConstants.RtmErrorCodes.INVALID_AUTH_TOKEN )
                  {
                     authTokenExpired = true;
                  }
                  else
                  {
                     result.putInt( AccountManager.KEY_ERROR_CODE,
                                    AccountManager.ERROR_CODE_REMOTE_EXCEPTION );
                     result.putString( AccountManager.KEY_ERROR_MESSAGE,
                                       se.responseMessage );
                     result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT,
                                        false );
                     
                     return result;
                  }
               }
               else
               {
                  result.putInt( AccountManager.KEY_ERROR_CODE,
                                 AccountManager.ERROR_CODE_REMOTE_EXCEPTION );
                  result.putString( AccountManager.KEY_ERROR_MESSAGE,
                                    e.getLocalizedMessage() );
                  result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, false );
                  
                  return result;
               }
            }
         }
         
         if ( missingCredential || authTokenExpired )
         {
            final Intent intent = new Intent( context,
                                              AuthenticatorActivity.class );
            
            configureIntent( intent, account );
            
            if ( missingCredential )
               intent.putExtra( AuthenticatorActivity.PARAM_MISSINGCREDENTIALS,
                                true );
            else if ( authTokenExpired )
               intent.putExtra( AuthenticatorActivity.PARAM_AUTH_TOKEN_EXPIRED,
                                true );
            
            intent.putExtra( AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
                             response );
            
            result.putParcelable( AccountManager.KEY_INTENT, intent );
         }
      }
      else
      {
         result.putString( AccountManager.KEY_ERROR_MESSAGE,
                           context.getString( R.string.auth_err_cause_inv_auth_token ) );
         result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, false );
      }
      
      return result;
   }
   


   @Override
   public String getAuthTokenLabel( String authTokenType )
   {
      if ( authTokenType.equals( Constants.AUTH_TOKEN_TYPE ) )
      {
         return context.getString( R.string.rtm_full );
      }
      
      return null;
   }
   


   @Override
   public Bundle hasFeatures( AccountAuthenticatorResponse response,
                              Account account,
                              String[] features ) throws NetworkErrorException
   {
      boolean satisfied = features != null;
      
      for ( int i = 0; satisfied && i < features.length; i++ )
      {
         boolean match = false;
         
         for ( int j = 0; j < Constants.FEATURES.length && !match; j++ )
         {
            match = features[ i ].equals( Constants.FEATURES[ j ] );
         }
         
         satisfied = match;
      }
      
      final Bundle result = new Bundle();
      result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, satisfied );
      return result;
   }
   


   private void configureIntent( Intent intent, Account account )
   {
      final AccountManager accountManager = AccountManager.get( context );
      
      for ( int i = 0; i < Constants.FEATURES.length; i++ )
      {
         final String userData = accountManager.getUserData( account,
                                                             Constants.FEATURES[ i ] );
         
         if ( userData != null )
         {
            intent.putExtra( Constants.FEATURES[ i ], userData );
         }
      }
      
      intent.putExtra( AccountManager.KEY_ACCOUNT_NAME, account.name );
   }
}
