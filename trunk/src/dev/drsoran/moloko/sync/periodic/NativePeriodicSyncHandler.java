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
import android.content.SyncResult;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm;


class NativePeriodicSyncHandler extends AbstractPeriodicSyncHandler
{
   
   public NativePeriodicSyncHandler( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public void setPeriodicSync( long startUtc, long intervalMs )
   {
      final Account account = AccountUtils.getRtmAccount( context );
      
      if ( account != null )
      {
         ContentResolver.addPeriodicSync( account,
                                          Rtm.AUTHORITY,
                                          getExtras(),
                                          intervalMs / 1000 );
         
         Log.i( LogUtils.toTag( NativePeriodicSyncHandler.class ),
                "Added new periodic sync repeating every "
                   + DateUtils.formatElapsedTime( intervalMs / 1000 ) );
      }
   }
   
   
   
   @Override
   public void delayNextSync( SyncResult syncResult, long seconds )
   {
      syncResult.delayUntil = seconds;
   }
   
   
   
   @Override
   public void resetPeriodicSync()
   {
      final Account account = AccountUtils.getRtmAccount( context );
      
      if ( account != null )
      {
         ContentResolver.removePeriodicSync( account,
                                             Rtm.AUTHORITY,
                                             getExtras() );
         
         Log.i( LogUtils.toTag( NativePeriodicSyncHandler.class ),
                "Removed periodic sync" );
      }
   }
   
   
   
   private final static Bundle getExtras()
   {
      final Bundle bundle = new Bundle();
      
      bundle.putBoolean( Constants.SYNC_EXTRAS_SCHEDULED, Boolean.TRUE );
      bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, Boolean.TRUE );
      
      return bundle;
   }
   
   
   
   @Override
   public void shutdown()
   {
      resetPeriodicSync();
   }
}
