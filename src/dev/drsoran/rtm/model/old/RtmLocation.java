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

import org.w3c.dom.Element;


/**
 * Represents a location.
 * 
 * @author Edouard Mercier
 * @since 2008.05.22
 */
public class RtmLocation
{
   private final String id;
   
   private final String name;
   
   private final float longitude;
   
   private final float latitude;
   
   private final String address;
   
   private final boolean viewable;
   
   private int zoom;
   
   
   
   public RtmLocation( String id, String name, float lon, float lat,
      String address, boolean viewable, int zoom )
   {
      this.id = id;
      this.name = name;
      this.longitude = lon;
      this.latitude = lat;
      this.address = address;
      this.viewable = viewable;
      this.zoom = zoom;
   }
   
   
   
   public RtmLocation( Element element )
   {
      id = textNullIfEmpty( element, "id" );
      name = textNullIfEmpty( element, "name" );
      longitude = Float.parseFloat( element.getAttribute( "longitude" ) );
      latitude = Float.parseFloat( element.getAttribute( "latitude" ) );
      address = textNullIfEmpty( element, "address" );
      zoom = Integer.parseInt( element.getAttribute( "zoom" ) );
      viewable = element.getAttribute( "viewable" ).equals( "1" ) ? true
                                                                 : false;
   }
   
   
   
   public int getZoom()
   {
      return zoom;
   }
   
   
   
   public void setZoom( int zoom )
   {
      this.zoom = zoom;
   }
   
   
   
   public String getId()
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
      return viewable;
   }
}
