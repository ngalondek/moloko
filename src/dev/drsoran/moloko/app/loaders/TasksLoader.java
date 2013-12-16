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
import dev.drsoran.Iterables;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;


public class TasksLoader extends AbstractLoader< List< Task > >
{
   public final static int ID = R.id.loader_tasks;
   
   private final TaskContentOptions taskContentOptions;
   
   private final TasksList tasksList;
   
   private final RtmSmartFilter smartFilter;
   
   
   
   public TasksLoader( DomainContext context, TasksList tasksList,
      TaskContentOptions taskContentOptions )
   {
      super( context );
      this.tasksList = tasksList;
      this.taskContentOptions = taskContentOptions;
      this.smartFilter = null;
   }
   
   
   
   public TasksLoader( DomainContext context, RtmSmartFilter filter,
      TaskContentOptions taskContentOptions )
   {
      super( context );
      this.tasksList = null;
      this.taskContentOptions = taskContentOptions;
      this.smartFilter = filter;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.TASKS_CONTENT_URI;
   }
   
   
   
   @Override
   protected List< Task > queryResultInBackground( IContentRepository contentRepository )
   {
      final Iterable< Task > tasks;
      
      try
      {
         if ( tasksList != null )
         {
            tasks = contentRepository.getTasksInTasksList( tasksList,
                                                           taskContentOptions );
         }
         else
         {
            tasks = contentRepository.getTasksFromSmartFilter( smartFilter,
                                                               taskContentOptions );
         }
         
         return Iterables.asList( tasks );
      }
      catch ( GrammarException e )
      {
         throw new ContentException( e );
      }
   }
}
