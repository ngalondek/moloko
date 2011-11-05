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

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.MolokoDateUtils.EstimateStruct;


public class EstimatePickerDialog extends AbstractPickerDialog
{
   public final static int UNIT_DAY = 0;
   
   public final static int UNIT_HOUR = 1;
   
   public final static int UNIT_MINUTE = 2;
   
   private final Context context;
   
   private AlertDialog impl;
   
   private WheelView numberWheel;
   
   private WheelView unitWheel;
   
   

   public EstimatePickerDialog( Activity activity )
   {
      this( activity, -1 );
   }
   


   public EstimatePickerDialog( Activity activity, long initial )
   {
      this.context = activity;
      
      final EstimateStruct estimateStruct = MolokoDateUtils.parseEstimated( initial );
      
      int value = 1;
      int unit = UNIT_DAY;
      
      if ( estimateStruct.days > 0 )
      {
         value = estimateStruct.days;
         unit = UNIT_DAY;
      }
      
      else if ( estimateStruct.hours > 0 )
      {
         value = estimateStruct.hours;
         unit = UNIT_HOUR;
      }
      
      else if ( estimateStruct.minutes > 0 )
      {
         value = estimateStruct.minutes;
         unit = UNIT_MINUTE;
      }
      
      init( activity, value, unit );
   }
   


   private void init( final Activity activity, int initialValue, int unit )
   {
      if ( initialValue == 0 )
         initialValue = 1;
      
      final LayoutInflater inflater = LayoutInflater.from( activity );
      final View view = inflater.inflate( R.layout.estimate_picker_dialog, null );
      
      numberWheel = (WheelView) view.findViewById( R.id.estimate_dlg_number_wheel );
      numberWheel.setViewAdapter( new NumericWheelAdapter( activity, 1, 999 ) );
      numberWheel.setCurrentItem( initialValue - 1 );
      
      unitWheel = (WheelView) view.findViewById( R.id.estimate_dlg_unit_wheel );
      setUnits( initialValue );
      unitWheel.setCurrentItem( unit );
      
      // Connect this as last otherwise it will be called when setting
      // the initial value
      numberWheel.addScrollingListener( new OnWheelScrollListener()
      {
         public void onScrollingStarted( WheelView wheel )
         {
         }
         


         public void onScrollingFinished( WheelView wheel )
         {
            // 0-based
            // Set the right texts if the value changes. E.g. 1 day - 2 days
            setUnits( wheel.getCurrentItem() + 1 );
         }
      } );
      
      this.impl = new AlertDialog.Builder( activity ).setIcon( R.drawable.ic_dialog_thumb )
                                                     .setTitle( R.string.dlg_estimate_picker_title )
                                                     .setView( view )
                                                     .setPositiveButton( R.string.btn_ok,
                                                                         new OnClickListener()
                                                                         {
                                                                            public void onClick( DialogInterface dialog,
                                                                                                 int which )
                                                                            {
                                                                               notifyOnDialogCloseListener( CloseReason.OK,
                                                                                                            getMillis() );
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
      this.impl.setOwnerActivity( activity );
   }
   


   @Override
   public void show()
   {
      impl.show();
   }
   


   @Override
   public void dismiss()
   {
      impl.dismiss();
   }
   


   public int getValue()
   {
      return numberWheel.getCurrentItem() + 1;
   }
   


   public int getUnit()
   {
      return unitWheel.getCurrentItem();
   }
   


   public long getMillis()
   {
      long millis = 1000 * getValue(); // s -> ms
      
      final int unit = getUnit();
      
      switch ( unit )
      {
         case UNIT_DAY:
            millis = millis * 3600 * 24;
            break;
         case UNIT_HOUR:
            millis = millis * 3600;
            break;
         case UNIT_MINUTE:
            millis = millis * 60;
            break;
         default :
            return -1;
      }
      
      return millis;
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
