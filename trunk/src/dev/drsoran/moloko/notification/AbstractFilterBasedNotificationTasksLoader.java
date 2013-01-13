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

package dev.drsoran.moloko.notification;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;


abstract class AbstractFilterBasedNotificationTasksLoader extends
         AsyncTask< Void, Void, Cursor >
{
   public interface IFilterBasedTasksLoadedHandler
   {
      void onTasksLoaded( String filter, Cursor result );
   }
   
   protected final Context context;
   
   private final IFilterBasedTasksLoadedHandler handler;
   
   
   
   protected AbstractFilterBasedNotificationTasksLoader( Context context,
      IFilterBasedTasksLoadedHandler handler )
   {
      this.context = context;
      this.handler = handler;
   }
   
   
   
   @Override
   protected void onPostExecute( Cursor result )
   {
      if ( handler != null )
      {
         handler.onTasksLoaded( getFilterString(), result );
      }
   }
   
   
   
   public abstract String getFilterString();
}
