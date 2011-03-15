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

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


public class DuePickerDialog
{
   private final EditText widget;
   
   private AlertDialog impl;
   
   private WheelView dateDayWheel;
   
   private WheelView dateMonthWheel;
   
   private WheelView dateYearWheel;
   
   private WheelView wheelWithWeekdays;
   
   private Calendar calendar;
   
   private int dateFormat;
   
   

   public DuePickerDialog( Context context, EditText widget )
   {
      this.widget = widget;
      
      if ( widget != null )
         calendar = RtmDateTimeParsing.parseDateTimeSpec( widget.getText()
                                                                .toString() );
      
      if ( calendar == null )
      {
         calendar = DateParser.getCalendar();
         calendar.setTimeInMillis( System.currentTimeMillis() );
      }
      
      init( context );
   }
   


   private void init( final Context context )
   {
      final LayoutInflater inflater = LayoutInflater.from( context );
      final View view = inflater.inflate( R.layout.due_picker_dialog, null );
      
      final Settings settings = MolokoApp.getSettings();
      
      dateFormat = settings.getDateformat();
      
      if ( dateFormat == Settings.DATEFORMAT_EU )
      {
         dateDayWheel = (WheelView) view.findViewById( R.id.due_dlg_date_wheel_1 );
         dateMonthWheel = (WheelView) view.findViewById( R.id.due_dlg_date_wheel_2 );
      }
      else
      {
         dateDayWheel = (WheelView) view.findViewById( R.id.due_dlg_date_wheel_2 );
         dateMonthWheel = (WheelView) view.findViewById( R.id.due_dlg_date_wheel_1 );
      }
      
      initDaysWheel( context );
      initMonthsWheel( context );
      
      dateYearWheel = (WheelView) view.findViewById( R.id.due_dlg_year_wheel );
      dateYearWheel.setViewAdapter( new NumericWheelAdapter( context,
                                                             calendar.getMinimum( Calendar.YEAR ),
                                                             calendar.getMaximum( Calendar.YEAR ) ) );
      dateYearWheel.setCurrentItem( calendar.get( Calendar.YEAR ) - 1 );
      
      dateMonthWheel.addScrollingListener( new OnWheelScrollListener()
      {
         public void onScrollingStarted( WheelView wheel )
         {
         }
         


         public void onScrollingFinished( WheelView wheel )
         {
            getCalendar();
            initDaysWheel( context );
            
            if ( dateDayWheel != wheelWithWeekdays )
               initMonthsWheel( context );
         }
      } );
      
      dateYearWheel.addScrollingListener( new OnWheelScrollListener()
      {
         public void onScrollingStarted( WheelView wheel )
         {
         }
         


         public void onScrollingFinished( WheelView wheel )
         {
            getCalendar();
            initDaysWheel( context );
            
            if ( dateDayWheel != wheelWithWeekdays )
               initMonthsWheel( context );
         }
      } );
      
      this.impl = new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_time )
                                                    .setTitle( R.string.dlg_due_picker_title )
                                                    .setView( view )
                                                    .setPositiveButton( R.string.btn_ok,
                                                                        new OnClickListener()
                                                                        {
                                                                           public void onClick( DialogInterface dialog,
                                                                                                int which )
                                                                           {
                                                                              if ( widget != null )
                                                                              {
                                                                                 if ( calendar.isSet( Calendar.HOUR_OF_DAY ) )
                                                                                 {
                                                                                    widget.setText( MolokoDateUtils.formatDateTime( calendar.getTimeInMillis(),
                                                                                                                                    MolokoDateUtils.FORMAT_NUMERIC
                                                                                                                                       | MolokoDateUtils.FORMAT_WITH_YEAR
                                                                                                                                       | MolokoDateUtils.FORMAT_SHOW_WEEKDAY
                                                                                                                                       | MolokoDateUtils.FORMAT_ABR_ALL ) );
                                                                                 }
                                                                                 else
                                                                                 {
                                                                                    widget.setText( MolokoDateUtils.formatDate( calendar.getTimeInMillis(),
                                                                                                                                MolokoDateUtils.FORMAT_NUMERIC
                                                                                                                                   | MolokoDateUtils.FORMAT_WITH_YEAR
                                                                                                                                   | MolokoDateUtils.FORMAT_SHOW_WEEKDAY
                                                                                                                                   | MolokoDateUtils.FORMAT_ABR_ALL ) );
                                                                                 }
                                                                              }
                                                                           }
                                                                        } )
                                                    .setNegativeButton( R.string.btn_cancel,
                                                                        null )
                                                    .create();
   }
   


   public void show()
   {
      impl.show();
   }
   


   public Calendar getCalendar()
   {
      calendar.set( Calendar.DAY_OF_MONTH, dateDayWheel.getCurrentItem() + 1 );
      calendar.set( Calendar.MONTH, dateMonthWheel.getCurrentItem() );
      calendar.set( Calendar.YEAR, dateYearWheel.getCurrentItem() + 1 );
      
      return calendar;
   }
   


   private void initDaysWheel( Context context )
   {
      dateDayWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                   calendar,
                                                                   Calendar.DAY_OF_MONTH,
                                                                   "d",
                                                                   dateFormat == Settings.DATEFORMAT_EU
                                                                                                       ? DateFormatWheelTextAdapter.TYPE_SHOW_WEEKDAY
                                                                                                       : DateFormatWheelTextAdapter.TYPE_DEFAULT,
                                                                   DateFormatWheelTextAdapter.FLAG_MARK_TODAY ) );
      dateDayWheel.setCurrentItem( calendar.get( Calendar.DAY_OF_MONTH ) - 1 );
      wheelWithWeekdays = dateDayWheel;
   }
   


   private void initMonthsWheel( Context context )
   {
      dateMonthWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                     calendar,
                                                                     Calendar.MONTH,
                                                                     "MMM",
                                                                     dateFormat == Settings.DATEFORMAT_US
                                                                                                         ? DateFormatWheelTextAdapter.TYPE_SHOW_WEEKDAY
                                                                                                         : DateFormatWheelTextAdapter.TYPE_DEFAULT,
                                                                     0 ) );
      dateMonthWheel.setCurrentItem( calendar.get( Calendar.MONTH ) );
      wheelWithWeekdays = dateDayWheel;
   }
}
