/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.app.home;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.rtm.sync.SyncTime;


public class LastSyncWidget extends AsyncLoadingWidget< SyncTime >
{
   public LastSyncWidget( Context context )
   {
      super( context,
             R.layout.home_activity_drawer_last_sync_widget,
             R.id.last_sync_time );
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return null;
   }
   
   
   
   @Override
   protected void initializeNonLoadables( View view )
   {
      final Account account = getAccount();
      setDrawerUsername( view, account );
   }
   
   
   
   @Override
   protected SyncTime doBackgroundQuery()
   {
      final DomainContext domainContext = DomainContext.get( getContext() );
      return domainContext.getContentRepository().getSyncTimes();
   }
   
   
   
   @Override
   protected void onLoadingFinished( View switchView, SyncTime data )
   {
      setLastSyncTime( (TextView) switchView, data );
      switchView.setVisibility( View.VISIBLE );
   }
   
   
   
   private void setDrawerUsername( View view, Account account )
   {
      final TextView userName = (TextView) view.findViewById( R.id.user_name );
      userName.setText( account.name );
   }
   
   
   
   private void setLastSyncTime( TextView lastSyncTimeView, SyncTime syncTime )
   {
      long lastSyncMillis = Math.max( syncTime.getLastSyncInMillis(),
                                      syncTime.getLastSyncOutMillis() );
      
      final String lastSyncTimeString;
      if ( lastSyncMillis != Constants.NO_TIME )
      {
         final long now = AppContext.get( getContext() )
                                    .getCalendarProvider()
                                    .getNowMillisUtc();
         
         lastSyncTimeString = DateUtils.getRelativeTimeSpanString( lastSyncMillis,
                                                                   now,
                                                                   DateUtils.MINUTE_IN_MILLIS )
                                       .toString();
      }
      else
      {
         lastSyncTimeString = getContext().getString( R.string.phr_unknown );
      }
      
      lastSyncTimeView.setText( getContext().getString( R.string.home_last_sync,
                                                        lastSyncTimeString ) );
   }
   
   
   
   private Account getAccount()
   {
      final Account account = AppContext.get( getContext() )
                                        .getAccountService()
                                        .getRtmAccount();
      return account;
   }
}
