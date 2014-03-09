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

package dev.drsoran.moloko.app.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.service.RtmServicePermission;


public class AccountIssueFragment extends AuthFragment
{
   public final static class Config
   {
      public final static String ACCOUNT_ALREADY_EXISTS = "account_exists";
   }
   
   @InstanceState( key = Intents.Extras.AUTH_TOKEN_EXPIRED )
   private boolean isAuthTokenExpired;
   
   @InstanceState( key = Intents.Extras.AUTH_MISSINGCREDENTIALS )
   private boolean isMissingCredentials;
   
   @InstanceState( key = Config.ACCOUNT_ALREADY_EXISTS )
   private boolean isAccountAlreadyExisting;
   
   @InstanceState( key = AuthConstants.FEAT_PERMISSION )
   private String permission;
   
   private IStartAuthenticationFragmentListener listener;
   
   
   
   public final static AccountIssueFragment newInstance( Bundle config )
   {
      final AccountIssueFragment fragment = new AccountIssueFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public AccountIssueFragment()
   {
      registerAnnotatedConfiguredInstance( this, AccountIssueFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IStartAuthenticationFragmentListener )
         listener = (IStartAuthenticationFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.auth_account_issue_fragment,
                                                  container,
                                                  false );
      
      final TextView messageView = (TextView) fragmentView.findViewById( R.id.error );
      if ( isAuthTokenExpired )
      {
         messageView.setText( R.string.auth_expired_auth_token );
      }
      else if ( isMissingCredentials )
      {
         messageView.setText( R.string.auth_missing_credential );
      }
      else if ( isAccountAlreadyExisting )
      {
         messageView.setText( R.string.auth_account_exists );
         fragmentView.findViewById( android.R.id.button1 ).setEnabled( false );
      }
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onContinueButtonClicked()
   {
      if ( listener != null )
      {
         listener.onStartAuthentication( RtmServicePermission.valueOf( permission ) );
      }
   }
}
