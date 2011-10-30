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
   
   public void onOpenTask( int pos )
   {
   }
   


   public void onSelectTasks()
   {
   }
   


   public void onTaskSortChanged( int newTaskSort )
   {
   }
   


   public void onEditTask( int pos )
   {
   }
   


   public void onOpenList( int pos, String listId )
   {
   }
   


   public void onOpenLocation( int pos, String locationId )
   {
   }
   


   public void onShowTasksWithTag( String tag )
   {
   }
   


   public void onShowTasksWithTags( List< String > tags, String logicalOperator )
   {
   }
   


   public void onEditSelectedTasks( List< ? extends Task > tasks )
   {
   }
   


   public void onCompleteSelectedTasks( List< ? extends Task > tasks )
   {
   }
   


   public void onUncompleteSelectedTasks( List< ? extends Task > tasks )
   {
   }
   


   public void onPostponeSelectedTasks( List< ? extends Task > tasks )
   {
   }
   


   public void onDeleteSelectedTasks( List< ? extends Task > tasks )
   {
   }
   


   @Override
   public void onSelectionChanged( List< ? extends Task > selectedTasks )
   {
   }
}
