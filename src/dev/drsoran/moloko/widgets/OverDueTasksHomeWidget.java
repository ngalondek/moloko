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
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class OverDueTasksHomeWidget extends AsyncLoadingHomeWidget implements
         IOnTimeChangedListener
{
   private final ViewGroup widgetContainer;
   
   private final TextView label;
   
   private final ContentObserver dbObserver;
   
   private final Runnable reloadRunnable = new Runnable()
   {
      public void run()
      {
         asyncReload();
      }
   };
   
   

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
      MolokoApp.get( getContext() )
               .registerOnTimeChangedListener( IOnTimeChangedListener.MINUTE_TICK
                                                  | IOnTimeChangedListener.SYSTEM_TIME,
                                               this );
   }
   


   public void refresh()
   {
   }
   


   @Override
   public void stop()
   {
      super.stop();
      
      MolokoApp.get( getContext() ).unregisterOnTimeChangedListener( this );
      TasksProviderPart.unregisterContentObserver( getContext(), dbObserver );
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
         + DateParser.tokenNames[ DateParser.NOW ] );
      
      return Intents.createSmartFilterIntent( getContext(),
                                              filter,
                                              label.getText().toString(),
                                              -1 );
   }
   


   public Runnable getRunnable()
   {
      return null;
   }
   


   public void onTimeChanged( int which )
   {
      asyncReloadWithoutSpinner();
   }
   


   @Override
   protected Cursor doBackgroundQuery()
   {
      final String selection = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                           + DateParser.tokenNames[ DateParser.NOW ],
                                                        true );
      
      return getContext().getContentResolver().query( RawTasks.CONTENT_URI,
                                                      new String[]
                                                      { RawTasks._ID },
                                                      selection,
                                                      null,
                                                      null );
   }
   
}
