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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoFragment;
import dev.drsoran.moloko.fragments.listeners.IQuickAddTaskButtonBarFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.moloko.util.ShowButtonTextAsToast;


public class QuickAddTaskButtonBarFragment extends MolokoFragment implements
         View.OnClickListener, View.OnLongClickListener
{
   public final static QuickAddTaskButtonBarFragment newInstance( Bundle config )
   {
      final QuickAddTaskButtonBarFragment fragment = new QuickAddTaskButtonBarFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   private IQuickAddTaskButtonBarFragmentListener listener;
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   public void setQuickAddTaskButtonBarFragmentListener( IQuickAddTaskButtonBarFragmentListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View view = inflater.inflate( R.layout.quick_add_task_button_bar_fragment,
                                          container,
                                          false );
      return view;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      setListener( view, R.id.quick_add_task_btn_due_date );
      setListener( view, R.id.quick_add_task_btn_prio );
      setListener( view, R.id.quick_add_task_btn_list_tags );
      setListener( view, R.id.quick_add_task_btn_location );
      setListener( view, R.id.quick_add_task_btn_repeat );
      setListener( view, R.id.quick_add_task_btn_estimate );
   }
   
   
   
   @Override
   public void onClick( View view )
   {
      switch ( view.getId() )
      {
         case R.id.quick_add_task_btn_due_date:
            notifyInsertOperator( RtmSmartAddTokenizer.OP_DUE_DATE );
            break;
         
         case R.id.quick_add_task_btn_prio:
            notifyInsertOperator( RtmSmartAddTokenizer.OP_PRIORITY );
            break;
         
         case R.id.quick_add_task_btn_list_tags:
            notifyInsertOperator( RtmSmartAddTokenizer.OP_LIST_TAGS );
            break;
         
         case R.id.quick_add_task_btn_location:
            notifyInsertOperator( RtmSmartAddTokenizer.OP_LOCATION );
            break;
         
         case R.id.quick_add_task_btn_repeat:
            notifyInsertOperator( RtmSmartAddTokenizer.OP_REPEAT );
            break;
         
         case R.id.quick_add_task_btn_estimate:
            notifyInsertOperator( RtmSmartAddTokenizer.OP_ESTIMATE );
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public boolean onLongClick( View view )
   {
      switch ( view.getId() )
      {
         case R.id.quick_add_task_btn_due_date:
         case R.id.quick_add_task_btn_prio:
         case R.id.quick_add_task_btn_list_tags:
         case R.id.quick_add_task_btn_location:
         case R.id.quick_add_task_btn_repeat:
         case R.id.quick_add_task_btn_estimate:
         {
            new ShowButtonTextAsToast( getSherlockActivity() ).show( view,
                                                                     (CharSequence) view.getTag() );
            return true;
         }
         
         default :
            return false;
      }
   }
   
   
   
   private void setListener( View parent, int buttonId )
   {
      final View button = parent.findViewById( buttonId );
      button.setOnClickListener( this );
      button.setOnLongClickListener( this );
   }
   
   
   
   private void notifyInsertOperator( char operator )
   {
      if ( listener != null )
         listener.onQuickAddTaskOperatorSelected( operator );
   }
}
