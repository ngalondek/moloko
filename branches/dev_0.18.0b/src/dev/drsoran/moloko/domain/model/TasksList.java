/* 
 * Copyright (c) 2013 Ronny Röhricht
 *
 * This file is part of Moloko.
 *
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.domain.model;

import java.io.Serializable;

import dev.drsoran.moloko.util.Strings;


public class TasksList extends LifeTimeManaged implements Serializable
{
   private static final long serialVersionUID = -1926589032995094153L;
   
   private final long id;
   
   private final int position;
   
   private final boolean locked;
   
   private final boolean archived;
   
   private String name;
   
   private ExtendedTaskCount tasksCount;
   
   private RtmSmartFilter smartFilter;
   
   
   
   public TasksList( long id, long createdMillisUtc, String name, int position,
      boolean locked, boolean archived )
   {
      super( createdMillisUtc );
      
      this.id = id;
      this.name = name;
      this.position = position;
      this.locked = locked;
      this.archived = archived;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public void setName( String name )
   {
      if ( Strings.isNullOrEmpty( name ) )
      {
         throw new IllegalArgumentException( "name" );
      }
      
      this.name = name;
   }
   
   
   
   public int getPosition()
   {
      return position;
   }
   
   
   
   public boolean isLocked()
   {
      return locked;
   }
   
   
   
   public boolean isArchived()
   {
      return archived;
   }
   
   
   
   public boolean hasTaskCount()
   {
      return tasksCount != null;
   }
   
   
   
   public ExtendedTaskCount getTasksCount()
   {
      return tasksCount;
   }
   
   
   
   public void setTasksCount( ExtendedTaskCount tasksCount )
   {
      this.tasksCount = tasksCount;
   }
   
   
   
   public boolean isSmartList()
   {
      return smartFilter != null;
   }
   
   
   
   public RtmSmartFilter getSmartFilter()
   {
      return smartFilter;
   }
   
   
   
   public void setSmartFilter( RtmSmartFilter filter )
   {
      this.smartFilter = filter;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "TasksList [id=%s, %s, name=%s, position=%s, smart=%s]",
                            id,
                            super.toString(),
                            name,
                            position,
                            isSmartList() );
   }
}
