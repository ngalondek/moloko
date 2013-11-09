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
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.services.IDateFormatterService;


public class DueEditText extends ClearableEditText
{
   private final static int FORMAT = IDateFormatterService.FORMAT_NUMERIC
      | IDateFormatterService.FORMAT_WITH_YEAR;
   
   private Due due;
   
   private IValueChangedListener valueChangedListener;
   
   
   
   public DueEditText( Context context )
   {
      this( context, null, android.R.attr.editTextStyle );
   }
   
   
   
   public DueEditText( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.editTextStyle );
   }
   
   
   
   public DueEditText( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   public void setDue( Due due )
   {
      setDueImpl( due );
      updateEditDueText();
   }
   
   
   
   public void setDue( String dueString )
   {
      commitInput( dueString );
   }
   
   
   
   public void setValueChangedListener( IValueChangedListener listener )
   {
      this.valueChangedListener = listener;
   }
   
   
   
   public ValidationResult validate()
   {
      if ( isEnabled() )
      {
         final Due due = parseDue( getTextTrimmed() );
         return validateDue( due );
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   public Due getDue()
   {
      commitInput( getTextTrimmed() );
      return due;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UiUtils.hasInputCommitted( actionCode ) )
      {
         setDueByParseString( getTextTrimmed() );
         
         final boolean inputValid = validateDue( due ).isOk();
         stayInEditText = !inputValid;
         
         if ( inputValid )
         {
            updateEditDueText();
            notifyChange();
         }
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
         due = handleEmptyInputString();
      }
   }
   
   
   
   private void updateEditDueText()
   {
      if ( isDueValid() )
      {
         if ( !due.hasDate() )
         {
            setText( null );
         }
         else if ( due.hasDueTime() )
         {
            setText( getUiContext().getDateFormatter()
                                   .formatDateTime( due.getMillisUtc(), FORMAT ) );
         }
         else
         {
            setText( getUiContext().getDateFormatter()
                                   .formatDate( due.getMillisUtc(), FORMAT ) );
         }
      }
   }
   
   
   
   private Due parseDue( String dueStr )
   {
      if ( TextUtils.isEmpty( dueStr ) )
      {
         return handleEmptyInputString();
      }
      else
      {
         try
         {
            final MolokoCalendar cal = getUiContext().getParsingService()
                                                     .getDateTimeParsing()
                                                     .parseDateTime( dueStr );
            return new Due( cal.getTimeInMillis(), cal.hasTime() );
         }
         catch ( GrammarException e )
         {
            return null;
         }
      }
   }
   
   
   
   private Due handleEmptyInputString()
   {
      return Due.EMPTY;
   }
   
   
   
   private void setDueImpl( Due due )
   {
      if ( !isDueValid() && due == null )
      {
         this.due = Due.EMPTY;
      }
      
      if ( due != null )
      {
         this.due = due;
      }
   }
   
   
   
   private void setDueByParseString( String dueString )
   {
      due = parseDue( dueString );
   }
   
   
   
   private boolean isDueValid()
   {
      return due != null;
   }
   
   
   
   private ValidationResult validateDue( Due due )
   {
      final boolean valid = due != null;
      if ( !valid )
      {
         return new ValidationResult( getContext().getString( R.string.task_edit_validate_due,
                                                              getTextTrimmed() ),
                                      this );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private void notifyChange()
   {
      if ( valueChangedListener != null )
      {
         valueChangedListener.onValueChanged( due, Due.class );
      }
   }
   
   
   
   private void commitInput( String input )
   {
      if ( isEnabled() )
      {
         setDueByParseString( input );
         updateEditDueText();
      }
   }
}
