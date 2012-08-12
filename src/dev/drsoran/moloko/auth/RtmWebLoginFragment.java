/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.auth;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Strings;


public class RtmWebLoginFragment extends AuthFragment implements
         IAuthSequenceListener
{
   private static final String TAG = "Moloko."
      + RtmWebLoginFragment.class.getSimpleName();
   
   private final Handler handler = new Handler();
   
   @InstanceState( key = Constants.FEAT_PERMISSION, defaultValue = "read" )
   private String permission;
   
   private AuthenticatorActivity authenticatorActivity;
   
   private AsyncRtmAuthenticator authenticator;
   
   private TextView messageText;
   
   private WebView webView;
   
   private Button button;
   
   
   
   public final static RtmWebLoginFragment newInstance( Bundle config )
   {
      final RtmWebLoginFragment fragment = new RtmWebLoginFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public RtmWebLoginFragment()
   {
      registerAnnotatedConfiguredInstance( this, RtmWebLoginFragment.class );
      setRetainInstance( true );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      authenticatorActivity = (AuthenticatorActivity) activity;
      if ( authenticator != null )
      {
         authenticator.onAttach( this );
      }
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( authenticator == null )
      {
         createAuthenticator();
      }
   }
   
   
   
   @Override
   public void onDetach()
   {
      if ( authenticator != null )
      {
         authenticator.onDetach();
      }
      
      authenticatorActivity = null;
      
      super.onDetach();
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.auth_rtm_web_login_fragment,
                                                  container,
                                                  false );
      
      messageText = (TextView) fragmentView.findViewById( android.R.id.text1 );
      
      webView = (WebView) fragmentView.findViewById( R.id.webview );
      webView.getSettings().setJavaScriptEnabled( true );
      webView.setWebViewClient( new RtmWebViewClient() );
      
      button = (Button) fragmentView.findViewById( android.R.id.button2 );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      authenticator.beginAuthentication( this,
                                         RtmAuth.Perms.valueOf( permission ) );
   }
   
   
   
   @Override
   public void onPreBeginAuthentication()
   {
      getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
      messageText.setText( getString( R.string.auth_dlg_get_auth_token,
                                      getPermissionLocalized() ) );
   }
   
   
   
   @Override
   public void onPostBeginAuthentication( String loginUrl,
                                          ServiceException exception )
   {
      if ( exception != null )
      {
         notifyAuthenticationFailed( exception );
      }
      else if ( TextUtils.isEmpty( loginUrl ) )
      {
         notifyAuthenticationFailed( R.string.auth_err_cause_inv_login_url );
      }
      else
      {
         Log.d( TAG, "LoginURL: " + loginUrl );
         
         messageText.setText( getString( R.string.auth_info_text ) );
         
         webView.setVisibility( View.VISIBLE );
         webView.loadUrl( loginUrl );
         
         button.setText( R.string.btn_continue );
      }
   }
   
   
   
   @Override
   public void onAuthenticationCompleted( String authToken,
                                          ServiceException exception )
   {
      Log.d( TAG, "AuthToken: " + authToken );
      
      if ( exception != null )
      {
         notifyAuthenticationFailed( exception );
      }
      else if ( TextUtils.isEmpty( authToken ) )
      {
         notifyAuthenticationFailed( R.string.auth_err_cause_inv_auth_token );
      }
      else
      {
         messageText.setText( getString( R.string.auth_completing ) );
         webView.setVisibility( View.INVISIBLE );
         button.setText( R.string.btn_cancel );
         
         getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
         
         // We want to get the complete RtmAuth instance
         authenticator.checkAuthToken( this, authToken );
      }
   }
   
   
   
   @Override
   public void onAuthTokenChecked( RtmAuth rtmAuth, ServiceException exception )
   {
      getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );
      
      if ( exception != null )
      {
         notifyAuthenticationFailed( exception );
      }
      else if ( rtmAuth == null )
      {
         notifyAuthenticationFailed( R.string.auth_err_cause_inv_auth_token );
      }
      else
      {
         notifyAuthenticationFinished( rtmAuth );
      }
   }
   
   
   
   @Override
   public void onCancelButtonClicked()
   {
      final String buttonText = button.getText().toString();
      
      if ( Strings.equals( buttonText, getString( R.string.btn_cancel ) ) )
      {
         notifyAuthenticationCanceled();
      }
      else if ( Strings.equals( buttonText, getString( R.string.btn_continue ) ) )
      {
         authenticator.completeAuthentication( this );
      }
   }
   
   
   
   @Override
   public void onDestroy()
   {
      if ( authenticator != null )
      {
         authenticator.onDetach();
         authenticator = null;
      }
      
      authenticatorActivity = null;
      
      super.onDestroy();
   }
   
   
   
   @Override
   public void post( Runnable runnable )
   {
      handler.post( runnable );
   }
   
   
   
   private void createAuthenticator()
   {
      try
      {
         authenticator = new AsyncRtmAuthenticator( authenticatorActivity );
      }
      catch ( ServiceInternalException e )
      {
         Log.e( LogUtils.toTag( RtmWebLoginFragment.class ),
                "Error creating RTM service",
                e );
      }
   }
   
   
   
   private void shutdownRtmAuthenticator()
   {
      if ( authenticator != null )
      {
         authenticator.shutdown();
      }
      
      authenticator = null;
   }
   
   
   
   private void notifyAuthenticationFinished( RtmAuth rtmAuth )
   {
      authenticatorActivity.onAuthenticationFinished( rtmAuth );
   }
   
   
   
   private void notifyAuthenticationCanceled()
   {
      shutdownRtmAuthenticator();
      getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );
      
      authenticatorActivity.onAuthenticationCanceled();
   }
   
   
   
   private void notifyAuthenticationFailed( Exception exception )
   {
      notifyAuthenticationFailed( AsyncRtmAuthenticator.getExceptionCause( exception ) );
   }
   
   
   
   private void notifyAuthenticationFailed( int resId )
   {
      notifyAuthenticationFailed( getString( resId ) );
   }
   
   
   
   private void notifyAuthenticationFailed( String cause )
   {
      shutdownRtmAuthenticator();
      authenticatorActivity.onAuthenticationFailed( cause );
   }
   
   
   
   private String getPermissionLocalized()
   {
      switch ( RtmAuth.Perms.valueOf( permission ) )
      {
         case read:
            return getString( R.string.auth_perm_read );
            
         case write:
         case delete:
            return getString( R.string.auth_perm_delete );
            
         default :
            throw new RuntimeException();
      }
   }
   
   
   private class RtmWebViewClient extends WebViewClient
   {
      
      @Override
      public void onPageStarted( WebView view, String url, Bitmap favicon )
      {
         super.onPageStarted( view, url, favicon );
         getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
      }
      
      
      
      @Override
      public void onPageFinished( WebView view, String url )
      {
         super.onPageFinished( view, url );
         getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );
      }
      
      
      
      // This prevents that a new browser will be started if a link is
      // clicked. So we handle this.
      @Override
      public boolean shouldOverrideUrlLoading( WebView view, String url )
      {
         view.loadUrl( url );
         return true;
      }
   }
}
