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
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.fragments.listeners.IEditFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoEditFragmentActivity extends MolokoFragmentActivity
         implements IEditFragmentListener
{
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, int which )
   {
      switch ( dialogId )
      {
         case R.id.dlg_read_only_access:
            handleReadOnlyAccessDialogClick( which );
            break;
         
         case R.id.dlg_apply_changes:
            handleApplyChangesDialogClick( which );
            break;
         
         case R.id.dlg_cancel_with_changes:
            handleCancelWithChangesDialogClick( which );
            break;
         
         case R.id.dlg_delete_element:
            handleDeleteElementDialogClick( which );
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, which );
            break;
      }
   }
   


   @Override
   public void applyModifications( ModificationSet modificationSet,
                                   ApplyChangesInfo applyInfo )
   {
      if ( !hasWritableDatabaseAccess() )
      {
         showOnlyReadableDatabaseAccessDialog();
      }
      else
      {
         final boolean ok = Queries.applyModifications( this,
                                                        modificationSet,
                                                        applyInfo.getProgressMessage() );
         applyInfo.showApplyResultToast( this, ok );
      }
   }
   


   protected void performDatabaseModification( Runnable action )
   {
      if ( action != null )
      {
         if ( !hasWritableDatabaseAccess() )
         {
            showOnlyReadableDatabaseAccessDialog();
         }
         else
         {
            action.run();
         }
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
   


   protected void handleApplyChangesDialogClick( int which )
   {
   }
   


   protected void handleCancelWithChangesDialogClick( int which )
   {
   }
   


   protected void handleDeleteElementDialogClick( int which )
   {
   }
}
