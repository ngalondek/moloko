/*
 * Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.activities;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;


public class StartUpActivity extends MolokoFragmentActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + StartUpActivity.class.getSimpleName();
   
   private final static String STATE_INDEX_KEY = "state_index";
   
   private final static int MSG_STATE_CHANGED = 0;
   
   private final static int STATE_CHECK_ACCOUNT = 0;
   
   private final static int STATE_DETERMINE_STARTUP_VIEW = 1;
   
   private final static int STATE_COMPLETED = 2;
   
   private final static int[] STATE_SEQUENCE =
   { STATE_CHECK_ACCOUNT, STATE_DETERMINE_STARTUP_VIEW, STATE_COMPLETED };
   
   private int stateIndex = 0;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.startup_activity );
      setTitle( R.string.app_startup );
      
      if ( savedInstanceState != null )
         stateIndex = savedInstanceState.getInt( STATE_INDEX_KEY, 0 );
      
      reEvaluateCurrentState();
   }
   


   @Override
   protected void onDestroy()
   {
      handler.removeMessages( MSG_STATE_CHANGED );
      
      super.onDestroy();
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
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      if ( requestCode == 0 )
         switchToNextState();
      else
         super.onActivityResult( requestCode, resultCode, data );
   }
   


   private void checkAccount()
   {
      boolean switchToNextState = true;
      final Account account = AccountUtils.getRtmAccount( this );
      
      if ( account == null )
      {
         UIUtils.showNoAccountDialog( this, new Runnable()
         {
            @Override
            public void run()
            {
               switchToNextState();
            }
         } );
         
         switchToNextState = false;
      }
      
      if ( switchToNextState )
         switchToNextState();
   }
   


   private void determineStartupView()
   {
      final Settings settings = MolokoApp.getSettings();
      
      if ( settings != null )
      {
         final int startUpView = settings.getStartupView();
         
         if ( startUpView == Settings.STARTUP_VIEW_DEFAULT_LIST )
         {
            // Check that the set default list exists and can be shown
            final String defaultListId = settings.getDefaultListId();
            
            try
            {
               if ( !existsList( defaultListId ) )
               {
                  new AlertDialogFragment.Builder().setTitle( getString( R.string.dlg_missing_def_list_title ) )
                                                   .setIcon( R.drawable.ic_prefs_info )
                                                   .setMessage( getString( R.string.dlg_missing_def_list_text ) )
                                                   .setNeutralButton( R.string.btn_continue,
                                                                      new OnClickListener()
                                                                      {
                                                                         @Override
                                                                         public void onClick( DialogInterface dialog,
                                                                                              int which )
                                                                         {
                                                                            final Settings settings = MolokoApp.getSettings();
                                                                            
                                                                            settings.setStartupView( Settings.STARTUP_VIEW_DEFAULT );
                                                                            settings.setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
                                                                            
                                                                            switchToNextState();
                                                                         }
                                                                      } )
                                                   .show( this );
               }
            }
            catch ( RemoteException e )
            {
               // We simply ignore the exception and start with default view.
               // Perhaps next time it works again.
               settings.setStartupView( Settings.STARTUP_VIEW_DEFAULT );
            }
         }
         
         switchToNextState();
      }
      else
      {
         throw new IllegalStateException( "Moloko settings instace is null." );
      }
   }
   


   private void onStartUpCompleted()
   {
      final int startUpView = MolokoApp.getSettings().getStartupView();
      
      switch ( startUpView )
      {
         case Settings.STARTUP_VIEW_DEFAULT_LIST:
            startActivity( Intents.createOpenListIntentById( this,
                                                             MolokoApp.getSettings()
                                                                      .getDefaultListId(),
                                                             null ) );
            break;
         
         case Settings.STARTUP_VIEW_LISTS:
            startActivity( new Intent( Intent.ACTION_VIEW,
                                       ListOverviews.CONTENT_URI ) );
            break;
         
         case Settings.STARTUP_VIEW_HOME:
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
      handler.sendEmptyMessage( MSG_STATE_CHANGED );
   }
   


   private boolean existsList( String id ) throws RemoteException
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Lists.CONTENT_URI );
      
      return client != null && Queries.exists( client, Lists.CONTENT_URI, id );
   }
   


   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
   
   private final Handler handler = new Handler()
   {
      @Override
      public void handleMessage( Message msg )
      {
         switch ( msg.what )
         {
            case MSG_STATE_CHANGED:
               switch ( stateIndex )
               {
                  case STATE_CHECK_ACCOUNT:
                     checkAccount();
                     break;
                  
                  case STATE_DETERMINE_STARTUP_VIEW:
                     determineStartupView();
                     break;
                  
                  case STATE_COMPLETED:
                     onStartUpCompleted();
                     break;
                  
                  default :
                     throw new IllegalStateException( "Unknown state: "
                        + stateIndex );
               }
               break;
            
            default :
               super.handleMessage( msg );
         }
      }
   };
}
