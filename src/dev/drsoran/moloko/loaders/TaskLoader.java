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

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskLoader extends AbstractLoader< Task >
{
   public final static int ID = R.id.loader_task;
   
   
   public static class Config
   {
      public final static String TASK_ID = "task_id";
   }
   
   private final String taskId;
   
   
   
   public TaskLoader( Context context, String taskId )
   {
      super( context );
      this.taskId = taskId;
   }
   
   
   
   @Override
   protected Task queryResultInBackground( ContentProviderClient client )
   {
      Task task = null;
      
      if ( taskId != null )
         task = TasksProviderPart.getTask( client, taskId );
      
      return task;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Tasks.CONTENT_URI;
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
