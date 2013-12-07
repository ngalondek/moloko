/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import dev.drsoran.rtm.IConnectionFactory;


class ConnectionService implements IConnectionService
{
   private final Context context;
   
   private final IConnectionFactory connectionFactory;
   
   
   
   public ConnectionService( Context context,
      IConnectionFactory rtmConnectionFactory )
   {
      this.context = context;
      this.connectionFactory = rtmConnectionFactory;
   }
   
   
   
   @Override
   public boolean hasInternetConnection()
   {
      boolean connected = false;
      
      final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
      
      final NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
      
      if ( networkInfos != null )
      {
         for ( int i = 0; i < networkInfos.length && !connected; i++ )
         {
            connected = networkInfos[ i ] != null
               && networkInfos[ i ].isConnectedOrConnecting();
         }
      }
      
      return connected;
   }
   
   
   
   @Override
   public IConnectionFactory getConnectionFactory()
   {
      return connectionFactory;
   }
}
