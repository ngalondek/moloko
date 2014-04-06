/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.app.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.model.TasksList;


public class TasksListWidget implements INavWidget
{
   private final Context context;
   
   private final TasksList tasksList;
   
   
   
   public TasksListWidget( Context context, TasksList tasksList )
   {
      this.context = context;
      this.tasksList = tasksList;
   }
   
   
   
   @Override
   public void setDirty()
   {
   }
   
   
   
   @Override
   public void start()
   {
   }
   
   
   
   @Override
   public void stop()
   {
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return Intents.createOpenListIntent( tasksList, null );
   }
   
   
   
   @Override
   public View getView( View convertView )
   {
      convertView = LayoutInflater.from( context )
                                  .inflate( R.layout.home_activity_drawer_taskslist_widget,
                                            null );
      
      ( (TextView) convertView.findViewById( android.R.id.text1 ) ).setText( tasksList.getName() );
      
      final int numIncompleted = tasksList.getTasksCount()
                                          .getIncompleteTaskCount();
      
      final TextView taskCount = (TextView) convertView.findViewById( R.id.numTasks );
      if ( numIncompleted > 0 )
      {
         taskCount.setText( String.valueOf( numIncompleted ) );
      }
      else
      {
         taskCount.setVisibility( View.INVISIBLE );
      }
      
      return convertView;
   }
}
