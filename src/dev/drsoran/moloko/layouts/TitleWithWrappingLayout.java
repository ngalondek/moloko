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
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;


public class TitleWithWrappingLayout extends TitleWithViewLayout
{
   private WrappingLayout.LayoutParams wrappingLayoutParams = new WrappingLayout.LayoutParams( 1,
                                                                                               1 );
   
   private WrappingLayout wrappingLayout;
   
   

   public TitleWithWrappingLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      initView( context, attrs, getViewContainer() );
   }
   


   public TitleWithWrappingLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs, root );
      initView( context, attrs, getViewContainer() );
   }
   


   @Override
   public void setEnabled( boolean enabled )
   {
      super.setEnabled( enabled );
      wrappingLayout.setEnabled( false );
   }
   


   private void initView( Context context,
                          AttributeSet attrs,
                          ViewGroup container )
   {
      wrappingLayout = new WrappingLayout( context );
      wrappingLayout.setId( R.id.title_with_wrapping_layout );
      
      // WrappingLayout
      TypedArray array = context.obtainStyledAttributes( attrs,
                                                         R.styleable.WrappingLayout,
                                                         0,
                                                         0 );
      
      final int hor_spc = array.getDimensionPixelOffset( R.styleable.WrappingLayout_horizontal_spacing,
                                                         1 );
      final int ver_spc = array.getDimensionPixelOffset( R.styleable.WrappingLayout_vertical_spacing,
                                                         1 );
      
      wrappingLayoutParams = new WrappingLayout.LayoutParams( hor_spc, ver_spc );
      
      array.recycle();
      
      container.addView( wrappingLayout );
   }
   


   @Override
   public void addView( View child )
   {
      child.setLayoutParams( wrappingLayoutParams );
      wrappingLayout.addView( child );
   }
   


   @Override
   public void removeAllViews()
   {
      wrappingLayout.removeAllViews();
   }
   
}
