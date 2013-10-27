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

import java.io.Serializable;

import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterSyntax;
import dev.drsoran.moloko.util.Strings;


public class RtmSmartFilter implements Serializable
{
   private static final long serialVersionUID = 7003676702627283961L;
   
   private final String filter;
   
   
   
   public RtmSmartFilter( String filter )
   {
      if ( Strings.isNullOrEmpty( filter ) )
      {
         throw new IllegalArgumentException( "filter" );
      }
      
      this.filter = transformFilter( filter );
   }
   
   
   
   public String getFilterString()
   {
      return filter;
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
      
      if ( o.getClass() != getClass() )
      {
         return false;
      }
      
      final RtmSmartFilter other = (RtmSmartFilter) o;
      final boolean equal = Strings.equalsNullAware( filter, other.filter );
      
      return equal;
   }
   
   
   
   @Override
   public int hashCode()
   {
      return filter.hashCode();
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmSmartFilter [filter=%s]", filter );
   }
   
   
   
   private static String transformFilter( String filter )
   {
      // Check if there was no operator used. If so it has the
      // same meaning as operator name:
      if ( !filter.contains( ":" ) )
      {
         filter = RtmSmartFilterSyntax.OP_NAME + Strings.quotify( filter );
      }
      
      return filter;
   }
}
