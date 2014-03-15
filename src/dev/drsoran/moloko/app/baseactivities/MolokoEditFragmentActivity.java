/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.baseactivities;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.fragments.IEditFragment;


public abstract class MolokoEditFragmentActivity extends MolokoFragmentActivity
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
   
   
   
   public boolean validateFragment( IEditFragment fragment )
   {
      final ValidationResult validationResult = fragment.validate();
      final boolean valid = validationResult.isOk();
      if ( !valid )
      {
         showValidationError( validationResult );
      }
      
      return valid;
   }
   
   
   
   public void showValidationError( ValidationResult validationResult )
   {
      final View sourceView = validationResult.getSourceView();
      
      if ( sourceView instanceof TextView )
      {
         showValidationErrorInline( (TextView) sourceView,
                                    validationResult.getValidationErrorMessage() );
      }
      else
      {
         showValidationErrorAsToast( validationResult.getValidationErrorMessage() );
      }
      
      final View focusView = validationResult.getRequestFocusOnValidationError();
      
      if ( focusView != null )
      {
         focusView.requestFocus();
      }
   }
   
   
   
   private void showValidationErrorInline( TextView textView,
                                           String validationMessage )
   {
      textView.setError( validationMessage );
   }
   
   
   
   private void showValidationErrorAsToast( String validationMessage )
   {
      Toast.makeText( this, validationMessage, Toast.LENGTH_LONG ).show();
   }
   
   
   
   public boolean finishFragmentEditing( IEditFragment editFragment )
   {
      boolean ok = validateFragment( editFragment );
      
      if ( ok )
      {
         editFragment.onFinishEditing();
      }
      
      return ok;
   }
   
   
   
   public boolean cancelFragmentEditing( IEditFragment editFragment )
   {
      return editFragment.onCancelEditing();
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
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         finish();
      }
   }
   
   
   
   protected void handleDeleteElementDialogClick( String tag, int which )
   {
   }
   
   
   
   @Deprecated
   private boolean canceledByFragmentTag( int which, String tag )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         final Fragment frag = findAddedFragmentByTag( tag );
         if ( frag != null )
         {
            removeFragmentByTag( tag,
                                 FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
         }
         
         return frag != null;
      }
      
      return false;
   }
}
