package dev.drsoran.moloko.prefs;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.util.DateUtils;
import dev.drsoran.provider.Rtm;
import dev.drsoran.rtm.RtmSettings;


public class RtmSyncStatePreference extends InfoTextPreference implements
         SyncStatusObserver, OnCancelListener
{
   private Object syncStatusHandle;
   
   private ProgressDialog dialog;
   
   private Account account;
   
   private final Handler handler;
   
   

   public RtmSyncStatePreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      handler = new Handler();
      
      final AccountManager accountManager = AccountManager.get( getContext() );
      
      if ( accountManager != null )
      {
         final Account[] accounts = accountManager.getAccountsByType( Constants.ACCOUNT_TYPE );
         
         // TODO: We simple take the first one. Think about showing a choose dialog.
         if ( accounts != null && accounts.length > 0 )
            account = accounts[ 0 ];
      }
      
      if ( account != null )
      {
         final RtmSettings settings = MolokoApp.getSettings().getRtmSettings();
         if ( settings == null )
         {
            setInfoText( R.string.moloko_prefs_rtm_sync_text_no_settings );
         }
         else
         {
            final String date = DateUtils.formatDateTime( getContext(),
                                                          settings.getSyncTimeStamp()
                                                                  .toMillis( false ),
                                                          DateUtils.FORMAT_SHOW_DATE
                                                             | DateUtils.FORMAT_SHOW_YEAR
                                                             | DateUtils.FORMAT_NUMERIC_DATE );
            
            setInfoText( getContext().getString( R.string.moloko_prefs_rtm_sync_text_in_sync,
                                                 date ) );
         }
      }
      else
      {
         setInfoText( R.string.g_no_account );
      }
   }
   


   @Override
   protected void onBindView( View view )
   {
      if ( account != null )
      {
         final RtmSettings settings = MolokoApp.getSettings().getRtmSettings();
         
         if ( settings == null )
         {
            setInfoText( R.string.moloko_prefs_rtm_sync_text_no_settings );
         }
         else
         {
            final String date = DateUtils.formatDateTime( getContext(),
                                                          settings.getSyncTimeStamp()
                                                                  .toMillis( false ),
                                                          DateUtils.FORMAT_SHOW_DATE
                                                             | DateUtils.FORMAT_SHOW_YEAR
                                                             | DateUtils.FORMAT_NUMERIC_DATE );
            
            setInfoText( getContext().getString( R.string.moloko_prefs_rtm_sync_text_in_sync,
                                                 date ) );
         }
      }
      else
      {
         setInfoText( R.string.g_no_account );
      }
      
      view.setEnabled( account != null );
      
      super.onBindView( view );
   }
   


   @Override
   protected void onClick()
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
      if ( !ContentResolver.isSyncPending( account, Rtm.AUTHORITY )
         && !ContentResolver.isSyncActive( account, Rtm.AUTHORITY ) )
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
