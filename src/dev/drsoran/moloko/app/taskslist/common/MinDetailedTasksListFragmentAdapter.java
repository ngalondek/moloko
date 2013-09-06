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

package dev.drsoran.moloko.app.taskslist.common;

import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.widgets.MolokoListView;
import dev.drsoran.moloko.ui.widgets.SimpleLineView;


class MinDetailedTasksListFragmentAdapter extends
         AbstractTasksListFragmentAdapter
{
   public MinDetailedTasksListFragmentAdapter( MolokoListView listView )
   {
      super( listView,
             R.layout.mindetailed_taskslist_listitem,
             R.layout.mindetailed_selectable_taskslist_listitem );
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      convertView = super.getView( position, convertView, parent );
      
      if ( !isInMultiChoiceModalActionMode() )
      {
         final SimpleLineView priority = (SimpleLineView) convertView.findViewById( R.id.taskslist_listitem_priority );
         final Task task = getItem( position );
         
         UiUtils.setPriorityColor( getContext(), priority, task );
      }
      
      return convertView;
   }
   
   
   
   @Override
   protected boolean mustSwitchLayout( View convertView )
   {
      if ( isInMultiChoiceModalActionMode() )
      {
         return convertView.findViewById( R.id.taskslist_selectable_mindetailed_listitem ) == null;
      }
      else
      {
         return convertView.findViewById( R.id.taskslist_mindetailed_listitem ) == null;
      }
   }
}
