package dev.drsoran.moloko.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class TitleBarLayout extends LinearLayout
{
   
   public TitleBarLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.app_titlebar, this, true );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleBar,
                                                               0,
                                                               0 );
      
      final String titleText = array.getString( R.styleable.TitleBar_titleText );
      if ( titleText != null )
         ( (TextView) findViewById( R.id.app_titlebar_text ) ).setText( titleText );
      
      final int showButtons = array.getInt( R.styleable.TitleBar_showButton, 0 );
      
      // Show search button
      if ( ( showButtons & 1 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_search );
         setVisible( R.id.app_titlebar_btn_search );
      }
      
      array.recycle();
   }
   


   private void setVisible( int id )
   {
      findViewById( id ).setVisibility( VISIBLE );
   }
}
