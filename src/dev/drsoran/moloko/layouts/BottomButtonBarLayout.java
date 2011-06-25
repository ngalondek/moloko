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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import dev.drsoran.moloko.R;


public class BottomButtonBarLayout extends FrameLayout
{
   private ViewGroup content;
   
   private Button button1;
   
   private Button button2;
   
   

   public BottomButtonBarLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      LayoutInflater.from( context ).inflate( R.layout.app_bottom_button_bar,
                                              this,
                                              true );
      
      initImpl( context, attrs );
   }
   


   public BottomButtonBarLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs );
      
      LayoutInflater.from( context ).inflate( R.layout.app_bottom_button_bar,
                                              root,
                                              true );
      
      initImpl( context, attrs );
   }
   


   private void initImpl( Context context, AttributeSet attrs )
   {
      content = (ViewGroup) findViewById( android.R.id.content );
      button1 = (Button) content.findViewById( android.R.id.button1 );
      button2 = (Button) content.findViewById( android.R.id.button2 );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.BottomButtonBarLayout,
                                                               0,
                                                               0 );
      
      setBackgroundColor( array );
      
      int numAdded = setButtonAttr( context, button1, array, new int[]
      { R.styleable.BottomButtonBarLayout_button1Text,
       R.styleable.BottomButtonBarLayout_button1Drawable,
       R.styleable.BottomButtonBarLayout_button1OnClick } );
      
      button1.setVisibility( numAdded > 0 ? VISIBLE : GONE );
      
      numAdded = setButtonAttr( context, button2, array, new int[]
      { R.styleable.BottomButtonBarLayout_button2Text,
       R.styleable.BottomButtonBarLayout_button2Drawable,
       R.styleable.BottomButtonBarLayout_button2OnClick } );
      
      button2.setVisibility( numAdded > 0 ? VISIBLE : GONE );
      
      layoutButtons( array );
      
      array.recycle();
   }
   


   private final void layoutButtons( TypedArray array )
   {
      Button visibleButton = null;
      int numButtonsVisible = 0;
      
      if ( button1.getVisibility() == VISIBLE )
      {
         visibleButton = button1;
         ++numButtonsVisible;
      }
      
      if ( button2.getVisibility() == VISIBLE )
      {
         visibleButton = button2;
         ++numButtonsVisible;
      }
      
      if ( numButtonsVisible == 1 )
      {
         ( (FrameLayout.LayoutParams) content.getLayoutParams() ).width = FrameLayout.LayoutParams.WRAP_CONTENT;
         final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) visibleButton.getLayoutParams();
         
         params.setMargins( 5, 5, 5, 5 );
         
         if ( !array.getBoolean( R.styleable.BottomButtonBarLayout_stretchSingleButton,
                                 true ) )
         {
            params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.weight = 0;
         }
      }
   }
   


   private final void setBackgroundColor( TypedArray array )
   {
      final int color = array.getColor( R.styleable.BottomButtonBarLayout_backgroundColor,
                                        -1 );
      
      if ( color != -1 )
      {
         content.setBackgroundColor( color );
      }
      
      setBackgroundDrawable( content.getBackground() );
   }
   


   private final int setButtonAttr( Context context,
                                    Button button,
                                    TypedArray array,
                                    int[] attrs )
   {
      int numAdded = 0;
      
      final String text = array.getString( attrs[ 0 ] );
      
      if ( text != null )
      {
         ++numAdded;
         button.setText( text );
         
         if ( array.hasValue( attrs[ 1 ] ) )
         {
            button.setCompoundDrawables( array.getDrawable( attrs[ 1 ] ),
                                         null,
                                         null,
                                         null );
            ++numAdded;
         }
         
         if ( array.hasValue( attrs[ 2 ] ) )
         {
            if ( !context.isRestricted() )
            {
               final String handlerName = array.getString( attrs[ 2 ] );
               
               if ( !TextUtils.isEmpty( handlerName ) )
               {
                  button.setOnClickListener( new ButtonOnClickHandler( handlerName ) );
                  ++numAdded;
               }
            }
            else
            {
               throw new IllegalStateException( "The buttonXOnClick attribute cannot "
                  + "be used within a restricted context" );
            }
         }
      }
      
      return numAdded;
   }
   
   
   private final class ButtonOnClickHandler implements OnClickListener
   {
      private final String handlerName;
      
      private Method handler;
      
      

      public ButtonOnClickHandler( String handlerName )
      {
         this.handlerName = handlerName;
      }
      


      public void onClick( View v )
      {
         if ( handler == null )
         {
            try
            {
               handler = getContext().getClass().getMethod( handlerName,
                                                            View.class );
            }
            catch ( NoSuchMethodException e )
            {
               int id = getId();
               String idText = id == NO_ID ? "" : " with id '"
                  + getContext().getResources().getResourceEntryName( id )
                  + "'";
               throw new IllegalStateException( "Could not find a method "
                  + handlerName + "(View) in the activity "
                  + getContext().getClass() + " for onClick handler"
                  + " on view " + BottomButtonBarLayout.this.getClass()
                  + idText, e );
            }
         }
         
         try
         {
            handler.invoke( getContext(), BottomButtonBarLayout.this );
         }
         catch ( IllegalAccessException e )
         {
            throw new IllegalStateException( "Could not execute non "
               + "public method of the activity", e );
         }
         catch ( InvocationTargetException e )
         {
            throw new IllegalStateException( "Could not execute "
               + "method of the activity", e );
         }
      }
   }
}
