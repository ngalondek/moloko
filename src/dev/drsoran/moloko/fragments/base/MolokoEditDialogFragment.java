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
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoEditDialogFragment< T extends Fragment > extends
         MolokoDialogFragment implements IEditFragment< T >
{
   
   public final ViewGroup getContentView()
   {
      final View root = getView();
      
      if ( root != null )
         return (ViewGroup) root.findViewById( android.R.id.content );
      else
         return null;
   }
   


   @Override
   public final boolean onFinishEditing()
   {
      boolean ok = validateInput();
      
      if ( ok && hasChanges() )
      {
         if ( !hasWritableDatabaseAccess() )
         {
            showOnlyReadableDatabaseAccessDialog();
            ok = false;
         }
         else
         {
            ok = saveChanges();
         }
      }
      
      if ( ok )
         getDialog().dismiss();
      
      return ok;
   }
   


   @Override
   public final void onCancelEditing()
   {
      if ( hasChanges() )
      {
         UIUtils.showCancelWithChangesDialog( getFragmentActivity(),
                                              new Runnable()
                                              {
                                                 @Override
                                                 public void run()
                                                 {
                                                    getDialog().cancel();
                                                 }
                                              },
                                              null );
      }
      else
      {
         getDialog().cancel();
      }
   }
   


   protected boolean validateInput()
   {
      return true;
   }
   


   private boolean hasWritableDatabaseAccess()
   {
      return AccountUtils.isWriteableAccess( getFragmentActivity() );
   }
   


   protected void showOnlyReadableDatabaseAccessDialog()
   {
      UIUtils.showReadOnlyAccessDialog( getFragmentActivity() );
   }
   


   protected abstract boolean saveChanges();
}
