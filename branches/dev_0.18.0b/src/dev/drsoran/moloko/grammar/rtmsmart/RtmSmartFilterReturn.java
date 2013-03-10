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

package dev.drsoran.moloko.grammar.rtmsmart;

public class RtmSmartFilterReturn
{
   public final String queryString;
   
   public final boolean hasCompletedOperator;
   
   public final boolean matchQuery;
   
   
   
   public RtmSmartFilterReturn( String queryString, boolean hasCompletedOperator )
   {
      this( queryString, hasCompletedOperator, false );
   }
   
   
   
   public RtmSmartFilterReturn( String queryString,
      boolean hasCompletedOperator, boolean matchQuery )
   {
      this.queryString = queryString;
      this.hasCompletedOperator = hasCompletedOperator;
      this.matchQuery = matchQuery;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( o == this )
      {
         return true;
      }
      
      if ( o == null || o.getClass() != getClass() )
      {
         return false;
      }
      
      final RtmSmartFilterReturn other = (RtmSmartFilterReturn) o;
      
      return other.queryString.equals( queryString )
         && other.hasCompletedOperator == hasCompletedOperator;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int code = queryString.hashCode();
      code = code * 31 ^ ( hasCompletedOperator ? 0 : 1 );
      
      return code;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "%s, complOp: %b",
                            queryString,
                            hasCompletedOperator );
   }
}
