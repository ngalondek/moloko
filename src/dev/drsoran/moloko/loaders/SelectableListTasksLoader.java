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

package dev.drsoran.moloko.loaders;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.SelectableListTask;
import dev.drsoran.rtm.Task;


public class SelectableListTasksLoader extends
         AbstractTasksListLoader< SelectableListTask >
{
   private final String selection;
   
   private final String order;
   
   private final List< String > selectedTaskIds;
   
   

   public SelectableListTasksLoader( Context context, String selection,
      String order, List< String > selectedTaskIds )
   {
      super( context );
      this.selection = selection;
      this.order = order;
      this.selectedTaskIds = selectedTaskIds;
   }
   


   @Override
   protected List< SelectableListTask > queryResultInBackground( ContentProviderClient client )
   {
      final List< Task > tasks = TasksProviderPart.getTasks( client,
                                                             selection,
                                                             order );
      final List< SelectableListTask > selectableTasks = SelectableListTask.fromListTaskList( ListTask.fromTaskList( tasks ) );
      
      if ( selectableTasks != null )
         SelectableListTask.selectTasksById( selectableTasks, selectedTaskIds );
      
      return selectableTasks;
   }
}
