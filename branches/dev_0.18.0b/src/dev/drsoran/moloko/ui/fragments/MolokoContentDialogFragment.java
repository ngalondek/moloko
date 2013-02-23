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

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class MolokoContentDialogFragment extends MolokoDialogFragment
{
   private ViewGroup dialogView;
   
   
   
   @Override
   public final Dialog onCreateDialog( Bundle savedInstanceState )
   {
      dialogView = createDialogView( LayoutInflater.from( getSherlockActivity() ) );
      onDialogViewCreated( dialogView );
      
      final Dialog dialog = createDialog( dialogView );
      return dialog;
   }
   
   
   
   protected void onDialogViewCreated( ViewGroup dialogView )
   {
   }
   
   
   
   public ViewGroup getDialogView()
   {
      return dialogView;
   }
   
   
   
   public ViewGroup getContentView()
   {
      return (ViewGroup) dialogView.findViewById( android.R.id.content );
   }
   
   
   
   protected abstract ViewGroup createDialogView( LayoutInflater inflater );
   
   
   
   protected abstract Dialog createDialog( View fragmentView );
}
