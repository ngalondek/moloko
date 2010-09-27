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

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class OverDueTasksHomeWidget extends LinearLayout implements
         OnClickListener
{
   private ViewGroup widgetContainer;
   
   private TextView label;
   
   

   public OverDueTasksHomeWidget( Context context, AttributeSet attrs,
      int labelId )
   {
      super( context, attrs );
      
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
                                      .inflate( R.layout.home_activity_overdue_widget,
                                                null );
      
      {
         final TextView counterView = (TextView) view.findViewById( R.id.counter_bubble );
         final String selection = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
            + "today" );
         
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
   


   public void onClick( View v )
   {
      final RtmSmartFilter filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
         + "today" );
      
      getContext().startActivity( Intents.createSmartFilterIntent( getContext(),
                                                                   filter,
                                                                   label.getText()
                                                                        .toString(),
                                                                   -1 ) );
   }
}
