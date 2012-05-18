/*
 * Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.Task;


public class MinDetailedTasksListFragmentAdapter extends
         AbstractTasksListFragmentAdapter
{
   public MinDetailedTasksListFragmentAdapter( Context context,
      List< Task > tasks )
   {
      super( context,
             R.layout.mindetailed_taskslist_listitem,
             R.layout.mindetailed_selectable_taskslist_listitem,
             tasks );
   }
   
   
   
   @Override
   protected boolean mustSwitchLayout( View convertView )
   {
      if ( isCheckable() )
      {
         return convertView.findViewById( R.id.taskslist_selectable_mindetailed_listitem ) == null;
      }
      else
      {
         return convertView.findViewById( R.id.taskslist_mindetailed_listitem ) == null;
      }
   }
}
