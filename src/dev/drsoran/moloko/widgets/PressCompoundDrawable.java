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

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.UIUtils;


public final class PressCompoundDrawable
{
   private boolean clearBtnDown = false;
   
   private final TextView textView;
   
   private Runnable unsetPressed;
   
   private boolean isShown;
   
   
   
   public PressCompoundDrawable( TextView textView )
   {
      if ( textView == null )
         throw new NullPointerException( "textView is null" );
      
      this.textView = textView;
      
      if ( textView.length() > 0 )
         show();
      else
         hide();
   }
   
   
   
   public final void show()
   {
      textView.setCompoundDrawablesWithIntrinsicBounds( 0,
                                                        0,
                                                        R.drawable.app_edittext_clear,
                                                        0 );
      isShown = true;
   }
   
   
   
   public final void hide()
   {
      textView.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
      isShown = false;
   }
   
   
   
   public final boolean isShown()
   {
      return isShown;
   }
   
   
   
   public boolean onTouchEvent( MotionEvent event )
   {
      final Drawable clearDrawable = textView.getCompoundDrawables()[ 2 ];
      if ( clearDrawable != null
         && ( event.getX() > textView.getMeasuredWidth()
            - textView.getPaddingRight() - clearDrawable.getIntrinsicWidth()
            - textView.getCompoundDrawablePadding() ) )
      {
         switch ( event.getAction() )
         {
            case MotionEvent.ACTION_DOWN:
               clearBtnDown = true;
               break;
            
            case MotionEvent.ACTION_UP:
               textView.clearComposingText();
               textView.getEditableText().clear();
               textView.requestFocus();
               
            default :
               clearBtnDown = false;
               break;
         }
         
         textView.refreshDrawableState();
         
         return true;
      }
      
      return false;
   }
   
   
   
   public int[] onCreateDrawableState()
   {
      if ( clearBtnDown )
      {
         if ( unsetPressed == null )
         {
            unsetPressed = new Runnable()
            {
               @Override
               public void run()
               {
                  textView.setPressed( false );
               }
            };
         }
         
         if ( textView.getHandler() != null )
            textView.getHandler().post( unsetPressed );
         
         return UIUtils.CHECKED_STATE_SET;
      }
      
      return null;
   }
}
