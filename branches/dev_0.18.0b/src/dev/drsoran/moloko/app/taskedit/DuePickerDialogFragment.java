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

import java.util.Calendar;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.format.MolokoDateFormatter;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.adapters.DateFormatWheelTextAdapter;
import dev.drsoran.moloko.ui.adapters.DueTimeWheelTextAdapter;
import dev.drsoran.moloko.ui.state.InstanceState;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DuePickerDialogFragment extends AbstractPickerDialogFragment
{
   public final static class Config
   {
      public final static String DUE_MILLIS = "due_millis";
      
      public final static String HAS_DUE_TIME = "has_due_time";
   }
   
   @InstanceState( key = Config.DUE_MILLIS )
   private long dueMillis = System.currentTimeMillis();
   
   @InstanceState( key = Config.HAS_DUE_TIME )
   private boolean hasDueTime;
   
   private boolean is24hTimeFormat;
   
   private WheelView dateDayWheel;
   
   private WheelView dateMonthWheel;
   
   private WheelView dateYearWheel;
   
   private TimeWheelGroup timeWheelGroup;
   
   
   
   public final static void show( FragmentActivity activity,
                                  long dueMillis,
                                  boolean hasDueTime )
   {
      final Bundle config = new Bundle( 2 );
      config.putLong( Config.DUE_MILLIS, dueMillis );
      config.putBoolean( Config.HAS_DUE_TIME, hasDueTime );
      
      show( activity, config );
   }
   
   
   
   public final static void show( FragmentActivity activity, Bundle config )
   {
      final DuePickerDialogFragment frag = newInstance( config );
      UiUtils.showDialogFragment( activity,
                                  frag,
                                  DuePickerDialogFragment.class.getName() );
   }
   
   
   
   public final static DuePickerDialogFragment newInstance( Bundle config )
   {
      final DuePickerDialogFragment frag = new DuePickerDialogFragment();
      
      frag.setArguments( config );
      
      return frag;
   }
   
   
   
   public DuePickerDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this, DuePickerDialogFragment.class );
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      initTimeFormat();
      
      final View content = initWheels();
      final Dialog dialog = createDialogImpl( content );
      
      return dialog;
   }
   
   
   
   private void initTimeFormat()
   {
      is24hTimeFormat = MolokoApp.getSettings( getSherlockActivity() )
                                 .is24hTimeformat();
   }
   
   
   
   private View initWheels()
   {
      final Activity activity = getSherlockActivity();
      final LayoutInflater inflater = LayoutInflater.from( activity );
      final View view = inflater.inflate( R.layout.due_picker_dialog, null );
      
      final char[] dateFormatOrder = MolokoDateFormatter.getDateFormatOrder( activity );
      assignWheelsByDateFormat( view, dateFormatOrder );
      
      initDaysWheel( activity );
      initMonthsWheel( activity );
      initYearsWheel( activity );
      
      timeWheelGroup = new TimeWheelGroup( activity, view, 0, hasDueTime );
      
      dateDayWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            updateDueTimeMillis();
         }
      } );
      
      dateMonthWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            updateDueTimeMillis();
            initDaysWheel( activity );
         }
      } );
      
      dateYearWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            updateDueTimeMillis();
            initDaysWheel( activity );
         }
      } );
      
      return view;
   }
   
   
   
   private Dialog createDialogImpl( View content )
   {
      final Activity activity = getSherlockActivity();
      
      return new AlertDialog.Builder( activity ).setIcon( R.drawable.ic_dialog_time )
                                                .setTitle( R.string.dlg_due_picker_title )
                                                .setView( content )
                                                .setPositiveButton( R.string.btn_ok,
                                                                    new OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          DuePickerDialogFragment.this.notifiyDialogClosedOk();
                                                                       }
                                                                    } )
                                                .setNegativeButton( R.string.btn_cancel,
                                                                    new OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          DuePickerDialogFragment.this.notifiyDialogClosedCancel();
                                                                       }
                                                                    } )
                                                .create();
   }
   
   
   
   public MolokoCalendar getCalendar()
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setTimeInMillis( dueMillis );
      cal.setHasTime( hasDueTime );
      
      return cal;
   }
   
   
   
   public boolean hasTime()
   {
      return hasDueTime;
   }
   
   
   
   private void updateDueTimeMillis()
   {
      // First set the calendar to the first. Otherwise we get a wrap-around
      // if we set the month and the day is beyond the maximum.
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      cal.set( Calendar.DAY_OF_MONTH, 1 );
      cal.set( Calendar.MONTH, dateMonthWheel.getCurrentItem() );
      cal.set( Calendar.YEAR, dateYearWheel.getCurrentItem() );
      
      int day = dateDayWheel.getCurrentItem() + 1;
      
      if ( cal.getActualMaximum( Calendar.DAY_OF_MONTH ) < day )
         day = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
      
      cal.set( Calendar.DAY_OF_MONTH, day );
      timeWheelGroup.putTime( cal );
      
      dueMillis = cal.getTimeInMillis();
   }
   
   
   
   private void updateHasDueTime()
   {
      hasDueTime = timeWheelGroup.hasTime;
   }
   
   
   
   private void assignWheelsByDateFormat( View content, char[] dateFormatOrder )
   {
      final int[] genericWheels = new int[]
      { R.id.due_dlg_date_wheel_1, R.id.due_dlg_date_wheel_2,
       R.id.due_dlg_date_wheel_3 };
      
      for ( int i = 0; i < genericWheels.length || i < dateFormatOrder.length; i++ )
      {
         final char symbol = dateFormatOrder[ i ];
         switch ( symbol )
         {
            case DateFormat.DATE:
               dateDayWheel = (WheelView) content.findViewById( genericWheels[ i ] );
               break;
            case DateFormat.MONTH:
               dateMonthWheel = (WheelView) content.findViewById( genericWheels[ i ] );
               break;
            case DateFormat.YEAR:
               dateYearWheel = (WheelView) content.findViewById( genericWheels[ i ] );
               break;
            default :
               break;
         
         }
      }
   }
   
   
   
   private void initDaysWheel( Context context )
   {
      final MolokoCalendar cal = getCalendar();
      dateDayWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                   cal,
                                                                   Calendar.DAY_OF_MONTH,
                                                                   "d",
                                                                   DateFormatWheelTextAdapter.TYPE_SHOW_WEEKDAY,
                                                                   DateFormatWheelTextAdapter.FLAG_MARK_TODAY ) );
      dateDayWheel.setCurrentItem( cal.get( Calendar.DAY_OF_MONTH ) - 1 );
   }
   
   
   
   private void initMonthsWheel( Context context )
   {
      final MolokoCalendar cal = getCalendar();
      dateMonthWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                     cal,
                                                                     Calendar.MONTH,
                                                                     "MMM",
                                                                     DateFormatWheelTextAdapter.TYPE_DEFAULT,
                                                                     0 ) );
      dateMonthWheel.setCurrentItem( cal.get( Calendar.MONTH ) );
   }
   
   
   
   private void initYearsWheel( Context context )
   {
      final MolokoCalendar cal = getCalendar();
      dateYearWheel.setViewAdapter( new DateFormatWheelTextAdapter( context,
                                                                    cal,
                                                                    Calendar.YEAR,
                                                                    "yyyy",
                                                                    DateFormatWheelTextAdapter.TYPE_DEFAULT,
                                                                    0 ) );
      dateYearWheel.setCurrentItem( cal.get( Calendar.YEAR ) );
   }
   
   
   private final class TimeWheelGroup implements OnWheelScrollListener
   {
      private final WheelView[] wheelViews = new WheelView[ 3 ];
      
      private final int offIndex;
      
      private final int[] lastNonOffIndex =
      { -1, -1, -1 };
      
      private final int[] lastIndex =
      { 0, 0, 0 };
      
      private boolean notifying;
      
      private boolean hasTime;
      
      
      
      public TimeWheelGroup( Context context, View view, int offIndex,
         boolean hasTime )
      {
         this.offIndex = offIndex;
         this.hasTime = hasTime;
         
         wheelViews[ 0 ] = (WheelView) view.findViewById( R.id.due_dlg_time_wheel_hour );
         wheelViews[ 1 ] = (WheelView) view.findViewById( R.id.due_dlg_time_wheel_minute );
         wheelViews[ 2 ] = (WheelView) view.findViewById( R.id.due_dlg_time_wheel_am_pm );
         
         final MolokoCalendar cal = getCalendar();
         
         initHourWheel( context, cal );
         initMinuteWheel( context, cal );
         initAmPmWheel( context, cal );
         
         setLastIndex( wheelViews[ 0 ] );
         setLastIndex( wheelViews[ 1 ] );
         
         wheelViews[ 0 ].addScrollingListener( this );
         wheelViews[ 1 ].addScrollingListener( this );
         
         if ( !is24hTimeFormat )
         {
            setLastIndex( wheelViews[ 2 ] );
            wheelViews[ 2 ].addScrollingListener( this );
         }
      }
      
      
      
      @Override
      public void onScrollingStarted( WheelView wheel )
      {
      }
      
      
      
      @Override
      public void onScrollingFinished( WheelView wheel )
      {
         if ( !notifying )
         {
            // a wheel has been put into off
            if ( wheel.getCurrentItem() == offIndex )
            {
               // put other wheels into off
               notifying = true;
               setOtherWheelsOff( wheel );
               notifying = false;
            }
            // a wheel has been put from off to on
            else if ( getLastIndex( wheel ) == offIndex )
            {
               // put other wheels into on
               notifying = true;
               restoreOtherWheels( wheel );
               notifying = false;
            }
         }
         
         setLastIndex( wheel );
         
         updateHasDueTime();
         updateDueTimeMillis();
      }
      
      
      
      public MolokoCalendar putTime( MolokoCalendar cal )
      {
         if ( !hasTime )
            cal.setHasTime( false );
         else
         {
            if ( is24hTimeFormat )
               cal.set( Calendar.HOUR_OF_DAY,
                        wheelViews[ 0 ].getCurrentItem() - 1 );
            else
               cal.set( Calendar.HOUR, wheelViews[ 0 ].getCurrentItem() - 1 );
            
            cal.set( Calendar.MINUTE, wheelViews[ 1 ].getCurrentItem() - 1 );
            
            if ( !is24hTimeFormat )
               cal.set( Calendar.AM_PM, wheelViews[ 2 ].getCurrentItem() - 1 );
            else
               cal.clear( Calendar.AM_PM );
            
            cal.set( Calendar.SECOND, 0 );
            cal.set( Calendar.MILLISECOND, 0 );
         }
         
         return cal;
      }
      
      
      
      private void initHourWheel( Context context, MolokoCalendar calendar )
      {
         if ( is24hTimeFormat )
         {
            wheelViews[ 0 ].setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                         calendar,
                                                                         DueTimeWheelTextAdapter.TYPE_HOUR_OF_DAY ) );
            if ( !hasTime || !calendar.hasTime() )
               wheelViews[ 0 ].setCurrentItem( offIndex );
            else
               wheelViews[ 0 ].setCurrentItem( calendar.get( Calendar.HOUR_OF_DAY ) + 1 );
         }
         else
         {
            wheelViews[ 0 ].setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                         calendar,
                                                                         DueTimeWheelTextAdapter.TYPE_HOUR ) );
            if ( !hasTime || !calendar.hasTime() )
               wheelViews[ 0 ].setCurrentItem( offIndex );
            else
               wheelViews[ 0 ].setCurrentItem( calendar.get( Calendar.HOUR ) + 1 );
         }
      }
      
      
      
      private void initMinuteWheel( Context context, MolokoCalendar calendar )
      {
         wheelViews[ 1 ].setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                      calendar,
                                                                      DueTimeWheelTextAdapter.TYPE_MINUTE ) );
         if ( !hasTime || !calendar.isSet( Calendar.MINUTE ) )
            wheelViews[ 1 ].setCurrentItem( offIndex );
         else
            wheelViews[ 1 ].setCurrentItem( calendar.get( Calendar.MINUTE ) + 1 );
      }
      
      
      
      private void initAmPmWheel( Context context, MolokoCalendar calendar )
      {
         if ( !is24hTimeFormat )
         {
            wheelViews[ 2 ].setViewAdapter( new DueTimeWheelTextAdapter( context,
                                                                         calendar,
                                                                         DueTimeWheelTextAdapter.TYPE_AM_PM ) );
            
            if ( !hasTime || !calendar.isSet( Calendar.AM_PM ) )
               wheelViews[ 2 ].setCurrentItem( offIndex );
            else
               wheelViews[ 2 ].setCurrentItem( calendar.get( Calendar.AM_PM ) + 1 );
         }
         else
         {
            wheelViews[ 2 ].setCurrentItem( offIndex );
            wheelViews[ 2 ].setVisibility( View.GONE );
         }
      }
      
      
      
      private void setOtherWheelsOff( WheelView wheel )
      {
         final int wheelIdx = getWheelIndex( wheel );
         
         for ( int i = 1; wheelIdx != -1 && i < wheelViews.length; i++ )
            wheelViews[ ( wheelIdx + i ) % wheelViews.length ].setCurrentItem( offIndex,
                                                                               true );
      }
      
      
      
      private void restoreOtherWheels( WheelView wheel )
      {
         final int wheelIdx = getWheelIndex( wheel );
         
         for ( int i = 1; wheelIdx != -1 && i < wheelViews.length; i++ )
         {
            final int idx = ( wheelIdx + i ) % wheelViews.length;
            final WheelView otherWheel = wheelViews[ idx ];
            
            if ( otherWheel.getVisibility() != View.GONE )
            {
               if ( lastNonOffIndex[ idx ] != -1 )
                  otherWheel.setCurrentItem( lastNonOffIndex[ idx ], true );
               else
                  otherWheel.setCurrentItem( offIndex + 1, true );
            }
         }
      }
      
      
      
      private int getWheelIndex( WheelView wheel )
      {
         for ( int i = 0; i < wheelViews.length; i++ )
            if ( wheelViews[ i ] == wheel )
               return i;
         
         return -1;
      }
      
      
      
      private void setLastIndex( WheelView wheel )
      {
         final int wheelIdx = getWheelIndex( wheel );
         
         if ( wheelIdx != -1 )
         {
            lastIndex[ wheelIdx ] = wheel.getCurrentItem();
            
            if ( lastIndex[ wheelIdx ] != offIndex )
            {
               lastNonOffIndex[ wheelIdx ] = lastIndex[ wheelIdx ];
               hasTime = true;
            }
            else
            {
               hasTime = false;
            }
         }
      }
      
      
      
      private int getLastIndex( WheelView wheel )
      {
         final int wheelIdx = getWheelIndex( wheel );
         
         if ( wheelIdx != -1 )
            return lastIndex[ wheelIdx ];
         else
            return -1;
      }
   }
}
