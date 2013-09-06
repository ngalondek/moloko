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

package dev.drsoran.moloko.app.notification;

import java.util.Date;


class DueTaskNotification
{
   private final long taskId;
   
   private final long due;
   
   private boolean isVisible = true;
   
   
   
   public DueTaskNotification( long taskId, long due )
   {
      this.taskId = taskId;
      this.due = due;
   }
   
   
   
   public boolean isVisible()
   {
      return isVisible;
   }
   
   
   
   public void setVisible( boolean isVisible )
   {
      this.isVisible = isVisible;
   }
   
   
   
   public long getTaskId()
   {
      return taskId;
   }
   
   
   
   public long getDue()
   {
      return due;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( o == null )
         return false;
      
      if ( o == this )
         return true;
      
      if ( o.getClass() != getClass() )
         return false;
      
      final DueTaskNotification other = (DueTaskNotification) o;
      
      return taskId == other.taskId && due == other.due;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int hashCode = (int) taskId;
      hashCode = 31 * hashCode + (int) due;
      
      return hashCode;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "%s, %s, %s",
                            taskId,
                            new Date( due ).toString(),
                            String.valueOf( isVisible ) );
   }
}
