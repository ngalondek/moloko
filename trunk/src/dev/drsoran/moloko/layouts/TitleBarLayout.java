/*
 * Copyright (c) 2010 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.layouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.HomeActivity;


public class TitleBarLayout extends LinearLayout implements
         View.OnClickListener
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
      
      findViewById( R.id.app_titlebar_btn_home ).setOnClickListener( this );
      findViewById( R.id.app_titlebar_btn_search ).setOnClickListener( this );
      
      final int showButtons = array.getInt( R.styleable.TitleBar_showButton, 0 );
      
      // Show search button
      if ( ( showButtons & 1 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_search );
         setVisible( R.id.app_titlebar_btn_search );
      }
      
      // Show home button
      if ( ( showButtons & 2 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_home );
         setVisible( R.id.app_titlebar_btn_home );
      }
      
      array.recycle();
   }
   


   private void setVisible( int id )
   {
      findViewById( id ).setVisibility( VISIBLE );
   }
   


   public void onClick( View v )
   {
      switch ( v.getId() )
      {
         case R.id.app_titlebar_btn_search:
            if ( getContext() instanceof Activity )
               ( (Activity) getContext() ).onSearchRequested();
            break;
         
         case R.id.app_titlebar_btn_home:
            getContext().startActivity( new Intent( getContext(),
                                                    HomeActivity.class ) );
            break;
         
         default :
            break;
      }
   }
}
