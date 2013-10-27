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

package dev.drsoran.moloko.app.taskslist.common;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTextView;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTokenizerAdapter;


class QuickAddTaskActionModeCallback implements ActionMode.Callback,
         IQuickAddTaskButtonBarFragmentListener
{
   private final AbstractFullDetailedTasksListActivity activity;
   
   private final RtmSmartFilter filter;
   
   private RtmSmartAddTextView quickAddTaskInput;
   
   private QuickAddTaskActionModeInputHandler quickAddTaskInputHandler;
   
   
   
   public QuickAddTaskActionModeCallback(
      AbstractFullDetailedTasksListActivity activity, RtmSmartFilter filter )
   {
      this.activity = activity;
      this.filter = filter;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      final UiContext context = activity.getAppContext().asUiContext();
      final View quickAddTaskInputView = LayoutInflater.from( context )
                                                       .inflate( R.layout.quick_add_task_actionmode,
                                                                 null );
      mode.setCustomView( quickAddTaskInputView );
      
      quickAddTaskInput = (RtmSmartAddTextView) quickAddTaskInputView.findViewById( R.id.quick_add_task_edit );
      quickAddTaskInput.setTokenizer( new RtmSmartAddTokenizerAdapter( context.getSmartAddService() ) );
      quickAddTaskInput.setThreshold( 1 );
      quickAddTaskInput.setAdapter( new RtmSmartAddAdapter( context ) );
      
      connectToCommitInput();
      
      quickAddTaskInputHandler = new QuickAddTaskActionModeInputHandler( context,
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
         activity.addBottomFragment( fragment ).commit();
      }
      
      fragment.setQuickAddTaskButtonBarFragmentListener( this );
   }
   
   
   
   private void hideButtomButtonBar()
   {
      final QuickAddTaskButtonBarFragment fragment = getButtonBarFragment();
      if ( fragment != null )
      {
         fragment.setQuickAddTaskButtonBarFragmentListener( null );
         activity.removeBottomFragment();
      }
   }
   
   
   
   private QuickAddTaskButtonBarFragment getButtonBarFragment()
   {
      return activity.getBottomFragment( QuickAddTaskButtonBarFragment.class );
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
      if ( activity instanceof IQuickAddTaskActionModeListener )
      {
         ( (IQuickAddTaskActionModeListener) activity ).onQuickAddAddNewTask( quickAddTaskInputHandler.getNewTaskConfig() );
      }
   }
}
