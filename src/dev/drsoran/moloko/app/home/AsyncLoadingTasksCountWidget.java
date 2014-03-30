/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;


abstract class AsyncLoadingTasksCountWidget extends
         AsyncLoadingWidget< Integer >
{
   protected AsyncLoadingTasksCountWidget( Context context )
   {
      super( context,
             R.layout.home_activity_drawer_item_with_widget,
             R.id.numTasks );
   }
   
   
   
   @Override
   protected void initializeSwitchView( View switchView, Integer data )
   {
      setTasksCount( (TextView) switchView, data );
   }
   
   
   
   private void setTasksCount( TextView counterView, Integer tasksCount )
   {
      if ( tasksCount != null )
      {
         counterView.setText( String.valueOf( tasksCount ) );
         counterView.setVisibility( tasksCount > 0 ? View.VISIBLE
                                                  : View.INVISIBLE );
      }
      else
      {
         counterView.setText( "?" );
         counterView.setVisibility( View.GONE );
      }
   }
}
