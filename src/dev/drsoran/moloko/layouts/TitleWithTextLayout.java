package dev.drsoran.moloko.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class TitleWithTextLayout extends LinearLayout
{
   
   public TitleWithTextLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      setOrientation( VERTICAL );
      
      LayoutInflater.from( context ).inflate( R.layout.title_with_text,
                                              this,
                                              true );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleWithText,
                                                               0,
                                                               0 );
      
      // Top line
      {
         final View topLine = (View) findViewById( R.id.title_with_text_top_line );
         
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
         setAttr( (TextView) findViewById( R.id.title_with_text_title ),
                  array,
                  new int[]
                  { R.styleable.TitleWithText_title,
                   R.styleable.TitleWithText_titleColor,
                   R.styleable.TitleWithText_titleSize,
                   R.styleable.TitleWithText_titleStyle,
                   R.styleable.TitleWithText_titlePaddingTop } );
      }
      
      // Text
      {
         setAttr( (TextView) findViewById( R.id.title_with_text_text ),
                  array,
                  new int[]
                  { R.styleable.TitleWithText_text,
                   R.styleable.TitleWithText_textColor,
                   R.styleable.TitleWithText_textSize,
                   R.styleable.TitleWithText_textStyle,
                   R.styleable.TitleWithText_textPaddingTop } );
      }
      
      array.recycle();
   }
   


   private final static void setAttr( TextView view,
                                      TypedArray array,
                                      int[] attrs )
   {
      String text = array.getString( attrs[ 0 ] );
      if ( text == null )
         view.setVisibility( View.GONE );
      else
      {
         view.setText( text );
         
         if ( array.hasValue( attrs[ 1 ] ) )
         {
            view.setTextColor( array.getColor( attrs[ 1 ], 0 ) );
         }
         
         if ( array.hasValue( attrs[ 2 ] ) )
         {
            view.setTextSize( array.getDimension( attrs[ 2 ], 0.0f ) );
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
}
