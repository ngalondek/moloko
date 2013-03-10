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

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.os.Bundle;
import dev.drsoran.moloko.app.baseactivities.MolokoFragmentActivity;
import dev.drsoran.moloko.ui.fragments.listeners.IAlertDialogFragmentListener;


/**
 * Implementation taken from android.accounts.AccountAuthenticatorActivity
 */
class AccountAuthenticatorFragmentActivity extends MolokoFragmentActivity
         implements IAlertDialogFragmentListener
{
   private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
   
   private Bundle mResultBundle = null;
   
   
   
   public final void setAccountAuthenticatorResult( Bundle result )
   {
      mResultBundle = result;
   }
   
   
   
   @Override
   public void onCreate( Bundle icicle )
   {
      super.onCreate( icicle );
      
      mAccountAuthenticatorResponse = getIntent().getParcelableExtra( AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE );
      
      if ( mAccountAuthenticatorResponse != null )
      {
         mAccountAuthenticatorResponse.onRequestContinued();
      }
   }
   
   
   
   @Override
   public void finish()
   {
      if ( mAccountAuthenticatorResponse != null )
      {
         // send the result bundle back if set, otherwise send an error.
         if ( mResultBundle != null )
         {
            mAccountAuthenticatorResponse.onResult( mResultBundle );
         }
         else
         {
            mAccountAuthenticatorResponse.onError( AccountManager.ERROR_CODE_CANCELED,
                                                   "canceled" );
         }
         mAccountAuthenticatorResponse = null;
      }
      super.finish();
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
}
