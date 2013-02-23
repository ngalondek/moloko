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

package dev.drsoran.moloko.ui.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.ui.fragments.MolokoDialogFragment;


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
      final SherlockFragmentActivity context = getSherlockActivity();
      
      final View aboutMolokoView = inflateAboutContent( context );
      
      final Dialog dialog = new AlertDialog.Builder( context ).setIcon( R.drawable.ic_launcher )
                                                              .setTitle( context.getString( R.string.moloko_about_text ) )
                                                              .setView( aboutMolokoView )
                                                              .setNeutralButton( context.getString( R.string.phr_ok ),
                                                                                 null )
                                                              .create();
      
      return dialog;
   }
   
   
   
   public static View inflateAboutContent( Context context )
   {
      final ContextThemeWrapper themedContext = new ContextThemeWrapper( context,
                                                                         0 );
      themedContext.setTheme( R.style.Theme_Moloko_Dialog );
      
      final LayoutInflater inflater = LayoutInflater.from( themedContext );
      final View aboutMolokoView = inflater.inflate( R.layout.about_dialog_fragment,
                                                     null,
                                                     false );
      
      final Spanned message = Html.fromHtml( themedContext.getString( R.string.moloko_about_info,
                                                                      MolokoApp.getVersionName( context ) ) );
      
      final TextView messageView = (TextView) aboutMolokoView.findViewById( android.R.id.text1 );
      messageView.setText( message );
      messageView.setMovementMethod( LinkMovementMethod.getInstance() );
      
      return aboutMolokoView;
   }
}
