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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class OverDueTasksHomeWidget extends LinearLayout implements
         IMolokoHomeWidget
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
   }
   


   public View getWidgetView()
   {
      final View view = LayoutInflater.from( getContext() )
                                      .inflate( R.layout.home_activity_overdue_widget,
                                                null );
      return view;
   }
   


   public Intent getIntent()
   {
      final RtmSmartFilter filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
         + DateParser.tokenNames[ DateParser.TODAY ] );
      
      return Intents.createSmartFilterIntent( getContext(),
                                              filter,
                                              label.getText().toString(),
                                              -1 );
   }
   


   public void refresh()
   {
      final TextView counterView = (TextView) findViewById( R.id.counter_bubble );
      final String selection = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                           + DateParser.tokenNames[ DateParser.TODAY ],
                                                        true );
      Cursor c = null;
      
      try
      {
         
         c = getContext().getContentResolver().query( RawTasks.CONTENT_URI,
                                                      new String[]
                                                      { RawTasks._ID },
                                                      selection,
                                                      null,
                                                      null );
         if ( c != null )
         {
            counterView.setText( String.valueOf( c.getCount() ) );
         }
         else
         {
            counterView.setText( "?" );
         }
      }
      finally
      {
         if ( c != null )
            c.close();
      }
   }
   
}
