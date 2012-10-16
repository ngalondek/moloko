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

package dev.drsoran.moloko.adapters.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;


public abstract class SwappableArrayAdapter< T > extends ArrayAdapter< T >
{
   protected SwappableArrayAdapter( Context context )
   {
      super( context, View.NO_ID );
   }
   
   
   
   protected SwappableArrayAdapter( Context context, int textViewResourceId )
   {
      super( context, textViewResourceId );
   }
   
   
   
   protected SwappableArrayAdapter( Context context, List< T > objects )
   {
      super( context, View.NO_ID, objects );
   }
   
   
   
   protected SwappableArrayAdapter( Context context, int resource,
      int textViewResourceId, List< T > objects )
   {
      super( context, resource, textViewResourceId, objects );
   }
   
   
   
   protected SwappableArrayAdapter( Context context, int resource,
      int textViewResourceId )
   {
      super( context, resource, textViewResourceId );
   }
   
   
   
   protected SwappableArrayAdapter( Context context, int textViewResourceId,
      List< T > objects )
   {
      super( context, textViewResourceId, objects );
   }
   
   
   
   @Override
   public final boolean hasStableIds()
   {
      return true;
   }
   
   
   
   public void swap( List< T > newData )
   {
      setNotifyOnChange( false );
      
      clear();
      for ( T data : newData )
      {
         add( data );
      }
      
      setNotifyOnChange( true );
      
      notifyDataSetChanged();
   }
   
   
   
   @Override
   public abstract long getItemId( int position );
}
