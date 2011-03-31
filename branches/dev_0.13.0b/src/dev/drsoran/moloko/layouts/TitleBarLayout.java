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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.HomeActivity;


public class TitleBarLayout extends LinearLayout implements
         View.OnClickListener
{
   private final Checkable addTaskBtn;
   
   

   public TitleBarLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setId( R.id.app_title_bar );
      setOrientation( LinearLayout.VERTICAL );
      
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
         setBtnVisible( R.id.app_titlebar_btn_search );
      }
      
      // Show home button
      if ( ( showButtons & 2 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_home );
         setBtnVisible( R.id.app_titlebar_btn_home );
      }
      
      // Show add task button
      if ( ( showButtons & 4 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_add_task );
         addTaskBtn = (Checkable) setBtnVisible( R.id.app_titlebar_btn_add_task );
      }
      else
      {
         addTaskBtn = null;
      }
      
      array.recycle();
   }
   


   public void showAddTaskInput( boolean visible )
   {
      if ( addTaskBtn != null )
      {
         if ( addTaskBtn.isChecked() != visible )
         {
            addTaskBtn.setChecked( visible );
            addOrRemoveAddTaskLayout();
         }
      }
   }
   


   private void setVisible( int id )
   {
      findViewById( id ).setVisibility( VISIBLE );
   }
   


   private View setBtnVisible( int id )
   {
      final View btn = findViewById( id );
      btn.setOnClickListener( this );
      btn.setVisibility( View.VISIBLE );
      
      return btn;
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
            final Intent intent = new Intent( getContext(), HomeActivity.class );
            
            // If we return to the home screen from another activity
            // we clear the stack.
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            
            getContext().startActivity( intent );
            break;
         
         case R.id.app_titlebar_btn_add_task:
            if ( addTaskBtn != null )
               addOrRemoveAddTaskLayout();
            break;
         
         default :
            break;
      }
   }
   


   private final void addOrRemoveAddTaskLayout()
   {
      if ( addTaskBtn.isChecked() )
      {
         LayoutInflater.from( getContext() )
                       .inflate( R.layout.app_titlebar_quick_add_task,
                                 this,
                                 true )
                       .findViewById( R.id.app_titlebar_quick_add_task_edit )
                       .requestFocus();
      }
      else
         removeView( findViewById( R.id.app_titlebar_quick_add_task_layout ) );
      
      requestLayout();
   }
}
