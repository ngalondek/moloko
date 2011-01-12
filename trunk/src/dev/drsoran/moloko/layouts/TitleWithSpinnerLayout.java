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
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import dev.drsoran.moloko.R;


public class TitleWithSpinnerLayout extends TitleWithViewLayout
{
   public interface StringConverter
   {
      String convertToString( Object object );
   }
   
   private Spinner spinner;
   
   private StringConverter converter;
   
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
   


   public void setStringConverter( StringConverter converter )
   {
      this.converter = converter;
   }
   


   public void setSelection( String value, int notFoundIndex )
   {
      final SpinnerAdapter adapter = spinner.getAdapter();
      
      if ( adapter != null )
      {
         boolean found = false;
         final int cnt = adapter.getCount();
         
         if ( value != null )
         {
            for ( int i = 0; !found && i < cnt; i++ )
            {
               final Object item = adapter.getItem( i );
               
               String spinnerValue = null;
               
               if ( converter != null )
                  spinnerValue = converter.convertToString( item );
               else if ( values != null && values.length > i )
                  spinnerValue = values[ i ];
               else
                  spinnerValue = item.toString();
               
               if ( spinnerValue.equals( value ) )
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
            spinner.setSelection( notFoundIndex );
      }
   }
   


   public String getValueAtPos( int pos )
   {
      if ( values != null && values.length > pos )
         return values[ pos ];
      else
         return null;
   }
   


   private void initView( Context context,
                          AttributeSet attrs,
                          ViewGroup container )
   {
      spinner = new Spinner( context );
      spinner.setId( R.id.title_with_spinner_layout );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleWithSpinner,
                                                               0,
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
}
