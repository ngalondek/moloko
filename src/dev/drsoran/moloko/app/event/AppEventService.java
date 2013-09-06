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

package dev.drsoran.moloko.app.event;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.IntentFilter;
import android.os.Handler;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.IHandlerTokenFactory;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.services.IAppEventService;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Reflection;
import dev.drsoran.moloko.util.Strings;


public class AppEventService implements IAppEventService
{
   private final DomainContext context;
   
   private final Handler handler;
   
   private final IHandlerToken handlerToken;
   
   private IEventDrain eventDrain;
   
   private ListenerList< IOnSettingsChangedListener > settingsChangedListeners;
   
   private ListenerList< ISyncStatusListener > syncStatusListeners;
   
   private ListenerList< IAccountUpdatedListener > accountUpdatedListeners;
   
   private SyncStatusReceiver syncStatusReceiver;
   
   private AccountsUpdatedReceiver accountsUpdatedReceiver;
   
   private SettingsListener settingsListener;
   
   
   
   public AppEventService( DomainContext context, Handler handler,
      ILog logService, IHandlerTokenFactory handlerTokenFactory )
   {
      this.context = context;
      this.handler = handler;
      this.handlerToken = handlerTokenFactory.acquireToken();
      
      createListenerLists( logService );
      createEventDrain();
      
      registerSyncStatusReceiver();
      registerAccountsUpdatedReceiver();
      registerSettingsListener();
   }
   
   
   
   public void shutdown()
   {
      unregisterSettingsListener();
      unregisterAccountsUpdatedReceiver();
      unregisterSyncStatusReceiver();
      
      deleteListenerLists();
      
      handlerToken.release();
   }
   
   
   
   @Override
   public void registerOnSettingsChangedListener( int which,
                                                  IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         settingsChangedListeners.registerListener( which, listener );
      }
   }
   
   
   
   @Override
   public void unregisterOnSettingsChangedListener( IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         settingsChangedListeners.unregisterListener( listener );
      }
   }
   
   
   
   @Override
   public void registerSyncStatusChangedListener( ISyncStatusListener listener )
   {
      if ( listener != null )
      {
         syncStatusListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   
   
   
   @Override
   public void unregisterSyncStatusChangedListener( ISyncStatusListener listener )
   {
      if ( listener != null )
      {
         syncStatusListeners.unregisterListener( listener );
      }
   }
   
   
   
   @Override
   public void registerAccountUpdatedListener( IAccountUpdatedListener listener )
   {
      if ( listener != null )
      {
         accountUpdatedListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   
   
   
   @Override
   public void unregisterAccountUpdatedListener( IAccountUpdatedListener listener )
   {
      if ( listener != null )
      {
         accountUpdatedListeners.unregisterListener( listener );
      }
   }
   
   
   
   private void createListenerLists( ILog logService )
   {
      try
      {
         settingsChangedListeners = new ListenerList< IOnSettingsChangedListener >( Reflection.findMethod( IOnSettingsChangedListener.class,
                                                                                                           "onSettingsChanged" ) );
         syncStatusListeners = new ListenerList< ISyncStatusListener >( Reflection.findMethod( ISyncStatusListener.class,
                                                                                               "onSyncStatusChanged" ) );
         accountUpdatedListeners = new ListenerList< IAccountUpdatedListener >( Reflection.findMethod( IAccountUpdatedListener.class,
                                                                                                       "onAccountUpdated" ) );
      }
      catch ( SecurityException e )
      {
         logService.e( getClass(), Strings.EMPTY_STRING, e );
         throw e;
      }
      catch ( NoSuchMethodException e )
      {
         logService.e( getClass(), Strings.EMPTY_STRING, e );
         throw new RuntimeException( e );
      }
   }
   
   
   
   private void deleteListenerLists()
   {
      if ( settingsChangedListeners != null )
      {
         settingsChangedListeners.clear();
         settingsChangedListeners = null;
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
   
   
   
   private void registerSyncStatusReceiver()
   {
      syncStatusReceiver = new SyncStatusReceiver( eventDrain );
      context.registerReceiver( syncStatusReceiver,
                                new IntentFilter( Intents.Action.SYNC_STATUS_UPDATE ) );
   }
   
   
   
   private void unregisterSyncStatusReceiver()
   {
      context.unregisterReceiver( syncStatusReceiver );
   }
   
   
   
   private void registerSettingsListener()
   {
      settingsListener = new SettingsListener( context, eventDrain, handler );
   }
   
   
   
   private void unregisterSettingsListener()
   {
      settingsListener.shutdown();
   }
   
   
   
   private void registerAccountsUpdatedReceiver()
   {
      accountsUpdatedReceiver = new AccountsUpdatedReceiver( eventDrain );
      context.registerReceiver( accountsUpdatedReceiver,
                                new IntentFilter( AccountManager.LOGIN_ACCOUNTS_CHANGED_ACTION ) );
   }
   
   
   
   private void unregisterAccountsUpdatedReceiver()
   {
      context.unregisterReceiver( accountsUpdatedReceiver );
   }
   
   
   
   private void createEventDrain()
   {
      eventDrain = new AbstractEventDrain( handlerToken )
      {
         @Override
         protected void handleSyncStatusChanged( int what )
         {
            syncStatusListeners.notifyListeners( what );
         }
         
         
         
         @Override
         protected void handleSettingChanged( int what )
         {
            settingsChangedListeners.notifyListeners( what );
         }
         
         
         
         @Override
         protected void handleAccountUpdated( int what, Account account )
         {
            accountUpdatedListeners.notifyListeners( what, account );
         }
      };
   }
}
