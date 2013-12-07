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

package dev.drsoran.moloko.event;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import dev.drsoran.Strings;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.IHandlerTokenFactory;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Reflection;


public class MolokoSystemEventService implements ISystemEventService
{
   private final Context context;
   
   private final IHandlerToken handlerToken;
   
   private IEventDrain eventDrain;
   
   private ListenerList< IOnTimeChangedListener > timeChangedListeners;
   
   private ListenerList< IOnNetworkStatusChangedListener > networkStatusListeners;
   
   private NetworkStatusReceiver networkStatusReceiver;
   
   private TimeChangedReceiver timeChangedReceiver;
   
   private TimeTickReceiver timeTickReceiver;
   
   
   
   public MolokoSystemEventService( Context context, ILog logService,
      IHandlerTokenFactory handlerTokenFactory )
   {
      this.context = context;
      this.handlerToken = handlerTokenFactory.acquireToken();
      
      createListenerLists( logService );
      createEventDrain();
      
      registerNetworkStatusReceiver();
      registerTimeChangedReceiver();
      registerTimeTickReceiver();
   }
   
   
   
   public void shutdown()
   {
      unregisterTimeChangedReceiver();
      unregisterTimeTickReceiver();
      unregisterNetworkStatusReceiver();
      
      deleteListenerLists();
      
      handlerToken.release();
   }
   
   
   
   @Override
   public void registerOnTimeChangedListener( int which,
                                              IOnTimeChangedListener listener )
   {
      if ( listener != null )
      {
         timeChangedListeners.registerListener( which, listener );
      }
   }
   
   
   
   @Override
   public void unregisterOnTimeChangedListener( IOnTimeChangedListener listener )
   {
      if ( listener != null )
      {
         timeChangedListeners.unregisterListener( listener );
      }
   }
   
   
   
   @Override
   public void registerOnNetworkStatusChangedListener( IOnNetworkStatusChangedListener listener )
   {
      if ( listener != null )
      {
         networkStatusListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   
   
   
   @Override
   public void unregisterOnNetworkStatusChangedListener( IOnNetworkStatusChangedListener listener )
   {
      if ( listener != null )
      {
         networkStatusListeners.unregisterListener( listener );
      }
   }
   
   
   
   private void createListenerLists( ILog logService )
   {
      try
      {
         timeChangedListeners = new ListenerList< IOnTimeChangedListener >( Reflection.findMethod( IOnTimeChangedListener.class,
                                                                                                   "onTimeChanged" ) );
         networkStatusListeners = new ListenerList< IOnNetworkStatusChangedListener >( Reflection.findMethod( IOnNetworkStatusChangedListener.class,
                                                                                                              "onNetworkStatusChanged" ) );
      }
      catch ( SecurityException e )
      {
         logService.e( getClass(), Strings.EMPTY_STRING, e );
         throw e;
      }
      catch ( NoSuchMethodException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   private void deleteListenerLists()
   {
      if ( timeChangedListeners != null )
      {
         timeChangedListeners.clear();
         timeChangedListeners = null;
      }
      
      if ( networkStatusListeners != null )
      {
         networkStatusListeners.clear();
         networkStatusListeners = null;
      }
   }
   
   
   
   private void registerNetworkStatusReceiver()
   {
      networkStatusReceiver = new NetworkStatusReceiver( eventDrain );
      context.registerReceiver( networkStatusReceiver,
                                new IntentFilter( "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED" ) );
      context.registerReceiver( networkStatusReceiver,
                                new IntentFilter( "android.net.conn.CONNECTIVITY_CHANGE" ) );
   }
   
   
   
   private void unregisterNetworkStatusReceiver()
   {
      context.unregisterReceiver( networkStatusReceiver );
   }
   
   
   
   private void registerTimeChangedReceiver()
   {
      timeChangedReceiver = new TimeChangedReceiver( eventDrain );
      context.registerReceiver( timeChangedReceiver,
                                new IntentFilter( "android.intent.action.TIME_SET" ) );
      context.registerReceiver( timeChangedReceiver,
                                new IntentFilter( Intent.ACTION_DATE_CHANGED ) );
   }
   
   
   
   private void unregisterTimeChangedReceiver()
   {
      context.unregisterReceiver( timeChangedReceiver );
   }
   
   
   
   private void registerTimeTickReceiver()
   {
      timeTickReceiver = new TimeTickReceiver( eventDrain );
      context.registerReceiver( timeTickReceiver,
                                new IntentFilter( Intent.ACTION_TIME_TICK ) );
   }
   
   
   
   private void unregisterTimeTickReceiver()
   {
      context.unregisterReceiver( timeTickReceiver );
   }
   
   
   
   private void createEventDrain()
   {
      eventDrain = new AbstractEventDrain( handlerToken )
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
      };
   }
}
