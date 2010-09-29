/* 
	Copyright (c) 2010 Ronny Röhricht

	This file is part of Moloko.

	Moloko is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Moloko is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

	Contributors:
     Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.widgets;

import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class CalendarHomeWidget extends LinearLayout implements OnClickListener
{
   private ViewGroup widgetContainer;
   
   private TextView label;
   
   public final static int TODAY = 1 << 0;
   
   public final static int TOMORROW = 1 << 1;
   
   private final int type;
   
   

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
      
      setOnClickListener( this );
   }
   


   public View getWidgetView()
   {
      final Context context = getContext();
      
      final View view = LayoutInflater.from( context )
                                      .inflate( R.layout.home_activity_calendar_widget,
                                                null );
      
      final Calendar cal = getCalendar();
      
      {
         final TextView date = (TextView) view.findViewById( R.id.home_calendar_date );
         date.setText( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
      }
      
      {
         final TextView counterView = (TextView) view.findViewById( R.id.counter_bubble );
         final String selection = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_LIT
            + MolokoDateUtils.formatDate( cal.getTimeInMillis(),
                                          MolokoDateUtils.FORMAT_PARSER ) );
         
         final Cursor c = context.getContentResolver()
                                 .query( RawTasks.CONTENT_URI, new String[]
                                 { RawTasks._ID }, selection, null, null );
         
         if ( c != null )
         {
            counterView.setText( String.valueOf( c.getCount() ) );
            c.close();
         }
         else
         {
            counterView.setText( "?" );
         }
      }
      
      return view;
   }
   


   private Calendar getCalendar()
   {
      final Calendar cal = Calendar.getInstance( MolokoApp.getSettings()
                                                          .getTimezone() );
      cal.setTimeInMillis( MolokoDateUtils.toLocal( System.currentTimeMillis() ) );
      
      switch ( type )
      {
         case TOMORROW:
            cal.roll( Calendar.DAY_OF_MONTH, true );
         case TODAY:
         default :
            break;
      }
      
      return cal;
   }
   


   public void onClick( View v )
   {
      final Calendar cal = getCalendar();
      final RtmSmartFilter filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_DUE_LIT
         + MolokoDateUtils.formatDate( cal.getTimeInMillis(),
                                       MolokoDateUtils.FORMAT_PARSER ) );
      
      getContext().startActivity( Intents.createSmartFilterIntent( getContext(),
                                                                   filter,
                                                                   label.getText()
                                                                        .toString(),
                                                                   -1 ) );
   }
   
}
