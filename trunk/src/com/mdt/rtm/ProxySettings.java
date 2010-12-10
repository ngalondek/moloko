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

package com.mdt.rtm;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.R;


public class ProxySettings
{
   public final static String TAG = "Moloko."
      + ProxySettings.class.getSimpleName();
   
   private final String proxyHostName;
   
   private final int proxyPortNumber;
   
   private final String proxyLogin;
   
   private final String proxyPassword;
   
   

   public ProxySettings( String proxyHostName, int proxyPortNumber )
   {
      this.proxyHostName = proxyHostName;
      this.proxyPortNumber = proxyPortNumber;
      this.proxyLogin = null;
      this.proxyPassword = null;
   }
   


   public ProxySettings( String proxyHostName, int proxyPortNumber,
      String proxyLogin, String proxyPassword )
   {
      super();
      this.proxyHostName = proxyHostName;
      this.proxyPortNumber = proxyPortNumber;
      this.proxyLogin = proxyLogin;
      this.proxyPassword = proxyPassword;
   }
   


   public String getProxyHostName()
   {
      return proxyHostName;
   }
   


   public int getProxyPortNumber()
   {
      return proxyPortNumber;
   }
   


   public String getProxyLogin()
   {
      return proxyLogin;
   }
   


   public String getProxyPassword()
   {
      return proxyPassword;
   }
   


   public static ProxySettings loadFromPreferences( Context context,
                                                    SharedPreferences prefs )
   {
      ProxySettings settings = null;
      
      if ( prefs != null )
      {
         final String hostname = prefs.getString( context.getString( R.string.key_conn_proxy_host ),
                                                  null );
         final String port = prefs.getString( context.getString( R.string.key_conn_proxy_port ),
                                              null );
         final String user = prefs.getString( context.getString( R.string.key_conn_proxy_user ),
                                              null );
         final String pass = prefs.getString( context.getString( R.string.key_conn_proxy_pass ),
                                              null );
         
         boolean ok = true;
         
         int portNum = 0;
         
         if ( TextUtils.isEmpty( hostname ) )
         {
            Log.w( TAG, "Unable to use proxy, no hostname." );
            ok = false;
         }
         if ( ok && TextUtils.isEmpty( port ) )
         {
            Log.w( TAG, "Unable to use proxy, no port." );
            ok = false;
         }
         if ( ok )
         {
            try
            {
               portNum = Integer.parseInt( port );
            }
            catch ( NumberFormatException nfe )
            {
               ok = false;
               Log.w( TAG, "Unable to use proxy, invalid port.", nfe );
            }
         }
         
         settings = new ProxySettings( hostname, portNum, user, pass );
      }
      else
      {
         Log.w( TAG, "Unable to access the settings." );
      }
      
      return settings;
   }
}
