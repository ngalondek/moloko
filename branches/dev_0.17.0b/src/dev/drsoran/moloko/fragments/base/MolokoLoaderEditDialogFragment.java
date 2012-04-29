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

import android.app.Activity;
import android.view.ViewGroup;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.fragments.base.impl.EditFragmentImpl;


public abstract class MolokoLoaderEditDialogFragment< D > extends
         MolokoLoaderDialogFragment< D > implements IEditFragment
{
   private final EditFragmentImpl impl;
   
   
   
   public MolokoLoaderEditDialogFragment()
   {
      impl = new EditFragmentImpl( this );
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
   }
   
   
   
   @Override
   public void onDetach()
   {
      impl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public final ApplyChangesInfo onFinishEditing()
   {
      if ( hasChanges() )
      {
         return getChanges();
      }
      
      return ApplyChangesInfo.EMPTY;
   }
   
   
   
   @Override
   public void onCancelEditing()
   {
   }
   
   
   
   protected abstract ApplyChangesInfo getChanges();
}
