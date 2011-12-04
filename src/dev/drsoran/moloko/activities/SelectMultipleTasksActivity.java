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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.os.Bundle;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.SelectableTasksListsFragment;
import dev.drsoran.moloko.fragments.listeners.ISelectableTasksListFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.Task;


public class SelectMultipleTasksActivity extends AbstractTasksListActivity
         implements ISelectableTasksListFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + SelectMultipleTasksActivity.class.getSimpleName();
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setTitleWithSelectedCount( 0 );
   }
   
   
   
   @Override
   public void onOpenTask( int pos )
   {
      if ( getTasksListFragment() != null )
         getTasksListFragment().toggle( pos );
   }
   
   
   
   @Override
   public void onSelectionChanged( List< ? extends Task > selectedTasks )
   {
      setTitleWithSelectedCount( selectedTasks.size() );
   }
   
   
   
   @Override
   public void onEditSelectedTasks( List< ? extends Task > tasks )
   {
      final int selCnt = tasks.size();
      if ( selCnt > 0 )
         if ( selCnt > 1 )
            startActivity( Intents.createEditMultipleTasksIntent( this, tasks ) );
         else
            startActivity( Intents.createEditTaskIntent( this, tasks.get( 0 ) ) );
   }
   
   
   
   @Override
   protected SelectableTasksListsFragment getTasksListFragment()
   {
      if ( super.getTasksListFragment() instanceof SelectableTasksListsFragment )
         return (SelectableTasksListsFragment) super.getTasksListFragment();
      else
         return null;
   }
   
   
   
   private void setTitleWithSelectedCount( int cnt )
   {
      getSupportActionBar().setTitle( getString( R.string.app_task_select_multiple,
                                                 cnt ) );
   }
}
