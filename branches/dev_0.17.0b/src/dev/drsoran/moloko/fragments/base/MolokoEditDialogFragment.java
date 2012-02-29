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

package dev.drsoran.moloko.fragments.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.content.ContentProviderActionItemList;


public abstract class MolokoEditDialogFragment< T extends Fragment > extends
         MolokoContentDialogFragment implements IEditFragment< T >
{
   private final EditFragmentImpl impl;
   
   
   
   protected MolokoEditDialogFragment()
   {
      impl = new EditFragmentImpl( this );
   }
   
   
   
   @Override
   public void onAttach( SupportActivity activity )
   {
      super.onAttach( activity );
      impl.onAttach( activity.asActivity() );
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
   public final boolean onFinishEditing()
   {
      boolean ok = validateInput();
      
      if ( ok && hasChanges() )
      {
         ok = saveChanges();
      }
      
      if ( ok )
      {
         getDialog().dismiss();
      }
      
      return ok;
   }
   
   
   
   @Override
   public final void onCancelEditing()
   {
      if ( hasChanges() )
      {
         requestCancelEditing();
      }
      else
      {
         getDialog().cancel();
      }
   }
   
   
   
   public void showShoftInput( View view )
   {
      impl.showShoftInput( view );
   }
   
   
   
   protected boolean validateInput()
   {
      return true;
   }
   
   
   
   protected boolean applyModifications( ContentProviderActionItemList actionItemList,
                                         ApplyChangesInfo applyChangesInfo )
   {
      return impl.applyModifications( actionItemList, applyChangesInfo );
   }
   
   
   
   protected boolean applyModifications( Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications )
   {
      return impl.applyModifications( modifications.first, modifications.second );
   }
   
   
   
   protected void requestCancelEditing()
   {
      impl.requestCancelEditing( getTag() );
   }
   
   
   
   @Override
   protected void onDialogViewCreated( ViewGroup dialogView )
   {
      impl.setWindowToken( dialogView.getWindowToken() );
   }
   
   
   
   protected abstract boolean saveChanges();
}
