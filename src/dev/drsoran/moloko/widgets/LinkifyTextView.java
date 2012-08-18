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

package dev.drsoran.moloko.widgets;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;


public class LinkifyTextView extends TextView
{
   public LinkifyTextView( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
      init();
   }
   
   
   
   public LinkifyTextView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      init();
   }
   
   
   
   public LinkifyTextView( Context context )
   {
      super( context );
      init();
   }
   
   
   
   @Override
   public boolean onTouchEvent( MotionEvent event )
   {
      final int action = event.getAction();
      
      if ( action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN )
      {
         int x = (int) event.getX();
         int y = (int) event.getY();
         
         x -= getTotalPaddingLeft();
         y -= getTotalPaddingTop();
         
         x += getScrollX();
         y += getScrollY();
         
         final Layout layout = getLayout();
         int line = layout.getLineForVertical( y );
         int off = layout.getOffsetForHorizontal( line, x );
         
         final CharSequence text = getText();
         if ( text instanceof Spannable )
         {
            final Spannable buffer = (Spannable) getText();
            final ClickableSpan[] link = buffer.getSpans( off,
                                                          off,
                                                          ClickableSpan.class );
            
            if ( link.length != 0 )
            {
               if ( action == MotionEvent.ACTION_UP )
               {
                  link[ 0 ].onClick( this );
               }
               else if ( action == MotionEvent.ACTION_DOWN )
               {
                  Selection.setSelection( buffer,
                                          buffer.getSpanStart( link[ 0 ] ),
                                          buffer.getSpanEnd( link[ 0 ] ) );
               }
               
               return true;
            }
            else
            {
               Selection.removeSelection( buffer );
            }
         }
      }
      
      return super.onTouchEvent( event );
   }
   
   
   
   private void init()
   {
      setLinksClickable( false );
   }
}
