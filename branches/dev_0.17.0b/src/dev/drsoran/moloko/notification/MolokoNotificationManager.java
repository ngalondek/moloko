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

import android.content.Context;
import dev.drsoran.moloko.IOnBootCompletedListener;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.MolokoApp;


public class MolokoNotificationManager implements IOnBootCompletedListener,
         IOnTimeChangedListener, IOnSettingsChangedListener
{
   private final Context context;
   
   private List< INotifier > notifiers = new ArrayList< INotifier >();
   
   
   
   public MolokoNotificationManager( Context context )
   {
      this.context = context;
      
      registerListeners();
      createNotifiers();
      
      recreateNotifications();
   }
   
   
   
   public void shutdown()
   {
      unregisterListeners();
      shutdownNotifiers();
   }
   
   
   
   @Override
   public void onBootCompleted()
   {
      MolokoApp.get( context ).unregisterOnBootCompletedListener( this );
      recreateNotifications();
   }
   
   
   
   public void onSystemLanguageChanged()
   {
      recreateNotifications();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      for ( INotifier notifier : notifiers )
      {
         notifier.onTimeChanged( which );
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      for ( INotifier notifier : notifiers )
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
      for ( INotifier notifier : notifiers )
      {
         notifier.shutdown();
      }
      
      notifiers.clear();
   }
   
   
   
   private void registerListeners()
   {
      MolokoApp.get( context ).registerOnBootCompletedListener( this );
      MolokoApp.get( context )
               .registerOnTimeChangedListener( IOnTimeChangedListener.ALL, this );
      MolokoApp.get( context )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                   this );
   }
   
   
   
   private void unregisterListeners()
   {
      // IOnBootCompletedListener will be unregistered in onBootCompleted().
      MolokoApp.get( context ).unregisterOnTimeChangedListener( this );
      MolokoApp.get( context ).unregisterOnSettingsChangedListener( this );
   }
}
