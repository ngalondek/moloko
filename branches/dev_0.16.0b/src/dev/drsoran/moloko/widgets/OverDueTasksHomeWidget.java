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
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SqlSelectionFilter;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class OverDueTasksHomeWidget extends AsyncTimeDependentHomeWidget
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
   


   @Override
   public void start()
   {
      super.start();
      
      TasksProviderPart.registerContentObserver( getContext(), dbObserver );
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
                                      .inflate( R.layout.home_activity_overdue_widget,
                                                null );
      return view;
   }
   


   public Intent getIntent()
   {
      final SqlSelectionFilter filter = new SqlSelectionFilter( getSelection() );
      
      return Intents.createSqlSelectionFilterIntent( getContext(),
                                                     filter,
                                                     label.getText().toString(),
                                                     -1 );
   }
   


   public Runnable getRunnable()
   {
      return null;
   }
   


   @Override
   protected Integer doBackgroundQuery()
   {
      final Cursor c = getContext().getContentResolver()
                                   .query( RawTasks.CONTENT_URI, new String[]
                                   { RawTasks._ID }, getSelection(), null, null );
      
      if ( c != null )
      {
         c.close();
         return Integer.valueOf( c.getCount() );
      }
      
      return null;
   }
   


   @Override
   protected void onMinuteTick()
   {
      asyncReloadWithoutSpinner();
   }
   


   @Override
   protected void onSystemTimeChanged()
   {
      asyncReloadWithoutSpinner();
   }
   


   private final String getSelection()
   {
      final String tasksDueBeforeToday = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                                     + DateParser.tokenNames[ DateParser.TODAY ],
                                                                  true );
      final String tasksDueBeforeNow = RtmSmartFilter.evaluate( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                                   + DateParser.tokenNames[ DateParser.NOW ],
                                                                true );
      
      return new StringBuilder( tasksDueBeforeToday ).append( " OR ( " )
                                                     .append( tasksDueBeforeNow )
                                                     .append( " AND " )
                                                     .append( Tasks.HAS_DUE_TIME )
                                                     .append( " != 0 )" )
                                                     .toString();
   }
}
