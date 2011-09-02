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

package dev.drsoran.moloko.activities;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.fragments.dialogs.NoAccountDialogFragment;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;


public class StartUpActivity extends MolokoFragmentActivity implements
         AccountManagerCallback< Bundle >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + StartUpActivity.class.getSimpleName();
   
   public final static String ONLY_CHECK_ACCOUNT = "only_check_account";
   
   private final static String STATE_INDEX_KEY = "state_index";
   
   private final static int MSG_STATE_CHANGED = 0;
   
   private final static int STATE_CHECK_ACCOUNT = 0;
   
   private final static int STATE_DETERMINE_STARTUP_VIEW = 1;
   
   private final static int STATE_COMPLETED = 2;
   
   private final static int[] STATE_SEQUENCE =
   { STATE_CHECK_ACCOUNT, STATE_DETERMINE_STARTUP_VIEW, STATE_COMPLETED };
   
   private ViewGroup widgetContainer;
   
   private ViewGroup buttonBar;
   
   private AccountManagerFuture< Bundle > addAccountHandle;
   
   private int stateIndex = 0;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.startup_activity );
      
      widgetContainer = (ViewGroup) findViewById( android.R.id.widget_frame );
      buttonBar = (ViewGroup) findViewById( R.id.btn_bar );
      
      if ( savedInstanceState != null )
         stateIndex = savedInstanceState.getInt( STATE_INDEX_KEY, 0 );
      
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
   


   private boolean checkAccount()
   {
      final Account account = AccountUtils.getRtmAccount( this );
      
      if ( account == null )
      {
         final DialogFragment dialogFragment = NoAccountDialogFragment.newInstance( Bundle.EMPTY );
         dialogFragment.show( getSupportFragmentManager(),
                              NoAccountDialogFragment.class.getSimpleName() );
         
         // widgetContainer.removeAllViews();
         //
         // // LayoutInflater.from( this )
         // // .inflate( R.layout.startup_activity_no_account_widget,
         // // widgetContainer,
         // // true );
         //
         // final Button btnCenter = getButton( android.R.id.button1 );
         // btnCenter.setVisibility( View.VISIBLE );
         // btnCenter.setText( R.string.btn_new_account );
         // btnCenter.setOnClickListener( new OnClickListener()
         // {
         // @Override
         // public void onClick( View v )
         // {
         // onAddNewAccount();
         // }
         // } );
         // setButtonIcon( btnCenter, R.drawable.ic_button_add );
         //
         // buttonBar.setVisibility( View.VISIBLE );
         
      }
      
      return account != null;
   }
   


   private void createAccountOrSwitchToNext()
   {
      if ( checkAccount() )
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
                  widgetContainer.removeAllViews();
                  
                  LayoutInflater.from( this )
                                .inflate( R.layout.startup_activity_no_def_list_widget,
                                          widgetContainer,
                                          true );
                  
                  final Button btnCenter = getButton( android.R.id.button1 );
                  btnCenter.setVisibility( View.VISIBLE );
                  btnCenter.setText( R.string.btn_continue );
                  btnCenter.setOnClickListener( new OnClickListener()
                  {
                     @Override
                     public void onClick( View v )
                     {
                        settings.setStartupView( Settings.STARTUP_VIEW_DEFAULT );
                        settings.setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
                        switchToNextState();
                     }
                  } );
                  
                  buttonBar.setVisibility( View.VISIBLE );
               }
               else
               {
                  // STARTUP_VIEW_DEFAULT_LIST
                  switchToNextState();
               }
            }
            catch ( RemoteException e )
            {
               // We simply ignore the exception and start with lists view.
               // Perhaps next time it works again.
               settings.setStartupView( Settings.STARTUP_VIEW_DEFAULT );
               switchToNextState();
            }
         }
         else
         {
            // STARTUP_VIEW_LISTS
            switchToNextState();
         }
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
   


   private void onAddNewAccount()
   {
      if ( addAccountHandle != null )
      {
         addAccountHandle.cancel( true );
         addAccountHandle = null;
      }
      
      final AccountManager accountManager = AccountManager.get( this );
      
      addAccountHandle = accountManager.addAccount( Constants.ACCOUNT_TYPE,
                                                    Constants.AUTH_TOKEN_TYPE,
                                                    null,
                                                    new Bundle(),
                                                    this,
                                                    this,
                                                    handler );
   }
   


   @Override
   public void run( AccountManagerFuture< Bundle > result )
   {
      addAccountHandle = null;
      
      if ( result.isDone() )
      {
         try
         {
            result.getResult();
            switchToNextState();
         }
         catch ( OperationCanceledException e )
         {
            Toast.makeText( this,
                            R.string.err_add_account_canceled,
                            Toast.LENGTH_SHORT );
         }
         catch ( AuthenticatorException e )
         {
            // According to the doc this can only happen
            // if there is no authenticator registered for the
            // account type. This should not happen.
            Toast.makeText( this, R.string.err_unexpected, Toast.LENGTH_LONG )
                 .show();
         }
         catch ( IOException e )
         {
            // Will be notified in the AuthenticatorActivity
         }
      }
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
      if ( getIntent().hasExtra( ONLY_CHECK_ACCOUNT ) )
      {
         if ( checkAccount() )
            finish();
      }
      else
         handler.sendEmptyMessage( MSG_STATE_CHANGED );
   }
   


   private Button getButton( int id )
   {
      return (Button) buttonBar.findViewById( id );
   }
   


   private void setButtonIcon( Button button, int id )
   {
      final BitmapDrawable icon = new BitmapDrawable( getResources().openRawResource( id ) );
      
      icon.setBounds( 0, 0, 30, 30 );
      
      button.setCompoundDrawables( icon, null, null, null );
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
                     createAccountOrSwitchToNext();
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
