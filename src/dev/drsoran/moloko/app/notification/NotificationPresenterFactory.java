/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.notification;

import android.content.Context;
import android.os.Build;
import dev.drsoran.moloko.app.MolokoApp;


class NotificationPresenterFactory
{
   public static IPermanentNotificationPresenter createPermanentNotificationPresenter( Context context )
   {
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.HONEYCOMB ) )
      {
         return new HoneycombPermanentNotificationPresenter( context );
      }
      else
      {
         return new PreHoneycombPermanentNotificationPresenter( context );
      }
   }
   
   
   
   public static IDueTaskNotificationPresenter createDueTaskNotificationPresenter( Context context )
   {
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.HONEYCOMB ) )
      {
         return new StackedDueTaskNotificationPresenter( context );
      }
      else
      {
         return new MultiDueTaskNotificationPresenter( context );
      }
   }
}
