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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.UiUtils;


public final class ClearButtonCompoundDrawable
{
   
   private final TextView textView;
   
   private Drawable clearButtonDrawable;
   
   private Runnable unsetPressed;
   
   private boolean clearBtnDown;
   
   private boolean isShown;
   
   
   
   public ClearButtonCompoundDrawable( TextView textView )
   {
      this( textView, null, R.attr.clearButtonStyle );
   }
   
   
   
   public ClearButtonCompoundDrawable( TextView textView, AttributeSet attrs )
   {
      this( textView, attrs, R.attr.clearButtonStyle );
   }
   
   
   
   public ClearButtonCompoundDrawable( TextView textView, AttributeSet attrs,
      int defStyle )
   {
      this.textView = textView;
      initClearButtonDrawable( attrs, defStyle );
   }
   
   
   
   private void initClearButtonDrawable( AttributeSet attrs, int defStyle )
   {
      final Context context = textView.getContext();
      final TypedArray styleArray = context.obtainStyledAttributes( attrs,
                                                                    R.styleable.ClearButtonCompoundDrawable,
                                                                    defStyle,
                                                                    0 );
      
      final Drawable drawablePressed = styleArray.getDrawable( R.styleable.ClearButtonCompoundDrawable_drawablePressed );
      final Drawable drawableUnPressed = styleArray.getDrawable( R.styleable.ClearButtonCompoundDrawable_drawableUnpressed );
      
      final StateListDrawable selector = new StateListDrawable();
      selector.addState( new int[]
      { android.R.attr.state_checked }, drawablePressed );
      selector.addState( new int[]
      { -android.R.attr.state_checked }, drawableUnPressed );
      
      clearButtonDrawable = selector;
      
      styleArray.recycle();
   }
   
   
   
   public final void show()
   {
      final Drawable[] compoundDrawables = textView.getCompoundDrawables();
      textView.setCompoundDrawablesWithIntrinsicBounds( compoundDrawables[ 0 ],
                                                        compoundDrawables[ 1 ],
                                                        clearButtonDrawable,
                                                        compoundDrawables[ 3 ] );
      isShown = true;
   }
   
   
   
   public final void hide()
   {
      final Drawable[] compoundDrawables = textView.getCompoundDrawables();
      textView.setCompoundDrawablesWithIntrinsicBounds( compoundDrawables[ 0 ],
                                                        compoundDrawables[ 1 ],
                                                        compoundDrawables[ 2 ],
                                                        compoundDrawables[ 3 ] );
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
         
         textView.post( unsetPressed );
         return UiUtils.CHECKED_STATE_SET;
      }
      
      return null;
   }
}
