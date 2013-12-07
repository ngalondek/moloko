/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package dev.drsoran.rtm.model.old;

import java.util.Comparator;

import org.w3c.dom.Element;

import dev.drsoran.Strings;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;


public class RtmList
{
   private static final class LessIdComperator implements Comparator< RtmList >
   {
      @Override
      public int compare( RtmList object1, RtmList object2 )
      {
         return object1.id.compareTo( object2.id );
      }
      
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final String name;
   
   private final long created;
   
   private final long modified;
   
   private final long deleted;
   
   private final int locked;
   
   private final int archived;
   
   private final int position;
   
   private final String smartFilter;
   
   
   
   public RtmList( String id, String name, long created, long modified,
      long deleted, int locked, int archived, int position, String smartFilter )
   {
      this.id = id;
      this.name = name;
      this.created = created;
      this.modified = modified;
      this.deleted = deleted;
      this.locked = locked;
      this.archived = archived;
      this.position = position;
      this.smartFilter = smartFilter;
   }
   
   
   
   public RtmList( Element elt )
   {
      this.id = elt.getAttribute( "id" );
      this.name = elt.getAttribute( "name" );
      this.created = null;
      this.modified = null;
      if ( Integer.parseInt( elt.getAttribute( "deleted" ) ) == 0 )
         this.deleted = null;
      else
         this.deleted = new ParcelableDate( System.currentTimeMillis() );
      this.locked = Integer.parseInt( elt.getAttribute( "locked" ) );
      this.archived = Integer.parseInt( elt.getAttribute( "archived" ) );
      this.position = Integer.parseInt( elt.getAttribute( "position" ) );
      
      final Element filter = child( elt, "filter" );
      
      if ( filter != null )
      {
         this.smartFilter = new RtmSmartFilter( filter );
      }
      else
      {
         this.smartFilter = null;
      }
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public long getCreatedDate()
   {
      return created;
   }
   
   
   
   public long getModifiedDate()
   {
      return modified;
   }
   
   
   
   public long getDeletedDate()
   {
      return deleted;
   }
   
   
   
   public int getLocked()
   {
      return locked;
   }
   
   
   
   public int getArchived()
   {
      return archived;
   }
   
   
   
   public int getPosition()
   {
      return position;
   }
   
   
   
   public String getSmartFilter()
   {
      return smartFilter;
   }
   
   
   
   @Override
   public String toString()
   {
      return "<"
         + id
         + ","
         + name
         + ( ( smartFilter != null ) ? "," + smartFilter : Strings.EMPTY_STRING )
         + ">";
   }
}
