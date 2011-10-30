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

package dev.drsoran.moloko.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;


public class NoAccountDialogFragment extends MolokoDialogFragment
{
   public final static NoAccountDialogFragment show( FragmentActivity activity )
   {
      final NoAccountDialogFragment fragment = new NoAccountDialogFragment();
      fragment.show( activity.getSupportFragmentManager(),
                     NoAccountDialogFragment.class.getName() );
      
      return fragment;
   }
   
   
   
   public final static NoAccountDialogFragment newInstance( Bundle config )
   {
      final NoAccountDialogFragment fragment = new NoAccountDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      return new AlertDialog.Builder( getFragmentActivity() ).setTitle( R.string.dlg_no_account_title )
                                                             .setIcon( R.drawable.rtm )
                                                             .setMessage( R.string.dlg_no_account_text )
                                                             .setPositiveButton( R.string.btn_new_account,
                                                                                 getGenericOnClickListener() )
                                                             .setNegativeButton( R.string.dlg_no_account_btn_no_account,
                                                                                 getGenericOnClickListener() )
                                                             .create();
   }
}
