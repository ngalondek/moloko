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

import dev.drsoran.moloko.util.Strings;


public class Estimation
{
   private final String sentence;
   
   private final long estimateMillisUtc;
   
   
   
   public Estimation( String sentence, long estimateMillisUtc )
   {
      this.sentence = sentence;
      this.estimateMillisUtc = estimateMillisUtc;
   }
   
   
   
   public String getSentence()
   {
      return sentence;
   }
   
   
   
   public long getMillisUtc()
   {
      return estimateMillisUtc;
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
      if ( o.getClass() != Estimation.class )
      {
         return false;
      }
      
      final Estimation other = (Estimation) o;
      
      return Strings.equals( sentence, other.sentence )
         && estimateMillisUtc == other.estimateMillisUtc;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + ( sentence != null ? sentence.hashCode() : 0 );
      result = 31 * result + (int) estimateMillisUtc;
      
      return result;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Estimation [sentence=%s, estimateMillisUtc=%s]",
                            sentence,
                            estimateMillisUtc );
   }
}
