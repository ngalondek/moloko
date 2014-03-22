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

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.rtm.Strings;


public class Estimation implements Serializable
{
   public static final Estimation EMPTY = new Estimation( null,
                                                          Constants.NO_TIME );
   
   private static final long serialVersionUID = 9108803485851940464L;
   
   private final String sentence;
   
   private final long estimateMillis;
   
   
   
   public Estimation( String sentence, long estimateMillis )
   {
      if ( Strings.isNullOrEmpty( sentence )
         && estimateMillis != Constants.NO_TIME )
      {
         throw new IllegalArgumentException();
      }
      
      this.sentence = sentence;
      this.estimateMillis = estimateMillis;
   }
   
   
   
   public String getSentence()
   {
      return sentence;
   }
   
   
   
   // TODO: This must not always be set, even if we have a sentence. The sentence may not be parsable. Consider this.
   public long getMillis()
   {
      return estimateMillis;
   }
   
   
   
   public boolean hasMillis()
   {
      return estimateMillis != Constants.NO_TIME;
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
      
      return sentence.equals( other.sentence )
         && estimateMillis == other.estimateMillis;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + sentence.hashCode();
      result = 31 * result + (int) estimateMillis;
      
      return result;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Estimation [sentence=%s, millis=%s]",
                            sentence,
                            estimateMillis );
   }
}
