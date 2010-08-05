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
