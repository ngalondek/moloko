/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.ui.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.RtmCalendar;


public class DueTimeWheelTextAdapter extends AbstractWheelTextAdapter
{
   public final static int TYPE_HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
   
   public final static int TYPE_HOUR = Calendar.HOUR;
   
   public final static int TYPE_MINUTE = Calendar.MINUTE;
   
   public final static int TYPE_AM_PM = Calendar.AM_PM;
   
   private final RtmCalendar calendar;
   
   private final DateFormat dateFormat;
   
   private final int type;
   
   private final int min;
   
   private final int max;
   
   private int lastIndex;
   
   
   
   public DueTimeWheelTextAdapter( Context context, RtmCalendar cal, int type )
   {
      super( context );
      
      this.calendar = RtmCalendar.getInstance();
      this.calendar.setTimeInMillis( cal.getTimeInMillis() );
      this.type = type;
      
      switch ( type )
      {
         case TYPE_AM_PM:
            dateFormat = new SimpleDateFormat( "aa" );
            break;
         case TYPE_HOUR:
            dateFormat = new SimpleDateFormat( "KK" );
            break;
         case TYPE_HOUR_OF_DAY:
            dateFormat = new SimpleDateFormat( "HH" );
            break;
         case TYPE_MINUTE:
            dateFormat = new SimpleDateFormat( "mm" );
            break;
         default :
            dateFormat = new SimpleDateFormat();
            break;
      }
      
      this.min = cal.getActualMinimum( type );
      
      // +1 due to "OFF" field
      this.max = cal.getActualMaximum( type ) + 1;
   }
   
   
   
   @Override
   public int getItemsCount()
   {
      return ( max - min ) + 1;
   }
   
   
   
   @Override
   protected CharSequence getItemText( int index )
   {
      lastIndex = index;
      
      if ( index == 0 )
      {
         return context.getText( R.string.dlg_due_picker_no_time );
      }
      else
      {
         // -1 due to "OFF" field
         calendar.set( type, index - 1 );
         dateFormat.setCalendar( calendar.toCalendar() );
         
         return dateFormat.format( calendar.getTime() );
      }
   }
   
   
   
   @Override
   protected void configureTextView( TextView view )
   {
      if ( lastIndex == 0 )
      {
         view.setTextColor( context.getResources()
                                   .getColor( R.color.app_dlg_due_picker_weekday ) );
      }
      else
      {
         super.configureTextView( view );
      }
   }
}
