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

package dev.drsoran.moloko.app.loaders;

import java.util.List;

import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.rtm.Iterables;


public class TasksLoader extends AbstractLoader< List< Task > >
{
   public final static int ID = R.id.loader_tasks;
   
   private final TaskContentOptions taskContentOptions;
   
   private final long tasksListId;
   
   private final RtmSmartFilter smartFilter;
   
   
   
   public TasksLoader( DomainContext context, long tasksListId,
      TaskContentOptions taskContentOptions )
   {
      super( context );
      
      if ( tasksListId == Constants.NO_ID )
      {
         throw new IllegalArgumentException( "tasksListId" );
      }
      
      this.tasksListId = tasksListId;
      this.taskContentOptions = taskContentOptions;
      this.smartFilter = null;
   }
   
   
   
   public TasksLoader( DomainContext context, RtmSmartFilter filter,
      TaskContentOptions taskContentOptions )
   {
      super( context );
      
      if ( filter == null )
      {
         throw new IllegalArgumentException( "filter" );
      }
      
      this.tasksListId = Constants.NO_ID;
      this.taskContentOptions = taskContentOptions;
      this.smartFilter = filter;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.TASKS_CONTENT_URI;
   }
   
   
   
   @Override
   public List< Task > queryResultInBackground( IContentRepository contentRepository )
   {
      final Iterable< Task > tasks;
      
      if ( tasksListId != Constants.NO_ID )
      {
         tasks = contentRepository.getTasksInPhysicalTasksList( tasksListId,
                                                                taskContentOptions );
      }
      else
      {
         tasks = contentRepository.getTasksFromSmartFilter( smartFilter,
                                                            taskContentOptions );
      }
      
      return Iterables.asList( tasks );
   }
}
