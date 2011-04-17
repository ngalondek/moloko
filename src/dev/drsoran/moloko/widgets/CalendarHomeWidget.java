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
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class CalendarHomeWidget extends AsyncLoadingHomeWidget
{
   private final ViewGroup widgetContainer;
   
   private final TextView label;
   
   public final static int TODAY = 1 << 0;
   
   public final static int TOMORROW = 1 << 1;
   
   private final int type;
   
   private final ContentObserver dbObserver;
   
   private final Runnable reloadRunnable = new Runnable()
   {
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
   


   public void start()
   {
      asyncReload();
      
      TasksProviderPart.registerContentObserver( getContext(), dbObserver );
   }
   


   public void refresh()
   {
      final Calendar cal = getCalendar();
      
      {
         final TextView date = (TextView) findViewById( R.id.home_calendar_date );
         date.setText( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
      }
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
   


   private Calendar getCalendar()
   {
      final Calendar cal = MolokoDateUtils.newCalendar();
      
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
   


   public Intent getIntent()
   {
      final Calendar cal = getCalendar();
      final RtmSmartFilter filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_DUE_LIT
         + MolokoDateUtils.formatDate( cal.getTimeInMillis(),
                                       MolokoDateUtils.FORMAT_PARSER ) );
      
      final String title = getContext().getString( ( ( type == TODAY )
                                                                      ? R.string.phr_today_with_date
                                                                      : R.string.phr_tomorrow_with_date ),
                                                   MolokoDateUtils.formatDate( cal.getTimeInMillis(),
                                                                               0 ) );
      
      return Intents.createSmartFilterIntent( getContext(), filter, title, -1 );
   }
   


   public Runnable getRunnable()
   {
      return null;
   }
   


   @Override
   protected Cursor doBackgroundQuery()
   {
      final Calendar cal = getCalendar();
      
      final String selection = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_LIT
                                                           + MolokoDateUtils.formatDate( cal.getTimeInMillis(),
                                                                                         MolokoDateUtils.FORMAT_PARSER ),
                                                        true );
      return getContext().getContentResolver().query( RawTasks.CONTENT_URI,
                                                      new String[]
                                                      { RawTasks._ID },
                                                      selection,
                                                      null,
                                                      null );
   }
}
