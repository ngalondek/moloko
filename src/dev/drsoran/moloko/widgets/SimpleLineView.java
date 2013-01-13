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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import dev.drsoran.moloko.R;


public class SimpleLineView extends View
{
   private ShapeDrawable lineDrawable;
   
   
   
   public SimpleLineView( Context context )
   {
      this( context, null, R.attr.simpleLineStyle );
   }
   
   
   
   public SimpleLineView( Context context, AttributeSet attrs )
   {
      this( context, attrs, R.attr.simpleLineStyle );
   }
   
   
   
   public SimpleLineView( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.SimpleLine,
                                                               0,
                                                               0 );
      
      final int color = array.getColor( R.styleable.SimpleLine_lineColor,
                                        context.getResources()
                                               .getColor( R.color.app_default_line ) );
      array.recycle();
      
      initLineDrawable( color );
   }
   
   
   
   public void setLineColor( int color )
   {
      lineDrawable.getPaint().setColor( color );
      invalidateDrawable( lineDrawable );
   }
   
   
   
   public int getLineColor()
   {
      return lineDrawable.getPaint().getColor();
   }
   
   
   
   private void initLineDrawable( int color )
   {
      lineDrawable = new ShapeDrawable( new RectShape() );
      setLineColor( color );
   }
   
   
   
   @Override
   protected void onDraw( Canvas canvas )
   {
      lineDrawable.setBounds( 0, 0, getWidth(), getHeight() );
      lineDrawable.draw( canvas );
   }
}
