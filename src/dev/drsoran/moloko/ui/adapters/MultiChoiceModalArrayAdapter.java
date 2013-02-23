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

package dev.drsoran.moloko.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Checkable;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.widgets.MolokoListView;


public abstract class MultiChoiceModalArrayAdapter< T > extends
         SwappableArrayAdapter< T >
{
   private final MolokoListView listView;
   
   private final LayoutInflater inflater;
   
   private final int resourceId;
   
   
   
   protected MultiChoiceModalArrayAdapter( MolokoListView listView,
      int resourceId )
   {
      super( listView.getContext() );
      
      this.listView = listView;
      this.resourceId = resourceId;
      this.inflater = ( (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE ) );
   }
   
   
   
   public LayoutInflater getLayoutInflater()
   {
      return inflater;
   }
   
   
   
   public boolean isMultiChoiceModalMode()
   {
      return listView.getChoiceMode() == MolokoListView.CHOICE_MODE_MULTIPLE_MODAL;
   }
   
   
   
   public boolean isInMultiChoiceModalActionMode()
   {
      return listView.isInMultiChoiceModalActionMode();
   }
   
   
   
   public int getResourceId()
   {
      return resourceId;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
      {
         convertView = inflater.inflate( resourceId, parent, false );
      }
      
      initCheckable( (ViewGroup) convertView, position );
      
      return convertView;
   }
   
   
   
   protected void initCheckable( ViewGroup listItemView, final int position )
   {
      final View checkableView = listItemView.findViewById( R.id.checkable );
      
      if ( checkableView != null )
      {
         checkableView.setVisibility( isMultiChoiceModalMode() ? View.VISIBLE
                                                              : View.GONE );
         
         // Setting this to null will clear out any convert views first
         checkableView.setOnClickListener( null );
         
         if ( isMultiChoiceModalMode() )
         {
            checkableView.setOnClickListener( new OnClickListener()
            {
               @Override
               public void onClick( View v )
               {
                  MultiChoiceModalArrayAdapter.this.listView.setItemChecked( position,
                                                                             ( (Checkable) checkableView ).isChecked() );
               }
            } );
         }
      }
   }
}
