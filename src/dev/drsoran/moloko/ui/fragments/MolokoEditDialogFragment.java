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

package dev.drsoran.moloko.ui.fragments;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.fragments.impl.EditFragmentImpl;


public abstract class MolokoEditDialogFragment extends
         MolokoContentDialogFragment implements IEditFragment
{
   private final EditFragmentImpl impl;
   
   
   
   protected MolokoEditDialogFragment()
   {
      impl = new EditFragmentImpl( this );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      impl.onAttach( activity );
   }
   
   
   
   @Override
   public void onDetach()
   {
      impl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      impl.onDestroyView();
      super.onDestroyView();
   }
   
   
   
   @Override
   public void onDestroy()
   {
      impl.onDestroy();
      super.onDestroy();
   }
   
   
   
   @Override
   public final void onFinishEditing()
   {
      if ( hasChanges() )
      {
         applyChanges();
      }
      
      getDialog().dismiss();
   }
   
   
   
   @Override
   public ValidationResult validate()
   {
      return ValidationResult.OK;
   }
   
   
   
   @Override
   public final void onCancelEditing()
   {
   }
   
   
   
   public void showShoftInput( View view )
   {
      impl.showShoftInput( view );
   }
   
   
   
   @Override
   protected void onDialogViewCreated( ViewGroup dialogView )
   {
      impl.setWindowToken( dialogView.getWindowToken() );
   }
   
   
   
   @Override
   public boolean hasWritableAccess()
   {
      return impl.hasWritableAccess();
   }
   
   
   
   protected abstract void applyChanges();
}
