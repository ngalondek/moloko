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
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.HomeActivity;
import dev.drsoran.moloko.dialogs.AddRenameListDialog;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.widgets.ToggleImageButton;
import dev.drsoran.moloko.widgets.ToggleImageButton.OnCheckedChangeListener;
import dev.drsoran.rtm.RtmSmartFilter;


public class TitleBarLayout extends LinearLayout implements
         View.OnClickListener, OnCheckedChangeListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TitleBarLayout.class.getSimpleName();
   
   public final static int BUTTON_NONE = 0;
   
   public final static int BUTTON_SEARCH = 1 << 0;
   
   public final static int BUTTON_HOME = 1 << 1;
   
   public final static int BUTTON_ADD_TASK = 1 << 2;
   
   public final static int BUTTON_ADD_LIST = 1 << 3;
   
   private ToggleImageButton addTaskBtn;
   
   private RtmSmartFilter addSmartListFilter;
   
   

   public TitleBarLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setId( R.id.app_title_bar );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.app_titlebar, this, true );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleBar,
                                                               0,
                                                               0 );
      
      final String titleText = array.getString( R.styleable.TitleBar_titleBarText );
      if ( titleText != null )
         ( (TextView) findViewById( R.id.app_actionbar_text_color ) ).setText( titleText );
      
      final int showButtons = array.getInt( R.styleable.TitleBar_showButton, 0 );
      setButtonsVisible( showButtons );
      
      array.recycle();
   }
   


   @Override
   public boolean dispatchKeyEvent( KeyEvent event )
   {
      if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK && addTaskBtn != null
         && addTaskBtn.isChecked() )
      {
         showAddTaskInput( false );
         return true;
      }
      else
         return super.dispatchKeyEvent( event );
   }
   


   public void showAddTaskInput( boolean visible )
   {
      if ( addTaskBtn != null )
      {
         if ( addTaskBtn.isChecked() != visible )
            addTaskBtn.setChecked( visible );
      }
   }
   


   public void setAddSmartListFilter( RtmSmartFilter filter )
   {
      addSmartListFilter = filter;
   }
   


   public void setButtonsVisible( int buttonMask )
   {
      // Show search button
      {
         setVisible( R.id.app_titlebar_sep_search,
                     ( buttonMask & BUTTON_SEARCH ) == BUTTON_SEARCH );
         setBtnVisible( R.id.app_titlebar_btn_search,
                        ( buttonMask & BUTTON_SEARCH ) == BUTTON_SEARCH );
      }
      
      // Show home button
      {
         setVisible( R.id.app_titlebar_sep_home,
                     ( buttonMask & BUTTON_HOME ) == BUTTON_HOME );
         setBtnVisible( R.id.app_titlebar_btn_home,
                        ( buttonMask & BUTTON_HOME ) == BUTTON_HOME );
      }
      
      final boolean hasWriteAccess;
      if ( isInEditMode() )
         hasWriteAccess = true;
      else
         hasWriteAccess = !AccountUtils.isReadOnlyAccess( getContext() );
      
      // Show add task button
      {
         setVisible( R.id.app_titlebar_sep_add_task,
                     ( hasWriteAccess && ( ( buttonMask & BUTTON_ADD_TASK ) == BUTTON_ADD_TASK ) ) );
         addTaskBtn = (ToggleImageButton) setBtnVisible( R.id.app_titlebar_btn_add_task,
                                                         ( hasWriteAccess && ( ( buttonMask & BUTTON_ADD_TASK ) == BUTTON_ADD_TASK ) ) );
         addTaskBtn.setOnCheckedChangeListener( this );
      }
      
      // Show add list button
      {
         setVisible( R.id.app_titlebar_sep_add_list,
                     ( hasWriteAccess && ( buttonMask & BUTTON_ADD_LIST ) == BUTTON_ADD_LIST ) );
         setBtnVisible( R.id.app_titlebar_btn_add_list,
                        ( hasWriteAccess && ( buttonMask & BUTTON_ADD_LIST ) == BUTTON_ADD_LIST ) );
      }
   }
   


   @Override
   public void onCheckedChanged( ToggleImageButton button, boolean checked )
   {
      // hideOrShowAddTaskLayout();
   }
   


   private void setVisible( int id, boolean visible )
   {
      findViewById( id ).setVisibility( visible ? VISIBLE : GONE );
   }
   


   private View setBtnVisible( int id, boolean visible )
   {
      final View btn = findViewById( id );
      btn.setOnClickListener( this );
      btn.setVisibility( visible ? View.VISIBLE : GONE );
      
      return btn;
   }
   


   @Override
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
         
         case R.id.app_titlebar_btn_add_list:
            showAddTaskInput( false );
            AddRenameListDialog.newDialogWithFilter( getContext(),
                                                     addSmartListFilter )
                               .show();
            break;
         
         default :
            break;
      }
   }
   
   
   // private final void hideOrShowAddTaskLayout()
   // {
   // if ( addTaskBtn.isChecked() )
   // {
   // if ( addTaskSection == null )
   // {
   // addTaskSection = new AddTaskSection( this );
   // }
   //
   // addTaskSection.show( true, addTaskFilter );
   // }
   // else
   // {
   // if ( addTaskSection != null )
   // addTaskSection.show( false, null );
   // }
   //
   // requestLayout();
   // }
   
   final static class SavedState extends BaseSavedState
   {
      Parcelable addTaskState;
      
      

      SavedState( Parcelable superState )
      {
         super( superState );
      }
      


      private SavedState( Parcel in )
      {
         super( in );
         addTaskState = in.readParcelable( null );
      }
      


      @Override
      public void writeToParcel( Parcel out, int flags )
      {
         super.writeToParcel( out, flags );
         out.writeParcelable( addTaskState, flags );
      }
      
      public static final Parcelable.Creator< SavedState > CREATOR = new Parcelable.Creator< SavedState >()
      {
         @Override
         public SavedState createFromParcel( Parcel in )
         {
            return new SavedState( in );
         }
         


         @Override
         public SavedState[] newArray( int size )
         {
            return new SavedState[ size ];
         }
      };
   }
}
