/* 
 *	Copyright (c) 2014 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.home;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.TasksListContentOptions;
import dev.drsoran.rtm.Iterables;


public class TasksListsSectionWidget extends
         AsyncLoadingWidget< List< TasksList > >
{
   private final AbstractHomeNavAdapter adapter;
   
   private int sectionPosition;
   
   private int countOfTasksLists;
   
   private final AtomicBoolean isReload = new AtomicBoolean();
   
   
   
   public TasksListsSectionWidget( Context context,
      AbstractHomeNavAdapter adapter )
   {
      super( context,
             R.layout.home_activity_drawer_taskslists_section_widget,
             -1 );
      
      this.adapter = adapter;
   }
   
   
   
   @Override
   public void start()
   {
      super.start();
      sectionPosition = adapter.getPositon( this );
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return null;
   }
   
   
   
   @Override
   protected void initializeNonLoadables( View view )
   {
      ( (TextView) view.findViewById( android.R.id.text1 ) ).setText( R.string.app_tasklists );
   }
   
   
   
   @Override
   protected List< TasksList > doBackgroundQuery()
   {
      isReload.set( true );
      return Iterables.asList( DomainContext.get( getContext() )
                                            .getContentRepository()
                                            .getAllTasksLists( TasksListContentOptions.WithTaskCount ) );
   }
   
   
   
   @Override
   protected void onLoadingFinished( View switchView, List< TasksList > data )
   {
      // Only create new Widgets it the data was reloaded. In the other cases the widgets can stay.
      if ( isReload.get() )
      {
         fillListView( data );
         isReload.set( false );
      }
   }
   
   
   
   private void fillListView( List< TasksList > data )
   {
      final Collection< INavWidget > widgets = createTasksListWidgets( data );
      
      if ( countOfTasksLists > 0 )
      {
         int fromPosition = sectionPosition + 1;
         adapter.replaceWidgets( fromPosition,
                                 fromPosition + countOfTasksLists,
                                 widgets );
      }
      else
      {
         adapter.insertWidgetsAfter( sectionPosition, widgets );
      }
      
      countOfTasksLists = data.size();
   }
   
   
   
   private Collection< INavWidget > createTasksListWidgets( List< TasksList > data )
   {
      final Collection< INavWidget > widgets = new ArrayList< INavWidget >( data.size() );
      for ( TasksList tasksList : data )
      {
         widgets.add( new TasksListWidget( getContext(), tasksList ) );
      }
      
      return widgets;
   }
}
