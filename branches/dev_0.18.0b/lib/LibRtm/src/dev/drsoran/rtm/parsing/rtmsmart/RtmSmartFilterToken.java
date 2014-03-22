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

package dev.drsoran.rtm.parsing.rtmsmart;

import java.util.Locale;


public class RtmSmartFilterToken
{
   public final int operatorType;
   
   public final String value;
   
   public final boolean isNegated;
   
   
   
   public RtmSmartFilterToken( int operatorType, String value, boolean negated )
   {
      this.operatorType = operatorType;
      this.value = value;
      this.isNegated = negated;
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
      
      if ( o.getClass() != getClass() )
      {
         return false;
      }
      
      final RtmSmartFilterToken other = (RtmSmartFilterToken) o;
      
      return other.operatorType == operatorType && other.value.equals( value )
         && other.isNegated == isNegated;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int hashCode = operatorType;
      hashCode = 31 * hashCode ^ value.hashCode();
      hashCode = 31 * hashCode ^ ( isNegated ? 0 : 1 );
      
      return hashCode;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( Locale.ENGLISH,
                            "Op: %d, %s, neg: %b",
                            operatorType,
                            value,
                            isNegated );
   }
}
