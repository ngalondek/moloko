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

package dev.drsoran.moloko.app.notification;

import java.util.Locale;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.Configuration;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents.Extras;
import dev.drsoran.moloko.app.event.IAccountUpdatedListener;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.services.IAppEventService;
import dev.drsoran.moloko.event.IOnTimeChangedListener;


class MolokoNotificationManager implements IOnTimeChangedListener,
         IOnSettingsChangedListener, IAccountUpdatedListener
{
   private final AppContext context;
   
   private final IStatusbarNotifier[] notifiers = new IStatusbarNotifier[ 2 ];
   
   private Locale lastUsedLocale;
   
   private boolean started;
   
   
   
   public MolokoNotificationManager( AppContext context )
   {
      this.context = context;
      this.lastUsedLocale = getCurrentNotificatonLocale();
      
      registerListeners();
   }
   
   
   
   public void start()
   {
      if ( !started )
      {
         recreateNotificationsImpl();
         started = true;
      }
   }
   
   
   
   public boolean isStarted()
   {
      return started;
   }
   
   
   
   public void shutdown()
   {
      if ( started )
      {
         unregisterListeners();
         shutdownNotifiers();
         started = false;
      }
   }
   
   
   
   public void onConfigurationChanged( Configuration newConfig )
   {
      final Locale currentLocale = getCurrentNotificatonLocale();
      if ( !currentLocale.equals( lastUsedLocale ) )
      {
         lastUsedLocale = currentLocale;
         
         // Only if the manager is started we recreate the notifications.
         // Otherwise we simply store the changed locale.
         if ( isStarted() )
         {
            recreateNotifications();
         }
      }
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      checkMolokoNotificationManagerIsStarted();
      
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onTimeChanged( which );
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      checkMolokoNotificationManagerIsStarted();
      
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onSettingsChanged( which );
      }
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      checkMolokoNotificationManagerIsStarted();
      recreateNotifications();
   }
   
   
   
   public void onNotificationClicked( Intent intent )
   {
      checkMolokoNotificationManagerIsStarted();
      
      int notificationId = getNotificationId( intent );
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onNotificationClicked( notificationId, intent );
      }
   }
   
   
   
   public void onNotificationCleared( Intent intent )
   {
      checkMolokoNotificationManagerIsStarted();
      
      int notificationId = getNotificationId( intent );
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onNotificationCleared( notificationId, intent );
      }
   }
   
   
   
   public void recreateNotifications()
   {
      checkMolokoNotificationManagerIsStarted();
      recreateNotificationsImpl();
   }
   
   
   
   private void createNotifiers()
   {
      notifiers[ 0 ] = new PermanentNotifier( context );
      notifiers[ 1 ] = new DueTasksNotifier( context );
   }
   
   
   
   private void shutdownNotifiers()
   {
      for ( int i = 0; i < notifiers.length; i++ )
      {
         if ( notifiers[ i ] != null )
         {
            notifiers[ i ].shutdown();
            notifiers[ i ] = null;
         }
      }
   }
   
   
   
   private void recreateNotificationsImpl()
   {
      shutdownNotifiers();
      createNotifiers();
   }
   
   
   
   private void registerListeners()
   {
      context.getSystemEvents()
             .registerOnTimeChangedListener( IOnTimeChangedListener.ALL, this );
      
      final IAppEventService appEventService = context.getAppEvents();
      appEventService.registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                         this );
      appEventService.registerAccountUpdatedListener( this );
   }
   
   
   
   private void unregisterListeners()
   {
      final IAppEventService appEventService = context.getAppEvents();
      appEventService.unregisterAccountUpdatedListener( this );
      appEventService.unregisterOnSettingsChangedListener( this );
      
      context.getSystemEvents().unregisterOnTimeChangedListener( this );
   }
   
   
   
   private Locale getCurrentNotificatonLocale()
   {
      return context.getSettings().getLocale();
   }
   
   
   
   private void checkMolokoNotificationManagerIsStarted()
   {
      if ( !started )
      {
         throw new IllegalStateException( getClass().getSimpleName()
            + " is not started" );
      }
   }
   
   
   
   private static int getNotificationId( Intent intent )
   {
      int notificationId = intent.getIntExtra( Extras.KEY_NOTIFICATION_ID, -1 );
      if ( notificationId == -1 )
      {
         throw new IllegalArgumentException( "The intent has no notification ID." );
      }
      
      return notificationId;
   }
}
