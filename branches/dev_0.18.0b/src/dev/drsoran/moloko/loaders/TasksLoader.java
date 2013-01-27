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

package dev.drsoran.moloko.loaders;

import java.util.Collections;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.rtm.Task;


public class TasksLoader extends AbstractTasksListLoader< Task >
{
   public final static int ID = R.id.loader_tasks;
   
   private final String selection;
   
   private final String order;
   
   private final String taskId;
   
   
   
   public TasksLoader( Context context, String taskId )
   {
      super( context );
      this.selection = null;
      this.order = null;
      this.taskId = taskId;
   }
   
   
   
   public TasksLoader( Context context, String selection, String order )
   {
      super( context );
      this.selection = selection;
      this.order = order;
      this.taskId = null;
   }
   
   
   
   @Override
   protected List< Task > queryResultInBackground( ContentProviderClient client )
   {
      final List< Task > tasks;
      
      if ( taskId != null )
         tasks = Collections.singletonList( TasksProviderPart.getTask( client,
                                                                       taskId ) );
      else
         tasks = TasksProviderPart.getTasks( client, selection, order );
      
      return tasks;
   }
}
