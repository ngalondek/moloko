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

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.MolokoDateUtils.EstimateStruct;


public class EstimatePickerDialog
{
   public final static int UNIT_DAY = 0;
   
   public final static int UNIT_HOUR = 1;
   
   public final static int UNIT_MINUTE = 2;
   
   private final Context context;
   
   private AlertDialog impl;
   
   private WheelView numberWheel;
   
   private WheelView unitWheel;
   
   

   public EstimatePickerDialog( Context context, long millis )
   {
      this.context = context;
      
      final EstimateStruct estimateStruct = MolokoDateUtils.parseEstimated( millis );
      
      int value = 1;
      int unit = 0;
      
      if ( estimateStruct.days > 0 )
      {
         value = estimateStruct.days;
         unit = UNIT_DAY;
      }
      
      if ( estimateStruct.hours > 0 )
      {
         value = estimateStruct.hours;
         unit = UNIT_HOUR;
      }
      
      if ( estimateStruct.minutes > 0 )
      {
         value = estimateStruct.minutes;
         unit = UNIT_MINUTE;
      }
      
      init( context, value, unit );
   }
   


   public EstimatePickerDialog( Context context, int initialValue, int unit )
   {
      this.context = context;
      
      init( context, initialValue, unit );
   }
   


   private void init( Context context, int initialValue, int unit )
   {
      final LayoutInflater inflater = LayoutInflater.from( context );
      final View view = inflater.inflate( R.layout.estimate_picker_dialog, null );
      
      numberWheel = (WheelView) view.findViewById( R.id.estimate_dlg_number_wheel );
      numberWheel.setViewAdapter( new NumericWheelAdapter( context, 1, 999 ) );
      numberWheel.setCyclic( true );
      numberWheel.setCurrentItem( initialValue > 0 ? --initialValue
                                                  : initialValue );
      
      unitWheel = (WheelView) view.findViewById( R.id.estimate_dlg_unit_wheel );
      setUnits( initialValue );
      unitWheel.setCurrentItem( unit );
      
      // Connect this as last otherwise it will be called when setting
      // the initial value
      numberWheel.addChangingListener( new OnWheelChangedListener()
      {
         public void onChanged( WheelView wheel, int oldValue, int newValue )
         {
            // 0-based
            // Set the right texts if the value changes. E.g. 1 day - 2 days
            if ( ( oldValue == 0 && newValue > 0 )
               || ( oldValue > 0 && newValue == 0 ) )
               setUnits( ++newValue );
         }
      } );
      
      // TODO: Set thumb icon
      this.impl = new AlertDialog.Builder( context ).setTitle( R.string.dlg_estimate_picker_title )
                                                    .setView( view )
                                                    .setPositiveButton( R.string.btn_ok,
                                                                        null )
                                                    .setNegativeButton( R.string.btn_cancel,
                                                                        null )
                                                    .create();
   }
   


   public void show()
   {
      impl.show();
   }
   


   private void setUnits( int pos )
   {
      final Resources res = context.getResources();
      
      unitWheel.setViewAdapter( new ArrayWheelAdapter< String >( context,
                                                                 new String[]
                                                                 {
                                                                  res.getQuantityText( R.plurals.g_day,
                                                                                       pos )
                                                                     .toString(),
                                                                  res.getQuantityText( R.plurals.g_hour,
                                                                                       pos )
                                                                     .toString(),
                                                                  res.getQuantityText( R.plurals.g_minute,
                                                                                       pos )
                                                                     .toString() } ) );
   }
}
