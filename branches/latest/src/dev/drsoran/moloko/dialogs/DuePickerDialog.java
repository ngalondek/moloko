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

import kankan.wheel.widget.OnWheelChangedListener;
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
   
   private final Context context;
   
   private final EditText widget;
   
   private AlertDialog impl;
   
   private WheelView dateDayWheel;
   
   private WheelView dateMonthWheel;
   
   private WheelView dateYearWheel;
   
   private Calendar calendar;
   
   

   public DuePickerDialog( Context context, EditText widget )
   {
      this.context = context;
      this.widget = widget;
      
      if ( widget != null )
         calendar = RtmDateTimeParsing.parseDateTimeSpec( widget.getText()
                                                                .toString() );
      
      if ( calendar == null )
         calendar = DateParser.getCalendar();
      
      init( context );
   }
   


   private void init( final Context context )
   {
      final LayoutInflater inflater = LayoutInflater.from( context );
      final View view = inflater.inflate( R.layout.due_picker_dialog, null );
      
      final Settings settings = MolokoApp.getSettings();
      
      if ( settings.getDateformat() == Settings.DATEFORMAT_EU )
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
      dateYearWheel.setViewAdapter( new NumericWheelAdapter( context,
                                                             calendar.getMinimum( Calendar.YEAR ),
                                                             calendar.getMaximum( Calendar.YEAR ) ) );
      
      dateMonthWheel.addChangingListener( new OnWheelChangedListener()
      {
         public void onChanged( WheelView wheel, int oldValue, int newValue )
         {
         }
      } );
      
      dateYearWheel.addChangingListener( new OnWheelChangedListener()
      {
         public void onChanged( WheelView wheel, int oldValue, int newValue )
         {
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
      return calendar;
   }
}
