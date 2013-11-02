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

import dev.drsoran.moloko.content.Constants;


public class CloudEntry implements Comparable< CloudEntry >
{
   private final CloudEntryType type;
   
   private final String name;
   
   private final int count;
   
   private final long elementId;
   
   
   
   public CloudEntry( CloudEntryType type, String display, int count )
   {
      this( type, display, count, Constants.NO_ID );
   }
   
   
   
   public CloudEntry( CloudEntryType type, String display, int count,
      long elementId )
   {
      this.type = type;
      this.name = display;
      this.count = count;
      this.elementId = elementId;
   }
   
   
   
   public String getDisplay()
   {
      return name;
   }
   
   
   
   public CloudEntryType getType()
   {
      return type;
   }
   
   
   
   public int getCount()
   {
      return count;
   }
   
   
   
   public long getElementId()
   {
      return elementId;
   }
   
   
   
   @Override
   public int compareTo( CloudEntry other )
   {
      int res = type.compareTo( other.type );
      
      if ( res == 0 )
      {
         res = name.compareToIgnoreCase( other.name );
      }
      
      if ( res == 0 )
      {
         res = count - other.count;
      }
      
      return res;
   }
   
   
   
   @Override
   public String toString()
   {
      return "<" + type + ": " + name + "(" + count + ")>";
   }
}
