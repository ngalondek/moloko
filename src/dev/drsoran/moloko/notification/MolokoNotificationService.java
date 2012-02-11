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

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import dev.drsoran.moloko.NotifierContext;


public class MolokoNotificationService extends Service
{
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
      notificationManager.start();
      
      return Service.START_STICKY;
   }
   
   
   
   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      notificationManager.onConfiurationChanged( newConfig );
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
