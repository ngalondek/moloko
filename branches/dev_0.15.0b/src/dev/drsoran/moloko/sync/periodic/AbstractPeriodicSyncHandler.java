/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.sync.periodic;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import dev.drsoran.moloko.IOnBootCompletedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.prefs.SyncIntervalPreference;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.provider.Rtm;


public abstract class AbstractPeriodicSyncHandler implements
         IPeriodicSyncHandler, IOnBootCompletedListener,
         OnSharedPreferenceChangeListener
{
   protected final Context context;
   
   

   protected AbstractPeriodicSyncHandler( Context context )
   {
      this.context = context;
      MolokoApp.get( context ).registerOnBootCompletedListener( this );
      
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      if ( prefs != null )
      {
         prefs.registerOnSharedPreferenceChangeListener( this );
      }
   }
   


   public void onBootCompleted()
   {
      SyncUtils.schedulePeriodicSync( context );
      MolokoApp.get( context ).unregisterOnBootCompletedListener( this );
   }
   


   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( sharedPreferences != null && key != null
         && key.equals( context.getString( R.string.key_sync_inverval ) ) )
      {
         final Account account = AccountUtils.getRtmAccount( context );
         
         if ( account != null )
         {
            final long syncInterval = SyncIntervalPreference.getSyncInterval( context,
                                                                              sharedPreferences );
            
            // enable auto sync
            ContentResolver.setSyncAutomatically( account,
                                                  Rtm.AUTHORITY,
                                                  syncInterval != Constants.SYNC_INTERVAL_MANUAL );
            
            if ( syncInterval != Constants.SYNC_INTERVAL_MANUAL )
               SyncUtils.schedulePeriodicSync( context.getApplicationContext(),
                                               syncInterval );
         }
      }
   }
   


   public void shutdown()
   {
      resetPeriodicSync();
      
      MolokoApp.get( context ).unregisterOnBootCompletedListener( this );
      
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      if ( prefs != null )
      {
         prefs.unregisterOnSharedPreferenceChangeListener( this );
      }
   }
}
