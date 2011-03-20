/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.dialogs;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import dev.drsoran.moloko.R;


public class MultiChoiceDialog implements DialogInterface,
         OnMultiChoiceClickListener, OnClickListener
{
   private final Context context;
   
   private final AlertDialog impl;
   
   private final CharSequence items[];
   
   private final boolean states[];
   
   private OnClickListener closeListener;
   
   

   public MultiChoiceDialog( Context context, List< CharSequence > items,
      OnClickListener onClickListener )
   {
      this.context = context;
      
      this.items = new CharSequence[ items.size() ];
      items.toArray( this.items );
      
      this.states = new boolean[ items.size() ];
      
      this.impl = new AlertDialog.Builder( context ).setMultiChoiceItems( this.items,
                                                                          this.states,
                                                                          this )
                                                    .create();
      this.closeListener = onClickListener;
      
      setButtonText( this.context.getString( R.string.phr_ok ) );
   }
   


   public void show()
   {
      impl.show();
   }
   


   public MultiChoiceDialog setIcon( int resId )
   {
      impl.setIcon( resId );
      return this;
   }
   


   public MultiChoiceDialog setTitle( CharSequence text )
   {
      impl.setTitle( text );
      return this;
   }
   


   public MultiChoiceDialog setButtonText( CharSequence text )
   {
      impl.setButton( text, this );
      return this;
   }
   


   public MultiChoiceDialog setButton2Text( CharSequence text )
   {
      impl.setButton2( text, this );
      return this;
   }
   


   public void onClick( DialogInterface dialog, int which, boolean isChecked )
   {
      this.states[ which ] = isChecked;
   }
   


   public CharSequence[] getItems()
   {
      return items;
   }
   


   public boolean[] getStates()
   {
      return states;
   }
   


   public void onClick( DialogInterface dialog, int which )
   {
      if ( this.closeListener != null )
      {
         this.closeListener.onClick( this, which );
      }
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
