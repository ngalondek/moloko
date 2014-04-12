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

package dev.drsoran.moloko.app.taskslist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.taskslist.IShowTasksWithTagsListener.LogicalOperation;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.MolokoDialogFragment;


class ChooseTagsDialogFragment extends MolokoDialogFragment implements
         OnMultiChoiceClickListener
{
   public final static class Config
   {
      public final static String TAG_STRINGS = "tag_strings";
      
      private final static String SELECTION_STATE = "selection_state";
   }
   
   private IShowTasksWithTagsListener listener;
   
   @InstanceState( key = Config.TAG_STRINGS )
   private final String[] tagStrings = new String[ 0 ];
   
   @InstanceState( key = Config.SELECTION_STATE )
   private final boolean[] selectionState = new boolean[ tagStrings.length ];
   
   
   
   public final static void show( Activity activity, Collection< String > tags )
   {
      final Bundle config = new Bundle( 2 );
      
      String[] tagsArray = new String[ tags.size() ];
      tagsArray = tags.toArray( tagsArray );
      
      config.putStringArray( Config.TAG_STRINGS, tagsArray );
      config.putBooleanArray( Config.SELECTION_STATE,
                              new boolean[ tagsArray.length ] );
      
      show( activity, config );
   }
   
   
   
   public final static void show( Activity activity, Bundle config )
   {
      final ChooseTagsDialogFragment frag = newInstance( config );
      UiUtils.showDialogFragment( activity,
                                  frag,
                                  ChooseTagsDialogFragment.class.getName() );
   }
   
   
   
   public final static ChooseTagsDialogFragment newInstance( Bundle config )
   {
      final ChooseTagsDialogFragment frag = new ChooseTagsDialogFragment();
      
      frag.setArguments( config );
      
      return frag;
   }
   
   
   
   public ChooseTagsDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this, ChooseTagsDialogFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IShowTasksWithTagsListener )
         listener = (IShowTasksWithTagsListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      final DialogInterface.OnClickListener onShowTagsClickListener = new DialogInterface.OnClickListener()
      {
         @Override
         public void onClick( DialogInterface dialog, int which )
         {
            notifyOnShowTagsClicked( which );
         }
      };
      
      final Activity activity = getActivity();
      
      final Dialog dialog = new AlertDialog.Builder( activity ).setTitle( getResources().getQuantityString( R.plurals.taskslist_open_tags,
                                                                                                            tagStrings.length ) )
                                                               .setIcon( R.drawable.ic_dialog_tag )
                                                               .setMultiChoiceItems( tagStrings,
                                                                                     selectionState,
                                                                                     this )
                                                               .setPositiveButton( R.string.abstaskslist_dlg_show_tags_and,
                                                                                   onShowTagsClickListener )
                                                               .setNeutralButton( R.string.abstaskslist_dlg_show_tags_or,
                                                                                  onShowTagsClickListener )
                                                               .setNegativeButton( R.string.btn_cancel,
                                                                                   null )
                                                               .create();
      return dialog;
   }
   
   
   
   @Override
   public void onClick( DialogInterface dialog, int which, boolean isChecked )
   {
      selectionState[ which ] = isChecked;
   }
   
   
   
   private void notifyOnShowTagsClicked( int which )
   {
      if ( listener != null )
      {
         List< String > tagsToShow = getSelectedTags();
         if ( tagsToShow.size() > 0 )
         {
            switch ( which )
            {
               case Dialog.BUTTON_POSITIVE:
                  listener.onShowTasksWithTags( tagsToShow,
                                                LogicalOperation.AND );
                  break;
               
               case Dialog.BUTTON_NEUTRAL:
                  listener.onShowTasksWithTags( tagsToShow, LogicalOperation.OR );
                  break;
               
               default :
                  break;
            }
         }
      }
   }
   
   
   
   private List< String > getSelectedTags()
   {
      final List< String > selectedTags = new ArrayList< String >();
      
      for ( int i = 0; i < selectionState.length; i++ )
      {
         if ( selectionState[ i ] )
         {
            selectedTags.add( tagStrings[ i ] );
         }
      }
      
      return selectedTags;
   }
}
