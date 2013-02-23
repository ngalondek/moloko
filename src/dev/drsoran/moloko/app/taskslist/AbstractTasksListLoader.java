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

package dev.drsoran.moloko.app.taskslist;

import java.util.List;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.loaders.AbstractLoader;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListLoader< T extends Task > extends
         AbstractLoader< List< T > >
{
   protected AbstractTasksListLoader( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Tasks.CONTENT_URI;
   }
   
   
   
   @Override
   protected void clearResult( List< T > result )
   {
      result.clear();
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      TasksProviderPart.registerContentObserver( getContext(), observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      TasksProviderPart.unregisterContentObserver( getContext(), observer );
   }
}
