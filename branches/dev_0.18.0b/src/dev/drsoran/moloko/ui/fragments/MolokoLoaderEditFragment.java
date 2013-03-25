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
import android.os.Bundle;
import android.view.View;
import dev.drsoran.moloko.app.content.ApplyContentChangesInfo;
import dev.drsoran.moloko.ui.fragments.impl.EditFragmentImpl;


public abstract class MolokoLoaderEditFragment< D > extends
         MolokoLoaderFragment< D > implements IEditFragment
{
   private final EditFragmentImpl impl;
   
   
   
   protected MolokoLoaderEditFragment()
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
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      impl.onViewCreated( view, savedInstanceState );
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
   public final ApplyContentChangesInfo onFinishEditing()
   {
      if ( hasChanges() )
      {
         return getApplyChangesInfo();
      }
      
      return ApplyContentChangesInfo.EMPTY;
   }
   
   
   
   @Override
   public void onCancelEditing()
   {
   }
   
   
   
   protected abstract ApplyContentChangesInfo getApplyChangesInfo();
}
