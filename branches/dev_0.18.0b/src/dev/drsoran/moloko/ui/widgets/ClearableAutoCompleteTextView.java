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
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.SystemContext;


public class ClearableAutoCompleteTextView extends AutoCompleteTextView
{
   private final EditTextFocusHandler editTextFocusHandler;
   
   private ClearButtonCompoundDrawable clearButton;
   
   
   
   public ClearableAutoCompleteTextView( Context context )
   {
      this( context, null, android.R.attr.autoCompleteTextViewStyle );
   }
   
   
   
   public ClearableAutoCompleteTextView( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.autoCompleteTextViewStyle );
   }
   
   
   
   public ClearableAutoCompleteTextView( Context context, AttributeSet attrs,
      int defStyle )
   {
      super( context, attrs, defStyle );
      init( attrs );
      
      IHandlerToken handlerToken = SystemContext.get( context )
                                                .acquireHandlerToken();
      editTextFocusHandler = new EditTextFocusHandler( this, handlerToken );
   }
   
   
   
   @Override
   protected void onDetachedFromWindow()
   {
      editTextFocusHandler.shutdown();
      super.onDetachedFromWindow();
   }
   
   
   
   public String getTextTrimmed()
   {
      return super.getText().toString().trim();
   }
   
   
   
   @Override
   public boolean onTouchEvent( MotionEvent event )
   {
      if ( clearButton != null )
         return clearButton.onTouchEvent( event ) || super.onTouchEvent( event );
      else
         return false;
   }
   
   
   
   @Override
   protected void onTextChanged( CharSequence text,
                                 int start,
                                 int lengthBefore,
                                 int lengthAfter )
   {
      super.onTextChanged( text, start, lengthBefore, lengthAfter );
      
      if ( clearButton != null )
      {
         final boolean clearButtonIsShown = clearButton.isShown();
         
         if ( clearButtonIsShown && text.length() == 0 )
            clearButton.hide();
         else if ( !clearButtonIsShown && text.length() > 0 )
            clearButton.show();
      }
   }
   
   
   
   @Override
   protected int[] onCreateDrawableState( int extraSpace )
   {
      if ( clearButton != null )
      {
         final int[] clearButtonState = clearButton.onCreateDrawableState();
         
         if ( clearButtonState != null )
         {
            final int[] drawableState = super.onCreateDrawableState( extraSpace
               + clearButtonState.length );
            
            mergeDrawableStates( drawableState, clearButtonState );
            
            return drawableState;
         }
      }
      
      return super.onCreateDrawableState( extraSpace );
   }
   
   
   
   private void init( AttributeSet attrs )
   {
      clearButton = new ClearButtonCompoundDrawable( this, attrs );
   }
}
