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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class AboutMolokoDialogFragment extends DialogFragment
{
   
   public final static AboutMolokoDialogFragment newInstance( Bundle config )
   {
      final AboutMolokoDialogFragment fragment = new AboutMolokoDialogFragment();
      
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
      final Activity context = getActivity();
      
      final TextView content = new TextView( context );
      content.setLayoutParams( new LayoutParams( LayoutParams.FILL_PARENT,
                                                 LayoutParams.FILL_PARENT ) );
      content.setPadding( 10, 10, 10, 10 );
      content.setTextSize( 16.0f );
      
      try
      {
         content.setText( Html.fromHtml( context.getString( R.string.moloko_about_info,
                                                            context.getPackageManager()
                                                                   .getPackageInfo( context.getPackageName(),
                                                                                    0 ).versionName ) ) );
         content.setMovementMethod( LinkMovementMethod.getInstance() );
         
         return new AlertDialog.Builder( context ).setTitle( context.getString( R.string.moloko_about_text ) )
                                                  .setIcon( R.drawable.ic_prefs_info )
                                                  .setPositiveButton( context.getString( R.string.phr_ok ),
                                                                      null )
                                                  .setView( content )
                                                  .create();
      }
      catch ( NameNotFoundException e )
      {
         
      }
      
      return null;
   }
}
