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

package dev.drsoran.moloko.fragments.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.fragments.listeners.IEditFragmentListener;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoLoaderEditFragment< T extends Fragment, D > extends
         MolokoLoaderFragment< D > implements IEditFragment< T >
{
   private IEditFragmentListener listener;
   
   
   
   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IEditFragmentListener )
         listener = (IEditFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      UIUtils.hideSoftInput( getContentView() );
      
      super.onDestroyView();
   }
   
   
   
   @Override
   public final boolean onFinishEditing()
   {
      boolean ok = true;
      
      if ( hasChanges() )
      {
         ok = saveChanges();
      }
      
      return ok;
   }
   
   
   
   @Override
   public void onCancelEditing()
   {
   }
   
   
   
   protected boolean applyModifications( ContentProviderActionItemList actionItemList,
                                         ApplyChangesInfo applyChangesInfo )
   {
      boolean ok = listener != null;
      return ok
         && listener.applyModifications( actionItemList, applyChangesInfo );
   }
   
   
   
   protected boolean applyModifications( Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications )
   {
      return applyModifications( modifications.first, modifications.second );
   }
   
   
   
   protected abstract boolean saveChanges();
}
