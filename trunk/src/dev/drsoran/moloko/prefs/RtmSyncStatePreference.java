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

package dev.drsoran.moloko.prefs;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SyncStatusObserver;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm;
import dev.drsoran.rtm.RtmSettings;


public class RtmSyncStatePreference extends InfoTextPreference implements
         SyncStatusObserver, OnCancelListener, OnAccountsUpdateListener,
         AccountManagerCallback< Bundle >
{
   private Object syncStatusHandle;
   
   private ProgressDialog dialog;
   
   private Account account;
   
   private AccountManagerFuture< Bundle > addAccountHandle;
   
   private final Handler handler = new Handler();
   
   

   public RtmSyncStatePreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      final AccountManager accountManager = AccountManager.get( getContext() );
      
      if ( accountManager != null )
      {
         account = AccountUtils.getRtmAccount( accountManager );
         accountManager.addOnAccountsUpdatedListener( this, handler, true );
      }
   }
   


   public void run( AccountManagerFuture< Bundle > future )
   {
      addAccountHandle = null;
      
      if ( future.isDone() )
      {
         try
         {
            future.getResult();
            onAccountsUpdated( null );
         }
         catch ( OperationCanceledException e )
         {
            Toast.makeText( getContext(),
                            R.string.err_add_account_canceled,
                            Toast.LENGTH_SHORT );
         }
         catch ( AuthenticatorException e )
         {
            // According to the doc this can only happen
            // if there is no authenticator registered for the
            // account type. This should not happen.
            Toast.makeText( getContext(),
                            R.string.err_unexpected,
                            Toast.LENGTH_LONG );
         }
         catch ( IOException e )
         {
            // Will be notified in the AuthenticatorActivity
         }
      }
   }
   


   public void onAccountsUpdated( Account[] accounts )
   {
      final AccountManager accountManager = AccountManager.get( getContext() );
      
      if ( accountManager != null )
      {
         account = AccountUtils.getRtmAccount( accountManager );
         notifyChanged();
      }
   }
   


   @Override
   public void cleanUp()
   {
      final AccountManager accountManager = AccountManager.get( getContext() );
      
      if ( accountManager != null )
         accountManager.removeOnAccountsUpdatedListener( this );
      
      if ( addAccountHandle != null )
      {
         addAccountHandle.cancel( true );
         addAccountHandle = null;
      }
      
      onSyncFinished();
   }
   


   @Override
   protected void onBindView( View view )
   {
      final ImageView widget = (ImageView) view.findViewById( R.id.moloko_prefs_widget_sync );
      
      if ( account != null )
      {
         final RtmSettings settings = MolokoApp.getSettings().getRtmSettings();
         
         if ( settings == null )
         {
            setInfoText( R.string.moloko_prefs_rtm_sync_text_no_settings );
         }
         else
         {
            final String date = MolokoDateUtils.formatDate( settings.getSyncTimeStamp()
                                                                    .getTime(),
                                                            MolokoDateUtils.FORMAT_NUMERIC
                                                               | MolokoDateUtils.FORMAT_WITH_YEAR );
            
            setInfoText( getContext().getString( R.string.moloko_prefs_rtm_sync_text_in_sync,
                                                 date ) );
         }
         
         widget.setImageResource( R.drawable.ic_white_refresh );
      }
      else
      {
         setInfoText( R.string.g_no_account );
         widget.setImageResource( R.drawable.ic_white_add );
      }
      
      super.onBindView( view );
   }
   


   @Override
   protected void onClick()
   {
      if ( account != null )
      {
         if ( syncStatusHandle == null )
         {
            syncStatusHandle = ContentResolver.addStatusChangeListener( dev.drsoran.moloko.service.sync.Constants.SYNC_OBSERVER_TYPE_STATUS,
                                                                        this );
            
            if ( syncStatusHandle != null )
            {
               final Bundle bundle = new Bundle();
               bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );
               bundle.putBoolean( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                                  true );
               
               ContentResolver.requestSync( account, Rtm.AUTHORITY, bundle );
               
               showDialog();
            }
         }
      }
      else
      {
         if ( addAccountHandle == null )
         {
            final AccountManager accountManager = AccountManager.get( getContext() );
            
            if ( accountManager != null )
            {
               addAccountHandle = accountManager.addAccount( Constants.ACCOUNT_TYPE,
                                                             Constants.AUTH_TOKEN_TYPE,
                                                             null,
                                                             new Bundle(),
                                                             (Activity) getContext(),
                                                             this,
                                                             handler );
            }
         }
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
   


   public void onStatusChanged( int which )
   {
      if ( !ContentResolver.isSyncActive( account, Rtm.AUTHORITY ) )
      {
         onSyncFinished();
         
         handler.post( new Runnable()
         {
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
   


   public void onCancel( DialogInterface dialog )
   {
      onSyncFinished();
      dialog = null;
   }
   


   private void onSyncFinished()
   {
      if ( syncStatusHandle != null )
      {
         ContentResolver.removeStatusChangeListener( syncStatusHandle );
         syncStatusHandle = null;
      }
   }
   
}
