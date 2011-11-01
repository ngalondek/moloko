/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.widgets;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class CalendarHomeWidget extends AsyncTimeDependentHomeWidget
{
   private final ViewGroup widgetContainer;
   
   private final TextView label;
   
   public final static int TODAY = 1 << 0;
   
   public final static int TOMORROW = 1 << 1;
   
   private final int type;
   
   private final ContentObserver dbObserver;
   
   private final Runnable reloadRunnable = new Runnable()
   {
      @Override
      public void run()
      {
         asyncReload();
      }
   };
   
   
   
   public CalendarHomeWidget( Context context, AttributeSet attrs, int labelId,
      int type )
   {
      super( context, attrs );
      
      // This must be set before a call to getWidgetView().
      this.type = type;
      
      setOrientation( LinearLayout.VERTICAL );
      
      final View view = LayoutInflater.from( context )
                                      .inflate( R.layout.home_activity_widget,
                                                this,
                                                true );
      
      widgetContainer = (ViewGroup) view.findViewById( R.id.widget_container );
      widgetContainer.addView( getWidgetView() );
      
      label = (TextView) view.findViewById( R.id.text );
      label.setText( labelId );
      
      final Handler handler = MolokoApp.getHandler( context );
      
      dbObserver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            // Aggregate several calls to a single update.
            DelayedRun.run( handler, reloadRunnable, 1000 );
         }
      };
   }
   
   
   
   @Override
   public void start()
   {
      super.start();
      
      TasksProviderPart.registerContentObserver( getContext(), dbObserver );
      
      setCalendarDayInWidget();
   }
   
   
   
   @Override
   public void stop()
   {
      super.stop();
      
      TasksProviderPart.unregisterContentObserver( getContext(), dbObserver );
   }
   
   
   
   public View getWidgetView()
   {
      final View view = LayoutInflater.from( getContext() )
                                      .inflate( R.layout.home_activity_calendar_widget,
                                                null );
      return view;
   }
   
   
   
   private MolokoCalendar getCalendar()
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      switch ( type )
      {
         case TOMORROW:
            cal.roll( Calendar.DAY_OF_YEAR, true );
         case TODAY:
         default :
            break;
      }
      
      return cal;
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      final MolokoCalendar cal = getCalendar();
      final RtmSmartFilter filter = new RtmSmartFilter( getFilterExpression( cal ) );
      
      final String title = getContext().getString( ( ( type == TODAY )
                                                                      ? R.string.phr_today_with_date
                                                                      : R.string.phr_tomorrow_with_date ),
                                                   MolokoDateUtils.formatDate( getContext(),
                                                                               cal.getTimeInMillis(),
                                                                               0 ) );
      
      return Intents.createSmartFilterIntent( getContext(), filter, title );
   }
   
   
   
   @Override
   public Runnable getRunnable()
   {
      return null;
   }
   
   
   
   @Override
   protected Integer doBackgroundQuery()
   {
      final MolokoCalendar cal = getCalendar();
      final String selection = RtmSmartFilter.evaluate( getFilterExpression( cal ),
                                                        true );
      
      final Cursor c = getContext().getContentResolver()
                                   .query( RawTasks.CONTENT_URI, new String[]
                                   { RawTasks._ID }, selection, null, null );
      
      if ( c != null )
      {
         final Integer cnt = Integer.valueOf( c.getCount() );
         c.close();
         
         return cnt;
      }
      
      return null;
   }
   
   
   
   @Override
   protected void onMidnight()
   {
      asyncReloadWithoutSpinner();
      setCalendarDayInWidget();
   }
   
   
   
   @Override
   protected void onSystemTimeChanged()
   {
      asyncReloadWithoutSpinner();
      setCalendarDayInWidget();
   }
   
   
   
   private String getFilterExpression( final MolokoCalendar cal )
   {
      return RtmSmartFilterLexer.OP_DUE_LIT
         + RtmSmartFilterLexer.quotify( MolokoDateUtils.formatDate( getContext(),
                                                                    cal.getTimeInMillis(),
                                                                    MolokoDateUtils.FORMAT_WITH_YEAR ) );
   }
   
   
   
   private void setCalendarDayInWidget()
   {
      final MolokoCalendar cal = getCalendar();
      final TextView date = (TextView) findViewById( R.id.home_calendar_date );
      date.setText( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
   }
}
