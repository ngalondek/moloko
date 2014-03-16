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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.rtm.parsing.GrammarException;


public class EstimateEditText extends ClearableEditText
{
   // Stored as Long to have "invalid" as null state
   private Long estimationMillis;
   
   private IValueChangedListener valueChangedListener;
   
   
   
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
   }
   
   
   
   public void setEstimate( Estimation estimation )
   {
      setEstimateByMillis( estimation != null ? estimation.getMillis()
                                             : Constants.NO_TIME );
      setFormattedEditText();
   }
   
   
   
   public void setEstimate( long estimateMillis )
   {
      setEstimateByMillis( estimateMillis );
      setFormattedEditText();
   }
   
   
   
   public void setEstimate( String estimate )
   {
      commitInput( estimate );
   }
   
   
   
   public void setValueChangedListener( IValueChangedListener changes )
   {
      this.valueChangedListener = changes;
   }
   
   
   
   public ValidationResult validate()
   {
      if ( isEnabled() )
      {
         final Long res = parseEstimate( getTextTrimmed() );
         return validateEstimate( res );
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   public Long getEstimateMillis()
   {
      commitInput( getTextTrimmed() );
      return estimationMillis;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UiUtils.hasInputCommitted( actionCode ) )
      {
         setEstimateByString( getTextTrimmed() );
         
         final boolean inputValid = validateEstimate( estimationMillis ).isOk();
         stayInEditText = !inputValid;
         
         if ( inputValid )
         {
            setFormattedEditText();
         }
         
         notifyChange();
      }
      
      if ( !stayInEditText )
      {
         super.onEditorAction( actionCode );
      }
   }
   
   
   
   @Override
   protected void onTextChanged( CharSequence text,
                                 int start,
                                 int before,
                                 int after )
   {
      super.onTextChanged( text, start, before, after );
      
      if ( TextUtils.isEmpty( text ) )
      {
         estimationMillis = handleEmptyInputString();
      }
   }
   
   
   
   private void setFormattedEditText()
   {
      if ( isEstimateSet() )
      {
         if ( estimationMillis.longValue() == Constants.NO_TIME )
         {
            setText( null );
         }
         else
         {
            setText( UiContext.get( getContext() )
                              .getDateFormatter()
                              .formatEstimated( estimationMillis.longValue() ) );
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
         try
         {
            return UiContext.get( getContext() )
                            .getParsingService()
                            .getDateTimeParsing()
                            .parseEstimated( estimateString );
         }
         catch ( GrammarException e )
         {
            return null;
         }
      }
   }
   
   
   
   private Long handleEmptyInputString()
   {
      return Long.valueOf( Constants.NO_TIME );
   }
   
   
   
   private void setEstimateByMillis( long estimateMillis )
   {
      this.estimationMillis = Long.valueOf( estimateMillis );
   }
   
   
   
   private void setEstimateByString( String estimateString )
   {
      final Long res = parseEstimate( estimateString );
      estimationMillis = res;
   }
   
   
   
   private boolean isEstimateSet()
   {
      return estimationMillis != null;
   }
   
   
   
   private ValidationResult validateEstimate( Long estimate )
   {
      final boolean valid = estimate != null;
      if ( !valid )
      {
         return new ValidationResult( getContext().getString( R.string.task_edit_validate_estimate,
                                                              getTextTrimmed() ) ).setSourceView( this )
                                                                                  .setFocusOnErrorView( this );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private void notifyChange()
   {
      if ( valueChangedListener != null )
      {
         valueChangedListener.onValueChanged( new Estimation( getTextTrimmed(),
                                                              estimationMillis == null
                                                                                      ? Constants.NO_TIME
                                                                                      : estimationMillis.longValue() ),
                                              Estimation.class );
      }
   }
   
   
   
   private void commitInput( String input )
   {
      if ( isEnabled() )
      {
         setEstimateByString( input );
         setFormattedEditText();
      }
   }
}
