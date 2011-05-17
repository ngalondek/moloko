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

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;


public class AddRenameListDialog
{
   
   private final Context context;
   
   private final Dialog impl;
   
   // If list is != null we are in rename mode otherwise in add mode
   private final RtmList list;
   
   

   public AddRenameListDialog( Context context, RtmList list )
   {
      this.context = context;
      this.list = list;
      
      impl = new Dialog( context );
      impl.setTitle( ( list == null ) ? R.string.dlg_add_list_title
                                     : R.string.dlg_rename_list_title );
      impl.setContentView( R.layout.add_rename_list_dialog );
      
      if ( list != null )
      {
         ( (TextView) impl.findViewById( R.id.add_rename_list_list_name ) ).setText( list.getName() );
         
         impl.findViewById( R.id.add_rename_list_smart_filter )
             .setVisibility( View.GONE );
      }
      
      impl.findViewById( R.id.btn_left )
          .setOnClickListener( new OnClickListener()
          {
             public void onClick( View v )
             {
                onOK();
             }
          } );
      impl.findViewById( R.id.btn_right )
          .setOnClickListener( new OnClickListener()
          {
             public void onClick( View v )
             {
                onCancel();
             }
          } );
   }
   


   public void cancel()
   {
      onCancel();
   }
   


   public void show()
   {
      impl.show();
   }
   


   private void onOK()
   {
      if ( validateInput() )
      {
         impl.dismiss();
      }
   }
   


   private void onCancel()
   {
      
      impl.cancel();
   }
   


   private boolean validateInput()
   {
      // Validate the list name
      {
         final TextView listName = (TextView) impl.findViewById( R.id.add_rename_list_list_name );
         final String text = UIUtils.getTrimmedText( listName );
         
         if ( TextUtils.isEmpty( text ) )
         {
            Toast.makeText( context,
                            R.string.dlg_add_rename_list_toast_empty_list_name,
                            Toast.LENGTH_LONG ).show();
            listName.requestFocus();
            return false;
         }
         else
         {
            final String trimmedText = text.trim();
            
            if ( trimmedText.equalsIgnoreCase( context.getString( R.string.app_list_name_inbox ) )
               || trimmedText.equalsIgnoreCase( context.getString( R.string.app_list_name_sent ) ) )
            {
               Toast.makeText( context,
                               R.string.dlg_add_rename_list_toast_invalid_list_name,
                               Toast.LENGTH_LONG )
                    .show();
               listName.requestFocus();
               return false;
            }
         }
      }
      
      // Validate the smart filter
      if ( list == null )
      {
         final TextView filter = (TextView) impl.findViewById( R.id.add_rename_list_smart_filter );
         final String text = UIUtils.getTrimmedText( filter );
         
         if ( !TextUtils.isEmpty( text ) )
         {
            if ( RtmSmartFilter.evaluate( text.toString(), false ) == null )
            {
               Toast.makeText( context,
                               context.getString( R.string.dlg_add_rename_list_toast_invalid_filter,
                                                  text ),
                               Toast.LENGTH_LONG )
                    .show();
               filter.requestFocus();
               return false;
            }
         }
      }
      
      return true;
   }
}
