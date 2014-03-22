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

package dev.drsoran.rtm;

import java.text.MessageFormat;


public final class Pair< F, S >
{
   public final F first;
   
   public final S second;
   
   
   
   public Pair( F first, S second )
   {
      this.first = first;
      this.second = second;
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
      
      @SuppressWarnings( "unchecked" )
      final Class< Pair< F, S >> thisClazz = (Class< Pair< F, S >>) getClass();
      
      if ( o.getClass() != thisClazz )
      {
         return false;
      }
      
      final Pair< F, S > other = thisClazz.cast( o );
      
      final boolean equalsFirst = first == other.first || first != null
         && first.equals( other.first );
      
      final boolean equalsSecond = second == other.second || second != null
         && second.equals( other.second );
      
      return equalsFirst && equalsSecond;
   }
   
   
   
   @Override
   public int hashCode()
   {
      final int firstHash = first != null ? first.hashCode() : 0;
      final int secondHash = second != null ? second.hashCode() : 0;
      
      return ( firstHash * 397 ) ^ secondHash;
   }
   
   
   
   @Override
   public String toString()
   {
      return MessageFormat.format( "[{0}, {1}]",
                                   first != null ? first.toString() : "null",
                                   second != null ? second.toString() : "null" );
   }
   
   
   
   public static < F, S > Pair< F, S > create( F first, S second )
   {
      return new Pair< F, S >( first, second );
   }
}
