/* 
	Copyright (c) 2012 Ronny Röhricht

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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import dev.drsoran.moloko.IOnNetworkStatusChangedListener;
import dev.drsoran.moloko.util.ListenerList;


public class NetworkStatusReceiver extends BroadcastReceiver
{
   
   private final Handler handler;
   
   
   
   public NetworkStatusReceiver( Handler handler )
   {
      this.handler = handler;
   }
   
   
   
   @Override
   public void onReceive( Context context, Intent intent )
   {
      final ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
      if ( cm != null )
      {
         final Message msg = new Message();
         msg.what = Integer.valueOf( IOnNetworkStatusChangedListener.BACKGROUND_DATA_STATUS );
         
         msg.obj = new ListenerList.MessgageObject< IOnNetworkStatusChangedListener >( IOnNetworkStatusChangedListener.class,
                                                                                       isConnected( cm ) );
         handler.sendMessage( msg );
      }
   }
   
   
   
   private boolean isConnected( ConnectivityManager connectivityManager )
   {
      final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      return activeNetworkInfo != null && activeNetworkInfo.isConnected();
   }
}
