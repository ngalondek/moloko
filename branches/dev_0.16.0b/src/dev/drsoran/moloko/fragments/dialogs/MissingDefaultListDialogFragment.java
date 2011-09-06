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
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;


public class MissingDefaultListDialogFragment extends DialogFragment
{
   public final static MissingDefaultListDialogFragment show( FragmentActivity activity )
   {
      final MissingDefaultListDialogFragment fragment = new MissingDefaultListDialogFragment();
      fragment.show( activity.getSupportFragmentManager(),
                     MissingDefaultListDialogFragment.class.getName() );
      
      return fragment;
   }
   


   public final static MissingDefaultListDialogFragment newInstance( Bundle config )
   {
      final MissingDefaultListDialogFragment fragment = new MissingDefaultListDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   


   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setStyle( STYLE_NORMAL, 0 );
   }
   


   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      return new AlertDialog.Builder( getActivity() ).setTitle( R.string.dlg_missing_def_list_title )
                                                     .setIcon( R.drawable.ic_prefs_info )
                                                     .setMessage( R.string.dlg_missing_def_list_text )
                                                     .setPositiveButton( R.string.btn_continue,
                                                                         new OnClickListener()
                                                                         {
                                                                            @Override
                                                                            public void onClick( DialogInterface dialog,
                                                                                                 int which )
                                                                            {
                                                                               final Settings settings = MolokoApp.getSettings();
                                                                               
                                                                               settings.setStartupView( Settings.STARTUP_VIEW_DEFAULT );
                                                                               settings.setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
                                                                            }
                                                                         } )
                                                     .create();
   }
}
