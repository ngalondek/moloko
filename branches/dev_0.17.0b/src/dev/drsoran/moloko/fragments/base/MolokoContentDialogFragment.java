/* 
 *	Copyright (c) 2012 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.fragments.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IOnSettingsChangedListener;


public abstract class MolokoContentDialogFragment extends MolokoDialogFragment
         implements IOnSettingsChangedListener
{
   private ViewGroup dialogView;
   
   
   
   @Override
   public final Dialog onCreateDialog( Bundle savedInstanceState )
   {
      dialogView = createContent( LayoutInflater.from( getFragmentActivity() ) );
      onContentCreated( dialogView );
      
      final Dialog dialog = createDialog( dialogView );
      return dialog;
   }
   
   
   
   protected void onContentCreated( ViewGroup dialogView )
   {
   }
   
   
   
   public ViewGroup getDialogView()
   {
      return dialogView;
   }
   
   
   
   @Override
   public ViewGroup getContentView()
   {
      return super.getContentView();
   }
   
   
   
   protected abstract ViewGroup createContent( LayoutInflater inflater );
   
   
   
   protected abstract Dialog createDialog( View fragmentView );
}