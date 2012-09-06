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

package dev.drsoran.moloko;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import dev.drsoran.moloko.receivers.AccountsUpdatedReceiver;
import dev.drsoran.moloko.receivers.NetworkStatusReceiver;
import dev.drsoran.moloko.receivers.SyncStatusReceiver;
import dev.drsoran.moloko.receivers.TimeChangedReceiver;
import dev.drsoran.moloko.receivers.TimeTickReceiver;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Reflection;
import dev.drsoran.moloko.util.Strings;


public class NotifierContext extends ContextWrapper
{
   private final static String TAG = "Moloko."
      + NotifierContext.class.getSimpleName();
   
   private ListenerList< IOnTimeChangedListener > timeChangedListeners;
   
   private ListenerList< IOnSettingsChangedListener > settingsChangedListeners;
   
   private ListenerList< IOnNetworkStatusChangedListener > networkStatusListeners;
   
   private ListenerList< ISyncStatusListener > syncStatusListeners;
   
   private ListenerList< IAccountUpdatedListener > accountUpdatedListeners;
   
   private NetworkStatusReceiver networkStatusReceiver;
   
   private TimeChangedReceiver timeChangedReceiver;
   
   private TimeTickReceiver timeTickReceiver;
   
   private SyncStatusReceiver syncStatusReceiver;
   
   private AccountsUpdatedReceiver accountsUpdatedReceiver;
   
   private SettingsListener settingsListener;
   
   
   
   public NotifierContext( Context base )
   {
      super( base );
      
      createListenerLists();
      registerNetworkStatusReceiver();
      registerTimeChangedReceiver();
      registerTimeTickReceiver();
      registerSyncStatusReceiver();
      registerAccountsUpdatedReceiver();
      registerSettingsListener();
   }
   
   
   
   public void shutdown()
   {
      unregisterSettingsListener();
      unregisterAccountsUpdatedReceiver();
      unregisterSyncStatusReceiver();
      unregisterTimeChangedReceiver();
      unregisterTimeTickReceiver();
      unregisterNetworkStatusReceiver();
      
      deleteListenerLists();
      
      handler.release();
      handler = null;
   }
   
   
   
   public void registerOnSettingsChangedListener( int which,
                                                  IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         settingsChangedListeners.registerListener( which, listener );
      }
   }
   
   
   
   public void unregisterOnSettingsChangedListener( IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         settingsChangedListeners.removeListener( listener );
      }
   }
   
   
   
   public void registerOnTimeChangedListener( int which,
                                              IOnTimeChangedListener listener )
   {
      if ( listener != null )
      {
         timeChangedListeners.registerListener( which, listener );
      }
   }
   
   
   
   public void unregisterOnTimeChangedListener( IOnTimeChangedListener listener )
   {
      if ( listener != null )
      {
         timeChangedListeners.removeListener( listener );
      }
   }
   
   
   
   public void registerOnNetworkStatusChangedListener( IOnNetworkStatusChangedListener listener )
   {
      if ( listener != null )
      {
         networkStatusListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   
   
   
   public void unregisterOnNetworkStatusChangedListener( IOnNetworkStatusChangedListener listener )
   {
      if ( listener != null )
      {
         networkStatusListeners.removeListener( listener );
      }
   }
   
   
   
   public void registerSyncStatusChangedListener( ISyncStatusListener listener )
   {
      if ( listener != null )
      {
         syncStatusListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   
   
   
   public void unregisterSyncStatusChangedListener( ISyncStatusListener listener )
   {
      if ( listener != null )
      {
         syncStatusListeners.removeListener( listener );
      }
   }
   
   
   
   public void registerAccountUpdatedListener( IAccountUpdatedListener listener )
   {
      if ( listener != null )
      {
         accountUpdatedListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   
   
   
   public void unregisterAccountUpdatedListener( IAccountUpdatedListener listener )
   {
      if ( listener != null )
      {
         accountUpdatedListeners.removeListener( listener );
      }
   }
   
   
   
   private void createListenerLists()
   {
      try
      {
         timeChangedListeners = new ListenerList< IOnTimeChangedListener >( Reflection.findMethod( IOnTimeChangedListener.class,
                                                                                                   "onTimeChanged" ) );
         settingsChangedListeners = new ListenerList< IOnSettingsChangedListener >( Reflection.findMethod( IOnSettingsChangedListener.class,
                                                                                                           "onSettingsChanged" ) );
         networkStatusListeners = new ListenerList< IOnNetworkStatusChangedListener >( Reflection.findMethod( IOnNetworkStatusChangedListener.class,
                                                                                                              "onNetworkStatusChanged" ) );
         syncStatusListeners = new ListenerList< ISyncStatusListener >( Reflection.findMethod( ISyncStatusListener.class,
                                                                                               "onSyncStatusChanged" ) );
         accountUpdatedListeners = new ListenerList< IAccountUpdatedListener >( Reflection.findMethod( IAccountUpdatedListener.class,
                                                                                                       "onAccountUpdated" ) );
      }
      catch ( SecurityException e )
      {
         Log.e( TAG, Strings.EMPTY_STRING, e );
         throw e;
      }
   }
   
   
   
   private void deleteListenerLists()
   {
      if ( timeChangedListeners != null )
      {
         timeChangedListeners.clear();
         timeChangedListeners = null;
      }
      
      if ( settingsChangedListeners != null )
      {
         settingsChangedListeners.clear();
         settingsChangedListeners = null;
      }
      
      if ( networkStatusListeners != null )
      {
         networkStatusListeners.clear();
         networkStatusListeners = null;
      }
      
      if ( syncStatusListeners != null )
      {
         syncStatusListeners.clear();
         syncStatusListeners = null;
      }
      
      if ( accountUpdatedListeners != null )
      {
         accountUpdatedListeners.clear();
         accountUpdatedListeners = null;
      }
   }
   
   
   
   private void registerNetworkStatusReceiver()
   {
      networkStatusReceiver = new NetworkStatusReceiver( handler );
      registerReceiver( networkStatusReceiver,
                        new IntentFilter( "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED" ) );
      registerReceiver( networkStatusReceiver,
                        new IntentFilter( "android.net.conn.CONNECTIVITY_CHANGE" ) );
   }
   
   
   
   private void unregisterNetworkStatusReceiver()
   {
      unregisterReceiver( networkStatusReceiver );
   }
   
   
   
   private void registerTimeChangedReceiver()
   {
      timeChangedReceiver = new TimeChangedReceiver( handler );
      registerReceiver( timeChangedReceiver,
                        new IntentFilter( "android.intent.action.TIME_SET" ) );
      registerReceiver( timeChangedReceiver,
                        new IntentFilter( Intent.ACTION_DATE_CHANGED ) );
   }
   
   
   
   private void unregisterTimeChangedReceiver()
   {
      unregisterReceiver( timeChangedReceiver );
   }
   
   
   
   private void registerTimeTickReceiver()
   {
      timeTickReceiver = new TimeTickReceiver( handler );
      registerReceiver( timeTickReceiver,
                        new IntentFilter( Intent.ACTION_TIME_TICK ) );
   }
   
   
   
   private void unregisterTimeTickReceiver()
   {
      unregisterReceiver( timeTickReceiver );
   }
   
   
   
   private void registerSyncStatusReceiver()
   {
      syncStatusReceiver = new SyncStatusReceiver( handler );
      registerReceiver( syncStatusReceiver,
                        new IntentFilter( Intents.Action.SYNC_STATUS_UPDATE ) );
   }
   
   
   
   private void unregisterSyncStatusReceiver()
   {
      unregisterReceiver( syncStatusReceiver );
   }
   
   
   
   private void registerSettingsListener()
   {
      settingsListener = new SettingsListener( this, handler );
   }
   
   
   
   private void unregisterSettingsListener()
   {
      settingsListener.shutdown();
   }
   
   
   
   private void registerAccountsUpdatedReceiver()
   {
      accountsUpdatedReceiver = new AccountsUpdatedReceiver( handler );
      registerReceiver( accountsUpdatedReceiver,
                        new IntentFilter( AccountManager.LOGIN_ACCOUNTS_CHANGED_ACTION ) );
   }
   
   
   
   private void unregisterAccountsUpdatedReceiver()
   {
      unregisterReceiver( accountsUpdatedReceiver );
   }
   
   private NotifierContextHandler handler = new NotifierContextHandler()
   {
      @Override
      protected void handleTimeChanged( int what )
      {
         timeChangedListeners.notifyListeners( what );
      }
      
      
      
      @Override
      protected void handleNetworkStatusChanged( int what, boolean connected )
      {
         networkStatusListeners.notifyListeners( what,
                                                 Boolean.valueOf( connected ) );
      }
      
      
      
      @Override
      protected void handleSyncStatusChanged( int what )
      {
         syncStatusListeners.notifyListeners( what );
      }
      
      
      
      @Override
      protected void handleSettingChanged( int what, Object oldValues )
      {
         settingsChangedListeners.notifyListeners( what, oldValues );
      }
      
      
      
      @Override
      protected void handleAccountUpdated( int what, Account account )
      {
         accountUpdatedListeners.notifyListeners( what, account );
      }
   };
}
