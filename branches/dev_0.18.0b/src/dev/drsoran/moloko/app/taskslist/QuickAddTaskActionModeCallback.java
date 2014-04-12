/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskslist;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTextView;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTextViewTokenizer;


class QuickAddTaskActionModeCallback implements ActionMode.Callback,
         IQuickAddTaskButtonBarFragmentListener
{
   private final TasksListFragment tasksListFragment;
   
   private final RtmSmartFilter filter;
   
   private RtmSmartAddTextView quickAddTaskInput;
   
   private QuickAddTaskActionModeInputHandler quickAddTaskInputHandler;
   
   
   
   public QuickAddTaskActionModeCallback( TasksListFragment fragment,
      RtmSmartFilter filter )
   {
      this.tasksListFragment = fragment;
      this.filter = filter;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      final View quickAddTaskInputView = LayoutInflater.from( tasksListFragment.getActivity() )
                                                       .inflate( R.layout.quick_add_task_actionmode,
                                                                 null );
      mode.setCustomView( quickAddTaskInputView );
      
      final UiContext uiContext = tasksListFragment.getUiContext();
      
      quickAddTaskInput = (RtmSmartAddTextView) quickAddTaskInputView.findViewById( R.id.quick_add_task_edit );
      quickAddTaskInput.setTokenizer( new RtmSmartAddTextViewTokenizer( uiContext.getSmartAddService() ) );
      quickAddTaskInput.setThreshold( 1 );
      quickAddTaskInput.setAdapter( new RtmSmartAddAdapter( tasksListFragment.getActivity(),
                                                            uiContext.getSmartAddService() ) );
      
      connectToCommitInput();
      
      quickAddTaskInputHandler = new QuickAddTaskActionModeInputHandler( uiContext,
                                                                         quickAddTaskInput );
      quickAddTaskInput.requestFocus();
      
      mode.getMenuInflater().inflate( R.menu.quick_add_task_actionmode, menu );
      
      showButtomButtonBar();
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      quickAddTaskInputHandler.preselect( filter );
      return false;
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_quick_add_task:
            onInputCommitted();
            return true;
            
         default :
            return false;
      }
   }
   
   
   
   @Override
   public void onDestroyActionMode( ActionMode mode )
   {
      quickAddTaskInputHandler = null;
      disconnectFromCommitInput();
      hideButtomButtonBar();
   }
   
   
   
   @Override
   public void onQuickAddTaskOperatorSelected( char operator )
   {
      quickAddTaskInputHandler.insertOperatorAtCursor( operator );
   }
   
   
   
   private void showButtomButtonBar()
   {
      QuickAddTaskButtonBarFragment fragment = getButtonBarFragment();
      if ( fragment == null )
      {
         fragment = QuickAddTaskButtonBarFragment.newInstance( null );
         tasksListFragment.addButtonBarFragment( fragment ).commit();
      }
      
      fragment.setQuickAddTaskButtonBarFragmentListener( this );
   }
   
   
   
   private void hideButtomButtonBar()
   {
      final QuickAddTaskButtonBarFragment fragment = getButtonBarFragment();
      if ( fragment != null )
      {
         fragment.setQuickAddTaskButtonBarFragmentListener( null );
         tasksListFragment.removeButtonBarFragment();
      }
   }
   
   
   
   private QuickAddTaskButtonBarFragment getButtonBarFragment()
   {
      return tasksListFragment.getButtonBarFragment();
   }
   
   
   
   private void connectToCommitInput()
   {
      quickAddTaskInput.setOnEditorActionListener( new OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            if ( UiUtils.hasInputCommitted( actionId ) )
            {
               onInputCommitted();
               return true;
            }
            
            return false;
         }
      } );
   }
   
   
   
   private void disconnectFromCommitInput()
   {
      quickAddTaskInput.setOnEditorActionListener( null );
   }
   
   
   
   private void onInputCommitted()
   {
      if ( tasksListFragment instanceof IQuickAddTaskActionModeListener )
      {
         ( (IQuickAddTaskActionModeListener) tasksListFragment ).onQuickAddAddNewTask( quickAddTaskInputHandler.getNewTaskConfig() );
      }
   }
}
