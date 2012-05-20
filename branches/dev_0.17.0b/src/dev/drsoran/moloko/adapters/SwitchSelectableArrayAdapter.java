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

package dev.drsoran.moloko.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


abstract class SwitchSelectableArrayAdapter< T > extends
         SelectableArrayAdapter< T > implements ISwitchSelectableAdapter
{
   private final LayoutInflater inflater;
   
   private final int unselectedResourceId;
   
   private final int selectedResourceId;
   
   private boolean isSelectable;
   
   
   
   protected SwitchSelectableArrayAdapter( Context context,
      int unselectedResourceId, int selectedResourceId, List< T > items )
   {
      super( context, items );
      
      this.unselectedResourceId = unselectedResourceId;
      this.selectedResourceId = selectedResourceId;
      this.inflater = ( (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) );
   }
   
   
   
   public LayoutInflater getLayoutInflater()
   {
      return inflater;
   }
   
   
   
   @Override
   public boolean isSelectable()
   {
      return isSelectable;
   }
   
   
   
   @Override
   public void setSelectable( boolean isSelectable )
   {
      if ( this.isSelectable != isSelectable )
      {
         this.isSelectable = isSelectable;
         notifyDataSetChanged();
      }
   }
   
   
   
   @Override
   public int getUnselectedLayoutRessource()
   {
      return unselectedResourceId;
   }
   
   
   
   @Override
   public int getSelectedLayoutRessource()
   {
      return selectedResourceId;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null || mustSwitchLayout( convertView ) )
      {
         convertView = createListItemView( parent );
      }
      
      final T item = getItem( position );
      initCheckBox( item, (ViewGroup) convertView );
      
      return convertView;
   }
   
   
   
   private View createListItemView( ViewGroup parent )
   {
      if ( isSelectable() )
      {
         return inflater.inflate( selectedResourceId, parent, false );
      }
      else
      {
         return inflater.inflate( unselectedResourceId, parent, false );
      }
   }
   
   
   
   private void initCheckBox( final T item, ViewGroup listItemView )
   {
      final CheckBox checkBoxView = (CheckBox) listItemView.findViewById( android.R.id.checkbox );
      
      if ( checkBoxView != null )
      {
         checkBoxView.setVisibility( isSelectable ? View.VISIBLE : View.GONE );
         
         if ( isSelectable )
         {
            // Note: Setting the checkChangedListener to null before initializing
            // the checked mark prevents notification from recycled convert views.
            checkBoxView.setOnCheckedChangeListener( null );
            checkBoxView.setChecked( isSelected( item ) );
            checkBoxView.setOnCheckedChangeListener( new OnCheckedChangeListener()
            {
               @Override
               public void onCheckedChanged( CompoundButton buttonView,
                                             boolean isChecked )
               {
                  if ( isChecked )
                  {
                     select( item );
                  }
                  else
                  {
                     deselect( item );
                  }
               }
            } );
         }
         else
         {
            checkBoxView.setOnCheckedChangeListener( null );
         }
      }
   }
   
   
   
   protected abstract boolean mustSwitchLayout( View convertView );
}
