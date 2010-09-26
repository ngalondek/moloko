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

package dev.drsoran.moloko.activities;

import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class HomeAdapter extends BaseAdapter
{
   private final static int[] LABELS =
   { R.string.home_btn_today, R.string.home_btn_tomorrow };
   
   private final static int[] WIDGETS =
   { R.layout.home_activity_calendar_widget,
    R.layout.home_activity_calendar_widget };
   
   private final Context context;
   
   

   public HomeAdapter( Context context )
   {
      this.context = context;
   }
   


   public int getCount()
   {
      return WIDGETS.length;
   }
   


   public Object getItem( int position )
   {
      return null;
   }
   


   public long getItemId( int position )
   {
      return 0;
   }
   


   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
      {
         // if it's not recycled, initialize some attributes
         switch ( position )
         {
            // TODAY
            case 0:
               return createCalendarView( position,
                                          Calendar.getInstance( MolokoApp.getSettings()
                                                                         .getTimezone() ) );
               // TOMORROW
            case 1:
               final Calendar cal = Calendar.getInstance( MolokoApp.getSettings()
                                                                   .getTimezone() );
               cal.roll( Calendar.DAY_OF_MONTH, true );
               
               return createCalendarView( position, cal );
               
            default :
               return createView( position );
         }
      }
      else
      {
         return convertView;
      }
   }
   


   private View createView( int position )
   {
      final LayoutInflater inflater = LayoutInflater.from( context );
      
      final View view = inflater.inflate( R.layout.home_activity_widget,
                                          null,
                                          false );
      
      final ViewGroup widgetContainer = (ViewGroup) view.findViewById( R.id.widget_container );
      inflater.inflate( WIDGETS[ position ], widgetContainer, true );
      
      ( (TextView) view.findViewById( R.id.text ) ).setText( LABELS[ position ] );
      
      return view;
   }
   


   private View createCalendarView( int position, Calendar date )
   {
      date.clear( Calendar.HOUR_OF_DAY );
      
      final View view = createView( position );
      
      final TextView dateView = (TextView) view.findViewById( R.id.home_calendar_date );
      dateView.setText( String.valueOf( date.get( Calendar.DAY_OF_MONTH ) ) );
      
      final TextView counterView = (TextView) view.findViewById( R.id.counter_bubble );
      
      final String selection = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_LIT
         + MolokoDateUtils.formatDate( date.getTimeInMillis(),
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
      
      return view;
   }
}
