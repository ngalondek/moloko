/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.domain.model;

public abstract class TasksList extends LifeTimeManaged implements ITasksList
{
   private final long id;
   
   private final boolean locked;
   
   private final boolean archived;
   
   private String name;
   
   private ExtendedTaskCount tasksCount;
   
   
   
   protected TasksList( long id, long createdMillisUtc, boolean locked,
      boolean archived )
   {
      super( createdMillisUtc );
      
      this.id = id;
      this.locked = locked;
      this.archived = archived;
   }
   
   
   
   @Override
   public long getId()
   {
      return id;
   }
   
   
   
   @Override
   public String getName()
   {
      return name;
   }
   
   
   
   public void setName( String name )
   {
      this.name = name;
   }
   
   
   
   @Override
   public boolean isLocked()
   {
      return locked;
   }
   
   
   
   @Override
   public boolean isArchived()
   {
      return archived;
   }
   
   
   
   @Override
   public boolean hasTaskCount()
   {
      return tasksCount != null;
   }
   
   
   
   @Override
   public ExtendedTaskCount getTasksCount()
   {
      return tasksCount;
   }
   
   
   
   @Override
   public void setTasksCount( ExtendedTaskCount tasksCount )
   {
      if ( tasksCount == null )
      {
         throw new IllegalArgumentException( "tasksCount" );
      }
      
      this.tasksCount = tasksCount;
   }
}
