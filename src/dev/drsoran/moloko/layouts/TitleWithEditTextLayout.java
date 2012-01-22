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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.widgets.ClearableEditText;


public class TitleWithEditTextLayout extends TitleWithViewLayout
{
   private EditText editText;
   
   private int imeTypeEnabled;
   
   private final static int imeTypeDisabled = InputType.TYPE_NULL;
   
   
   
   public TitleWithEditTextLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      initView( context, attrs, getViewContainer() );
   }
   
   
   
   public TitleWithEditTextLayout( Context context, AttributeSet attrs,
      ViewGroup root )
   {
      super( context, attrs, root );
      initView( context, attrs, getViewContainer() );
   }
   
   
   
   @Override
   public EditText getView()
   {
      return editText;
   }
   
   
   
   public void setText( CharSequence value )
   {
      editText.setText( value );
   }
   
   
   
   public Editable getText()
   {
      return editText.getText();
   }
   
   
   
   public void addTextChangedListener( TextWatcher watcher )
   {
      editText.addTextChangedListener( watcher );
   }
   
   
   
   public void removeTextChangedListener( TextWatcher watcher )
   {
      editText.removeTextChangedListener( watcher );
   }
   
   
   
   public final void setHint( CharSequence hint )
   {
      editText.setHint( hint );
   }
   
   
   
   public final void setHint( int resid )
   {
      editText.setHint( resid );
   }
   
   
   
   @Override
   public void setEnabled( boolean enabled )
   {
      super.setEnabled( enabled );
      editText.setEnabled( enabled );
      editText.setFocusable( enabled );
      editText.setFocusableInTouchMode( enabled );
      editText.setInputType( enabled ? imeTypeEnabled : imeTypeDisabled );
   }
   
   
   
   private void initView( Context context,
                          AttributeSet attrs,
                          ViewGroup container )
   {
      editText = new ClearableEditText( context, attrs );
      editText.setLayoutParams( generateDefaultLayoutParams() );
      editText.setId( R.id.title_with_edit_text );
      
      imeTypeEnabled = editText.getInputType();
      
      container.addView( editText );
   }
}
