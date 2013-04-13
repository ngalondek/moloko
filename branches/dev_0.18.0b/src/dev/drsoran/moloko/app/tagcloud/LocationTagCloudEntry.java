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

import android.view.View;
import android.widget.Button;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Location;


class LocationTagCloudEntry extends TagCloudEntry implements
         View.OnClickListener, View.OnLongClickListener
{
   private final Location location;
   
   
   
   public LocationTagCloudEntry( Location location )
   {
      super( location.getName() );
      this.location = location;
   }
   
   
   
   @Override
   public int compareTo( TagCloudEntry other )
   {
      int cmp = LocationTagCloudEntry.class.getName()
                                           .compareTo( other.getClass()
                                                            .getName() );
      if ( cmp == 0 )
      {
         final LocationTagCloudEntry otherLocationTagCloudEntry = (LocationTagCloudEntry) other;
         final long otherLocationId = otherLocationTagCloudEntry.location.getId();
         final long locationId = location.getId();
         
         if ( otherLocationId < locationId )
         {
            cmp = -1;
         }
         else if ( otherLocationId > locationId )
         {
            cmp = 1;
         }
      }
      
      return cmp + super.compareTo( other );
   }
   
   
   
   @Override
   public void present( Button button )
   {
      button.setOnClickListener( this );
      button.setLongClickable( true );
      button.setOnLongClickListener( this );
      button.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
      button.setTextColor( button.getContext()
                                 .getResources()
                                 .getColor( R.color.tagcloud_tag_text_normal ) );
   }
   
   
   
   @Override
   public boolean onLongClick( View v )
   {
      if ( getTagCloudFragmentListener() != null )
      {
         getTagCloudFragmentListener().onOpenLocationWithOtherApp( location );
         return true;
      }
      
      return false;
   }
   
   
   
   @Override
   public void onClick( View v )
   {
      if ( getTagCloudFragmentListener() != null )
      {
         getTagCloudFragmentListener().onOpenLocation( location );
      }
   }
   
   
   
   @Override
   public String toString()
   {
      return "Location";
   }
}
