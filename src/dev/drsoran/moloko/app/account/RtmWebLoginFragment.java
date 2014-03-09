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

import java.text.MessageFormat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import dev.drsoran.Strings;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmAuthHandle;
import dev.drsoran.rtm.service.RtmFrob;
import dev.drsoran.rtm.service.RtmServicePermission;


public class RtmWebLoginFragment extends AuthFragment implements
         IAuthSequenceListener
{
   @InstanceState( key = AuthConstants.FEAT_PERMISSION, defaultValue = "read" )
   private String permission;
   
   @InstanceState( key = "frob", defaultValue = InstanceState.NULL )
   private String frob;
   
   private AuthenticatorActivity authenticatorActivity;
   
   private AsyncRtmAuthenticator authenticator;
   
   private IHandlerToken handler;
   
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
      handler = getUiContext().acquireHandlerToken();
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
   public void onStart()
   {
      super.onStart();
      
      if ( authenticator != null )
      {
         authenticator.onAttach( this );
      }
   }
   
   
   
   @Override
   public void onStop()
   {
      if ( authenticator != null )
      {
         authenticator.onDetach();
      }
      
      super.onStop();
   }
   
   
   
   @Override
   public void onDetach()
   {
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
      
      webView.requestFocus();
      authenticator.beginAuthentication( this,
                                         RtmServicePermission.valueOf( permission ) );
   }
   
   
   
   @Override
   public void onPreBeginAuthentication()
   {
      getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
      messageText.setText( getString( R.string.auth_dlg_get_auth_token,
                                      getPermissionLocalized() ) );
   }
   
   
   
   @Override
   public void onPostBeginAuthentication( RtmAuthHandle authHandle,
                                          RtmServiceException exception )
   {
      if ( exception != null )
      {
         notifyAuthenticationFailed( exception );
      }
      else if ( TextUtils.isEmpty( authHandle.getAuthUri() ) )
      {
         notifyAuthenticationFailed( R.string.auth_err_cause_inv_login_url );
      }
      else
      {
         frob = authHandle.getFrob().getValue();
         
         Log().d( getClass(),
                  MessageFormat.format( "LoginURL: {0}",
                                        authHandle.getAuthUri() ) );
         
         messageText.setText( getString( R.string.auth_info_text ) );
         
         webView.setVisibility( View.VISIBLE );
         webView.loadUrl( authHandle.getAuthUri() );
         
         button.setText( R.string.btn_continue );
      }
   }
   
   
   
   @Override
   public void onAuthenticationCompleted( RtmAuth rtmAuth,
                                          RtmServiceException exception )
   {
      Log().d( getClass(), MessageFormat.format( "AuthToken: {0}", rtmAuth ) );
      
      if ( exception != null )
      {
         notifyAuthenticationFailed( exception );
      }
      else if ( TextUtils.isEmpty( rtmAuth.getToken() ) )
      {
         notifyAuthenticationFailed( R.string.auth_err_cause_inv_auth_token );
      }
      else
      {
         messageText.setText( getString( R.string.auth_completing ) );
         webView.setVisibility( View.INVISIBLE );
         button.setText( R.string.btn_cancel );
         
         getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );
         notifyAuthenticationFinished( rtmAuth );
      }
   }
   
   
   
   @Override
   public void onCancelButtonClicked()
   {
      final String buttonText = button.getText().toString();
      
      if ( Strings.equalsNullAware( buttonText, getString( R.string.btn_cancel ) ) )
      {
         notifyAuthenticationCanceled();
      }
      else if ( Strings.equalsNullAware( buttonText,
                                         getString( R.string.btn_continue ) ) )
      {
         authenticator.completeAuthentication( this, new RtmFrob( frob ) );
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
      
      if ( handler != null )
      {
         handler.release();
         handler = null;
      }
      
      super.onDestroy();
   }
   
   
   
   @Override
   public void post( Runnable runnable )
   {
      handler.post( runnable );
   }
   
   
   
   private void createAuthenticator()
   {
      final AppContext appContext = AppContext.get( authenticatorActivity );
      authenticator = new AsyncRtmAuthenticator( appContext.getRtmAuthService(),
                                                 appContext.getExecutorService() );
   }
   
   
   
   private void shutdownRtmAuthenticator()
   {
      if ( authenticator != null )
      {
         authenticator.shutdown();
      }
      
      authenticator = null;
   }
   
   
   
   private void notifyAuthenticationFinished( RtmAuth auth )
   {
      authenticatorActivity.onAuthenticationFinished( auth );
   }
   
   
   
   private void notifyAuthenticationCanceled()
   {
      shutdownRtmAuthenticator();
      getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );
      
      authenticatorActivity.onAuthenticationCanceled();
   }
   
   
   
   private void notifyAuthenticationFailed( Exception exception )
   {
      notifyAuthenticationFailed( exception.getLocalizedMessage() );
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
      switch ( RtmServicePermission.valueOf( permission ) )
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
         
         // This may be null if the callback from the WebView comes while
         // detaching the fragment.
         if ( getSherlockActivity() != null )
         {
            getSherlockActivity().setSupportProgressBarIndeterminateVisibility( true );
         }
      }
      
      
      
      @Override
      public void onPageFinished( WebView view, String url )
      {
         super.onPageFinished( view, url );
         
         // This may be null if the callback from the WebView comes while
         // detaching the fragment.
         if ( getSherlockActivity() != null )
         {
            getSherlockActivity().setSupportProgressBarIndeterminateVisibility( false );
         }
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
