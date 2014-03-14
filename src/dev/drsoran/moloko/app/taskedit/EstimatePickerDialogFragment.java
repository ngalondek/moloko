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

package dev.drsoran.moloko.app.taskedit;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.TimeStruct;


public class EstimatePickerDialogFragment extends AbstractPickerDialogFragment
{
   private final static int UNIT_DAY = 0;
   
   private final static int UNIT_HOUR = 1;
   
   private final static int UNIT_MINUTE = 2;
   
   @InstanceState( key = TaskColumns.ESTIMATE_MILLIS )
   private long estimateMillis;
   
   private WheelView numberWheel;
   
   private WheelView unitWheel;
   
   
   
   public final static EstimatePickerDialogFragment show( FragmentActivity activity,
                                                          long estimateMillis )
   {
      final Bundle config = new Bundle( 1 );
      config.putLong( TaskColumns.ESTIMATE_MILLIS, estimateMillis );
      
      return show( activity, config );
   }
   
   
   
   public final static EstimatePickerDialogFragment show( FragmentActivity activity,
                                                          Bundle config )
   {
      final EstimatePickerDialogFragment frag = newInstance( config );
      UiUtils.showDialogFragment( activity,
                                  frag,
                                  EstimatePickerDialogFragment.class.getName() );
      return frag;
   }
   
   
   
   public final static EstimatePickerDialogFragment newInstance( Bundle config )
   {
      final EstimatePickerDialogFragment frag = new EstimatePickerDialogFragment();
      
      frag.setArguments( config );
      
      return frag;
   }
   
   
   
   public EstimatePickerDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           EstimatePickerDialogFragment.class );
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
      {
         configure( savedInstanceState );
      }
      
      final Pair< Integer, Integer > valueAndUnit = getValueAndUnitFromMillis();
      final View content = initWheels( valueAndUnit.first, valueAndUnit.second );
      
      final Dialog dialog = createDialogImpl( content );
      return dialog;
   }
   
   
   
   @Override
   protected void notifyValueChanged( IValueChangedListener listener )
   {
      listener.onValueChanged( getEstimateMillis(), long.class );
   }
   
   
   
   private Pair< Integer, Integer > getValueAndUnitFromMillis()
   {
      final TimeStruct estimateStruct = MolokoDateUtils.getTimeStruct( estimateMillis );
      
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
      
      return Pair.create( Integer.valueOf( value ), Integer.valueOf( unit ) );
   }
   
   
   
   private View initWheels( int initialValue, int unit )
   {
      if ( initialValue == 0 )
         initialValue = 1;
      
      final Activity activity = getSherlockActivity();
      final LayoutInflater inflater = LayoutInflater.from( activity );
      final View view = inflater.inflate( R.layout.estimate_picker_dialog, null );
      
      numberWheel = (WheelView) view.findViewById( R.id.estimate_dlg_number_wheel );
      numberWheel.setViewAdapter( new NumericWheelAdapter( activity, 1, 365 ) );
      numberWheel.setCurrentItem( initialValue - 1 );
      
      unitWheel = (WheelView) view.findViewById( R.id.estimate_dlg_unit_wheel );
      setUnits( initialValue );
      unitWheel.setCurrentItem( unit );
      
      // Connect this as last otherwise it will be called when setting
      // the initial value
      unitWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            calculateMillis();
         }
      } );
      
      numberWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            calculateMillis();
            // 0-based
            // Set the right texts if the value changes. E.g. 1 day - 2 days
            setUnits( wheel.getCurrentItem() + 1 );
         }
      } );
      
      return view;
   }
   
   
   
   private Dialog createDialogImpl( View contentView )
   {
      final Activity activity = getSherlockActivity();
      
      return new AlertDialog.Builder( activity ).setIcon( R.drawable.ic_dialog_thumb )
                                                .setTitle( R.string.dlg_estimate_picker_title )
                                                .setView( contentView )
                                                .setPositiveButton( R.string.btn_ok,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          EstimatePickerDialogFragment.this.notifiyDialogClosedOk();
                                                                       }
                                                                    } )
                                                .setNegativeButton( R.string.btn_cancel,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          EstimatePickerDialogFragment.this.notifiyDialogClosedCancel();
                                                                       }
                                                                    } )
                                                .create();
   }
   
   
   
   public int getValue()
   {
      return numberWheel.getCurrentItem() + 1;
   }
   
   
   
   public int getUnit()
   {
      return unitWheel.getCurrentItem();
   }
   
   
   
   public long getEstimateMillis()
   {
      return estimateMillis;
   }
   
   
   
   private void calculateMillis()
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
            throw new IllegalStateException( "Unexpected wheel unit " + unit );
      }
      
      estimateMillis = millis;
   }
   
   
   
   private void setUnits( int pos )
   {
      final Context context = getSherlockActivity();
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
