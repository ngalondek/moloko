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
import android.view.ViewGroup;
import dev.drsoran.moloko.ui.fragments.impl.EditFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.RtmAccessLevelFragmentImpl;


public abstract class MolokoLoaderEditDialogFragment< D > extends
         MolokoLoaderDialogFragment< D > implements IEditFragment
{
   private final EditFragmentImpl impl;
   
   private final RtmAccessLevelFragmentImpl accessImpl;
   
   
   
   public MolokoLoaderEditDialogFragment()
   {
      impl = new EditFragmentImpl( this );
      accessImpl = new RtmAccessLevelFragmentImpl();
   }
   
   
   
   @Override
   public void onDialogViewCreated( ViewGroup dialogView )
   {
      super.onDialogViewCreated( dialogView );
      impl.onViewCreated( dialogView, null );
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      impl.onDestroyView();
      super.onDestroyView();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      impl.onAttach( activity );
      accessImpl.onAttach( activity );
   }
   
   
   
   @Override
   public void onDestroy()
   {
      accessImpl.onDetach();
      impl.onDestroy();
      
      super.onDestroy();
   }
   
   
   
   @Override
   public void onDetach()
   {
      impl.onDetach();
      super.onDetach();
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
   public void onCancelEditing()
   {
   }
   
   
   
   @Override
   public boolean hasWritableAccess()
   {
      return accessImpl.hasWritableAccess();
   }
   
   
   
   protected abstract void applyChanges();
}
