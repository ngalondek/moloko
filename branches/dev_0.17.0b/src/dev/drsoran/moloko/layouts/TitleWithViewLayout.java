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


public abstract class TitleWithViewLayout extends LinearLayout
{
   protected TitleWithViewLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      LayoutInflater.from( context ).inflate( R.layout.title_with_view,
                                              this,
                                              true );
      
      initImpl( context, attrs );
   }
   
   
   
   protected TitleWithViewLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs );
      
      LayoutInflater.from( context ).inflate( R.layout.title_with_view,
                                              root,
                                              true );
      
      initImpl( context, attrs );
   }
   
   
   
   public TextView getTitleView()
   {
      return (TextView) findViewById( R.id.title_with_view_title );
   }
   
   
   
   public abstract View getView();
   
   
   
   protected ViewGroup getViewContainer()
   {
      return (ViewGroup) findViewById( R.id.title_with_view_container );
   }
   
   
   
   private void initImpl( Context context, AttributeSet attrs )
   {
      setOrientation( VERTICAL );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleWithView,
                                                               R.attr.titleWithViewStyle,
                                                               0 );
      
      // Image
      setImage( array.getDrawable( R.styleable.TitleWithView_imageSrc ) );
      
      // Top line
      setTopLine( array );
      
      final TextView text = (TextView) findViewById( R.id.title_with_view_title );
      
      // Title
      setAttr( context, text, array, new int[]
      { R.styleable.TitleWithView_titleText,
       R.styleable.TitleWithView_titleColor,
       R.styleable.TitleWithView_titleSize,
       R.styleable.TitleWithView_titleStyle,
       R.styleable.TitleWithView_titlePaddingTop } );
      
      // Padding
      if ( array.hasValue( R.styleable.TitleWithView_allPadding ) )
      {
         final int padSpc = array.getDimensionPixelSize( R.styleable.TitleWithView_allPadding,
                                                         0 );
         setPadding( padSpc, padSpc, padSpc, padSpc );
      }
      
      // Margin
      if ( array.hasValue( R.styleable.TitleWithView_viewMarginTop ) )
      {
         ( (LinearLayout.LayoutParams) text.getLayoutParams() ).bottomMargin = array.getDimensionPixelSize( R.styleable.TitleWithView_viewMarginTop,
                                                                                                            0 );
      }
      
      array.recycle();
   }
   
   
   
   private void setImage( Drawable drawable )
   {
      final ImageView imageView = (ImageView) findViewById( R.id.title_with_view_image );
      
      if ( imageView != null )
      {
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
   }
   
   
   
   private void setTopLine( TypedArray array )
   {
      final View topLine = findViewById( R.id.title_with_view_top_line );
      
      if ( topLine != null )
      {
         if ( array.getBoolean( R.styleable.TitleWithView_showTopLine, true ) )
         {
            ( (GradientDrawable) topLine.getBackground() ).setColor( array.getColor( R.styleable.TitleWithView_topLineColor,
                                                                                     R.color.app_default_line ) );
            topLine.getLayoutParams().height = array.getDimensionPixelSize( R.styleable.TitleWithView_topLineHeight,
                                                                            1 );
         }
         else
         {
            topLine.setVisibility( View.GONE );
         }
      }
   }
   
   
   
   protected final static void setAttr( Context context,
                                        TextView view,
                                        TypedArray array,
                                        int[] attrs )
   {
      final String text = array.getString( attrs[ 0 ] );
      
      if ( text == null )
         view.setVisibility( View.GONE );
      else
         view.setText( text );
      
      if ( array.hasValue( attrs[ 1 ] ) )
      {
         view.setTextColor( array.getColor( attrs[ 1 ], 0 ) );
      }
      
      if ( array.hasValue( attrs[ 2 ] ) )
      {
         final float spDensity = context.getResources().getDisplayMetrics().scaledDensity;
         final float size = array.getDimension( attrs[ 2 ], 0.0f ) / spDensity;
         view.setTextSize( size );
      }
      
      if ( array.hasValue( attrs[ 3 ] ) )
      {
         final Typeface typeface = Typeface.create( (String) null,
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
