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

package dev.drsoran.moloko.app.prefs;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IAccountUpdatedListener;
import dev.drsoran.moloko.app.event.ISyncStatusListener;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.ui.services.IDateFormatterService;


class RtmSyncStatePreference extends InfoTextPreference implements
         ISyncStatusListener, OnCancelListener,
         AccountManagerCallback< Bundle >, IAccountUpdatedListener
{
   private ProgressDialog dialog;
   
   private Account account;
   
   private AccountManagerFuture< Bundle > addAccountHandle;
   
   private final IHandlerToken handlerToken;
   
   
   
   public RtmSyncStatePreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      final AppContext appContext = getAppContext();
      
      handlerToken = appContext.acquireHandlerToken();
      account = appContext.getAccountService().getRtmAccount();
      
      appContext.getAppEvents().registerAccountUpdatedListener( this );
   }
   
   
   
   @Override
   public void run( AccountManagerFuture< Bundle > future )
   {
      addAccountHandle = null;
      
      if ( future.isDone() )
      {
         try
         {
            future.getResult();
         }
         catch ( OperationCanceledException e )
         {
            Toast.makeText( getContext(),
                            R.string.err_add_account_canceled,
                            Toast.LENGTH_SHORT ).show();
         }
         catch ( AuthenticatorException e )
         {
            // According to the doc this can only happen
            // if there is no authenticator registered for the
            // account type. This should not happen.
            Toast.makeText( getContext(),
                            R.string.err_unexpected,
                            Toast.LENGTH_LONG ).show();
         }
         catch ( IOException e )
         {
            // Will be notified in the AuthenticatorActivity
         }
      }
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      this.account = account;
      notifyChanged();
   }
   
   
   
   @Override
   public void cleanUp()
   {
      getAppContext().getAppEvents().unregisterAccountUpdatedListener( this );
      
      if ( addAccountHandle != null )
      {
         addAccountHandle.cancel( true );
         addAccountHandle = null;
      }
      
      unregisterSyncStatusChangedListener();
      handlerToken.release();
   }
   
   
   
   @Override
   protected void setupPreference( View view )
   {
      super.setupPreference( view );
      
      if ( account != null )
      {
         final Settings settings = getSettings().getRtmSettings();
         
         if ( settings == null )
         {
            setSummary( R.string.moloko_prefs_rtm_sync_text_no_settings );
         }
         else
         {
            final String date = getAppContext().getDateFormatter()
                                               .formatDate( settings.getSyncTimeStampMillis(),
                                                            IDateFormatterService.FORMAT_NUMERIC
                                                               | IDateFormatterService.FORMAT_WITH_YEAR );
            
            setSummary( getContext().getString( R.string.moloko_prefs_rtm_sync_text_in_sync,
                                                date ) );
         }
         
         setIcon( R.drawable.ic_prefs_refresh );
      }
      else
      {
         setSummary( R.string.g_no_account );
         setIcon( R.drawable.ic_prefs_add );
      }
   }
   
   
   
   @Override
   protected void onClick()
   {
      if ( account != null )
      {
         registerSyncStatusChangedListener();
         getAppContext().getSyncService().requestSettingsOnlySync( account );
         
         showDialog();
      }
      else
      {
         // TODO: Show NoAccountDialogFragment if we use PreferenceFragment
         getContext().startActivity( Intents.createNewAccountIntent() );
      }
   }
   
   
   
   protected void showDialog()
   {
      dialog = ProgressDialog.show( getContext(),
                                    null,
                                    getContext().getString( R.string.moloko_prefs_rtm_sync_dlg_message ),
                                    true );
      dialog.setCancelable( true );
      dialog.setOnCancelListener( this );
   }
   
   
   
   @Override
   public void onSyncStatusChanged( int status )
   {
      if ( status == Constants.SYNC_STATUS_FINISHED )
      {
         unregisterSyncStatusChangedListener();
         
         handlerToken.post( new Runnable()
         {
            @Override
            public void run()
            {
               notifyChanged();
            }
         } );
         
         if ( dialog != null )
         {
            dialog.dismiss();
            dialog = null;
         }
      }
   }
   
   
   
   @Override
   public void onCancel( DialogInterface dialog )
   {
      unregisterSyncStatusChangedListener();
      dialog = null;
   }
   
   
   
   private void registerSyncStatusChangedListener()
   {
      getAppContext().getAppEvents().registerSyncStatusChangedListener( this );
   }
   
   
   
   private void unregisterSyncStatusChangedListener()
   {
      getAppContext().getAppEvents().unregisterSyncStatusChangedListener( this );
   }
}
