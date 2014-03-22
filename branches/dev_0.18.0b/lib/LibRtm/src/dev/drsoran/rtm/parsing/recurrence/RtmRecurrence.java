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

/**
 * 
 */
package dev.drsoran.rtm.parsing.recurrence;

import dev.drsoran.rtm.Strings;



public class RtmRecurrence
{
   public static RtmRecurrence EMPTY = new RtmRecurrence( null, false );
   
   private final String pattern;
   
   private final boolean isEveryRecurrence;
   
   
   
   public RtmRecurrence( String pattern, boolean isEveryRecurrence )
   {
      this.pattern = pattern;
      this.isEveryRecurrence = isEveryRecurrence;
   }
   
   
   
   public String getPattern()
   {
      return pattern;
   }
   
   
   
   public boolean isEveryRecurrence()
   {
      return isEveryRecurrence;
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
      if ( o.getClass() != RtmRecurrence.class )
      {
         return false;
      }
      
      final RtmRecurrence other = (RtmRecurrence) o;
      
      return Strings.equalsNullAware( pattern, other.pattern )
         && isEveryRecurrence == other.isEveryRecurrence;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + pattern.hashCode();
      result = 31 * result + ( isEveryRecurrence ? 1 : 0 );
      
      return result;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Recurrence [pattern=%s, isEvery=%s]",
                            pattern,
                            isEveryRecurrence );
   }
}
