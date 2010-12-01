/* 
	Copyright (c) 2010 Ronny Röhricht

	This file is part of Moloko.

	Moloko is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Moloko is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

	Contributors:
     Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.receivers;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Connection;
import dev.drsoran.moloko.util.SyncUtils;
import dev.drsoran.provider.Rtm;


public class NetworkStatusReceiver extends BroadcastReceiver
{
   
   @Override
   public void onReceive( Context context, Intent intent )
   {
      boolean stop = false;
      
      // Check if we musn't use background data any more
      if ( intent.getAction()
                 .equals( ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED ) )
      {
         final ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
         
         if ( cm != null )
         {
            // Background data allowed
            if ( cm.getBackgroundDataSetting()
               && Connection.isConnected( context.getApplicationContext() ) )
            {
               // This respects the SYNC_INTERVAL_MANUAL setting
               SyncUtils.scheduleSyncAlarm( context.getApplicationContext() );
            }
            
            // Disconnected or background data forbidden
            else
            {
               stop = true;
            }
         }
      }
      else if ( intent.getAction()
                      .equals( ConnectivityManager.CONNECTIVITY_ACTION ) )
      {
         // If we've lost connection
         if ( intent.getBooleanExtra( ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                                      false ) )
         {
            // Stop sync alarm
            stop = true;
         }
         
         // If we have connection again. This respects the SYNC_INTERVAL_MANUAL setting.
         else
         {
            SyncUtils.scheduleSyncAlarm( context.getApplicationContext() );
         }
      }
      
      if ( stop )
      {
         final Account account = AccountUtils.getRtmAccount( context.getApplicationContext() );
         
         if ( account != null )
         {
            if ( ContentResolver.isSyncActive( account, Rtm.AUTHORITY )
               || ContentResolver.isSyncPending( account, Rtm.AUTHORITY ) )
            {
               ContentResolver.cancelSync( account, Rtm.AUTHORITY );
            }
         }
         
         SyncUtils.stopSyncAlarm( context.getApplicationContext() );
      }
   }
}
