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

package dev.drsoran.moloko.app.taskedit;

import android.app.Activity;
import dev.drsoran.moloko.app.taskedit.IPickerDialogListener.CloseReason;
import dev.drsoran.moloko.ui.fragments.MolokoDialogFragment;


abstract class AbstractPickerDialogFragment extends MolokoDialogFragment
{
   private IPickerDialogListener listener;
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IPickerDialogListener )
         listener = (IPickerDialogListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   
   
   
   protected void notifiyDialogClosedOk()
   {
      if ( listener != null )
         listener.onPickerDialogClosed( this, CloseReason.OK );
   }
   
   
   
   protected void notifiyDialogClosedCancel()
   {
      if ( listener != null )
         listener.onPickerDialogClosed( this, CloseReason.CANCELED );
   }
}
