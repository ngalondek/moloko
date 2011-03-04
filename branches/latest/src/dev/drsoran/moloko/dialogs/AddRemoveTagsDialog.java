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
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dev.drsoran.moloko.R;


public class AddRemoveTagsDialog implements DialogInterface
{
   private final Context context;
   
   private final Dialog impl;
   
   

   public AddRemoveTagsDialog( Context context )
   {
      this.context = context;
      this.impl = new Dialog( context );
      this.impl.setContentView( R.layout.add_rem_tag_dlg );
      
      final ListView tagList = (ListView) this.impl.findViewById( R.id.add_rem_tag_dlg_tag_list );
      
      tagList.setAdapter( new ArrayAdapter< String >( context,
                                                      R.layout.add_rem_tag_dlg_listitem,
                                                      R.id.add_rem_tag_dlg_listitem_tag,
                                                      new String[]
                                                      { "tag1", "tagggasga",
                                                       "ddd" } ) );
   }
   


   public void show()
   {
      impl.show();
   }
   


   public void cancel()
   {
      impl.cancel();
   }
   


   public void dismiss()
   {
      impl.dismiss();
   }
}
