/* 
 *	Copyright (c) 2011 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.widgets;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;
import dev.drsoran.moloko.IChangesTarget;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.datetime.DateParserFactory;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


public class EstimateEditText extends ClearableEditText
{
   public final static String EDIT_ESTIMATE_TEXT = "edit_estimate_text";
   
   private Long estimateMillis;
   
   private boolean isSupportingFreeTextInput;
   
   private IChangesTarget changes;
   
   
   
   public EstimateEditText( Context context )
   {
      this( context, null, android.R.attr.editTextStyle );
   }
   
   
   
   public EstimateEditText( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.editTextStyle );
   }
   
   
   
   public EstimateEditText( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
      checkFreeTextInput();
      
      setEnabled( isSupportingFreeTextInput );
   }
   
   
   
   public void setEstimate( long estimateMillis )
   {
      setEstimateByMillis( estimateMillis );
      updateEditText();
   }
   
   
   
   public void setEstimate( String estimate )
   {
      if ( isSupportingFreeTextInput )
      {
         setEstimateByString( estimate );
         updateEditText();
      }
   }
   
   
   
   public void putInitialValue( Bundle initialValues )
   {
      initialValues.putString( EDIT_ESTIMATE_TEXT, getText().toString() );
   }
   
   
   
   public void setChangesTarget( IChangesTarget changes )
   {
      this.changes = changes;
   }
   
   
   
   public boolean validate()
   {
      if ( isSupportingFreeTextInput )
      {
         final Long res = parseEstimate( getText().toString() );
         return validateEstimate( res );
      }
      else
      {
         return true;
      }
   }
   
   
   
   public Long getEstimateMillis()
   {
      return estimateMillis;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UIUtils.hasInputCommitted( actionCode ) )
      {
         setEstimateByString( getText().toString() );
         
         final boolean inputValid = validateEstimate( estimateMillis );
         stayInEditText = !inputValid;
         
         if ( inputValid )
            updateEditText();
      }
      
      if ( !stayInEditText )
         super.onEditorAction( actionCode );
   }
   
   
   
   @Override
   protected void onTextChanged( CharSequence text,
                                 int start,
                                 int before,
                                 int after )
   {
      super.onTextChanged( text, start, before, after );
      
      putTextChange();
      
      if ( TextUtils.isEmpty( text ) )
      {
         estimateMillis = handleEmptyInputString();
      }
   }
   
   
   
   private void updateEditText()
   {
      if ( isEstimateValid() )
      {
         if ( estimateMillis.longValue() == -1 )
         {
            setText( null );
         }
         else
         {
            setText( MolokoDateUtils.formatEstimated( getContext(),
                                                      estimateMillis.longValue() ) );
         }
      }
   }
   
   
   
   private Long parseEstimate( String estimateString )
   {
      if ( TextUtils.isEmpty( estimateString ) )
      {
         return handleEmptyInputString();
      }
      else
      {
         return RtmDateTimeParsing.parseEstimated( estimateString );
      }
   }
   
   
   
   private Long handleEmptyInputString()
   {
      return Long.valueOf( -1 );
   }
   
   
   
   private void setEstimateByMillis( long estimateMillis )
   {
      this.estimateMillis = estimateMillis;
   }
   
   
   
   private void setEstimateByString( String estimateString )
   {
      estimateMillis = parseEstimate( estimateString );
   }
   
   
   
   private boolean isEstimateValid()
   {
      return estimateMillis != null;
   }
   
   
   
   private boolean validateEstimate( Long estimate )
   {
      final boolean valid = estimate != null;
      if ( !valid )
      {
         Toast.makeText( getContext(),
                         getContext().getString( R.string.task_edit_validate_estimate,
                                                 getText().toString() ),
                         Toast.LENGTH_LONG )
              .show();
         
         requestFocus();
      }
      
      return valid;
   }
   
   
   
   private void putTextChange()
   {
      if ( changes != null )
         changes.putChange( EDIT_ESTIMATE_TEXT,
                            getText().toString(),
                            String.class );
   }
   
   
   
   private void checkFreeTextInput()
   {
      if ( !isInEditMode() )
      {
         final Locale resLocale = MolokoApp.get( getContext() )
                                           .getActiveResourcesLocale();
         isSupportingFreeTextInput = DateParserFactory.existsDateParserWithMatchingLocale( resLocale );
      }
   }
}
