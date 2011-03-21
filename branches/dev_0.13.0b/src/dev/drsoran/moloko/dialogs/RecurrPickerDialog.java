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

package dev.drsoran.moloko.dialogs;

import kankan.wheel.widget.WheelView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Strings;


public class RecurrPickerDialog extends AbstractPickerDialog
{
   private AlertDialog impl;
   
   private WheelView intervalWheel;
   
   

   public RecurrPickerDialog( Context context, String pattern )
   {
      init( context, pattern );
   }
   


   private void init( final Context context, String pattern )
   {
      final LayoutInflater inflater = LayoutInflater.from( context );
      final View view = inflater.inflate( R.layout.recurr_picker_dialog, null );
      
      intervalWheel = (WheelView) view.findViewById( R.id.recurr_dlg_interval_wheel );
      
      initIntervalWheel( context );
      
      this.impl = new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_time )
                                                    .setTitle( R.string.dlg_recurr_picker_title )
                                                    .setView( view )
                                                    .setPositiveButton( R.string.btn_ok,
                                                                        new OnClickListener()
                                                                        {
                                                                           public void onClick( DialogInterface dialog,
                                                                                                int which )
                                                                           {
                                                                              notifyOnDialogCloseListener( CloseReason.OK,
                                                                                                           Strings.EMPTY_STRING );
                                                                           }
                                                                        } )
                                                    .setNegativeButton( R.string.btn_cancel,
                                                                        new OnClickListener()
                                                                        {
                                                                           public void onClick( DialogInterface dialog,
                                                                                                int which )
                                                                           {
                                                                              notifyOnDialogCloseListener( CloseReason.CANCELED,
                                                                                                           null );
                                                                           }
                                                                        } )
                                                    .create();
   }
   


   @Override
   public void show()
   {
      impl.show();
   }
   


   private void initIntervalWheel( Context context )
   {
      
   }
}
