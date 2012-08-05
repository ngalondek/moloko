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

package dev.drsoran.moloko.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;


public class AboutMolokoDialogFragment extends MolokoDialogFragment
{
   
   public final static AboutMolokoDialogFragment newInstance( Bundle config )
   {
      final AboutMolokoDialogFragment fragment = new AboutMolokoDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      final Activity context = getSherlockActivity();
      
      Spanned message = null;
      
      try
      {
         message = Html.fromHtml( context.getString( R.string.moloko_about_info,
                                                     context.getPackageManager()
                                                            .getPackageInfo( context.getPackageName(),
                                                                             0 ).versionName ) );
      }
      catch ( NameNotFoundException e )
      {
      }
      
      final Dialog dialog = new AlertDialog.Builder( context ).setIcon( R.drawable.ic_launcher )
                                                              .setTitle( context.getString( R.string.moloko_about_text ) )
                                                              .setMessage( message )
                                                              .setNeutralButton( context.getString( R.string.phr_ok ),
                                                                                 null )
                                                              .create();
      
      return dialog;
   }
}
