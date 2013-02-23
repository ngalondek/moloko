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

package dev.drsoran.moloko.model;

public abstract class TasksList extends LifeTimeManaged
{
   private final long id;
   
   private final boolean locked;
   
   private final boolean archived;
   
   private String name;
   
   private boolean defaultList;
   
   
   
   protected TasksList( long id, long createdMillisUtc, boolean locked,
      boolean archived )
   {
      super( createdMillisUtc );
      
      this.id = id;
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
      this.name = name;
   }
   
   
   
   public boolean isDefaultList()
   {
      return defaultList;
   }
   
   
   
   public void setDefaultList( boolean defaultList )
   {
      this.defaultList = defaultList;
   }
   
   
   
   public boolean isLocked()
   {
      return locked;
   }
   
   
   
   public boolean isArchived()
   {
      return archived;
   }
}
