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

package dev.drsoran.rtm.parsing.recurrence;

public final class OperandByDayValue
{
   public final Integer qualifier;
   
   public final int weekday;
   
   
   
   public OperandByDayValue( Integer qualifier, int weekday )
   {
      this.qualifier = qualifier;
      this.weekday = weekday;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( o == null )
      {
         return false;
      }
      
      if ( o == this )
      {
         return true;
      }
      
      if ( o.getClass() != OperandByDayValue.class )
      {
         return false;
      }
      
      final OperandByDayValue other = (OperandByDayValue) o;
      
      final boolean equalQualifier = qualifier == other.qualifier
         || qualifier != null && qualifier.equals( other.qualifier );
      
      return equalQualifier && other.weekday == weekday;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int hash = qualifier != null ? qualifier.hashCode() : 0;
      hash += 397 * weekday;
      
      return hash;
   }
   
   
   
   @Override
   public String toString()
   {
      return qualifier != null ? qualifier + "" + weekday + "" : weekday + "";
   }
}
