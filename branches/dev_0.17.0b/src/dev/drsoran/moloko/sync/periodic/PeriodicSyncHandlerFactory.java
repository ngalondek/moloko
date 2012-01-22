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
import android.os.Bundle;


public final class PeriodicSyncHandlerFactory
{
   public final static IPeriodicSyncHandler createPeriodicSyncHandler( Context context )
   {
      // Check which Android version we have and if we can use native
      // periodic sync support.
      try
      {
         ContentResolver.class.getMethod( "addPeriodicSync",
                                          Account.class,
                                          String.class,
                                          Bundle.class,
                                          long.class );
         
         return new NativePeriodicSyncHandler( context );
      }
      catch ( Throwable e )
      {
         return new AlarmManagerPeriodicSyncHandler( context );
      }
   }
}
