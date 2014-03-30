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

package dev.drsoran.moloko.app.home;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.rtm.Iterables;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


public class CalendarWidget extends AsyncLoadingTasksCountWidget
{
   private final int textResId;
   
   public final static int TODAY = 1 << 0;
   
   public final static int TOMORROW = 1 << 1;
   
   private final int type;
   
   
   
   public CalendarWidget( Context context, int type, int textResId )
   {
      super( context );
      
      // This must be set before a call to getWidgetView().
      this.type = type;
      this.textResId = textResId;
   }
   
   
   
   @Override
   protected void initializeNonLoadables( View view )
   {
      ( (TextView) view.findViewById( android.R.id.text1 ) ).setText( textResId );
      setCalendarDayInWidget( view );
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
      
      final UiContext uiContext = UiContext.get( getContext() );
      final String title = uiContext.getString( ( ( type == TODAY )
                                                                   ? R.string.phr_today_with_date
                                                                   : R.string.phr_tomorrow_with_date ),
                                                uiContext.getDateFormatter()
                                                         .formatDate( cal.getTimeInMillis(),
                                                                      0 ) );
      
      return Intents.createSmartFilterIntent( uiContext, filter, title );
   }
   
   
   
   @Override
   protected Integer doBackgroundQuery()
   {
      try
      {
         final Iterable< Task > tasks = DomainContext.get( getContext() )
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
   
   
   
   private String getFilterExpression( RtmCalendar cal )
   {
      final String dueDate = UiContext.get( getContext() )
                                      .getDateFormatter()
                                      .formatDate( cal.getTimeInMillis(),
                                                   IDateFormatterService.FORMAT_WITH_YEAR
                                                      | IDateFormatterService.FORMAT_NUMERIC );
      
      return new RtmSmartFilterBuilder().due( dueDate ).toString();
   }
   
   
   
   private void setCalendarDayInWidget( View view )
   {
      final ViewGroup widgetFrame = (ViewGroup) view.findViewById( android.R.id.widget_frame );
      TextView date = (TextView) widgetFrame.findViewById( R.id.home_calendar_date );
      
      if ( date == null )
      {
         final View widget = LayoutInflater.from( getContext() )
                                           .inflate( R.layout.home_activity_drawer_calendar_widget,
                                                     widgetFrame,
                                                     true );
         date = (TextView) widget.findViewById( R.id.home_calendar_date );
      }
      
      final RtmCalendar cal = getCalendar();
      date.setText( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
   }
}
