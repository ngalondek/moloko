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

package dev.drsoran.moloko.auth;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import dev.drsoran.moloko.SystemContext;


/**
 * Service to handle Account authentication. It instantiates the authenticator and returns its IBinder.
 */
public class AuthenticationService extends Service
{
   private RtmAccountAuthenticator authenticator;
   
   
   
   @Override
   public void onCreate()
   {
      authenticator = new RtmAccountAuthenticator( SystemContext.get( this ) );
   }
   
   
   
   @Override
   public IBinder onBind( Intent intent )
   {
      if ( intent != null
         && intent.getAction()
                  .equals( AccountManager.ACTION_AUTHENTICATOR_INTENT ) )
      {
         return authenticator.getIBinder();
      }
      
      return null;
   }
}
