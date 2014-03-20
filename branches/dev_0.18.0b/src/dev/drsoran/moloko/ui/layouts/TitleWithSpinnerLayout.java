/*
 * Copyright (c) 2012 Ronny R�hricht
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

package dev.drsoran.moloko.ui.layouts;

import java.util.Arrays;
import java.util.Collection;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import dev.drsoran.moloko.R;


public class TitleWithSpinnerLayout extends TitleWithViewLayout
{
   private Spinner spinner;
   
   private String[] values;
   
   
   
   public TitleWithSpinnerLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      initView( context, attrs, getViewContainer() );
   }
   
   
   
   public TitleWithSpinnerLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs, root );
      initView( context, attrs, getViewContainer() );
   }
   
   
   
   @Override
   protected Parcelable onSaveInstanceState()
   {
      final Parcelable superState = super.onSaveInstanceState();
      final SavedState state = new SavedState( superState );
      state.spinnerState = spinner.onSaveInstanceState();
      
      return state;
   }
   
   
   
   @Override
   protected void onRestoreInstanceState( Parcelable state )
   {
      final SavedState ss = (SavedState) state;
      super.onRestoreInstanceState( ss.getSuperState() );
      
      values = ss.values;
      spinner.onRestoreInstanceState( ss.spinnerState );
   }
   
   
   
   @Override
   public Spinner getView()
   {
      return spinner;
   }
   
   
   
   @Override
   public void setEnabled( boolean enabled )
   {
      super.setEnabled( enabled );
      spinner.setEnabled( false );
   }
   
   
   
   public void setAdapter( SpinnerAdapter adapter )
   {
      spinner.setAdapter( adapter );
   }
   
   
   
   public void setOnItemSelectedListener( OnItemSelectedListener listener )
   {
      spinner.setOnItemSelectedListener( listener );
   }
   
   
   
   public void setSelectionByEntry( String entry, int notFoundIndex )
   {
      final SpinnerAdapter adapter = spinner.getAdapter();
      
      if ( adapter != null )
      {
         boolean found = false;
         final int cnt = adapter.getCount();
         
         if ( entry != null )
         {
            for ( int i = 0; !found && i < cnt; i++ )
            {
               final Object item = adapter.getItem( i );
               
               String spinnerEntry = item.toString();
               
               if ( spinnerEntry.equals( entry ) )
               {
                  if ( spinner.getSelectedItemPosition() != i )
                  {
                     spinner.setSelection( i );
                  }
                  
                  found = true;
               }
            }
         }
         
         if ( !found && notFoundIndex != -1 && cnt > notFoundIndex )
         {
            spinner.setSelection( notFoundIndex );
         }
      }
   }
   
   
   
   public void setSelectionByValue( String value, int notFoundIndex )
   {
      if ( values != null )
      {
         boolean found = false;
         
         for ( int i = 0; !found && i < values.length; i++ )
         {
            if ( value == null
               && values[ i ] == null
               || ( value != null && values[ i ] != null && value.equals( values[ i ] ) ) )
            {
               if ( spinner.getSelectedItemPosition() != i )
               {
                  spinner.setSelection( i );
               }
               
               found = true;
            }
         }
         
         if ( !found && notFoundIndex != -1 && values.length > notFoundIndex )
         {
            spinner.setSelection( notFoundIndex );
         }
      }
   }
   
   
   
   public void setValues( Collection< String > values )
   {
      if ( values == null )
      {
         throw new IllegalArgumentException( "values" );
      }
      
      this.values = new String[ values.size() ];
      this.values = values.toArray( this.values );
   }
   
   
   
   public void setValues( String[] values )
   {
      if ( values == null )
      {
         throw new IllegalArgumentException( "values are null" );
      }
      
      this.values = Arrays.copyOf( values, values.length );
   }
   
   
   
   public String getValueAtPos( int pos )
   {
      if ( values != null && values.length > pos )
      {
         return values[ pos ];
         
      }
      return null;
   }
   
   
   
   public String getSelectedValue()
   {
      return getValueAtPos( spinner.getSelectedItemPosition() );
   }
   
   
   
   private void initView( Context context,
                          AttributeSet attrs,
                          ViewGroup container )
   {
      spinner = new Spinner( context, attrs );
      spinner.setId( R.id.title_with_spinner_layout );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleWithSpinner,
                                                               R.attr.titleWithSpinnerStyle,
                                                               0 );
      
      final int entriesId = array.getResourceId( R.styleable.TitleWithSpinner_spinnerEntries,
                                                 -1 );
      
      if ( entriesId != -1 )
      {
         final ArrayAdapter< CharSequence > adapter = ArrayAdapter.createFromResource( context,
                                                                                       entriesId,
                                                                                       android.R.layout.simple_spinner_item );
         adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
         
         setAdapter( adapter );
      }
      
      final int valuesId = array.getResourceId( R.styleable.TitleWithSpinner_spinnerValues,
                                                -1 );
      
      if ( valuesId != -1 )
      {
         values = context.getResources().getStringArray( valuesId );
      }
      
      array.recycle();
      
      container.addView( spinner );
   }
   
   
   private static class SavedState extends BaseSavedState
   {
      String[] values;
      
      Parcelable spinnerState;
      
      
      
      public SavedState( Parcelable superState )
      {
         super( superState );
      }
      
      
      
      @Override
      public void writeToParcel( Parcel dest, int flags )
      {
         super.writeToParcel( dest, flags );
         
         dest.writeStringArray( values );
         dest.writeParcelable( spinnerState, flags );
      }
      
      
      
      private SavedState( Parcel in )
      {
         super( in );
         
         in.readStringArray( values );
         spinnerState = in.readParcelable( null );
      }
      
      @SuppressWarnings( "unused" )
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