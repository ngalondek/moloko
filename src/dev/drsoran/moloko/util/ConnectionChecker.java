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

package dev.drsoran.moloko.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public final class ConnectionChecker
{
   public final static boolean isConnected( Context context )
   {
      boolean connected = false;
      
      final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
      
      final NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
      
      for ( int i = 0; i < networkInfos.length && !connected; i++ )
      {
         connected = networkInfos[ i ].isConnectedOrConnecting();
      }
      
      return connected;
   }
}
