/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import dev.drsoran.moloko.app.AppContext;


/**
 * Service to handle Account sync. It instantiates the {@link SyncAdapter} and returns its IBinder.
 */
public class AndroidSyncProvider extends Service
{
   private final static Object thisLock = new Object();
   
   private static SyncAdapter syncAdapter;
   
   
   
   /*
    * {@inheritDoc}
    */
   @Override
   public void onCreate()
   {
      synchronized ( thisLock )
      {
         if ( syncAdapter == null )
         {
            syncAdapter = new SyncAdapter( AppContext.get( getApplicationContext() ),
                                           true );
         }
      }
   }
   
   
   
   /*
    * {@inheritDoc}
    */
   @Override
   public IBinder onBind( Intent intent )
   {
      return syncAdapter.getSyncAdapterBinder();
   }
   
}
