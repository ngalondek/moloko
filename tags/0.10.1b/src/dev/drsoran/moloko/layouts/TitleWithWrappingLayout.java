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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class TitleWithWrappingLayout extends LinearLayout
{
   private WrappingLayout.LayoutParams wrappingLayoutParams = new WrappingLayout.LayoutParams( 1,
                                                                                               1 );
   
   private final WrappingLayout wrappingLayout;
   
   

   public TitleWithWrappingLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.title_with_wrapping_layout, this, true );
      
      wrappingLayout = (WrappingLayout) findViewById( R.id.wrapping_container );
      
      init( context, attrs );
   }
   


   public TitleWithWrappingLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.title_with_wrapping_layout, root, true );
      
      wrappingLayout = (WrappingLayout) findViewById( R.id.wrapping_container );
      
      init( context, attrs );
   }
   


   private void init( Context context, AttributeSet attrs )
   {
      setOrientation( VERTICAL );
      
      TypedArray array = context.obtainStyledAttributes( attrs,
                                                         R.styleable.TitleWithText,
                                                         0,
                                                         0 );
      
      // Image
      setImage( array.getDrawable( R.styleable.TitleWithText_imageSrc ) );
      
      // Top line
      {
         final View topLine = findViewById( R.id.title_with_text_top_line );
         
         if ( array.getBoolean( R.styleable.TitleWithText_showTopLine, true ) )
         {
            ( (GradientDrawable) topLine.getBackground() ).setColor( array.getColor( R.styleable.TitleWithText_topLineColor,
                                                                                     R.color.app_default_line ) );
            topLine.getLayoutParams().height = array.getDimensionPixelSize( R.styleable.TitleWithText_topLineHeight,
                                                                            1 );
         }
         else
         {
            topLine.setVisibility( View.GONE );
         }
      }
      
      // Title
      {
         setAttr( context,
                  (TextView) findViewById( R.id.title_with_text_title ),
                  array,
                  new int[]
                  { R.styleable.TitleWithText_title,
                   R.styleable.TitleWithText_titleColor,
                   R.styleable.TitleWithText_titleSize,
                   R.styleable.TitleWithText_titleStyle,
                   R.styleable.TitleWithText_titlePaddingTop } );
      }
      
      array.recycle();
      
      // WrappingLayout
      {
         array = context.obtainStyledAttributes( attrs,
                                                 R.styleable.WrappingLayout,
                                                 0,
                                                 0 );
         
         final int hor_spc = array.getDimensionPixelOffset( R.styleable.WrappingLayout_horizontal_spacing,
                                                            1 );
         final int ver_spc = array.getDimensionPixelOffset( R.styleable.WrappingLayout_vertical_spacing,
                                                            1 );
         
         wrappingLayoutParams = new WrappingLayout.LayoutParams( hor_spc,
                                                                 ver_spc );
         array.recycle();
      }
      
   }
   


   @Override
   public void addView( View child )
   {
      child.setLayoutParams( wrappingLayoutParams );
      wrappingLayout.addView( child );
   }
   


   private void setImage( Drawable drawable )
   {
      final ImageView imageView = (ImageView) findViewById( R.id.title_with_text_image );
      
      if ( drawable != null )
      {
         imageView.setVisibility( VISIBLE );
         imageView.setImageDrawable( drawable );
      }
      else
      {
         imageView.setVisibility( GONE );
      }
   }
   


   private final static void setAttr( Context context,
                                      TextView view,
                                      TypedArray array,
                                      int[] attrs )
   {
      final String text = array.getString( attrs[ 0 ] );
      
      setTextViewText( view, text );
      
      if ( text != null )
      {
         if ( array.hasValue( attrs[ 1 ] ) )
         {
            view.setTextColor( array.getColor( attrs[ 1 ], 0 ) );
         }
         
         if ( array.hasValue( attrs[ 2 ] ) )
         {
            final float spDensity = context.getResources().getDisplayMetrics().scaledDensity;
            final float size = array.getDimension( attrs[ 2 ], 0.0f )
               / spDensity;
            view.setTextSize( size );
         }
         
         if ( array.hasValue( attrs[ 3 ] ) )
         {
            final Typeface typeface = Typeface.create( "",
                                                       array.getInt( attrs[ 3 ],
                                                                     Typeface.NORMAL ) );
            view.setTypeface( typeface );
         }
         
         if ( array.hasValue( attrs[ 4 ] ) )
         {
            view.setPadding( 0,
                             array.getDimensionPixelOffset( attrs[ 4 ], 0 ),
                             0,
                             0 );
         }
      }
   }
   


   private final static void setTextViewText( TextView view, String text )
   {
      if ( text == null )
         view.setVisibility( View.GONE );
      else
         view.setText( text );
   }
}
