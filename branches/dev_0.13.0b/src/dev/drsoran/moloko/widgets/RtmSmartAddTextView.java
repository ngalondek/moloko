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

package dev.drsoran.moloko.widgets;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;


public class RtmSmartAddTextView extends MultiAutoCompleteTextView
{
   private final Map< Integer, ArrayAdapter< String > > operatorAdapters = new HashMap< Integer, ArrayAdapter< String > >();
   
   

   public RtmSmartAddTextView( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   


   public RtmSmartAddTextView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   


   public RtmSmartAddTextView( Context context )
   {
      super( context );
      
   }
   


   ArrayAdapter< String > addApdapterForOperator( int operator )
   {
      ArrayAdapter< String > adapter = operatorAdapters.get( Integer.valueOf( operator ) );
      
      if ( adapter == null )
      {
         adapter = new ArrayAdapter< String >( getContext(),
                                               android.R.layout.simple_dropdown_item_1line,
                                               android.R.id.text1 );
         operatorAdapters.put( Integer.valueOf( operator ), adapter );
      }
      
      return adapter;
   }
}
