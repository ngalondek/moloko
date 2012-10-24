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

package dev.drsoran.moloko.notification;

import java.util.Locale;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.Configuration;
import dev.drsoran.moloko.IAccountUpdatedListener;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.NotifierContext;
import dev.drsoran.moloko.util.Intents.Extras;


class MolokoNotificationManager implements IOnTimeChangedListener,
         IOnSettingsChangedListener, IAccountUpdatedListener
{
   private final NotifierContext context;
   
   private final IStatusbarNotifier[] notifiers = new IStatusbarNotifier[ 2 ];
   
   private Locale lastUsedLocale;
   
   
   
   public MolokoNotificationManager( NotifierContext context )
   {
      this.context = context;
      this.lastUsedLocale = getCurrentNotificatonLocale();
      
      registerListeners();
   }
   
   
   
   public void start()
   {
      recreateNotifications();
   }
   
   
   
   public void shutdown()
   {
      unregisterListeners();
      shutdownNotifiers();
   }
   
   
   
   public void onConfigurationChanged( Configuration newConfig )
   {
      final Locale currentLocale = getCurrentNotificatonLocale();
      if ( !currentLocale.equals( lastUsedLocale ) )
      {
         lastUsedLocale = currentLocale;
         recreateNotifications();
      }
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onTimeChanged( which );
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onSettingsChanged( which );
      }
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      recreateNotifications();
   }
   
   
   
   public void onNotificationClicked( Intent intent )
   {
      int notificationId = getNotificationId( intent );
      for ( int i = 0; i < notifiers.length; i++ )
      {
         notifiers[ i ].onNotificationClicked( notificationId, intent );
      }
   }
   
   
   
   public void onNotificationCleared( Intent intent )
   {
      int notificationId = getNotificationId( intent );
      for ( int i = 0; i < notifiers.length; i++ )
      {
         notifiers[ i ].onNotificationCleared( notificationId, intent );
      }
   }
   
   
   
   public void recreateNotifications()
   {
      shutdownNotifiers();
      createNotifiers();
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
   
   
   
   private void registerListeners()
   {
      context.registerOnTimeChangedListener( IOnTimeChangedListener.ALL, this );
      context.registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                 this );
      context.registerAccountUpdatedListener( this );
   }
   
   
   
   private void unregisterListeners()
   {
      context.unregisterAccountUpdatedListener( this );
      context.unregisterOnTimeChangedListener( this );
      context.unregisterOnSettingsChangedListener( this );
   }
   
   
   
   private Locale getCurrentNotificatonLocale()
   {
      return MolokoApp.getSettings( context ).getLocale();
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
