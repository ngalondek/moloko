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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kankan.wheel.widget.adapters.AbstractWheelAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoDateUtils;


public class DateFormatWheelTextAdapter extends AbstractWheelAdapter
{
   public final static int TYPE_DEFAULT = 0;
   
   public final static int TYPE_SHOW_WEEKDAY = 1;
   
   public final static int FLAG_MARK_TODAY = 1 << 0;
   
   private final LayoutInflater inflater;
   
   private final int type;
   
   private final Calendar calendar;
   
   private final int calField;
   
   private final DateFormat format;
   
   private final SimpleDateFormat weekDayFormat;
   
   private final int min;
   
   private final int max;
   
   private final int flags;
   
   private final int valueColor;
   
   private final int todayColor;
   
   

   public DateFormatWheelTextAdapter( Context context, Calendar cal,
      int calField, String dateFormat, int type, int flags )
   {
      this.calendar = Calendar.getInstance( MolokoApp.getSettings()
                                                     .getTimezone() );
      this.calendar.setTimeInMillis( cal.getTimeInMillis() );
      MolokoDateUtils.clearTime( this.calendar );
      
      this.calField = calField;
      this.type = type;
      this.format = new SimpleDateFormat( dateFormat, Locale.getDefault() );
      this.format.setTimeZone( calendar.getTimeZone() );
      
      if ( type == TYPE_SHOW_WEEKDAY )
      {
         this.weekDayFormat = new SimpleDateFormat( "EE", Locale.getDefault() );
         this.weekDayFormat.setTimeZone( calendar.getTimeZone() );
      }
      else
         this.weekDayFormat = null;
      
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      
      this.min = calendar.getActualMinimum( calField );
      this.max = calendar.getActualMaximum( calField );
      this.flags = flags;
      
      this.valueColor = context.getResources()
                               .getColor( R.color.app_dlg_due_picker_value );
      this.todayColor = context.getResources()
                               .getColor( R.color.app_dlg_due_picker_today );
   }
   


   public View getItem( int index, View convertView, ViewGroup parent )
   {
      View view = convertView;
      
      if ( view == null )
      {
         switch ( type )
         {
            case TYPE_DEFAULT:
               view = inflater.inflate( R.layout.due_picker_dialog_type_default,
                                        parent,
                                        false );
               break;
            case TYPE_SHOW_WEEKDAY:
               view = inflater.inflate( R.layout.due_picker_dialog_type_weekday,
                                        parent,
                                        false );
               break;
            default :
               break;
         }
      }
      
      if ( view != null )
      {
         if ( calField != Calendar.DAY_OF_MONTH )
            calendar.set( calField, index );
         else
            calendar.set( calField, index + 1 );
         
         final Date date = calendar.getTime();
         
         final TextView value = (TextView) view.findViewById( R.id.due_dlg_value );
         value.setText( format.format( date ) );
         
         int color = valueColor;
         
         if ( ( flags & FLAG_MARK_TODAY ) == FLAG_MARK_TODAY
            && MolokoDateUtils.isToday( date.getTime() ) )
            color = todayColor;
         
         value.setTextColor( color );
         
         switch ( type )
         {
            case TYPE_SHOW_WEEKDAY:
               final TextView weekday = (TextView) view.findViewById( R.id.due_dlg_weekday );
               weekday.setText( weekDayFormat.format( date ) );
               break;
            default :
               break;
         }
      }
      
      return view;
   }
   


   public int getItemsCount()
   {
      return ( max - min ) + 1;
   }
}
