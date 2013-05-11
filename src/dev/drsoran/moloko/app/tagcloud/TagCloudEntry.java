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

package dev.drsoran.moloko.app.tagcloud;

public class TagCloudEntry implements Comparable< TagCloudEntry >
{
   private final TagCloudEntryType type;
   
   private final String name;
   
   private final int count;
   
   
   
   public TagCloudEntry( TagCloudEntryType type, String name, int count )
   {
      this.type = type;
      this.name = name;
      this.count = count;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public TagCloudEntryType getType()
   {
      return type;
   }
   
   
   
   public int getCount()
   {
      return count;
   }
   
   
   
   @Override
   public int compareTo( TagCloudEntry other )
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
