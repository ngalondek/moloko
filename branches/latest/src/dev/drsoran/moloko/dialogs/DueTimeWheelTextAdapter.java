/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.dialogs;

import java.util.Calendar;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.text.format.DateFormat;
import dev.drsoran.moloko.MolokoApp;


public class DueTimeWheelTextAdapter extends AbstractWheelTextAdapter
{
   public final static int TYPE_HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
   
   public final static int TYPE_HOUR = Calendar.HOUR;
   
   public final static int TYPE_MINUTE = Calendar.MINUTE;
   
   public final static int TYPE_AM_PM = Calendar.AM_PM;
   
   private final Calendar calendar;
   
   private final int type;
   
   private final int min;
   
   private final int max;
   
   

   public DueTimeWheelTextAdapter( Context context, Calendar cal, int type )
   {
      super( context );
      
      this.calendar = Calendar.getInstance( MolokoApp.getSettings()
                                                     .getTimezone() );
      this.calendar.setTimeInMillis( cal.getTimeInMillis() );
      this.type = type;
      this.min = cal.getActualMinimum( type );
      this.max = cal.getActualMaximum( type );
   }
   


   @Override
   protected CharSequence getItemText( int index )
   {
      if ( type == TYPE_AM_PM )
      {
         calendar.set( type, index );
         return DateFormat.format( "aa", calendar );
      }
      else
      {
         calendar.set( type, index + 1 );
         return String.format( "%02d", calendar.get( type ) );
      }
   }
   


   public int getItemsCount()
   {
      return ( max - min ) + 1;
   }
}
