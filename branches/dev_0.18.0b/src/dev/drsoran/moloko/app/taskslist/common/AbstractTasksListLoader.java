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

package dev.drsoran.moloko.app.taskslist.common;

import java.util.List;

import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.app.content.loaders.AbstractLoader;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Task;


abstract class AbstractTasksListLoader< T extends Task > extends
         AbstractLoader< List< T > >
{
   protected AbstractTasksListLoader( DomainContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.TASKS_CONTENT_URI;
   }
   
   
   
   @Override
   protected void clearResult( List< T > result )
   {
      result.clear();
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      TasksProviderPart.put( getContext(), observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      TasksProviderPart.unregisterContentObserver( getContext(), observer );
   }
}
