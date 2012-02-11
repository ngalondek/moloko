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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.res.Configuration;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.NotifierContext;


class MolokoNotificationManager implements IOnTimeChangedListener,
         IOnSettingsChangedListener
{
   private final NotifierContext context;
   
   private final List< IStatusbarNotifier > notifiers = new ArrayList< IStatusbarNotifier >();
   
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
   
   
   
   public void onConfiurationChanged( Configuration newConfig )
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
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.onSettingsChanged( which );
      }
   }
   
   
   
   public void recreateNotifications()
   {
      shutdownNotifiers();
      createNotifiers();
   }
   
   
   
   private void createNotifiers()
   {
      notifiers.add( new PermanentNotifier( context ) );
      notifiers.add( new DueTasksNotifier( context ) );
   }
   
   
   
   private void shutdownNotifiers()
   {
      for ( IStatusbarNotifier notifier : notifiers )
      {
         notifier.shutdown();
      }
      
      notifiers.clear();
   }
   
   
   
   private void registerListeners()
   {
      context.registerOnTimeChangedListener( IOnTimeChangedListener.ALL, this );
      context.registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                 this );
   }
   
   
   
   private void unregisterListeners()
   {
      context.unregisterOnTimeChangedListener( this );
      context.unregisterOnSettingsChangedListener( this );
   }
   
   
   
   private Locale getCurrentNotificatonLocale()
   {
      return MolokoApp.getSettings( context ).getLocale();
   }
}
