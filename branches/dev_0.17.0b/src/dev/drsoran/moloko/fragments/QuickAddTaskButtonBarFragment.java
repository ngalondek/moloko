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


public class QuickAddTaskButtonBarFragment extends MolokoFragment implements
         View.OnClickListener
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
      super.onDetach();
      listener = null;
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
      
      view.findViewById( R.id.quick_add_task_btn_due_date )
          .setOnClickListener( this );
      view.findViewById( R.id.quick_add_task_btn_prio )
          .setOnClickListener( this );
      view.findViewById( R.id.quick_add_task_btn_list_tags )
          .setOnClickListener( this );
      view.findViewById( R.id.quick_add_task_btn_location )
          .setOnClickListener( this );
      view.findViewById( R.id.quick_add_task_btn_repeat )
          .setOnClickListener( this );
      view.findViewById( R.id.quick_add_task_btn_estimate )
          .setOnClickListener( this );
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
   
   
   
   private void notifyInsertOperator( char operator )
   {
      if ( listener != null )
         listener.onQuickAddTaskOperatorSelected( operator );
   }
}
