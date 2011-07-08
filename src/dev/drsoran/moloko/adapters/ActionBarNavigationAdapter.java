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

package dev.drsoran.moloko.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class ActionBarNavigationAdapter extends
         ArrayAdapter< ActionBarNavigationAdapter.Item >
{
   public final static class Item
   {
      public final int iconResId;
      
      public final String itemText;
      
      

      public Item( int iconResId, String itemText )
      {
         this.iconResId = iconResId;
         this.itemText = itemText;
      }
      


      public static Item fromString( String itemText )
      {
         return new Item( -1, itemText );
      }
   }
   
   

   public ActionBarNavigationAdapter( Context context,
      List< ActionBarNavigationAdapter.Item > list )
   {
      super( context, 0, list );
   }
   


   @Override
   public View getDropDownView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
         convertView = View.inflate( getContext(),
                                     android.R.layout.simple_dropdown_item_1line,
                                     null );
      
      final Item item = getItem( position );
      
      final TextView itemTextView = (TextView) convertView.findViewById( android.R.id.text1 );
      itemTextView.setText( item.itemText );
      
      if ( item.iconResId != -1 )
      {
         itemTextView.setCompoundDrawables( getContext().getResources()
                                                        .getDrawable( item.iconResId ),
                                            null,
                                            null,
                                            null );
         itemTextView.setCompoundDrawablePadding( 5 );
      }
      
      return convertView;
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
         convertView = View.inflate( getContext(),
                                     R.layout.simple_spinner_item,
                                     null );
      
      final Item item = getItem( position );
      final TextView itemTextView = (TextView) convertView.findViewById( android.R.id.text1 );
      itemTextView.setText( item.itemText );
      
      return convertView;
   }
}
