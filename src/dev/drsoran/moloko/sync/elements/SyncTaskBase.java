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

package dev.drsoran.moloko.sync.elements;

import java.util.Comparator;
import java.util.Date;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


abstract class SyncTaskBase
{
   protected final static class LessIdComperator< T extends SyncTaskBase >
            implements Comparator< T >
   {
      public int compare( T object1, T object2 )
      {
         return object1.task.getId().compareTo( object2.task.getId() );
      }
   }
   
   protected final RtmTaskSeries taskSeries;
   
   protected final RtmTask task;
   
   

   protected SyncTaskBase( RtmTaskSeries taskSeries )
   {
      this( taskSeries, taskSeries.getTasks().get( 0 ) );
   }
   


   protected SyncTaskBase( RtmTaskSeries taskSeries, String taskId )
   {
      this( taskSeries, taskSeries.getTask( taskId ) );
   }
   


   protected SyncTaskBase( RtmTaskSeries taskSeries, RtmTask task )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskseries is null" );
      if ( task == null )
         throw new NullPointerException( "task is null" );
      
      this.taskSeries = taskSeries;
      this.task = task;
   }
   


   public RtmTaskSeries getTaskSeries()
   {
      return taskSeries;
   }
   


   public RtmTask getTask()
   {
      return task;
   }
   


   public Date getCreatedDate()
   {
      return taskSeries.getCreatedDate();
   }
   


   public Date getModifiedDate()
   {
      return taskSeries.getModifiedDate();
   }
   


   public Date getDeletedDate()
   {
      return task.getDeletedDate();
   }
   


   public boolean hasModification( ModificationSet modificationSet )
   {
      return modificationSet.hasModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                        taskSeries.getId() ) )
         || modificationSet.hasModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                       task.getId() ) );
      
   }
}
