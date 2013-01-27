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

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import dev.drsoran.moloko.IChangesTarget;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ValidationResult;
import dev.drsoran.moloko.grammar.recurrence.RecurrenceParserFactory;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;


public class RecurrenceEditText extends ClearableEditText
{
   public final static String EDIT_RECURRENCE_TEXT = "edit_recurrence_text";
   
   private Pair< String, Boolean > recurrencePattern;
   
   private boolean isSupportingFreeTextInput;
   
   private IChangesTarget changes;
   
   
   
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
      checkFreeTextInput();
      
      setEnabled( isSupportingFreeTextInput );
   }
   
   
   
   public void setRecurrence( String recurrencePattern,
                              boolean isEveryRecurrence )
   {
      setRecurrenceByPattern( recurrencePattern, isEveryRecurrence );
      updateEditText();
   }
   
   
   
   public void setRecurrence( String recurrenceSentence )
   {
      commitInput( recurrenceSentence );
   }
   
   
   
   public void putInitialValue( Bundle initialValues )
   {
      initialValues.putString( EDIT_RECURRENCE_TEXT, getTextTrimmed() );
   }
   
   
   
   public void setChangesTarget( IChangesTarget changes )
   {
      this.changes = changes;
   }
   
   
   
   public ValidationResult validate()
   {
      if ( isSupportingFreeTextInput )
      {
         final Pair< String, Boolean > res = parseRecurrenceSentence( getTextTrimmed() );
         return validateRecurrence( res );
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   public Pair< String, Boolean > getRecurrencePattern()
   {
      commitInput( getTextTrimmed() );
      return recurrencePattern;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UIUtils.hasInputCommitted( actionCode ) )
      {
         setRecurrenceBySentence( getTextTrimmed() );
         
         final boolean inputValid = validateRecurrence( recurrencePattern ).isOk();
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
         recurrencePattern = handleEmptyInputString();
      }
   }
   
   
   
   private void updateEditText()
   {
      if ( isRecurrencePatternValid() )
      {
         if ( TextUtils.isEmpty( recurrencePattern.first ) )
         {
            setText( null );
         }
         else
         {
            final String sentence = RecurrenceParsing.parseRecurrencePattern( getContext(),
                                                                              recurrencePattern.first,
                                                                              recurrencePattern.second );
            setText( ( sentence != null
                                       ? sentence
                                       : getContext().getString( R.string.task_datetime_err_recurr ) ) );
         }
      }
   }
   
   
   
   private Pair< String, Boolean > parseRecurrenceSentence( String recurrenceSentence )
   {
      if ( TextUtils.isEmpty( recurrenceSentence ) )
      {
         return handleEmptyInputString();
      }
      else
      {
         return RecurrenceParsing.parseRecurrence( recurrenceSentence );
      }
   }
   
   
   
   private Pair< String, Boolean > handleEmptyInputString()
   {
      return new Pair< String, Boolean >( Strings.EMPTY_STRING, Boolean.FALSE );
   }
   
   
   
   private void setRecurrenceByPattern( String recurrencePattern,
                                        boolean isEveryRecurrence )
   {
      this.recurrencePattern = Pair.create( recurrencePattern,
                                            isEveryRecurrence );
   }
   
   
   
   private void setRecurrenceBySentence( String recurrenceSentence )
   {
      recurrencePattern = parseRecurrenceSentence( recurrenceSentence );
   }
   
   
   
   private boolean isRecurrencePatternValid()
   {
      return recurrencePattern != null;
   }
   
   
   
   private ValidationResult validateRecurrence( Pair< String, Boolean > recurrencePattern )
   {
      final boolean valid = recurrencePattern != null;
      if ( !valid )
      {
         return new ValidationResult( getContext().getString( R.string.task_edit_validate_recurrence,
                                                              getTextTrimmed() ),
                                      this );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private void putTextChange()
   {
      if ( changes != null )
         changes.putChange( EDIT_RECURRENCE_TEXT,
                            getTextTrimmed(),
                            String.class );
   }
   
   
   
   private void checkFreeTextInput()
   {
      if ( !isInEditMode() )
      {
         final Locale resLocale = MolokoApp.get( getContext() )
                                           .getActiveResourcesLocale();
         isSupportingFreeTextInput = RecurrenceParserFactory.existsDateParserWithMatchingLocale( resLocale );
      }
   }
   
   
   
   private void commitInput( String input )
   {
      if ( isSupportingFreeTextInput )
      {
         setRecurrenceBySentence( input );
         updateEditText();
      }
   }
}
