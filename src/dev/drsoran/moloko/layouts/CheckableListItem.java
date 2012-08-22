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

package dev.drsoran.moloko.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import dev.drsoran.moloko.R;


public class CheckableListItem extends LinearLayout implements Checkable
{
   private Checkable delegate;
   
   
   
   public CheckableListItem( Context context )
   {
      super( context );
   }
   
   
   
   public CheckableListItem( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   public CheckableListItem( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   @Override
   protected void onAttachedToWindow()
   {
      super.onAttachedToWindow();
      
      if ( !isInEditMode() )
         delegate = (CheckBox) findViewById( R.id.checkable );
   }
   
   
   
   @Override
   public boolean isChecked()
   {
      return delegate.isChecked();
   }
   
   
   
   @Override
   public void setChecked( boolean checked )
   {
      delegate.setChecked( checked );
   }
   
   
   
   @Override
   public void toggle()
   {
      delegate.toggle();
   }
}
