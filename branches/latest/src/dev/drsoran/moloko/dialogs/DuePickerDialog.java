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
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.util.MolokoDateUtils;


public class DuePickerDialog extends AbstractPickerDialog
{
   private AlertDialog impl;
   
   private WheelView dateDayWheel;
   
   private WheelView dateMonthWheel;
   
   private WheelView dateYearWheel;
   
   private WheelView timeHourWheel;
   
   private WheelView timeMinuteWheel;
   
   private WheelView timeAmPmWheel;
   
   private final Calendar calendar;
   
   private int dateFormat;
   
   

   public DuePickerDialog( Context context )
   {
      this( context, System.currentTimeMillis(), false );
   }
   


   public DuePickerDialog( Context context, long initial, boolean hasDueTime )
   {
      this.calendar = Calendar.getInstance( MolokoApp.getSettings()
                                                     .getTimezone() );
      this.calendar.setTimeInMillis( initial != -1 ? initial
                                                  : System.currentTimeMillis() );
      
      if ( !hasDueTime )
         MolokoDateUtils.clearTime( calendar );
      
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
      
      dateYearWheel = (WheelView) view.findViewById( R.id.due_dlg_year_wheel );
      timeHourWheel = (WheelView) view.findViewById( R.id.due_dlg_time_wheel_hour );
      timeMinuteWheel = (WheelView) view.findViewById( R.id.due_dlg_time_wheel_minute );
      timeAmPmWheel = (WheelView) view.findViewById( R.id.due_dlg_time_wheel_am_pm );
      
      initDaysWheel( context );
      initMonthsWheel( context );
      initYearsWheel( context );
      initHourWheel( context );
      initMinuteWheel( context );
      initAmPmWheel( context );
      
      dateMonthWheel.addScrollingListener( new OnWheelScrollListener()
      {
         public void onScrollingStarted( WheelView wheel )
         {
         }
         


         public void onScrollingFinished( WheelView wheel )
         {
            getCalendar();
            initDaysWheel( context );
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
                                                                              notifyOnDialogCloseListener( CloseReason.OK,
                                                                                                           getCalendar().getTimeInMillis(),
                                                                                                           Boolean.FALSE );
                                                                           }
                                                                        } )
                                                    .setNegativeButton( R.string.btn_cancel,
                                                                        new OnClickListener()
                                                                        {
                                                                           public void onClick( DialogInterface dialog,
                                                                                                int which )
                                                                           {
                                                                              notifyOnDialogCloseListener( CloseReason.CANCELED,
                                                                                                           null );
                                                                           }
                                                                        } )
                                                    .create();
   }
   


   @Override
   public void show()
   {
      impl.show();
   }
   


   public Calendar getCalendar()
   {
      // First set the calendar to the first. Otherwise we get a wrap-around
      // if we set the month and the day is beyond the maximum.
      calendar.set( Calendar.DAY_OF_MONTH, 1 );
      calendar.set( Calendar.MONTH, dateMonthWheel.getCurrentItem() );
      calendar.set( Calendar.YEAR, dateYearWheel.getCurrentItem() + 1 );
      
      int day = dateDayWheel.getCurrentItem() + 1;
      
      if ( calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) < day )
         day = calendar.getActualMaximum( Calendar.DAY_OF_MONTH );
      
      calendar.set( Calendar.DAY_OF_MONTH, day );
      
      return calendar;
   }
   


   private void initDaysWheel( Context context )
   {
      dateDayWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                   calendar,
                                                                   Calendar.DAY_OF_MONTH,
                                                                   "d",
                                                                   DateFormatWheelTextAdapter.TYPE_SHOW_WEEKDAY,
                                                                   DateFormatWheelTextAdapter.FLAG_MARK_TODAY ) );
      dateDayWheel.setCurrentItem( calendar.get( Calendar.DAY_OF_MONTH ) - 1 );
   }
   


   private void initMonthsWheel( Context context )
   {
      dateMonthWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                     calendar,
                                                                     Calendar.MONTH,
                                                                     "MMM",
                                                                     DateFormatWheelTextAdapter.TYPE_DEFAULT,
                                                                     0 ) );
      dateMonthWheel.setCurrentItem( calendar.get( Calendar.MONTH ) );
   }
   


   private void initYearsWheel( Context context )
   {
      dateYearWheel.setViewAdapter( new NumericWheelAdapter( context,
                                                             calendar.getMinimum( Calendar.YEAR ),
                                                             calendar.getMaximum( Calendar.YEAR ) ) );
      dateYearWheel.setCurrentItem( calendar.get( Calendar.YEAR ) - 1 );
   }
   


   private void initHourWheel( Context context )
   {
      if ( MolokoApp.getSettings().getTimeformat() == Settings.TIMEFORMAT_24 )
      {
         timeHourWheel.setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                    calendar,
                                                                    DueTimeWheelTextAdapter.TYPE_HOUR_OF_DAY ) );
         timeHourWheel.setCurrentItem( calendar.get( Calendar.HOUR_OF_DAY ) - 1 );
      }
      else
      {
         timeHourWheel.setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                    calendar,
                                                                    DueTimeWheelTextAdapter.TYPE_HOUR ) );
         timeHourWheel.setCurrentItem( calendar.get( Calendar.HOUR ) - 1 );
      }
   }
   


   private void initMinuteWheel( Context context )
   {
      timeMinuteWheel.setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                   calendar,
                                                                   DueTimeWheelTextAdapter.TYPE_MINUTE ) );
      timeMinuteWheel.setCurrentItem( calendar.get( Calendar.MINUTE ) - 1 );
   }
   


   private void initAmPmWheel( Context context )
   {
      if ( MolokoApp.getSettings().getTimeformat() == Settings.TIMEFORMAT_12 )
      {
         timeAmPmWheel.setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                    calendar,
                                                                    DueTimeWheelTextAdapter.TYPE_AM_PM ) );
         timeAmPmWheel.setCurrentItem( calendar.get( Calendar.AM_PM ) - 1 );
      }
      else
      {
         timeAmPmWheel.setVisibility( View.GONE );
      }
   }
}
