/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class TitleWithTextLayout extends TitleWithViewLayout
{
   public TitleWithTextLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      initView( context, attrs, getViewContainer() );
   }
   


   public TitleWithTextLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs, root );
      initView( context, attrs, getViewContainer() );
   }
   


   private void initView( Context context,
                          AttributeSet attrs,
                          ViewGroup container )
   {
      final TextView text = new TextView( context );
      text.setId( R.id.title_with_text_text );
      text.setAutoLinkMask( Linkify.ALL );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleWithText,
                                                               0,
                                                               0 );
      // Text
      setAttr( context, text, array, new int[]
      { R.styleable.TitleWithText_text, R.styleable.TitleWithText_textColor,
       R.styleable.TitleWithText_textSize, R.styleable.TitleWithText_textStyle,
       R.styleable.TitleWithText_textPaddingTop } );
      
      array.recycle();
      
      container.addView( text );
   }
   
}
