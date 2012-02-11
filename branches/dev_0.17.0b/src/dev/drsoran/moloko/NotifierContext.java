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

import java.lang.reflect.Method;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dev.drsoran.moloko.receivers.NetworkStatusReceiver;
import dev.drsoran.moloko.receivers.SyncStatusReceiver;
import dev.drsoran.moloko.receivers.TimeChangedReceiver;
import dev.drsoran.moloko.receivers.TimeTickReceiver;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.ListenerList.MessgageObject;
import dev.drsoran.moloko.util.Strings;


public class NotifierContext extends ContextWrapper
{
   private final static String TAG = "Moloko."
      + NotifierContext.class.getSimpleName();
   
   private ListenerList< IOnTimeChangedListener > timeChangedListeners;
   
   private ListenerList< IOnSettingsChangedListener > settingsChangedListeners;
   
   private ListenerList< IOnNetworkStatusChangedListener > networkStatusListeners;
   
   private ListenerList< ISyncStatusListener > syncStatusListeners;
   
   private NetworkStatusReceiver networkStatusReceiver;
   
   private TimeChangedReceiver timeChangedReceiver;
   
   private TimeTickReceiver timeTickReceiver;
   
   private SyncStatusReceiver syncStatusReceiver;
   
   private SettingsListener settingsListener;
   
   
   
   public NotifierContext( Context base )
   {
      super( base );
      
      createListenerLists();
      registerNetworkStatusReceiver();
      registerTimeChangedReceiver();
      registerTimeTickReceiver();
      registerSyncStatusReceiver();
      registerSettingsListener();
   }
   
   
   
   public void shutdown()
   {
      unregisterSettingsListener();
      unregisterSyncStatusReceiver();
      unregisterTimeChangedReceiver();
      unregisterTimeTickReceiver();
      unregisterNetworkStatusReceiver();
      deleteListenerLists();
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
   
   
   
   private void createListenerLists()
   {
      try
      {
         timeChangedListeners = new ListenerList< IOnTimeChangedListener >( findMethod( IOnTimeChangedListener.class,
                                                                                        "onTimeChanged" ) );
         settingsChangedListeners = new ListenerList< IOnSettingsChangedListener >( findMethod( IOnSettingsChangedListener.class,
                                                                                                "onSettingsChanged" ) );
         networkStatusListeners = new ListenerList< IOnNetworkStatusChangedListener >( findMethod( IOnNetworkStatusChangedListener.class,
                                                                                                   "onNetworkStatusChanged" ) );
         syncStatusListeners = new ListenerList< ISyncStatusListener >( findMethod( ISyncStatusListener.class,
                                                                                    "onSyncStatusChanged" ) );
      }
      catch ( SecurityException e )
      {
         Log.e( TAG, Strings.EMPTY_STRING, e );
         throw e;
      }
      catch ( NoSuchMethodException e )
      {
         Log.e( TAG, Strings.EMPTY_STRING, e );
         throw new IllegalStateException( e );
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
   
   
   
   private final static < T > Method findMethod( Class< T > clazz, String name ) throws NoSuchMethodException
   {
      Method method = null;
      
      final Method[] methods = clazz.getMethods();
      
      for ( int i = 0; i < methods.length && method == null; i++ )
      {
         if ( methods[ i ].getName().equals( name ) )
            method = methods[ i ];
      }
      
      if ( method == null )
         throw new NoSuchMethodException( "The class " + clazz.getName()
            + " does not has a method " + name );
      
      return method;
   }
   
   private final Handler handler = new Handler()
   {
      @Override
      public void handleMessage( Message msg )
      {
         boolean handled = false;
         if ( msg.obj instanceof ListenerList.MessgageObject< ? > )
         {
            handled = true;
            
            final ListenerList.MessgageObject< ? > msgObj = (MessgageObject< ? >) msg.obj;
            
            if ( msgObj.type.getName()
                            .equals( IOnTimeChangedListener.class.getName() ) )
            {
               timeChangedListeners.notifyListeners( msg.what );
            }
            else if ( msgObj.type.getName()
                                 .equals( IOnSettingsChangedListener.class.getName() ) )
            {
               settingsChangedListeners.notifyListeners( msg.what, msgObj.value );
            }
            else if ( msgObj.type.getName()
                                 .equals( IOnNetworkStatusChangedListener.class.getName() ) )
            {
               networkStatusListeners.notifyListeners( msg.what, msgObj.value );
            }
            else if ( msgObj.type.getName()
                                 .equals( ISyncStatusListener.class.getName() ) )
            {
               syncStatusListeners.notifyListeners( msg.what );
            }
            else
            {
               handled = false;
            }
         }
         
         if ( !handled )
         {
            super.handleMessage( msg );
         }
      }
   };
}
