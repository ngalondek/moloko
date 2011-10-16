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

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Strings;


public class TitleWithSpinnerLayout extends TitleWithViewLayout
{
   private final String TAG = "Moloko."
      + TitleWithSpinnerLayout.class.getSimpleName();
   
   
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
   
   
   
   public void setStringConverter( StringConverter converter )
   {
      this.converter = converter;
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
               
               String spinnerEntry = null;
               
               if ( converter != null )
                  spinnerEntry = converter.convertToString( item );
               else
                  spinnerEntry = item.toString();
               
               if ( spinnerEntry.equals( entry ) )
               {
                  if ( spinner.getSelectedItemPosition() != i )
                     spinner.setSelection( i );
                  
                  found = true;
               }
            }
         }
         
         if ( !found && notFoundIndex != -1 && cnt > notFoundIndex )
            spinner.setSelection( notFoundIndex );
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
                  spinner.setSelection( i );
               
               found = true;
            }
         }
         
         if ( !found && notFoundIndex != -1 && values.length > notFoundIndex )
            spinner.setSelection( notFoundIndex );
      }
   }
   
   
   
   public void setValues( List< String > values )
   {
      if ( values == null )
         throw new NullPointerException( "values are null" );
      
      this.values = new String[ values.size() ];
      for ( int i = 0; i < values.size(); ++i )
      {
         this.values[ i ] = values.get( i );
      }
   }
   
   
   
   public void setValues( String[] values )
   {
      if ( values == null )
         throw new NullPointerException( "values are null" );
      
      this.values = new String[ values.length ];
      for ( int i = 0; i < values.length; ++i )
      {
         this.values[ i ] = values[ i ];
      }
   }
   
   
   
   public void setValues( Cursor c, int colIdx )
   {
      if ( c == null )
         throw new NullPointerException( "cursor is null" );
      
      this.values = new String[ c.getCount() ];
      
      if ( c.getCount() > 0 )
      {
         boolean ok = c.moveToFirst();
         for ( int i = 0; ok && !c.isAfterLast(); c.moveToNext(), ++i )
         {
            this.values[ i ] = c.getString( colIdx );
         }
         
         if ( !ok )
         {
            this.values = null;
            LogUtils.logDBError( getContext(), TAG, Strings.EMPTY_STRING );
         }
      }
   }
   
   
   
   public String getValueAtPos( int pos )
   {
      if ( values != null && values.length > pos )
         return values[ pos ];
      else
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
      if ( !isInEditMode() )
      {
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
      }
      
      array.recycle();
      
      container.addView( spinner );
   }
}
