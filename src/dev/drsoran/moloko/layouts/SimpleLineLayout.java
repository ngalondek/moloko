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
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import dev.drsoran.moloko.R;


public class SimpleLineLayout extends LinearLayout
{
   public SimpleLineLayout( Context context, AttributeSet attrs )
   {
      this( context, attrs, null );
   }
   
   
   
   public SimpleLineLayout( Context context, AttributeSet attrs, ViewGroup root )
   {
      super( context, attrs );
      
      if ( root == null )
      {
         root = this;
      }
      
      final View content = LayoutInflater.from( context )
                                         .inflate( R.layout.app_simple_line,
                                                   root,
                                                   false );
      
      initImpl( context, attrs, content );
      
      root.addView( content, content.getLayoutParams() );
   }
   
   
   
   private void initImpl( Context context, AttributeSet attrs, View line )
   {
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.SimpleLine,
                                                               0,
                                                               0 );
      
      final int color = array.getColor( R.styleable.SimpleLine_lineColor,
                                        context.getResources()
                                               .getColor( R.color.app_default_line ) );
      
      ( (GradientDrawable) line.getBackground() ).setColor( color );
      
      array.recycle();
      
      setLayoutParams( generateLayoutParams( attrs ) );
   }
}
