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
import dev.drsoran.moloko.util.Strings;


public class Location
{
   private final long id;
   
   private final String name;
   
   private final float longitude;
   
   private final float latitude;
   
   private final String address;
   
   private final boolean isViewable;
   
   private final int zoom;
   
   
   
   public Location( long id, String name, float longitude, float latitude,
      String address, boolean isViewable, int zoom )
   {
      if ( id == Constants.NO_ID )
      {
         throw new IllegalArgumentException( "id" );
      }
      
      if ( name == null )
      {
         throw new IllegalArgumentException( "name" );
      }
      
      this.id = id;
      this.name = name;
      this.longitude = longitude;
      this.latitude = latitude;
      this.address = address;
      this.isViewable = isViewable;
      this.zoom = zoom;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public float getLongitude()
   {
      return longitude;
   }
   
   
   
   public float getLatitude()
   {
      return latitude;
   }
   
   
   
   public String getAddress()
   {
      return address;
   }
   
   
   
   public boolean isViewable()
   {
      return isViewable;
   }
   
   
   
   public int getZoom()
   {
      return zoom;
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
      
      if ( o.getClass() != Location.class )
      {
         return false;
      }
      
      final Location other = (Location) o;
      
      return id == other.id && longitude == other.longitude
         && latitude == other.latitude && isViewable == other.isViewable
         && zoom == other.zoom && Strings.equals( name, other.name )
         && Strings.equals( address, other.address );
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + (int) id;
      result = 31 * result + (int) longitude;
      result = 31 * result + (int) latitude;
      result = 31 * result + ( isViewable ? 0 : 1 );
      result = 31 * result + zoom;
      result = 31 * result + name.hashCode();
      result = 31 * result + ( address != null ? address.hashCode() : 0 );
      
      return result;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Location [id=%s, name=%s, address=%s, isViewable=%s]",
                            id,
                            name,
                            address,
                            isViewable );
   }
}
