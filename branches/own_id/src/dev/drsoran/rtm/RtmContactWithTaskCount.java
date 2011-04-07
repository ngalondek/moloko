/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.rtm;

public class RtmContactWithTaskCount
{
   private final RtmContact contact;
   
   private final int taskCount;
   
   

   public RtmContactWithTaskCount( RtmContact contact, int taskCount )
   {
      this.contact = contact;
      this.taskCount = taskCount;
   }
   


   public long getId()
   {
      return contact.getId();
   }
   


   public String getFullname()
   {
      return contact.getFullname();
   }
   


   public String getUsername()
   {
      return contact.getUsername();
   }
   


   public int getTaskCount()
   {
      return taskCount;
   }
   
}
