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

package dev.drsoran.moloko.fragments.dialogs;

import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.AbstractPickerDialogFragment;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;


public class RecurrPickerDialogFragment extends AbstractPickerDialogFragment
{
   public final static class Config
   {
      public final static String RECURR_PATTERN = "recurr_pattern";
      
      public final static String IS_EVERY_RECURR = "is_every_recurr";
   }
   
   private View container;
   
   private WheelView evAftWheel;
   
   private WheelView intervalWheel;
   
   private WheelView freqWheel;
   
   

   public final static void show( FragmentActivity activity,
                                  String pattern,
                                  boolean isEvery )
   {
      final Bundle config = new Bundle( 2 );
      config.putString( Config.RECURR_PATTERN, pattern );
      config.putBoolean( Config.IS_EVERY_RECURR, isEvery );
      
      show( activity, config );
   }
   


   public final static void show( FragmentActivity activity, Bundle config )
   {
      final RecurrPickerDialogFragment frag = newInstance( config );
      UIUtils.showDialogFragment( activity,
                                  frag,
                                  RecurrPickerDialogFragment.class.getName() );
   }
   


   public final static RecurrPickerDialogFragment newInstance( Bundle config )
   {
      final RecurrPickerDialogFragment frag = new RecurrPickerDialogFragment();
      
      frag.setArguments( config );
      
      return frag;
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      if ( configuration != null )
      {
         configuration.putString( Config.RECURR_PATTERN, getPatternString() );
         configuration.putBoolean( Config.IS_EVERY_RECURR, isEvery() );
      }
      
      super.onSaveInstanceState( outState );
   }
   


   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.RECURR_PATTERN ) )
         configuration.putString( Config.RECURR_PATTERN,
                                  config.getString( Config.RECURR_PATTERN ) );
      if ( config.containsKey( Config.IS_EVERY_RECURR ) )
         configuration.putBoolean( Config.IS_EVERY_RECURR,
                                   config.getBoolean( Config.IS_EVERY_RECURR ) );
   }
   


   @Override
   protected void putDefaultConfigurationTo( Bundle bundle )
   {
      super.putDefaultConfigurationTo( bundle );
      
      bundle.putString( Config.RECURR_PATTERN, Strings.EMPTY_STRING );
      bundle.putBoolean( Config.IS_EVERY_RECURR, false );
   }
   


   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      final FragmentActivity activity = getFragmentActivity();
      
      final LayoutInflater inflater = LayoutInflater.from( activity );
      container = inflater.inflate( R.layout.recurr_picker_dialog, null );
      
      initWheels();
      
      final Dialog dialog = createDialogImpl();
      return dialog;
   }
   


   public int getInterval()
   {
      return intervalWheel.getCurrentItem() + 1;
   }
   


   public int getFreqValue()
   {
      switch ( freqWheel.getCurrentItem() )
      {
         case 0:
            return RecurrencePatternParser.VAL_YEARLY;
         case 1:
            return RecurrencePatternParser.VAL_MONTHLY;
         case 2:
            return RecurrencePatternParser.VAL_WEEKLY;
         case 3:
            return RecurrencePatternParser.VAL_DAILY;
         default :
            return -1;
      }
   }
   


   public String getFreqValueAsString()
   {
      switch ( freqWheel.getCurrentItem() )
      {
         case 0:
            return RecurrencePatternParser.VAL_YEARLY_LIT;
         case 1:
            return RecurrencePatternParser.VAL_MONTHLY_LIT;
         case 2:
            return RecurrencePatternParser.VAL_WEEKLY_LIT;
         case 3:
            return RecurrencePatternParser.VAL_DAILY_LIT;
         default :
            return Strings.EMPTY_STRING;
      }
   }
   


   public boolean isEvery()
   {
      return evAftWheel.getCurrentItem() == 0;
   }
   


   public String getPatternString()
   {
      final StringBuilder sb = new StringBuilder();
      
      sb.append( RecurrencePatternParser.OP_FREQ_LIT )
        .append( "=" )
        .append( getFreqValueAsString() );
      sb.append( RecurrencePatternParser.OPERATOR_SEP );
      sb.append( RecurrencePatternParser.OP_INTERVAL_LIT )
        .append( "=" )
        .append( getInterval() );
      
      return RecurrenceParsing.ensureRecurrencePatternOrder( sb.toString() );
   }
   


   public Pair< String, Boolean > getPattern()
   {
      return Pair.create( getPatternString(), isEvery() );
   }
   


   public String getSentence()
   {
      return RecurrenceParsing.parseRecurrencePattern( getFragmentActivity(),
                                                       getPatternString(),
                                                       isEvery() );
   }
   


   private void initWheels()
   {
      final String pattern = configuration.getString( Config.RECURR_PATTERN );
      final Map< Integer, List< Object >> elements = RecurrenceParsing.parseRecurrencePattern( pattern );
      
      // Every, After wheel
      {
         evAftWheel = (WheelView) container.findViewById( R.id.recurr_dlg_ev_aft_wheel );
         
         final boolean isEvery = configuration.getBoolean( Config.IS_EVERY_RECURR );
         initEvAftWheel( isEvery );
      }
      
      // Interval
      {
         intervalWheel = (WheelView) container.findViewById( R.id.recurr_dlg_interval_wheel );
         initIntervalWheel( getPatternElement( elements,
                                               RecurrencePatternParser.OP_INTERVAL,
                                               Integer.class ) );
      }
      
      // Frequency
      {
         freqWheel = (WheelView) container.findViewById( R.id.recurr_dlg_freq_wheel );
         initFreqWheel( elements );
      }
      
      intervalWheel.addScrollingListener( new OnWheelScrollListener()
      {
         public void onScrollingStarted( WheelView wheel )
         {
         }
         


         public void onScrollingFinished( WheelView wheel )
         {
            initFreqWheel( null );
         }
      } );
   }
   


   private void initEvAftWheel( boolean isEvery )
   {
      final Activity activity = getFragmentActivity();
      final Resources res = activity.getResources();
      
      evAftWheel.setViewAdapter( new ArrayWheelAdapter< String >( activity,
                                                                  new String[]
                                                                  {
                                                                   res.getString( R.string.dlg_recurr_picker_every ),
                                                                   res.getString( R.string.dlg_recurr_picker_after ) } ) );
      evAftWheel.setCurrentItem( isEvery ? 0 : 1 );
   }
   


   private void initIntervalWheel( Integer interval )
   {
      final Activity activity = getFragmentActivity();
      intervalWheel.setViewAdapter( new NumericWheelAdapter( activity, 1, 999 ) );
      
      if ( interval != null )
         intervalWheel.setCurrentItem( interval.intValue() - 1 );
      else
         intervalWheel.setCurrentItem( 0 );
   }
   


   private void initFreqWheel( Map< Integer, List< Object >> elements )
   {
      final Activity activity = getFragmentActivity();
      final Resources res = activity.getResources();
      final int interval = getInterval();
      
      freqWheel.setViewAdapter( new ArrayWheelAdapter< String >( activity,
                                                                 new String[]
                                                                 {
                                                                  res.getQuantityText( R.plurals.g_year,
                                                                                       interval )
                                                                     .toString(),
                                                                  res.getQuantityText( R.plurals.g_month,
                                                                                       interval )
                                                                     .toString(),
                                                                  res.getQuantityText( R.plurals.g_week,
                                                                                       interval )
                                                                     .toString(),
                                                                  res.getQuantityText( R.plurals.g_day,
                                                                                       interval )
                                                                     .toString() } ) );
      
      if ( elements != null )
      {
         Integer freq = getPatternElement( elements,
                                           RecurrencePatternParser.OP_FREQ,
                                           Integer.class );
         
         if ( freq != null )
         {
            switch ( freq.intValue() )
            {
               case RecurrencePatternParser.VAL_YEARLY:
                  freqWheel.setCurrentItem( 0 );
                  break;
               case RecurrencePatternParser.VAL_MONTHLY:
                  freqWheel.setCurrentItem( 1 );
                  break;
               case RecurrencePatternParser.VAL_WEEKLY:
                  freqWheel.setCurrentItem( 2 );
                  break;
               case RecurrencePatternParser.VAL_DAILY:
                  freqWheel.setCurrentItem( 3 );
                  break;
               default :
                  freq = Integer.valueOf( -1 );
                  break;
            }
         }
         else
         {
            freq = Integer.valueOf( -1 );
         }
         
         if ( freq.intValue() == -1 )
            freqWheel.setCurrentItem( 0 );
      }
   }
   


   private Dialog createDialogImpl()
   {
      final Activity activity = getFragmentActivity();
      
      return new AlertDialog.Builder( activity ).setIcon( R.drawable.ic_dialog_recurrent )
                                                .setTitle( R.string.dlg_recurr_picker_title )
                                                .setView( container )
                                                .setPositiveButton( R.string.btn_ok,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          RecurrPickerDialogFragment.this.notifiyDialogClosedOk();
                                                                       }
                                                                    } )
                                                .setNegativeButton( R.string.btn_cancel,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          RecurrPickerDialogFragment.this.notifiyDialogClosedCancel();
                                                                       }
                                                                    } )
                                                .create();
   }
   


   private final static < V > V getPatternElement( Map< Integer, List< Object > > elements,
                                                   int key,
                                                   Class< V > type )
   {
      final List< Object > values = elements.get( key );
      
      if ( values == null || values.isEmpty() )
         return null;
      
      final Object o = values.get( 0 );
      
      if ( o.getClass() == type )
         return type.cast( o );
      else
         return null;
   }
}
