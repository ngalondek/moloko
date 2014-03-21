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

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public abstract class LayoutSwitchMultiChoiceModalAdapter< T > extends
         MultiChoiceModalArrayAdapter< T >
{
   private final int checkedResourceId;
   
   
   
   protected LayoutSwitchMultiChoiceModalAdapter( ListView listView,
      int uncheckedResourceId, int checkedResourceId )
   {
      super( listView, uncheckedResourceId );
      this.checkedResourceId = checkedResourceId;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null || mustSwitchLayout( convertView ) )
      {
         convertView = createListItemView( parent );
      }
      
      initCheckable( (ViewGroup) convertView, position );
      
      return convertView;
   }
   
   
   
   private View createListItemView( ViewGroup parent )
   {
      if ( isInMultiChoiceModalActionMode() )
      {
         return getLayoutInflater().inflate( checkedResourceId, parent, false );
      }
      else
      {
         return getLayoutInflater().inflate( getResourceId(), parent, false );
      }
   }
   
   
   
   protected abstract boolean mustSwitchLayout( View convertView );
}
