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
package dev.drsoran.moloko.domain.model;

import java.io.Serializable;

import dev.drsoran.rtm.parsing.recurrence.RtmRecurrence;


public class Recurrence implements Serializable
{
   private static final long serialVersionUID = -2693479322154192406L;
   
   public final static Recurrence EMPTY = new Recurrence( RtmRecurrence.EMPTY );
   
   private final RtmRecurrence rtmRecurrence;
   
   
   
   public Recurrence( RtmRecurrence rtmRecurrence )
   {
      if ( rtmRecurrence == null )
      {
         throw new IllegalArgumentException( "rtmRecurrence" );
      }
      
      this.rtmRecurrence = rtmRecurrence;
   }
   
   
   
   public Recurrence( String pattern, boolean isEveryRecurrence )
   {
      rtmRecurrence = new RtmRecurrence( pattern, isEveryRecurrence );
   }
   
   
   
   public String getPattern()
   {
      return rtmRecurrence.getPattern();
   }
   
   
   
   public boolean isEveryRecurrence()
   {
      return rtmRecurrence.isEveryRecurrence();
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
      if ( o.getClass() != Recurrence.class )
      {
         return false;
      }
      
      final Recurrence other = (Recurrence) o;
      
      return rtmRecurrence.equals( other.rtmRecurrence );
   }
   
   
   
   @Override
   public int hashCode()
   {
      return rtmRecurrence.hashCode();
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Recurrence [pattern=%s, isEvery=%s]",
                            getPattern(),
                            isEveryRecurrence() );
   }
}
