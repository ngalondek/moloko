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
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.QwertyKeyListener;
import android.util.AttributeSet;


public class RtmSmartAddTextView extends ClearableMultiAutoCompleteTextView
{
   private Tokenizer tokenizer;
   
   
   
   public RtmSmartAddTextView( Context context )
   {
      super( context );
   }
   
   
   
   public RtmSmartAddTextView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   public RtmSmartAddTextView( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   @Override
   public void setTokenizer( Tokenizer t )
   {
      super.setTokenizer( t );
      tokenizer = t;
   }
   
   
   
   @Override
   protected void replaceText( CharSequence text )
   {
      clearComposingText();
      
      final Editable editable = getText();
      
      final int end = getSelectionEnd();
      final int start = tokenizer.findTokenStart( editable, end );
      final String original = TextUtils.substring( editable, start, end );
      
      // If the original to be replaced starts with an operator, we pass
      // the operator to the tokenizer, otherwise it would get lost.
      if ( original.length() > 0
         && RtmSmartAddTokenizer.isOperator( original.charAt( 0 ), null ) )
      {
         text = TextUtils.concat( String.valueOf( original.charAt( 0 ) ), text );
      }
      
      QwertyKeyListener.markAsReplaced( editable, start, end, original );
      editable.replace( start, end, tokenizer.terminateToken( text ) );
   }
}
