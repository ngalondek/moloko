/* 
 * Copyright (c) 2013 Ronny Röhricht
 *
 * This file is part of Moloko.
 *
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.model;

import dev.drsoran.Strings;


public class RtmTasksList
{
   private final String id;
   
   private final long deletedMillisUtc;
   
   private final int position;
   
   private final boolean locked;
   
   private final boolean archived;
   
   private final String name;
   
   private final String smartFilter;
   
   
   
   public RtmTasksList( String id, long deletedMillisUtc, int position,
      boolean locked, boolean archived, String name, String smartFilter )
   {
      if ( id == RtmConstants.NO_ID )
      {
         throw new IllegalArgumentException( "id" );
      }
      
      if ( Strings.isNullOrEmpty( name ) )
      {
         throw new IllegalArgumentException( "name" );
      }
      
      this.id = id;
      this.deletedMillisUtc = deletedMillisUtc;
      this.position = position;
      this.locked = locked;
      this.archived = archived;
      this.name = name;
      this.smartFilter = smartFilter;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public long getDeletedMillisUtc()
   {
      return deletedMillisUtc;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public int getPosition()
   {
      return position;
   }
   
   
   
   public boolean isLocked()
   {
      return locked;
   }
   
   
   
   public boolean isArchived()
   {
      return archived;
   }
   
   
   
   public boolean isSmartList()
   {
      return smartFilter != null;
   }
   
   
   
   public String getSmartFilter()
   {
      return smartFilter;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmTasksList [id=%s, %s, name=%s, position=%s, smart=%s]",
                            id,
                            super.toString(),
                            name,
                            position,
                            isSmartList() );
   }
}
