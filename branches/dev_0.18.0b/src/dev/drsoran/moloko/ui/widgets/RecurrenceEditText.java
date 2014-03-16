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
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.rtm.parsing.GrammarException;


public class RecurrenceEditText extends ClearableEditText
{
   private final IRecurrenceParsing recurrenceParsing;
   
   private Recurrence recurrence;
   
   private IValueChangedListener valueChangedListener;
   
   
   
   public RecurrenceEditText( Context context )
   {
      this( context, null, android.R.attr.editTextStyle );
   }
   
   
   
   public RecurrenceEditText( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.editTextStyle );
   }
   
   
   
   public RecurrenceEditText( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
      recurrenceParsing = isInEditMode() ? null
                                        : UiContext.get( context )
                                                   .getParsingService()
                                                   .getRecurrenceParsing();
   }
   
   
   
   public void setRecurrence( Recurrence recurrence )
   {
      setRecurrenceImpl( recurrence );
      setRecurrenceSentenceEditText();
   }
   
   
   
   public void setRecurrence( String recurrenceSentence )
   {
      commitInput( recurrenceSentence );
   }
   
   
   
   public void setValueChangedListener( IValueChangedListener listener )
   {
      this.valueChangedListener = listener;
   }
   
   
   
   public ValidationResult validate()
   {
      if ( isEnabled() )
      {
         Recurrence recurrence;
         try
         {
            recurrence = parseRecurrenceSentence( getTextTrimmed() );
         }
         catch ( GrammarException e )
         {
            recurrence = null;
         }
         
         return validateRecurrence( recurrence );
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   public Recurrence getRecurrence()
   {
      commitInput( getTextTrimmed() );
      return recurrence;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UiUtils.hasInputCommitted( actionCode ) )
      {
         setRecurrenceBySentence( getTextTrimmed() );
         
         final boolean inputValid = validateRecurrence( recurrence ).isOk();
         stayInEditText = !inputValid;
         
         if ( inputValid )
         {
            setRecurrenceSentenceEditText();
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
         recurrence = handleEmptyInputString();
      }
   }
   
   
   
   private void setRecurrenceSentenceEditText()
   {
      if ( isRecurrencePatternValid() )
      {
         if ( TextUtils.isEmpty( recurrence.getPattern() ) )
         {
            setText( null );
         }
         else
         {
            try
            {
               final String sentence = recurrenceParsing.parseRecurrencePatternToSentence( recurrence.getPattern(),
                                                                                           recurrence.isEveryRecurrence() );
               setText( sentence );
            }
            catch ( GrammarException e )
            {
               setText( getContext().getString( R.string.task_datetime_err_recurr ) );
            }
         }
      }
   }
   
   
   
   private Recurrence parseRecurrenceSentence( String recurrenceSentence ) throws GrammarException
   {
      if ( TextUtils.isEmpty( recurrenceSentence ) )
      {
         return handleEmptyInputString();
      }
      else
      {
         return recurrenceParsing.parseRecurrence( recurrenceSentence );
      }
   }
   
   
   
   private Recurrence handleEmptyInputString()
   {
      return Recurrence.EMPTY;
   }
   
   
   
   private void setRecurrenceImpl( Recurrence recurrence )
   {
      this.recurrence = recurrence;
   }
   
   
   
   private void setRecurrenceBySentence( String recurrenceSentence )
   {
      try
      {
         recurrence = parseRecurrenceSentence( recurrenceSentence );
      }
      catch ( GrammarException e )
      {
         recurrence = null;
      }
   }
   
   
   
   private boolean isRecurrencePatternValid()
   {
      return recurrence != null;
   }
   
   
   
   private ValidationResult validateRecurrence( Recurrence recurrence )
   {
      final boolean valid = recurrence != null;
      if ( !valid )
      {
         return new ValidationResult( getContext().getString( R.string.task_edit_validate_recurrence,
                                                              getTextTrimmed() ) ).setSourceView( this )
                                                                                  .setFocusOnErrorView( this );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private void notifyChange()
   {
      if ( valueChangedListener != null )
      {
         valueChangedListener.onValueChanged( recurrence, Recurrence.class );
      }
   }
   
   
   
   private void commitInput( String input )
   {
      if ( isEnabled() )
      {
         setRecurrenceBySentence( input );
         setRecurrenceSentenceEditText();
      }
   }
}
