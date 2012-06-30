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

package dev.drsoran.moloko.actionmodes;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.listener.IQuickAddTaskActionModeListener;
import dev.drsoran.moloko.activities.MolokoFragmentActivity;
import dev.drsoran.moloko.fragments.QuickAddTaskButtonBarFragment;
import dev.drsoran.moloko.fragments.listeners.IQuickAddTaskButtonBarFragmentListener;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.widgets.RtmSmartAddTextView;
import dev.drsoran.rtm.RtmSmartFilter;


public class QuickAddTaskActionModeCallback implements ActionMode.Callback,
         IQuickAddTaskButtonBarFragmentListener
{
   private final MolokoFragmentActivity activity;
   
   private final RtmSmartFilter filter;
   
   private RtmSmartAddTextView quickAddTaskInput;
   
   private QuickAddTaskActionModeInputHandler quickAddTaskInputHandler;
   
   
   
   public QuickAddTaskActionModeCallback( MolokoFragmentActivity activity,
      RtmSmartFilter filter )
   {
      this.activity = activity;
      this.filter = filter;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      final Context context = activity.getSupportActionBar().getThemedContext();
      final View quickAddTaskInputView = LayoutInflater.from( context )
                                                       .inflate( R.layout.quick_add_task_action_mode,
                                                                 null );
      mode.setCustomView( quickAddTaskInputView );
      
      quickAddTaskInput = (RtmSmartAddTextView) quickAddTaskInputView.findViewById( R.id.quick_add_task_edit );
      connectToCommitInput();
      
      quickAddTaskInputHandler = new QuickAddTaskActionModeInputHandler( context,
                                                                         quickAddTaskInput );
      
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
      return false;
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
         activity.getSupportFragmentManager()
                 .beginTransaction()
                 .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE )
                 .add( R.id.frag_quick_add_task_button_bar, fragment )
                 .commit();
      }
      
      fragment.setQuickAddTaskButtonBarFragmentListener( this );
   }
   
   
   
   private void hideButtomButtonBar()
   {
      final QuickAddTaskButtonBarFragment fragment = getButtonBarFragment();
      if ( fragment != null )
      {
         fragment.setQuickAddTaskButtonBarFragmentListener( null );
         
         activity.getSupportFragmentManager()
                 .beginTransaction()
                 .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE )
                 .remove( fragment )
                 .commit();
      }
   }
   
   
   
   private QuickAddTaskButtonBarFragment getButtonBarFragment()
   {
      return (QuickAddTaskButtonBarFragment) activity.findAddedFragmentById( R.id.frag_quick_add_task_button_bar );
   }
   
   
   
   private void connectToCommitInput()
   {
      quickAddTaskInput.setOnEditorActionListener( new OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            if ( UIUtils.hasInputCommitted( actionId ) )
            {
               if ( activity instanceof IQuickAddTaskActionModeListener )
               {
                  final QuickAddTaskActionModeInputHandler handler = QuickAddTaskActionModeCallback.this.quickAddTaskInputHandler;
                  ( (IQuickAddTaskActionModeListener) activity ).onQuickAddAddNewTask( handler.getNewTaskConfig() );
               }
               
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
}
