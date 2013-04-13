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

public class Due
{
   private final long dueMillisUtc;
   
   private final boolean hasDueTime;
   
   
   
   public Due( long dueMillisUtc, boolean hasDueTime )
   {
      this.dueMillisUtc = dueMillisUtc;
      this.hasDueTime = hasDueTime;
   }
   
   
   
   public long getMillisUtc()
   {
      return dueMillisUtc;
   }
   
   
   
   public boolean hasDueTime()
   {
      return hasDueTime;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( o == this )
      {
         return true;
      }
      if ( o == null )
      {
         return false;
      }
      if ( o.getClass() != Due.class )
      {
         return false;
      }
      
      final Due other = (Due) o;
      
      return dueMillisUtc == other.dueMillisUtc
         && hasDueTime == other.hasDueTime;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + (int) dueMillisUtc;
      result = 31 * result + ( hasDueTime ? 1 : 0 );
      
      return result;
   }
}
