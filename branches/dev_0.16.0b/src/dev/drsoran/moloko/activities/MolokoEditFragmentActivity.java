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

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.util.Pair;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.fragments.listeners.IEditFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoEditFragmentActivity extends MolokoFragmentActivity
         implements IEditFragmentListener
{
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
         case R.id.dlg_read_only_access:
            handleReadOnlyAccessDialogClick( which );
            break;
         
         case R.id.dlg_apply_changes:
            handleApplyChangesDialogClick( tag, which );
            break;
         
         case R.id.dlg_cancel_with_changes:
            if ( !canceledByFragmentTag( which, tag ) )
               handleCancelWithChangesDialogClick( tag, which );
            break;
         
         case R.id.dlg_delete_element:
            handleDeleteElementDialogClick( tag, which );
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, tag, which );
            break;
      }
   }
   
   
   
   @Override
   public void requestCancelEditing( String fragmentTag )
   {
      UIUtils.showCancelWithChangesDialog( this, fragmentTag );
   }
   
   
   
   @Override
   public boolean applyModifications( ContentProviderActionItemList actionItemList,
                                      ApplyChangesInfo applyInfo )
   {
      boolean ok = true;
      
      if ( !hasWritableDatabaseAccess() )
      {
         ok = false;
         showOnlyReadableDatabaseAccessDialog();
      }
      else
      {
         if ( actionItemList == null )
         {
            ok = false;
            showApplyChangesInfoAsToast( applyInfo, false );
         }
         else if ( actionItemList.size() > 0 )
         {
            final String progressMessage = applyInfo != null
                                                            ? applyInfo.getProgressMessage()
                                                            : null;
            ok = Queries.applyActionItemList( this,
                                              actionItemList,
                                              progressMessage );
            showApplyChangesInfoAsToast( applyInfo, ok );
         }
      }
      
      return ok;
   }
   
   
   
   public boolean applyModifications( Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications )
   {
      return applyModifications( modifications.first, modifications.second );
   }
   
   
   
   private void showApplyChangesInfoAsToast( ApplyChangesInfo applyChangesInfo,
                                             boolean success )
   {
      if ( applyChangesInfo != null )
      {
         applyChangesInfo.showApplyResultToast( this, success );
      }
   }
   
   
   
   private boolean hasWritableDatabaseAccess()
   {
      return AccountUtils.isWriteableAccess( this );
   }
   
   
   
   protected void showOnlyReadableDatabaseAccessDialog()
   {
      UIUtils.showReadOnlyAccessDialog( this );
   }
   
   
   
   private void handleReadOnlyAccessDialogClick( int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         startActivity( Intents.createOpenSystemAccountSettingsIntent() );
      }
   }
   
   
   
   protected void handleApplyChangesDialogClick( String tag, int which )
   {
   }
   
   
   
   protected void handleCancelWithChangesDialogClick( String tag, int which )
   {
   }
   
   
   
   protected void handleDeleteElementDialogClick( String tag, int which )
   {
   }
   
   
   
   private boolean canceledByFragmentTag( int which, String tag )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         final Fragment frag = findAddedFragmentByTag( tag );
         if ( frag != null )
            removeFragmentByTag( tag,
                                 FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
         
         return frag != null;
      }
      
      return false;
   }
}
