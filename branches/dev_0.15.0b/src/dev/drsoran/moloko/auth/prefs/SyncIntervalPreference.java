/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.auth.prefs;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.prefs.AutoSummaryListPreference;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.provider.Rtm;


public class SyncIntervalPreference extends AutoSummaryListPreference
{
   private final static String TAG = "Moloko."
      + SyncIntervalPreference.class.getSimpleName();
   
   

   public SyncIntervalPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   


   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( sharedPreferences != null && key != null
         && key.equals( getContext().getString( R.string.key_sync_inverval ) ) )
      {
         final Account account = AccountUtils.getRtmAccount( getContext() );
         
         if ( account != null )
         {
            final long syncInterval = getSyncInterval( getContext(),
                                                       sharedPreferences );
            
            // enable auto sync
            ContentResolver.setSyncAutomatically( account,
                                                  Rtm.AUTHORITY,
                                                  syncInterval != Constants.SYNC_INTERVAL_MANUAL );
            
            if ( syncInterval != Constants.SYNC_INTERVAL_MANUAL )
               SyncUtils.scheduleSyncAlarm( getContext().getApplicationContext(),
                                            syncInterval );
         }
      }
      else
         super.onSharedPreferenceChanged( sharedPreferences, key );
   }
   


   public final static long getSyncInterval( Context context )
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         return getSyncInterval( context, prefs );
      }
      else
      {
         Log.e( TAG, "Unable to load sync interval setting." );
         return Constants.SYNC_INTERVAL_MANUAL;
      }
   }
   


   public final static long getSyncInterval( Context context,
                                             SharedPreferences preferences )
   {
      try
      {
         return Long.valueOf( preferences.getString( context.getString( R.string.key_sync_inverval ),
                                                     context.getString( R.string.acc_pref_sync_interval_default_value ) ) );
      }
      catch ( NumberFormatException nfe )
      {
         Log.e( TAG, "Unable to load sync interval setting.", nfe );
         return Constants.SYNC_INTERVAL_MANUAL;
      }
   }
}
