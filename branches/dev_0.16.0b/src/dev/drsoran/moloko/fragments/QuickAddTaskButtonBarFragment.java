package dev.drsoran.moloko.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( listener instanceof IQuickAddTaskButtonBarFragmentListener )
         listener = (IQuickAddTaskButtonBarFragmentListener) activity;
      else
         listener = null;
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
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
