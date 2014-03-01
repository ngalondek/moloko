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
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


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
      
      getUiContext().asDomainContext()
                    .getContentRepository()
                    .registerContentObserver( dbObserver,
                                              ContentUris.TASKS_CONTENT_URI );
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
                                      .inflate( R.layout.home_activity_overdue_widget,
                                                null );
      return view;
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return Intents.createSmartFilterIntent( getContext(),
                                              getSmartFilter(),
                                              label.getText().toString() );
   }
   
   
   
   @Override
   protected Integer doBackgroundQuery()
   {
      try
      {
         final Iterable< Task > tasks = getUiContext().asDomainContext()
                                                      .getContentRepository()
                                                      .getTasksFromSmartFilter( getSmartFilter(),
                                                                                TaskContentOptions.Minimal );
         return Iterables.size( tasks );
      }
      catch ( ContentException e )
      {
         getUiContext().Log().e( getClass(), e.getLocalizedMessage() );
      }
      catch ( GrammarException e )
      {
         getUiContext().Log().e( getClass(), e.getLocalizedMessage() );
      }
      
      return -1;
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
   
   
   
   private final RtmSmartFilter getSmartFilter()
   {
      return new RtmSmartFilterBuilder().dueBefore()
                                        .today()
                                        .or()
                                        .dueBefore()
                                        .now()
                                        .toSmartFilter();
   }
}
