/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.rtm;

import com.mdt.rtm.data.RtmList;


public class RtmListWithTaskCount
{
   private final RtmList impl;
   
   private final int taskCount;
   
   

   public RtmListWithTaskCount( RtmList impl, int taskCount )
      throws NullPointerException
   {
      if ( impl == null )
         throw new NullPointerException();
      
      this.impl = impl;
      this.taskCount = taskCount;
   }
   


   public RtmListWithTaskCount( String id, String name, int deleted,
      int locked, int archived, int position, RtmSmartFilter smartFilter,
      int taskCount )
   {
      this.impl = new RtmList( id,
                               name,
                               deleted,
                               locked,
                               archived,
                               position,
                               smartFilter );
      this.taskCount = taskCount;
   }
   


   public String getId()
   {
      return impl.getId();
   }
   


   public String getName()
   {
      return impl.getName();
   }
   


   public int getDeleted()
   {
      return impl.getDeleted();
   }
   


   public int getLocked()
   {
      return impl.getLocked();
   }
   


   public int getArchived()
   {
      return impl.getArchived();
   }
   


   public int getPosition()
   {
      return impl.getPosition();
   }
   


   public RtmSmartFilter getSmartFilter()
   {
      return impl.getSmartFilter();
   }
   


   public boolean hasSmartFilter()
   {
      return impl.getSmartFilter() != null;
   }
   


   public boolean isSmartFilterValid()
   {
      return !hasSmartFilter() || taskCount > -1;
   }
   


   public int getTaskCount()
   {
      return taskCount;
   }
}
