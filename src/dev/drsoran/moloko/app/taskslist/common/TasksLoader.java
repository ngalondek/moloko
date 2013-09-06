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

package dev.drsoran.moloko.app.taskslist.common;

import java.util.Collections;
import java.util.List;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;


class TasksLoader extends AbstractTasksListLoader< Task >
{
   public final static int ID = R.id.loader_tasks;
   
   private final String selection;
   
   private final String order;
   
   private final Long taskId;
   
   
   
   public TasksLoader( DomainContext context, long taskId )
   {
      super( context );
      this.selection = null;
      this.order = null;
      this.taskId = taskId;
   }
   
   
   
   public TasksLoader( DomainContext context, String selection, String order )
   {
      super( context );
      this.selection = selection;
      this.order = order;
      this.taskId = null;
   }
   
   
   
   @Override
   protected List< Task > queryResultInBackground( IContentRepository contentRepository )
   {
      final List< Task > tasks;
      
      if ( taskId != null )
      {
         tasks = Collections.singletonList( contentRepository.getTask( taskId,
                                                                       TaskContentOptions.Complete ) );
      }
      else
      {
         tasks = contentRepository.getTasksFromSmartFilter( smartFilter,
                                                            TaskContentOptions.Complete );
      }
      
      return tasks;
   }
}
