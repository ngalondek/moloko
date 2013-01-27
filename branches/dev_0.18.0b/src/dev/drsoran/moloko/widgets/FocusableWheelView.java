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

package dev.drsoran.moloko.widgets;

import kankan.wheel.widget.WheelView;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;
import dev.drsoran.moloko.util.UIUtils;


public class FocusableWheelView extends WheelView implements Checkable
{
   static class SavedState extends BaseSavedState
   {
      boolean checked;
      
      
      
      SavedState( Parcelable superState )
      {
         super( superState );
      }
      
      
      
      private SavedState( Parcel in )
      {
         super( in );
         checked = (Boolean) in.readValue( null );
      }
      
      
      
      @Override
      public void writeToParcel( Parcel out, int flags )
      {
         super.writeToParcel( out, flags );
         out.writeValue( checked );
      }
      
      
      
      @Override
      public String toString()
      {
         return FocusableWheelView.class.getSimpleName() + ".SavedState{"
            + Integer.toHexString( System.identityHashCode( this ) )
            + " checked=" + checked + "}";
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
   
   private boolean checked = false;
   
   
   
   public FocusableWheelView( Context context )
   {
      super( context );
      init();
   }
   
   
   
   public FocusableWheelView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      init();
   }
   
   
   
   public FocusableWheelView( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
      init();
   }
   
   
   
   private void init()
   {
      setFocusable( true );
      setFocusableInTouchMode( false );
      setClickable( true );
   }
   
   
   
   @Override
   public boolean isChecked()
   {
      return checked;
   }
   
   
   
   @Override
   public void setChecked( boolean checked )
   {
      if ( checked != this.checked )
      {
         this.checked = checked;
         refreshDrawableState();
      }
   }
   
   
   
   @Override
   public void toggle()
   {
      setChecked( !checked );
   }
   
   
   
   @Override
   public boolean performClick()
   {
      toggle();
      return super.performClick();
   }
   
   
   
   @Override
   public boolean dispatchPopulateAccessibilityEvent( AccessibilityEvent event )
   {
      boolean populated = super.dispatchPopulateAccessibilityEvent( event );
      
      if ( !populated )
      {
         event.setChecked( checked );
      }
      
      return populated;
   }
   
   
   
   @Override
   public Parcelable onSaveInstanceState()
   {
      final Parcelable superState = super.onSaveInstanceState();
      final SavedState ss = new SavedState( superState );
      
      ss.checked = isChecked();
      return ss;
   }
   
   
   
   @Override
   public void onRestoreInstanceState( Parcelable state )
   {
      final SavedState ss = (SavedState) state;
      
      super.onRestoreInstanceState( ss.getSuperState() );
      setChecked( ss.checked );
      requestLayout();
   }
   
   
   
   @Override
   protected void onFocusChanged( boolean gainFocus,
                                  int direction,
                                  Rect previouslyFocusedRect )
   {
      super.onFocusChanged( gainFocus, direction, previouslyFocusedRect );
      
      if ( !gainFocus )
         setChecked( false );
   }
   
   
   
   @Override
   public boolean onKeyDown( int keyCode, KeyEvent event )
   {
      if ( isChecked() )
      {
         if ( keyCode == KeyEvent.KEYCODE_DPAD_UP )
         {
            setCurrentItem( getCurrentItem() - 1, true );
            return true;
         }
         if ( keyCode == KeyEvent.KEYCODE_DPAD_DOWN )
         {
            setCurrentItem( getCurrentItem() + 1, true );
            return true;
         }
      }
      
      return super.onKeyDown( keyCode, event );
   }
   
   
   
   @Override
   protected int[] onCreateDrawableState( int extraSpace )
   {
      final int[] drawableState = super.onCreateDrawableState( extraSpace + 1 );
      if ( isChecked() )
      {
         mergeDrawableStates( drawableState, UIUtils.CHECKED_STATE_SET );
      }
      return drawableState;
   }
   
   
   
   @Override
   protected boolean verifyDrawable( Drawable who )
   {
      return super.verifyDrawable( who ) || who == getCenterDrawable();
   }
   
   
   
   @Override
   protected void drawableStateChanged()
   {
      super.drawableStateChanged();
      
      if ( getCenterDrawable() != null )
      {
         final int[] myDrawableState = getDrawableState();
         
         // Set the state of the Drawable
         getCenterDrawable().setState( myDrawableState );
         
         invalidateWheel( false );
      }
   }
}
