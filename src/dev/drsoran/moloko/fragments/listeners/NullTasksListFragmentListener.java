/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.fragments.listeners;

import java.util.List;

import dev.drsoran.rtm.Task;


public class NullTasksListFragmentListener implements
         IFullDetailedTasksListFragmentListener,
         IMinDetailedTasksListFragmentListener,
         ISelectableTasksListFragmentListener
{
   
   @Override
   public void onSelectionChanged( List< ? extends Task > selectedTasks )
   {
   }
   
   
   
   @Override
   public void onShowTasksWithTags( List< String > tags )
   {
   }
   
   
   
   @Override
   public void onEditTask( int pos )
   {
   }
   
   
   
   @Override
   public void onCompleteTask( int pos )
   {
   }



   @Override
   public void onIncompleteTask( int pos )
   {
   }



   @Override
   public void onPostponeTask( int pos )
   {
   }



   @Override
   public void onDeleteTask( int pos )
   {
   }



   @Override
   public void onOpenList( int pos, String listId )
   {
   }
   
   
   
   @Override
   public void onOpenLocation( int pos, String locationId )
   {
   }
   
   
   
   @Override
   public void onSelectTasks()
   {
   }
   
   
   
   @Override
   public void onOpenTask( int pos )
   {
   }
   
   
   
   @Override
   public void onTaskSortChanged( int newTaskSort )
   {
   }
   
   
   
   @Override
   public void onEditSelectedTasks( List< ? extends Task > tasks )
   {
   }
   
   
   
   @Override
   public void onCompleteSelectedTasks( List< ? extends Task > tasks )
   {
   }
   
   
   
   @Override
   public void onIncompleteSelectedTasks( List< ? extends Task > tasks )
   {
   }
   
   
   
   @Override
   public void onPostponeSelectedTasks( List< ? extends Task > tasks )
   {
   }
   
   
   
   @Override
   public void onDeleteSelectedTasks( List< ? extends Task > tasks )
   {
   }
}
