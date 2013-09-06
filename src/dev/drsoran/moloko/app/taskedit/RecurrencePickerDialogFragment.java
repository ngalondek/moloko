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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.util.Pair;
import dev.drsoran.moloko.util.Strings;


class RecurrencePickerDialogFragment extends AbstractPickerDialogFragment
{
   public final static class Config
   {
      public final static String RECURR_PATTERN = "recurr_pattern";
      
      public final static String IS_EVERY_RECURR = "is_every_recurr";
   }
   
   private final static String DEFAULT_RECURRENCE = "after 1 year";
   
   private IRecurrenceParsing recurrenceParsing;
   
   @InstanceState( key = Config.RECURR_PATTERN )
   private String recurrencePattern;
   
   @InstanceState( key = Config.IS_EVERY_RECURR )
   private boolean isEveryRecurrence;
   
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
      final RecurrencePickerDialogFragment frag = newInstance( config );
      UiUtils.showDialogFragment( activity,
                                  frag,
                                  RecurrencePickerDialogFragment.class.getName() );
   }
   
   
   
   public final static RecurrencePickerDialogFragment newInstance( Bundle config )
   {
      final RecurrencePickerDialogFragment frag = new RecurrencePickerDialogFragment();
      
      frag.setArguments( config );
      
      return frag;
   }
   
   
   
   public RecurrencePickerDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           RecurrencePickerDialogFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      recurrenceParsing = getUiContext().getParsingService()
                                        .getRecurrenceParsing();
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
      {
         configure( savedInstanceState );
      }
      
      try
      {
         ensureValidRecurrencePattern();
         
         final FragmentActivity activity = getSherlockActivity();
         
         final LayoutInflater inflater = LayoutInflater.from( activity );
         container = inflater.inflate( R.layout.recurr_picker_dialog, null );
         
         initWheels();
         
         final Dialog dialog = createDialogImpl();
         return dialog;
      }
      catch ( GrammarException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   private void ensureValidRecurrencePattern() throws GrammarException
   {
      if ( TextUtils.isEmpty( recurrencePattern ) )
      {
         final Pair< String, Boolean > parseReturn = recurrenceParsing.parseRecurrence( DEFAULT_RECURRENCE );
         recurrencePattern = parseReturn.first;
         isEveryRecurrence = parseReturn.second.booleanValue();
      }
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
            return RecurrencePatternSyntax.VAL_YEARLY;
         case 1:
            return RecurrencePatternSyntax.VAL_MONTHLY;
         case 2:
            return RecurrencePatternSyntax.VAL_WEEKLY;
         case 3:
            return RecurrencePatternSyntax.VAL_DAILY;
         default :
            return Strings.EMPTY_STRING;
      }
   }
   
   
   
   public boolean isEvery()
   {
      return isEveryRecurrence;
   }
   
   
   
   public String getPatternString()
   {
      return recurrencePattern;
   }
   
   
   
   public Recurrence getRecurrence()
   {
      return new Recurrence( getPatternString(), isEvery() );
   }
   
   
   
   public String getSentence() throws GrammarException
   {
      return recurrenceParsing.parseRecurrencePatternToSentence( getPatternString(),
                                                                 isEvery() );
   }
   
   
   
   private void initWheels() throws GrammarException
   {
      final Map< Integer, List< Object >> elements = recurrenceParsing.tokenizeRecurrencePattern( recurrencePattern );
      
      // Every, After wheel
      {
         evAftWheel = (WheelView) container.findViewById( R.id.recurr_dlg_ev_aft_wheel );
         initEvAftWheel( isEveryRecurrence );
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
      
      evAftWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            updateIsEveryRecurrence();
         }
      } );
      
      intervalWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            updateRecurrencePattern();
            initFreqWheel( null );
         }
      } );
      
      freqWheel.addScrollingListener( new OnWheelScrollListener()
      {
         @Override
         public void onScrollingStarted( WheelView wheel )
         {
         }
         
         
         
         @Override
         public void onScrollingFinished( WheelView wheel )
         {
            updateRecurrencePattern();
         }
      } );
   }
   
   
   
   private void initEvAftWheel( boolean isEvery )
   {
      final Activity activity = getSherlockActivity();
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
      final Activity activity = getSherlockActivity();
      intervalWheel.setViewAdapter( new NumericWheelAdapter( activity, 1, 365 ) );
      
      if ( interval != null )
         intervalWheel.setCurrentItem( interval.intValue() - 1 );
      else
         intervalWheel.setCurrentItem( 0 );
   }
   
   
   
   private void initFreqWheel( Map< Integer, List< Object >> elements )
   {
      final Activity activity = getSherlockActivity();
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
   
   
   
   private void updateRecurrencePattern()
   {
      final StringBuilder sb = new StringBuilder();
      
      sb.append( RecurrencePatternSyntax.OP_FREQ )
        .append( RecurrencePatternSyntax.OPERATOR_VALUE_SEP )
        .append( getFreqValueAsString() );
      sb.append( RecurrencePatternSyntax.OPERATOR_SEP );
      sb.append( RecurrencePatternSyntax.OP_INTERVAL )
        .append( RecurrencePatternSyntax.OPERATOR_VALUE_SEP )
        .append( getInterval() );
      
      recurrencePattern = recurrenceParsing.ensureRecurrencePatternOrder( sb.toString() );
   }
   
   
   
   private void updateIsEveryRecurrence()
   {
      isEveryRecurrence = evAftWheel.getCurrentItem() == 0;
   }
   
   
   
   private Dialog createDialogImpl()
   {
      final Activity activity = getSherlockActivity();
      
      return new AlertDialog.Builder( activity ).setIcon( R.drawable.ic_dialog_recurrent )
                                                .setTitle( R.string.dlg_recurr_picker_title )
                                                .setView( container )
                                                .setPositiveButton( R.string.btn_ok,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          RecurrencePickerDialogFragment.this.notifiyDialogClosedOk();
                                                                       }
                                                                    } )
                                                .setNegativeButton( R.string.btn_cancel,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          RecurrencePickerDialogFragment.this.notifiyDialogClosedCancel();
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
