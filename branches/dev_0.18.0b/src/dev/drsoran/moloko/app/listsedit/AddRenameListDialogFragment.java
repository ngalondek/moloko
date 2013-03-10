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

package dev.drsoran.moloko.app.listsedit;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.content.ApplyChangesInfo;
import dev.drsoran.moloko.content.db.RtmListsTable;
import dev.drsoran.moloko.content.db.RtmListsTable.NewRtmListId;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.fragments.MolokoEditDialogFragment;
import dev.drsoran.moloko.ui.fragments.listeners.IMolokoEditDialogFragmentListener;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class AddRenameListDialogFragment extends MolokoEditDialogFragment
{
   @InstanceState( key = Intents.Extras.KEY_LIST )
   private RtmList list;
   
   @InstanceState( key = Intents.Extras.KEY_FILTER )
   private IFilter filter;
   
   private IMolokoEditDialogFragmentListener listener;
   
   private TextView listNameEdit;
   
   private TextView filterEdit;
   
   
   
   public final static AddRenameListDialogFragment newInstance( Bundle config )
   {
      final AddRenameListDialogFragment fragment = new AddRenameListDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public AddRenameListDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AddRenameListDialogFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IMolokoEditDialogFragmentListener )
         listener = (IMolokoEditDialogFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   protected ViewGroup createDialogView( LayoutInflater inflater )
   {
      final View view = inflater.inflate( R.layout.add_rename_list_dialog, null );
      
      final boolean isRenameMode = isRenameMode();
      
      listNameEdit = ( (TextView) view.findViewById( R.id.add_rename_list_list_name ) );
      filterEdit = ( (TextView) view.findViewById( R.id.add_rename_list_smart_filter ) );
      
      if ( isRenameMode )
      {
         configureAsRenameListDialog();
      }
      else
      {
         configureAsNewListDialog();
      }
      
      return (ViewGroup) view;
   }
   
   
   
   @Override
   protected Dialog createDialog( View fragmentView )
   {
      final Activity activity = getSherlockActivity();
      
      final boolean isRenameMode = isRenameMode();
      final String title;
      
      if ( isRenameMode )
      {
         title = getString( R.string.dlg_rename_list_title );
      }
      else if ( filter != null )
      {
         title = getString( R.string.dlg_add_smart_list_title );
      }
      else
      {
         title = getString( R.string.dlg_add_list_title );
      }
      
      return new AlertDialog.Builder( activity ).setIcon( R.drawable.ic_dialog_list )
                                                .setTitle( title )
                                                .setView( fragmentView )
                                                .setPositiveButton( R.string.btn_ok,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          if ( listener != null )
                                                                          {
                                                                             listener.onFinishEditDialogFragment( AddRenameListDialogFragment.this );
                                                                          }
                                                                       }
                                                                    } )
                                                .setNegativeButton( R.string.btn_cancel,
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          if ( listener != null )
                                                                          {
                                                                             listener.onCancelEditDialogFragment( AddRenameListDialogFragment.this );
                                                                          }
                                                                       }
                                                                    } )
                                                .create();
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      listNameEdit.requestFocus();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   private void configureAsRenameListDialog()
   {
      listNameEdit.setText( list.getName() );
      
      // Show only the list name edit, so this will close on IME action
      listNameEdit.setImeActionLabel( filterEdit.getImeActionLabel(),
                                      filterEdit.getImeActionId() );
      filterEdit.setVisibility( View.GONE );
   }
   
   
   
   private void configureAsNewListDialog()
   {
      if ( filter instanceof RtmSmartFilter )
      {
         filterEdit.setText( ( (RtmSmartFilter) filter ).getFilterString() );
      }
   }
   
   
   
   private boolean isRenameMode()
   {
      return list != null;
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      final String trimmedListName = UiUtils.getTrimmedText( listNameEdit );
      
      if ( list != null )
      {
         return !list.getName().equals( trimmedListName );
      }
      else
      {
         return !TextUtils.isEmpty( trimmedListName )
            || !TextUtils.isEmpty( UiUtils.getTrimmedText( filterEdit ) );
      }
   }
   
   
   
   @Override
   protected ApplyChangesInfo getChanges()
   {
      if ( list == null )
      {
         return createNewList();
      }
      else
      {
         return renameList( list );
      }
   }
   
   
   
   @Override
   public ValidationResult validate()
   {
      ValidationResult result = validateListName();
      
      if ( result.isOk() && list == null )
      {
         result = validateSmartFilter();
      }
      
      return result;
   }
   
   
   
   private ValidationResult validateListName()
   {
      final String text = UiUtils.getTrimmedText( listNameEdit );
      
      if ( TextUtils.isEmpty( text ) )
      {
         return new ValidationResult( getString( R.string.dlg_add_rename_list_toast_empty_list_name ),
                                      listNameEdit );
      }
      else
      {
         final String trimmedText = text.trim();
         
         if ( trimmedText.equalsIgnoreCase( getString( R.string.app_list_name_inbox ) )
            || trimmedText.equalsIgnoreCase( getString( R.string.app_list_name_sent ) ) )
         {
            return new ValidationResult( getString( R.string.dlg_add_rename_list_toast_invalid_list_name ),
                                         listNameEdit );
         }
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private ValidationResult validateSmartFilter()
   {
      final String text = UiUtils.getTrimmedText( filterEdit );
      
      if ( !TextUtils.isEmpty( text )
         && RtmSmartFilter.evaluate( text.toString(), false ) == null )
      {
         return new ValidationResult( getString( R.string.dlg_add_rename_list_toast_invalid_filter,
                                                 text ),
                                      filterEdit );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private ApplyChangesInfo createNewList()
   {
      final NewRtmListId newListId = createNewListId();
      final Date createdDate = new Date();
      
      final RtmList newList = new RtmList( newListId.rtmListId,
                                           UiUtils.getTrimmedText( listNameEdit ),
                                           createdDate,
                                           createdDate,
                                           null,
                                           0,
                                           0,
                                           0,
                                           getEnteredSmartFilter() );
      
      return RtmListEditUtils.insertList( getSherlockActivity(), newList );
   }
   
   
   
   private NewRtmListId createNewListId()
   {
      return RtmListsTable.createNewListId( getSherlockActivity().getContentResolver()
                                                                        .acquireContentProviderClient( Lists.CONTENT_URI ) );
   }
   
   
   
   private ApplyChangesInfo renameList( RtmList list )
   {
      return RtmListEditUtils.setListName( getSherlockActivity(),
                                           list.getId(),
                                           UiUtils.getTrimmedText( listNameEdit ) );
   }
   
   
   
   private final RtmSmartFilter getEnteredSmartFilter()
   {
      RtmSmartFilter filter = null;
      
      final String text = UiUtils.getTrimmedText( filterEdit );
      if ( !TextUtils.isEmpty( text ) )
      {
         filter = new RtmSmartFilter( text );
         if ( filter.getEvaluatedFilterString( false ) == null )
            filter = null;
      }
      
      return filter;
   }
}
