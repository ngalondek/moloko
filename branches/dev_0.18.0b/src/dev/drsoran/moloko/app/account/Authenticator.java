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

package dev.drsoran.moloko.app.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.RtmServiceConstants;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;


public class Authenticator extends AbstractAccountAuthenticator
{
   private final AppContext context;
   
   
   
   public Authenticator( Context context )
   {
      super( context );
      this.context = AppContext.get( context );
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
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public Bundle updateCredentials( AccountAuthenticatorResponse response,
                                    Account account,
                                    String authTokenType,
                                    Bundle options ) throws NetworkErrorException
   {
      // Currently it is not possible to update an authToken for an account. This method
      // is intended to be used to update the password. But we store no password so
      // we can't use it.
      throw new UnsupportedOperationException();
      
      /**
       * final Bundle result = new Bundle();
       * 
       * boolean ok = options != null;
       * 
       * if ( ok ) { // Check the feature to update if ( options.getBoolean( Constants.FEAT_PERMISSION ) ) { final
       * Intent intent = new Intent( context, AuthenticatorActivity.class );
       * 
       * configureIntent( context, intent, account );
       * 
       * intent.putExtra( AuthenticatorActivity.PARAM_UPDATECREDENTIALS, true ); intent.putExtra(
       * AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response );
       * 
       * result.putParcelable( AccountManager.KEY_INTENT, intent ); } else { ok = false; } }
       * 
       * if ( !ok ) { result.putInt( AccountManager.KEY_ERROR_CODE, AccountManager.ERROR_CODE_BAD_ARGUMENTS );
       * result.putBoolean( AccountManager.KEY_BOOLEAN_RESULT, false ); }
       * 
       * return result; }
       */
   }
   
   
   
   @Override
   public Bundle editProperties( AccountAuthenticatorResponse response,
                                 String accountType )
   {
      throw new UnsupportedOperationException();
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
            Service service = null;
            try
            {
               service = ServiceImpl.getInstance( context.getConnectionService()
                                                         .getRtmConnectionFactory(),
                                                  context.Log(),
                                                  context.getSettings()
                                                         .isUsingHttps(),
                                                  new ApplicationInfo( apiKey,
                                                                       sharedSecret,
                                                                       null ) );
               service.auth_checkToken( authToken );
               
               if ( !authTokenExpired )
               {
                  result.putString( AccountManager.KEY_ACCOUNT_NAME,
                                    account.name );
                  result.putString( AccountManager.KEY_ACCOUNT_TYPE,
                                    Constants.ACCOUNT_TYPE );
                  result.putString( AccountManager.KEY_AUTHTOKEN, authToken );
               }
            }
            catch ( Throwable e )
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
            finally
            {
               if ( service != null )
                  service.shutdown();
            }
         }
         
         if ( missingCredential || authTokenExpired )
         {
            final Intent intent = new Intent( context,
                                              AuthenticatorActivity.class );
            
            configureIntent( context, intent, account );
            
            if ( missingCredential )
               intent.putExtra( Intents.Extras.AUTH_MISSINGCREDENTIALS, true );
            else if ( authTokenExpired )
               intent.putExtra( Intents.Extras.AUTH_TOKEN_EXPIRED, true );
            
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
   
   
   
   private final static void configureIntent( Context context,
                                              Intent intent,
                                              Account account )
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
