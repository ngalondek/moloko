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

package dev.drsoran.moloko.app.prefs;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.fragments.dialogs.AboutMolokoDialogFragment;


class AboutPreference extends InfoTextPreference
{
   
   public AboutPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   protected void onClick()
   {
      final Context context = getContext();
      final View contentView = AboutMolokoDialogFragment.inflateAboutContent( context );
      
      new AlertDialog.Builder( context ).setIcon( R.drawable.ic_prefs_info )
                                        .setTitle( R.string.moloko_about_text )
                                        .setView( contentView )
                                        .setPositiveButton( context.getString( R.string.phr_ok ),
                                                            null )
                                        .show();
   }
}
