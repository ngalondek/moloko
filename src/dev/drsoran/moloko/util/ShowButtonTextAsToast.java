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

package dev.drsoran.moloko.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ShowButtonTextAsToast
{
   private final Context context;
   
   
   
   public ShowButtonTextAsToast( Context context )
   {
      this.context = context;
   }
   
   
   
   public void show( Button button )
   {
      show( button, button.getText() );
   }
   
   
   
   @SuppressLint( "ShowToast" )
   public void show( View view, CharSequence text )
   {
      final Toast cheatSheet = Toast.makeText( context,
                                               text,
                                               Toast.LENGTH_SHORT );
      showToast( cheatSheet, view );
   }
   
   
   
   private void showToast( Toast cheatSheet, View view )
   {
      final int[] screenPos = new int[ 2 ];
      final Rect displayFrame = new Rect();
      
      view.getLocationOnScreen( screenPos );
      view.getWindowVisibleDisplayFrame( displayFrame );
      
      final int width = view.getWidth();
      final int height = view.getHeight();
      final int midy = screenPos[ 1 ] + height / 2;
      final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
      
      if ( midy < displayFrame.height() )
      {
         // Show along the top; follow action buttons
         cheatSheet.setGravity( Gravity.TOP | Gravity.RIGHT, screenWidth
            - screenPos[ 0 ] - width / 2, height );
      }
      else
      {
         // Show along the bottom center
         cheatSheet.setGravity( Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                                0,
                                height );
      }
      
      cheatSheet.show();
   }
}
