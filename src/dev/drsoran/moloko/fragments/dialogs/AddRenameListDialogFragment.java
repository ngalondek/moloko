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

package dev.drsoran.moloko.fragments.dialogs;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.content.RtmListsProviderPart.NewRtmListId;
import dev.drsoran.moloko.fragments.base.MolokoEditDialogFragment;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class AddRenameListDialogFragment extends
         MolokoEditDialogFragment< AddRenameListDialogFragment >
{
   public static class Config
   {
      public final static String LIST = "list";
      
      public final static String FILTER = "filter";
   }
   
   private TextView listNameEdit;
   
   private TextView filterEdit;
   
   
   
   public final static AddRenameListDialogFragment newInstance( Bundle config )
   {
      final AddRenameListDialogFragment fragment = new AddRenameListDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   protected ViewGroup createContent( LayoutInflater inflater )
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
      final Activity activity = getFragmentActivity();
      
      final boolean isRenameMode = isRenameMode();
      final String title = isRenameMode
                                       ? getString( R.string.dlg_rename_list_title )
                                       : getString( R.string.dlg_add_list_title );
      
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
                                                                          AddRenameListDialogFragment.this.onFinishEditing();
                                                                       }
                                                                    } )
                                                .setNegativeButton( R.string.btn_cancel,
                                                                    null )
                                                .create();
   }
   
   
   
   private void configureAsRenameListDialog()
   {
      listNameEdit.setText( getConfiguredList().getName() );
      
      // Show only the list name edit, so this will close on IME action
      listNameEdit.setImeActionLabel( filterEdit.getImeActionLabel(),
                                      filterEdit.getImeActionId() );
      filterEdit.setVisibility( View.GONE );
   }
   
   
   
   private void configureAsNewListDialog()
   {
      final IFilter filter = getConfiguredFilter();
      
      if ( filter instanceof RtmSmartFilter )
         filterEdit.setText( ( (RtmSmartFilter) filter ).getFilterString() );
   }
   
   
   
   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.LIST ) )
         configuration.putParcelable( Config.LIST,
                                      config.getParcelable( Config.LIST ) );
      if ( config.containsKey( Config.FILTER ) )
         configuration.putParcelable( Config.FILTER,
                                      config.getParcelable( Config.FILTER ) );
   }
   
   
   
   private boolean isRenameMode()
   {
      return getConfiguredList() != null;
   }
   
   
   
   private RtmList getConfiguredList()
   {
      return configuration.getParcelable( Config.LIST );
   }
   
   
   
   private IFilter getConfiguredFilter()
   {
      return configuration.getParcelable( Config.FILTER );
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      final String trimmedListName = UIUtils.getTrimmedText( listNameEdit );
      final RtmList list = getConfiguredList();
      
      if ( list != null )
         return !list.getName().equals( trimmedListName );
      else
         return !TextUtils.isEmpty( trimmedListName )
            || !TextUtils.isEmpty( UIUtils.getTrimmedText( filterEdit ) );
   }
   
   
   
   @Override
   protected boolean saveChanges()
   {
      final RtmList list = getConfiguredList();
      
      if ( list == null )
         return createNewList();
      else
         return renameList( list );
   }
   
   
   
   @Override
   protected boolean validateInput()
   {
      boolean ok = validateListName();
      
      if ( ok && getConfiguredList() == null )
         ok = validateSmartFilter();
      
      return ok;
   }
   
   
   
   private boolean validateListName()
   {
      final String text = UIUtils.getTrimmedText( listNameEdit );
      
      if ( TextUtils.isEmpty( text ) )
      {
         Toast.makeText( getFragmentActivity(),
                         R.string.dlg_add_rename_list_toast_empty_list_name,
                         Toast.LENGTH_LONG ).show();
         listNameEdit.requestFocus();
         return false;
      }
      else
      {
         final String trimmedText = text.trim();
         
         if ( trimmedText.equalsIgnoreCase( getString( R.string.app_list_name_inbox ) )
            || trimmedText.equalsIgnoreCase( getString( R.string.app_list_name_sent ) ) )
         {
            Toast.makeText( getFragmentActivity(),
                            R.string.dlg_add_rename_list_toast_invalid_list_name,
                            Toast.LENGTH_LONG )
                 .show();
            listNameEdit.requestFocus();
            return false;
         }
      }
      
      return true;
   }
   
   
   
   private boolean validateSmartFilter()
   {
      final String text = UIUtils.getTrimmedText( filterEdit );
      
      if ( !TextUtils.isEmpty( text ) )
      {
         if ( RtmSmartFilter.evaluate( text.toString(), false ) == null )
         {
            Toast.makeText( getFragmentActivity(),
                            getString( R.string.dlg_add_rename_list_toast_invalid_filter,
                                       text ),
                            Toast.LENGTH_LONG )
                 .show();
            filterEdit.requestFocus();
            return false;
         }
      }
      
      return true;
   }
   
   
   
   private boolean createNewList()
   {
      boolean ok = true;
      
      final NewRtmListId newListId = createNewListId();
      final Date createdDate = new Date();
      
      final RtmList newList = new RtmList( newListId.rtmListId,
                                           UIUtils.getTrimmedText( listNameEdit ),
                                           createdDate,
                                           createdDate,
                                           null,
                                           0,
                                           0,
                                           0,
                                           getEnteredSmartFilter() );
      
      final Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications = RtmListEditUtils.insertList( getFragmentActivity(),
                                                                                                                 newList );
      ok = applyModifications( modifications );
      
      return ok;
   }
   
   
   
   private NewRtmListId createNewListId()
   {
      return RtmListsProviderPart.createNewListId( getFragmentActivity().getContentResolver()
                                                                        .acquireContentProviderClient( Lists.CONTENT_URI ) );
   }
   
   
   
   private boolean renameList( RtmList list )
   {
      final Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications = RtmListEditUtils.setListName( getFragmentActivity(),
                                                                                                                  list.getId(),
                                                                                                                  UIUtils.getTrimmedText( listNameEdit ) );
      return applyModifications( modifications );
   }
   
   
   
   private final RtmSmartFilter getEnteredSmartFilter()
   {
      RtmSmartFilter filter = null;
      
      final String text = UIUtils.getTrimmedText( filterEdit );
      if ( !TextUtils.isEmpty( text ) )
      {
         filter = new RtmSmartFilter( text );
         if ( filter.getEvaluatedFilterString( false ) == null )
            filter = null;
      }
      
      return filter;
   }
   
   
   
   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      return null;
   }
}
