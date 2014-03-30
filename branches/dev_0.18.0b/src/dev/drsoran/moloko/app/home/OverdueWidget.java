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

package dev.drsoran.moloko.app.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.rtm.Iterables;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


public class OverdueWidget extends AsyncLoadingTasksCountWidget
{
   public OverdueWidget( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return Intents.createSmartFilterIntent( getContext(),
                                              getSmartFilter(),
                                              getContext().getText( R.string.home_label_overdue )
                                                          .toString() );
   }
   
   
   
   @Override
   protected void initializeNonLoadables( View view )
   {
      ( (TextView) view.findViewById( android.R.id.text1 ) ).setText( R.string.home_label_overdue );
      setIcon( view );
   }
   
   
   
   private void setIcon( View view )
   {
      final ViewGroup widgetFrame = (ViewGroup) view.findViewById( android.R.id.widget_frame );
      ImageView icon = (ImageView) widgetFrame.findViewById( android.R.id.icon );
      
      if ( icon == null )
      {
         final View iconWidget = LayoutInflater.from( getContext() )
                                               .inflate( R.layout.home_activity_drawer_icon_widget,
                                                         widgetFrame,
                                                         true );
         
         icon = (ImageView) iconWidget.findViewById( android.R.id.icon );
      }
      
      icon.setImageDrawable( getContext().getResources()
                                         .getDrawable( R.drawable.ic_home_time ) );
   }
   
   
   
   @Override
   protected Integer doBackgroundQuery()
   {
      final DomainContext domainContext = DomainContext.get( getContext() );
      
      try
      {
         final Iterable< Task > tasks = domainContext.getContentRepository()
                                                     .getTasksFromSmartFilter( getSmartFilter(),
                                                                               TaskContentOptions.None );
         return Iterables.size( tasks );
      }
      catch ( ContentException e )
      {
         domainContext.Log().e( getClass(), e.getLocalizedMessage() );
      }
      catch ( GrammarException e )
      {
         domainContext.Log().e( getClass(), e.getLocalizedMessage() );
      }
      
      return -1;
   }
   
   
   
   private static RtmSmartFilter getSmartFilter()
   {
      return new RtmSmartFilter( new RtmSmartFilterBuilder().dueBefore()
                                                            .today()
                                                            .or()
                                                            .dueBefore()
                                                            .now()
                                                            .toString() );
   }
}
