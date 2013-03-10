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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SqlSelectionFilter;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class OverDueTasksHomeWidget extends AsyncTimeDependentHomeWidget
{
   private final ViewGroup widgetContainer;
   
   private final TextView label;
   
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
   
   
   
   public OverDueTasksHomeWidget( Context context, AttributeSet attrs,
      int labelId )
   {
      super( context, attrs );
      handlerToken = getUiContext().acquireHandlerToken();
      
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
      TasksProviderPart.registerContentObserver( getContext(), dbObserver );
   }
   
   
   
   @Override
   public void stop()
   {
      TasksProviderPart.unregisterContentObserver( getContext(), dbObserver );
      handlerToken.removeRunnablesAndMessages();
      
      super.stop();
   }
   
   
   
   public View getWidgetView()
   {
      final View view = LayoutInflater.from( getContext() )
                                      .inflate( R.layout.home_activity_overdue_widget,
                                                null );
      return view;
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      final SqlSelectionFilter filter = new SqlSelectionFilter( getSelection() );
      
      return Intents.createSqlSelectionFilterIntent( getContext(),
                                                     filter,
                                                     label.getText().toString() );
   }
   
   
   
   @Override
   protected Integer doBackgroundQuery()
   {
      final Cursor c = getContext().getContentResolver()
                                   .query( RawTasks.CONTENT_URI, new String[]
                                   { RawTasks._ID }, getSelection(), null, null );
      
      if ( c != null )
      {
         final Integer cnt = Integer.valueOf( c.getCount() );
         c.close();
         
         return cnt;
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
