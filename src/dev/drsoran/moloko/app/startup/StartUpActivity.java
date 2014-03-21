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

package dev.drsoran.moloko.app.startup;

import java.util.NoSuchElementException;

import android.accounts.Account;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.Intents.HomeAction;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.home.HomeActivity;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;


public class StartUpActivity extends MolokoActivity
{
   private final static String STATE_INDEX_KEY = "state_index";
   
   private final static int STATE_CHECK_ACCOUNT = 0;
   
   private final static int STATE_DETERMINE_STARTUP_VIEW = 1;
   
   private final static int STATE_CHECK_SYNC_AT_STARTUP = 2;
   
   private final static int STATE_COMPLETED = 3;
   
   private final static int[] STATE_SEQUENCE =
   { STATE_CHECK_ACCOUNT, STATE_DETERMINE_STARTUP_VIEW,
    STATE_CHECK_SYNC_AT_STARTUP, STATE_COMPLETED };
   
   private int stateIndex = 0;
   
   private ISettingsService settings;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      settings = getAppContext().getSettings();
      
      setContentView( R.layout.startup_activity );
      setTitle( R.string.app_startup );
      
      if ( savedInstanceState != null )
      {
         stateIndex = savedInstanceState.getInt( STATE_INDEX_KEY, 0 );
      }
      
      reEvaluateCurrentState();
   }
   
   
   
   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putInt( STATE_INDEX_KEY, stateIndex );
   }
   
   
   
   @Override
   protected void onRestoreInstanceState( Bundle savedInstanceState )
   {
      super.onRestoreInstanceState( savedInstanceState );
      
      stateIndex = savedInstanceState.getInt( STATE_INDEX_KEY, 0 );
      
      reEvaluateCurrentState();
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      if ( dialogId == R.id.dlg_no_account && which == Dialog.BUTTON_NEGATIVE )
      {
         switchToNextState();
      }
      else if ( dialogId == R.id.dlg_startup_default_list_not_exists
         && which == Dialog.BUTTON_NEUTRAL )
      {
         settings.setStartupView( SettingConstants.STARTUP_VIEW_DEFAULT );
         settings.setDefaultListId( SettingConstants.NO_DEFAULT_LIST_ID );
         
         switchToNextState();
      }
      else
      {
         super.onAlertDialogFragmentClick( dialogId, tag, which );
      }
   }
   
   
   
   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      if ( requestCode == StartActivityRequestCode.ADD_ACCOUNT )
         switchToNextState();
      else
         super.onActivityResult( requestCode, resultCode, data );
   }
   
   
   
   private void checkAccount()
   {
      boolean switchToNextState = true;
      final Account account = getAppContext().getAccountService()
                                             .getRtmAccount();
      
      if ( account == null )
      {
         showNoAccountDialog();
         switchToNextState = false;
      }
      
      if ( switchToNextState )
      {
         switchToNextState();
      }
   }
   
   
   
   private void determineStartupView()
   {
      final int startUpView = settings.getStartupView();
      
      if ( startUpView == SettingConstants.STARTUP_VIEW_DEFAULT_LIST )
      {
         // Check that the set default list exists and can be shown
         final long defaultListId = settings.getDefaultListId();
         
         if ( !existsDefaultList( defaultListId ) )
         {
            new AlertDialogFragment.Builder( R.id.dlg_startup_default_list_not_exists ).setTitle( getString( R.string.dlg_missing_def_list_title ) )
                                                                                       .setIcon( R.drawable.ic_prefs_info )
                                                                                       .setMessage( getString( R.string.dlg_missing_def_list_text ) )
                                                                                       .setNeutralButton( R.string.btn_continue )
                                                                                       .show( this );
         }
         else
         {
            switchToNextState();
         }
      }
      else
      {
         switchToNextState();
      }
   }
   
   
   
   private void checkAndPerformSyncAtStartUp()
   {
      if ( settings.isSyncAtStartup() && !settings.isManualSyncOnly() )
      {
         final Account account = getAppContext().getAccountService()
                                                .getRtmAccount();
         if ( account != null )
         {
            getAppContext().getSyncService().requestManualSync( account );
         }
      }
      
      switchToNextState();
   }
   
   
   
   private void onStartUpCompleted()
   {
      final int startUpView = settings.getStartupView();
      
      switch ( startUpView )
      {
         case SettingConstants.STARTUP_VIEW_DEFAULT_LIST:
            startActivityWithHomeAction( Intents.createOpenListIntentById( settings.getDefaultListId() ),
                                         HomeAction.HOME );
            break;
         
         case SettingConstants.STARTUP_VIEW_LISTS:
            startActivityWithHomeAction( Intents.createOpenListOverviewsIntent(),
                                         HomeAction.HOME );
            break;
         
         case SettingConstants.STARTUP_VIEW_HOME:
            startActivity( new Intent( this, HomeActivity.class ) );
            break;
         
         default :
            throw new IllegalStateException( "Unknown state: " + startUpView );
      }
      
      finish();
   }
   
   
   
   private void switchToNextState()
   {
      if ( stateIndex + 1 < STATE_SEQUENCE.length )
      {
         ++stateIndex;
         reEvaluateCurrentState();
      }
      else
      {
         throw new IllegalStateException( "No following state. The StartUpActivity should have been exited now." );
      }
   }
   
   
   
   private void reEvaluateCurrentState()
   {
      switch ( stateIndex )
      {
         case STATE_CHECK_ACCOUNT:
            checkAccount();
            break;
         
         case STATE_DETERMINE_STARTUP_VIEW:
            determineStartupView();
            break;
         
         case STATE_CHECK_SYNC_AT_STARTUP:
            checkAndPerformSyncAtStartUp();
            break;
         
         case STATE_COMPLETED:
            onStartUpCompleted();
            break;
         
         default :
            throw new IllegalStateException( "Unknown state: " + stateIndex );
      }
   }
   
   
   
   private boolean existsDefaultList( long id )
   {
      try
      {
         getAppContext().getContentRepository().getTasksList( id );
         return true;
      }
      catch ( NoSuchElementException e )
      {
         return false;
      }
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[] {};
   }
}
