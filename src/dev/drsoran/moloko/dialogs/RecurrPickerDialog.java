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

import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;


public class RecurrPickerDialog extends AbstractPickerDialog
{
   private final Context context;
   
   private AlertDialog impl;
   
   private View container;
   
   private WheelView evAftWheel;
   
   private WheelView intervalWheel;
   
   private WheelView freqWheel;
   
   

   public RecurrPickerDialog( Context context, String pattern, boolean isEvery )
   {
      this.context = context;
      init( pattern, isEvery );
   }
   


   private void init( String pattern, boolean isEvery )
   {
      final Map< Integer, List< Object >> elements = RecurrenceParsing.parseRecurrencePattern( pattern );
      
      final LayoutInflater inflater = LayoutInflater.from( context );
      container = inflater.inflate( R.layout.recurr_picker_dialog, null );
      
      // Every, After wheel
      {
         evAftWheel = (WheelView) container.findViewById( R.id.recurr_dlg_ev_aft_wheel );
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
      
      this.impl = new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_recurrent )
                                                    .setTitle( R.string.dlg_recurr_picker_title )
                                                    .setView( container )
                                                    .setPositiveButton( R.string.btn_ok,
                                                                        new OnClickListener()
                                                                        {
                                                                           public void onClick( DialogInterface dialog,
                                                                                                int which )
                                                                           {
                                                                              notifyOnDialogCloseListener( CloseReason.OK,
                                                                                                           getPattern(),
                                                                                                           Boolean.valueOf( isEvery() ) );
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
   


   public String getPattern()
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
   


   public String getSentence()
   {
      return RecurrenceParsing.parseRecurrencePattern( getPattern(), isEvery() );
   }
   


   private void initEvAftWheel( boolean isEvery )
   {
      final Resources res = context.getResources();
      evAftWheel.setViewAdapter( new ArrayWheelAdapter< String >( context,
                                                                  new String[]
                                                                  {
                                                                   res.getString( R.string.dlg_recurr_picker_every ),
                                                                   res.getString( R.string.dlg_recurr_picker_after ) } ) );
      
   }
   


   private void initIntervalWheel( Integer interval )
   {
      intervalWheel.setViewAdapter( new NumericWheelAdapter( context, 1, 999 ) );
      
      if ( interval != null )
         intervalWheel.setCurrentItem( interval.intValue() - 1 );
      else
         intervalWheel.setCurrentItem( 0 );
   }
   


   private void initFreqWheel( Map< Integer, List< Object >> elements )
   {
      final Resources res = context.getResources();
      final int interval = getInterval();
      
      freqWheel.setViewAdapter( new ArrayWheelAdapter< String >( context,
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
