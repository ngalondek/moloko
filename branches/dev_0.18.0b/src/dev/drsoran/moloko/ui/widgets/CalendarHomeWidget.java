/*
 * Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.ui.widgets;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.Iterables;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


public class CalendarHomeWidget extends AsyncTimeDependentHomeWidget
{
   private final ViewGroup widgetContainer;
   
   private final TextView label;
   
   public final static int TODAY = 1 << 0;
   
   public final static int TOMORROW = 1 << 1;
   
   private final int type;
   
   private final ContentObserver dbObserver;
   
   private final IHandlerToken handlerToken;
   
   private final Runnable reloadRunnable = new Runnable()
   {
      @Override
      public void run()
      {
         asyncReload();
      }
   };
   
   
   
   public CalendarHomeWidget( Context context, AttributeSet attrs )
   {
      this( context, attrs, R.string.phr_nothing, -1 );
   }
   
   
   
   public CalendarHomeWidget( Context context, AttributeSet attrs, int labelId,
      int type )
   {
      super( context, attrs );
      handlerToken = getUiContext().acquireHandlerToken();
      
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
      
      dbObserver = new ContentObserver( null )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            // Aggregate several calls to a single update.
            DelayedRun.run( handlerToken, reloadRunnable, 1000 );
         }
      };
   }
   
   
   
   @Override
   public void start()
   {
      super.start();
      
      getUiContext().asDomainContext()
                    .getContentRepository()
                    .registerContentObserver( dbObserver,
                                              ContentUris.TASKS_CONTENT_URI );
      
      setCalendarDayInWidget();
   }
   
   
   
   @Override
   public void stop()
   {
      getUiContext().asDomainContext()
                    .getContentRepository()
                    .unregisterContentObserver( dbObserver );
      
      handlerToken.removeRunnablesAndMessages();
      
      super.stop();
   }
   
   
   
   public View getWidgetView()
   {
      final View view = LayoutInflater.from( getContext() )
                                      .inflate( R.layout.home_activity_calendar_widget,
                                                null );
      return view;
   }
   
   
   
   private RtmCalendar getCalendar()
   {
      final RtmCalendar cal = RtmCalendar.getInstance();
      
      switch ( type )
      {
         case TOMORROW:
            cal.add( Calendar.DAY_OF_YEAR, 1 );
         case TODAY:
         default :
            break;
      }
      
      return cal;
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      final RtmCalendar cal = getCalendar();
      final RtmSmartFilter filter = new RtmSmartFilter( getFilterExpression( cal ) );
      
      final String title = getContext().getString( ( ( type == TODAY )
                                                                      ? R.string.phr_today_with_date
                                                                      : R.string.phr_tomorrow_with_date ),
                                                   getUiContext().getDateFormatter()
                                                                 .formatDate( cal.getTimeInMillis(),
                                                                              0 ) );
      
      return Intents.createSmartFilterIntent( getContext(), filter, title );
   }
   
   
   
   @Override
   protected Integer doBackgroundQuery()
   {
      try
      {
         final Iterable< Task > tasks = getUiContext().asDomainContext()
                                                      .getContentRepository()
                                                      .getTasksFromSmartFilter( new RtmSmartFilter( getFilterExpression( getCalendar() ) ),
                                                                                TaskContentOptions.None );
         return Iterables.size( tasks );
      }
      catch ( GrammarException e )
      {
         return null;
      }
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
   
   
   
   private String getFilterExpression( RtmCalendar cal )
   {
      final String dueDate = getUiContext().getDateFormatter()
                                           .formatDate( cal.getTimeInMillis(),
                                                        IDateFormatterService.FORMAT_WITH_YEAR
                                                           | IDateFormatterService.FORMAT_NUMERIC );
      
      return new RtmSmartFilterBuilder().due( dueDate ).toString();
   }
   
   
   
   private void setCalendarDayInWidget()
   {
      final RtmCalendar cal = getCalendar();
      final TextView date = (TextView) findViewById( R.id.home_calendar_date );
      date.setText( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
   }
}
