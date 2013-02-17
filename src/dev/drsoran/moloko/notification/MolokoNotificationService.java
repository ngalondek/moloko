/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.notification;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.NotifierContext;
import dev.drsoran.moloko.TokenBasedHandler;
import dev.drsoran.moloko.util.Intents.Action;


public class MolokoNotificationService extends Service
{
   private final static TokenBasedHandler handler = new TokenBasedHandler();
   
   private NotifierContext notifierContext;
   
   private MolokoNotificationManager notificationManager;
   
   
   
   @Override
   public void onCreate()
   {
      super.onCreate();
      
      createNotifierContext();
      createNotificationManager();
   }
   
   
   
   @Override
   public int onStartCommand( Intent intent, int flags, int startId )
   {
      try
      {
         // The service may be killed by the system meanwhile. We can come
         // here with a notification clicked intent here without a start
         // after the service is resumed by the system.
         ensureServiceIsStarted();
         
         if ( intent != null )
         {
            final String action = intent.getAction();
            
            if ( Action.NOTIFICATION_SERVICE_START.equals( action ) )
            {
               notificationManager.recreateNotifications();
            }
            else if ( Action.NOTIFICATION_SERVICE_NOTIFICATION_CLICKED.equals( action ) )
            {
               handleNotificationClicked( intent );
            }
            else if ( Action.NOTIFICATION_SERVICE_NOTIFICATON_CLEARED.equals( action ) )
            {
               handleNotificationCleared( intent );
            }
         }
      }
      catch ( Throwable e )
      {
         stopSelf();
         
         throw new RuntimeException( "MolokoNotificationService encountered an exception. Stopping self.",
                                     e );
      }
      
      return Service.START_STICKY;
   }
   
   
   
   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      notificationManager.onConfigurationChanged( newConfig );
   }
   
   
   
   @Override
   public void onDestroy()
   {
      deleteNotificationManager();
      deleteNotifierContext();
      
      super.onDestroy();
   }
   
   
   
   @Override
   public IBinder onBind( Intent intent )
   {
      return serviceNotBindable();
   }
   
   
   
   public static IHandlerToken acquireHandlerToken()
   {
      return handler.aquireToken();
   }
   
   
   
   private void ensureServiceIsStarted()
   {
      if ( !notificationManager.isStarted() )
      {
         notificationManager.start();
      }
   }
   
   
   
   private void handleNotificationClicked( Intent intent )
   {
      notificationManager.onNotificationClicked( intent );
   }
   
   
   
   private void handleNotificationCleared( Intent intent )
   {
      notificationManager.onNotificationCleared( intent );
   }
   
   
   
   private void createNotifierContext()
   {
      notifierContext = new NotifierContext( this );
   }
   
   
   
   private void deleteNotifierContext()
   {
      if ( notifierContext != null )
      {
         notifierContext.shutdown();
         notifierContext = null;
      }
   }
   
   
   
   private void createNotificationManager()
   {
      if ( notificationManager == null )
      {
         notificationManager = new MolokoNotificationManager( notifierContext );
      }
   }
   
   
   
   private void deleteNotificationManager()
   {
      if ( notificationManager != null )
      {
         notificationManager.shutdown();
         notificationManager = null;
      }
   }
   
   
   
   private final static IBinder serviceNotBindable()
   {
      return null;
   }
}
